package br.com.api.coffebank.service;

import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.mapper.ClienteMapper;
import br.com.api.coffebank.repository.ClienteRepository;
import br.com.api.coffebank.service.event.producer.ClienteProducer;
import br.com.api.coffebank.service.validador.ClienteValidador;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteServiceImp implements ClienteService{
	
	private final ClienteValidador clienteValidador;
	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;
	private final ClienteProducer clienteProducer;
	
	@Transactional
	@Override
	public RespostaClienteDto criarCliente(RequisicaoClienteDto dto) {
		clienteValidador.validaSeCpfJaExiste(dto.dadosPessoais().cpf());
		Cliente cliente = clienteMapper.toEntity(dto);
		clienteRepository.save(cliente);
		clienteProducer.enviarClienteCriadoKafka(cliente.getCodigoCliente());
		return clienteMapper.toDto(cliente);
	}

	@Override
	public RespostaClienteDto buscarClientePeloCodigo(Long codigo) {
		return clienteMapper.toDto(clienteValidador.validaSeOCodigoDoClienteExiste(codigo));
	}

	@Transactional
	@Override
	public void deletaClientePeloCodigo(Long codigo) {
		clienteValidador.validaSeOCodigoExisteMasNaoRetornaEntity(codigo);
		clienteRepository.deleteById(codigo);
		clienteProducer.enviarClienteDeletadoKafka(codigo);
	}

	@Transactional
	@Override
	public RespostaClienteDto atualizaClientePeloCodigo(RequisicaoClienteDto dto, Long codigo) {
		Cliente clienteEntity = clienteValidador.validaSeOCodigoDoClienteExiste(codigo);
		clienteValidador.validaCpfUrlIgualAoCorpo(clienteEntity.getDadosPessoais().getCpf(), dto.dadosPessoais().cpf());
		clienteMapper.atualizaDto(dto, clienteEntity);
		return clienteMapper.toDto(clienteEntity);
	}

}
