package br.com.RestauranteRioBranco.dto.request;

import java.time.LocalDate;

import br.com.RestauranteRioBranco.utils.enums.EOrderStatus;

public class FilterOrderRequest {
	
	private String customerName;
	
	private LocalDate initialDate;
	
	private LocalDate finalDate;
	
	private EOrderStatus status;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}

	public EOrderStatus getStatus() {
		return status;
	}

	public void setStatus(EOrderStatus status) {
		this.status = status;
	}

}
