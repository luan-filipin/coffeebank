package br.com.api.coffebank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.RequisicaoTransacaoValorDto;
import br.com.api.coffebank.service.event.producer.ContaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transacoes")
@Tag(name = "Conta", description = "Endpoints para depositar e sacar valores")
public class ContaController {
	
	private final ContaProducer contaProducer;

    @Operation(
            summary = "Deposita saldo na conta",
            description = "Deposita valores na conta via evento kafka de forma automatica",
            responses = {
                    	@ApiResponse(responseCode = "202", description = "Deposito realizado com sucesso"),
            }
    )
	@PostMapping("/depositar")
	public ResponseEntity<Void> depositarValor(@RequestBody @Valid RequisicaoTransacaoValorDto dto) {
		contaProducer.enviarValorDepositoKafka(dto.codigoCliente(), dto.valor());
		return ResponseEntity.accepted().build();
	}
    
    @Operation(
            summary = "Saca saldo da conta",
            description = "Saca valores na conta via evento kafka de forma automatica",
            responses = {
                    	@ApiResponse(responseCode = "202", description = "Saque realizado com sucesso"),
            }
    )	
	@PostMapping("/sacar")
	public ResponseEntity<Void> sacarValor(@RequestBody @Valid RequisicaoTransacaoValorDto dto){
		contaProducer.enviarValorSacarKafka(dto.codigoCliente(), dto.valor());
		return ResponseEntity.accepted().build();
	}
}
