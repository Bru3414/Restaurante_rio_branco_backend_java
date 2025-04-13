package br.com.RestauranteRioBranco.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.RestauranteRioBranco.dto.OrderDTO;

@Controller
@CrossOrigin(origins =  {"http://localhost:3000", "http://localhost:3001"})
public class OrderWebSocketController {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	public OrderWebSocketController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void notifyNewOrder(OrderDTO order) {
		messagingTemplate.convertAndSend("/topic/new-order", order);
		System.out.println("Novo pedido, nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name());
	}
}
