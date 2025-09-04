package br.com.api.coffebank.dto.resposta;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de retorno do token gerado após autenticação")
public record RespostaTokenDto(
    
    @Schema(description = "Token JWT gerado para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token

) {}
