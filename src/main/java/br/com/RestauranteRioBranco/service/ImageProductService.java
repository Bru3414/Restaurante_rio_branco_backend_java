package br.com.RestauranteRioBranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.ImageProductDTO;
import br.com.RestauranteRioBranco.dto.ProductDTO;
import br.com.RestauranteRioBranco.entity.ImageProductEntity;
import br.com.RestauranteRioBranco.repository.ImageProductRepository;

@Service
public class ImageProductService {
	
	@Autowired
	private ImageProductRepository imageProductRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ProductService productService;
	
	public ImageProductDTO uploadImage(ImageProductDTO image) {
		ImageProductEntity imageEntity = new ImageProductEntity(image);
		
		return new ImageProductDTO(imageProductRepository.save(imageEntity));
	}
	
	public ImageProductDTO updateImage(ImageProductDTO image) {
		ImageProductEntity imageEntity = imageProductRepository.findByName(image.getName());
		imageEntity.setUrl(image.getUrl());
		
		return new ImageProductDTO(imageProductRepository.save(imageEntity));
	}
	
	public void deleteImage(Long id) {
		ImageProductEntity imageEntity = imageProductRepository.findById(id).get();
		ImageProductEntity imageDefault = imageProductRepository.findByName("item_no_image.png");
		
		s3Service.deleteImage(imageEntity.getName());
		
		imageEntity.getProducts().forEach(item -> {
			item.setImage(imageDefault);
			productService.updadeProduct(new ProductDTO(item));
			});
		
		imageProductRepository.delete(imageEntity);
	}
	
	public List<ImageProductDTO> findAllImages() {
		List<ImageProductEntity> images = imageProductRepository.findAll();
		return images.stream().map(ImageProductDTO::new).toList();
	}
	
}
