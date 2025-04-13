package br.com.RestauranteRioBranco.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.controller.OrderWebSocketController;
import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.OrderDTO;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.OrderEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;
import br.com.RestauranteRioBranco.repository.CustomerRepository;
import br.com.RestauranteRioBranco.repository.OrderRepository;
import br.com.RestauranteRioBranco.repository.ProductQtdRepository;
import br.com.RestauranteRioBranco.security.jwt.JwtUtils;
import br.com.RestauranteRioBranco.utils.ProductQtdJsonConverter;
import br.com.RestauranteRioBranco.utils.enums.EOrderStatus;
import br.com.RestauranteRioBranco.utils.enums.EPayment;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductQtdRepository productQtdRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ProductQtdJsonConverter proJsonConvert;
	
	@Autowired
	private OrderWebSocketController orderWebSocketController;
	
	private Double subTotal;
	
	@Transactional
	public OrderDTO createOrder(String token, String payment, String troco) {
		String jwt = token.replace("Bearer ", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDate today = dateTime.toLocalDate();
		Integer nOrder = orderRepository.findLastDailySequence(today) + 1;
		
		List<ProductQtdEntity> listProductsQtd = customer.getCart().getProducts();
		
		if (listProductsQtd == null || listProductsQtd.size() <= 0){
			throw new RuntimeException("Error: Nenhum produto no carrinho");
		}

		
		EPayment tipoPayment = EPayment.fromString(payment);
		
		subTotal = 0.0;
		listProductsQtd.forEach(item -> subTotal += item.getPrice());
		
		AddressEntity addressEntity = customer.getAddress().stream().filter((item) -> item.getIsSelected().equals(true)).toList().get(0);
		
		
		OrderDTO orderDTO = new OrderDTO(nOrder, dateTime, EOrderStatus.AGUARDANDO_APROVACAO,
				customer.getId(), customer.getUser().getName(),
				listProductsQtd, addressEntity,
				tipoPayment, troco, subTotal, 7.0, subTotal+7);
		
		List<ProductQtdEntity> productsCopy = orderDTO.getProductsQtdJson()
			    .stream()
			    .map(original -> original) 
			    .collect(Collectors.toList());
		
		orderDTO.setProductsQtdJson(productsCopy);
		OrderEntity orderEntity = orderRepository.save(new OrderEntity(orderDTO));
		
		customer.getCart().getProducts().removeIf(item -> {
			item.setCart(null);
			productQtdRepository.delete(item);
			return true;
			});
		customerRepository.save(customer);
		orderWebSocketController.notifyNewOrder(orderDTO);
		return new OrderDTO(orderRepository.findById(orderEntity.getId()).get());
	}
	
	@Scheduled(fixedRate = 60000)
	public void CancelOrdersNotApproved() {
		log.info("Verificando pedidos não aprovados para cancelamento automático...");
		LocalDateTime limit = LocalDateTime.now().minusMinutes(12);
		
		List<OrderEntity> pedidosPendentes = 
				orderRepository.findByStatus(EOrderStatus.AGUARDANDO_APROVACAO)
				.stream().filter((item) -> item.getDateTime().isBefore(limit)).toList();
		
		for (OrderEntity pedido : pedidosPendentes) {
            pedido.setStatus(EOrderStatus.CANCELADO);
        }
		orderRepository.saveAll(pedidosPendentes);
		for (OrderEntity pedido : pedidosPendentes) {
            pedido.setStatus(EOrderStatus.CANCELADO);
            log.info("⏱️ Pedido " + pedido.getId() + " cancelado automaticamente.");
        }
	}

}
