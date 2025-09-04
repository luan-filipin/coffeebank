package br.com.api.coffebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.resposta.ErroRespostaDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/cliente")
@Tag(name = "Clientes", description = "Endpoints para criar, buscar, deletar e atualizar cliente")
public class ClienteController {

	private final ClienteService clienteService;
	
    @Operation(
            summary = "Criar cliente",
            description = "Cria um cliente no sistema e dispara um evento para criação automática da conta via Kafka",
            responses = {
                    	@ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            }
    )
	@PostMapping
	public ResponseEntity<RespostaClienteDto> criarCliente(@RequestBody @Valid RequisicaoClienteDto dto){
		RespostaClienteDto clienteCriado = clienteService.criarCliente(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
	}
	
    @Operation(
            summary = "Buscar cliente",
            description = "Busca um cliente pelo codigo do cliente",
            responses = {
                        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            }
    )
	@GetMapping("/{codigo}")
	public ResponseEntity<RespostaClienteDto> buscarClientePeloCodigo(@Parameter(description = "Código único do cliente", example = "123") @PathVariable Long codigo){
		RespostaClienteDto cliente = clienteService.buscarClientePeloCodigo(codigo);
		return ResponseEntity.ok(cliente);
	}
	
    @Operation(
            summary = "Deletar cliente",
            description = "Deleta um cliente e dispara um evento para a exclusão automática da conta via Kafka",
            responses = {
            			@ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            }
    )
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletaClientePeloCodigo(@Parameter(description = "Código único do cliente", example = "123") @PathVariable Long codigo){
		clienteService.deletaClientePeloCodigo(codigo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente existente. Caso o CPF informado na URL seja diferente do CPF no corpo da requisição, retorna erro.",
            responses = {
                        @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
            }
    )
	@PutMapping("/{codigo}")
	public ResponseEntity<RespostaClienteDto> atualizarCliente(@RequestBody @Valid RequisicaoClienteDto dto, @Parameter(description = "Código único do cliente", example = "123") @PathVariable Long codigo){
		RespostaClienteDto clienteAtualizado = clienteService.atualizaClientePeloCodigo(dto, codigo);
		return ResponseEntity.ok(clienteAtualizado);
	}
}
