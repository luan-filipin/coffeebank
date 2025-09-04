package br.com.api.coffebank.dto.resposta;

import br.com.api.coffebank.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de resposta retornado após a criação de um usuário")
public record RespotaUsuarioCriadoDto(

    @Schema(description = "Nome de usuário criado", example = "Silva")
    String username,
    
    @Schema(description = "Papel do usuário no sistema", example = "ADMIN")
    UserRole role

) {}
