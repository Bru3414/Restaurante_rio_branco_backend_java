package br.com.RestauranteRioBranco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	Optional<AddressEntity> findByIsSelected(Boolean isSelected);
}
