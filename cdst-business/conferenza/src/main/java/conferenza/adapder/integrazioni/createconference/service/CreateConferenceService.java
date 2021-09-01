package conferenza.adapder.integrazioni.createconference.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.LocalizzazioneDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraEntiDTO;
import conferenza.builder.EnteBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Persona;
import conferenza.model.ResponsabileConferenza;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.service.CaricaComboService;
import conferenza.service.ConferenzaService;
import conferenza.service.DocumentoService;
import conferenza.service.ParticipantService;
import conferenza.service._BaseService;
import conferenza.util.DbConst;

/**
 * Espone servizzi "Comuni" per la creazione di una conferenza da Servizio di integrazione..
 * @author guideluc
 *
 */
@Service
public class CreateConferenceService  extends _BaseService{

	
	private static final Logger logger = LogManager.getLogger(CreateConferenceService.class.getName());
	
	
    @Autowired
    private DocumentAdapterService adapterService;
    
	@Autowired
	private conferenza.adapder.alfresco.AlfrescoHelper alfrescoHelper;
	
		
	@Autowired
	protected
	RuoloPartecipanteRepository ruoloPartRepo;
	
	@Autowired
	RuoloPersonaRepository ruoloPersRepo;
	
	@Autowired
	ParticipantService partService;
	
	@Autowired
	PartecipanteBuilder partBuilder;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	CaricaComboService comboService;
	
	@Autowired
	EnteBuilder enteBuilder;
	
	@Autowired
	protected
	ConferenzaService confService;
	
	@Autowired
	protected
	ConferenzaRepository confRepo;
	
	@Autowired
	protected
	DocumentoService documentoService;
	
	@Autowired
	protected
	TipologiaDocumentoRepository tipologiaDocumentoRepository;
	
	@Autowired
	protected
	AccreditamentoRepository accreditamentoRepo;
	
	@Autowired
	protected
	PartecipanteRepository partRepo;
	
	@Autowired
	protected
	PersonaRepository personaRepo;
	
	@Autowired
	protected
	ResponsabileConferenzaRepository respConfRepo;	

	public void creaPartecipanti(IntegFrontieraDTO frontieraDTO, Integer id) {
		creaPartecipanti(frontieraDTO, id,null,null);
	}
	
	public void creaPartecipanti(IntegFrontieraDTO frontieraDTO, Integer id,String amministrazionePrecedente,String utenteResponsabile) {
		if(utenteResponsabile != null && amministrazionePrecedente != null) {
			Ente ente = this.enteRepo.findById(Integer.parseInt(amministrazionePrecedente)).get();
			EnteDTO ammProc  = fillEnteDTO(ente);
			ammProc.setFlagAmmProc(Boolean.TRUE);
			ammProc.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
					.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get()));
			ammProc.setId(this.comboService.creaEnte(ammProc).getId());
			PartecipanteDTO ammProcPartecipante = this.partBuilder.buildEnteDTOToPartecipanteDTO(ammProc);
			
			ResponsabileConferenza respConf = this.respConfRepo.findById(Integer.parseInt(utenteResponsabile)).get();
			
			if (respConf != null && respConf.getPersona() != null) {
				PersonaRuoloConferenzaDTO responsabile = fillResponsabileDTO(respConf.getPersona());
				responsabile.setProfilo(createNotNullLabelValue(
						this.ruoloPersRepo.findById(Integer.toString(DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA)).get()));
				ammProcPartecipante.getListaUtente().add(responsabile);
			}
			
			PartecipanteDTO richiedentePartecipante = this.partBuilder.buildEnteDTOToPartecipanteDTO(
					this.enteBuilder.enteToEnteDTO(this.enteRepo.findById(DbConst.ENTE_RICHIEDENTE).get()));
			richiedentePartecipante.setDescEnte("----");
			richiedentePartecipante.setRuolo(createNotNullLabelValue(
					this.ruoloPartRepo.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)).get()));
			if (frontieraDTO.getRichiedente_cf() != null) {
				PersonaRuoloConferenzaDTO richiedente = fillRichiedenteDTO(frontieraDTO);
				richiedente.setProfilo(createNotNullLabelValue(
						this.ruoloPersRepo.findById(Integer.toString(DbConst.RUOLO_PERSONA_RICHIEDENTE)).get()));
				richiedentePartecipante.getListaUtente().add(richiedente);
				richiedentePartecipante.setCf(richiedente.getCodiceFiscale());
				richiedentePartecipante.setPec(richiedente.getEmail());
			}
			
			List<EnteDTO> listaEnti = new ArrayList<>();
			frontieraDTO.getEntiPartecipanti().stream()
					.map(f -> fillEnteDTO(f))
					.forEach(e -> {e.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
					.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_COMPETENTE)).get()));
					e.setFlagAmmProc(Boolean.FALSE);
					e.setId(this.comboService.creaEnte(e).getId());
					listaEnti.add(e);
						});
			List<PartecipanteDTO> listaPatecipanti = listaEnti.stream().map(e -> this.partBuilder.buildEnteDTOToPartecipanteDTO(e)).collect(Collectors.toList());
			listaPatecipanti.add(ammProcPartecipante);
			listaPatecipanti.add(richiedentePartecipante);
			
			listaPatecipanti.stream().forEach(p -> this.partService.creaPartecipante(p, id));
			
		} else { 
			EnteDTO ammProc = fillEnteDTO(frontieraDTO.getEnteProcedente());
			ammProc.setFlagAmmProc(Boolean.TRUE);
			ammProc.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
					.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get()));
			ammProc.setId(this.comboService.creaEnte(ammProc).getId());
			PartecipanteDTO ammProcPartecipante = this.partBuilder.buildEnteDTOToPartecipanteDTO(ammProc);
			PersonaRuoloConferenzaDTO responsabile = fillResponsabileDTO(frontieraDTO);
			responsabile.setProfilo(createNotNullLabelValue(
					this.ruoloPersRepo.findById(Integer.toString(DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA)).get()));
			ammProcPartecipante.getListaUtente().add(responsabile);
			
			PartecipanteDTO richiedentePartecipante = this.partBuilder.buildEnteDTOToPartecipanteDTO(
					this.enteBuilder.enteToEnteDTO(this.enteRepo.findById(DbConst.ENTE_RICHIEDENTE).get()));
			richiedentePartecipante.setDescEnte("----");
			richiedentePartecipante.setRuolo(createNotNullLabelValue(
					this.ruoloPartRepo.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)).get()));
			if (frontieraDTO.getRichiedente_cf() != null) {
				PersonaRuoloConferenzaDTO richiedente = fillRichiedenteDTO(frontieraDTO);
				richiedente.setProfilo(createNotNullLabelValue(
						this.ruoloPersRepo.findById(Integer.toString(DbConst.RUOLO_PERSONA_RICHIEDENTE)).get()));
				richiedentePartecipante.getListaUtente().add(richiedente);
				richiedentePartecipante.setCf(richiedente.getCodiceFiscale());
				richiedentePartecipante.setPec(richiedente.getEmail());
			}
			
			List<EnteDTO> listaEnti = new ArrayList<>();
			frontieraDTO.getEntiPartecipanti().stream()
					.map(f -> fillEnteDTO(f))
					.forEach(e -> {e.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
					.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_COMPETENTE)).get()));
					e.setFlagAmmProc(Boolean.FALSE);
					e.setId(this.comboService.creaEnte(e).getId());
					listaEnti.add(e);
						});
			List<PartecipanteDTO> listaPatecipanti = listaEnti.stream().map(e -> this.partBuilder.buildEnteDTOToPartecipanteDTO(e)).collect(Collectors.toList());
			listaPatecipanti.add(ammProcPartecipante);
			listaPatecipanti.add(richiedentePartecipante);
			
			listaPatecipanti.stream().forEach(p -> this.partService.creaPartecipante(p, id));
		}
		
		
		}

	
	/**
	 * 
	 * @param conferenzaDTO
	 * @param cf - nei processi asincroni il responsabile non è definito!
	 * @return
	 */
	public Integer creaConferenza(ConferenzaDTO conferenzaDTO, String cf) {
		conferenzaDTO.setCodiceFiscaleCreatore(cf);
		Integer idConferenza = this.confService.creaConferenza(conferenzaDTO, true).getId();
		Conferenza conferenza = this.confService.findConferenceById(idConferenza);
		this.confRepo.save(conferenza);
		return idConferenza;
	}

	
	public ConferenzaDTO fillConferenzaDTO(IntegFrontieraDTO frontiera) {
		
		ConferenzaDTO conferenzaDto=new ConferenzaDTO();
		
		RichiedenteDTO richiedente=new RichiedenteDTO() ;
		richiedente.setCodiceFiscaleRichiedente(frontiera.getRichiedente_cf());
		richiedente.setNomeRichiedente(frontiera.getRichiedente_nome());
		richiedente.setCognomeRichiedente(frontiera.getRichiedente_cognome());
		richiedente.setPec(frontiera.getRichiedente_pec());
		richiedente.setRiferimentoIstanza(frontiera.getNome_procedimento());
		//imposto la data attuale
		richiedente.setDataAvvio(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		LocalizzazioneDTO localizzazione = new LocalizzazioneDTO();
		if (frontiera.getRichiedente_provincia_istat() != null) {
			localizzazione.setProvincia(new LabelValue(frontiera.getRichiedente_provincia_istat(),
					frontiera.getRichiedente_provincia_nome()));
		}
		if (frontiera.getRichiedente_comune_istat() != null) {
			localizzazione.setComune(new LabelValue(frontiera.getRichiedente_comune_istat(),
					frontiera.getRichiedente_comune_nome()));
		}
		
		/*if (frontiera.getEnteProcedente().getEnte_codice_provincia() != null) {
			localizzazione.setProvincia(new LabelValue(frontiera.getEnteProcedente().getEnte_codice_provincia(),
					frontiera.getEnteProcedente().getEnte_provincia()));
		}
		if (frontiera.getEnteProcedente().getEnte_codice_comune() != null) {
			localizzazione.setComune(new LabelValue(frontiera.getEnteProcedente().getEnte_codice_comune(),
					frontiera.getEnteProcedente().getEnte_comune()));
		}*/
		
		PraticaDTO pratica =new PraticaDTO();		
		pratica.setRichiedente(richiedente);
		pratica.setLocalizzazione(localizzazione);
				
		conferenzaDto.setPratica(pratica);
		conferenzaDto.setStep(1);
		
		return conferenzaDto;
	}
	
	public PersonaRuoloConferenzaDTO fillResponsabileDTO(IntegFrontieraDTO frontieraDTO) {
		PersonaRuoloConferenzaDTO personaRuolo = new PersonaRuoloConferenzaDTO();
		personaRuolo.setNome(frontieraDTO.getResponsabile_nome());
		personaRuolo.setCognome(frontieraDTO.getResponsabile_cognome());
		personaRuolo.setCodiceFiscale(frontieraDTO.getResponsabile_cf());
		personaRuolo.setEmail(frontieraDTO.getResponsabile_pec());
		return personaRuolo;
	}
	
	public PersonaRuoloConferenzaDTO fillResponsabileDTO(Persona pers) {
		PersonaRuoloConferenzaDTO personaRuolo = new PersonaRuoloConferenzaDTO();
		personaRuolo.setNome(pers.getNome());
		personaRuolo.setCognome(pers.getCognome());
		personaRuolo.setCodiceFiscale(pers.getCodiceFiscale());
		personaRuolo.setEmail(pers.getEmail());
		return personaRuolo;
	}
	
	public PersonaRuoloConferenzaDTO fillRichiedenteDTO(IntegFrontieraDTO frontieraDTO) {
		PersonaRuoloConferenzaDTO personaRuolo = new PersonaRuoloConferenzaDTO();
		personaRuolo.setNome(frontieraDTO.getRichiedente_nome());
		personaRuolo.setCognome(frontieraDTO.getRichiedente_cognome());
		personaRuolo.setCodiceFiscale(frontieraDTO.getRichiedente_cf());
		personaRuolo.setEmail(frontieraDTO.getRichiedente_pec());
		return personaRuolo;
	}
	

	public EnteDTO fillEnteDTO (IntegFrontieraEntiDTO frontieraEnteDTO) {
		EnteDTO enteDTO = new EnteDTO();
		enteDTO.setNome(frontieraEnteDTO.getEnte_nome());
		enteDTO.setDescrizione(frontieraEnteDTO.getEnte_nome());
		enteDTO.setCodiceFiscale(frontieraEnteDTO.getId().toString());
		enteDTO.setPec(frontieraEnteDTO.getEnte_pec());
		return enteDTO;
	}
	
	
	public EnteDTO fillEnteDTO (Ente ente) {
		EnteDTO enteDTO = new EnteDTO();
		enteDTO.setNome(ente.getDescrizione());
		enteDTO.setDescrizione(ente.getDescrizione());
		enteDTO.setCodiceFiscale(ente.getCodiceFiscaleEnte());
		enteDTO.setPec(ente.getPecEnte());
		return enteDTO;
	}



}
