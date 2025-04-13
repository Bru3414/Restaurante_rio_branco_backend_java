package br.com.RestauranteRioBranco.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.RestauranteRioBranco.entity.ProductQtdEntity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
@Component
public class ProductQtdJsonConverter implements AttributeConverter<List<ProductQtdEntity>, String> {
	    
	    private final ObjectMapper objectMapper = new ObjectMapper();

	    @Override
	    public String convertToDatabaseColumn(List<ProductQtdEntity> products) {
	        try {
	        	return objectMapper.writeValueAsString(products);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Erro ao converter ProductQtd para JSON", e);
	        }
	    }

	    @Override
	    public List<ProductQtdEntity> convertToEntityAttribute(String products) {
	    	if (products == null || products.isBlank()) {
	    		System.out.println("productsJson vazio");
	            return new ArrayList<>(); // Retorna lista vazia se JSON for null
	        }

	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            
	            List<ProductQtdEntity> produtos = objectMapper.readValue(products, new TypeReference<List<ProductQtdEntity>>() {});
	            return produtos;
	        } catch (JsonProcessingException e) {
	        	log.error("Erro ao converter JSON para lista de ProductQtd: " + e.getMessage(), e);
	            throw new RuntimeException("Erro ao converter JSON para lista de ProductQtd: " + e.getMessage(), e);
	        }
	    }
	
}
