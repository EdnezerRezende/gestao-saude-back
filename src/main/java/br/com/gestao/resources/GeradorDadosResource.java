package br.com.gestao.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.gestao.entidades.Doenca;
import br.com.gestao.entidades.Leito;
import br.com.gestao.entidades.LocalOrigem;
import br.com.gestao.repositories.DoencaRepository;
import br.com.gestao.repositories.LeitoRepository;
import br.com.gestao.repositories.LocalOrigemRepository;

@Path("api/gera-dados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GeradorDadosResource {


	@Inject
	LeitoRepository leitoDAO;
	
	@Inject
	LocalOrigemRepository localOrigemRepository;
	
	@Inject
	DoencaRepository doencaRepository;
	
	@GET
	@Transactional
	@Path("/leitos")
	public void gerarDadosLeito(){
		List<Leito> leitos = new ArrayList<>();
		int[] femininoA = {28, 29, 30, 31, 32, 33, 34, 35, 36, 42, 43, 44, 45, 46, 54, 55, 56, 57, 58, 59, 60, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87};
		int[] femininoB = {15,16,17,18,19,20,21,22,31,32,33,34,35,36,45,46,47,48,49,50,59,60,61,62,63,64,75,76,77,78,79,80,81,82,83,84,85};
		int[] masculinoA = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,37,38,39,40,41,47,48,49,50,51,52,53,61,62,63,64,65,66,67};
		int[] masculinoB = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,23,24,25,26,27,28,29,30,37,38,39,40,41,42,43,44,51,52,53,54,55,56,57,58,65,66,67,68,69,70,71,72,73,74};
		for (int femininoA2 : femininoA) {
			Leito leito = geraLeito(femininoA2, "A", "FEMININO");
			leitos.add(leito);
		}
		for (int femininoB2:femininoB) {
			Leito leito = geraLeito(femininoB2, "B", "FEMININO");
			leitos.add(leito);
		}
		for (int masculinoA2:masculinoA) {
			Leito leito = geraLeito(masculinoA2, "A", "MASCULINO");
			leitos.add(leito);
		}
		for (int masculinoB2:masculinoB) {
			Leito leito = geraLeito(masculinoB2, "B", "MASCULINO");
			leitos.add(leito);
		}
		leitoDAO.persist(leitos);
	}

	private Leito geraLeito(int numeroLeito, String ala, String setor) {
		Leito leito = new Leito();
		leito.setAla(ala);
		leito.setSetor(setor);
		leito.setEmUso(Boolean.FALSE);
		leito.setIsDeletado(Boolean.FALSE);
		leito.setNumeroLeito(numeroLeito);
		return leito;
	}
	
	@GET
	@Path("/origens")
	@Transactional
	public void geraDadosOrigens() {
		List<LocalOrigem> locaisorigens = new ArrayList<>();
		String[] origens = {"HRAN", "HRC", "HRSM", "UPA NB", "UPA SAM"};
		
		for (String nome:origens) {
			LocalOrigem local = geraLocaisOrigem(nome);
			locaisorigens.add(local);
		}
		
		localOrigemRepository.persist(locaisorigens);
	}
	
	private LocalOrigem geraLocaisOrigem(String nomeLocal){
		LocalOrigem local = new LocalOrigem();
		local.setIsDeletado(Boolean.FALSE);
		local.setNomeOrigem(nomeLocal);
		return local;
	}
	
	@GET
	@Transactional
	@Path("/doencas")
	public void geraDadosDoencas() {
		List<Doenca> doencas = new ArrayList<>();
		String[] doe = {"ASMA", "STENT CORONÁRIO", "DM", "HAS", "TABAGISMO", "NEGA", "DPOC", "NEOPLASIA C. UTERINO", "OBESIDADE", "TIROIDISMO", "ICC", "FIBROMIALGIA", "HIPOTIROIDISMO", 
		                    "TAGISTA ETILISTA", "CHAGAS", "DISLIPIDEMIA", "MIOCARDIOPATIA", "DEPRESSÃO", "GOTA", "HANSENIASE EM TRATAMENTO", "HEPATOPATIAS ALCOOLICA", "ETILISMO",
		                    "DM/2", "EX TABAGISTA", "TEP", "DLP", "ARRITMIA", "ENFISEMA", "OSTEOPOROSE", "IC DESCOMP", "ETILISTA", "DEP. QUÍMICO", "DEF. VISUAL", "HEPATITE B", "CARDIOPATIA",
		                    "ALZHEIMER", "DPOC ICC" };
		for (String nomeDoenca: doe) {
			Doenca doenca = new Doenca();
			doenca.setIsDeletado(Boolean.FALSE);
			doenca.setNomeDoenca(nomeDoenca);
			doencas.add(doenca);
		}
		doencaRepository.persist(doencas);
	}
}
