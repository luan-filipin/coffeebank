package br.com.api.coffebank.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@Column(name = "codigo_cliente", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoCliente;
	
	@CreationTimestamp
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@Embedded
	private DadosPessoais dadosPessoais;
	
	@Embedded
	private Endereco endereco;
	
}
