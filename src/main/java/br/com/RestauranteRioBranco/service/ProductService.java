package br.com.RestauranteRioBranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.ImageProductDTO;
import br.com.RestauranteRioBranco.dto.ProductDTO;
import br.com.RestauranteRioBranco.entity.ImageProductEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;
import br.com.RestauranteRioBranco.repository.ImageProductRepository;
import br.com.RestauranteRioBranco.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	
	public ProductDTO createProduct(ProductDTO product) {
		ProductEntity productEntity = new ProductEntity(product);
		productEntity.setId(null);
		
		return new ProductDTO(productRepository.save(productEntity));
	}
	
	public ProductDTO updadeProduct(ProductDTO product) {
		ProductEntity productEntity = new ProductEntity(product);
		return new ProductDTO(productRepository.save(productEntity));
	}
	
	public void deleteProduct(Long id) {
		ProductEntity productEntity = productRepository.findById(id).get();
		productRepository.delete(productEntity);
	}
	
	public List<ProductDTO> findAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		return products.stream().map(ProductDTO::new).toList();
	}
	
	public List<ProductDTO> findAllProductsByIsinMenu() {
		List<ProductEntity> products = productRepository.findAllByIsInMenu(true);
		return products.stream().map(ProductDTO::new).toList();
	}
	
}
