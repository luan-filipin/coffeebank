package br.com.api.coffebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	private final ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<RespostaClienteDto> criarCliente(@RequestBody @Valid RequisicaoClienteDto dto){
		RespostaClienteDto clienteCriado = clienteService.criarCliente(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
	}
	
	
	@GetMapping
	public ResponseEntity<RespostaClienteDto> buscarClientePeloCodigo(@RequestParam Long codigo){
		RespostaClienteDto cliente = clienteService.buscarClientePeloCodigo(codigo);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletaClientePeloCodigo(@RequestParam Long codigo){
		clienteService.deletaClientePeloCodigo(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
