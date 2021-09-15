package br.com.builders.domain.model;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.builders.domain.StatusCliente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class Cliente {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;
	
	@NotNull
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusCliente status;
	
	@Transient
	private Integer idade;
	
	public int getIdade() {
		Period periodo = Period.between(LocalDate.now(), this.dataNascimento);
		int calculoIdade = Math.abs(periodo.getYears());
		
		return calculoIdade;
	}
	
}
