package br.com.gestao.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.gestao.dtos.AltaPacienteDTO;
import br.com.gestao.dtos.PacienteNewDTO;
import br.com.gestao.dtos.dashBoardPacientesDTO;
import br.com.gestao.entidades.Comorbidade;
import br.com.gestao.entidades.Doenca;
import br.com.gestao.entidades.Leito;
import br.com.gestao.entidades.Origem;
import br.com.gestao.entidades.Paciente;
import br.com.gestao.repositories.ComorbidadeRepository;
import br.com.gestao.repositories.DoencaRepository;
import br.com.gestao.repositories.LeitoRepository;
import br.com.gestao.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class PacienteService {
	
	@Inject 
	LeitoRepository leitoRepository;
	
	@Inject
	DoencaRepository doencaRepository;
	
	@Inject
	PacienteRepository pacienteRepository;
	
	@Inject
	ComorbidadeRepository comorbidadeRepository;
	
	public List<Paciente> todosPacientesSemAlta(){
		return pacienteRepository.list("dataAlta is null");
	}
	
	public dashBoardPacientesDTO todosQuantidadePacientesPorSexo(){
		List<Paciente> pacientesTotais = pacienteRepository.list("dataAlta is null");
		
		dashBoardPacientesDTO dto = new dashBoardPacientesDTO();
		dto.setQuantidadeFeminino(pacientesTotais.stream().filter(paciente -> paciente.getSexo().equals("FEMININO")).count());
		dto.setQuantidadeMasculino(pacientesTotais.stream().filter(paciente -> paciente.getSexo().equals("MASCULINO")).count());
		dto.setQuantidadeTotal(pacientesTotais.stream().count());
		
		return dto;
	}
	
	public List<Paciente> todosPacientesSemAltaSemLeito(){
		List<Paciente> pacientesTotais = pacienteRepository.list("dataAlta is null");
		List<Paciente> pacientesRetorno = pacientesTotais.stream().filter(paciente -> paciente.getLeitos().size() == 0).collect(Collectors.toList());
		return pacientesRetorno;
	}
	
	public Paciente pacientePorId(Long idPaciente){
		return pacienteRepository.findById(idPaciente);
	}
	
	public List<Paciente> todosPacientesGeral(){
		return pacienteRepository.listAll();
	}
	
	public void salvarPaciente(PacienteNewDTO pacienteDTO) {
		Origem origem = new Origem();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		origem.setDataAdmissaoOrigem(LocalDateTime.parse(pacienteDTO.getOrigem().getDataAdmissaoOrigem(), formatter));
//		origem.setLocalOrigem(pacienteDTO.getOrigem().getLocalOrigem());
		origem.persist();
				
		Paciente paciente = new Paciente();
		paciente.setNome(pacienteDTO.getNome());
		DateTimeFormatter formatterDataNas = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		paciente.setDataNascimento(LocalDate.parse(pacienteDTO.getDataNascimento(), formatterDataNas));
		paciente.setOrigem(origem);
		Set<Comorbidade> comorbi = new HashSet<>();
		paciente.setComorbidades(comorbi);
		pacienteDTO.getComorbidades().forEach(comorbide -> {
			Doenca doenca = doencaRepository.findById(comorbide.getId());

			Comorbidade comorbidade = new Comorbidade();
			comorbidade.setDoenca(doenca);
			comorbidadeRepository.persist(comorbidade);
			comorbidade.setPaciente(paciente);
			paciente.getComorbidades().add(comorbidade);
		});
		
		paciente.setSexo(pacienteDTO.getSexo());
		paciente.setDataAdmissaoHCMG(LocalDateTime.now());
		paciente.setNumeroSES(pacienteDTO.getNumeroSES());
		pacienteRepository.persist(paciente);
		comorbidadeRepository.persist(paciente.getComorbidades());
	}
	
	public void associarLeito(Long idPaciente, Long idLeito) {
		Paciente paciente = pacienteRepository.findById(idPaciente);

		paciente.getLeitos().stream().forEach(leito-> {
			if(leito.getEmUso()) {
				leito.setEmUso(Boolean.FALSE);
				leito.setDataUtilizacaoFinal(LocalDateTime.now());
				leitoRepository.persist(leito);
			}
		});
		
		Leito leito = leitoRepository.findById(idLeito);
		leito.setEmUso(Boolean.TRUE);
		leito.setDataUtilizacaoInicial(LocalDateTime.now());
		leito.setPaciente(paciente);
		leitoRepository.persist(leito);
		
		paciente.getLeitos().add(leito);
		pacienteRepository.persist(paciente);
	}
	
	public void concederAlta(Long idPaciente, AltaPacienteDTO altaPacienteDTO) {
		Paciente paciente = pacienteRepository.findById(idPaciente);
		paciente.setDataAlta(LocalDateTime.now());
		paciente.setTextoAlta(altaPacienteDTO.getTextoAlta());
		
		paciente.getLeitos().stream().forEach(leito -> {
			if (leito.getEmUso()) {
				leito.setEmUso(Boolean.FALSE);
				leito.setDataUtilizacaoFinal(LocalDateTime.now());
			}
		});
		
		pacienteRepository.persist(paciente);
		
	}

}
