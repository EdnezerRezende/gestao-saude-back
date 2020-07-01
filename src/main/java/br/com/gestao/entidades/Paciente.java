package br.com.gestao.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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
@EqualsAndHashCode(callSuper=false, exclude= {"nome", "dataNascimento", "sexo", "leitos", "origem", "comorbidades", "dataAdmissaoHCMG", "dataAlta", "numeroSES"})
public class Paciente extends PanacheEntity{
	
	private String nome;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	private String sexo;
	
	@OneToMany(mappedBy = "paciente", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Leito> leitos = new HashSet<>();
	
	//private Telefone telefone;
	
	@OneToOne(cascade=CascadeType.DETACH)
	private Origem origem;

	@OneToMany(mappedBy = "paciente", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Comorbidade> comorbidades = new HashSet<>();
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime dataAdmissaoHCMG;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime dataAlta;
	
	private Long numeroSES;
	
	@Lob @Basic(fetch=FetchType.LAZY)
	private String textoAlta;
		
}
