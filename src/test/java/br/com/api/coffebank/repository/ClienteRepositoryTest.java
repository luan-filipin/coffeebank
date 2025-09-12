package br.com.api.coffebank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.entity.DadosPessoais;
import br.com.api.coffebank.entity.Endereco;
import br.com.api.coffebank.entity.enums.TipoSexo;

@Testcontainers
@DataJpaTest
class ClienteRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
			.withDatabaseName("cliente_teste")
			.withUsername("admin")
			.withPassword("admin");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@DisplayName("Deve buscar o cliente pelo CPF com sucesso.")
	@Test
	void deveBuscarClientePeloCpfComSucesso() {
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente();
		clienteEntity.setDadosPessoais(dadosPessoaisEntity);
		clienteEntity.setEndereco(enderecoEntity);
			
		clienteRepository.save(clienteEntity);
		
		Optional<Cliente> result = clienteRepository.findByDadosPessoais_Cpf("12345678910");
		
		assertThat(result).isPresent();
		assertThat(result.get().getDadosPessoais().getCpf()).isEqualTo("12345678910");
		assertThat(result.get().getDadosPessoais().getNome()).isEqualTo("Luan");
	}
	
	@DisplayName("Deve falhar ao buscar cliente pelo CPF.")
	@Test
	void deveFalharAoBuscarClientePeloCpf() {
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente();
		clienteEntity.setDadosPessoais(dadosPessoaisEntity);
		clienteEntity.setEndereco(enderecoEntity);
		
		clienteRepository.save(clienteEntity);

		Optional<Cliente> result = clienteRepository.findByDadosPessoais_Cpf("12345678917");
		
		assertThat(result).isNotPresent();
	}
	
	@DisplayName("Deve buscar o cliente pelo codigo do cliente com sucesso.")
	@Test
	void deveBuscarClientePeloCodigoComSucesso() {
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente();
		clienteEntity.setDadosPessoais(dadosPessoaisEntity);
		clienteEntity.setEndereco(enderecoEntity);
		
		Cliente clienteSalvo =clienteRepository.save(clienteEntity);
		
		Optional<Cliente> result = clienteRepository.findByCodigoCliente(clienteSalvo.getCodigoCliente());
		
		assertThat(result).isPresent();
		assertThat(result.get().getCodigoCliente()).isEqualTo(clienteSalvo.getCodigoCliente());
		assertThat(result.get().getDadosPessoais().getNome()).isEqualTo("Luan");
		assertThat(result.get().getDadosPessoais().getCpf()).isEqualTo("12345678910");
	}
	
	@DisplayName("Deve falhar ao buscar o cliente pelo codigo do cliente.")
	@Test
	void deveFalharAoBuscarClientePeloCodigo() {
		
	    LocalDate dataNascimento = LocalDate.of(1992, 5, 2);
	    
		DadosPessoais dadosPessoaisEntity = new DadosPessoais("Luan", "teste@teste.com", TipoSexo.MASCULINO, "12345678910", "33333333", dataNascimento, "Brasileiro");
		Endereco enderecoEntity = new Endereco("Rua teste", "83", "BairroTeste", "Sao Paulo", "Casa", "Brasil");
		Cliente clienteEntity = new Cliente();
		clienteEntity.setDadosPessoais(dadosPessoaisEntity);
		clienteEntity.setEndereco(enderecoEntity);
		
		Cliente clienteSalvo = clienteRepository.save(clienteEntity);
		
		Optional<Cliente> result = clienteRepository.findByCodigoCliente(clienteSalvo.getCodigoCliente() + 1);
		
		assertThat(result).isNotPresent();
	}
}
