package br.com.gestao.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.gestao.entidades.Paciente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {

	public Boolean existeByNome(String nomePaciente) {
		return find("nome = :nomePaciente ", Parameters.with("nomePaciente", nomePaciente)).firstResult() != null;
	};
	
	
}
