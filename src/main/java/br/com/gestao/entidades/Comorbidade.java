package br.com.gestao.entidades;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(callSuper=false, exclude= {"doenca", "paciente"})
public class Comorbidade extends PanacheEntity{
	
	@OneToOne(cascade=CascadeType.DETACH)
	private Doenca doenca;

	@ManyToOne
	@JsonbTransient
	private Paciente paciente;

}
