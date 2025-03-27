package br.com.RestauranteRioBranco.dto.request;

import br.com.RestauranteRioBranco.dto.AddressDTO;

public class CreateOrderRequest {
	
	private AddressDTO address;
	
	private String payment;
	
	private String troco;

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getTroco() {
		return troco;
	}

	public void setTroco(String troco) {
		this.troco = troco;
	}

}
