package br.com.builders.domain.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.builders.domain.StatusCliente;
import lombok.Data;

@Data
public class ClienteFilter {

	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	private StatusCliente status;
	
}
