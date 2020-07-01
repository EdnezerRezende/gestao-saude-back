package br.com.gestao.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.gestao.entidades.Leito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class LeitoRepository implements PanacheRepository<Leito> {

	public Boolean existeByNumero(Integer numeroLeito) {
		return find("numeroLeito = :numeroLeito and isDeletado = :isDeletado", Parameters.with("numeroLeito", numeroLeito).and("isDeletado", Boolean.FALSE)).firstResult() != null;
	};
}
