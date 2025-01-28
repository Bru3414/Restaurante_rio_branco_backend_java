package br.com.RestauranteRioBranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.ImageProductEntity;

public interface ImageProductRepository extends JpaRepository<ImageProductEntity, Long> {
	public ImageProductEntity findByName(String name);
}
