package br.com.RestauranteRioBranco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.request.CreateAddressRequest;
import br.com.RestauranteRioBranco.dto.response.AddressSelectedResponse;
import br.com.RestauranteRioBranco.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins =  {"http://localhost:3000/", "http://localhost:3001"})
@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@GetMapping()
	public List<AddressDTO> findAllAddressByCustomer(@RequestHeader("Authorization") String token) {
		return addressService.findAllAddressByCustomer(token);
	}
	
	@PutMapping("/set-selected/{id}")
	public AddressSelectedResponse updateAddressSelected(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
		return addressService.updateAddressSelected(token, id);
	}
	
	@PostMapping("/create-new-address")
	public void create(@RequestHeader("Authorization") String token, @RequestBody CreateAddressRequest address) {
		addressService.createAddress(token, address);
	}
	
	@PutMapping
	public void update(@RequestBody AddressDTO address) {
		addressService.updateAddress(address);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		addressService.deleteAddress(id);
	}
	
	@GetMapping("/findAll")
	public List<AddressDTO> findAll() {
		return addressService.findAllAddress();
	}
}
