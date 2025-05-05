package br.com.RestauranteRioBranco.entity.specifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.RestauranteRioBranco.dto.request.FilterOrderRequest;
import br.com.RestauranteRioBranco.entity.OrderEntity;
import jakarta.persistence.criteria.Predicate;

public class OrderSpecifications {
	
	public static Specification<OrderEntity> withFilters(FilterOrderRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCustomerName() != null && !filter.getCustomerName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("customerName")), "%" + filter.getCustomerName().toLowerCase() + "%"));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getInitialDate() != null) {
                LocalDateTime startOfDay = filter.getInitialDate().atStartOfDay();
                LocalDateTime endDateTime;

                if (filter.getFinalDate() != null) {
                    endDateTime = filter.getFinalDate().plusDays(1).atStartOfDay();
                    predicates.add(cb.greaterThanOrEqualTo(root.get("dateTime"), startOfDay));
                    predicates.add(cb.lessThan(root.get("dateTime"), endDateTime));
                } else {
                    LocalDateTime endOfDay = filter.getInitialDate().plusDays(1).atStartOfDay();
                    predicates.add(cb.between(root.get("dateTime"), startOfDay, endOfDay));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
