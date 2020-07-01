package br.com.gestao.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.gestao.entidades.Doenca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class DoencaRepository implements PanacheRepository<Doenca> {

	public Boolean existeByNome(String nomeDoenca) {
		return find("nomeDoenca = :nomeDoenca and isDeletado = :isDeletado", Parameters.with("nomeDoenca", nomeDoenca).and("isDeletado", Boolean.FALSE)).firstResult() != null;
	};
}
