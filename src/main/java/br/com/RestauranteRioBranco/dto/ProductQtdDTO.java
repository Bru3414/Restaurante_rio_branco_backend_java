package br.com.RestauranteRioBranco.dto;

import java.util.Objects;

import br.com.RestauranteRioBranco.entity.ProductEntity;

public class ProductQtdDTO {

	private ProductEntity product;
	
	private Integer quantity;

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
