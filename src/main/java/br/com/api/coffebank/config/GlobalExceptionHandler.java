package br.com.api.coffebank.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.api.coffebank.dto.resposta.ErroCampoDto;
import br.com.api.coffebank.dto.resposta.ErroRespostaDto;
import br.com.api.coffebank.exception.CodigoInexistenteException;
import br.com.api.coffebank.exception.CpfUrlDiferenteDoCorpoException;
import br.com.api.coffebank.exception.ErroAoGerarTokenException;
import br.com.api.coffebank.exception.NumeroDaContaNaoExisteException;
import br.com.api.coffebank.exception.TokenInvalidoOuExpiradoException;
import br.com.api.coffebank.exception.UsuarioExisteException;
import br.com.api.coffebank.exception.CpfJaExisteException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErroRespostaDto> handlerGenerico(RuntimeException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
	@ExceptionHandler(ErroAoGerarTokenException.class)
	public ResponseEntity<ErroRespostaDto> handlerErroAoGerarToken(ErroAoGerarTokenException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
	@ExceptionHandler(TokenInvalidoOuExpiradoException.class)
	public ResponseEntity<ErroRespostaDto> handlerTokenInvalidoOuExpirado(TokenInvalidoOuExpiradoException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(), 
				HttpStatus.UNAUTHORIZED.value(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}
	
	@ExceptionHandler(CodigoInexistenteException.class)
	public ResponseEntity<ErroRespostaDto> handlerCpfInexistente(CodigoInexistenteException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND.value(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(NumeroDaContaNaoExisteException.class)
	public ResponseEntity<ErroRespostaDto> handlerNumeroDaContaNaoExiste(NumeroDaContaNaoExisteException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(), 
				HttpStatus.NOT_FOUND.value(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(CpfJaExisteException.class)
	public ResponseEntity<ErroRespostaDto> handlerValidaSeCpfJaExiste(CpfJaExisteException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(),
				HttpStatus.CONFLICT.value(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(UsuarioExisteException.class)
	public ResponseEntity<ErroRespostaDto> handlerUsuarioExiste(UsuarioExisteException ex, HttpServletRequest request){
		ErroRespostaDto erro = new ErroRespostaDto(
				ex.getMessage(),
				HttpStatus.CONFLICT.value(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	//Esse handler é disparado quando a falha de validação em objetos do corpo da requisição.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroRespostaDto> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		List<ErroCampoDto> erros = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(erro -> new ErroCampoDto(erro.getField(), erro.getDefaultMessage()))
				.toList();
		
		ErroRespostaDto resposta = new ErroRespostaDto(
				"Campos inválidos",
				HttpStatus.BAD_REQUEST.value(), 
				request.getRequestURI(),
				erros);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
	}
	
	//Esse handler é disparado quando a falha de validação no parametro de entrada.
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroRespostaDto> handerconstraintViolation(ConstraintViolationException ex, HttpServletRequest request){
		
		List<ErroCampoDto> erros = ex.getConstraintViolations()
				.stream()
				.map(violation -> new ErroCampoDto(
						violation.getPropertyPath().toString(), 
						violation.getMessage()))
				.toList();
		
		ErroRespostaDto resposta = new ErroRespostaDto(
				"Campos inválidos",
				HttpStatus.BAD_REQUEST.value(),
				request.getRequestURI(),
				erros);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
	}
	
	//Esse Handler é disparado quando um parâmentro obrigatorio nao é enviado.
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErroRespostaDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
	    ErroRespostaDto erro = new ErroRespostaDto(
	        "Método " + ex.getMethod() + " não é suportado para esta URL.",
	        HttpStatus.METHOD_NOT_ALLOWED.value(),
	        request.getRequestURI()
	    );
	    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(erro);
	}
	
	@ExceptionHandler(CpfUrlDiferenteDoCorpoException.class)
	public ResponseEntity<ErroRespostaDto> handleCodigoUrlDiferenteDoCorpo(
	        CpfUrlDiferenteDoCorpoException ex, HttpServletRequest request) {

	    ErroRespostaDto erro = new ErroRespostaDto(
	            ex.getMessage(),
	            HttpStatus.BAD_REQUEST.value(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

}
