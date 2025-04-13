package br.com.RestauranteRioBranco.utils.enums;
import java.util.Arrays;

import java.text.Normalizer;

public enum EPayment {
	
	DINHEIRO,
	CARTAO,
	PIX,
	LOCAL;
	
	public static EPayment fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("O valor não pode ser nulo");
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                                      .replaceAll("\\p{M}", "")
                                      .toUpperCase();

        return Arrays.stream(EPayment.values())
                     .filter(e -> e.name().equals(normalized))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Forma de pagamento inválida: " + value));
    }

}
