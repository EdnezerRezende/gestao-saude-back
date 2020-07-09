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

import br.com.gestao.dtos.LocalOrigemNewDTO;
import br.com.gestao.entidades.LocalOrigem;
import br.com.gestao.repositories.LocalOrigemRepository;
import br.com.gestao.services.PacienteService;

@Path("api/local-origem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocalOrigemResource {

	@Inject
	PacienteService pacienteService;
	
	@Inject
	LocalOrigemRepository localOrigemDAO;
	
	@GET
	public List<LocalOrigem> buscarTodasOrigens(){
		return localOrigemDAO.list("isDeletado", Boolean.FALSE);
	}
	
	@GET
	@Path("/{localOrigemId}")
	public LocalOrigem buscarPorId(@PathParam(value="localOrigemId") Long localOrigemId){
		return localOrigemDAO.findById(localOrigemId);
	}
	
	@POST
	@Transactional
	public void salvarLocalOrigem(LocalOrigemNewDTO localDto){
		Boolean existeByNome = localOrigemDAO.existeByNomeOrigem(localDto.getNomeOrigem());
		if(existeByNome) {
			throw new RuntimeException("Local de Origem já cadastrada");
		}
		
		LocalOrigem local = new LocalOrigem();
		local.setNomeOrigem(localDto.getNomeOrigem());
		local.setIsDeletado(Boolean.FALSE);
		localOrigemDAO.persist(local);
	}
	
	@DELETE
	@Transactional
	@Path("/{idLocalOrigem}")
	public void deleteLocalOrigem(@PathParam(value = "idLocalOrigem") Long idLocalOrigem) {
		LocalOrigem local = localOrigemDAO.findById(idLocalOrigem);
		local.setIsDeletado(Boolean.TRUE);
		localOrigemDAO.persist(local);
	}
}
