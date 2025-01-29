package br.com.RestauranteRioBranco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.RestauranteRioBranco.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
