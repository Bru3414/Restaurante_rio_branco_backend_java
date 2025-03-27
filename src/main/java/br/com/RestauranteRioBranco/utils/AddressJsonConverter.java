package br.com.RestauranteRioBranco.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.RestauranteRioBranco.entity.AddressEntity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
@Component
public class AddressJsonConverter implements AttributeConverter<AddressEntity, String> {
		    
		    private final ObjectMapper objectMapper = new ObjectMapper();

		    @Override
		    public String convertToDatabaseColumn(AddressEntity address) {
		        try {
		            return objectMapper.writeValueAsString(address);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException("Erro ao converter ProductQtd para JSON", e);
		        }
		    }

		    @Override
		    public AddressEntity convertToEntityAttribute(String address) {
		        try {
		            return objectMapper.readValue(address, AddressEntity.class);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException("Erro ao converter JSON para ProductQtd", e);
		        }
		    }


}
