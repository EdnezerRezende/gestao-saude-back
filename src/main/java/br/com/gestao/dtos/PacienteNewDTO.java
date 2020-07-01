package br.com.gestao.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteNewDTO {

	private String nome;
	
	private String dataNascimento;
	
	private String sexo;
	
	private Long numeroSES;
	
	private OrigemDTO origem;
	
	private List<ComorbidesDTO> comorbidades;


}
