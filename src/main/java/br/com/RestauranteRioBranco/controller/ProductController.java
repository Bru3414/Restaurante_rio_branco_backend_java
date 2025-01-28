package br.com.RestauranteRioBranco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.ImageProductDTO;
import br.com.RestauranteRioBranco.dto.ProductDTO;
import br.com.RestauranteRioBranco.entity.ImageProductEntity;
import br.com.RestauranteRioBranco.entity.ProductEntity;
import br.com.RestauranteRioBranco.repository.ImageProductRepository;
import br.com.RestauranteRioBranco.service.ImageProductService;
import br.com.RestauranteRioBranco.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins =  {"http://localhost:3000/", "http://localhost:3001"})
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ImageProductService imageProductService;
	
	@Autowired
	private ImageProductRepository imageProductRepository;
	
	@PostMapping
	public ProductDTO create(@RequestBody ProductDTO product) {
		ImageProductEntity imageDefault = imageProductRepository.findByName("item_no_image.png");
		if (imageDefault == null) {
			imageDefault = new ImageProductEntity();
			imageDefault.setName("item_no_image.png");
			imageDefault.setUrl("https://resriobranco-images.s3.sa-east-1.amazonaws.com/item_no_image/item_no_image.png");
			imageDefault = new ImageProductEntity(imageProductService.uploadImage(new ImageProductDTO(imageDefault)));
		}
		
		if (product.getImage().getId() == -1) {
			product.setImage(imageDefault);
		}
		return productService.createProduct(product);
	}
	
	@PutMapping
	public ProductDTO updade(@RequestBody ProductDTO product) {
		ImageProductEntity imageDefault = imageProductRepository.findByName("item_no_image.png");
		
		if (product.getImage().getId() == -1) {
			product.setImage(imageDefault);
		}
		
		return productService.updadeProduct(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<ProductDTO> findAll() {
		return productService.findAllProducts();
	}
	
	@GetMapping("/isinmenu")
	public List<ProductDTO> findAllByIsInMenu() {
		return productService.findAllProductsByIsinMenu();
	}
	
}
