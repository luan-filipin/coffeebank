package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.dto.resposta.RespotaUsuarioCriadoDto;
import br.com.api.coffebank.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	
	RequisicaoUsuarioDto toDto(Usuario entity);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	Usuario toEntity(RequisicaoUsuarioDto dto);

	RespotaUsuarioCriadoDto toCriarDto(Usuario entity);
}
