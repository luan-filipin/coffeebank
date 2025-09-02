package br.com.api.coffebank.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.dto.resposta.RespotaUsuarioCriadoDto;
import br.com.api.coffebank.entity.Usuario;
import br.com.api.coffebank.mapper.UsuarioMapper;
import br.com.api.coffebank.repository.UsuarioRepository;
import br.com.api.coffebank.service.validador.UsuarioValidador;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioValidador usuarioValidador;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(usuarioRepository.findByUsername(username))
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
	}
	
	@Transactional
	public RespotaUsuarioCriadoDto criarUsuario(RequisicaoUsuarioDto usuarioDto) {
		usuarioValidador.validaSeUsuarioExiste(usuarioDto.username());
		Usuario usuario = usuarioMapper.toEntity(usuarioDto);
		usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return usuarioMapper.toCriarDto(usuarioSalvo);
	}
}