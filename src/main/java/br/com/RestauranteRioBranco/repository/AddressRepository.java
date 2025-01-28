package br.com.RestauranteRioBranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
