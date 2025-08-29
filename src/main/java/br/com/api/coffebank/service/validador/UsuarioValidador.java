package br.com.api.coffebank.service.validador;

import org.springframework.stereotype.Component;

import br.com.api.coffebank.exception.UsuarioExisteException;
import br.com.api.coffebank.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UsuarioValidador {

	private final UsuarioRepository usuarioRepository;
	
	public void validaSeUsuarioExiste(String usuario) {
		if(usuarioRepository.existsByUsuario(usuario)) {
			throw new UsuarioExisteException();
		}
	}
}
