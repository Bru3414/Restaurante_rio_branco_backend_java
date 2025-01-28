package br.com.RestauranteRioBranco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.RestauranteRioBranco.dto.UserDTO;
import br.com.RestauranteRioBranco.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public void create(@RequestBody UserDTO user) {
		userService.createUser(user);
	}
	
	@PutMapping
	public void update(@RequestBody UserDTO user) {
		userService.updateUser(user);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}
	
	@GetMapping
	public List<UserDTO> findAll() {
		return userService.findAllUsers();
	}
	
}
