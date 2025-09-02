package br.com.api.coffebank.dto.event;

import java.math.BigDecimal;
import java.time.Instant;

public record ContaEventDto(

		Long codigoCliente,
		BigDecimal valor,
		Instant timestamp) {

}
