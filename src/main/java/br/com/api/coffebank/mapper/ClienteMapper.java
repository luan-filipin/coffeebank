package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;

import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	RespostaClienteDto toDto(Cliente entity);
	
	Cliente toEntity(CriarClienteDto dto);
}
