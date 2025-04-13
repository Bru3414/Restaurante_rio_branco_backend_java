package br.com.RestauranteRioBranco.dto.response;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.utils.enums.EBairro;
import br.com.RestauranteRioBranco.utils.enums.ECity;

public class AddressSelectedResponse {
	
	private String address;
	private String number;
	private String complement;
	private ECity city;
	private EBairro bairro;
	
	public AddressSelectedResponse() {
		
	}
	
	public void setAddressSelectedResponse(AddressEntity address) {
		this.address = address.getAddress();
		this.bairro = address.getBairro();
		this.city = address.getCity();
		this.complement = address.getComplement();
		this.number = address.getNumber();
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
	public EBairro getBairro() {
		return bairro;
	}
	public void setBairro(EBairro bairro) {
		this.bairro = bairro;
	}

}
