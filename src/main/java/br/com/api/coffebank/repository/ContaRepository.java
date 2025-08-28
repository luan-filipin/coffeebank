package br.com.api.coffebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.coffebank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	boolean existsByNumeroConta(String numeroConta);
}
