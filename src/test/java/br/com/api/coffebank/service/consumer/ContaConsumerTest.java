package br.com.api.coffebank.service.consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.api.coffebank.dto.event.ClienteEventDto;
import br.com.api.coffebank.dto.event.ContaEventDto;
import br.com.api.coffebank.service.ContaService;
import br.com.api.coffebank.service.event.consumer.ContaConsumerImp;

@ExtendWith(MockitoExtension.class)
class ContaConsumerTest {
	
	@Mock
	private ContaService contaService;
	
	@InjectMocks
	private ContaConsumerImp contaConsumerImp;
	
	@DisplayName("Deve processar o evento de cliente criado com sucesso.")
	@Test
	void deveProcessarEventoDeClienteCriadoComSucesso() {
		
		ClienteEventDto evento = new ClienteEventDto(1L);
		
		doNothing().when(contaService).criarConta(evento.codigoCliente());
		
		contaConsumerImp.consumirEventoClienteCriadoKafka(evento);
		
		verify(contaService).criarConta(evento.codigoCliente());
	}
	
	@DisplayName("Deve lançar exception se criar conta falhar.")
	@Test
	void deveLancarExceptionSeCriarContaFalhar() {
		
		ClienteEventDto evento = new ClienteEventDto(1L);
		
		doThrow(new RuntimeException()).when(contaService).criarConta(evento.codigoCliente());
		
		contaConsumerImp.consumirEventoClienteCriadoKafka(evento);
		
		verify(contaService).criarConta(evento.codigoCliente());
	}
	
	@DisplayName("Deve processar o evento de cliente deletado com sucesso.")
	@Test
	void deveProcessarEventoDeClienteDeletadoComSucesso() {
		
		ClienteEventDto evento = new ClienteEventDto(1L);
		
		doNothing().when(contaService).deletarConta(evento.codigoCliente());
		
		contaConsumerImp.consumirEventoClienteDeletadoKafka(evento);
		
		verify(contaService).deletarConta(evento.codigoCliente());
	}
	
	@DisplayName("Deve lançar exception se deletar conta falhar.")
	@Test
	void deveLancarExceptionSeDeletarContaFalhar() {
		
		ClienteEventDto evento = new ClienteEventDto(1L);
		
		doThrow(new RuntimeException()).when(contaService).deletarConta(evento.codigoCliente());;
		
		contaConsumerImp.consumirEventoClienteDeletadoKafka(evento);
		
		verify(contaService).deletarConta(evento.codigoCliente());
	}
	
	@DisplayName("Deve processar o evento de depositar com sucesso.")
	@Test
	void deveProcessarEventoDeDepositarComSucesso() {
		
		ContaEventDto evento = new ContaEventDto(1L, BigDecimal.valueOf(100), Instant.now());
		
		doNothing().when(contaService).depositarValor(evento.codigoCliente(), null);
		
		contaConsumerImp.consumirEventoDepositarValor(evento);
		
		verify(contaService).depositarValor(evento.codigoCliente(), evento.valor());
	}
	
	@DisplayName("Deve lançar exception se depositar fallhar.")
	@Test
	void deveLancarExceptionSeDepositarFalhar() {
		
		ContaEventDto evento = new ContaEventDto(1L, BigDecimal.valueOf(100), Instant.now());

		doThrow(new RuntimeException()).when(contaService).depositarValor(evento.codigoCliente(), evento.valor());
		
		contaConsumerImp.consumirEventoDepositarValor(evento);
		
		verify(contaService).depositarValor(evento.codigoCliente(), evento.valor());
	}
	
	@DisplayName("Deve processar o evento de sacar com sucesso.")
	@Test
	void deveProcessarEventoDeSacarComSucesso() {
		
		ContaEventDto evento = new ContaEventDto(1L, BigDecimal.valueOf(100), Instant.now());

		doNothing().when(contaService).sacarValor(evento.codigoCliente(), evento.valor());
		
		contaConsumerImp.consumirEventoSacarValor(evento);
		
		verify(contaService).sacarValor(evento.codigoCliente(), evento.valor());
	}
	
	@DisplayName("Deve lançar exception se o saque falhar.")
	@Test
	void deveLancarExceptionSeSaquefalhar() {
		
		ContaEventDto evento = new ContaEventDto(1L, BigDecimal.valueOf(100), Instant.now());

		doThrow(new RuntimeException()).when(contaService).sacarValor(evento.codigoCliente(), evento.valor());
		
		contaConsumerImp.consumirEventoSacarValor(evento);
		
		verify(contaService).sacarValor(evento.codigoCliente(), evento.valor());
	}
	

}
