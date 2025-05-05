package br.com.RestauranteRioBranco.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.dto.OrderDTO;
import br.com.RestauranteRioBranco.utils.AddressJsonConverter;
import br.com.RestauranteRioBranco.utils.ProductQtdJsonConverter;
import br.com.RestauranteRioBranco.utils.enums.EOrderStatus;
import br.com.RestauranteRioBranco.utils.enums.EPayment;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_ORDER")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, updatable = false)
	private Integer nOrder;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime dateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EOrderStatus status;
	
	@Column(nullable = false, updatable = false)
	private Long customer_id;
	
	@Column(nullable = false, updatable = false)
	private String customerName;
	
	@Convert(converter = ProductQtdJsonConverter.class)
	@Basic(fetch = FetchType.EAGER)
	@Column(nullable = false, updatable = false, columnDefinition = "TEXT")
	private List<ProductQtdEntity> productsQtdJson;
	
	@Convert(converter = AddressJsonConverter.class)
	@Column(nullable = true, updatable = false, columnDefinition = "TEXT")
	private AddressEntity addressJson;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false)
	private EPayment payment;
	
	@Column(nullable = true, updatable = false, length = 40)
	private String troco;
	
	@Column(nullable = false, updatable = false)
	private Double subTotal;
	
	@Column(nullable = true, updatable = false)
	private Double entregaPrice;
	
	@Column(nullable = false, updatable = false)
	private Double TotalPrice;

	public OrderEntity(Integer nOrder, LocalDateTime dateTime, EOrderStatus status, Long customer_id, String customer_name,
			List<ProductQtdEntity> productsQtdJson, AddressEntity addressJson, EPayment payment, String troco, Double subTotal,
			Double entregaPrice, Double totalPrice) {
		super();
		this.nOrder = nOrder;
		this.dateTime = dateTime;
		this.status = status;
		this.customer_id = customer_id;
		this.customerName = customer_name;
		this.productsQtdJson = productsQtdJson;
		this.addressJson = addressJson;
		this.payment = payment;
		this.troco = troco;
		this.subTotal = subTotal;
		this.entregaPrice = entregaPrice;
		TotalPrice = totalPrice;
	}
	
	public OrderEntity(OrderDTO order) {
		BeanUtils.copyProperties(order, this);
	}
	
	public OrderEntity() {
		
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
		return customerName;
	}

	public void setCustomer_name(String customer_name) {
		this.customerName = customer_name;
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
		OrderEntity other = (OrderEntity) obj;
		return Objects.equals(id, other.id);
	}
	
}
