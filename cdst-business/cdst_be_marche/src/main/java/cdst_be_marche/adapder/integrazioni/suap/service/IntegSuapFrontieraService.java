package cdst_be_marche.adapder.integrazioni.suap.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.AlfrescoDocumentAdapterDTO;
import cdst_be_marche.DTO.ConferenzaDTO;
import cdst_be_marche.DTO.DocumentoDTO;
import cdst_be_marche.DTO.EnteDTO;
import cdst_be_marche.DTO.FonteFile;
import cdst_be_marche.DTO.LocalizzazioneDTO;
import cdst_be_marche.DTO.ModalitaSalvataggioFile;
import cdst_be_marche.DTO.PartecipanteDTO;
import cdst_be_marche.DTO.PersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.PraticaDTO;
import cdst_be_marche.DTO.RichiedenteDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.adapder.documentale.DocumentAdapterService;
import cdst_be_marche.adapder.integrazioni.suap.IntegSuapFrontieraConfigurator;
import cdst_be_marche.adapder.integrazioni.suap.DTO.IntegSuapFrontieraDTO;
import cdst_be_marche.adapder.integrazioni.suap.DTO.IntegSuapFrontieraDocumentDTO;
import cdst_be_marche.adapder.integrazioni.suap.DTO.IntegSuapFrontieraEntiDTO;
import cdst_be_marche.adapder.integrazioni.suap.model.IntegSuapFrontieraConferenza;
import cdst_be_marche.adapder.integrazioni.suap.persistance.IntegFrontieraConferenzaRepository;
import cdst_be_marche.builder.PartecipanteBuilder;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.AlfrescoDocumentAdapter;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RuoloPartecipante;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.EnteRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.RuoloPartecipanteRepository;
import cdst_be_marche.repository.RuoloPersonaRepository;
import cdst_be_marche.repository.TipologiaDocumentoRepository;
import cdst_be_marche.service.CaricaComboService;
import cdst_be_marche.service.ConferenzaService;
import cdst_be_marche.service.DocumentoService;
import cdst_be_marche.service.ParticipantService;
import cdst_be_marche.service._BaseService;
import cdst_be_marche.util.DbConst;

/**
 * 
 * @author guideluc
 *
 */
@Transactional
@Service
public class IntegSuapFrontieraService extends _BaseService{

	private static final Logger logger = LogManager.getLogger(IntegSuapFrontieraService.class.getName());
	
	@Autowired
	IntegFrontieraConferenzaRepository frontieraConfRepo;
	
    @Autowired
    private DocumentAdapterService adapterService;
    
	@Autowired
	private cdst_be_marche.adapder.alfresco.AlfrescoHelper alfrescoHelper;
	
	@Autowired
	IntegSuapFrontieraConfigurator integConfigurator;
		
	@Autowired
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
	ConferenzaService confService;
	
	@Autowired
	ConferenzaRepository confRepo;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	TipologiaDocumentoRepository tipologiaDocumentoRepository;
	
	@Autowired
	AccreditamentoRepository accreditamentoRepo;
	
	@Autowired
	PartecipanteRepository partRepo;
	
	@Autowired
	PersonaRepository personaRepo;
		
	public AlfrescoDocumentAdapterDTO storeAlfrescoAdapter(IntegSuapFrontieraDocumentDTO item) 
    throws IOException {
		AlfrescoDocumentAdapterDTO document = new AlfrescoDocumentAdapterDTO();        
        document.setAlfrescoId(item.getId_alfresco());           
        document.setIdpraticaExt(String.valueOf(item.getId_pratica()));       
        Document docCMSI=alfrescoHelper.getAlfrescoDocumentById(item.getId_alfresco()); 
        logger.info (docCMSI.getContentStreamMimeType());        
        if(docCMSI!=null) {
        	document.setName(docCMSI.getName());
        }	
        //Persistenza del file Caricato su Alfresco
        AlfrescoDocumentAdapter adapter=adapterService.storeAlfrescoDocument(document);
        document.setId(adapter.getId());	
        return document;
	}
	
	/**
	 * 
	 * @param list
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<AlfrescoDocumentAdapterDTO> storeListAlfrescoAdapterIntegSuapFrontieraDocument(List<IntegSuapFrontieraDocumentDTO> list) 
		    throws IOException {
				List <AlfrescoDocumentAdapterDTO> listdocument = new ArrayList<AlfrescoDocumentAdapterDTO>();        
		        for(IntegSuapFrontieraDocumentDTO item: list) {
		        	listdocument.add(storeAlfrescoAdapter(item));		        	
		        }				
				return listdocument;		        
	}	
	
	public void creaPartecipanti(IntegSuapFrontieraDTO frontieraDTO, Integer id) {
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
				this.partBuilder.enteToEnteDTO(this.enteRepo.findById(DbConst.ENTE_RICHIEDENTE).get()));
		richiedentePartecipante.setDescEnte("----");
		richiedentePartecipante.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)).get()));
		PersonaRuoloConferenzaDTO richiedente = fillRichiedenteDTO(frontieraDTO);
		richiedente.setProfilo(createNotNullLabelValue(
				this.ruoloPersRepo.findById(Integer.toString(DbConst.RUOLO_PERSONA_RICHIEDENTE)).get()));
		richiedentePartecipante.getListaUtente().add(richiedente);
		List<EnteDTO> listaEnti = new ArrayList<>();
		frontieraDTO.getEntiPartecipanti().stream()
				.map(f -> fillEnteDTO(f))
				.forEach(e -> {e.setRuolo(createNotNullLabelValue(this.ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_COMPETENTE)).get()));
				e.setFlagAmmProc(Boolean.FALSE);
				e.setId(this.comboService.creaEnte(e).getId());
				listaEnti.add(e);
					});
		List<PartecipanteDTO> listaPatecipanti = listaEnti.stream()
				.map(e -> this.partBuilder.buildEnteDTOToPartecipanteDTO(e)).collect(Collectors.toList());
		listaPatecipanti.add(ammProcPartecipante);
		listaPatecipanti.add(richiedentePartecipante);
		listaPatecipanti.stream().forEach(p -> this.partService.creaPartecipante(p, id));
		}

	public Integer creaConferenza(ConferenzaDTO conferenzaDTO, String cf) {
		Integer idConferenza = this.confService.creaConferenza(conferenzaDTO).getId();
		Conferenza conferenza = this.confService.findConferenceById(idConferenza);
		conferenza.setCodiceFiscaleCreatoreConferenza(cf);
		this.confRepo.save(conferenza);
		return idConferenza;
	}

	public void creaDocumenti(List<AlfrescoDocumentAdapterDTO> listAlfrescoDocumentAdapterDTO, Integer idConference, String cfResponsabile) {
		RuoloPartecipante ruoloPartecipante = ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get();
		Partecipante enteProcedente = partRepo
				.findByRuoloPartecipanteAndConferenza(ruoloPartecipante, confRepo.findByIdConferenza(idConference))
				.get(0);
		Accreditamento accreditamentoResponsabile = accreditamentoRepo.findByPartecipanteAndPersona(enteProcedente,
				personaRepo.findByCodiceFiscaleIgnoreCase(cfResponsabile).get()).get(0);
		if (listAlfrescoDocumentAdapterDTO != null) {
			for (AlfrescoDocumentAdapterDTO alfrescoDocumentAdapterDTO : listAlfrescoDocumentAdapterDTO) {
				DocumentoDTO documentoDTO = fillDocumentDTO(alfrescoDocumentAdapterDTO);
				String idAlfresco = alfrescoDocumentAdapterDTO.getAlfrescoId();
				documentoService.creaDocumentoRiferimento(documentoDTO, idAlfresco, ModalitaSalvataggioFile.Alfresco,
						FonteFile.suap, idConference, accreditamentoResponsabile.getId());
			}
		}
	}
	
	public ConferenzaDTO fillConferenzaDTO(IntegSuapFrontieraDTO frontiera) {
		
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
	
	public PersonaRuoloConferenzaDTO fillResponsabileDTO(IntegSuapFrontieraDTO frontieraDTO) {
		PersonaRuoloConferenzaDTO personaRuolo = new PersonaRuoloConferenzaDTO();
		personaRuolo.setNome(frontieraDTO.getResponsabile_nome());
		personaRuolo.setCognome(frontieraDTO.getResponsabile_cognome());
		personaRuolo.setCodiceFiscale(frontieraDTO.getResponsabile_cf());
		personaRuolo.setEmail(frontieraDTO.getResponsabile_pec());
		return personaRuolo;
	}
	
	public PersonaRuoloConferenzaDTO fillRichiedenteDTO(IntegSuapFrontieraDTO frontieraDTO) {
		PersonaRuoloConferenzaDTO personaRuolo = new PersonaRuoloConferenzaDTO();
		personaRuolo.setNome(frontieraDTO.getRichiedente_nome());
		personaRuolo.setCognome(frontieraDTO.getRichiedente_cognome());
		personaRuolo.setCodiceFiscale(frontieraDTO.getRichiedente_cf());
		personaRuolo.setEmail(frontieraDTO.getRichiedente_pec());
		return personaRuolo;
	}
	
	public DocumentoDTO fillDocumentDTO(AlfrescoDocumentAdapterDTO alfrescoDocumentAdapterDTO) {
		DocumentoDTO doc = new DocumentoDTO();
		doc.setNomeFile(alfrescoDocumentAdapterDTO.getName());
		doc.setTipoDocumento(createNotNullLabelValue(
				tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;			
	}

	public EnteDTO fillEnteDTO (IntegSuapFrontieraEntiDTO frontieraEnteDTO) {
		EnteDTO enteDTO = new EnteDTO();
		enteDTO.setNome(frontieraEnteDTO.getEnte_nome());
		enteDTO.setDescrizione(frontieraEnteDTO.getEnte_nome());
		enteDTO.setCodiceFiscale(frontieraEnteDTO.getId().toString());
		enteDTO.setPec(frontieraEnteDTO.getEnte_pec());
		return enteDTO;
	}

	/**
	 * Salvataggio conferenza, partecipanti e documenti a partire da un oggetto IntegSuapFrontieraDTO
	 * @param frontieraDTO
	 * @return 
	 * @throws IOException
	 */
	public Integer persistConference(IntegSuapFrontieraDTO frontieraDTO) throws IOException {
		ConferenzaDTO conferenzaDTO = fillConferenzaDTO(frontieraDTO);
		conferenzaDTO.setId(creaConferenza(conferenzaDTO, frontieraDTO.getResponsabile_cf()));
		Conferenza conferenzaSaved = this.confService.findConferenceById(conferenzaDTO.getId());
		this.frontieraConfRepo.save(new IntegSuapFrontieraConferenza(frontieraDTO.getId_pratica(), conferenzaSaved));
		creaPartecipanti(frontieraDTO, conferenzaDTO.getId());

		/*
		 * creazione dei documenti
		 */
		List<AlfrescoDocumentAdapterDTO> listAlfrescoDocumentAdapterDTO = storeListAlfrescoAdapterIntegSuapFrontieraDocument(
				frontieraDTO.getListaDocumenti());
		creaDocumenti(listAlfrescoDocumentAdapterDTO, conferenzaDTO.getId(), frontieraDTO.getResponsabile_cf());
		return conferenzaDTO.getId();
	}

	public String getUrlConferenza(Integer idConferenza) {
		//TODO: impostare in tutti i file application... l'url giusta
		String baseUrlDettaglioConferenza = integConfigurator.getBaseUrlDettaglioConferenza();
		if (baseUrlDettaglioConferenza == null || !baseUrlDettaglioConferenza.contains("{idConferenza}")) {
			baseUrlDettaglioConferenza = "http://localhost:8080/conferences/{idConferenza}";
		}
		return baseUrlDettaglioConferenza.replaceAll("\\{idConferenza\\}", idConferenza.toString());
	}
	
	public static void main(String[] args) {
		String baseUrlDettaglioConferenza = "https://suaplazio.esf.eng.it/conferenza-suap/conference/{idConferenza}/edit";
		if (baseUrlDettaglioConferenza == null || !baseUrlDettaglioConferenza.contains("{idConferenza}")) {
			baseUrlDettaglioConferenza = "http://localhost:8080/conferences/{idConferenza}";
		}
		String ret = baseUrlDettaglioConferenza.replaceAll("\\{idConferenza\\}", "4");
		System.out.println(ret);
	}
}
