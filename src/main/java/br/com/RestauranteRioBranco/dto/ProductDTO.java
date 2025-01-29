package br.com.RestauranteRioBranco.dto;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import br.com.RestauranteRioBranco.entity.ImageProductEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;
import br.com.RestauranteRioBranco.utils.enums.ECategory;

public class ProductDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private ImageProductEntity image;
	
	private Boolean isInMenu;
	
	private ECategory category;
	
	public ProductDTO(ProductEntity product) {
		BeanUtils.copyProperties(product, this);
	}
	
	public ProductDTO() {
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ImageProductEntity getImage() {
		return image;
	}

	public void setImage(ImageProductEntity image) {
		this.image = image;
	}

	public Boolean getIsInMenu() {
		return isInMenu;
	}

	public void setIsInMenu(Boolean isInMenu) {
		this.isInMenu = isInMenu;
	}

	public ECategory getCategory() {
		return category;
	}

	public void setCategory(ECategory category) {
		this.category = category;
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
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
