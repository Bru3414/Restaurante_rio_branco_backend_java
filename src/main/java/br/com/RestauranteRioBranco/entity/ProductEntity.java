package br.com.RestauranteRioBranco.entity;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.RestauranteRioBranco.dto.ProductDTO;
import br.com.RestauranteRioBranco.utils.enums.ECategory;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_PRODUCT")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Boolean isInMenu;
	
	@Column(nullable = false)
	private ECategory category;
	
	@ManyToOne
	@JoinColumn(name = "image_id", nullable = false)
	@JsonBackReference
	private ImageProductEntity image;
	
	public ProductEntity(ProductDTO product) {
		BeanUtils.copyProperties(product, this);
	}
	
	public ProductEntity() {
		
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

	public ImageProductEntity getImage() {
		return image;
	}

	public void setImage(ImageProductEntity image) {
		this.image = image;
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
		ProductEntity other = (ProductEntity) obj;
		return Objects.equals(id, other.id);
	}

}
