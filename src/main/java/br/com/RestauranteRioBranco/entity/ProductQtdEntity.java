package br.com.RestauranteRioBranco.entity;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(nullable = true, length = 100)
	private String obs;
	
	@Column(nullable = false)
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonIgnore
	private CartEntity cart;
	
	public ProductQtdEntity (ProductQtdDTO product) {
		BeanUtils.copyProperties(product, this);
	}
	
	public ProductQtdEntity() {
		
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
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
	
	public void addQtd() {
		setQuantity(quantity + 1);
		setPrice(product.getPrice() * quantity);
	}
	
	public void diminuiQtd() {
		setQuantity(quantity - 1);
		setPrice(product.getPrice() * quantity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, obs, product);
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
		return Objects.equals(cart, other.cart) && Objects.equals(obs, other.obs)
				&& Objects.equals(product, other.product);
	}
	
}
