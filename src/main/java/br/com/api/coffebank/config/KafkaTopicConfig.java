package br.com.api.coffebank.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
	
    @Bean
    public NewTopic clienteCriadoTopic() {
        return new NewTopic("cliente-criado", 1, (short) 1);
    }

}
