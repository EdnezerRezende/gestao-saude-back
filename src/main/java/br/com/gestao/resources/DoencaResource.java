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

import br.com.gestao.dtos.DoencaNewDTO;
import br.com.gestao.entidades.Doenca;
import br.com.gestao.repositories.DoencaRepository;

@Path("api/doencas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoencaResource {


	@Inject
	DoencaRepository doencaDAO;
	
	@GET
	public List<Doenca> buscarTodasDoencas(){
		return doencaDAO.list("isDeletado", Boolean.FALSE);
	}
	
	@POST
	@Transactional
	public void salvarDoenca(DoencaNewDTO doencaDto){
		Doenca doenca = new Doenca();
		Boolean existeByNome = doencaDAO.existeByNome(doencaDto.getNomeDoenca());
		if(existeByNome) {
			throw new RuntimeException("Doença já cadastrada");
		}
		doenca.setNomeDoenca(doencaDto.getNomeDoenca());
		doenca.setIsDeletado(Boolean.FALSE);
		doencaDAO.persist(doenca);
	}
	
	@DELETE
	@Transactional
	@Path("/{idDoenca}")
	public void deleteDoenca(@PathParam(value = "idDoenca") Long idDoenca) {
		Doenca doenca = doencaDAO.findById(idDoenca);
		doenca.setIsDeletado(Boolean.TRUE);
		doencaDAO.persist(doenca);
	}
}
