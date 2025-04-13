package br.com.RestauranteRioBranco.dto.request;


public class CreateOrderRequest {

	private String payment;
	
	private String troco;

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
