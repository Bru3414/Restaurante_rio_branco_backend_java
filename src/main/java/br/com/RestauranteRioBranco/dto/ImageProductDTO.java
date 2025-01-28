package br.com.RestauranteRioBranco.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.ImageProductEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;

public class ImageProductDTO {
	
	private Long id;

	private String name;

	private String url;

	private List<ProductEntity> products;
	
	public ImageProductDTO(ImageProductEntity imageProduct) {
		BeanUtils.copyProperties(imageProduct, this);
	}
	
	public ImageProductDTO() {
		
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
		ImageProductDTO other = (ImageProductDTO) obj;
		return Objects.equals(id, other.id);
	}
}
