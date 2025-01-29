package br.com.RestauranteRioBranco.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CartEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;

public class CustomerDTO {
	
	private Long id;
	
	private UserEntity user;
	
	private String phone;
	
	private List<AddressEntity> address;
	
	private CartEntity cart;
	
	public CustomerDTO(CustomerEntity customer) {
		BeanUtils.copyProperties(customer, this);
	}
	
	public CustomerDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<AddressEntity> getAddress() {
		return address;
	}

	public void setAddress(List<AddressEntity> address) {
		this.address = address;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
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
		CustomerDTO other = (CustomerDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
