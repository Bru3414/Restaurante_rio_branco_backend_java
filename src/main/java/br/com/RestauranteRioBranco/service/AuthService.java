package br.com.RestauranteRioBranco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.RestauranteRioBranco.dto.AccessDTO;
import br.com.RestauranteRioBranco.dto.AuthenticationDTO;
import br.com.RestauranteRioBranco.security.jwt.JwtUtils;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManeger;
	
	@Autowired
	private JwtUtils jwtUtils;

	public AccessDTO login(AuthenticationDTO authDTO) {
		
		try {
			//Cria mecanismo de credencial para o Sptring
			UsernamePasswordAuthenticationToken userAuth = 
					new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
			
			//Prepara mecamismo para autenticacao
			Authentication authentication = authenticationManeger.authenticate(userAuth);
			
			//Busca usuario logado
			UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
			
			String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
			
			AccessDTO accessDTO = new AccessDTO(token);
			
			return accessDTO;
			
		}catch(BadCredentialsException e) {
			//TODO LOGIN OU SENHA INVALIDO
		}
		return null;
		
	}
}
