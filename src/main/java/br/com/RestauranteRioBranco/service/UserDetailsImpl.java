package br.com.RestauranteRioBranco.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import br.com.RestauranteRioBranco.entity.UserEntity;

public class UserDetailsImpl implements UserDetails {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private String password;
	
	private List<AddressEntity> address;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String name, String email, String phone, String password,
			List<AddressEntity> address, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(UserEntity user) {
		
		return new UserDetailsImpl(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPassword(), user.getAddress(), new ArrayList<>());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
