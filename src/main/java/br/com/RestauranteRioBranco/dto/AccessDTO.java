package br.com.RestauranteRioBranco.dto;


public class AccessDTO {
	
	private String token;
	
	public AccessDTO(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
