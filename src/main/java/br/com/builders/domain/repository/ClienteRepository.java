package br.com.builders.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.cliente.ClienteRepositoryQuery;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{

}
