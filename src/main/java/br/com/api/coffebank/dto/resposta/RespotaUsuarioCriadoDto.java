package br.com.api.coffebank.dto.resposta;

import br.com.api.coffebank.entity.enums.UserRole;

public record RespotaUsuarioCriadoDto (String username, UserRole role){

}
