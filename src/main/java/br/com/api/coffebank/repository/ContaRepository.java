package br.com.api.coffebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.coffebank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	boolean existsByCodigoCliente(Long codigoCliente);
	
	Optional<Conta> findByCodigoCliente(Long codigoCliente);
	
	void deleteByCodigoCliente(Long codigoCliente);
}
