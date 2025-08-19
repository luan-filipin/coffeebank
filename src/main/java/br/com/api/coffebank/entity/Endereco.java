package br.com.api.coffebank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Endereco {

	@Column(nullable = false)
	private String rua;
	
	@Column(nullable = false)
	private String numero;
	
	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String complemento;
	
	@Column(nullable = false)
	private String pais;
}
