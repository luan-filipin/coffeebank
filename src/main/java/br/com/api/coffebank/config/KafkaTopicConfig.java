package br.com.api.coffebank.config;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
	
	@Value("${kafka.topicos}")
	private String[] topicos;
	
    @Bean
    public List<NewTopic> criarTopicos() {
        return Arrays.stream(topicos)
        		.map(nome -> new NewTopic(nome, 1, (short)1))
        		.toList();
    }

}
