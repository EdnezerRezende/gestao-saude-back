package br.com.gestao.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.gestao.entidades.Comorbidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ComorbidadeRepository implements PanacheRepository<Comorbidade> {

}
