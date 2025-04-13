package br.com.RestauranteRioBranco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.OrderDTO;
import br.com.RestauranteRioBranco.dto.request.CreateOrderRequest;
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
		return orderService.createOrder(token, createOrder.getPayment(), createOrder.getTroco());
	}
}
