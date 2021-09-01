package conferenza.consolleAmministrativa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EnteCsvDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.EnteUfficiCsvDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ImpresaDTO;
import conferenza.DTO.ListaConferenzaAnteprimaDTO;
import conferenza.DTO.ListaProfiloDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.RicercaProtocollo;
import conferenza.DTO.RicercaUnifyDTO;
import conferenza.DTO.RicercaUtente;
import conferenza.DTO.RispostaDocumentoDTO;
import conferenza.DTO.RispostaEnteDTO;
import conferenza.DTO.RispostaListaConferenzaAnteprimaDTO;
import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.DTO.RispostaListaProfiloDTO;
import conferenza.DTO.RispostaPersonaDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.DTO.bean.ListaLabelValue;
import conferenza.builder.ConferenzaBuilder;
import conferenza.builder.DocumentoBuilder;
import conferenza.builder.EnteBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.builder.ProtocollazoneBuilder;
import conferenza.builder.UtenteBuilder;
import conferenza.consolleAmministrativa.DTO.EntePreviewDTO;
import conferenza.consolleAmministrativa.DTO.EnteRicerca;
import conferenza.consolleAmministrativa.DTO.ImpresaRicerca;
import conferenza.consolleAmministrativa.DTO.InvioMailIndizioneDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaCompletaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedenteDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedenteRicerca;
import conferenza.consolleAmministrativa.DTO.ResponsabileConfCompletoDTO;
import conferenza.consolleAmministrativa.DTO.RicercaResponsabileConferenzaDTO;
import conferenza.consolleAmministrativa.DTO.UtenteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaEntePreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaPrecaricamentoImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaPrecaricamentoPartecipantePreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaPrecaricamentoRichiedentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaResponsabileConfPreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaUtentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaConferenzaConsolleDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaImpresaDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPreaccreditatiPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoDelegatoPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoPartecipantePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoRichiedentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaResponsabileConfPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaUtentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoDelegatoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoImpresaDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoPartecipanteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoRichiedenteDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaResponsabileConfCompletoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaUtenteCompletoDTO;
import conferenza.consolleAmministrativa.builder.ConsolleAmministrativaBuilder;
import conferenza.consolleAmministrativa.model.RicercaResponsabileConferenza;
import conferenza.consolleAmministrativa.model.RicercaRubricaDelegati;
import conferenza.consolleAmministrativa.model.RicercaRubricaImprese;
import conferenza.consolleAmministrativa.model.RicercaRubricaPartecipanti;
import conferenza.consolleAmministrativa.model.RicercaRubricaPreaccreditati;
import conferenza.consolleAmministrativa.model.RicercaRubricaRichiedenti;
import conferenza.consolleAmministrativa.repository.RicercaResponsabileConferenzaRepository;
import conferenza.consolleAmministrativa.repository.RicercaRubricaDelegatiRepository;
import conferenza.consolleAmministrativa.repository.RicercaRubricaImpreseRepository;
import conferenza.consolleAmministrativa.repository.RicercaRubricaPartecipantiRepository;
import conferenza.consolleAmministrativa.repository.RicercaRubricaPreaccreditatiRepository;
import conferenza.consolleAmministrativa.repository.RicercaRubricaRichiedentiRepository;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderImpresa;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRespConferenza;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRubricaDelegati;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRubricaImprese;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRubricaPartecipanti;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRubricaPreaccreditati;
import conferenza.consolleAmministrativa.repository.specification.SpecificationBuilderRicercaRubricaRichiedenti;
import conferenza.consolleAmministrativa.validator.AccessValidator;
import conferenza.exception.NotEditableException;
import conferenza.exception.NotFoundEx;
import conferenza.mail.EmailRepositoryService;
import conferenza.model.Accreditamento;
import conferenza.model.CompetenzaAutorizzativa;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Ente;
import conferenza.model.GruppoUtenti;
import conferenza.model.Impresa;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.Profilo;
import conferenza.model.Protocollo;
import conferenza.model.RegistroDocumento;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RicercaConferenza;
import conferenza.model.RubricaDelegati;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.RubricaPreaccreditati;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.RuoloPartecipante;
import conferenza.model.RuoloPersona;
import conferenza.model.TipoProfilo;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.model.TipologiaDocumento;
import conferenza.model.Utente;
import conferenza.model.bean._Typological;
import conferenza.protocollo.service.ProtocolloService;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.CompetenzaAutorizzativaRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EnteAppoggioCSVRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EnteUfficiAppoggioCSVRepository;
import conferenza.repository.GruppoUtentiRepository;
import conferenza.repository.ImpresaRepository;
import conferenza.repository.ListaPreaccreditatiPreviewDTO;
import conferenza.repository.ListaPrecaricamentoDelegatoPreviewDTO;
import conferenza.repository.PartecipanteRepository;
import conferenza.service.ProtocollazioneService;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RicercaConferenzaRepository;
import conferenza.repository.RicercaConferenzaSpecificationsBuilder;
import conferenza.repository.RubricaDelegatiRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.RubricaPartecipantiRepository;
import conferenza.repository.RubricaPreaccreditatiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.SearchCriteria;
import conferenza.repository.SpecificationBuilderEnte;
import conferenza.repository.SpecificationBuilderUtente;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.UtenteRepository;
import conferenza.service.AccreditamentoService;
import conferenza.service.CaricaComboService;
import conferenza.service.DocumentoService;
import conferenza.service.EnteService;
import conferenza.service.FileSystemService;
import conferenza.service.ParticipantService;
import conferenza.service.ProtocollazioneService;
import conferenza.service.UtenteService;
import conferenza.service._BaseService;
import conferenza.util.DbConst;
import conferenza.validator.ConferenceValidator;

@Transactional
@Service
public class ConsolleAmministrativaService extends _BaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsolleAmministrativaService.class.getName());

	@Autowired
	AccessValidator accessValidator;

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	ResponsabileConferenzaRepository respConfRepo;

	@Autowired
	GruppoUtentiRepository gruppoUtentiRepository;
	
	@Autowired
	ConferenceValidator confValidator;

	@Autowired
	ParticipantService partService;

	@Autowired
	PartecipanteBuilder partBuilder;

	@Autowired
	EnteBuilder enteBuilder;

	@Autowired
	RuoloPartecipanteRepository ruoloPartRepo;

	@Autowired
	RuoloPersonaRepository ruoloPersonaRepo;
	
	@Autowired
	PartecipanteRepository partRepo;

	@Autowired
	AccreditamentoService accreditamentoService;

	@Autowired
	EnteService enteService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	CaricaComboService comboService;

	@Autowired
	RubricaImpreseRepository rubricaImprRepo;

	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;
	
	@Autowired
	RubricaDelegatiRepository rubricaDelRepo;
	
	@Autowired
	RubricaPreaccreditatiRepository rubricaPreaccrRepo;
	
	@Autowired
	ImpresaRepository impresaRepo;

	@Autowired
	ConferenzaBuilder confBuilder;

	@Autowired
	RubricaPartecipantiRepository rubricaPartRepo;

	@Autowired
	CompetenzaAutorizzativaRepository compAutoRepo;

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	EmailRepositoryService emailRepoService;

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	ConsolleAmministrativaBuilder consolleBuilder;

	@Autowired
	RicercaResponsabileConferenzaRepository ricercaRespConfRepo;

	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	RicercaRubricaRichiedentiRepository ricercaRubrRichRepo;
	
	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipoConfSpecRepo;
	
	@Autowired
	ProfiloRepository profiloRepo;
	
	@Autowired
	TipoProfiloRepository tipoProfiloRepo;
	
	@Autowired
	UtenteBuilder utenteBuilder;
	
	@Autowired
	RicercaRubricaPartecipantiRepository ricercaRubPartRepo;
	
	@Autowired
	RicercaRubricaImpreseRepository ricercaRubImpRepo;
	
	@Autowired
	RicercaRubricaDelegatiRepository ricercaRubrDelRepo;
	
	@Autowired
	RicercaRubricaPreaccreditatiRepository ricercaRubrPreaccreditaiRepo;
	
	@Autowired
	DocumentoBuilder documentoBuilder;
	
	@Autowired
	EnteAppoggioCSVRepository enteAppoRepo;
	
	@Autowired
	EnteUfficiAppoggioCSVRepository enteUfficiAppoRepo;
	
	@Autowired
	AccreditamentoRepository accreditamentoRepo;
	
	@Autowired
	RicercaConferenzaRepository ricercaConfRepo;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	TipologiaDocumentoRepository tipoDocRepo;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	ProtocollazoneBuilder protocolloBuilder;

	@Autowired
	ProtocollazioneService protService;

	@Autowired
	private FileSystemService fileSystemService;
	
	public void caricaDocumentoEnti(MultipartFile file, String tipo) throws IOException {
		CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator('\t');
		CsvMapper mapper = new CsvMapper();
		if (tipo.equals("enti")) {
			MappingIterator<EnteCsvDTO> readValues = mapper.readerFor(EnteCsvDTO.class).with(bootstrapSchema)
					.readValues(file.getInputStream());
			List<EnteCsvDTO> allValues = readValues.readAll();
			allValues.stream().map(csv -> documentoBuilder.buildEnteCSV(csv)).forEach(ente -> enteAppoRepo.save(ente));
		}
		if (tipo.equals("uffici")) {
			MappingIterator<EnteUfficiCsvDTO> readValues = mapper.readerFor(EnteUfficiCsvDTO.class).with(bootstrapSchema)
					.readValues(file.getInputStream());
			List<EnteUfficiCsvDTO> allValues = readValues.readAll();
			allValues.stream().map(csv -> documentoBuilder.buildEnteUfficiCSV(csv)).forEach(ufficio -> enteUfficiAppoRepo.save(ufficio));
		}		
	}

	public String updateEnti(String tipo) {
		String risposta = "error";
		if (tipo.equals("enti")) {
			risposta = enteRepo.updateEnti();
		}
		if (tipo.equals("uffici")) {
			risposta = enteRepo.updateEntiUffici();
		}
		return risposta;
	}

	public IdentifiableDTO updateconferenceManager(Integer id, Integer idManager) {
		accessValidator.consolleAdminAccesso();
		accessValidator.validateParameter(Integer.toString(idManager));
		ResponsabileConferenza respConf = respConfRepo.findById(idManager).orElse(null);
		if (respConf == null) {
			throw new NotFoundEx("Manager not found");
		}
		Conferenza conferenza = confRepo.findById(id).orElse(null);
		if (conferenza == null) {
			throw new NotFoundEx("Conference not found");
		}
		confValidator.validateStatoConferenza(conferenza);
		conferenza.setAmministrazioneProcedente(respConf.getEnte());
		conferenza.setCodiceFiscaleResponsabileConferenza(respConf.getPersona().getCodiceFiscale());
		// Conferenza salvata
		confRepo.save(conferenza);

		Partecipante partVecchio = partRepo.findByConferenzaAndEnte(conferenza, respConf.getEnte()).orElse(null);
		Partecipante ammProcVecchia = partService.findAmministrazioneProcedente(conferenza);
		Partecipante ammProcNuova = creaAmmProcedente(conferenza, respConf);
		Accreditamento accreditamentoResponsabile = accreditamentoService
				.creaAccreditamentoReponsabile(respConf.getPersona(), ammProcNuova);
		// scambio puntamenti tra la vecchia e la nuova amministrazione procedente
		partService.scambioPuntamentiPartecipante(ammProcNuova.getIdPartecipante(), ammProcVecchia.getIdPartecipante());
		ammProcVecchia.getAccreditati().stream().forEach(
				a -> accreditamentoService.scambioPuntamentiAccreditati(accreditamentoResponsabile.getId(), a.getId()));
		// scambio puntamenti tra la nuova amministrazione procedente e l'eventuale ente
		// già presente
		if (partVecchio != null) {
			partService.scambioPuntamentiPartecipante(ammProcNuova.getIdPartecipante(),
					partVecchio.getIdPartecipante());
			partVecchio.getAccreditati().stream().forEach(a -> accreditamentoService
					.scambioPuntamentiAccreditati(accreditamentoResponsabile.getId(), a.getId()));
			partService.eliminaPartecipante(partVecchio.getIdPartecipante());
		}
		// eliminazione vecchia amministrazione procedente
		partService.eliminaPartecipante(ammProcVecchia.getIdPartecipante());
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(conferenza.getIdConferenza());
		return identifiableDTO;
	}

	public Partecipante creaAmmProcedente(Conferenza conferenza, ResponsabileConferenza respConf) {
		EnteDTO ammProcDTO = enteBuilder.enteToEnteDTO(respConf.getEnte());
		ammProcDTO.setRuolo(createNotNullLabelValue(ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).orElse(null)));
		PartecipanteDTO ammProcPartDTO = partBuilder.buildEnteDTOToPartecipanteDTO(ammProcDTO);
		Integer idAmmProc = partService.creaPartecipante(ammProcPartDTO, conferenza.getIdConferenza()).getId();
		return partRepo.findById(idAmmProc).orElse(null);
	}

	public IdentifiableDTO patchEnte(EnteDTO enteDTO, Integer id) {
		accessValidator.consolleAdminAccesso();
		Ente ente = enteRepo.findById(id).orElse(null);
		if (ente == null) {
			throw new NotFoundEx("Authority not found");
		}
		if (enteDTO.getFlagAmmProc()) {
			if(ente.getCodiceUfficio() != null) {
				throw new NotEditableException("This is an office. It must be not a proceeding administration");
			}
			ente.setFlagAmministrazioneProcedente(enteDTO.getFlagAmmProc());
			creaProfiloEnte(ente);
			ente = enteRepo.save(ente);
		} else {
			if (isFlagModificabile(ente)) {
				ente.setFlagAmministrazioneProcedente(enteDTO.getFlagAmmProc());
				for (Profilo profilo : profiloRepo.findByAmministrazioneProcedente(ente)) {
					profiloRepo.delete(profilo);
				}
				for (ResponsabileConferenza resp: respConfRepo.findByEnte(ente)) {
					respConfRepo.delete(resp);
				}
			}
		}
		if (enteDTO.getPec() != null && !enteDTO.getPec().isEmpty()) {
			ente.setPecEnte(enteDTO.getPec());
			ente = enteRepo.save(ente);
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(ente.getIdEnte());
		return identifiableDTO;
	}
	
	private Boolean isFlagModificabile(Ente ente) {
		Boolean flagModificabile = Boolean.TRUE;
		for (Profilo profilo: profiloRepo.findByAmministrazioneProcedente(ente)) {
			if(utenteRepo.findByProfilo(profilo).size() != 0) {
				flagModificabile = Boolean.FALSE;
			}
		}
		if (confRepo.findByAmministrazioneProcedente(ente).size() != 0) {
			flagModificabile = Boolean.FALSE;
		}
		if (!flagModificabile) {
			throw new NotEditableException("This authority must be a proceeding administration");
		}
		return flagModificabile;
	}

	public IdentifiableDTO creaUtenteEPersona(UtenteDTO utenteDTO) {
		accessValidator.consolleAdminAccesso();
		accessValidator.datiObbligatoriUtenteDTO(utenteDTO);
		if (utenteRepo.findByCodiceFiscaleIgnoreCase(utenteDTO.getCodiceFiscale()).isPresent()) {
			throw new NotEditableException("This user already exists");
		}
		Utente utente = utenteRepo.save(utenteBuilder.buildUtente(utenteDTO));
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(utenteDTO.getCodiceFiscale()).orElse(null);
		if(persona == null) {
			PersonaDTO personaDTO = partBuilder.buildUtenteDTOToPersonaDTO(utenteDTO);
			personaRepo.save(partBuilder.buildPersona(personaDTO));
		}
		
		setUserManagersImpersonate(utente, utenteDTO);
		
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(utente.getIdUtente());
		return identifiableDTO;
	}

	public IdentifiableDTO patchUtente(UtenteDTO utenteDTO, Integer id) {
		accessValidator.consolleAdminAccesso();
		Utente utente = utenteRepo.findById(id).orElse(null);
		if (utenteDTO.getProfilo() != null
				&& !utenteDTO.getProfilo().getKey().equals(Integer.toString(utente.getProfilo().getId()))) {
			utente = accessValidator.validateCambioProfiloUtente(id);
			Boolean wasResponsabile = utente.isResponsabile();
			if (wasResponsabile)
					deleteResponsabile(utente);

			Boolean wasCreatore = utente.isCreatore();
			if (wasCreatore) {
				utente.setDelegaCreazioneAltreAmministrazioni(Boolean.FALSE);
			}
			
			TipoProfilo newtipoprofilo = tipoProfiloRepo.findByDescrizione(utenteDTO.getProfilo().getValue()).orElse(null);
			if(newtipoprofilo != null) {
				utente.getProfilo().setTipoProfilo(newtipoprofilo);
				
				logger.debug("@patchUtente id: " + utente.getProfilo().getId() + " ente: " + utente.getProfilo().getAmministrazioneProcedente().getIdEnte() + " tipo: " + utente.getProfilo().getTipoProfilo().getCodice());
				
				profiloRepo.saveAndFlush(utente.getProfilo());
			}
			
			utente.setProfilo(profiloRepo.findById(Integer.parseInt(utenteDTO.getProfilo().getKey())).orElse(null));
			
			Boolean isResponsabile = utente.isResponsabile();
			if (isResponsabile) {
				creaResponsabile(utente, utente.getProfilo().getAmministrazioneProcedente());
			}
			Boolean isCreatore = utente.isCreatore();
			if (isCreatore) {
				Boolean flag = (utenteDTO.getDelegaCreazioneAltreAmministrazioni() != null)
						? utenteDTO.getDelegaCreazioneAltreAmministrazioni()
						: Boolean.FALSE;
				utente.setDelegaCreazioneAltreAmministrazioni(flag);
			}
		}
		
		setUserManagersImpersonate(utente, utenteDTO);
		
		PersonaDTO personaDTO = partBuilder.buildUtenteDTOToPersonaDTO(utenteDTO);
		creaAggiornaPersonaEAggiornaUtente(personaDTO);
		
		// aggiorno anche il flag_firmatario quando previsto
		if(utenteDTO.getFlagFirmatario() != null)
			aggiornaFirmatario(personaDTO, utenteDTO.getFlagFirmatario());
	
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(utente.getIdUtente());
		return identifiableDTO;	
	}
	
	// xmfSE sets the relations
	private void setUserManagersImpersonate(Utente utente, UtenteDTO utenteDTO) {
		if(utenteDTO.getManagersToImpersonate() == null) return;

		gruppoUtentiRepository.deleteAllUserGroups(utente.getIdUtente());
		
		for(LabelValue manager : utenteDTO.getManagersToImpersonate()) {
			Integer idmanager = Integer.parseInt(manager.getKey());

			// GruppoUtenti currentmanagers = gruppoUtentiRepository.findByFkUtenteAndFkUtenteResponsabile(utente.getIdUtente(), idmanager);
			// if(currentmanagers != null) continue;

			// add
			GruppoUtenti newGruppoUtenti = new GruppoUtenti();
			newGruppoUtenti.setFk_utente(utente.getIdUtente());
			newGruppoUtenti.setFk_utente_responsabile(idmanager);
			
			gruppoUtentiRepository.save(newGruppoUtenti);
		}
	}

	private void deleteResponsabile(Utente utente) {
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null);
		Ente ammProcedente = utente.getProfilo().getAmministrazioneProcedente();
		ResponsabileConferenza resp = respConfRepo.findByEnteAndPersona(ammProcedente, persona).orElse(null);
		if (resp != null) {
			respConfRepo.delete(resp);
		}
	}

	public IdentifiableDTO creaResponsabileConferenza(ResponsabileConfCompletoDTO respConferenzaDTO) {
		accessValidator.consolleAdminAccesso();
		Ente ammProcendente = enteRepo
				.findById(Integer.parseInt(respConferenzaDTO.getAmministrazioneProcedente().getKey())).orElse(null);
		if(ammProcendente == null) {
			throw new NotFoundEx("This authority does not exist");
		}
		if(!ammProcendente.getFlagAmministrazioneProcedente()) {
			throw new NotEditableException("This authority is not a proceeding administration");
		}
		PersonaDTO personaDTO = consolleBuilder.buildRespConfCompletoDTOToPersonaDTO(respConferenzaDTO);
		accessValidator.datiObbligatoriPersonaDTO(personaDTO);
		Persona persona = creaAggiornaPersonaEAggiornaUtente(personaDTO);
		if (respConfRepo.findByEnteAndPersona(ammProcendente, persona).isPresent()) {
			throw new NotEditableException("This manager already exists");
		}
		UtenteDTO utenteDTO = utenteBuilder.buildPersonaDTOToUtenteDTO(personaDTO);
		TipoProfilo tipoProfilo = tipoProfiloRepo.findById(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA)).orElse(null);
		Profilo profilo = profiloRepo.findByAmministrazioneProcedenteAndTipoProfilo(ammProcendente, tipoProfilo).orElse(null);
		if(profilo == null) {
			throw new NotFoundEx("il profilo non esiste");
		}
		utenteDTO.setProfilo(new LabelValue(Integer.toString(profilo.getId()), profilo.getTipoProfilo().getDescrizione()));
		Utente utente = creaUtente(utenteDTO);
		ResponsabileConferenza respConferenza = creaResponsabile(utente, ammProcendente);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(respConferenza.getId());
		return identifiableDTO;
	}

	private ResponsabileConferenza creaResponsabile(Utente utente, Ente ente) {
		ResponsabileConferenza respConferenza = new ResponsabileConferenza();
		respConferenza.setEnte(ente);
		respConferenza.setPersona(personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null));
		return respConfRepo.save(respConferenza);
	}

	public IdentifiableDTO patchPersona(PersonaDTO personaDTO, Integer id) {
		accessValidator.consolleAdminAccesso();
		Persona persona = personaRepo.findById(id).orElse(null);
		if (persona == null) {
			throw new NotFoundEx("Person not found");
		}
		personaDTO.setCodiceFiscale(persona.getCodiceFiscale());
		persona = creaAggiornaPersonaEAggiornaUtente(personaDTO);		
		persona = personaRepo.save(persona);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(persona.getIdPersona());
		return identifiableDTO;
	}

	public Utente AggiornaUtente(PersonaDTO personaDTO) {
		Utente utente = consolleBuilder.buildPersonaDTOToUtente(personaDTO);
		if (utente != null) {
			utente = utenteRepo.save(utente);
		}
		return utente;
	}

	public RispostaListaProfiloDTO findProfileByAmministrazioneProcedente(Integer idAmmProcedente) {
		accessValidator.consolleAdminAccesso();
		List<Profilo> listaProfilo = profiloRepo
				.findByAmministrazioneProcedente(enteRepo.findById(idAmmProcedente).orElse(null));
		ListaProfiloDTO lista = new ListaProfiloDTO();
		listaProfilo.stream().forEach(p -> lista.getList().add(utenteBuilder.buildProfiloToProfiloDTO(p)));
		lista.setTotalNumber(Long.valueOf(lista.getList().size()));
		RispostaListaProfiloDTO risposta = new RispostaListaProfiloDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaListaUtentePreviewDTO findUtenti(RicercaUtente ricerca) {
		accessValidator.consolleAdminAccesso();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				utenteBuilder.mapColonne(mapColonneUtente(ricerca.getColonnaOrdinamento())));
		Iterable<Utente> it = utenteRepo.findAll(filtroUtente(ricerca), page);
		ListaUtentePreviewDTO lista = new ListaUtentePreviewDTO();
		for (Utente utente: it) {
			lista.getList().add(utenteBuilder.buildUtentePreviewDTO(utente));
		}
		lista.setTotalNumber(utenteRepo.count(filtroUtente(ricerca)));
		RispostaListaUtentePreviewDTO risposta = new RispostaListaUtentePreviewDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	public Specification<Utente> filtroUtente(RicercaUtente ricerca) {
		SpecificationBuilderUtente builder = new SpecificationBuilderUtente();
		List<SearchCriteria> criteriaUtente = criteriaUtente(ricerca);
		List<SearchCriteria> criteriaAmmProcedenteUtente = criteriaAmmProcedenteUtente(ricerca.getAmministrazioneProcedente());
		return builder.buildAllOr(criteriaAmmProcedenteUtente).and(builder.buildAllOr(criteriaUtente));
	}
	
	private List<SearchCriteria> criteriaAmmProcedenteUtente(String amministrazioneProcedente) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (amministrazioneProcedente != null && !amministrazioneProcedente.isEmpty()) {
			List<Ente> listaEnti = enteRepo.findByDescrizioneEnteContainingIgnoreCase(amministrazioneProcedente);
			for (Ente ente : listaEnti) {
				List<Profilo> lista = profiloRepo.findByAmministrazioneProcedente(ente);
				for (Profilo profilo : lista) {
					parametriRicerca.add(new SearchCriteria("profilo", "=", profilo));
				}
			}
		}
		return parametriRicerca;
	}
	
	public List<SearchCriteria> criteriaUtente(RicercaUtente ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca != null && ricerca.getValue() != null && !ricerca.getValue().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nome", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("cognome", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("codiceFiscale", "%", ricerca.getValue()));
		}
		return parametriRicerca;
	}

	public RispostaUtenteCompletoDTO findUtenteSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		accessValidator.esistenzaUtente(id);
		RispostaUtenteCompletoDTO risposta = new RispostaUtenteCompletoDTO();
		Utente utente = utenteRepo.findById(id).orElse(null);
		UtenteCompletoDTO utenteCompletoDTO = utenteBuilder.buildUtenteCompletoDTO(utente);
		if(utente != null) {
			List<LabelValue> list = new ArrayList<LabelValue>();
			
			for(Utente user : utente.getGruppoUtenti()) {
				LabelValue item = new LabelValue();
				item.setKey(user.getCodice());
				item.setValue(user.getDescrizione());
				
				list.add(item);
			}
			
			utenteCompletoDTO.setManagersToImpersonate(list);
		}
		
		risposta.setData(utenteCompletoDTO);
		return risposta;
	}
	
	public String invioEmailIndizione(Integer idConf, InvioMailIndizioneDTO mailDTO, boolean sendnonpec) {
		accessValidator.consolleAdminAccesso();
		String subject = "Indizione conferenza";
		String from = "prova@eng.it";
		String textmessage = "Messaggio di Prova \n procedere all'autoaccreditamento  ";
		Conferenza conferenza = confRepo.findById(idConf).orElse(null);
		TipologiaDocumento tipologiaDocumento = tipoDocRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE).orElse(null);
		Documento documento = documentoRepo.findByConferenzaAndTipologiaDocumento(conferenza, tipologiaDocumento).orElse(null);
		if (documento == null) {
			throw new NotFoundEx("document not found");
		}
		Ente ente = enteRepo.findById(mailDTO.getIdEnte()).orElse(null);
		Partecipante partecipante = null;
		if (conferenza != null && ente != null) {
			partecipante = partRepo.findByConferenzaAndEnte(conferenza, ente).orElse(null);
		}
		if (partecipante == null) {
			throw new NotFoundEx("participant not found");
		}
		
		//@TODO: verificare nuova metodologia!
		//emailRepoService.sendMailIndizioneForConferenceMailSpecifica(idConf, from, textmessage, subject, null,documento, mailDTO.getEmail(), partecipante);
		emailRepoService.sendSingleMailIndizioneForConferenceMailSpecifica(idConf, from, textmessage, subject, null,documento, mailDTO.getEmail(), partecipante, sendnonpec);
		
		return "ok";
	}

	public ListaEntePreviewDTO findEnteAll(EnteRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoEnteRicerca(ricerca.getColonnaOrdinamento()));
		Iterable<Ente> itEnte = enteRepo.findAll(filtroEnte(ricerca), page);
		ListaEntePreviewDTO lista = new ListaEntePreviewDTO();
		for (Ente ente : itEnte) {
			EntePreviewDTO enteDTO = new EntePreviewDTO();
			enteDTO.setNome(ente.getDescrizioneEnte());
			enteDTO.setFlagAmmProc(ente.getFlagAmministrazioneProcedente());
			enteDTO.setPec(ente.getPecEnte());
			enteDTO.setIdEnte(ente.getIdEnte());
			lista.getList().add(enteDTO);
		}
		lista.setTotalNumber(enteRepo.count(filtroEnte(ricerca)));
		return lista;
	}

	private Specification<Ente> filtroEnte(EnteRicerca ricerca) {
		List<SearchCriteria> parametriRicerca = ricercaEntePerNomeFlagProcedente(ricerca);
		SpecificationBuilderEnte builder = new SpecificationBuilderEnte();
		return builder.buildAnd(parametriRicerca);
	}

	private List<SearchCriteria> ricercaEntePerNomeFlagProcedente(EnteRicerca ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca.getNome() != null && !ricerca.getNome().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("descrizioneEnte", "%", ricerca.getNome()));
		}
		if (ricerca.getFlagAmmProc() != null) {
			parametriRicerca.add(new SearchCriteria("flagAmministrazioneProcedente", "=", ricerca.getFlagAmmProc()));
		}
		return parametriRicerca;
	}

	public RispostaListaResponsabileConfPreviewDTO findResponsabiliConferenzaAll(
			RicercaResponsabileConferenzaDTO ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaResponsabileConfPreviewDTO lista = findResponsabiliConferenza(ricerca);
		RispostaListaResponsabileConfPreviewDTO risposta = new RispostaListaResponsabileConfPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}

	private ListaResponsabileConfPreviewDTO findResponsabiliConferenza(RicercaResponsabileConferenzaDTO ricerca) {
		ListaResponsabileConfPreviewDTO lista = new ListaResponsabileConfPreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoRicercaRespConferenza(ricerca.getColonnaOrdinamento()));
		Specification<RicercaResponsabileConferenza> spec = filtroRespConferenza(ricerca);
		Iterable<RicercaResponsabileConferenza> itRicercaRespConf = ricercaRespConfRepo.findAll(spec, page);
		for (RicercaResponsabileConferenza ricercaResp : itRicercaRespConf) {
			lista.getList().add(consolleBuilder.ricercaRespConferenzaToRespConfPreview(ricercaResp));
		}
		lista.setTotalNumber(ricercaRespConfRepo.count(spec));
		return lista;
	}

	private Specification<RicercaResponsabileConferenza> filtroRespConferenza(
			RicercaResponsabileConferenzaDTO ricerca) {
		List<SearchCriteria> criteriaPersona = criteriaPersona(ricerca.getValue());
		List<SearchCriteria> criteriaAmmProcedente = criteriaAmmProcedente(ricerca.getAmministrazioneProcedente());
		SpecificationBuilderRicercaRespConferenza builder = new SpecificationBuilderRicercaRespConferenza();
		return builder.buildAllOr(criteriaPersona).and(builder.buildAnd(criteriaAmmProcedente));
	}

	private List<SearchCriteria> criteriaPersona(String value) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (value != null && !value.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nomeResponsabile", "%", value));
			parametriRicerca.add(new SearchCriteria("cognomeResponsabile", "%", value));
			parametriRicerca.add(new SearchCriteria("codiceFiscaleResponsabile", "%", value));
		}
		return parametriRicerca;
	}

	private List<SearchCriteria> criteriaAmmProcedente(String ammProcedente) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ammProcedente != null && !ammProcedente.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("ammProcedente", "%", ammProcedente));
		}
		return parametriRicerca;
	}

	public String eliminaUtente(Integer id) {
		accessValidator.consolleAdminAccesso();
		Utente utente = accessValidator.validateCambioProfiloUtente(id);
		deleteResponsabile(utente);
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null);
		List<Integer> lista = rubricaRichRepo.findByPersona(persona).stream().map(rr -> rr.getIdRubricaRichiedenti())
				.collect(Collectors.toList());
		for (Integer idRr : lista) {
			eliminaRichiedentePrecaricato(idRr);
		}
		deletePersona(persona);
		utente.setDataFine(new Date());
		utenteRepo.save(utente);
		return "ok";
	}
	
	private void deletePersona(Persona persona) {
		accessValidator.validateDeletePersona(persona);
		persona.setDataFine(new Date());
		personaRepo.save(persona);
	}

	public RispostaListaPrecaricamentoRichiedentePreviewDTO findRichiedentePrecaricatoAll(
			PrecaricamentoRichiedenteRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaPrecaricamentoRichiedentePreviewDTO lista = findRichiedentePrecaricato(ricerca);
		RispostaListaPrecaricamentoRichiedentePreviewDTO risposta = new RispostaListaPrecaricamentoRichiedentePreviewDTO();
		risposta.setData(lista);
		return risposta;
	}

	private ListaPrecaricamentoRichiedentePreviewDTO findRichiedentePrecaricato(
			PrecaricamentoRichiedenteRicerca ricerca) {
		ListaPrecaricamentoRichiedentePreviewDTO lista = new ListaPrecaricamentoRichiedentePreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoRichiedenteRicerca(ricerca.getColonnaOrdinamento()));
		Specification<RicercaRubricaRichiedenti> spec = filtroRubricaRichiedente(ricerca);
		Iterable<RicercaRubricaRichiedenti> it = ricercaRubrRichRepo.findAll(spec, page);
		for(RicercaRubricaRichiedenti ricercaRub: it) {
			lista.getList().add(consolleBuilder.buildRicercaRubricaRichiedentiToPrecRichPreviewDTO(ricercaRub));
		}
		lista.setTotalNumber(ricercaRubrRichRepo.count(spec));
		return lista;
	}

	private Specification<RicercaRubricaRichiedenti> filtroRubricaRichiedente(
			PrecaricamentoRichiedenteRicerca ricerca) {
		List<SearchCriteria> criteriaRichiedente = criteriaRichiedente(ricerca.getValue());
		List<SearchCriteria> criteriaImpresa = criteriaImpresa(ricerca.getImpresa(), ricerca.getCollegamentoAImpresa());
		List<SearchCriteria> criteriaTipoConf = criteriaTipoConfRich(ricerca.getCodiceTipoConf());
		SpecificationBuilderRicercaRubricaRichiedenti builder = new SpecificationBuilderRicercaRubricaRichiedenti();
		return builder.buildAllOr(criteriaRichiedente).and(builder.buildAnd(criteriaImpresa))
				.and(builder.buildAnd(criteriaTipoConf));
	}

	private List<SearchCriteria> criteriaTipoConfRich(String codiceTipoConf) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (codiceTipoConf != null && !codiceTipoConf.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("codiceTipoConfRubricaRichiedenti", "=", codiceTipoConf));
		}
		return parametriRicerca;
	}

	private List<SearchCriteria> criteriaImpresa(String impresa, Boolean collegamentoAImpresa) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (impresa != null && !impresa.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("descrizioneImpresa", "%", impresa));
		} else {
			if(collegamentoAImpresa != null && !collegamentoAImpresa) {
				parametriRicerca.add(new SearchCriteria("descrizioneImpresa", "is null", impresa));	
			}
		}
		return parametriRicerca;
	}
	
	private List<SearchCriteria> criteriaRichiedente(String value) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (value != null && !value.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nomeRichiedente", "%", value));
			parametriRicerca.add(new SearchCriteria("cognomeRichiedente", "%", value));
			parametriRicerca.add(new SearchCriteria("codiceFiscaleRichiedente", "%", value));
		}
		return parametriRicerca;
	}

	public RispostaPrecaricamentoRichiedenteDTO findRichiedentePrecaricatoSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaRichiedenti rubricaRich = accessValidator.validateRubricaRichiedenti(id);
		PrecaricamentoRichiedenteDTO prec = consolleBuilder.buildRubricaRichiedenteToPrecRichiedenteDTO(rubricaRich);
		RispostaPrecaricamentoRichiedenteDTO risposta = new RispostaPrecaricamentoRichiedenteDTO();
		risposta.setData(prec);
		return risposta;
	}

	public IdentifiableDTO creaAggiornaRichiedentePrecaricato(Integer id, PrecaricamentoRichiedenteDTO richiedenteDTO) {
		Persona persona = creaAggiornaPersonaEAggiornaUtente(consolleBuilder.buildPrecRichiedenteDTOToPersonaDTO(richiedenteDTO));
		RubricaRichiedenti rubricaRich = new RubricaRichiedenti();
		if (id != null) {
			rubricaRich = rubricaRichRepo.findById(id).orElse(null);
		}
		rubricaRich.setPersona(persona);
		if (richiedenteDTO.getTipoConf() != null) {
			TipologiaConferenzaSpecializzazione tipoConfSpec = tipoConfSpecRepo.findById(richiedenteDTO.getTipoConf().getKey()).orElse(null);
			Boolean cambioTipologiaConf = !tipoConfSpec.equals(rubricaRich.getTipologiaConferenzaSpecializzazione());
			if (cambioTipologiaConf) {
				accessValidator.validateDuplicatoRubricaRich(rubricaRich);
				rubricaRich.setTipologiaConferenzaSpecializzazione(tipoConfSpec);
			}
		}
		if (richiedenteDTO.getFlagDissociaRichiedente() != null && richiedenteDTO.getFlagDissociaRichiedente()) {
			rubricaRich.setImpresa(null);
			rubricaRich.setPrincipale(null);
		}
		if (richiedenteDTO.getImpresa() != null) {
			RubricaImprese rubricaImprese = accessValidator
					.validateRubricaImpresa(Integer.parseInt(richiedenteDTO.getImpresa().getKey()));
			accessValidator.validateRubricaImpreseTipoConf(rubricaRich.getTipologiaConferenzaSpecializzazione(),
					rubricaImprese.getTipologiaConferenzaSpecializzazione());
			rubricaRich.setPrincipale(richiedenteDTO.getFlagRichPrincipale());
			rubricaRich.setImpresa(rubricaImprese);
		}
		RubricaRichiedenti rubricaRichSaved = rubricaRichRepo.save(rubricaRich);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(rubricaRichSaved.getIdRubricaRichiedenti());
		return identifiableDTO;
	}

	public Persona creaAggiornaPersonaEAggiornaUtente(PersonaDTO personaDTO) {
		Persona personaSaved = personaRepo.save(partBuilder.buildPersona(personaDTO));
		AggiornaUtente(personaDTO);
		return personaSaved;
	}

	public IdentifiableDTO aggiornaRichiedentePrecaricato(Integer id, PrecaricamentoRichiedenteDTO richiedenteDTO) {
		accessValidator.consolleAdminAccesso();
		accessValidator.validateRubricaRichiedenti(id);
		return creaAggiornaRichiedentePrecaricato(id, richiedenteDTO);
	}

	public IdentifiableDTO creaRichiedentePrecaricato(PrecaricamentoRichiedenteDTO richiedenteDTO) {
		accessValidator.consolleAdminAccesso();
		TipologiaConferenzaSpecializzazione tipoConfSpec = tipoConfSpecRepo.findById(richiedenteDTO.getTipoConf().getKey()).orElse(null);
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(richiedenteDTO.getCodiceFiscale()).orElse(null);
		accessValidator.validateEsistenzaRubricaRichiedenti(tipoConfSpec, persona);
		Integer idRichiedentePrecaricato = null;
		return creaAggiornaRichiedentePrecaricato(idRichiedentePrecaricato, richiedenteDTO);
	}

	public String eliminaRichiedentePrecaricato(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaRichiedenti rubricaRich = accessValidator.validateRubricaRichiedenti(id);
		rubricaRichRepo.delete(rubricaRich);
		return "ok";
	}

	public RispostaListaPrecaricamentoPartecipantePreviewDTO findPartecipantePrecaricatoAll(
			PrecaricamentoPartecipanteRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaPrecaricamentoPartecipantePreviewDTO lista = findPartecipantePrecaricato(ricerca);
		RispostaListaPrecaricamentoPartecipantePreviewDTO risposta = new RispostaListaPrecaricamentoPartecipantePreviewDTO();
		risposta.setData(lista);
		return risposta;
	}

	private ListaPrecaricamentoPartecipantePreviewDTO findPartecipantePrecaricato(
			PrecaricamentoPartecipanteRicerca ricerca) {
		ListaPrecaricamentoPartecipantePreviewDTO lista = new ListaPrecaricamentoPartecipantePreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoPartecipanteRicerca(ricerca.getColonnaOrdinamento()));
		Specification<RicercaRubricaPartecipanti> spec = filtroRubricaPartecipante(ricerca);
		Iterable<RicercaRubricaPartecipanti> it = ricercaRubPartRepo.findAll(spec, page);
		for (RicercaRubricaPartecipanti ricercaPart: it) {
			lista.getList().add(consolleBuilder.buildRubricaPartecipantiToPrecPartecipantePreviewDTO(ricercaPart));
		}
		lista.setTotalNumber(ricercaRubPartRepo.count(spec));
		return lista;
	}
	
	private Specification<RicercaRubricaPartecipanti> filtroRubricaPartecipante(
			PrecaricamentoPartecipanteRicerca ricerca) {
		List<SearchCriteria> criteriaEnte = criteriaEnteRicercaRubPart(ricerca);
		List<SearchCriteria> criteriaTipoConf = criteriaTipoConfRicercaRubPart(ricerca.getCodiceTipoConf());
		SpecificationBuilderRicercaRubricaPartecipanti builder = new SpecificationBuilderRicercaRubricaPartecipanti();
		return builder.buildAllOr(criteriaEnte).and(builder.buildAnd(criteriaTipoConf));
	}

	private List<SearchCriteria> criteriaTipoConfRicercaRubPart(String codiceTipoConf) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (codiceTipoConf != null && !codiceTipoConf.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("codiceTipologiaConferenza", "=", codiceTipoConf));
		}
		return parametriRicerca;
	}

	private List<SearchCriteria> criteriaEnteRicercaRubPart(PrecaricamentoPartecipanteRicerca ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca.getNome() != null && !ricerca.getNome().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nomeEnte", "%", ricerca.getNome()));
		}
		return parametriRicerca;
	}

	public RispostaPrecaricamentoPartecipanteCompletoDTO findPartecipantePrecaricatoSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaPartecipanti rubricaPart = accessValidator.validateRubricaPartecipanti(id);
		PrecaricamentoPartecipanteCompletoDTO prec = consolleBuilder.buildRubricaPartecipantiToPrecPartecipanteCompletoDTO(rubricaPart);
		RispostaPrecaricamentoPartecipanteCompletoDTO risposta = new RispostaPrecaricamentoPartecipanteCompletoDTO();
		risposta.setData(prec);
		return risposta;
	}

	public IdentifiableDTO creaAggiornaPartecipantePrecaricato(Integer id, PrecaricamentoPartecipanteDTO partecipantDTO) {
		RubricaPartecipanti rubricaPart;
		if (id == null) {
			rubricaPart = new RubricaPartecipanti();
			accessValidator.datiObbligatoriPrecaricamentoPartecipanteDTO(partecipantDTO);
			if (partecipantDTO.getEnte() != null) {
				Ente ente = enteRepo.findById(Integer.parseInt(partecipantDTO.getEnte().getKey())).orElse(null);
				rubricaPart.setEnte(ente);
			}
		} else {
			rubricaPart = rubricaPartRepo.findById(id).orElse(null);
		}
		if (partecipantDTO.getTipologiaConferenza() != null) {
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione = tipoConfSpecRepo
					.findById(partecipantDTO.getTipologiaConferenza().getKey()).orElse(null);
			Boolean cambioTipologiaConf = !tipologiaConferenzaSpecializzazione.equals(rubricaPart.getTipologiaConferenzaSpecializzazione());
			if (cambioTipologiaConf) {
				rubricaPart.setTipologiaConferenzaSpecializzazione(tipologiaConferenzaSpecializzazione);
				accessValidator.validateRubricaPartecipantiEsistenza(rubricaPart);
			}
		}
		if (partecipantDTO.getRuoloPartecipante() != null) {
			RuoloPartecipante ruolo = ruoloPartRepo.findById(partecipantDTO.getRuoloPartecipante().getKey())
					.orElse(null);
			rubricaPart.setRuoloPartecipante(ruolo);
		}
		List<CompetenzaAutorizzativa> listaCompAuto = new ArrayList<>();
		partecipantDTO.getCompetenzaAutorizzativa().stream().map(label -> label.getKey())
				.forEach(key -> listaCompAuto.add(compAutoRepo.findById(key).orElse(null)));
		rubricaPart.getListaCompAutoPerRbricaPartecipante().clear();
		rubricaPart.setListaCompAutoPerRbricaPartecipante(listaCompAuto);
		RubricaPartecipanti saved = rubricaPartRepo.save(rubricaPart);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdRubricaPartecipanti());
		return identifiableDTO;
	}

	public IdentifiableDTO aggiornaPartecipantePrecaricato(Integer id, PrecaricamentoPartecipanteDTO partecipantDTO) {
		accessValidator.consolleAdminAccesso();
		return creaAggiornaPartecipantePrecaricato(id, partecipantDTO);
	}

	public IdentifiableDTO creaPartecipantePrecaricato(PrecaricamentoPartecipanteDTO partecipantDTO) {
		accessValidator.consolleAdminAccesso();
		Integer idRubricaPartecante = null;
		return creaAggiornaPartecipantePrecaricato(idRubricaPartecante, partecipantDTO);
	}

	public String eliminaPartecipantePrecaricato(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaPartecipanti rubricaPart = accessValidator.validateRubricaPartecipanti(id);
		rubricaPart.getListaCompAutoPerRbricaPartecipante().clear();
		rubricaPartRepo.delete(rubricaPart);
		return "ok";
	}

	public RispostaListaPrecaricamentoImpresaPreviewDTO findImpresaPrecaricataPaginataAll(
			PrecaricamentoImpresaRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaPrecaricamentoImpresaPreviewDTO lista = findImpresaPrecaricata(ricerca);
		RispostaListaPrecaricamentoImpresaPreviewDTO risposta = new RispostaListaPrecaricamentoImpresaPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}

	private ListaPrecaricamentoImpresaPreviewDTO findImpresaPrecaricata(PrecaricamentoImpresaRicerca ricerca) {
		ListaPrecaricamentoImpresaPreviewDTO lista = new ListaPrecaricamentoImpresaPreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoImpresaRicerca(ricerca.getColonnaOrdinamento()));
		Specification<RicercaRubricaImprese> spec = filtroRubricaImprese(ricerca);
		Iterable<RicercaRubricaImprese> it = ricercaRubImpRepo.findAll(spec, page);
		for (RicercaRubricaImprese ricercaImprese: it) {
			lista.getList().add(consolleBuilder.buildRubricaImpreseToPrecImpresaPreviewDTO(ricercaImprese));
		}
		lista.setTotalNumber(ricercaRubImpRepo.count(spec));
		return lista;
	}

	private Specification<RicercaRubricaImprese> filtroRubricaImprese(PrecaricamentoImpresaRicerca ricerca) {
		List<SearchCriteria> criteriaRubricaImpresa = criteriaRubricaImpresa(ricerca);
		List<SearchCriteria> criteriaTipoConf = criteriaTipoConfRicercaRubPart(ricerca.getTipologiaConferenza());
		SpecificationBuilderRicercaRubricaImprese builder = new SpecificationBuilderRicercaRubricaImprese();
		return builder.buildAllOr(criteriaRubricaImpresa).and(builder.buildAnd(criteriaTipoConf));
	}

	private List<SearchCriteria> criteriaRubricaImpresa(PrecaricamentoImpresaRicerca ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca.getValue() != null && !ricerca.getValue().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nome", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("codiceFiscale", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("partitaIva", "%", ricerca.getValue()));
		}
		return parametriRicerca;
	}

	public RispostaPrecaricamentoImpresaDTO findImpresaPrecaricataSingola(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaImprese rubricaImprese = accessValidator.validateRubricaImprese(id);
		PrecaricamentoImpresaDTO prec = consolleBuilder.buildRubricaImpreseToPrecImpresaDTO(rubricaImprese);
		RispostaPrecaricamentoImpresaDTO risposta = new RispostaPrecaricamentoImpresaDTO();
		risposta.setData(prec);
		return risposta;
	}

	public RispostaListaImpresaPreviewDTO findImpresaPaginataAll(ImpresaRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaImpresaPreviewDTO lista = findImpresa(ricerca);
		RispostaListaImpresaPreviewDTO risposta = new RispostaListaImpresaPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}

	private ListaImpresaPreviewDTO findImpresa(ImpresaRicerca ricerca) {
		ListaImpresaPreviewDTO lista = new ListaImpresaPreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoImpresa(ricerca.getColonnaOrdinamento()));
		Specification<Impresa> spec = filtroImpresa(ricerca);
		Iterable<Impresa> it = impresaRepo.findAll(spec, page);
		for (Impresa impresa: it) {
			lista.getList().add(consolleBuilder.buildImpresaToImpresaPreviewDTO(impresa));
		}
		lista.setTotalNumber(impresaRepo.count(spec));
		return lista;
	}

	private Specification<Impresa> filtroImpresa(ImpresaRicerca ricerca) {
		List<SearchCriteria> criteriaAnagraficaImpresa = criteriaAnagraficaImpresa(ricerca);
		SpecificationBuilderImpresa builder = new SpecificationBuilderImpresa();
		return builder.buildAllOr(criteriaAnagraficaImpresa);
	}

	private List<SearchCriteria> criteriaAnagraficaImpresa(ImpresaRicerca ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca.getValue() != null && !ricerca.getValue().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("denominazione", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("codiceFiscale", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("partitaIva", "%", ricerca.getValue()));
		}
		return parametriRicerca;
	}

	public IdentifiableDTO associaImpresaInRubrica(PrecaricamentoImpresaCompletaDTO impresaDTO) {
		accessValidator.consolleAdminAccesso();
		Boolean associaImpresa = Boolean.TRUE;
		accessValidator.datiObbligatoriPrecaricamentoImpresaCompletaDTO(impresaDTO, associaImpresa);
		
		Impresa impresa = impresaRepo.findByPartitaIvaIgnoreCase(impresaDTO.getPartitaIva()).orElse(null);
		TipologiaConferenzaSpecializzazione tipoConfSpec = accessValidator
				.esistenzaTipologiaConferenzaSpec(impresaDTO.getTipologiaConferenza().getKey());
		accessValidator.esistenzaRubricaImpresa(impresa, tipoConfSpec);
		if (impresa == null) {
			impresa = creaAggiornaImpresa(consolleBuilder.buildPrecImpresaCompletaDTOToImpresaDTO(impresaDTO));
		}
		RubricaImprese rubricaImprese = new RubricaImprese();
		rubricaImprese.setImpresa(impresa);
		rubricaImprese.setTipologiaConferenzaSpecializzazione(tipoConfSpec);
		rubricaImprese.setStepConferenzaModificabili(impresaDTO.getStepConferenzaModificabili());
		rubricaImprese.setListaStepConferenzaModificabili(consolleBuilder.concatenaStep(impresaDTO.getStepAttivi()));
		RubricaImprese rubricaImpreseSaved = rubricaImprRepo.save(rubricaImprese);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(rubricaImpreseSaved.getIdRubricaImprese());
		return identifiableDTO;
	}

	private Impresa creaAggiornaImpresa(ImpresaDTO impresaDTO) {
		Impresa impresa = impresaRepo.save(consolleBuilder.buildImpresa(impresaDTO));
		return impresa;
	}
	
	public IdentifiableDTO modificaRubricaImpresa(PrecaricamentoImpresaCompletaDTO impresaDTO, Integer id) {
		accessValidator.consolleAdminAccesso();
		Boolean associaImpresa = Boolean.FALSE;
		accessValidator.datiObbligatoriPrecaricamentoImpresaCompletaDTO(impresaDTO, associaImpresa);
		RubricaImprese rubricaImprese = accessValidator.validateRubricaImpresa(id);
		Impresa impresa = creaAggiornaImpresa(consolleBuilder.buildPrecImpresaCompletaDTOToImpresaDTO(impresaDTO));
		rubricaImprese.setImpresa(impresa);
		if (impresaDTO.getStepConferenzaModificabili() != null) {
			rubricaImprese.setStepConferenzaModificabili(impresaDTO.getStepConferenzaModificabili());
		}
		if (impresaDTO.getStepAttivi() != null) {
			rubricaImprese.setListaStepConferenzaModificabili(consolleBuilder.concatenaStep(impresaDTO.getStepAttivi()));
		}
		TipologiaConferenzaSpecializzazione tipoConfSpec = null;
		if (impresaDTO.getTipologiaConferenza() != null) {
			rubricaImprese.setTipologiaConferenzaSpecializzazione(
					tipoConfSpecRepo.findById(impresaDTO.getTipologiaConferenza().getKey()).orElse(null));
			accessValidator.esistenzaRubricaImpresa(impresa, tipoConfSpec);
		}
		RubricaImprese rubricaImpreseSaved = rubricaImprRepo.save(rubricaImprese);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(rubricaImpreseSaved.getIdRubricaImprese());
		return identifiableDTO;
	}

	public String separaImpresaInRubrica(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaImprese rubricaImprese = accessValidator.validateRubricaImprese(id);
		rubricaImprRepo.delete(rubricaImprese);
		return "ok";
	}

	public String eliminaImpresa(Integer id) {
		accessValidator.consolleAdminAccesso();
		Impresa impresa = accessValidator.validateDeleteImpresa(id);
		List<RubricaImprese> listaRubricaImprese = rubricaImprRepo.findByImpresa(impresa);
		for (RubricaImprese rubricaImprese: listaRubricaImprese) {
			List<RubricaRichiedenti> listaRubricaRichiedenti = rubricaRichRepo.findByImpresa(rubricaImprese);
			for (RubricaRichiedenti rubricaRichiedenti: listaRubricaRichiedenti) {
				eliminaRichiedentePrecaricato(rubricaRichiedenti.getIdRubricaRichiedenti());
			}
			separaImpresaInRubrica(rubricaImprese.getIdRubricaImprese());
		}
		impresaRepo.delete(impresa);
		return "ok";
	}
	
	public Utente creaUtente(UtenteDTO utenteDTO) {
		Utente utente = utenteRepo.findByCodiceFiscaleIgnoreCase(utenteDTO.getCodiceFiscale()).orElse(null);
		if (utente == null) {
			utente = utenteRepo.save(utenteBuilder.buildUtente(utenteDTO));
		}
		return utente;		
	}

	public RispostaEnteDTO findEnteSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		accessValidator.esistenzaEnte(id);
		RispostaEnteDTO risposta = new RispostaEnteDTO();
		risposta.setData(enteBuilder.enteToEnteDTO((enteRepo.findById(id).orElse(null))));
		return risposta;
	}

	public RispostaResponsabileConfCompletoDTO findResponsabiliConferenzaSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		accessValidator.esistenzaResponsabileConferenza(id);
		RispostaResponsabileConfCompletoDTO risposta = new RispostaResponsabileConfCompletoDTO();
		risposta.setData(consolleBuilder.buildrespConfToRespConfCompletoDTO(respConfRepo.findById(id).orElse(null)));
		return risposta;
	}

	public RispostaListaConferenzaAnteprimaDTO findConferenzaAll(RicercaUnifyDTO ricerca) {
		accessValidator.consolleAdminAccesso();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonneConferenza(ricerca.getColonnaOrdinamento()));
		ListaConferenzaAnteprimaDTO lista = new ListaConferenzaAnteprimaDTO();
		Specification<RicercaConferenza> spec = filtroConferenza(ricerca);
		Iterable<RicercaConferenza> it = ricercaConfRepo.findAll(spec, page);
		for (RicercaConferenza ricercaConferenza: it) {
			lista.getList().add(confBuilder.buildRicercaUnifyToConferenzaAnteprimaDTO(ricercaConferenza));
		}
		lista.setTotalNumber(ricercaConfRepo.count(spec));
		RispostaListaConferenzaAnteprimaDTO risposta = new RispostaListaConferenzaAnteprimaDTO();
		risposta.setData(lista);
		return risposta;
	}

	private Specification<RicercaConferenza> filtroConferenza(RicercaUnifyDTO ricerca) {
		List<SearchCriteria> criteriaConferenza = criteriaConferenza(ricerca);
		RicercaConferenzaSpecificationsBuilder builder = new RicercaConferenzaSpecificationsBuilder();
		return builder.buildAllOr(criteriaConferenza);
	}

	private List<SearchCriteria> criteriaConferenza(RicercaUnifyDTO ricerca) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricerca.getValue() != null && !ricerca.getValue().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("riferimentoIstanza", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneTipologiaConferenza", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneStato", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("nomeRichiedente", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("cognomeRichiedente", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneProvincia", "%", ricerca.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneComune", "%", ricerca.getValue()));
			try {
				Integer numero = Integer.parseInt(ricerca.getValue());
				parametriRicerca.add(new SearchCriteria("idConferenza", "=", numero));
			} catch (NumberFormatException e) {
				logger.debug("parametro di ricerca non numerico, non viene effettuata la ricerca su idConferenza");
			}
		}
		return parametriRicerca;
	}

	public RispostaConferenzaConsolleDTO findConferenzaSingola(Integer id) {
		accessValidator.consolleAdminAccesso();
		accessValidator.esistenzaConferenza(id);
		RispostaConferenzaConsolleDTO risposta = new RispostaConferenzaConsolleDTO();
		risposta.setData(consolleBuilder.buildConferenzaToConferenzaConsolleDTO(confRepo.findById(id).orElse(null)));
		return risposta;
	}

	public RispostaListaLabelValueDTO findImpresaAll(String key) {
		accessValidator.consolleAdminAccesso();
		Sort sort = new Sort(Direction.ASC, "denominazione");
		List<Impresa> listaImpresa = null;
		if (key == null || key.isEmpty()) {
			listaImpresa = impresaRepo.findAll(sort);
		} else {
			listaImpresa = impresaRepo.findByDenominazioneContainingIgnoreCase(key, sort);
		}
		return findTypologicalAll(listaImpresa);
	}
	
	private RispostaListaLabelValueDTO findTypologicalAll(List<? extends _Typological> listaTypological) {
		ListaLabelValue lista = new ListaLabelValue();
		for (_Typological typological : listaTypological) {
			lista.getList()
					.add(new LabelValue(typological.getCodice(), messageSource.getMessage(typological.getDescrizione(),
							null, typological.getDescrizione(), request.getLocale())));
		}
		lista.setTotalNumber(new Long(listaTypological.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaImpresaDTO findImpresaSingola(Integer id) {
		accessValidator.consolleAdminAccesso();
		Impresa impresa = accessValidator.esistenzaImpresa(id);
		RispostaImpresaDTO risposta = new RispostaImpresaDTO();
		risposta.setData(confBuilder.buildImpresaDTO(impresa));
		return risposta;
	}

	public RispostaListaLabelValueDTO findPersoneAll(String key) {
		Sort sort = new Sort(Direction.ASC, "cognome").and(new Sort(Direction.ASC, "nome"));
		List<Persona> listaPersone = new ArrayList<>();
		if (key == null || key.isEmpty()) {
			listaPersone = this.personaRepo.findAll(sort);
		} else {
			listaPersone = this.personaRepo.findByNomeContainingOrCognomeContainingAllIgnoreCase(key, key, sort);
		}
		ListaLabelValue lista = new ListaLabelValue();
		for (Persona persona : listaPersone) {
			lista.getList().add(new LabelValue(Integer.toString(persona.getIdPersona()),
					persona.getCognome() + " " + persona.getNome() + " " + persona.getCodiceFiscale()));
		}
		lista.setTotalNumber(Long.valueOf(listaPersone.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaPersonaDTO findPersonaSingola(Integer id) {
		accessValidator.consolleAdminAccesso();
		Persona persona = accessValidator.esistenzaPersona(id);
		PersonaDTO personaDTO = partBuilder.buildPersonaDTO(persona);
		RispostaPersonaDTO risposta = new RispostaPersonaDTO();
		risposta.setData(personaDTO);
		return risposta;
	}

	public RispostaListaLabelValueDTO findImpresaPrecaricataAll(PrecaricamentoImpresaRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaLabelValue lista = new ListaLabelValue();
		Specification<RicercaRubricaImprese> spec = filtroRubricaImprese(ricerca);
		Iterable<RicercaRubricaImprese> it = ricercaRubImpRepo.findAll(spec);
		for (RicercaRubricaImprese ricercaImprese: it) {
			lista.getList().add(new LabelValue(Integer.toString(ricercaImprese.getIdRubricaImprese()), ricercaImprese.getNome()));
		}
		lista.setTotalNumber(ricercaRubImpRepo.count(spec));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaListaLabelValueDTO findEntiPartecipantiConferenza(Integer id) {
		accessValidator.consolleAdminAccesso();
		Conferenza conferenza = accessValidator.esistenzaConferenza(id);
		List<Partecipante> listaPartecipanti = conferenza.getPartecipantes();
		ListaLabelValue lista = new ListaLabelValue();
		listaPartecipanti.stream().forEach(p -> lista.getList().add(createNotNullLabelValue(p.getEnte())));
		lista.setTotalNumber(Long.valueOf(lista.getList().size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public IdentifiableDTO creaEnte(EnteDTO enteDTO) {
		accessValidator.consolleAdminAccesso();
		accessValidator.datiObbligatoriEnteDTO(enteDTO);
		accessValidator.esistenzaEnteByCodiceFiscaleAndCodiceUfficio(enteDTO.getCodiceFiscale(), enteDTO.getCodiceUfficio());
		accessValidator.datiCongruenti(enteDTO);
		Ente enteEsistente = null;
		Ente ente = consolleBuilder.buildEnteDTOToEnte(enteDTO, enteEsistente);
		Ente savedEnte = enteRepo.save(ente);
		if (enteDTO.getFlagAmmProc()!=null) {
			creaProfiloEnte(ente);
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(savedEnte.getIdEnte());
		return identifiableDTO;
	}

	private void creaProfiloEnte(Ente ente) {
		if (profiloRepo.findByAmministrazioneProcedente(ente).size() == 0) {
			for (TipoProfilo tipoProfilo : tipoProfiloRepo.findAll()) {
				Profilo profilo = new Profilo();
				profilo.setAmministrazioneProcedente(ente);
				profilo.setTipoProfilo(tipoProfilo);
				profiloRepo.save(profilo);
			}
		}
	}

	public IdentifiableDTO modificaEntiDaConsolle(EnteDTO enteDTO, Integer id) {
		accessValidator.consolleAdminAccesso();
		Ente enteEsistente = enteRepo.findById(id).orElse(null);
		if (enteEsistente == null) {
			throw new NotFoundEx("Authority not found");
		}
		Ente ente = consolleBuilder.buildEnteDTOToEnte(enteDTO, enteEsistente);
		Ente savedEnte = enteRepo.save(ente);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(savedEnte.getIdEnte());
		return identifiableDTO;
	}

	public RispostaListaLabelValueDTO findTipologiaDocumentoPerConsolle() {
		accessValidator.consolleAdminAccesso();
		List<TipologiaDocumento> listaTipoDoc = tipoDocRepo.findByFlagUploadConsolle(Boolean.TRUE);
		ListaLabelValue lista = new ListaLabelValue();
		listaTipoDoc.stream().forEach(tipo -> lista.getList().add(createNotNullLabelValue(tipo)));
		lista.setTotalNumber(Long.valueOf(lista.getList().size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaDocumentoDTO creaDocumento(DocumentoFileDTO documentoFileDTO, Integer idConference) {
		accessValidator.consolleAdminAccesso();
		accessValidator.datiObbligatoriCaricamentoDocumenti(documentoFileDTO);
		accessValidator.controlloTipologieDocConsolle(documentoFileDTO);
		Documento documento = documentoService.creaDocumento(documentoFileDTO, idConference);
		DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documento);
		documentoService.invioEmailDocumentoPerTipologia(documento);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}

	public Utente aggiornaFirmatario(PersonaDTO personaDTO, Boolean flagFirmatario) {
		Utente utente = consolleBuilder.buildPersonaDTOToUtente(personaDTO);
		utente.setFlagFirmatario(flagFirmatario);
		if (utente != null) {
			utente = utenteRepo.saveAndFlush(utente);
		}
		return utente;
	}
	
	public RispostaListaProtocolloPreviewDTO findProtocolli(RicercaProtocollo ricerca) {
		accessValidator.consolleAdminAccesso();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				protocolloBuilder.mapColonne(mapColonneUtente(ricerca.getColonnaOrdinamento())));
		ListaProtocolloPreviewDTO lista = new ListaProtocolloPreviewDTO();
		if (ricerca.getValue() != null &&
				!ricerca.getValue().equals("")) {
			lista = protService.findProtocolliError(ricerca.getValue());
		} else {
			lista  = protService.findProtocolliError(null);
		}
		
		RispostaListaProtocolloPreviewDTO risposta = new RispostaListaProtocolloPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	public IdentifiableDTO patchProtocollo(Integer id) {
		return protService.patchProtocollo(id);
		
	}
	
	public RispostaListaPrecaricamentoDelegatoPreviewDTO findDelegatoPrecaricatoAll(
			PrecaricamentoDelegatoRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaPrecaricamentoDelegatoPreviewDTO lista = findDelegatoPrecaricato(ricerca);
		RispostaListaPrecaricamentoDelegatoPreviewDTO risposta = new RispostaListaPrecaricamentoDelegatoPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	private ListaPrecaricamentoDelegatoPreviewDTO findDelegatoPrecaricato(
			PrecaricamentoDelegatoRicerca ricerca) {
		ListaPrecaricamentoDelegatoPreviewDTO lista = new ListaPrecaricamentoDelegatoPreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoDelegatoRicerca(ricerca.getColonnaOrdinamento()));
		Specification<RicercaRubricaDelegati> spec = filtroRubricaDelegato(ricerca);
		Iterable<RicercaRubricaDelegati> it = ricercaRubrDelRepo.findAll(spec, page);
		for(RicercaRubricaDelegati ricercaRub: it) {
			lista.getList().add(consolleBuilder.buildRicercaRubricaDelegatoToPrecDelPreviewDTO(ricercaRub));
		}
		lista.setTotalNumber(ricercaRubrDelRepo.count(spec));
		return lista;
	}

	private Specification<RicercaRubricaDelegati> filtroRubricaDelegato(
			PrecaricamentoDelegatoRicerca ricerca) {
		List<SearchCriteria> criteriaDelegato = criteriaDelegato(ricerca.getValue());
		List<SearchCriteria> criteriaTipoConf = criteriaTipoConfDel(ricerca.getCodiceTipoConf());
		SpecificationBuilderRicercaRubricaDelegati builder = new SpecificationBuilderRicercaRubricaDelegati();
		return builder.buildAllOr(criteriaDelegato).and(builder.buildAnd(criteriaTipoConf));
	}

	private List<SearchCriteria> criteriaDelegato(String value) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (value != null && !value.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nomeDelegato", "%", value));
			parametriRicerca.add(new SearchCriteria("cognomeDelegato", "%", value));
			parametriRicerca.add(new SearchCriteria("codiceFiscaleDelegato", "%", value));
		}
		return parametriRicerca;
	}
	
	private List<SearchCriteria> criteriaTipoConfDel(String codiceTipoConf) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (codiceTipoConf != null && !codiceTipoConf.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("codiceTipoConfRubricaDelegati", "=", codiceTipoConf));
		}
		return parametriRicerca;
	}
	
	public RispostaPrecaricamentoDelegatoDTO findDelegatoPrecaricatoSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(id);
		PrecaricamentoDelegatoDTO prec = consolleBuilder.buildRubricaDelegatoToPrecDelegatoDTO(rubricaDel);
		RispostaPrecaricamentoDelegatoDTO risposta = new RispostaPrecaricamentoDelegatoDTO();
		risposta.setData(prec);
		return risposta;
	}
	
	public IdentifiableDTO aggiornaDelegatoPrecaricato(Integer id, PrecaricamentoDelegatoDTO delegatoDTO) {
		accessValidator.consolleAdminAccesso();
		accessValidator.validateRubricaDelegati(id);
		return creaAggiornaDelegatoPrecaricato(id, delegatoDTO);
	}

	public IdentifiableDTO creaDelegatoPrecaricato(PrecaricamentoDelegatoDTO delegatoDTO) {
		accessValidator.consolleAdminAccesso();
		TipologiaConferenzaSpecializzazione tipoConfSpec = tipoConfSpecRepo.findById(delegatoDTO.getTipoConf().getKey()).orElse(null);
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(delegatoDTO.getCodiceFiscale()).orElse(null);
		accessValidator.validateEsistenzaRubricaDelegati(tipoConfSpec, persona);
		Integer idDelegatoPrecaricato = null;
		return creaAggiornaDelegatoPrecaricato(idDelegatoPrecaricato, delegatoDTO);
	}

	public String eliminaDelegatoPrecaricato(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(id);
		if(rubricaDel != null)
			rubricaDelRepo.delete(rubricaDel);
		return "ok";
	}
	
	public IdentifiableDTO creaAggiornaDelegatoPrecaricato(Integer id, PrecaricamentoDelegatoDTO delegatoDTO) {
		Persona persona = creaAggiornaPersonaEAggiornaUtente(consolleBuilder.buildPrecDelagatoDTOToPersonaDTO(delegatoDTO));
		RubricaDelegati rubricaDel = new RubricaDelegati();
		if (id != null) {
			rubricaDel = rubricaDelRepo.findById(id).orElse(null);
		}
		rubricaDel.setPersona(persona);
		if (delegatoDTO.getTipoConf() != null) {
			TipologiaConferenzaSpecializzazione tipoConfSpec = tipoConfSpecRepo.findById(delegatoDTO.getTipoConf().getKey()).orElse(null);
			Boolean cambioTipologiaConf = !tipoConfSpec.equals(rubricaDel.getTipologiaConferenzaSpecializzazione());
			if (cambioTipologiaConf) {
				accessValidator.validateDuplicatoRubricaDel(rubricaDel);
				rubricaDel.setTipologiaConferenzaSpecializzazione(tipoConfSpec);
			}
		}
		RubricaDelegati rubricaDelSaved = rubricaDelRepo.save(rubricaDel);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(rubricaDelSaved.getIdRubricaDelegato());
		return identifiableDTO;
	}

	public IdentifiableDTO aggiornaDocumentoDelega(Integer id, MultipartFile file) {
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(id);
		RubricaDelegati rubricaDelSaved = null;
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		if(rubricaDel != null ) {
			String riferimentoEsterno = fileSystemService.storeFile(file, id.toString());
			if( riferimentoEsterno != null) {
				rubricaDel.setNomeDocumento(file.getOriginalFilename());
				rubricaDel.setRiferimentoEsterno(riferimentoEsterno);
			}
			rubricaDelSaved = rubricaDelRepo.save(rubricaDel);
			identifiableDTO.setId(rubricaDelSaved.getIdRubricaDelegato());
		}
		return identifiableDTO;
	}
	
	public IdentifiableDTO cancellaDocumentoDelega(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(id);
		RubricaDelegati rubricaDelSaved = null;
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		if(rubricaDel != null && rubricaDel.getNomeDocumento() != null) {
			rubricaDel.setNomeDocumento(null);
			rubricaDel.setRiferimentoEsterno(null);
			rubricaDelSaved = rubricaDelRepo.save(rubricaDel);
			identifiableDTO.setId(rubricaDelSaved.getIdRubricaDelegato());
		}
		return identifiableDTO;
	}
	
	public RubricaDelegati getRubricaDelegato(Integer id){
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(id);
		return rubricaDel;
	}
	
	public Resource downloadDocumentoDelega(RubricaDelegati delegato) {
		Resource resource = null;
		accessValidator.consolleAdminAccesso();
		RubricaDelegati rubricaDel = accessValidator.validateRubricaDelegati(delegato.getIdRubricaDelegato());
		if(rubricaDel != null && rubricaDel.getRiferimentoEsterno() != null) {
			resource = fileSystemService.loadFileAsResourceFromFileSystemForDelegate(rubricaDel.getRiferimentoEsterno());
		}
		return resource;
	}

	public IdentifiableDTO creaPreaccreditato(PreaccreditatoDTO preaccreditatoDTO) {
		accessValidator.consolleAdminAccesso();
		Integer idRubricaPreaccreditato = null;
		return creaAggiornaPreaccreditatoPerEnte(idRubricaPreaccreditato, preaccreditatoDTO);
	}
	
	public RispostaListaPreaccreditatiPreviewDTO findPreaccreditatiAll(
			PreaccreditatoRicerca ricerca) {
		accessValidator.consolleAdminAccesso();
		ListaPreaccreditatiPreviewDTO lista = findPreaccreditati(ricerca);
		RispostaListaPreaccreditatiPreviewDTO risposta = new RispostaListaPreaccreditatiPreviewDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	private ListaPreaccreditatiPreviewDTO findPreaccreditati(
			PreaccreditatoRicerca ricerca) {
		ListaPreaccreditatiPreviewDTO lista = new ListaPreaccreditatiPreviewDTO();
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				consolleBuilder.mapColonnaOrdinamentoPreaccreditatoRicerca(ricerca.getColonnaOrdinamento()));
		Specification<RicercaRubricaPreaccreditati> spec = filtroRubricaPreaccreditato(ricerca);
		Iterable<RicercaRubricaPreaccreditati> it = ricercaRubrPreaccreditaiRepo.findAll(spec, page);
		for(RicercaRubricaPreaccreditati ricercaRub: it) {
			lista.getList().add(consolleBuilder.buildRicercaRubricaPreaccreditatoPreviewDTO(ricercaRub));
		}
		lista.setTotalNumber(ricercaRubrPreaccreditaiRepo.count(spec));
		return lista;
	}
	
	private Specification<RicercaRubricaPreaccreditati> filtroRubricaPreaccreditato(
			PreaccreditatoRicerca ricerca) {
		List<SearchCriteria> criteriaPreaccreditato = criteriaPreaccreditato(ricerca.getValue());
		List<SearchCriteria> criteriaTipoConf = criteriaTipoConf(ricerca.getCodiceTipoConf());
		SpecificationBuilderRicercaRubricaPreaccreditati builder = new SpecificationBuilderRicercaRubricaPreaccreditati();
		return builder.buildAllOr(criteriaPreaccreditato).and(builder.buildAnd(criteriaTipoConf));
	}

	private List<SearchCriteria> criteriaPreaccreditato(String value) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (value != null && !value.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("nomePreaccreditato", "%", value));
			parametriRicerca.add(new SearchCriteria("cognomePreaccreditato", "%", value));
			parametriRicerca.add(new SearchCriteria("codiceFiscalePreaccreditato", "%", value));
		}
		return parametriRicerca;
	}
	
	private List<SearchCriteria> criteriaTipoConf(String codiceTipoConf) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (codiceTipoConf != null && !codiceTipoConf.isEmpty()) {
			parametriRicerca.add(new SearchCriteria("codiceTipoConfPreaccreditato", "=", codiceTipoConf));
		}
		return parametriRicerca;
	}
	
	public RispostaPreaccreditatoDTO findPreaccreditatoSingolo(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaPreaccreditati rubricaPreaccr = accessValidator.validateRubricaPreaccreditati(id);
		PreaccreditatoDTO prec = consolleBuilder.buildRubricaPreaccreditatoToPreaccreditatoDTO(rubricaPreaccr);
		RispostaPreaccreditatoDTO risposta = new RispostaPreaccreditatoDTO();
		risposta.setData(prec);
		return risposta;
	}
	
	public IdentifiableDTO creaAggiornaPreaccreditatoPerEnte(Integer id, PreaccreditatoDTO preaccreditatoDTO) {
		RubricaPreaccreditati rubricaPreaccr;
		if (id == null) {
			rubricaPreaccr = new RubricaPreaccreditati();
			accessValidator.datiObbligatoriPreaccreditatiDTO(preaccreditatoDTO);
			if (preaccreditatoDTO.getEnte() != null) {
				Ente ente = enteRepo.findById(Integer.parseInt(preaccreditatoDTO.getEnte().getKey())).orElse(null);
				rubricaPreaccr.setEnte(ente);
			}
		} else {
			rubricaPreaccr = rubricaPreaccrRepo.findById(id).orElse(null);
		}
		Persona persona = creaAggiornaPersonaEAggiornaUtente(consolleBuilder.buildPreaccreditatoDTOToPersonaDTO(preaccreditatoDTO));
		rubricaPreaccr.setPersona(persona);
		if (preaccreditatoDTO.getTipoConf() != null) {
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione = tipoConfSpecRepo
					.findById(preaccreditatoDTO.getTipoConf().getKey()).orElse(null);
			Boolean cambioTipologiaConf = !tipologiaConferenzaSpecializzazione.equals(rubricaPreaccr.getTipologiaConferenzaSpecializzazione());
			if (cambioTipologiaConf) {
				rubricaPreaccr.setTipologiaConferenzaSpecializzazione(tipologiaConferenzaSpecializzazione);
				accessValidator.validateRubricaPreaccreditatoEsistenza(rubricaPreaccr);
			}
		}
		if (preaccreditatoDTO.getTipoProfilo() != null) {
			RuoloPersona ruolo = ruoloPersonaRepo.findById(preaccreditatoDTO.getTipoProfilo().getKey()).orElse(null);
			Boolean cambioRuolo = !ruolo.equals(rubricaPreaccr.getRuoloPersona());
			if(cambioRuolo) {
				rubricaPreaccr.setRuoloPersona(ruolo);
			}
		}

		RubricaPreaccreditati saved = rubricaPreaccrRepo.save(rubricaPreaccr);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdRubricaPreaccreditato());
		return identifiableDTO;
	}
	
	public String eliminaPreaccreditato(Integer id) {
		accessValidator.consolleAdminAccesso();
		RubricaPreaccreditati rubricaPreaccr = accessValidator.validateRubricaPreaccreditati(id);
		rubricaPreaccrRepo.delete(rubricaPreaccr);
		return "ok";
	}
}
