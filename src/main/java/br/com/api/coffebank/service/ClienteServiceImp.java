package br.com.api.coffebank.service;

import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;
import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.mapper.ClienteMapper;
import br.com.api.coffebank.repository.ClienteRepository;
import br.com.api.coffebank.service.validador.ClienteValidador;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteServiceImp implements ClienteService{
	
	private final ClienteValidador clienteValidador;
	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;
	
	@Transactional
	@Override
	public RespostaClienteDto criarCliente(CriarClienteDto dto) {
		clienteValidador.validaSeCpfJaExiste(dto.dadosPessoais().cpf());
		Cliente cliente = clienteMapper.toEntity(dto);
		clienteRepository.save(cliente);
		return clienteMapper.toDto(cliente);
	}

	@Override
	public RespostaClienteDto buscarClientePeloCodigo(Long codigo) {
		return clienteMapper.toDto(clienteValidador.validaSeOCodigoDoClienteExiste(codigo));
	}

}
