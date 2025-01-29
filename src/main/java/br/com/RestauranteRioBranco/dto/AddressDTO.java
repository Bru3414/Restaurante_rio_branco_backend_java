package br.com.RestauranteRioBranco.dto;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;

public class AddressDTO {
	
	private Long id;

	private String address;

	private String number;

	private String bairro;

	private String complement;

	private String city;
	
	private CustomerEntity customer;
	
	public AddressDTO(String address, String number, String bairro, String complement, String city,
			CustomerEntity customer) {
		super();
		this.address = address;
		this.number = number;
		this.bairro = bairro;
		this.complement = complement;
		this.city = city;
		this.customer = customer;
	}

	public AddressDTO(AddressEntity address) {
		BeanUtils.copyProperties(address, this);
	}
	
	public AddressDTO() {
		
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
		AddressDTO other = (AddressDTO) obj;
		return Objects.equals(id, other.id);
	}

}
