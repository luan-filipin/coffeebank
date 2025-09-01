package br.com.api.coffebank.service.event.producer;

public interface ClienteProducer {

	void enviarClienteCriadoKafka(Long codigoCliente);
	void enviarClienteDeletadoKafka(Long codigoCliente);
}
