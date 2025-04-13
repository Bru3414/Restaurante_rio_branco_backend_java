package br.com.RestauranteRioBranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.request.CreateAddressRequest;
import br.com.RestauranteRioBranco.dto.response.AddressSelectedResponse;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.repository.AddressRepository;
import br.com.RestauranteRioBranco.repository.CustomerRepository;
import br.com.RestauranteRioBranco.repository.UserRepository;
import br.com.RestauranteRioBranco.security.jwt.JwtUtils;
import br.com.RestauranteRioBranco.utils.enums.ECity;
import jakarta.transaction.Transactional;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public List<AddressDTO> findAllAddressByCustomer(String token) {
		String jwt = token.replace("Bearer", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		return customer.getAddress().stream().map(AddressDTO::new).toList();
	}
	
	public AddressSelectedResponse updateAddressSelected(String token, Long id) {
		String jwt = token.replace("Bearer", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		AddressSelectedResponse addressSelected = new AddressSelectedResponse();
		
		customer.getAddress().forEach((item) -> item.setIsSelected(false));
		customer.getAddress().forEach((item) -> {
			if (item.getId().equals(id)) {
				item.setIsSelected(true);
				addressSelected.setAddressSelectedResponse(item);
			}
		});
		
		customerRepository.save(customer);
		return addressSelected;
	}
	
	public void createAddress(String token, CreateAddressRequest addressRequest) {
		String jwt = token.replace("Bearer", "");
		String email = jwtUtils.getUsernameToken(jwt);
		CustomerEntity customer = customerRepository.findByUser_Email(email)
				.orElseThrow(() -> new RuntimeException("Error: Usuário não encontrado"));
		
		AddressEntity address = new AddressEntity();
		address.setAddress(addressRequest.getAddress());
		address.setBairro(addressRequest.getBairro());
		address.setComplement(addressRequest.getComplement());
		address.setNumber(addressRequest.getNumber());
		address.setCity(ECity.JARU_RO);
		address.setId(null);
		address.setIsMain(false);
		address.setIsSelected(false);
		address.setCustomer(customer);
		customer.getAddress().add(address);
		
		customerRepository.save(customer);
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
