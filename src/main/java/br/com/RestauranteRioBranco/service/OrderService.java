package br.com.RestauranteRioBranco.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.controller.OrderWebSocketController;
import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.OrderDTO;
import br.com.RestauranteRioBranco.dto.request.FilterOrderRequest;
import br.com.RestauranteRioBranco.dto.response.FilterOrderResponse;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.OrderEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;
import br.com.RestauranteRioBranco.entity.specifications.OrderSpecifications;
import br.com.RestauranteRioBranco.repository.CustomerRepository;
import br.com.RestauranteRioBranco.repository.OrderRepository;
import br.com.RestauranteRioBranco.repository.ProductQtdRepository;
import br.com.RestauranteRioBranco.repository.UserRepository;
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
	private UserRepository userRepository;
	
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
	public OrderDTO createOrder(String token, String typeDelivery, String payment, String troco) {
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
		
		AddressEntity addressEntity = null;
		Double frete = 0.0;
		if (typeDelivery.equals("DELIVERY")) {
			addressEntity = customer.getAddress().stream().filter((item) -> item.getIsSelected().equals(true)).toList().get(0);	
			frete = 7.0;
		}
		
		
		OrderDTO orderDTO = new OrderDTO(nOrder, dateTime, EOrderStatus.AGUARDANDO_APROVACAO,
				customer.getId(), customer.getUser().getName(),
				listProductsQtd, addressEntity,
				tipoPayment, troco, subTotal, frete, subTotal+frete);
		
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
	
	public List<OrderDTO> getOrdersForOrdersPanel() {
		List<OrderEntity> ordersForOrdersPanel = orderRepository.findOrdersForOrdersPanel(EOrderStatus.CANCELADO);
		
		return ordersForOrdersPanel.stream().map(OrderDTO::new).toList();
	}
	
	public void handleOrderStatus(OrderDTO order) {
		OrderEntity orderEntity = orderRepository.findById(order.getId())
				.orElseThrow(() -> new RuntimeException("Error: Pedido não encontrado"));
		
		switch (orderEntity.getStatus()) {
			case AGUARDANDO_APROVACAO: {
				orderEntity.setStatus(EOrderStatus.PRODUCAO);
				break;
			}
			case PRODUCAO: {
				orderEntity.setStatus(EOrderStatus.PRONTO);
				break;
			}
			case PRONTO: {
				orderEntity.setStatus(EOrderStatus.FINALIZADO);
				break;
			}
			default: {
				new RuntimeException("Error: Pedido finalizado ou cancelado");
			}
		}
		
		orderWebSocketController.notifyHandleOrderStatus(new OrderDTO(orderRepository.save(orderEntity)));
		
	}
	
	public FilterOrderResponse getOrdersByFilter(FilterOrderRequest filter) {
		log.info("Iniciando busca de pedidos com base no filtro...");
	
		Integer totalPages = (int) Math.ceil(orderRepository.findAll(OrderSpecifications.withFilters(filter)).size() / 10.0);
		Pageable pageable = PageRequest.of(filter.getnPage() - 1, 10, Sort.by("id").descending());
		Page<OrderEntity> pagedOrders = orderRepository.findAll(OrderSpecifications.withFilters(filter), pageable);
		
		FilterOrderResponse ordersResponse = new FilterOrderResponse(
				pagedOrders.stream().map(OrderDTO::new).toList(),
				totalPages,
				filter.getnPage());
		return ordersResponse;
	    
	}
	
	@Scheduled(fixedRate = 60000)
	public void CancelOrdersNotApproved() {
		log.info("Verificando pedidos não aprovados para cancelamento automático...");
		LocalDateTime limit = LocalDateTime.now().minusMinutes(15);
		
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
            orderWebSocketController.notifyCancelOrder(new OrderDTO(pedido));
        }
	}

}
