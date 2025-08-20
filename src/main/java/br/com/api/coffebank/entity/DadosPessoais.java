package br.com.api.coffebank.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class DadosPessoais {

	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoSexo sexo;
	
	@Column(nullable = false, unique = true)
	private String cpf;
	
	@Column(nullable = false)
	private String telefone;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	@Column(nullable = false)
	private String nacionalidade;
	
}
