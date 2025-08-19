package br.com.api.coffebank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "codigo_cliente", nullable = false)
	private Long codigoCliente;
	
	@Column(name = "numero_conta", nullable = false)
	private String numeroConta;
	
	@Column(nullable = false)
	private BigDecimal saldo;
	
	@Column(name = "tipo_conta", nullable = false)
	private TipoConta tipoConta;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
}
