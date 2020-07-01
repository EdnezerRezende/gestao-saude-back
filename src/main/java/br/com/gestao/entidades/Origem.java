package br.com.gestao.entidades;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@EqualsAndHashCode(callSuper=false, exclude= {"localOrigem", "dataAdmissaoOrigem", "paciente"})
public class Origem extends PanacheEntity {

	@OneToOne(cascade=CascadeType.DETACH)
	private LocalOrigem localOrigem;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime dataAdmissaoOrigem;
	
	@OneToOne
	private Paciente paciente;

}
