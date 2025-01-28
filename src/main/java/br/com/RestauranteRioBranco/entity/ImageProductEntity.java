package br.com.RestauranteRioBranco.entity;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.RestauranteRioBranco.dto.ImageProductDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_IMAGE_PRODUCT")
public class ImageProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String url;
	
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ProductEntity> products;
	
	public ImageProductEntity(ImageProductDTO imageProduct) {
		BeanUtils.copyProperties(imageProduct, this);
	}
	
	public ImageProductEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
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
		ImageProductEntity other = (ImageProductEntity) obj;
		return Objects.equals(id, other.id);
	}
	
}
