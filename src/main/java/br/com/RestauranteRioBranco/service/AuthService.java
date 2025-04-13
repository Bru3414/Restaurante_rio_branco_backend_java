package br.com.RestauranteRioBranco.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.AccessDTO;
import br.com.RestauranteRioBranco.dto.AddressDTO;
import br.com.RestauranteRioBranco.dto.AuthenticationDTO;
import br.com.RestauranteRioBranco.dto.UserDTO;
import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.CartEntity;
import br.com.RestauranteRioBranco.entity.CustomerEntity;
import br.com.RestauranteRioBranco.entity.Role;
import br.com.RestauranteRioBranco.entity.UserEntity;
import br.com.RestauranteRioBranco.repository.AddressRepository;
import br.com.RestauranteRioBranco.repository.CustomerRepository;
import br.com.RestauranteRioBranco.repository.RoleRepository;
import br.com.RestauranteRioBranco.repository.UserRepository;
import br.com.RestauranteRioBranco.security.jwt.JwtUtils;
import br.com.RestauranteRioBranco.utils.enums.ERole;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManeger;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public UserDetailsImpl BuscaUser(AuthenticationDTO authDTO) {
		try {
			//Cria mecanismo de credencial para o Spring
			UsernamePasswordAuthenticationToken userAuth = 
					new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
			
			//Prepara mecamismo para autenticacao
			Authentication authentication = authenticationManeger.authenticate(userAuth);
			
			//Busca usuario logado
			UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
			
			return userAuthenticate;
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Email ou senha inválido");
		}
	}
	

	public AccessDTO login(AuthenticationDTO authDTO) {
		
		UserDetailsImpl userAuthenticate = BuscaUser(authDTO);
			
		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
		
		List<String> roles = userAuthenticate.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		AccessDTO accessDTO = new AccessDTO(token, userAuthenticate.getId(), userAuthenticate.getName(), userAuthenticate.getEmail(), roles);
		
		log.info("Acesso autorizado, email cliente: " + accessDTO.getEmail());
		return accessDTO;
		
	}
	
	@Transactional
	public AccessDTO signup(UserDTO user, AddressDTO address, String phone) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Error: Email já cadastrado");
		}
		
		UserEntity userEntity = new UserEntity(user);
		userEntity.setId(null);
		userEntity.setPassword(encoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setName(ERole.ROLE_CUSTOMER);
		
		if (!roleRepository.existsByName(role.getName())) {
			roleRepository.save(role);
		}
		
		role = roleRepository.findByName(role.getName()).get();
		
		roles.add(role);
		userEntity.setRoles(roles);
		
		CustomerEntity customer = new CustomerEntity();
		
		customer.setId(null);
		customer.setUser(userEntity);
		customer.setPhone(phone);
		AddressEntity addressEntity = new AddressEntity(address);
		addressEntity.setId(null);
		addressEntity.setIsMain(true);
		addressEntity.setIsSelected(true);
		addressEntity.setCustomer(customer);
		
		List<AddressEntity> list = new ArrayList<>();
		list.add(addressEntity);
		customer.setAddress(list);
		CartEntity cart = new CartEntity();
		cart.setCustomer(customer);
		cart.setProducts(null);
		customer.setCart(cart);
		customerRepository.save(customer);
			
		AuthenticationDTO authDTO = new AuthenticationDTO();
		authDTO.setUsername(user.getEmail());
		authDTO.setPassword(user.getPassword());
		
		UserDetailsImpl userAuthenticate = BuscaUser(authDTO);	
		
		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
		
		List<String> rolesToList = userAuthenticate.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		AccessDTO accessDTO = new AccessDTO(token, userAuthenticate.getId(), userAuthenticate.getName(), userAuthenticate.getEmail(), rolesToList);
		
		log.info("Usuário criado com sucesso: " + accessDTO.getEmail());
		return accessDTO;
	}
}
