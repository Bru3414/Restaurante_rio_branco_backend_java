package br.com.RestauranteRioBranco.dto.response;

import java.util.List;

import br.com.RestauranteRioBranco.dto.OrderDTO;

public class FilterOrderResponse {
	
	private List<OrderDTO> orders;
	
	private Integer totalPages;
	
	private Integer currentPage;
	
	public FilterOrderResponse(List<OrderDTO> orders, Integer totalPages, Integer currentPage) {
		this.orders = orders;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
}
