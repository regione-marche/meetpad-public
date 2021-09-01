package conferenza.consolleAmministrativa.builder;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.ImpresaDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.builder.ConferenzaBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.consolleAmministrativa.DTO.ConferenzaConsolleDTO;
import conferenza.consolleAmministrativa.DTO.ImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatiPreviewDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoPreviewDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaCompletaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipantePreviewDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedenteDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.ResponsabileConfCompletoDTO;
import conferenza.consolleAmministrativa.DTO.ResponsabileConfPreviewDTO;
import conferenza.consolleAmministrativa.model.RicercaResponsabileConferenza;
import conferenza.consolleAmministrativa.model.RicercaRubricaDelegati;
import conferenza.consolleAmministrativa.model.RicercaRubricaImprese;
import conferenza.consolleAmministrativa.model.RicercaRubricaPartecipanti;
import conferenza.consolleAmministrativa.model.RicercaRubricaPreaccreditati;
import conferenza.consolleAmministrativa.model.RicercaRubricaRichiedenti;
import conferenza.exception.NotFoundEx;
import conferenza.model.CompetenzaAutorizzativa;
import conferenza.model.Comune;
import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Impresa;
import conferenza.model.Persona;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RubricaDelegati;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.RubricaPreaccreditati;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.Utente;
import conferenza.model.bean._Typological;
import conferenza.repository.ComuneRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EnteTipologiaAmministrativaRepository;
import conferenza.repository.EnteTipologiaIstatRepository;
import conferenza.repository.FormaGiuridicaRepository;
import conferenza.repository.ImpresaRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegioneRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.UtenteRepository;

@Component
public class ConsolleAmministrativaBuilder {
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	PersonaRepository personaRepo;
	
	@Autowired
	PartecipanteBuilder partBuilder;
	
	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;
	
	@Autowired
	ConferenzaBuilder confBuilder;
	
	@Autowired
	ResponsabileConferenzaRepository respConfRepo;
	
	@Autowired
	ImpresaRepository impresaRepo;
	
	@Autowired
	FormaGiuridicaRepository formaGiuridicaRepo;
	
	@Autowired
	RegioneRepository regioneRepo;
	
	@Autowired
	ProvinciaRepository provRepo;
	
	@Autowired
	ComuneRepository comuneRepo;
	
	@Autowired
	EnteTipologiaAmministrativaRepository enteTipoAmmRepo;
	
	@Autowired
	EnteTipologiaIstatRepository enteTipoIstatRepo;
	
	public LabelValue createNotNullLabelValue(_Typological typological) {
		if (typological != null) {
			String descrizione = messageSource.getMessage(typological.getDescrizione(), null,
					typological.getDescrizione(), request.getLocale());
			return new LabelValue(typological.getCodice(), descrizione);
		}
		return null;
	}
	
	public ResponsabileConfPreviewDTO ricercaRespConferenzaToRespConfPreview(RicercaResponsabileConferenza ricercaResp) {
		ResponsabileConfPreviewDTO respConfDTO = new ResponsabileConfPreviewDTO();
		respConfDTO.setAmministrazioneProcedente(ricercaResp.getAmmProcedente());
		respConfDTO.setNome(ricercaResp.getNomeResponsabile());
		respConfDTO.setCognome(ricercaResp.getCognomeResponsabile());
		respConfDTO.setCodiceFiscale(ricercaResp.getCodiceFiscaleResponsabile());
		respConfDTO.setIdPersona(ricercaResp.getIdPersonaResponsabile());
		return respConfDTO;
	}

	public String mapColonnaOrdinamentoRicercaRespConferenza(String colonnaOrdinamento) {
		String colonna = colonnaOrdinamento;
		if(colonnaOrdinamento.equals("name")) {
			colonna = "nomeResponsabile";
		}
		if(colonnaOrdinamento.equals("lastname")) {
			colonna = "cognomeResponsabile";
		}
		if(colonnaOrdinamento.equals("fiscalCode")) {
			colonna = "codiceFiscaleResponsabile";
		}
		if(colonnaOrdinamento.equals("prosecutingAdministration")) {
			colonna = "amministrazioneProcedente";
		}
		return colonna;
	}

	public String mapColonnaOrdinamentoEnteRicerca(String colonna) {
		if(colonna.equals("name")) {
			colonna = "descrizioneEnte";
		}
		if(colonna.equals("flagProsecutingAdministration")) {
			colonna = "flagAmministrazioneProcedente";
		}
		return colonna;
	}

	public PersonaDTO buildRespConfCompletoDTOToPersonaDTO(ResponsabileConfCompletoDTO respConferenza) {
		PersonaDTO personaDTO = new PersonaDTO();
		if(respConferenza != null) {
			personaDTO.setNome(respConferenza.getNome());
			personaDTO.setCognome(respConferenza.getCognome());
			personaDTO.setCodiceFiscale(respConferenza.getCodiceFiscale());
			personaDTO.setEmail(respConferenza.getEmail());
		}
		return personaDTO;
	}

	public String mapColonnaOrdinamentoRichiedenteRicerca(String colonna) {
		if(colonna.equals("name")) {
			colonna = "nomeRichiedente";
		}
		if(colonna.equals("lastname")) {
			colonna = "cognomeRichiedente";
		}
		if(colonna.equals("fiscalCode")) {
			colonna = "codiceFiscaleRichiedente";
		}
		if(colonna.equals("company")) {
			colonna = "descrizioneImpresa";
		}
		return colonna;
	}

	public PrecaricamentoRichiedentePreviewDTO buildRicercaRubricaRichiedentiToPrecRichPreviewDTO(
			RicercaRubricaRichiedenti ricerca) {
		PrecaricamentoRichiedentePreviewDTO prec = new PrecaricamentoRichiedentePreviewDTO();
		prec.setIdRichiedente(ricerca.getIdRubricaRichiedenti());
		prec.setNome(ricerca.getNomeRichiedente());
		prec.setCognome(ricerca.getCognomeRichiedente());
		prec.setCodiceFiscale(ricerca.getCodiceFiscaleRichiedente());
		if (ricerca.getIdImpresa() != null) {
			prec.setImpresa(new LabelValue(Integer.toString(ricerca.getIdImpresa()), ricerca.getDescrizioneImpresa()));
		}
		prec.setTipoConf(new LabelValue(ricerca.getCodiceTipoConfRubricaRichiedenti(), ricerca.getDescrizioneTipoConfRubricaRichiedenti()));
		return prec;
	}

	public PrecaricamentoRichiedenteDTO buildRubricaRichiedenteToPrecRichiedenteDTO(RubricaRichiedenti rubricaRich) {
		PrecaricamentoRichiedenteDTO prec = null;
		if (rubricaRich != null) {
			prec = new PrecaricamentoRichiedenteDTO();
			prec.setIdRichiedente(rubricaRich.getIdRubricaRichiedenti());
			prec.setNome(rubricaRich.getPersona().getNome());
			prec.setCognome(rubricaRich.getPersona().getCognome());
			prec.setCodiceFiscale(rubricaRich.getPersona().getCodiceFiscale());
			if (rubricaRich.getImpresa() != null) {
				prec.setImpresa(new LabelValue(Integer.toString(rubricaRich.getImpresa().getImpresa().getId()),
						rubricaRich.getImpresa().getImpresa().getDenominazione()));
			}
			prec.setEmail(rubricaRich.getPersona().getEmail());
			prec.setFlagRichPrincipale(rubricaRich.getPrincipale());
			prec.setTipoConf(createNotNullLabelValue(rubricaRich.getTipologiaConferenzaSpecializzazione()));
		}
		return prec;
	}

	public PersonaDTO buildPrecRichiedenteDTOToPersonaDTO(PrecaricamentoRichiedenteDTO richiedenteDTO) {
		PersonaDTO personaDTO = new PersonaDTO();
		if (richiedenteDTO != null) {
			personaDTO.setNome(richiedenteDTO.getNome());
			personaDTO.setCognome(richiedenteDTO.getCognome());
			personaDTO.setCodiceFiscale(richiedenteDTO.getCodiceFiscale());
			personaDTO.setEmail(richiedenteDTO.getEmail());
		}
		return personaDTO;
	}	
	
	public PersonaDTO buildPrecDelagatoDTOToPersonaDTO(PrecaricamentoDelegatoDTO delegatoDTO) {
		PersonaDTO personaDTO = new PersonaDTO();
		if (delegatoDTO != null) {
			personaDTO.setNome(delegatoDTO.getNome());
			personaDTO.setCognome(delegatoDTO.getCognome());
			personaDTO.setCodiceFiscale(delegatoDTO.getCodiceFiscale());
			personaDTO.setEmail(delegatoDTO.getEmail());
		}
		return personaDTO;
	}	
	
	public Utente buildPersonaDTOToUtente(PersonaDTO personaDTO) {
		Utente utente = utenteRepo.findByCodiceFiscaleIgnoreCase(personaDTO.getCodiceFiscale()).orElse(null);
		if (utente != null) {
			if (personaDTO.getNome() != null && !personaDTO.getNome().isEmpty()) {
				utente.setNome(personaDTO.getNome());
			}
			if (personaDTO.getCognome() != null && !personaDTO.getCognome().isEmpty()) {
				utente.setCognome(personaDTO.getCognome());
			}
			if (personaDTO.getEmail() != null && !personaDTO.getEmail().isEmpty()) {
				utente.setEmail(personaDTO.getEmail());
			}
		}
		return utente;
	}

	public String mapColonnaOrdinamentoPartecipanteRicerca(String colonnaOrdinamento) {
		if(colonnaOrdinamento.equals("authority")) {
			colonnaOrdinamento = "nomeEnte";
		}
		return colonnaOrdinamento;
	}

	public PrecaricamentoPartecipantePreviewDTO buildRubricaPartecipantiToPrecPartecipantePreviewDTO(
			RicercaRubricaPartecipanti ricercaPart) {
		PrecaricamentoPartecipantePreviewDTO partDTO = new PrecaricamentoPartecipantePreviewDTO();
		if (ricercaPart != null) {
			partDTO.setId(ricercaPart.getIdRubricaPartecipanti());
			partDTO.setEnte(new LabelValue(Integer.toString(ricercaPart.getIdEnte()), ricercaPart.getNomeEnte()));
			partDTO.setTipologiaConferenza(new LabelValue(ricercaPart.getCodiceTipologiaConferenza(),
					ricercaPart.getDescrizioneTipologiaConferenza()));
			partDTO.setEmail(ricercaPart.getEmail());
		}
		return partDTO;
	}

	public PrecaricamentoPartecipanteCompletoDTO buildRubricaPartecipantiToPrecPartecipanteCompletoDTO(
			RubricaPartecipanti rubricaPart) {
		PrecaricamentoPartecipanteCompletoDTO prec = null;
		if (rubricaPart != null) {
			prec = new PrecaricamentoPartecipanteCompletoDTO();
			prec.setIdRubricaPartecipante(rubricaPart.getIdRubricaPartecipanti());
			prec.setIdEnte(rubricaPart.getEnte().getIdEnte());
			prec.setDescrizioneEnte(rubricaPart.getEnte().getDescrizioneEnte());
			prec.setCodiceFiscale(rubricaPart.getEnte().getCodiceFiscaleEnte());
			prec.setEmail(rubricaPart.getEnte().getPecEnte());
			prec.setRuolo(createNotNullLabelValue(rubricaPart.getRuoloPartecipante()));
			prec.setTipologiaConferenza(createNotNullLabelValue(rubricaPart.getTipologiaConferenzaSpecializzazione()));
			for(CompetenzaAutorizzativa comp: rubricaPart.getListaCompAutoPerRbricaPartecipante()) {
				prec.getCompetenzaAutorizzativa().add(createNotNullLabelValue(comp));
			}
		}
		return prec;
	}

	public String mapColonnaOrdinamentoImpresaRicerca(String colonnaOrdinamento) {
		if(colonnaOrdinamento.equals("name")) {
			colonnaOrdinamento = "nome";
		}
		if(colonnaOrdinamento.equals("fiscalCode")) {
			colonnaOrdinamento = "codiceFiscale";
		}
		if(colonnaOrdinamento.equals("vatNumber")) {
			colonnaOrdinamento = "partitaIva";
		}
		return colonnaOrdinamento;
	}

	public PrecaricamentoImpresaPreviewDTO buildRubricaImpreseToPrecImpresaPreviewDTO(
			RicercaRubricaImprese ricercaImprese) {
		PrecaricamentoImpresaPreviewDTO prec = new PrecaricamentoImpresaPreviewDTO();
		if (ricercaImprese != null) {
			prec.setIdRubricaImprese(ricercaImprese.getIdRubricaImprese());
			prec.setIdImpresa(ricercaImprese.getIdImpresa());
			prec.setNome(ricercaImprese.getNome());
			prec.setCodiceFiscale(ricercaImprese.getCodiceFiscale());
			prec.setPartitaIva(ricercaImprese.getPartitaIva());
			prec.setTipologiaConferenza(new LabelValue(ricercaImprese.getCodiceTipologiaConferenza(),
					ricercaImprese.getDescrizioneTipologiaConferenza()));
		}
		return prec;
	}

	public PrecaricamentoImpresaDTO buildRubricaImpreseToPrecImpresaDTO(RubricaImprese rubricaImprese) {
		PrecaricamentoImpresaDTO prec = null;
		if (rubricaImprese != null) {
			prec = new PrecaricamentoImpresaDTO();
			prec.setIdRubricaImpresa(rubricaImprese.getIdRubricaImprese());
			prec.setTipologiaConferenza(createNotNullLabelValue(rubricaImprese.getTipologiaConferenzaSpecializzazione()));
			for(RubricaRichiedenti rr: rubricaRichRepo.findByImpresa(rubricaImprese)) {
				prec.getListaRichiedenti().add(buildRubricaRichiedenteToPrecRichiedentePreviewDTO(rr));
			}
			prec.setIdImpresa(rubricaImprese.getImpresa().getId());
			prec.setNome(rubricaImprese.getImpresa().getDenominazione());
			prec.setCodiceFiscale(rubricaImprese.getImpresa().getCodiceFiscale());
			prec.setPartitaIva(rubricaImprese.getImpresa().getPartitaIva());
			prec.setIndirizzo(rubricaImprese.getImpresa().getIndirizzo());
			prec.setFormaGiuridica(createNotNullLabelValue(rubricaImprese.getImpresa().getFormaGiuridica()));
			prec.setRegione(createNotNullLabelValue(rubricaImprese.getImpresa().getRegione()));
			prec.setProvincia(createNotNullLabelValue(rubricaImprese.getImpresa().getProvincia()));
			prec.setComune(createNotNullLabelValue(rubricaImprese.getImpresa().getComune()));
			prec.setStepConferenzaModificabili(rubricaImprese.getStepConferenzaModificabili());
			if (rubricaImprese.getListaStepConferenzaModificabili() != null) {
				String[] steps = rubricaImprese.getListaStepConferenzaModificabili().split(",");
				for (String step : steps) {
					prec.getStepAttivi().add(Integer.parseInt(step));
				}
			}
		}
		return prec;
	}

	public PrecaricamentoRichiedentePreviewDTO buildRubricaRichiedenteToPrecRichiedentePreviewDTO(
			RubricaRichiedenti rubricaRichiedenti) {
		PrecaricamentoRichiedentePreviewDTO prec = new PrecaricamentoRichiedentePreviewDTO();
		if (rubricaRichiedenti != null) {
			prec.setIdRichiedente(rubricaRichiedenti.getIdRubricaRichiedenti());
			prec.setNome(rubricaRichiedenti.getPersona().getNome());
			prec.setCognome(rubricaRichiedenti.getPersona().getCognome());
			prec.setCodiceFiscale(rubricaRichiedenti.getPersona().getCodiceFiscale());
			prec.setTipoConf(createNotNullLabelValue(rubricaRichiedenti.getTipologiaConferenzaSpecializzazione()));
			if (rubricaRichiedenti.getImpresa() != null) {
				prec.setImpresa(new LabelValue(Integer.toString(rubricaRichiedenti.getImpresa().getImpresa().getId()),
						rubricaRichiedenti.getImpresa().getImpresa().getDenominazione()));
			}
		}
		return prec;
	}

	public String mapColonnaOrdinamentoImpresa(String colonnaOrdinamento) {
		if(colonnaOrdinamento.equals("name")) {
			colonnaOrdinamento = "denominazione";
		}
		if(colonnaOrdinamento.equals("fiscalCode")) {
			colonnaOrdinamento = "codiceFiscale";
		}
		if(colonnaOrdinamento.equals("vatNumber")) {
			colonnaOrdinamento = "partitaIva";
		}
		return colonnaOrdinamento;
	}
	
	public PrecaricamentoDelegatoPreviewDTO buildRicercaRubricaDelegatoToPrecDelPreviewDTO(
			RicercaRubricaDelegati ricerca) {
		PrecaricamentoDelegatoPreviewDTO prec = new PrecaricamentoDelegatoPreviewDTO();
		prec.setIdDelegato(ricerca.getIdRubricaDelegato());
		prec.setNome(ricerca.getNomeDelegato());
		prec.setCognome(ricerca.getCognomeDelegato());
		prec.setCodiceFiscale(ricerca.getCodiceFiscaleDelegato());
		prec.setNomeDocumento(ricerca.getNomeDocumento());
		prec.setUrl(ricerca.getRiferimentoEsterno());
		prec.setTipoConf(new LabelValue(ricerca.getCodiceTipoConfRubricaDelegati(), ricerca.getDescrizioneTipoConfRubricaDelegati()));
		return prec;
	}
	
	public PrecaricamentoDelegatoDTO buildRubricaDelegatoToPrecDelegatoDTO(RubricaDelegati rubricaDelegato) {
		PrecaricamentoDelegatoDTO prec = null;
		if (rubricaDelegato != null) {
			prec = new PrecaricamentoDelegatoDTO();
			prec.setIdDelegato(rubricaDelegato.getIdRubricaDelegato());
			prec.setNome(rubricaDelegato.getPersona().getNome());
			prec.setCognome(rubricaDelegato.getPersona().getCognome());
			prec.setCodiceFiscale(rubricaDelegato.getPersona().getCodiceFiscale());
			prec.setEmail(rubricaDelegato.getPersona().getEmail());
			prec.setNomeDocumento(rubricaDelegato.getNomeDocumento());
			prec.setUrl(rubricaDelegato.getRiferimentoEsterno());
			prec.setTipoConf(createNotNullLabelValue(rubricaDelegato.getTipologiaConferenzaSpecializzazione()));
		}
		return prec;
	}
	
	public String mapColonnaOrdinamentoDelegatoRicerca(String colonna) {
		if(colonna.equals("name")) {
			colonna = "nomeDelegato";
		}
		if(colonna.equals("lastname")) {
			colonna = "cognomeDelegato";
		}
		if(colonna.equals("fiscalCode")) {
			colonna = "codiceFiscaleDelegato";
		}
		return colonna;
	}
	public String mapColonneConferenza(String colonna) {
		if (colonna.equals("id"))
			colonna = "idConferenza";
		if (colonna.equals("termineProcedimento"))
			colonna = "dataTermine";
		if (colonna.equals("terminePerIntegrazione"))
			colonna = "termineRichiestaIntegrazioniConferenza";
		if (colonna.equals("terminePerLaDetermina"))
			colonna = "termineEspressionePareri";
		if (colonna.equals("termineProssimaSessione"))
			colonna = "primaSessioneSimultanea";
		if (colonna.equals("tipologiaConferenza"))
			colonna = "tipologiaConferenza";
		if (colonna.equals("termineProcedimento"))
			colonna = "dataTermine";
		if (colonna.equals("stato"))
			colonna = "stato";
		if (colonna.equals("richiedente"))
			colonna = "codiceFiscaleRichiedente";
		return colonna;
	}

	public ImpresaPreviewDTO buildImpresaToImpresaPreviewDTO(Impresa impresa) {
		ImpresaPreviewDTO impresaDTO = new ImpresaPreviewDTO();
		if (impresa != null) {
			impresaDTO.setIdImpresa(impresa.getId());
			impresaDTO.setNome(impresa.getDenominazione());
			if (impresa.getCodiceFiscale() != null) {
				impresaDTO.setCodiceFiscale(impresa.getCodiceFiscale().toUpperCase());
			}
			impresaDTO.setPartitaIva(impresa.getPartitaIva());
		}
		return impresaDTO;
	}

	public ImpresaDTO buildPrecImpresaCompletaDTOToImpresaDTO(PrecaricamentoImpresaCompletaDTO precImpresaDTO) {
		ImpresaDTO impresaDTO = new ImpresaDTO();
		if (precImpresaDTO != null) {
			impresaDTO.setDenominazione(precImpresaDTO.getNome());
			if (precImpresaDTO.getCodiceFiscale() != null) {
				impresaDTO.setCf(precImpresaDTO.getCodiceFiscale().toUpperCase());
			}
			impresaDTO.setPartitaIva(precImpresaDTO.getPartitaIva());
			impresaDTO.setFormaGiuridica(precImpresaDTO.getFormaGiuridica());
			impresaDTO.setRegione(precImpresaDTO.getRegione());
			impresaDTO.setProvincia(precImpresaDTO.getProvincia());
			impresaDTO.setComune(precImpresaDTO.getComune());
			impresaDTO.setIndirizzo(precImpresaDTO.getIndirizzo());
		}
		return impresaDTO;
	}

	public String concatenaStep(List<Integer> listaStepAttivi) {
		String stringaStepAttivi = null;
		if (listaStepAttivi.size() != 0) {
			List<String> listaStringa = listaStepAttivi.stream().map(in -> Integer.toString(in))
					.collect(Collectors.toList());
			stringaStepAttivi = String.join(",", listaStringa);
		}
		return stringaStepAttivi;
	}

	public ResponsabileConfCompletoDTO buildrespConfToRespConfCompletoDTO(ResponsabileConferenza respConf) {
		ResponsabileConfCompletoDTO dto = null;
		if (respConf != null) {
			dto = new ResponsabileConfCompletoDTO();
			dto.setIdPersona(respConf.getPersona().getIdPersona());
			dto.setNome(respConf.getPersona().getNome());
			dto.setCognome(respConf.getPersona().getCognome());
			dto.setCodiceFiscale(respConf.getPersona().getCodiceFiscale());
			dto.setEmail(respConf.getPersona().getEmail());
			dto.setAmministrazioneProcedente(createNotNullLabelValue(respConf.getEnte()));
		}
		return dto;
	}

	public ConferenzaConsolleDTO buildConferenzaToConferenzaConsolleDTO(Conferenza conferenza) {
		ConferenzaDTO conferenzaDTO = confBuilder.buildConferenzaDTO(conferenza);
		ConferenzaConsolleDTO conferenzaConsolleDTO = buildConferenzaDTOToConferenzaConsolleDTO(conferenzaDTO);
		conferenzaConsolleDTO.setAmministrazioneProcedente(createNotNullLabelValue(conferenza.getAmministrazioneProcedente()));
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(conferenza.getCodiceFiscaleResponsabileConferenza())
				.orElse(null);
		ResponsabileConferenza respConf = respConfRepo
				.findByEnteAndPersona(conferenza.getAmministrazioneProcedente(), persona).orElse(null);
		
		if(respConf == null) {
			throw new NotFoundEx("Responsabile conferenza not found");
		}
		
		conferenzaConsolleDTO.setResponsabileConferenza(
				new LabelValue(Integer.toString(respConf.getId()), respConf.getPersona().getDescrizione()));
		return conferenzaConsolleDTO;
	}

	private ConferenzaConsolleDTO buildConferenzaDTOToConferenzaConsolleDTO(ConferenzaDTO conferenzaDTO) {
		ConferenzaConsolleDTO conferenzaConsolleDTO = new ConferenzaConsolleDTO();
		conferenzaConsolleDTO.setPratica(conferenzaDTO.getPratica());
		conferenzaConsolleDTO.setDefinizione(conferenzaDTO.getDefinizione());
		conferenzaConsolleDTO.setStep(conferenzaDTO.getStep());
		conferenzaConsolleDTO.setStato(conferenzaDTO.getStato());
		return conferenzaConsolleDTO;
	}

	public Impresa buildImpresa(ImpresaDTO impresaDTO) {
		Impresa impresa = impresaRepo.findByPartitaIvaIgnoreCase(impresaDTO.getPartitaIva()).orElse(null);
		
		if (impresa == null) {
			impresa = new Impresa();
			impresa.setPartitaIva(impresaDTO.getPartitaIva());
		}
		
		if (impresaDTO.getCf() != null && !impresaDTO.getCf().isEmpty()) {
			impresa.setCodiceFiscale(impresaDTO.getCf().toUpperCase());
		}		
		
		impresa.setDenominazione(impresaDTO.getDenominazione());
		impresa.setIndirizzo(impresaDTO.getIndirizzo());
		if (impresaDTO.getFormaGiuridica() != null) {
			impresa.setFormaGiuridica(
					formaGiuridicaRepo.findById(impresaDTO.getFormaGiuridica().getKey()).orElse(null));
		} else {
			impresa.setFormaGiuridica(null);
		}
		if (impresaDTO.getRegione() != null) {
			impresa.setRegione(regioneRepo.findById(impresaDTO.getRegione().getKey()).orElse(null));
		} else {
			impresa.setRegione(null);
		}
		if (impresaDTO.getProvincia() != null) {
			impresa.setProvincia(provRepo.findById(impresaDTO.getProvincia().getKey()).orElse(null));
		} else {
			impresa.setProvincia(null);
		}
		if (impresaDTO.getComune() != null) {
			impresa.setComune(comuneRepo.findById(impresaDTO.getComune().getKey()).orElse(null));
		} else {
			impresa.setComune(null);
		}
		return impresa;
	}

	public Ente buildEnteDTOToEnte(EnteDTO enteDTO, Ente enteEsistente) {
		Ente ente = new Ente();
		ente.setFlagAmministrazionePrincipale(Boolean.FALSE);
		ente.setFlagAmministrazioneProcedente(Boolean.FALSE);
		if (enteEsistente != null) {
			ente = enteEsistente;
		}
		if (enteDTO != null) {
			if (enteDTO.getNome() != null && !enteDTO.getNome().isEmpty()) {
				ente.setDescrizioneEnte(enteDTO.getNome());
			}
			if (enteDTO.getCodiceFiscale() != null && !enteDTO.getCodiceFiscale().isEmpty()) {
				ente.setCodiceFiscaleEnte(enteDTO.getCodiceFiscale());
			}
			if (enteDTO.getPec() != null && !enteDTO.getPec().isEmpty()) {
				ente.setPecEnte(enteDTO.getPec());
			}
			if (enteDTO.getCodiceUfficio() != null && !enteDTO.getCodiceUfficio().isEmpty()) {
				ente.setCodiceUfficio(enteDTO.getCodiceUfficio());
			}
			if (enteDTO.getFlagAmmProc() != null) {
				ente.setFlagAmministrazioneProcedente(enteDTO.getFlagAmmProc());
			}
			if (enteDTO.getRegione() != null) {
				ente.setRegione(regioneRepo.findByCodice(enteDTO.getRegione().getKey()).orElse(null));
			}
			if (enteDTO.getProvincia() != null) {
				ente.setProvincia(provRepo.findByCodice(enteDTO.getProvincia().getKey()).orElse(null));
			}
			if (enteDTO.getComune() != null) {
				Comune comune = comuneRepo.findById(enteDTO.getComune().getKey()).orElse(null);
				ente.setComune(comune);
				if (comune != null) {
					ente.setDescrizioneComune(comune.getDescrizione());
				}
			}
			if (enteDTO.getTipologiaAmm() != null) {
				ente.setEnteTipoAmm(enteTipoAmmRepo.findById(enteDTO.getTipologiaAmm().getKey()).orElse(null));
			}
			if (enteDTO.getTipologiaIstat() != null) {
				ente.setEnteTipoIstat(enteTipoIstatRepo.findById(enteDTO.getTipologiaIstat().getKey()).orElse(null));
			}
		}
		return ente;
	}

	public String mapColonnaOrdinamentoPreaccreditatoRicerca(String colonna) {
		if(colonna.equals("name")) {
			colonna = "nomePreaccreditato";
		}
		if(colonna.equals("lastname")) {
			colonna = "cognomePreaccreditato";
		}
		if(colonna.equals("fiscalCode")) {
			colonna = "codiceFiscalePreaccreditato";
		}
		return colonna;
	}
	
	public PreaccreditatiPreviewDTO buildRicercaRubricaPreaccreditatoPreviewDTO(
			RicercaRubricaPreaccreditati ricerca) {
		PreaccreditatiPreviewDTO prec = new PreaccreditatiPreviewDTO();
		prec.setIdAccreditato(ricerca.getIdRubricaPreaccreditato());
		prec.setNome(ricerca.getNomePreaccreditato());
		prec.setCognome(ricerca.getCognomePreaccreditato());
		prec.setCodiceFiscale(ricerca.getCodiceFiscalePreaccreditato());
		prec.setTipoProfilo(new LabelValue(ricerca.getCodiceTipoProfiloPreaccreditato(),  messageSource.getMessage(ricerca.getDescrizioneTipoProfiloPreaccreditato(),null,request.getLocale())));
		prec.setTipoConf(new LabelValue(ricerca.getCodiceTipoConfPreaccreditato(), ricerca.getDescrizioneTipoConfPreaccreditato()));
		prec.setIdEnte(ricerca.getIdEnte());
		prec.setEnte(new LabelValue(Integer.toString(ricerca.getIdEnte()), ricerca.getDescrizioneEntePreaccreditato()));
		return prec;
	}
	
	public PreaccreditatoDTO buildRubricaPreaccreditatoToPreaccreditatoDTO(RubricaPreaccreditati rubricaPreaccreditati) {
		PreaccreditatoDTO prec = null;
		if (rubricaPreaccreditati != null) {
			prec = new PreaccreditatoDTO();
			prec.setIdPreaccreditato(rubricaPreaccreditati.getIdRubricaPreaccreditato());
			prec.setNome(rubricaPreaccreditati.getPersona().getNome());
			prec.setCognome(rubricaPreaccreditati.getPersona().getCognome());
			prec.setCodiceFiscale(rubricaPreaccreditati.getPersona().getCodiceFiscale());
			prec.setEmail(rubricaPreaccreditati.getPersona().getEmail());
			prec.setTipoConf(createNotNullLabelValue(rubricaPreaccreditati.getTipologiaConferenzaSpecializzazione()));
			prec.setTipoProfilo(createNotNullLabelValue(rubricaPreaccreditati.getRuoloPersona()));
			prec.setEnte(createNotNullLabelValue(rubricaPreaccreditati.getEnte()));
		}
		return prec;
	}
	
	public PersonaDTO buildPreaccreditatoDTOToPersonaDTO(PreaccreditatoDTO preaccreditatoDTO) {
		PersonaDTO personaDTO = new PersonaDTO();
		if (preaccreditatoDTO != null) {
			personaDTO.setNome(preaccreditatoDTO.getNome());
			personaDTO.setCognome(preaccreditatoDTO.getCognome());
			personaDTO.setCodiceFiscale(preaccreditatoDTO.getCodiceFiscale());
			personaDTO.setEmail(preaccreditatoDTO.getEmail());
		}
		return personaDTO;
	}
}
