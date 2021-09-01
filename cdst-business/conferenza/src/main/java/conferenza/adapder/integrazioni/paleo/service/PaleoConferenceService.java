package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.DefinizioneDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.IstanzaDTO;
import conferenza.DTO.LocalizzazioneDTO;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.createconference.model.IntegFrontieraConferenza;
import conferenza.adapder.integrazioni.createconference.service.CreateConferenceService;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterTesta;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAllegatiAdapterDTO;
import conferenza.adapder.integrazioni.paleo.repository.IntegPaleoFrontieraConferenzaRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoFascicoloTipoConferenceRepository;
import conferenza.builder.ConferenzaBuilder;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Partecipante;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RuoloPartecipante;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;


@Service
public class PaleoConferenceService extends CreateConferenceService{

	@Autowired
	IntegPaleoFrontieraConferenzaRepository frontieraConfRepo;
	@Autowired
	PaleoFascicoloTipoConferenceRepository paleoFascicoloTipoConferenceRepository;
	
	@Autowired
	PaleoToFrontieraDTOService paleoToFrontieraDTOService;
	
	@Autowired
	PaleoAdapterService paleoAdapterService; 
	
	@Autowired
	ConferenzaBuilder conferenzaBuilder;

	@Autowired 
	UtenteService utenteService;
	
	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipologiaConferenzaSpecializzazioneRepository;
	
	public ConferenzaDTO fillConferenzaDTO(IntegFrontieraDTO frontiera, String tipologiaConferenza) {
		
		ConferenzaDTO conferenzaDto=new ConferenzaDTO();
		
		DefinizioneDTO definizione = new DefinizioneDTO();
		IstanzaDTO istanza = new IstanzaDTO();
		istanza.setTipologiaConferenza(conferenzaBuilder.createNotNullLabelValue(tipologiaConferenzaSpecializzazioneRepository.findById(tipologiaConferenza).get()));
		definizione.setIstanza(istanza);
		conferenzaDto.setDefinizione(definizione);
		
		RichiedenteDTO richiedente=new RichiedenteDTO() ;
		richiedente.setCodiceFiscaleRichiedente(frontiera.getRichiedente_cf());
		richiedente.setNomeRichiedente(frontiera.getRichiedente_nome());
		richiedente.setCognomeRichiedente(frontiera.getRichiedente_cognome());
		richiedente.setPec(frontiera.getRichiedente_pec());
		richiedente.setRiferimentoIstanza(frontiera.getNome_procedimento());
		
		if(frontiera.getTipologia() != null) {
			richiedente.setTipologia( new LabelValue(frontiera.getTipologia(), null) );
			if(frontiera.getAttivita() != null) {
				richiedente.setAttivita( new LabelValue(frontiera.getAttivita(), null));
				richiedente.setAzione( frontiera.getTipologia() != null ? new LabelValue(frontiera.getTipologia(), null):null );
			}
		}
		
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
		conferenzaDto.setStep(0);
		
		return conferenzaDto;
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 * @throws IOException
	 */
	public PaleoDocumentAdapterDTO storePaleoAdapter(IntegFrontieraDocumentDTO item) 
		    throws IOException {
		PaleoDocumentAdapterDTO paleoDTO=new 	PaleoDocumentAdapterDTO();
		paleoDTO.setId(item.getId());
		if(item.getId_paleo()!=null)
			paleoDTO.setIdPaleoDoc(Integer.parseInt(item.getId_paleo()));
		paleoDTO.setName(item.getNome());
		
		return paleoDTO;
    }	
	/**
	 * 
	 * @param list
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<PaleoDocumentAdapterDTO> storeListPaleoAdapterInFrontieraDocument(
			List<IntegFrontieraDocumentDTO> list) 
		    throws IOException {
				List <PaleoDocumentAdapterDTO> listdocument = new ArrayList<PaleoDocumentAdapterDTO>();
				if(list!=null &&! list.isEmpty())
		        for(IntegFrontieraDocumentDTO item: list) {
		        	listdocument.add(storePaleoAdapter(item));		        	
		        }				
				return listdocument;		        
	}		

	
	
	
	public Integer persistConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati) 
			throws IOException {			
		return persistConference(frontieraDTO, tipologiaConferenza, listaAllegati, 
				null,null);
	}
	
	/**
	 * 
	 * @param frontieraDTO
	 * @param listaAllegati
	 * @return
	 * @throws IOException
	 */
	public Integer persistConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati, 
			String amministrazionePrecedente,String utenteResponsabile) 
			throws IOException {
		ConferenzaDTO conferenzaDTO = this.fillConferenzaDTO(frontieraDTO, tipologiaConferenza);
		String codiceFiscaleCreatoreConferenza = (utenteService.getCodiceFiscaleUtente() != null)? utenteService.getCodiceFiscaleUtente().toUpperCase(): frontieraDTO.getResponsabile_cf();
		conferenzaDTO.setId(creaConferenza(conferenzaDTO, codiceFiscaleCreatoreConferenza));
		Conferenza conferenzaSaved = super.confService.findConferenceById(conferenzaDTO.getId());
		this.frontieraConfRepo.save(new IntegFrontieraConferenza(frontieraDTO.getId_fascicolo(), conferenzaSaved));
		creaPartecipanti(frontieraDTO, conferenzaDTO.getId(),amministrazionePrecedente, utenteResponsabile);

		String codiceFiscaleRepsonsabile = null;
		if(utenteResponsabile != null) {
			
			ResponsabileConferenza respConf = this.respConfRepo.findById(Integer.parseInt(utenteResponsabile)).get();
		
			if (respConf != null && respConf.getPersona() != null) {
				
				codiceFiscaleRepsonsabile = respConf.getPersona().getCodiceFiscale();	
			}
		} else {
			codiceFiscaleRepsonsabile = frontieraDTO.getResponsabile_cf();
		}
		
		//----------------------------------------------------------------------------------------------------
		//Lista dei Documenti Paleo - non contengono gli allegati..
		//List<PaleoDocumentAdapterDTO> listDocumentAdapterDTO = storeListPaleoAdapterInFrontieraDocument(frontieraDTO.getListaDocumenti());		
		//creaDocumenti(listDocumentAdapterDTO, conferenzaDTO.getId(), frontieraDTO.getResponsabile_cf());
		//----------------------------------------------------------------------------------------------------
		//Lista dei Allegati Documenti Paleo
		if(listaAllegati != null && !listaAllegati.isEmpty()) {
			List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO = PaleoDocumentAllegatiAdapterDTO.fillListDocumentAllegatiAdapterDTO (listaAllegati);		
			creaDocumentiAllegati(listDocumentAllegatiAdapterDTO, conferenzaDTO.getId(), codiceFiscaleRepsonsabile);
		}else {
			//1 - ottiene i soli allegati filtrati
			List<IntegFrontieraDocumentDTO> listAllegatiInIntegFrontiera= paleoToFrontieraDTOService.listAllegatiInIntegFrontieraByFilterList(listaAllegati);		
			//2 - gli allegati filtrati vengono trasformati nel fromato FTO per gli allegati..
			List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO = PaleoDocumentAllegatiAdapterDTO.fillListDocumentAllegatiAdapterDTO (listAllegatiInIntegFrontiera);
			//3 - vine chiamato il metodo corretto per gli  allegati filtrati
			creaDocumentiAllegati(listDocumentAllegatiAdapterDTO, conferenzaDTO.getId(), codiceFiscaleRepsonsabile);
		}		
		return conferenzaDTO.getId();
	}
	
	
	/**
	 * 
	 * @param listDocumentAdapterDTO
	 * @param idConference
	 * @param cfResponsabile
	 */
	public void creaDocumenti(List<PaleoDocumentAdapterDTO> listDocumentAdapterDTO, 
			Integer idConference, 
			String cfResponsabile) {
		RuoloPartecipante ruoloPartecipante = super.ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get();
		Partecipante enteProcedente = super.partRepo
				.findByRuoloPartecipanteAndConferenza(ruoloPartecipante, super.confRepo.findByIdConferenza(idConference))
				.get(0);
		Accreditamento accreditamentoResponsabile = 
				super.accreditamentoRepo.findByPartecipanteAndPersona(enteProcedente,
						super.personaRepo.findByCodiceFiscaleIgnoreCase(cfResponsabile).get()).get(0);
		if (listDocumentAdapterDTO != null) {
			for (PaleoDocumentAdapterDTO paleoDocumentAdapterDTO : listDocumentAdapterDTO) {
				DocumentoDTO documentoDTO = fillDocumentDTO(paleoDocumentAdapterDTO);
				//Paleo ha un a chiave doppia; per cui l'aggancio con il registro avviene con l'id della tabella PaleoDocumentAdapter
				super.documentoService.creaDocumentoRiferimento(documentoDTO, String.valueOf(paleoDocumentAdapterDTO.getId()) , 
						ModalitaSalvataggioFile.Paleo,
						FonteFile.paleo, idConference, accreditamentoResponsabile.getId());
			}
		}
	}
	
	/**
	 * 
	 * @param listDocumentAllegatiAdapterDTO
	 * @param idConference
	 * @param cfResponsabile
	 */
	public void creaDocumentiAllegati(List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO, 
			Integer idConference, 
			String cfResponsabile) {
		RuoloPartecipante ruoloPartecipante = super.ruoloPartRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get();
		Partecipante enteProcedente = super.partRepo
				.findByRuoloPartecipanteAndConferenza(ruoloPartecipante, super.confRepo.findByIdConferenza(idConference))
				.get(0);
		Accreditamento accreditamentoResponsabile = 
				super.accreditamentoRepo.findByPartecipanteAndPersona(enteProcedente,super.personaRepo.findByCodiceFiscaleIgnoreCase(cfResponsabile).get()).get(0);
		if (listDocumentAllegatiAdapterDTO != null) {
			for (PaleoDocumentAllegatiAdapterDTO paleoDocumentAllegatoAdapterDTO : listDocumentAllegatiAdapterDTO) {
				DocumentoDTO documentoDTO = fillDocumentAllegatiDTO(paleoDocumentAllegatoAdapterDTO);
				//Paleo ha un a chiave doppia; per cui l'aggancio con il registro avviene con l'id della tabella PaleoDocumentAdapter
				super.documentoService.creaDocumentoRiferimento(documentoDTO, String.valueOf(paleoDocumentAllegatoAdapterDTO.getId()) , 
						ModalitaSalvataggioFile.AllegatiPaleo,
						FonteFile.allegatipaleo, idConference, accreditamentoResponsabile.getId());
			}
		}
	}
	
	
	private DocumentoDTO fillDocumentAllegatiDTO(PaleoDocumentAllegatiAdapterDTO paleoDocumentAllegatoAdapterDTO) {
		DocumentoDTO doc = new DocumentoDTO();
		doc.setNomeFile(paleoDocumentAllegatoAdapterDTO.getName());
		doc.setTipoDocumento(createNotNullLabelValue(
				super.tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;	
	}

	public DocumentoDTO fillDocumentDTO(PaleoDocumentAdapterDTO paleoDocumentAdapterDTO) {
		DocumentoDTO doc = new DocumentoDTO();
		doc.setNomeFile(paleoDocumentAdapterDTO.getName());
		doc.setTipoDocumento(createNotNullLabelValue(
				super.tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;			
	}	
	
	public Boolean syncronize(Conferenza conferenza) throws Exception {
		if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())
					|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())) {
			paleoAdapterService.refreshFilesFromFascicoloPaleo(conferenza);
		}

		return true;
	}

}
