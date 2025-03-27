package br.com.RestauranteRioBranco.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	private Double subTotal;
	
	@Transactional
	public OrderDTO createOrder(String token, AddressDTO address, String payment, String troco) {
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
		
		AddressEntity addressEntity = new AddressEntity(address);
		
		
		OrderDTO orderDTO = new OrderDTO(nOrder, dateTime, EOrderStatus.AGUARDANDO_APROVACAO,
				customer.getId(), customer.getUser().getName(),
				listProductsQtd, addressEntity,
				tipoPayment, troco, subTotal, 7.0, subTotal+7);
		
		List<ProductQtdEntity> productsCopy = orderDTO.getProductsQtdJson()
			    .stream()
			    .map(original -> original) // Criar novos objetos (precisa de um construtor de cópia)
			    .collect(Collectors.toList());
		
		orderDTO.setProductsQtdJson(productsCopy);
		OrderEntity orderEntity = orderRepository.save(new OrderEntity(orderDTO));
		
		customer.getCart().getProducts().removeIf(item -> {
			item.setCart(null);
			productQtdRepository.delete(item);
			return true;
			});
		customerRepository.save(customer);
		//System.out.println("Produtos carregados: " + proJsonConvert.convertToEntityAttribute("\"{\"\"{\\\"\"id\\\"\":57,\\\"\"product\\\"\":{\\\"\"id\\\"\":1,\\\"\"name\\\"\":\\\"\"marmita\\\"\",\\\"\"description\\\"\":\\\"\"arroz, feijão, macarrão, farofa, maionese, 2 tipos de carne (a sua escolha)\\\"\",\\\"\"price\\\"\":20.0,\\\"\"isInMenu\\\"\":true,\\\"\"category\\\"\":\\\"\"MARMITA\\\"\",\\\"\"image\\\"\":{\\\"\"id\\\"\":4,\\\"\"name\\\"\":\\\"\"marmita.png\\\"\",\\\"\"url\\\"\":\\\"\"https://resriobranco-images.s3.sa-east-1.amazonaws.com/marmita.png\\\"\"}},\\\"\"quantity\\\"\":1,\\\"\"obs\\\"\":\\\"\"\\\"\",\\\"\"price\\\"\":20.0}\"\",\"\"{\\\"\"id\\\"\":58,\\\"\"product\\\"\":{\\\"\"id\\\"\":5,\\\"\"name\\\"\":\\\"\"fanta uva lata\\\"\",\\\"\"description\\\"\":\\\"\"fanta uva lata\\\"\",\\\"\"price\\\"\":6.0,\\\"\"isInMenu\\\"\":true,\\\"\"category\\\"\":\\\"\"BEBIDA\\\"\",\\\"\"image\\\"\":{\\\"\"id\\\"\":2,\\\"\"name\\\"\":\\\"\"fanta_uva_lata.png\\\"\",\\\"\"url\\\"\":\\\"\"https://resriobranco-images.s3.sa-east-1.amazonaws.com/fanta_uva_lata.png\\\"\"}},\\\"\"quantity\\\"\":2,\\\"\"obs\\\"\":\\\"\"\\\"\",\\\"\"price\\\"\":12.0}\"\"}\""));
		
		System.out.println("Produtos orderEntity: " + orderDTO.getProductsQtdJson());
		return new OrderDTO(orderRepository.findById(orderEntity.getId()).get());
	}

}
