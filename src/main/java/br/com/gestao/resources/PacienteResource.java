package br.com.gestao.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.gestao.dtos.AltaPacienteDTO;
import br.com.gestao.dtos.PacienteNewDTO;
import br.com.gestao.dtos.dashBoardPacientesDTO;
import br.com.gestao.entidades.Paciente;
import br.com.gestao.services.PacienteService;

@Path("api/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

	@Inject
	PacienteService pacienteService;
	
	@GET
	public List<Paciente> buscarTodosPacientesAtivos(){
		return pacienteService.todosPacientesSemAlta();
	}
	
	@GET
	@Path("/dashBoard/por-sexo")
	public dashBoardPacientesDTO buscarPacientesPorSexo(){
		return pacienteService.todosQuantidadePacientesPorSexo();
	}
	
	@GET
	@Path("/sem-leito")
	public List<Paciente> buscarTodosPacientesAtivosSemLeito(){
		return pacienteService.todosPacientesSemAltaSemLeito();
	}
	
	
	@GET
	@Path("/{idPaciente}")
	public Paciente pacientePorID(@PathParam(value = "idPaciente") Long idPaciente){
		return pacienteService.pacientePorId(idPaciente);
	}
	
	@GET
	@Path("/geral")
	public List<Paciente> buscarTodosPacientesGerais(){
		return pacienteService.todosPacientesGeral();
	}
	
	@POST
	@Transactional
	public void salvarPaciente(PacienteNewDTO pacienteDTO){
		pacienteService.salvarPaciente(pacienteDTO);
		
	}
	
	@PUT
	@Transactional
	@Path("/alta/{idPaciente}")
	public void alta(@PathParam(value = "idPaciente") Long idPaciente, AltaPacienteDTO altaPacienteDTO){
		pacienteService.concederAlta(idPaciente, altaPacienteDTO);
		
	}
	
	@PUT
	@Transactional
	@Path("associar-leito/{idPaciente}/{idLeito}")
	public void associarLeito(@PathParam(value = "idPaciente") Long idPaciente, @PathParam(value = "idLeito") Long idLeito) {
		pacienteService.associarLeito(idPaciente, idLeito);
	}
}
