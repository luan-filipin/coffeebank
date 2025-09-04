package br.com.api.coffebank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("CoffeBank – API Bancária Simples")
						.description("API de um sistema bancário simples, permitindo CRUD completo de clientes, criação de usuários e autenticação via token JWT. Eventos críticos, como criação de contas, são processados de forma assíncrona via Kafka.")
						.version("1.0")
						.contact(new Contact()
								.name("Luan Brito")
								.email("https://github.com/luan-filipin"))
					 );
	}
}
