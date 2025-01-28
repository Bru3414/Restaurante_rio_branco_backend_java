package br.com.RestauranteRioBranco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	public List<ProductEntity> findAllByIsInMenu(Boolean isinmenu);
}
