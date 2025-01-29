package br.com.RestauranteRioBranco.entity;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.RestauranteRioBranco.dto.CartDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_CART")
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private CustomerEntity customer;
	
	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<ProductQtdEntity> products;
	
	public CartEntity(CartDTO cart) {
		BeanUtils.copyProperties(cart, this);
	}
	
	public CartEntity() {
		
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
		CartEntity other = (CartEntity) obj;
		return Objects.equals(id, other.id);
	}
	
}
