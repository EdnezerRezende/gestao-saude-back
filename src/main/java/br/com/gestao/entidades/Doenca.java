package br.com.gestao.entidades;

import javax.persistence.Entity;

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
@EqualsAndHashCode(callSuper=false, exclude= {"nomeDoenca", "isDeletado"})
public class Doenca extends PanacheEntity {
	
	private String nomeDoenca;
	
	private Boolean isDeletado;
	
}
