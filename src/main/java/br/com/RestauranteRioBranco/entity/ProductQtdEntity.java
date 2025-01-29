package br.com.RestauranteRioBranco.entity;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.RestauranteRioBranco.dto.ProductQtdDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_PRODUCT_QTD")
public class ProductQtdEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;
	
	@Column(nullable = false, length = 2)
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonBackReference
	private CartEntity cart;
	
	public ProductQtdEntity (ProductQtdDTO product) {
		BeanUtils.copyProperties(product, this);
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
		ProductQtdEntity other = (ProductQtdEntity) obj;
		return Objects.equals(id, other.id);
	}
	
}
