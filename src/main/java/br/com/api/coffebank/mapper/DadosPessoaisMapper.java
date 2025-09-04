package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.coffebank.dto.resposta.RespostaClienteDadosPessoaisDto;
import br.com.api.coffebank.entity.DadosPessoais;

@Mapper(componentModel = "spring", uses = FormatarMapper.class)
public interface DadosPessoaisMapper {

    @Mapping(target = "telefone", source = "telefone", qualifiedByName = "formatarTelefone")
    RespostaClienteDadosPessoaisDto toDto(DadosPessoais entity);
}
