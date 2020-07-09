package br.com.gestao.resources;

import java.util.ArrayList;
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
import br.com.gestao.dtos.LeitoOcupacaoDTO;
import br.com.gestao.entidades.Leito;
import br.com.gestao.repositories.LeitoRepository;
import br.com.gestao.repositories.PacienteRepository;
import io.quarkus.panache.common.Parameters;

@Path("api/leitos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeitoResource {


	@Inject
	LeitoRepository leitoDAO;
	
	@Inject
	PacienteRepository pacienteRepository;
	
	@GET
	public List<Leito> buscarTodosLeitos(){
		return leitoDAO.list("isDeletado = :isDeletado", Parameters.with("isDeletado", Boolean.FALSE));
	}
	
	@GET
	@Path("/leitos-quantitativo")
	public List<Leito> buscarLeitoQuantitativo(){
		List<Leito> leitos = leitoDAO.list("isDeletado = :isDeletado order by numeroLeito", Parameters.with("isDeletado", Boolean.FALSE));
		return leitos;
	}
	
	@GET
	@Path("/leitos-ocupacao")
	public List<LeitoOcupacaoDTO> buscarLeitosPorOcupacao(){
		
		List<LeitoOcupacaoDTO> leitosOcupacao = new ArrayList<>();
		List<Leito> leitos = leitoDAO.list("isDeletado = :isDeletado order by numeroLeito", Parameters.with("isDeletado", Boolean.FALSE));
		leitos.stream().forEach(leito -> {
			LeitoOcupacaoDTO leitoOcupacao = new LeitoOcupacaoDTO();
			leitoOcupacao.setId(leito.id);
			leitoOcupacao.setAla(leito.getAla());
			leitoOcupacao.setNumeroLeito(leito.getNumeroLeito());
			leitoOcupacao.setSetor(leito.getSetor());

			if(leito.getEmUso()) {
				leitoOcupacao.setId(leito.getPaciente().id);
				leitoOcupacao.setNome(leito.getPaciente().getNome());
				leitoOcupacao.setNumeroSES(leito.getPaciente().getNumeroSES());
				leitoOcupacao.setSexo(leito.getPaciente().getSexo());
				leitoOcupacao.setTextoAlta(leito.getPaciente().getTextoAlta());
			}
			leitosOcupacao.add(leitoOcupacao);
		});
		return leitosOcupacao;
	}
	
	@GET
	@Path("/{leitoId}")
	public Leito buscarTodosLeitoPorId(@PathParam(value= "leitoId") Long leitoId ){
		return leitoDAO.findById(leitoId);
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
		
		Boolean existeByNumeroLeito = leitoDAO.existeByNumero(leitoDto.getNumeroLeito(), leitoDto.getAla(), leitoDto.getSetor());
		if(existeByNumeroLeito) {
			throw new RuntimeException("Leito já cadastrado");
		}
		
		Leito leito = new Leito();
		if(leitoDto.getId() != null) {
			leito = leitoDAO.findById(leitoDto.getId());
		}
		leito.setNumeroLeito(leitoDto.getNumeroLeito());
		leito.setAla(leitoDto.getAla());
		leito.setSetor(leitoDto.getSetor());
		leito.setIsDeletado(leito.getIsDeletado() == null ? Boolean.FALSE : leito.getIsDeletado());
		leito.setEmUso(leito.getEmUso() == null ? Boolean.FALSE : leito.getEmUso());
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
