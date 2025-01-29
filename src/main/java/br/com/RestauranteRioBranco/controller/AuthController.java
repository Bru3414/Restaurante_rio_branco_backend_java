package br.com.RestauranteRioBranco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.AuthenticationDTO;
import br.com.RestauranteRioBranco.dto.UserDTO;
import br.com.RestauranteRioBranco.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDTO) {
		return ResponseEntity.ok(authService.login(authDTO));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserDTO user) {
		return ResponseEntity.ok(authService.signup(user));
	}
}
