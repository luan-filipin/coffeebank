package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;

import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	
	RequisicaoUsuarioDto toDto(Usuario entity);
	
	Usuario toEntity(RequisicaoUsuarioDto dto);

}
