package br.com.RestauranteRioBranco.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.RestauranteRioBranco.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin(origins =  {"http://localhost:3000", "http://localhost:3001"})
public class OrderWebSocketController {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	public OrderWebSocketController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void notifyNewOrder(OrderDTO order) {
		messagingTemplate.convertAndSend("/topic/new-order", "Novo pedido, nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name());
		log.info("Novo pedido, nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name());
	}
	
	public void notifyCancelOrder(OrderDTO order) {
		messagingTemplate.convertAndSend("/topic/new-order", "Pedido cancelado, nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name());
		log.info("Pedido cancelado, nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name());
	}
	
	public void notifyHandleOrderStatus(OrderDTO order) {
		messagingTemplate.convertAndSend("/topic/new-order", "Status do pedido nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name()
				+ " alterado para: " + order.getStatus());
		log.info("Status do pedido nº: " + order.getnOrder() + ", do cliente: " + order.getCustomer_name()
		+ " alterado para: " + order.getStatus());
	}
}
