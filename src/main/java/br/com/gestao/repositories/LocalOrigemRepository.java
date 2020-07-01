package br.com.gestao.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.gestao.entidades.LocalOrigem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class LocalOrigemRepository implements PanacheRepository<LocalOrigem> {

	public Boolean existeByNomeOrigem(String nomeOrigem) {
		return find("nomeOrigem = :nomeOrigem and isDeletado = :isDeletado", Parameters.with("nomeOrigem", nomeOrigem).and("isDeletado", Boolean.FALSE)).firstResult() != null;
	};
}
