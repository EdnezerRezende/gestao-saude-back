package br.com.gestao.dtos;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeitoQuantitativoDTO {
	
	private Map<Integer, Boolean> numeroLeitos;
	
	private String ala;
	
	private String setor; 
	
	private Long quantidadeTotal;
	
	private Long quantidadeLiberado;
	
	private Long quantidadeOcupado;

}
