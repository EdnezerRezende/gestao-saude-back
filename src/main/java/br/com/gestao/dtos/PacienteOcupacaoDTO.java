package br.com.gestao.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteOcupacaoDTO {
	
	private Long id;

	private String nome;
	
	private String dataNascimento;
	
	private String sexo;
	
	private Long numeroSES;
	
	private String textoAlta;

}
