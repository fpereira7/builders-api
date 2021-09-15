package br.com.builders;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.builders.domain.StatusCliente;
import br.com.builders.domain.exception.EntidadeNaoEncontradaException;
import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import br.com.builders.domain.service.ClienteService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClienteIntegracaoTeste {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;

	@Test
	public void deveCadastrarClienteComSucesso() {
		Cliente cliente = new Cliente();
		cliente.setId(999L);
		cliente.setNome("joao da silva");
		cliente.setDataNascimento(LocalDate.now());
		cliente.setStatus(StatusCliente.ATIVO);

		cliente = clienteRepository.save(cliente);

		assertThat(cliente).isNotNull();
		assertThat(cliente.getId()).isNotNull();
	}

	@Test
	public void deveFalharAoCadastrarClienteSemNome() {
		Cliente cliente = new Cliente();
		cliente.setNome(null);
		
		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			clienteRepository.save(cliente);
		});

		assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	public void deveFalharAoTentarExcluirClienteInexistente() {
		EmptyResultDataAccessException erroEsperado = Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			clienteRepository.deleteById(999L);
		}); 
		
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharAoAtualizarClienteInexistente() {
		EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> {
			clienteService.excluir(100L);
		});
		
		assertThat(erroEsperado).isNotNull();
	}
	
}
