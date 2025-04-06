package br.com.RestauranteRioBranco.dto.request;

import br.com.RestauranteRioBranco.utils.enums.EBairro;

public class CreateAddressRequest {
	
	private String address;
	
	private String number;
	
	private EBairro bairro;
	
	private String complement;

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

}
