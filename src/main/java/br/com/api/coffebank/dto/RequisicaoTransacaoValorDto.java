package br.com.api.coffebank.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequisicaoTransacaoValorDto (
		@NotNull(message = "O campo codigoCliente é obrigatorio!") Long codigoCliente,
		@NotNull(message = "O campo valor é obrigatorio!") @Positive(message = "O valor do deposito deve ser positivo") BigDecimal valor) {

}
