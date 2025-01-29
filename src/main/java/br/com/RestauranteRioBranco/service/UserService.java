package br.com.RestauranteRioBranco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.UserDTO;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;
import br.com.RestauranteRioBranco.repository.AddressRepository;
import br.com.RestauranteRioBranco.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void createUser(UserDTO user) {
		UserEntity userEntity = new UserEntity(user);
		
		userEntity.setId(null);
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
	}
	
	public void updateUser(UserDTO user) {
		UserEntity userEntity = new UserEntity(user);
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(userEntity);
	}
	
	public void deleteUser(Long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		userRepository.delete(userEntity);
	}
	
	public List<UserDTO> findAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return users.stream().map(UserDTO::new).toList();
	}
	
}
