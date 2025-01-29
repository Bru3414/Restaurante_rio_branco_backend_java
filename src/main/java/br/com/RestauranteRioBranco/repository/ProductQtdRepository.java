package br.com.RestauranteRioBranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.ProductQtdEntity;

public interface ProductQtdRepository extends JpaRepository<ProductQtdEntity, Long> {

}
