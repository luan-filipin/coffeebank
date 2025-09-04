package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.entity.Cliente;

@Mapper(componentModel = "spring", uses = DadosPessoaisMapper.class)
public interface ClienteMapper {

	@Mapping(target = "dadosPessoais", source = "dadosPessoais") 
	RespostaClienteDto toDto(Cliente entity);
	
	@Mapping(target = "codigoCliente", ignore = true)
	@Mapping(target = "dataCriacao", ignore = true)
	Cliente toEntity(RequisicaoClienteDto dto);
	
	@Mapping(target = "codigoCliente", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
	@Mapping(target = "dadosPessoais.cpf", ignore = true)
	void atualizaDto(RequisicaoClienteDto dto, @MappingTarget Cliente entity);
}
