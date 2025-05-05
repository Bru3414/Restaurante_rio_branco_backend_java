package br.com.RestauranteRioBranco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.OrderDTO;
import br.com.RestauranteRioBranco.dto.request.CreateOrderRequest;
import br.com.RestauranteRioBranco.dto.request.FilterOrderRequest;
import br.com.RestauranteRioBranco.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins =  {"http://localhost:3000/", "http://localhost:3001"})
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/new-order")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public OrderDTO createOrder(@RequestHeader("Authorization") String token, @RequestBody CreateOrderRequest createOrder) {
		return orderService.createOrder(token, createOrder.getTypeDelivery(), createOrder.getPayment(), createOrder.getTroco());
	}
	
	@GetMapping("/get-orders-for-orders-panel")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<OrderDTO> getOrdersForOrdersPanel() {
		return orderService.getOrdersForOrdersPanel();
	}
	
	@PostMapping("/get-orders-filter")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<OrderDTO> getOrdersByFilter(@RequestBody FilterOrderRequest filter) {
		return orderService.getOrdersByFilter(filter);
	}
	
	@PutMapping("/handle-order-status")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Void> handleOrderStatus(@RequestBody OrderDTO order) {
		orderService.handleOrderStatus(order);
		return ResponseEntity.ok().build();
	}
}
