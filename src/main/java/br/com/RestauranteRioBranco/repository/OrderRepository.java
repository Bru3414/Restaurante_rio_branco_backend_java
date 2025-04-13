package br.com.RestauranteRioBranco.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.RestauranteRioBranco.entity.OrderEntity;
import br.com.RestauranteRioBranco.utils.enums.EOrderStatus;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	@Query("SELECT COALESCE(MAX(o.nOrder), 0) FROM OrderEntity o WHERE DATE(o.dateTime) = :date")
    Integer findLastDailySequence(@Param("date") LocalDate date);
	
	List<OrderEntity> findByStatus(EOrderStatus status);

}
