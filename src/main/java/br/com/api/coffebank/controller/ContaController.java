package br.com.api.coffebank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.RequisicaoTransacaoValorDto;
import br.com.api.coffebank.service.event.producer.ContaProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transacoes")
public class ContaController {
	
	private final ContaProducer contaProducer;

	@PostMapping("/depositar")
	public ResponseEntity<Void> depositarValor(@RequestBody @Valid RequisicaoTransacaoValorDto dto) {
		contaProducer.enviarValorDepositoKafka(dto.codigoCliente(), dto.valor());
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping("/sacar")
	public ResponseEntity<Void> sacarValor(@RequestBody @Valid RequisicaoTransacaoValorDto dto){
		contaProducer.enviarValorSacarKafka(dto.codigoCliente(), dto.valor());
		return ResponseEntity.accepted().build();
	}
}
