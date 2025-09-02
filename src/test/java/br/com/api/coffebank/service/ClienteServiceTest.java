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

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.api.coffebank.dto.RequisicaoClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.RequisicaoClienteEnderecoDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDadosPessoaisDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteEnderecoDto;
import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.entity.DadosPessoais;
import br.com.api.coffebank.entity.Endereco;
import br.com.api.coffebank.entity.enums.TipoSexo;
import br.com.api.coffebank.exception.CodigoInexistenteException;
import br.com.api.coffebank.exception.CpfJaExisteException;
import br.com.api.coffebank.exception.CpfUrlDiferenteDoCorpoException;
import br.com.api.coffebank.mapper.ClienteMapper;
import br.com.api.coffebank.repository.ClienteRepository;
import br.com.api.coffebank.service.event.producer.ClienteProducer;
import br.com.api.coffebank.service.validador.ClienteValidador;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private ClienteValidador clienteValidador;
	
	@Mock
	private ClienteMapper clienteMapper;
	
	@Mock
	private ClienteProducer clienteProducer;
	
	@InjectMocks
	private ClienteServiceImp clienteServiceImp;
	
	@DisplayName("POST -  Deve criar um cliente com sucesso.")
	@Test
	void deveCriarClienteComSucesso() {
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    LocalDateTime dataCriacao = LocalDateTime.of(2025, 8, 19, 12, 0);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente(1L, dataCriacao, dadosPessoaisEntity, enderecoEntity);
		
		RespostaClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RespostaClienteEnderecoDto dtoEnderecoEsperado = new RespostaClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaClienteDto dtoEsperado = new RespostaClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		doNothing().when(clienteValidador).validaSeCpfJaExiste(dtoDadosPessoaisEntrada.cpf());
		when(clienteMapper.toEntity(dtoEntrada)).thenReturn(clienteEntity);
		when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
		doNothing().when(clienteProducer).enviarClienteCriadoKafka(clienteEntity.getCodigoCliente());
		when(clienteMapper.toDto(clienteEntity)).thenReturn(dtoEsperado);
		
		RespostaClienteDto resultado = clienteServiceImp.criarCliente(dtoEntrada);
		
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
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		doThrow(new CpfJaExisteException()).when(clienteValidador).validaSeCpfJaExiste(dtoDadosPessoaisEntrada.cpf());
		
		assertThrows(CpfJaExisteException.class, () ->{
			clienteServiceImp.criarCliente(dtoEntrada);
		});
		
		
		verify(clienteMapper, never()).toEntity(any());
		verify(clienteRepository, never()).save(any());
		verify(clienteMapper, never()).toDto(any());
	}
	
	@DisplayName("GET -  Deve buscar um cliente pelo codigoCliente com sucesso.")
	@Test 
	void deveBuscarClientePeloCodigoComSucesso() {
		
		Long codigoCliente = 1L;
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    LocalDateTime dataCriacao = LocalDateTime.of(2025, 8, 19, 12, 0);
		
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente(1L, dataCriacao, dadosPessoaisEntity, enderecoEntity);
		
		RespostaClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RespostaClienteEnderecoDto dtoEnderecoEsperado = new RespostaClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaClienteDto dtoEsperado = new RespostaClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		when(clienteValidador.validaSeOCodigoDoClienteExiste(codigoCliente)).thenReturn(clienteEntity);
		when(clienteMapper.toDto(clienteEntity)).thenReturn(dtoEsperado);
		
		RespostaClienteDto resultado = clienteServiceImp.buscarClientePeloCodigo(codigoCliente);
		
		assertNotNull(resultado);
		assertEquals(resultado, dtoEsperado);
		
		verify(clienteValidador).validaSeOCodigoDoClienteExiste(codigoCliente);
		verify(clienteMapper).toDto(clienteEntity);
	}
	
	@DisplayName("GET -  Deve lançar exception se o codigoCliente não existir.")
	@Test 
	void deveLancarExceptionSeCodigoClienteNaoExistir() {
		
		Long codigoCliente = 1L;
		
		doThrow(new CodigoInexistenteException()).when(clienteValidador).validaSeOCodigoDoClienteExiste(codigoCliente);
		
		assertThrows(CodigoInexistenteException.class, ()->{
			clienteServiceImp.buscarClientePeloCodigo(codigoCliente);
		});
		
		verify(clienteMapper, never()).toDto(any());
	}
	
	@DisplayName("DELETE -  Deve deletar um cliente com sucesso.")
	@Test 
	void deveDeletarClienteComSucesso() {
		
		Long codigo = 1L;
		
		doNothing().when(clienteValidador).validaSeOCodigoExisteMasNaoRetornaEntity(codigo);
		doNothing().when(clienteRepository).deleteById(codigo);
		doNothing().when(clienteProducer).enviarClienteDeletadoKafka(codigo);
		
		clienteServiceImp.deletaClientePeloCodigo(codigo);
		
		verify(clienteValidador).validaSeOCodigoExisteMasNaoRetornaEntity(codigo);
		verify(clienteRepository).deleteById(codigo);
		verify(clienteProducer).enviarClienteDeletadoKafka(codigo);
	}
	
	@DisplayName("DELETE -  Deve lançar exception se o cliente nao existir.")
	@Test 
	void deveLancarExceptionSeClienteNaoExistir() {
		Long codigo = 1L;
		
		doThrow(new CodigoInexistenteException()).when(clienteValidador).validaSeOCodigoExisteMasNaoRetornaEntity(codigo);
		
		assertThrows(CodigoInexistenteException.class, ()->{
			clienteServiceImp.deletaClientePeloCodigo(codigo);
		});
		
		verify(clienteRepository, never()).deleteById(any());		
	}
	
	@DisplayName("UPDATE -  Deve atualizar um cliente pelo codigo com sucesso.")
	@Test 
	void deveAtualizarClientePeloCodigoComSucesso() {
		
		Long codigoCliente = 1L;
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    LocalDateTime dataCriacao = LocalDateTime.of(2025, 8, 19, 12, 0);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente(1L, dataCriacao, dadosPessoaisEntity, enderecoEntity);
		
		RespostaClienteDadosPessoaisDto dtoDadosPessoaisEsperado = new RespostaClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RespostaClienteEnderecoDto dtoEnderecoEsperado = new RespostaClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RespostaClienteDto dtoEsperado = new RespostaClienteDto(1L, dtoDadosPessoaisEsperado, dtoEnderecoEsperado);
		
		when(clienteValidador.validaSeOCodigoDoClienteExiste(codigoCliente)).thenReturn(clienteEntity);
		doNothing().when(clienteValidador).validaCpfUrlIgualAoCorpo(clienteEntity.getDadosPessoais().getCpf(), dtoEntrada.dadosPessoais().cpf());
		doNothing().when(clienteMapper).atualizaDto(dtoEntrada, clienteEntity);
		when(clienteMapper.toDto(clienteEntity)).thenReturn(dtoEsperado);
		
		RespostaClienteDto resultado = clienteServiceImp.atualizaClientePeloCodigo(dtoEntrada, codigoCliente);
		
		assertNotNull(resultado);
		assertEquals(resultado, dtoEsperado);
	
		verify(clienteValidador).validaSeOCodigoDoClienteExiste(codigoCliente);
		verify(clienteValidador).validaCpfUrlIgualAoCorpo(clienteEntity.getDadosPessoais().getCpf(), dtoEntrada.dadosPessoais().cpf());
		verify(clienteMapper).atualizaDto(dtoEntrada, clienteEntity);
		verify(clienteMapper).toDto(clienteEntity);
	}
	
	@DisplayName("UPDATE -  Deve lancar exception se codigo nao existir.")
	@Test 
	void deveLancarExceptionSeCodigoNaoExistir() {
		
		Long codigoCliente = 1L;
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		doThrow(new CodigoInexistenteException()).when(clienteValidador).validaSeOCodigoDoClienteExiste(codigoCliente);
		
		assertThrows(CodigoInexistenteException.class, ()->{
			clienteServiceImp.atualizaClientePeloCodigo(dtoEntrada, codigoCliente);
		});
		
		verify(clienteValidador, never()).validaCpfUrlIgualAoCorpo(any(), any());
		verify(clienteMapper, never()).atualizaDto(any(), any());
		verify(clienteMapper, never()).toDto(any());
		
	}
	
	@DisplayName("UPDATE -  Deve lancar exception se cpf da url for diferente do passado no corpo.")
	@Test 
	void deveLancarExceptionSeCpfUrlDiferenteDoCorpo() {
		
		Long codigoCliente = 1L;
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    LocalDateTime dataCriacao = LocalDateTime.of(2025, 8, 19, 12, 0);
		
		RequisicaoClienteDadosPessoaisDto dtoDadosPessoaisEntrada = new RequisicaoClienteDadosPessoaisDto("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		RequisicaoClienteEnderecoDto dtoEnderecoEntrada = new RequisicaoClienteEnderecoDto("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		RequisicaoClienteDto dtoEntrada = new RequisicaoClienteDto(dtoDadosPessoaisEntrada, dtoEnderecoEntrada);
		
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente(1L, dataCriacao, dadosPessoaisEntity, enderecoEntity);
		
		when(clienteValidador.validaSeOCodigoDoClienteExiste(codigoCliente)).thenReturn(clienteEntity);
		doThrow(new CpfUrlDiferenteDoCorpoException()).when(clienteValidador).validaCpfUrlIgualAoCorpo(clienteEntity.getDadosPessoais().getCpf(), dtoEntrada.dadosPessoais().cpf());
		
		assertThrows(CpfUrlDiferenteDoCorpoException.class, ()->{
			clienteServiceImp.atualizaClientePeloCodigo(dtoEntrada, codigoCliente);
		});
		
		verify(clienteValidador).validaSeOCodigoDoClienteExiste(codigoCliente);
		verify(clienteMapper, never()).atualizaDto(any(), any());
		verify(clienteMapper, never()).toDto(any());
	}
}
