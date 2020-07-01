package br.com.gestao.entidades;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
@EqualsAndHashCode(callSuper=false, exclude= {"nomeOrigem", "isDeletado", "origem"})
public class LocalOrigem extends PanacheEntity {
	
	private String nomeOrigem;
	
	private Boolean isDeletado;
	
	@OneToOne
	private Origem origem;
	
	
}
