package br.com.RestauranteRioBranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public void createAddress(AddressDTO address) {
		AddressEntity addressEntity = new AddressEntity(address);
		addressEntity.setId(null);
		addressRepository.save(addressEntity);
	}
	
	public void updateAddress(AddressDTO address) {
		AddressEntity addressEntity = new AddressEntity(address);
		addressRepository.save(addressEntity);
	}
	
	public void deleteAddress(Long id) {
		AddressEntity addressEntity = addressRepository.findById(id).get();
		addressRepository.delete(addressEntity);
	}
	
	public List<AddressDTO> findAllAddress() {
		List<AddressEntity> address = addressRepository.findAll();
		return address.stream().map(AddressDTO::new).toList();
	}
	
}
