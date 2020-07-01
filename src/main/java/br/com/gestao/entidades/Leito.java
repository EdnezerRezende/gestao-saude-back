package br.com.gestao.entidades;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false, exclude= {"numeroLeito", "isDeletado", "emUso", "paciente", "ala", "setor"})
public class Leito extends PanacheEntity {
	
	private Integer numeroLeito;
	
	private String ala;
	
	private String setor; 
	
	private Boolean isDeletado;
	
	private Boolean emUso;
	
	private LocalDateTime dataUtilizacaoInicial;
	
	private LocalDateTime dataUtilizacaoFinal;
	
	@ManyToOne
	@JsonbTransient
	private Paciente paciente;
}
