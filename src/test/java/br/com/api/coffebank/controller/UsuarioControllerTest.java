package br.com.api.coffebank.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.coffebank.config.GlobalExceptionHandler;
import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.dto.resposta.RespotaUsuarioCriadoDto;
import br.com.api.coffebank.entity.enums.UserRole;
import br.com.api.coffebank.repository.UsuarioRepository;
import br.com.api.coffebank.service.TokenService;
import br.com.api.coffebank.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UsuarioControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	private UsuarioService usuarioService;
	
	@MockitoBean
	private UsuarioRepository usuarioRepository;
	
	@MockitoBean
	private AuthenticationManager authenticationManager;
	
	@MockitoBean 
	private TokenService tokenService;
	
	@DisplayName("POST - Deve criar o usuario com sucesso.")
	@Test
	void deveCriarUsuarioComSucesso() throws Exception{
		
		RequisicaoUsuarioDto entradaDto = new RequisicaoUsuarioDto("Joao Teste", "1234", UserRole.ADMIN);
		RespotaUsuarioCriadoDto dtoEsperado = new RespotaUsuarioCriadoDto("Joao Teste", UserRole.ADMIN);

		when(usuarioService.criarUsuario(entradaDto)).thenReturn(dtoEsperado);
		
		mockMvc.perform(post("/api/usuario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(entradaDto)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.username").value("Joao Teste"))
		.andExpect(jsonPath("$.role").value("ADMIN"));
		
	}
	
	@DisplayName("POST - Deve lançar exception se os campos estiver invalidos.")
	@Test
	void develaLancarExceptionSeCamposInvalidos() throws Exception{
		
		RequisicaoUsuarioDto entradaDto = new RequisicaoUsuarioDto("", "1234", UserRole.ADMIN);

		mockMvc.perform(post("/api/usuario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(entradaDto)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.status").value(400))
		.andExpect(jsonPath("$.mensagem").value("Campos inválidos"))
		.andExpect(jsonPath("$.path").value("/api/usuario"))
		.andExpect(jsonPath("$.timestamp").exists())
		.andExpect(jsonPath("$.erros[0].campo").value("username"))
		.andExpect(jsonPath("$.erros[0].mensagem").value("O campo usuario é obrigatorio!"));
	}
}
