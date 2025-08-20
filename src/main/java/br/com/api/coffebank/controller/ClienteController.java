package br.com.api.coffebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ClienteController {

	private final ClienteService clienteService;
	
	@PostMapping("/cliente")
	public ResponseEntity<RespostaClienteDto> criarCliente(@RequestBody @Valid CriarClienteDto dto){
		RespostaClienteDto clienteCriado = clienteService.criarCliente(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
	}
	
	
	@GetMapping("/cliente")
	public ResponseEntity<RespostaClienteDto> buscarClientePeloCodigo(@RequestParam Long codigo){
		RespostaClienteDto cliente = clienteService.buscarClientePeloCodigo(codigo);
		return ResponseEntity.ok(cliente);
	}
}
