package br.com.gestao.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.gestao.dtos.LeitoNewDTO;
import br.com.gestao.entidades.Leito;
import br.com.gestao.repositories.LeitoRepository;
import io.quarkus.panache.common.Parameters;

@Path("api/leitos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeitoResource {


	@Inject
	LeitoRepository leitoDAO;
	
	@GET
	public List<Leito> buscarTodosLeitos(){
		return leitoDAO.list("isDeletado = :isDeletado", Parameters.with("isDeletado", Boolean.FALSE));
	}
	
	
	@GET
	@Path("/disponiveis")
	public List<Leito> buscarTodosLeitosDisponiveis(){
		return leitoDAO.list("isDeletado = :isDeletado and emUso = :emUso", Parameters.with("isDeletado", Boolean.FALSE).and("emUso", Boolean.FALSE));
	}
	
	
	@GET
	@Path("/disponiveis/{sexo}")
	public List<Leito> buscarTodosLeitosDisponiveis(@PathParam(value = "sexo") String sexo){
		return leitoDAO.list("isDeletado = :isDeletado and emUso = :emUso and setor = :sexo", Parameters.with("isDeletado", Boolean.FALSE).and("emUso", Boolean.FALSE).and("sexo", sexo));
	}
	
	@POST
	@Transactional
	public void salvarLeito(LeitoNewDTO leitoDto){
		
		Boolean existeByNumeroLeito = leitoDAO.existeByNumero(leitoDto.getNumeroLeito());
		if(existeByNumeroLeito) {
			throw new RuntimeException("Leito já cadastrado");
		}
		Leito leito = new Leito();
		leito.setNumeroLeito(leitoDto.getNumeroLeito());
		leito.setAla(leitoDto.getAla());
		leito.setSetor(leitoDto.getSetor());
		leito.setIsDeletado(Boolean.FALSE);
		leito.setEmUso(Boolean.FALSE);
		leitoDAO.persist(leito);
	}
	
	@DELETE
	@Transactional
	@Path("/{idLeito}")
	public void deleteLeito(@PathParam(value = "idLeito") Long idLeito) {
		Leito leito = leitoDAO.findById(idLeito);
		leito.setIsDeletado(Boolean.TRUE);
		leitoDAO.persist(leito);
	}
}
