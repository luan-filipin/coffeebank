package br.com.api.coffebank.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.coffebank.config.GlobalExceptionHandler;
import br.com.api.coffebank.dto.RequisicaoClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.RequisicaoClienteEnderecoDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteEnderecoDto;
import br.com.api.coffebank.entity.TipoSexo;
import br.com.api.coffebank.service.ClienteService;
import static org.hamcrest.Matchers.containsString;


@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	private ClienteService clienteService;
	
	@DisplayName("POST - Deve criar o cliente com sucesso.")
	@Test
	void deveCriarClienteComSucesso() throws Exception{
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
	
		RespostaClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RespostaClienteEnderecoDto dtoEnderecoEsperado = new RespostaClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaClienteDto dtoEsperado = new RespostaClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		when(clienteService.criarCliente(dtoEntrada)).thenReturn(dtoEsperado);
		
		mockMvc.perform(post("/api/cliente")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dtoEntrada)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.codigoCliente").value(1L))
		.andExpect(jsonPath("$.dadosPessoais.nome").value("Luan"))
		.andExpect(jsonPath("$.dadosPessoais.cpf").value("12345678910"));
		
	}
	
	@DisplayName("POST - Deve lançar exception se tiver algum campo nullo ou ausente.")
	@Test
	void deveLancarExceptionSeCampoForNulloOuAusente() throws Exception{
		
		LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		mockMvc.perform(post("/api/cliente")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dtoEntrada)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.status").value(400))
		.andExpect(jsonPath("$.mensagem").value("Campos inválidos"))
		.andExpect(jsonPath("$.path").value("/api/cliente"))
		.andExpect(jsonPath("$.timestamp").exists())
		.andExpect(jsonPath("$.erros[0].campo").value("dadosPessoais.nome"))
		.andExpect(jsonPath("$.erros[0].mensagem").value("O campo 'nome' é obrigatorio!"));
	}
	
	@DisplayName("GET - Deve buscar o cliente pelo codigo do cliente com sucesso.")
	@Test
	void deveBuscarClientePeloCodigoComSucesso() throws Exception{
		
		Long codigoCliente = 1L;
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
		
		RespostaClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RespostaClienteEnderecoDto dtoEnderecoEsperado = new RespostaClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaClienteDto dtoEsperado = new RespostaClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		when(clienteService.buscarClientePeloCodigo(codigoCliente)).thenReturn(dtoEsperado);
		
		mockMvc.perform(get("/api/cliente")
				.param("codigo", String.valueOf(codigoCliente)))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.codigoCliente").value(1L))
        .andExpect(jsonPath("$.dadosPessoais.nome").value("Luan"))
        .andExpect(jsonPath("$.dadosPessoais.email").value("teste@teste.com"))
        .andExpect(jsonPath("$.dadosPessoais.sexo").value("MASCULINO"))
        .andExpect(jsonPath("$.dadosPessoais.cpf").value("12345678910"));
	}
	
	@DisplayName("GET - Deve lançar exception se o campo estiver ausente.")
	@Test
	void deveLancarExceptionSeCampoEstiveAusente() throws Exception{

	    mockMvc.perform(get("/api/cliente"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.mensagem").value("O parâmetro 'codigo' é obrigatório!"))
        .andExpect(jsonPath("$.path").value("/api/cliente"))
        .andExpect(jsonPath("$.timestamp").exists());
	
	}
	
	@DisplayName("DELETE - Deve deletar cliente com sucesso pelo codigo.")
	@Test
	void deveDeletarClientePeloCodigoComSucesso()throws Exception{
		
		Long codigoCliente = 1L;
		
		doNothing().when(clienteService).deletaClientePeloCodigo(codigoCliente);
		
		mockMvc.perform(delete("/api/cliente")
				.param("codigo", String.valueOf(codigoCliente)))
		.andExpect(status().isNoContent());
	}
	
	@DisplayName("DELETE - Deve lançar exception caso nao tenha o parametro codigo.")
	@Test
	void deveLancarExceptionSeCodigoForAusente() throws Exception{
		
		mockMvc.perform(delete("/api/cliente"))
		.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.mensagem").value("O parâmetro 'codigo' é obrigatório!"))
        .andExpect(jsonPath("$.path").value("/api/cliente"))
        .andExpect(jsonPath("$.timestamp").exists());	
		
	}
	
}
