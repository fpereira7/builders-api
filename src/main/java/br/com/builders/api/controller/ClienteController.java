package br.com.builders.api.controller;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.builders.domain.exception.EntidadeNaoEncontradaException;
import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.ClienteRepository;
import br.com.builders.domain.repository.filter.ClienteFilter;
import br.com.builders.domain.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public Page<Cliente> buscarTodos(ClienteFilter clienteFilter, Pageable pageable) {
		return clienteRepository.filtrar(clienteFilter, pageable);
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public Cliente salvar(@RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<?> excluir(@PathVariable Long clienteId) {
		try {
			clienteService.excluir(clienteId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@RequestBody Cliente cliente, @PathVariable Long clienteId) {
		Cliente clienteAtual = clienteRepository.findById(clienteId).orElse(null);

		if (clienteAtual != null) {
			BeanUtils.copyProperties(cliente, clienteAtual, "id");

			Cliente clienteSalvo = clienteRepository.save(clienteAtual);
			return ResponseEntity.ok(clienteSalvo);
		}

		return ResponseEntity.notFound().build();

	}

	@PatchMapping("/{clienteId}")
	public ResponseEntity<?> atualizacaoGranular(@PathVariable Long clienteId,
			@RequestBody Map<String, Object> atributos) {
		Cliente clienteAtual = clienteRepository.findById(clienteId).orElse(null);

		if (clienteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		merge(atributos, clienteAtual);

		return atualizar(clienteAtual, clienteId);
	}

	private void merge(Map<String, Object> atributos, Cliente clienteAtual) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		// Registro de modulo para o mapper conseguir trabalhar com localdate
		objectMapper.registerModule(new JavaTimeModule());

		Cliente clienteAtributos = objectMapper.convertValue(atributos, Cliente.class);

		atributos.forEach((chave, valor) -> {
			Field field = ReflectionUtils.findField(Cliente.class, chave);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, clienteAtributos);

			ReflectionUtils.setField(field, clienteAtual, novoValor);
		});

	}
}
