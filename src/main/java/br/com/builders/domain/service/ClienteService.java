package br.com.builders.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.builders.domain.StatusCliente;
import br.com.builders.domain.exception.EntidadeNaoEncontradaException;
import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		cliente.setStatus(StatusCliente.ATIVO);
		return clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId) {
		try {
			clienteRepository.deleteById(clienteId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cliente cadastrado com o código %d", clienteId));
		}
	}
}
