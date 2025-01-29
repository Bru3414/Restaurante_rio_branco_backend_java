package br.com.RestauranteRioBranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
