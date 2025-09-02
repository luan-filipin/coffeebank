package br.com.api.coffebank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.dto.resposta.RespotaUsuarioCriadoDto;
import br.com.api.coffebank.entity.Usuario;
import br.com.api.coffebank.entity.enums.UserRole;
import br.com.api.coffebank.exception.UsuarioExisteException;
import br.com.api.coffebank.mapper.UsuarioMapper;
import br.com.api.coffebank.repository.UsuarioRepository;
import br.com.api.coffebank.service.validador.UsuarioValidador;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private UsuarioValidador usuarioValidador;

	@Mock
	private UsuarioMapper usuarioMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UsuarioService usuarioService;

	@DisplayName("Deve retornar o usuario se existir")
	@Test
	void deveBuscarUsuarioSeExistir() {
		
		Usuario usuario = new Usuario();
		usuario.setUsername("Joao Teste");
		
		when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(usuario);
		
		UserDetails resultado = usuarioService.loadUserByUsername(usuario.getUsername());
		
		assertEquals("Joao Teste", resultado.getUsername());
	}
	
	@DisplayName("Deve lançar exception se o username nao existir")
	@Test
	void deveLancarExceptionSeUsernameNaoExistir() {
		
		when(usuarioRepository.findByUsername("Joao Teste")).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class, ()->{
			usuarioService.loadUserByUsername("Joao Teste");
		});
	}
	
	@DisplayName("POST -  Deve criar um usaurio com sucesso.")
	@Test
	void deveCriarUsuarioComSucesso() {

		RequisicaoUsuarioDto entradaDto = new RequisicaoUsuarioDto("Joao Teste", "1234", UserRole.ADMIN);

		RespotaUsuarioCriadoDto dtoEsperado = new RespotaUsuarioCriadoDto("Joao Teste", UserRole.ADMIN);
		
		Usuario entidade = new Usuario();
		entidade.setId(1L);
		entidade.setUsername("Joao Test");
		entidade.setSenha("1234");
		entidade.setRole(UserRole.ADMIN);
		
		doNothing().when(usuarioValidador).validaSeUsuarioExiste(entradaDto.username());
		when(usuarioMapper.toEntity(entradaDto)).thenReturn(entidade);
		when(passwordEncoder.encode(entidade.getPassword())).thenReturn("encodedPassword");
		when(usuarioRepository.save(entidade)).thenReturn(entidade);
		when(usuarioMapper.toCriarDto(entidade)).thenReturn(dtoEsperado);
		
		RespotaUsuarioCriadoDto resultado = usuarioService.criarUsuario(entradaDto);
		
		
		assertNotNull(resultado);
		assertEquals(resultado.username(), dtoEsperado.username());
		assertEquals(resultado.role(), dtoEsperado.role());
		assertEquals("encodedPassword", entidade.getSenha());
		
		verify(usuarioValidador).validaSeUsuarioExiste(entradaDto.username());
		verify(usuarioMapper).toEntity(entradaDto);
		verify(passwordEncoder).encode("1234");
		verify(usuarioRepository).save(entidade);
		verify(usuarioMapper).toCriarDto(entidade);
		
	}
	
	@DisplayName("POST -  Deve lançar exception se o usuario ja existir.")
	@Test
	void deveLancarExceptionSeUsuarioJaExistir() {
		
		RequisicaoUsuarioDto entradaDto = new RequisicaoUsuarioDto("Joao Teste", "1234", UserRole.ADMIN);
		
		doThrow(new UsuarioExisteException()).when(usuarioValidador).validaSeUsuarioExiste(entradaDto.username());
		
		assertThrows(UsuarioExisteException.class, ()->{
			usuarioService.criarUsuario(entradaDto);
		});
		
		verify(usuarioMapper, never()).toEntity(any());
		verify(passwordEncoder, never()).encode(any());
		verify(usuarioRepository, never()).save(any());
		verify(usuarioMapper, never()).toCriarDto(any());
	}
}
