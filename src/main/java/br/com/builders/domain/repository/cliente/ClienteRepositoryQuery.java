package br.com.builders.domain.repository.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {

	public Page<Cliente> filtrar(ClienteFilter clientFilter, Pageable pageable);
}
