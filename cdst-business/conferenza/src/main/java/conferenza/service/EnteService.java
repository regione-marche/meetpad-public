package conferenza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.EnteRicercaGeograficaDTO;
import conferenza.DTO.EnteRicercaTipologicaDTO;
import conferenza.DTO.ListaEnteDTO;
import conferenza.DTO.bean.Errore;
import conferenza.builder.EnteBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.builder.UtenteBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.model.Ente;
import conferenza.model.EnteTipologiaAmministrativa;
import conferenza.model.EnteTipologiaIstat;
import conferenza.model.Provincia;
import conferenza.model.Regione;
import conferenza.repository.ComuneRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EnteTipologiaAmministrativaRepository;
import conferenza.repository.EnteTipologiaIstatRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegioneRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.SearchCriteria;
import conferenza.repository.SpecificationBuilderEnte;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.UtenteRepository;
import conferenza.validator.EnteValidator;

@Transactional
@Service
public class EnteService extends _BaseService{
	
	@Autowired
	EnteBuilder enteBuilder;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	RegioneRepository regioneRepo;
	
	@Autowired
	ProvinciaRepository provinciaRepo;
	
	@Autowired
	ComuneRepository comuneRepo;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	EnteValidator enteValidator;
	
	@Autowired
	EnteTipologiaIstatRepository enteTipoIstatRepo;
	
	@Autowired
	EnteTipologiaAmministrativaRepository enteTipoAmmRepo;
	
	@Autowired
	ProfiloRepository profiloRepo;
	
	@Autowired
	TipoProfiloRepository tipoProfiloRepo;
	
	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	ConferenzaRepository confRepo;
	
	@Autowired
	ResponsabileConferenzaRepository respConfRepo;
	
	@Autowired
	PersonaRepository personaRepo;
	
	@Autowired
	PartecipanteBuilder partBuilder;
		
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	UtenteBuilder utenteBuilder;

	public ListaEnteDTO findEnteByRicercaGeografica(EnteRicercaGeograficaDTO ricerca) {
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()), ricerca.getColonnaOrdinamento());
		Iterable<Ente> itEnte = enteRepo.findAll(filtroRicerca(ricerca, null), page);
		ListaEnteDTO lista = new ListaEnteDTO();
		for(Ente ente: itEnte) {
			lista.getList().add(enteBuilder.enteToEnteDTO(ente));
		}
		lista.setTotalNumber(enteRepo.count(filtroRicerca(ricerca, null)));
		return lista;
	}

	public Specification<Ente> filtroRicerca(EnteRicercaGeograficaDTO ricercaGeo,
			EnteRicercaTipologicaDTO ricercaTipo) {
		List<SearchCriteria> parametriRicerca = fillParametriRicerca(ricercaGeo, ricercaTipo);
		SpecificationBuilderEnte builder = new SpecificationBuilderEnte();
		return builder.buildAnd(parametriRicerca);
	}

	public List<SearchCriteria> fillParametriRicerca(EnteRicercaGeograficaDTO ricercaGeo,
			EnteRicercaTipologicaDTO ricercaTipo) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricercaGeo != null) {
			List<Errore> errors = enteValidator.validateEnteRicercaGeografica(ricercaGeo, messageSource);
			if (errors.size() != 0) {
				throw new InvalidFieldEx(errors);
			}
			parametriRicerca.add(new SearchCriteria("descrizioneEnte", "%", ricercaGeo.getValue()));
			Regione regione = regioneRepo.findById(ricercaGeo.getRegione()).orElse(null);
			parametriRicerca.add(new SearchCriteria("regione", "=", regione));
			if (ricercaGeo.getProvincia() != null && !ricercaGeo.getProvincia().isEmpty()) {
				Provincia provincia = provinciaRepo.findById(ricercaGeo.getProvincia()).orElse(null);
				parametriRicerca.add(new SearchCriteria("provincia", "=", provincia));
			}
			/*if (ricercaGeo.getComune() != null && !ricercaGeo.getComune().isEmpty()) {
				Comune comune = comuneRepo.findById(ricercaGeo.getComune()).orElse(null);
				parametriRicerca.add(new SearchCriteria("comune", "=", comune));
			}*/
			if (ricercaGeo.getComune() != null && !ricercaGeo.getComune().isEmpty()) {
				parametriRicerca.add(new SearchCriteria("descrizioneComune", "%", ricercaGeo.getComune()));
			}
		}
		if (ricercaTipo != null) {
			if (ricercaTipo.getValue() != null) {
				parametriRicerca.add(new SearchCriteria("descrizioneEnte", "%", ricercaTipo.getValue()));
			}
			if (ricercaTipo.getTipologiaIstat() != null) {
				EnteTipologiaIstat tipoIstat = enteTipoIstatRepo.findById(ricercaTipo.getTipologiaIstat()).orElse(null);
				parametriRicerca.add(new SearchCriteria("enteTipoIstat", "=", tipoIstat));
			}
			if (ricercaTipo.getTipologiaAmm() != null) {
				EnteTipologiaAmministrativa tipoAmm = enteTipoAmmRepo.findById(ricercaTipo.getTipologiaAmm()).orElse(null);
				parametriRicerca.add(new SearchCriteria("enteTipoAmm", "=", tipoAmm));
			}
		}
		return parametriRicerca;
	}

	public ListaEnteDTO findEnteByRicercaTipologica(EnteRicercaTipologicaDTO ricerca) {
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()), ricerca.getColonnaOrdinamento());
		Iterable<Ente> itEnte = enteRepo.findAll(filtroRicerca(null, ricerca), page);
		ListaEnteDTO lista = new ListaEnteDTO();
		for(Ente ente: itEnte) {
			lista.getList().add(enteBuilder.enteToEnteDTO(ente));
		}
		lista.setTotalNumber(enteRepo.count(filtroRicerca(null, ricerca)));
		return lista;
	}
}
