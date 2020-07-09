package br.com.gestao.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeitoOcupacaoDTO {
	
	private Long id;
	
	private Integer numeroLeito;
	
	private String ala;
	
	private String setor; 
	
	private Long idPaciente;

	private String nome;
	
	private String sexo;
	
	private Long numeroSES;
	
	private String textoAlta;
	
}
