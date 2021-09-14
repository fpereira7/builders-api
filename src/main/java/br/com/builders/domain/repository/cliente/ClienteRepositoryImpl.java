package br.com.builders.domain.repository.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.repository.filter.ClienteFilter;

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);

		// restricoes
		Predicate[] predicates = criarRestricoes(clienteFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Cliente> query = manager.createQuery(criteria);

		adicionarRestricoesPaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(clienteFilter));
	}

	private Long total(ClienteFilter clienteFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cliente> root = criteria.from(Cliente.class);

		Predicate[] predicates = criarRestricoes(clienteFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesPaginacao(TypedQuery<Cliente> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Predicate[] criarRestricoes(ClienteFilter clienteFilter, CriteriaBuilder builder, Root<Cliente> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.hasText(clienteFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + clienteFilter.getNome() + "%"));
		}

		if (clienteFilter.getDataNascimento() != null) {
			predicates.add(builder.equal(root.get("dataNascimento"), clienteFilter.getDataNascimento()));
		}

		if (clienteFilter.getStatus() != null) {
			predicates.add(builder.equal(builder.upper(root.get("status")), clienteFilter.getStatus()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
