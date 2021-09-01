package conferenza.adapder.integrazioni.domus.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.DefinizioneDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.IstanzaDTO;
import conferenza.DTO.LocalizzazioneDTO;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.documentale.service.DocumentalConferenceServiceInterface;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.createconference.model.IntegFrontieraConferenza;
import conferenza.adapder.integrazioni.createconference.service.CreateConferenceService;
import conferenza.adapder.integrazioni.domus.model.DomusPratica;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterAllegati;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterTesta;
import conferenza.adapder.integrazioni.domus.repository.DomusRegistryAdapterTestaRepository;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAllegatiAdapterDTO;
import conferenza.adapder.integrazioni.paleo.repository.IntegPaleoFrontieraConferenzaRepository;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.builder.ConferenzaBuilder;
import conferenza.model.Accreditamento;
import conferenza.model.Comune;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.FormaGiuridica;
import conferenza.model.Partecipante;
import conferenza.model.Provincia;
import conferenza.model.Regione;
import conferenza.model.RuoloPartecipante;
import conferenza.model.bean._Typological;
import conferenza.repository.CategoriaDocumentoRepository;
import conferenza.repository.ComuneRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.RegistroDocumentoTipoRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.service.DocumentoService;
import conferenza.service.FileSystemService;
import conferenza.util.DbConst;


@Service
public class DomusConferenceService extends CreateConferenceService {

		
	private static final Logger logger = LoggerFactory.getLogger(DomusConferenceService.class.getName());
	
	@Autowired
	ConferenzaBuilder conferenzaBuilder;
	
	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipologiaConferenzaRepository;
	
	@Autowired
	FileSystemService fileSystemService;
	
	@Autowired
	DocumentoService documentoService;
	@Autowired
	CategoriaDocumentoRepository categoriaDocumentoRepository;
	
	@Autowired
	ComuneRepository comuneRepository; 
	
	@Autowired
	ProvinciaRepository provinciaRepository;

	@Autowired
	ConferenzaRepository conferenzaRepository;
	
	@Autowired
	DomusRegistryAdapterTestaRepository domusRegistryAdapterTestaRepository;

	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	RegistroDocumentoTipoRepository registroDocumentoTipoRepository;
	
	@Autowired
	DomusService domusService;
	
	@Autowired
	PaleoAdapterService paleoAdapterService;
	
	@Autowired
	IntegPaleoFrontieraConferenzaRepository frontieraConfRepo;
	
	//===========================================================================================
	//	
	//===========================================================================================	
	/**
	 * 
	 * @param frontieraDTO =f{
	  
	   //0)		getResponsabile_cf
       //1) ConferenzaDTO.DefinizioneDTO.IstanzaDTO è funzione della tipologia di conferenza=f{ tipologiaConferenza }
	   //2) ConferenzaDTO.PraticaDTO.RichiedenteDTO    =f{ getRichiedente_cf, getRichiedente_nome, getRichiedente_cognome, getRichiedente_pec, getNome_procedimento }
	   //3) ConferenzaDTO.PraticaDTO.LocalizzazioneDTO =f( getRichiedente_provincia_istat, getRichiedente_comune_istat )
	 
	 * }
	 * @param listaAllegati
	 * 
	 * @param oggettoDeterminazione valore non passaato sulla frontiera..proprio dell'integrazione
	 * @return
	 * @throws IOException
	 */
	//@Transactional(
			//rollbackFor=DomusConferenceService.class, 
			//noRollbackFor=conferenza.adapder.integrazioni.domus.service.DomusConferenceService.class
	//)
	@Transactional(noRollbackFor = Exception.class)
	public Integer persistConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati,String oggettoDeterminazione) 
			throws IOException {
		
		//------------------------------------------------------------------------------------------------------------		
		//IntegFrontieraDTO to ConferenzaDTO per la creazione della conferenza a partire dai valori di Frontiera..
		ConferenzaDTO conferenzaDTO = this.fillConferenzaDTO(frontieraDTO, tipologiaConferenza);
		
		//------------------------------------------------------------------------------------------------------------
		//CREAZIONE DELLA CONFENREZA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		conferenzaDTO.setId(creaConferenza(conferenzaDTO, frontieraDTO.getResponsabile_cf()));
		Conferenza conferenzaSaved = super.confService.findConferenceById(conferenzaDTO.getId());
		conferenzaSaved.setOggettoDeterminazione(oggettoDeterminazione);
		//------------------------------------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------------------------------------
		//DLG:@TODO
		//------------------------------------------------------------------------------------------------------------
		this.frontieraConfRepo.save(new IntegFrontieraConferenza(frontieraDTO.getId_fascicolo(), conferenzaSaved));
		logger.debug("@domus frontiera saved: " + frontieraDTO.getId_fascicolo());
		
		//------------------------------------------------------------------------------------------------------------
		//CREAZIONE DEI PARTECIPANTI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//------------------------------------------------------------------------------------------------------------		
		creaPartecipanti(frontieraDTO, conferenzaDTO.getId());
		//------------------------------------------------------------------------------------------------------------	
		//Lista dei Documenti Paleo - non contengono gli allegati..
		//List<PaleoDocumentAdapterDTO> listDocumentAdapterDTO = storeListPaleoAdapterInFrontieraDocument(frontieraDTO.getListaDocumenti());		
		//creaDocumenti(listDocumentAdapterDTO, conferenzaDTO.getId(), frontieraDTO.getResponsabile_cf());
		//----------------------------------------------------------------------------------------------------
		//Lista dei Allegati Documenti Paleo
		//TODO fix perchè usa la lista degli allegati di paleo???
		if(listaAllegati != null && !listaAllegati.isEmpty()) {
			List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO = PaleoDocumentAllegatiAdapterDTO.fillListDocumentAllegatiAdapterDTO (listaAllegati);		
			creaDocumentiAllegati(listDocumentAllegatiAdapterDTO, conferenzaDTO.getId(), frontieraDTO.getResponsabile_cf());
		}else {
			//1 - ottiene i soli allegati filtrati
			//2 - gli allegati filtrati vengono trasformati nel fromato DTO per gli allegati..			
			//3 - vine chiamato il metodo corretto per gli  allegati filtrati
			//creaDocumentiAllegati(listDocumentAllegatiAdapterDTO, conferenzaDTO.getId(), frontieraDTO.getResponsabile_cf());
		}		
		return conferenzaDTO.getId();
	}	

public LabelValue createNotNullLabelValue(_Typological typological){
	if (typological != null) {
		String descrizione =typological.getDescrizione(); 
		return new LabelValue(typological.getCodice(), descrizione);
	}
	return null;
	
}	
	
//===========================================================================================
//	
//===========================================================================================	
/**
//1) ConferenzaDTO.DefinizioneDTO.IstanzaDTO è funzione della tipologia di conferenza=f{tipologiaConferenza}
//2) ConferenzaDTO.PraticaDTO.RichiedenteDTO =f{getRichiedente_cf,getRichiedente_nome,getRichiedente_cognome,getRichiedente_pec,getNome_procedimento}
//3) ConferenzaDTO.PraticaDTO.LocalizzazioneDTO =f(getRichiedente_provincia_istat,getRichiedente_comune_istat)
  
	 * @param frontiera
	 * @param tipologiaConferenza
	 * @return
*/
	private ConferenzaDTO fillConferenzaDTO(IntegFrontieraDTO frontiera, String tipologiaConferenza) {
		
		ConferenzaDTO conferenzaDto=new ConferenzaDTO();
		
		DefinizioneDTO definizione = new DefinizioneDTO();
		//1) ConferenzaDTO.DefinizioneDTO.IstanzaDTO è funzione della tipologia di conferenza=f{tipologiaConferenza}
		IstanzaDTO istanza = new IstanzaDTO();
		//Dà problemi di java.lang.IllegalStateException
		//istanza.setTipologiaConferenza(conferenzaBuilder.createNotNullLabelValue(tipologiaConferenzaRepository.findById(tipologiaConferenza).get()));
		istanza.setTipologiaConferenza(createNotNullLabelValue(tipologiaConferenzaRepository.findById(tipologiaConferenza).get()));
		
		definizione.setIstanza(istanza);
		conferenzaDto.setDefinizione(definizione);

		//2) ConferenzaDTO.PraticaDTO.RichiedenteDTO =f{getRichiedente_cf,getRichiedente_nome,getRichiedente_cognome,getRichiedente_pec,getNome_procedimento}
		RichiedenteDTO richiedente=new RichiedenteDTO() ;
		richiedente.setCodiceFiscaleRichiedente(frontiera.getRichiedente_cf());
		richiedente.setNomeRichiedente(frontiera.getRichiedente_nome());
		richiedente.setCognomeRichiedente(frontiera.getRichiedente_cognome());
		richiedente.setPec(frontiera.getRichiedente_pec());
		richiedente.setRiferimentoIstanza(frontiera.getNome_procedimento());
		//imposto la data attuale
		richiedente.setDataAvvio(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		richiedente.setTipologia(new LabelValue(frontiera.getTipologia(), null));
		richiedente.setAzione(new LabelValue(frontiera.getAzione(), null));
		richiedente.setAttivita(new LabelValue(frontiera.getAttivita(), null));

		//3) ConferenzaDTO.PraticaDTO.LocalizzazioneDTO =f(getRichiedente_provincia_istat,getRichiedente_comune_istat) 
		LocalizzazioneDTO localizzazione = new LocalizzazioneDTO();
		if (frontiera.getRichiedente_provincia_istat() != null) {
			localizzazione.setProvincia(new LabelValue(frontiera.getRichiedente_provincia_istat(),
					frontiera.getRichiedente_provincia_nome()));
		}
		if (frontiera.getRichiedente_comune_istat() != null) {
			localizzazione.setComune(new LabelValue(frontiera.getRichiedente_comune_istat(),
					frontiera.getRichiedente_comune_nome()));
		}
		
		//ConferenzaDTO.PraticaDTO
		PraticaDTO pratica =new PraticaDTO();		
		pratica.setRichiedente(richiedente);
		pratica.setLocalizzazione(localizzazione);
				
		conferenzaDto.setPratica(pratica);
		conferenzaDto.setStep(0);
		
		return conferenzaDto;
	}
	
	
	/**
	 *  =f{getName}
	 * 
	 * 
	 * @param paleoDocumentAllegatoAdapterDTO
	 * @return
	 */
	private DocumentoDTO fillDocumentAllegatiDTO(PaleoDocumentAllegatiAdapterDTO paleoDocumentAllegatoAdapterDTO) {
		DocumentoDTO doc = new DocumentoDTO();
		doc.setNomeFile(paleoDocumentAllegatoAdapterDTO.getName());
		doc.setTipoDocumento(createNotNullLabelValue(
				super.tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;	
	}

	/**
	 *  =f{getName}
	 * 
	 * @param paleoDocumentAdapterDTO
	 * @return
	 */
	private DocumentoDTO fillDocumentDTO(PaleoDocumentAdapterDTO paleoDocumentAdapterDTO) {
		DocumentoDTO doc = new DocumentoDTO();
		doc.setNomeFile(paleoDocumentAdapterDTO.getName());
		doc.setTipoDocumento(createNotNullLabelValue(
				super.tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;			
	}

	/**
	 * 
	 * @param listDocumentAllegatiAdapterDTO
	 * @param idConference
	 * @param cfResponsabile
	 */
	private  void creaDocumentiAllegati(List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO, 
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
		if (listDocumentAllegatiAdapterDTO != null) {
			for (PaleoDocumentAllegatiAdapterDTO paleoDocumentAllegatoAdapterDTO : listDocumentAllegatiAdapterDTO) {
				DocumentoDTO documentoDTO = fillDocumentAllegatiDTO(paleoDocumentAllegatoAdapterDTO);
				//Paleo ha un a chiave doppia; per cui l'aggancio con il registro avviene con l'id della tabella PaleoDocumentAdapter
				super.documentoService.creaDocumentoRiferimento(
						documentoDTO, 
						String.valueOf(paleoDocumentAllegatoAdapterDTO.getId()) , 
						ModalitaSalvataggioFile.Filesystem,
						FonteFile.conferenza, idConference, accreditamentoResponsabile.getId()
				);
			}
		}
	}
	
	
	/**
	 * 
	 * @param allegatiDomus
	 * @return
	 */
	private DocumentoDTO fillDocumentAllegatiDomus(DomusRegistryAdapterAllegati allegatiDomus) {
		DocumentoDTO doc = new DocumentoDTO();
		//Il nome  Del file ha un prefisso a seconda se è principale o meno
		doc.setNomeFile(("S".equals(allegatiDomus.getIsPrincipale())?"DocumentoPrincipale-":"Allegato-")+allegatiDomus.getNomeFile());
		doc.setTipoDocumento(createNotNullLabelValue(
				super.tipologiaDocumentoRepository.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).orElse(null)));
		return doc;	
	}	
	
	
	/**
	 * 
	 * @param listDocumentAdapterDTO
	 * @param idConference
	 * @param cfResponsabile
	 */
	public List<DocumentoDTO> creaDocumentiByreference(List<DomusRegistryAdapterAllegati> allegatiDomus, Integer idConference, String cfResponsabile) {
		
		List<DocumentoDTO> listaDocumentoDTO=new ArrayList<DocumentoDTO>();
		RuoloPartecipante ruoloPartecipante = super.ruoloPartRepo.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get();
		Partecipante enteProcedente = super.partRepo.findByRuoloPartecipanteAndConferenza(ruoloPartecipante, super.confRepo.findByIdConferenza(idConference)).get(0);
		Accreditamento accreditamentoResponsabile = 
				super.accreditamentoRepo.findByPartecipanteAndPersona(enteProcedente,
						super.personaRepo.findByCodiceFiscaleIgnoreCase(cfResponsabile).get()).get(0);
		if (allegatiDomus != null) {
			for (DomusRegistryAdapterAllegati item  : allegatiDomus) {
				
				//se il documento è già presente non salvare
				String externalReference = String.valueOf(item.getId());
				if(documentoService.isAlreadyPresentDocument(idConference, DbConst.REGISTRO_DOCUMENTO_FONTE_DOMUS, externalReference)) 
					continue;
				
				DocumentoDTO documentoDTO = fillDocumentAllegatiDomus(item);

				documentoDTO=super.documentoService.creaDocumentoRiferimento(documentoDTO, externalReference, 
						ModalitaSalvataggioFile.AllegatiDomus,
						FonteFile.allegatidomus, idConference, accreditamentoResponsabile.getId());
				
				listaDocumentoDTO.add(documentoDTO);
			}
		}
		return listaDocumentoDTO;
	}
	

	
	/**
	 * 
	 * @param file
	 * @param fileName
	 * @param idConferenza
	 * @return
	 * @throws IOException
	 */
	public  Documento storeFile(File file, String fileName,Integer idConferenza) throws IOException {		
		DocumentoFileDTO documentoFileDTO=new DocumentoFileDTO();
		int categoria=DbConst.CATEGORIA_DOCUMENTO_DOCUMENTAZIONE_CORREDO_ISTANZA;
		String tipoDocumento=DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA;  //DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA;		
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file",fileName, "text/plain", IOUtils.toByteArray(input));	    
		documentoFileDTO.setCategoria(categoriaDocumentoRepository.findById(String.valueOf(categoria)).orElse(null).getCodice());		
		documentoFileDTO.setFile(multipartFile);
		documentoFileDTO.setNomeFile(fileName);
		documentoFileDTO.setTipoDocumento(tipoDocumento);		
		Documento documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO,idConferenza); 				
		return documento;
	}
	
	
	/**
	 * 
	 * @param xmlIstanza
	 * @param idConferenza
	 * @throws Exception
	 */
	public void fillIntestatarioConferenza(DomusPratica xmlIstanza, Integer idConferenza, PraticaExtended pratica,
			String codiceFiscale) throws Exception {
		Conferenza conferenzaSaved = super.confService.findConferenceById(idConferenza);
		if(conferenzaSaved==null)
			throw new Exception("Problemi nella creazione della conferenza");
		
		Comune comunePratica=new Comune();
		Provincia provincia =new Provincia();
		Regione regione=new Regione();

		//Popola la localizzazione
		String denominazioneComune=xmlIstanza.getNomeComune();
		List<Comune> listComune=comuneRepository.findByDescrizioneContainingIgnoreCase(denominazioneComune, null);
		if(listComune!=null && ! listComune.isEmpty()){
			
			comunePratica=listComune.get(0);
			provincia = comunePratica.getProvincia();			
			regione= provincia.getRegione();
			
			conferenzaSaved.setLocalizzazioneComune(comunePratica);
			conferenzaSaved.setLocalizzazioneProvincia(provincia);
			conferenzaSaved.setLocalizzazioneIndirizzo(xmlIstanza.getAddress());
			conferenzaSaved.setFoglioMappale(xmlIstanza.getFoglioMappale());
		}else{
			logger.debug("Il tag comune è vuoto!");
			
		}
		
		if(xmlIstanza.getCfIntestatario()!=null && !"".equals(xmlIstanza.getCfIntestatario())){
			conferenzaSaved.setImpresaCodiceFiscale(xmlIstanza.getCfIntestatario());
			conferenzaSaved.setImpresaDenominazione(xmlIstanza.getCognomeIntestatario()+" "+xmlIstanza.getNomeIntestatario());
			FormaGiuridica impresaFormaGiuridica =new FormaGiuridica();
			//TODO: occorre ragionare sulla forma giuridica per l'intestatario
			impresaFormaGiuridica.setCodice("7");//Forma giuridica Altro?!?!?
			conferenzaSaved.setImpresaFormaGiuridica(impresaFormaGiuridica );
			conferenzaSaved.setImpresaIndirizzo(xmlIstanza.getAddress());
			if(xmlIstanza.getCfIntestatario()!=null)
			conferenzaSaved.setImpresaPartitaIva(xmlIstanza.getCfIntestatario());
			
			String intestatarioNomeComune= xmlIstanza.getComuneIntestatario();
			if(intestatarioNomeComune!=null && !"".equals(intestatarioNomeComune)){
				//listComune=comuneRepository.findByDescrizioneContainingIgnoreCase(intestatarioNomeComune, null);
				listComune=comuneRepository.findByDescrizioneIgnoreCase(intestatarioNomeComune, null);
				if(listComune!=null && ! listComune.isEmpty()){
					
					comunePratica=listComune.get(0);
					provincia = comunePratica.getProvincia();			
					regione= provincia.getRegione();
					
					conferenzaSaved.setLocalizzazioneComune(comunePratica);
					conferenzaSaved.setLocalizzazioneProvincia(provincia);
					conferenzaSaved.setLocalizzazioneIndirizzo(xmlIstanza.getAddress());
					conferenzaSaved.setImpresaComune(comunePratica);
					conferenzaSaved.setImpresaProvincia(provincia);
					conferenzaSaved.setImpresaRegione(regione);					
					conferenzaSaved.setImpresaIndirizzo(xmlIstanza.getIndirizzoIntestatario());
					
				}else{
					logger.debug("Il tag comune Intestatario è vuoto!");					
				}				
			}
			

		}
		else{
			if (pratica != null) {
				
				if (codiceFiscale != null) {
					conferenzaSaved.setImpresaCodiceFiscale(codiceFiscale);
				} else {
					if (pratica.getCodiceFiscaleRichiedente() != null &&
							!pratica.getCodiceFiscaleRichiedente().equals("")) {
						conferenzaSaved.setImpresaCodiceFiscale(pratica.getCodiceFiscaleRichiedente().trim());
					} else {
						conferenzaSaved.setImpresaCodiceFiscale("");
					}
				}
					
				if (pratica.getIntestatario() != null) {
					conferenzaSaved.setImpresaDenominazione(pratica.getIntestatario().trim());
				}
				
				
				FormaGiuridica impresaFormaGiuridica =new FormaGiuridica();
				//TODO: occorre ragionare sulla forma giuridica per l'intestatario
				impresaFormaGiuridica.setCodice("7");//Forma giuridica Altro?!?!?
				conferenzaSaved.setImpresaFormaGiuridica(impresaFormaGiuridica );
				conferenzaSaved.setImpresaIndirizzo(pratica.getIndirizzoIntervento());
				
				//if(xmlIstanza.getCfIntestatario()!=null)
				//conferenzaSaved.setImpresaPartitaIva(xmlIstanza.getCfIntestatario());
				
				String intestatarioNomeComune= pratica.getComuneIntervento();
				if(intestatarioNomeComune!=null && !"".equals(intestatarioNomeComune)){
					
					listComune=comuneRepository.findByDescrizioneIgnoreCase(intestatarioNomeComune.trim(), null);
					if(listComune!=null && ! listComune.isEmpty()){						
						comunePratica=listComune.get(0);
						provincia = comunePratica.getProvincia();			
						regione= provincia.getRegione();
						
						conferenzaSaved.setLocalizzazioneComune(comunePratica);
						conferenzaSaved.setLocalizzazioneProvincia(provincia);
						conferenzaSaved.setLocalizzazioneIndirizzo(pratica.getIndirizzoIntervento());
						conferenzaSaved.setImpresaComune(comunePratica);
						conferenzaSaved.setImpresaProvincia(provincia);
						conferenzaSaved.setImpresaRegione(regione);					
						conferenzaSaved.setImpresaIndirizzo(pratica.getIndirizzoIntervento());
						
					}else{
						logger.debug("Il tag comune Intestatario è vuoto!");					
					}					
				}
			} else {
				logger.debug("Non è stata passata alcuna impresa: il CD  intestatario è null!");
			}
			
		}
		this.confRepo.save(conferenzaSaved);		
		
	}

	public Boolean syncronize(Conferenza conferenza) throws Exception {
		if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())) {
			DomusRegistryAdapterTesta domusRegistryAdapterTesta = domusRegistryAdapterTestaRepository.findByIdConferenza(conferenza.getIdConferenza());		
			Pratica pratica = domusService.getProtocolliPraticaById(domusRegistryAdapterTesta.getIdPratica()); // , domusRegistryAdapterTesta.getFk_CodiceComune()
		
			//store degli allegati e delle pratiche
			//si occupa di eseguire lo store delle pratiche e dei documenti se non esistono!!!
			domusService.storeAllegatiPraticheMISWithoutStream(pratica, conferenza.getIdConferenza(), domusRegistryAdapterTesta, conferenza.getCodiceFiscaleResponsabileConferenza());
		}

		return true;
	}
	
	
}
