package br.com.RestauranteRioBranco.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.CartDTO;
import br.com.RestauranteRioBranco.dto.ProductQtdDTO;
import br.com.RestauranteRioBranco.entity.CartEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;
import br.com.RestauranteRioBranco.repository.CartRepository;
import br.com.RestauranteRioBranco.repository.CustomerRepository;
import br.com.RestauranteRioBranco.repository.ProductQtdRepository;
import br.com.RestauranteRioBranco.repository.ProductRepository;
import br.com.RestauranteRioBranco.security.jwt.JwtUtils;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductQtdRepository productQtdRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public CartDTO includeProduct(String token, ProductQtdDTO product) {
		String jwt = token.replace("Bearer ", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		ProductEntity productEntity = productRepository.findById(product.getProduct().getId())
				.orElseThrow(() -> new RuntimeException("Error: Produto não encontrado"));
		
		List<ProductQtdEntity> listProducts = new ArrayList<>();
		
		
		if (customer.getCart() == null) {
			CartEntity cart = new CartEntity();
			cart.setCustomer(customer);
			product.setQuantity(1);
			product.setId(null);
			product.setPrice(productEntity.getPrice());
			listProducts.add(new ProductQtdEntity(product));
			cart.setProducts(listProducts);
			customer.setCart(cart);
		} else {
			listProducts = customer.getCart().getProducts();
			
			Boolean hasInCart = false;
			for (int i = 0; i < listProducts.size(); i++) {
				 if (listProducts.get(i).equals(new ProductQtdEntity(product))) {
					 customer.getCart().getProducts().get(i).addQtd();
					 if (customer.getCart().getProducts().get(i).getQuantity() >= 70) {
						 throw new RuntimeException("Error: Limite máximo de 70 unidades");
					 }
					 hasInCart = true;
					 break;
				 }
			}
			
			if (!hasInCart) {
				product.setQuantity(1);
				product.setId(null);
				product.setPrice(productEntity.getPrice());
				customer.getCart().getProducts().add(new ProductQtdEntity(product));
			}
	
		}
		
		return new CartDTO(customerRepository.save(customer).getCart());
	}
	
	public CartDTO removeOneProduct(String token, ProductQtdDTO product) {
		String jwt = token.replace("Bearer ", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		List<ProductQtdEntity> listProducts = customer.getCart().getProducts();
		
		Boolean hasInCart = false;
		for (int i = 0; i < listProducts.size(); i++) {
			 if (listProducts.get(i).equals(new ProductQtdEntity(product))) {
				 customer.getCart().getProducts().get(i).diminuiQtd();
				 if (customer.getCart().getProducts().get(i).getQuantity() <= 0) {
					 customer.getCart().getProducts().remove(i);
				 }
				 hasInCart = true;
				 break;
			 }
		}
		
		if (hasInCart) {
			return new CartDTO(customerRepository.save(customer).getCart());
		}
		
		throw new RuntimeException("Error: item não encontrado no carrinho");
	}
	
	public CartDTO getCart(String token) {
		String jwt = token.replace("Bearer ", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		return new CartDTO(customer.getCart());
	}
}
