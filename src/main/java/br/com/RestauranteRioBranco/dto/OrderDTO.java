package br.com.RestauranteRioBranco.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.OrderEntity;
import br.com.RestauranteRioBranco.entity.ProductQtdEntity;
import br.com.RestauranteRioBranco.utils.enums.EOrderStatus;
import br.com.RestauranteRioBranco.utils.enums.EPayment;

public class OrderDTO {
	
	private Long id;
	
	private Integer nOrder;
	
	private LocalDateTime dateTime;
	
	private EOrderStatus status;
	
	private Long customer_id;
	
	private String customer_name;
	
	private List<ProductQtdEntity> productsQtdJson;
	
	private AddressEntity addressJson;
	
	private EPayment payment;
	
	private String troco;
	
	private Double subTotal;
	
	private Double entregaPrice;
	
	private Double TotalPrice;

	public OrderDTO(Integer nOrder, LocalDateTime dateTime, EOrderStatus status, Long customer_id, String customer_name,
			List<ProductQtdEntity> productsQtdJson, AddressEntity addressJson, EPayment payment, String troco, Double subTotal,
			Double entregaPrice, Double totalPrice) {
		super();
		this.nOrder = nOrder;
		this.dateTime = dateTime;
		this.status = status;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.productsQtdJson = productsQtdJson;
		this.addressJson = addressJson;
		this.payment = payment;
		this.troco = troco;
		this.subTotal = subTotal;
		this.entregaPrice = entregaPrice;
		TotalPrice = totalPrice;
	}
	
	public OrderDTO(OrderEntity order) {
		BeanUtils.copyProperties(order, this);
	}
	
	public OrderDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getnOrder() {
		return nOrder;
	}

	public void setnOrder(Integer nOrder) {
		this.nOrder = nOrder;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public EOrderStatus getStatus() {
		return status;
	}

	public void setStatus(EOrderStatus status) {
		this.status = status;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public List<ProductQtdEntity> getProductsQtdJson() {
		return productsQtdJson;
	}

	public void setProductsQtdJson(List<ProductQtdEntity> productsQtdJson) {
		this.productsQtdJson = productsQtdJson;
	}

	public AddressEntity getAddressJson() {
		return addressJson;
	}

	public void setAddressJson(AddressEntity addressJson) {
		this.addressJson = addressJson;
	}

	public EPayment getPayment() {
		return payment;
	}

	public void setPayment(EPayment payment) {
		this.payment = payment;
	}

	public String getTroco() {
		return troco;
	}

	public void setTroco(String troco) {
		this.troco = troco;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getEntregaPrice() {
		return entregaPrice;
	}

	public void setEntregaPrice(Double entregaPrice) {
		this.entregaPrice = entregaPrice;
	}

	public Double getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		TotalPrice = totalPrice;
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
		OrderDTO other = (OrderDTO) obj;
		return Objects.equals(id, other.id);
	}

}
