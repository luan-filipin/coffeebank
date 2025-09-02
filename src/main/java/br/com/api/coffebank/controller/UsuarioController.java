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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final UsuarioService usuarioService; 
	
	@PostMapping("/token")
	public ResponseEntity<RespostaTokenDto> token(@RequestBody @Valid UsuarioDto dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (Usuario)auth.getPrincipal();
		String token = tokenService.gerarToken(user);
		return ResponseEntity.ok(new RespostaTokenDto(token));
	}
	
	@PostMapping
	public ResponseEntity<RespotaUsuarioCriadoDto> criandoUsuario(@RequestBody @Valid RequisicaoUsuarioDto dto) {
		RespotaUsuarioCriadoDto usuarioCriado = usuarioService.criarUsuario(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
	}

	
}
