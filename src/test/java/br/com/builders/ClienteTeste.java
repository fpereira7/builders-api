package br.com.builders;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.builders.domain.StatusCliente;
import br.com.builders.domain.model.Cliente;

@SpringBootTest
public class ClienteTeste {

	@Test
	public void deveFalharSeClienteNaoInformarDataNascimento() {
		Cliente cliente = new Cliente(999L, "Joao", null, StatusCliente.ATIVO, null);

		assertThat(cliente.getDataNascimento()).isNull();
	}
	
	@Test
	public void idadeDeveSerIgualADezAnos() {
		LocalDate dataAtualFixa = LocalDate.of(2021, 9, 10);
		LocalDate dataNascimento = LocalDate.of(2011, 9, 10);
		
		Cliente cliente = new Cliente(999L, "Joao", dataNascimento , StatusCliente.ATIVO, null);
		
		Period periodo = Period.between(dataAtualFixa, dataNascimento);
		int calculoIdade = Math.abs(periodo.getYears());
		
		assertThat(cliente.getIdade()).isNotNull();
		assertThat(calculoIdade).isEqualTo(10);
		
	}
	
	@Test
	public void deveCalcularIdadeComSucessoApartirDaDataDeNascimento() {
		LocalDate dataNascimento = LocalDate.of(2011, 9, 10);
		Cliente cliente = new Cliente(999L, "Joao", dataNascimento , StatusCliente.ATIVO, null);
		
		assertThat(cliente.getIdade()).isNotNull();
	}
	
	@Test
	public void deveFalharSeDataNascimentoMaiorQueDataAtual() {
		LocalDate dataAtualFixa = LocalDate.of(2011, 9, 10);
		LocalDate dataNascimento = LocalDate.of(2021, 9, 10);
		
		int calculoIdade = dataAtualFixa.getYear() - dataNascimento.getYear();
		
		assertThat(calculoIdade).isLessThan(0);
		
	}
	
}
