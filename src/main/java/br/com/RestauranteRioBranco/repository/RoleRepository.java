package br.com.RestauranteRioBranco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.Role;
import br.com.RestauranteRioBranco.utils.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
	
	Boolean existsByName(ERole name);
}
