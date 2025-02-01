package br.com.RestauranteRioBranco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.ProductQtdEntity;

public interface ProductQtdRepository extends JpaRepository<ProductQtdEntity, Long> {
	Optional<List<ProductQtdEntity>> findAllByProduct_Id(Long id);
}
