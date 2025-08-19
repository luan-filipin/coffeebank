package br.com.api.coffebank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.api.coffebank.dto.CriarClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.CriarClienteEnderecoDto;
import br.com.api.coffebank.dto.resposta.RespostaCriacaoClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.resposta.RespostaCriacaoClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaCriacaoClienteEnderecoDto;
import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.entity.DadosPessoais;
import br.com.api.coffebank.entity.Endereco;
import br.com.api.coffebank.entity.TipoSexo;
import br.com.api.coffebank.exception.CpfJaExisteException;
import br.com.api.coffebank.mapper.ClienteMapper;
import br.com.api.coffebank.repository.ClienteRepository;
import br.com.api.coffebank.service.validador.ClienteValidador;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private ClienteValidador clienteValidador;
	
	@Mock
	private ClienteMapper clienteMapper;
	
	@InjectMocks
	private ClienteServiceImp clienteServiceImp;
	
	@DisplayName("POST -  Deve criar um cliente com sucesso.")
	@Test
	void deveCriarClienteComSucesso() {
		
		CriarClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new CriarClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", LocalDate.now(), "Brasileiro");
		CriarClienteEnderecoDto dtoEnderecoEntrada = new CriarClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		CriarClienteDto dtoEntrada = new CriarClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", LocalDate.now(), "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente(1L, LocalDateTime.now(), dadosPessoaisEntity, enderecoEntity);
		
		RespostaCriacaoClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaCriacaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", LocalDate.now(), "Brasileiro");
		RespostaCriacaoClienteEnderecoDto dtoEnderecoEsperado = new RespostaCriacaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaCriacaoClienteDto dtoEsperado = new RespostaCriacaoClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		doNothing().when(clienteValidador).validaSeCpfJaExiste(dtoDadosPessoaisEntrada.cpf());
		when(clienteMapper.toEntity(dtoEntrada)).thenReturn(clienteEntity);
		when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
		when(clienteMapper.toDto(clienteEntity)).thenReturn(dtoEsperado);
		
		RespostaCriacaoClienteDto resultado = clienteServiceImp.criarCliente(dtoEntrada);
		
		assertNotNull(resultado);
		assertEquals(resultado, dtoEsperado);
		
		verify(clienteValidador).validaSeCpfJaExiste(dtoDadosPessoaisEntrada.cpf());
		verify(clienteMapper).toEntity(dtoEntrada);
		verify(clienteRepository).save(clienteEntity);
		verify(clienteMapper).toDto(clienteEntity);
		
	}
	
	@DisplayName("POST -  Deve lançar exception se o Cpf ja existir no banco.")
	@Test 
	void deveLancarExceptionSeCpfJaExistir() {
		
		CriarClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new CriarClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", LocalDate.now(), "Brasileiro");
		CriarClienteEnderecoDto dtoEnderecoEntrada = new CriarClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		CriarClienteDto dtoEntrada = new CriarClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		doThrow(new CpfJaExisteException()).when(clienteValidador).validaSeCpfJaExiste(dtoDadosPessoaisEntrada.cpf());
		
		CpfJaExisteException exception = assertThrows(CpfJaExisteException.class, () ->{
			clienteServiceImp.criarCliente(dtoEntrada);
		});
		
		assertEquals("Ja existe um cliente com esse CPF!", exception.getMessage());
		
		verify(clienteMapper, never()).toEntity(any());
		verify(clienteRepository, never()).save(any());
		verify(clienteMapper, never()).toDto(any());
	}
}
