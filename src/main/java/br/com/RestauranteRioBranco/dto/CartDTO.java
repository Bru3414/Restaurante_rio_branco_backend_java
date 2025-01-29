package br.com.RestauranteRioBranco.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.CartEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;

public class CartDTO {
	
	private Long id;
	
	private CustomerEntity customer;

	private List<ProductQtdEntity> products;
	
	public CartDTO(CartEntity cart) {
		BeanUtils.copyProperties(cart, this);
	}
	
	public CartDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public List<ProductQtdEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductQtdEntity> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartDTO other = (CartDTO) obj;
		return Objects.equals(id, other.id);
	}
}
