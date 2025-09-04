package br.com.api.coffebank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FormatarMapper {

    @Named("formatarTelefone")
    default String formatarTelefone(String telefone) {
        String numeros = telefone.replaceAll("\\D", "");
        if (numeros.length() == 10) {
            return String.format("(%s) %s-%s",
                    numeros.substring(0, 2),
                    numeros.substring(2, 6),
                    numeros.substring(6, 10));
        } else if (numeros.length() == 11) {
            return String.format("(%s) %s-%s",
                    numeros.substring(0, 2),
                    numeros.substring(2, 7),
                    numeros.substring(7, 11));
        } else {
            return telefone;
        }
    }
}
