package br.com.RestauranteRioBranco.entity;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.utils.enums.EBairro;
import br.com.RestauranteRioBranco.utils.enums.ECity;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_ADDRESS")
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false, length = 6)
	private String number;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private EBairro bairro;
	
	@Column(nullable = false)
	private String complement;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private ECity city;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	@JsonBackReference
	private CustomerEntity customer;
	
	@Column(nullable = false)
	private Boolean isMain = false;
	
	@Column
	private Boolean isSelected = false;
	
	public AddressEntity(AddressDTO address) {
		BeanUtils.copyProperties(address, this);
	}
	
	public AddressEntity() {
		
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
		AddressEntity other = (AddressEntity) obj;
		return Objects.equals(id, other.id);
	}

}
