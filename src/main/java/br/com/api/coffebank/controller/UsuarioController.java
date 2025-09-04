package br.com.api.coffebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.coffebank.dto.RequisicaoUsuarioDto;
import br.com.api.coffebank.dto.UsuarioDto;
import br.com.api.coffebank.dto.resposta.RespostaTokenDto;
import br.com.api.coffebank.dto.resposta.RespotaUsuarioCriadoDto;
import br.com.api.coffebank.entity.Usuario;
import br.com.api.coffebank.service.TokenService;
import br.com.api.coffebank.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/usuario")
@Tag(name = "Usuario", description = "Endpoints para criar usuario e solicitar token")
public class UsuarioController {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final UsuarioService usuarioService; 
	
    @Operation(
            summary = "Solicitar token",
            description = "Retorna token para requisição após autenticação",
            responses = {
                    	@ApiResponse(responseCode = "200", description = "Token gerado com sucesso"),
            }
    )
	@PostMapping("/token")
	public ResponseEntity<RespostaTokenDto> token(@RequestBody @Valid UsuarioDto dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (Usuario)auth.getPrincipal();
		String token = tokenService.gerarToken(user);
		return ResponseEntity.ok(new RespostaTokenDto(token));
	}
	
    @Operation(
            summary = "Criar usuario",
            description = "Cria um usuario para autenticação e solicitação do token",
            responses = {
                    	@ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
            }
    )
	@PostMapping
	public ResponseEntity<RespotaUsuarioCriadoDto> criandoUsuario(@RequestBody @Valid RequisicaoUsuarioDto dto) {
		RespotaUsuarioCriadoDto usuarioCriado = usuarioService.criarUsuario(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
	}	
}
