package br.com.RestauranteRioBranco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.CartDTO;
import br.com.RestauranteRioBranco.dto.ProductQtdDTO;
import br.com.RestauranteRioBranco.dto.request.ProductQtdRequest;
import br.com.RestauranteRioBranco.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins =  {"http://localhost:3000/", "http://localhost:3001"})
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public CartDTO getCart(@RequestHeader("Authorization") String token) {
		return cartService.getCart(token);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public CartDTO includeProduct(@RequestHeader("Authorization") String token, @RequestBody ProductQtdRequest product) {
		return cartService.includeProduct(token, product);
	}
	
	@PostMapping("/remove")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public CartDTO removeOneProduct(@RequestHeader("Authorization") String token, @RequestBody ProductQtdDTO product) {
		return cartService.removeOneProduct(token, product);
	}
}