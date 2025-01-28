package br.com.RestauranteRioBranco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin()
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping
	public void create(@RequestBody AddressDTO address) {
		addressService.createAddress(address);
	}
	
	@PutMapping
	public void update(@RequestBody AddressDTO address) {
		addressService.updateAddress(address);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		addressService.deleteAddress(id);
	}
	
	@GetMapping
	public List<AddressDTO> findAll() {
		return addressService.findAllAddress();
	}
}
