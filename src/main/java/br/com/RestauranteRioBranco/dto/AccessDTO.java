package br.com.RestauranteRioBranco.dto;

import java.util.List;

public class AccessDTO {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String name;
	private String email;
	private List<String> roles;
	
	public AccessDTO(String token, Long id, String name, String email, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
