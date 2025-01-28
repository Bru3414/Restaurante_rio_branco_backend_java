package br.com.RestauranteRioBranco.entity;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.dto.AddressDTO;
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
	
	@Column(nullable = false, length = 50)
	private String bairro;
	
	@Column(nullable = false)
	private String complement;
	
	@Column(nullable = false, length = 50)
	private String city;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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
