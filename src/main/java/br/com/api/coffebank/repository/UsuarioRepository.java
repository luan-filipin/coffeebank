package br.com.api.coffebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.api.coffebank.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	UserDetails findByUsername(String usuario);
	
	boolean existsByUsername(String usuario);
}
