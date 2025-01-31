package br.com.RestauranteRioBranco.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.RestauranteRioBranco.dto.ImageProductDTO;
import br.com.RestauranteRioBranco.service.ImageProductService;
import br.com.RestauranteRioBranco.service.S3Service;

@RestController
@RequestMapping("/products/images")
@CrossOrigin(origins =  {"http://localhost:3000/", "http://localhost:3001"})
public class S3Controller {
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageProductService imageProductService;
	
	@PostMapping
	public ImageProductDTO uploadImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name) throws Exception {
		if (!multipartFile.getContentType().startsWith("image/")) {
			throw new Exception("O arquivo não é uma imagem");
		}
		
		File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
		
		StringBuilder url = new StringBuilder("https://resriobranco-images.s3.sa-east-1.amazonaws.com/");
		url.append(name);
		
		List<String> listImages = s3Service.findAllImages();
		
		ImageProductDTO imageDTO = new ImageProductDTO();
		imageDTO.setId(null);
		imageDTO.setName(name);
		imageDTO.setUrl(url.toString());
		
		s3Service.uploadImage(file, name);
		file.delete();
		
		if (listImages.contains(name)) {
			return imageProductService.updateImage(imageDTO);
		}
		
		return imageProductService.uploadImage(imageDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
		imageProductService.deleteImage(id);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<ImageProductDTO> findAllImages() {
		return imageProductService.findAllImages();
	}
}
