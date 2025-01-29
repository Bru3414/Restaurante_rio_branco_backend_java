package br.com.RestauranteRioBranco.dto;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.CartEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;

public class ProductQtdDTO {
	
	private Long id;

	private ProductEntity product;
	
	private Integer quantity;
	
	private CartEntity cart;
	
	public ProductQtdDTO(ProductQtdEntity product) {
		BeanUtils.copyProperties(product, this);
	}
	
	public ProductQtdDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductQtdDTO other = (ProductQtdDTO) obj;
		return Objects.equals(product, other.product);
	}
	
}
