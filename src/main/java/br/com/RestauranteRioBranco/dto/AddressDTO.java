package br.com.RestauranteRioBranco.dto;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;
import br.com.RestauranteRioBranco.utils.enums.EBairro;
import br.com.RestauranteRioBranco.utils.enums.ECity;

public class AddressDTO {
	
	private Long id;

	private String address;

	private String number;

	private EBairro bairro;

	private String complement;

	private ECity city;
	
	private CustomerEntity customer;
	
	private Boolean isMain = false;
	
	private Boolean isSelected = false;
	
	public AddressDTO(String address, String number, EBairro bairro, String complement, ECity city,
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

	public EBairro getBairro() {
		return bairro;
	}

	public void setBairro(EBairro bairro) {
		this.bairro = bairro;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public ECity getCity() {
		return city;
	}

	public void setCity(ECity city) {
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

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
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
