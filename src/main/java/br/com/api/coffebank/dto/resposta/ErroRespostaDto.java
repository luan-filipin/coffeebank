package br.com.api.coffebank.dto.resposta;

import java.time.Instant;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Objeto de erro padrão retornado pela API")
@Data
public class ErroRespostaDto {

	@Schema(description = "Mensagem de erro explicando o motivo da falha", 
            example = "Cliente com código 123 não encontrado")
	private String mensagem;
	
	@Schema(description = "Código de status HTTP associado ao erro", 
            example = "404")
	private int status;
	
	@Schema(description = "Caminho da requisição que gerou o erro", 
	        example = "/api/cliente/123")
	private String path;
	
	@Schema(description = "Momento em que o erro ocorreu (UTC)", 
	            example = "2025-09-04T14:23:45Z")
	private Instant timestamp;
	
	@Schema(description = "Lista de erros de validação de campos (se aplicável)")
	private List<ErroCampoDto> erros;
	
	public ErroRespostaDto(String mensagem, int status, String path) {
		this.mensagem = mensagem;
		this.status = status;
		this.path = path;
		this.timestamp = Instant.now();
	}
	
	public ErroRespostaDto(String message, int status, String path, List<ErroCampoDto> erros) {
		this(message, status, path);
		this.erros = erros;
	}
}
