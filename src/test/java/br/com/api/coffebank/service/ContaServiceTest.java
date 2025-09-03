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

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.api.coffebank.entity.Conta;
import br.com.api.coffebank.entity.enums.TipoConta;
import br.com.api.coffebank.exception.NumeroDaContaNaoExisteException;
import br.com.api.coffebank.exception.ValorNegativoException;
import br.com.api.coffebank.repository.ContaRepository;
import br.com.api.coffebank.service.validador.ContaValidador;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {
	
	@Mock
	private ContaRepository contaRepository;
	
	@Mock
	private ContaValidador contaValidador;
	
	@Captor
    private ArgumentCaptor<Conta> contaCaptor;
	
	@InjectMocks
	private ContaServiceImp contaServiceImp;
	
	@DisplayName("POST - Deve criar uma conta com sucesso.")
	@Test
	void deveCriarContaComSucesso() {
	    Long codigoCliente = 1L;

	    contaServiceImp.criarConta(codigoCliente);

	    // Captura o objeto salvo no repository
	    verify(contaRepository).save(contaCaptor.capture());
	    Conta contaSalva = contaCaptor.getValue();

	    // Validar os atributos da conta criada.
	    assertEquals(codigoCliente, contaSalva.getCodigoCliente());
	    assertEquals(BigDecimal.ZERO, contaSalva.getSaldo());
	    assertEquals(TipoConta.CORRENTE, contaSalva.getTipoConta());
	    assertNotNull(contaSalva.getNumeroConta());
	}
	
	@DisplayName("DELETE - Deve deletar conta com sucesso")
	@Test
	void deveDeletarContaComSucesso() {
		
		Long codigoCliente = 1L;
		
		doNothing().when(contaValidador).validaSeContaExiste(codigoCliente);
		doNothing().when(contaRepository).deleteByCodigoCliente(codigoCliente);
		
		contaServiceImp.deletarConta(codigoCliente);
		
		verify(contaValidador).validaSeContaExiste(codigoCliente);
		verify(contaRepository).deleteByCodigoCliente(codigoCliente);
	}
	
	@DisplayName("DELETE - Deve lançar exception se a conta nao existir.")
	@Test
	void deveLancarExceptionSeContaNaoExistir() {
		
		Long codigoCliente = 1L;
		
		doThrow(new NumeroDaContaNaoExisteException()).when(contaValidador).validaSeContaExiste(codigoCliente);
		
		assertThrows(NumeroDaContaNaoExisteException.class, ()->{
			contaServiceImp.deletarConta(codigoCliente);
		});
		
		verify(contaRepository, never()).deleteByCodigoCliente(any());
	}
	
	@DisplayName("Deve depositar valor com sucesso.")
	@Test
	void deveDepositarValorComSucesso() {
		
		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);
		
		Conta conta = new Conta();
		conta.setId(1L);
		conta.setCodigoCliente(1L);
		conta.setNumeroConta("123456");
		conta.setSaldo(BigDecimal.valueOf(50));
		conta.setTipoConta(TipoConta.CORRENTE);
		
		when(contaValidador.validaSeContaExisteERetornaEntidade(codigoCliente)).thenReturn(conta);
		doNothing().when(contaValidador).validaSeValorNullOuNegativo(valor);
		
		contaServiceImp.depositarValor(codigoCliente, valor);
		
		assertEquals(BigDecimal.valueOf(150), conta.getSaldo());
		
		verify(contaValidador).validaSeContaExisteERetornaEntidade(codigoCliente);
		verify(contaValidador).validaSeValorNullOuNegativo(valor);
	}
	
	@DisplayName("Deve lançar exception se a conta nao existir.")
	@Test
	void deveLancarExceptionSeContaNaoExistirParaDeposito() {
		
		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);
		
		doThrow(new NumeroDaContaNaoExisteException()).when(contaValidador).validaSeContaExisteERetornaEntidade(codigoCliente);
		
		assertThrows(NumeroDaContaNaoExisteException.class, ()->{
			contaServiceImp.depositarValor(codigoCliente, valor);
		});
		
		verify(contaValidador, never()).validaSeValorNullOuNegativo(any());
	}
	
	@DisplayName("Deve lançar exception se o valor for negativo ou null")
	@Test
	void deveLancarExceptionSeVaorNegativoOuNull() {
		
		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(-100);
		
		Conta conta = new Conta();
		conta.setId(1L);
		conta.setCodigoCliente(1L);
		conta.setNumeroConta("123456");
		conta.setSaldo(BigDecimal.valueOf(50));
		conta.setTipoConta(TipoConta.CORRENTE);
		
		when(contaValidador.validaSeContaExisteERetornaEntidade(codigoCliente)).thenReturn(conta);
		doThrow(new ValorNegativoException()).when(contaValidador).validaSeValorNullOuNegativo(valor);
		
		assertThrows(ValorNegativoException.class, ()->{
			contaServiceImp.depositarValor(codigoCliente, valor);
		});

	}
}
