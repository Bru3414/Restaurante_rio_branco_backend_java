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
	        	products = products.replaceAll("\"\"","\"");
	            System.out.println("Convertendo JSON para lista: " + products);
	            ObjectMapper objectMapper = new ObjectMapper();
	            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            
	            List<ProductQtdEntity> produtos = objectMapper.readValue(products, new TypeReference<List<ProductQtdEntity>>() {});
	            System.out.println("Conversão bem-sucedida: " + produtos);
	            return produtos;
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Erro ao converter JSON para lista de ProductQtd: " + e.getMessage(), e);
	        }
	    }
	
}
