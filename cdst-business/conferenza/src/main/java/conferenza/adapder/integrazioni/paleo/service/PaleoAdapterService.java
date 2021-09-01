package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import conferenza.DTO.IdentifiableDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoConferenzaFascicoliDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAllegatiAdapterDTO;
import conferenza.adapder.integrazioni.paleo.DTO.bean.PaleoDocumentiFascicoloDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoSoapClientAdapter;
import conferenza.adapder.integrazioni.paleo.exception.PaleoDocumentoNotFoundEx;
import conferenza.adapder.integrazioni.paleo.model.PaleoFascicoloTipoConference;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAllegatiAdapter;
import conferenza.adapder.integrazioni.paleo.model.PaleoTipoConferenceProperties;
import conferenza.adapder.integrazioni.paleo.repository.PaleoFascicoloTipoConferenceRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAdapterRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAllegatiAdapterRepository;
import conferenza.model.Conferenza;
import conferenza.model.DocumentoAttachment;
import conferenza.model.RegistroDocumento;
import conferenza.model.TipologiaConferenza;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.repository.DocumentoAttachRepository;
import conferenza.service.ConferenzaService;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.Allegato;
import it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5;
import it.marche.regione.paleo.services.Corrispondente;
import it.marche.regione.paleo.services.File;
import it.marche.regione.paleo.services.MessaggioRisultato;
import it.marche.regione.paleo.services.RespDocProtocolliInfo;
import it.marche.regione.paleo.services.RespDocumento;
import it.marche.regione.paleo.services.RespDocumentoExt;
import it.marche.regione.paleo.services.RespProtocolloArrivo;
import it.marche.regione.paleo.services.RespProtocolloArrivoExt;
import it.marche.regione.paleo.services.RespProtocolloPartenza;
import it.marche.regione.paleo.services.RespProtocolloPartenzaExt;

@Service
public class PaleoAdapterService {
	private static final Logger logger = LoggerFactory.getLogger(PaleoAdapterService.class.getName());

	  @Autowired
	  PaleoSoapClientAdapter paleoClientAdapter;
	  
	  @Autowired 
	  PaleoClientConfiguration clientConfiguration;
	
	  @Autowired 
	  ConferenzaService conferenzaService;

	  @Autowired 
	  PaleoPropertiesService paleoPropertiesService;
	  
	  @Autowired 
	  PaleoRegistryAdapterRepository paleoRegistryAdapterRepository;

	  @Autowired
	  PaleoConferenceService createConferenceService;
	  
	  @Autowired
	  PaleoFascicoloTipoConferenceRepository paleoFascicoloTipoConferenceRepository;	
	
	  
	  @Autowired
	  PaleoToFrontieraDTOService paleoToFrontieraDTOService;
	  
	  @Autowired
	  PaleoRegistryAllegatiAdapterRepository paleoAllegatiAdapterRepository;
	  
	  @Autowired
	  DocumentoAttachRepository documentoAttachRepository;
	  
	  
	  public Integer getIdFascioloTipoConferenza(String codiceFascicolo,String tipoconferenza) {
		  return paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(codiceFascicolo,tipoconferenza);
	  }
	  
	  
	  /**
	   * 
	   * @param codiceFascicolo
	   * @param listaFascicoliPaleo sono i codici paelo dei documenti da gestire..solo questi..
	   * @return
	   * @throws IOException
	   * @throws ServiceException
	   * @throws SOAPException
	   */
	public IdentifiableDTO doActionCreateConference(PaleoConferenzaFascicoliDTO paleoConferenzaFascicoloDTO)
			throws IOException, ServiceException, SOAPException {

		String fascicoloDTO=paleoConferenzaFascicoloDTO.getCodiceFascicolo();
		String tipoConferenzaAssociata=paleoConferenzaFascicoloDTO.getTipoConferenza();		
		String amministrazionePrecedente = paleoConferenzaFascicoloDTO.getAmministrazionePrecedente();
		String responsabile = paleoConferenzaFascicoloDTO.getResponsabile();
		
		if(fascicoloDTO==null || tipoConferenzaAssociata==null)
			throw new ServiceException("Fascicolo o tipo conferenza sono obbligatori!");

		boolean isUSR = false; try { isUSR = tipoConferenzaAssociata.equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS); } catch (Exception takedefault) { }
		
		
		Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(fascicoloDTO,tipoConferenzaAssociata);
		if(idFascioloTipoConferenza==null)
			logger.debug("idFascioloTipoConferenza is null!!!");
		else
			logger.debug("idFascioloTipoConferenza="+idFascioloTipoConferenza);
		
		/*
		 * verifica esistenza fascicolo su paleo
		 */
		initializeConferenzeParametersByTipoConferenceSpecializzazione(tipoConferenzaAssociata);
		
		logger.debug("-- getDocumentiProtocolliInFascicolo -- BEFORE");
		it.marche.regione.paleo.services.RespDocProtocolliInfo[] listProtocolliInFascicolo = getDocumentiProtocolliInFascicolo(fascicoloDTO, isUSR);
		logger.debug("-- getDocumentiProtocolliInFascicolo -- AFTER");
		if (listProtocolliInFascicolo == null || listProtocolliInFascicolo.length == 0) {
			logger.error("fascicolo Paleo non presente o vuoto: " + fascicoloDTO);
			//throw new PaleoFascicoloNotFoundEx("fascicolo Paleo non presente o vuoto");
		}

		  //varibili gobali..
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		Integer idConferenza = null;
		
		// verifica esistenza e creazione associazione fascicolo tipoConferenza

		PaleoFascicoloTipoConference paleoFascicoloTipoConference =null;
			
		paleoFascicoloTipoConference = new PaleoFascicoloTipoConference();
		if(idFascioloTipoConferenza==null) {
			paleoFascicoloTipoConference.setIdTipoConderenza(tipoConferenzaAssociata);
			paleoFascicoloTipoConference.setIdFascicolo(fascicoloDTO);
			paleoFascicoloTipoConference.setNomeFascicolo(paleoConferenzaFascicoloDTO.getDescrizioneFascicolo());
			paleoFascicoloTipoConference=paleoFascicoloTipoConferenceRepository.save(paleoFascicoloTipoConference);
			idFascioloTipoConferenza=paleoFascicoloTipoConference.getId();
		}else {
			Optional<PaleoFascicoloTipoConference> paleoFascicoloTipoConferenceOpt = paleoFascicoloTipoConferenceRepository.findById(idFascioloTipoConferenza);
			paleoFascicoloTipoConference=paleoFascicoloTipoConferenceOpt.get();
		}
		
        /**
         * In realtà mi aspetto che vengano "di fatto" sempre passati i socumenti Paelo di Interesse..
         */
		List<PaleoDocumentiFascicoloDTO> listaFascicoliPaleo = paleoConferenzaFascicoloDTO.getListaFascicoliPaleo();
		if (listaFascicoliPaleo != null && !listaFascicoliPaleo.isEmpty()) {
			idConferenza = createConference(paleoConferenzaFascicoloDTO) ;
			identifiableDTO.setId(idConferenza);
		}else{
			List<PaleoRegistryAdapter> listaPaleoRegistryAdapter = managePaleoProtocolliInFascicolo(paleoFascicoloTipoConference, isUSR);
			// genera il metadato per la creazione della conferenza...
			IntegFrontieraDTO frontieraDTO = paleoToFrontieraDTOService.getFrontieraDTOFromIdFascicolo(paleoConferenzaFascicoloDTO.getCodiceFascicolo(),tipoConferenzaAssociata);
			// genera la conferenza
			idConferenza = createConference(frontieraDTO, tipoConferenzaAssociata, frontieraDTO.getListaDocumenti(),amministrazionePrecedente, responsabile);
			
			// valorizzazione dell'id conferenza generato..
			identifiableDTO.setId(idConferenza);
		}
		//========================================
		//TODO - memorizzare associazione tipo conferenza| fascicolo| id_confernza in modo da permettere la molteplicità di creazione..
		
		//========================================		
		
		return identifiableDTO;
	}	  
	  
	private List<IntegFrontieraDocumentDTO> getFilteredIntegFrontieraDocumentDTO(
			PaleoConferenzaFascicoliDTO paleoConferenzaFascicoloDTO) {
		
		IntegFrontieraDTO frontieraDTO =paleoToFrontieraDTOService.getFrontieraDTOFromIdFascicolo(paleoConferenzaFascicoloDTO.getCodiceFascicolo(),paleoConferenzaFascicoloDTO.getTipoConferenza());
		List<IntegFrontieraDocumentDTO> allFocument=frontieraDTO.getListaDocumenti();
		
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();		
		List<PaleoDocumentiFascicoloDTO> listaFascicoliPaleo=paleoConferenzaFascicoloDTO.getListaFascicoliPaleo();
		for(PaleoDocumentiFascicoloDTO itemDcoumentiPaleo: listaFascicoliPaleo) {
			IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
			Integer idDadapter=null;
			String signature=itemDcoumentiPaleo.getCodiceDocumento();
			if(signature.lastIndexOf("|")>0)				
				idDadapter= paleoRegistryAdapterRepository.getIdAdapterBySegnaturaProtocollo( itemDcoumentiPaleo.getCodiceDocumento());
			else
				idDadapter= paleoRegistryAdapterRepository.getIdAdapterByDocumentNumber(Integer.parseInt(itemDcoumentiPaleo.getCodiceDocumento()) );
			docFrontieraDTO.setId(idDadapter);		
			//
			String nomeDocumento=getDescrizioneDocumento(allFocument,String.valueOf(idDadapter));			
			docFrontieraDTO.setNome(nomeDocumento);
			documentFrontieraDTO.add(docFrontieraDTO);
		}		
		return documentFrontieraDTO;
	}

	 /**
	  *  
	  * @param frontieraDTO
	  * @return
	  * @throws IOException
	  */
	 public Integer createConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati) 
			 throws IOException {		
			return createConference(frontieraDTO, tipologiaConferenza, listaAllegati,
					 null,null); 		 
	 } 
	 
	 /**
	  *  
	  * @param frontieraDTO
	  * @return
	  * @throws IOException
	  */
	 public Integer createConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati,
			 String amministrazionePrecedente,String responsabile) 
			 throws IOException {
			Integer idConferenza = null;
		    idConferenza = createConferenceService.persistConference( frontieraDTO, tipologiaConferenza, listaAllegati,amministrazionePrecedente,responsabile); 			
			return idConferenza;		 		 
	 } 
	 
	 /**
	  * 
	  * @param codiceTipoConference
	  * @param ambiente
	  */
	 public List<PaleoTipoConferenceProperties> initializeConferenzeParametersByTipoConferenceSpecializzazione(String codiceTipoConference) {
		 String ambiente=clientConfiguration.getAmbiente();
		  List<PaleoTipoConferenceProperties> listaConferenze =paleoPropertiesService.getListPaleoTipoConferenceProperties(codiceTipoConference,ambiente);
		  String propertiesType ="paleo.url";
		  if(listaConferenze!=null && !listaConferenze.isEmpty()) {
			  for(PaleoTipoConferenceProperties item: listaConferenze) {
				  String urlPaleo=null;
				  if(propertiesType.equals(item.getPropertiesType())) {
					  urlPaleo=item.getPropertiesValue();
					  logger.debug(urlPaleo);
					  clientConfiguration.setUrl(urlPaleo);
				  }	  
				  //TODO resettare l'oggetto configuration da passare ad al livello adapter..
			  }
		  }		
		  return listaConferenze;
	 }
	 
	  
	  
	 public boolean initializeConferenzeParameters(Integer idConference) {
		  Conferenza conference=null;
		  TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione=null;		 
		  if(idConference!=null) {
			  conference= conferenzaService.findConferenceById(idConference);
			  tipologiaConferenzaSpecializzazione= conference.getTipologiaConferenzaSpecializzazione();
			  //get Prarametri Paleo by Tipo Conferenza  
			  String codiceTipoConference=tipologiaConferenzaSpecializzazione.getCodice();
			  List<PaleoTipoConferenceProperties> listaConferenze =initializeConferenzeParametersByTipoConferenceSpecializzazione(codiceTipoConference); 			  			  
			  // allUsers is a Collection<User>
			  
			  return tipologiaConferenzaSpecializzazione.equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
		  }
		  
		  return false;
	 } 
	  
	 
	 /**
	  * Per Ottenere la risorsa da IdFile 
	  * @param idFilePaleo
	  * @param idConference
	  * @return
	  * @throws ServiceException
	  * @throws IOException
	  * @throws SOAPException
	  */
	  public Resource getFileStreamAsResource(Integer idFilePaleo,Integer idConference, boolean isUSR) throws ServiceException, IOException, SOAPException {
		  Conferenza conference=null;
		  TipologiaConferenza tipologiaConferenza=null;
		  //FONDAMENTALE : i parametri sono funzione della conferenza
		  initializeConferenzeParameters(idConference) ;	  
		  
		  return paleoClientAdapter.getFileStreamAsResource(idFilePaleo, isUSR);
	  }
	  
	  /**
	   * 
	   * @param idDocument
	   * @param idConference
	   * @return
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  public Resource getFileStreamAsResourceByIdDocument(Integer idDocument, boolean isUSR) throws ServiceException, IOException, SOAPException {
		  Integer idConference= paleoRegistryAdapterRepository.getIdConferenceByIdDocument(idDocument);
		  initializeConferenzeParameters(idConference) ;	  		  
		  Integer idAllegato=paleoRegistryAdapterRepository.getIdAllegatoByIdDocument(idDocument);		  
		  return paleoClientAdapter.getFileStreamAsResource(idAllegato, isUSR);		  
	  }
	  
	  public Resource getFileStreamAsResourceByIdRegistro(Integer idRegistro) throws ServiceException, IOException, SOAPException {
		  Integer idConference= paleoRegistryAdapterRepository.getIdConferenceByIdRegistro(idRegistro);
		  boolean isUSR = initializeConferenzeParameters(idConference) ;	
		  
		  Integer idAllegato=paleoRegistryAdapterRepository.getIdAllegatoByIdRegistro(idRegistro);		
		  return paleoClientAdapter.getFileStreamAsResource(idAllegato, isUSR);		  
	  }


	  /**
	   * recupera e salva la lista di protocolli, inserrendo tutte le informazioni nella tabella paleoRegistryAdapter
	   * 
	   * @param codiceFascicolo
	   * @param codiceTipoConference
	   * @throws ServiceException
	 * @throws SOAPException 
	 * @throws IOException 
	   */
	  public List<PaleoRegistryAdapter>  managePaleoProtocolliInFascicolo(
			  PaleoFascicoloTipoConference paleoFascicoloTipoConference, boolean isUSR
			  ) throws ServiceException, IOException, SOAPException {

		  String codiceFascicolo=paleoFascicoloTipoConference.getIdFascicolo();
		  String  codiceTipoConference=paleoFascicoloTipoConference.getIdTipoConderenza();
		  
		  Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(codiceFascicolo,codiceTipoConference);
		  //ottiene eventuali oggetti preesistetni
		  logger.debug("-- getlistPaleoAdapterByFascicolo -- BEFORE");
		  List<PaleoRegistryAdapter> esitentiAdapter=paleoRegistryAdapterRepository.getlistPaleoAdapterByFascicolo(codiceFascicolo,paleoFascicoloTipoConference.getId());
		  logger.debug("-- getlistPaleoAdapterByFascicolo -- AFTER");
		  
		  List<PaleoRegistryAdapter> listaPaleoRegistryAdapter=new ArrayList<PaleoRegistryAdapter>();
		  if(codiceFascicolo==null)
			  throw new ServiceException("Codice Fascicolo Non presente");
		  if(codiceTipoConference==null)
			  throw new ServiceException("Codice Tipo Conferenza Non presente");		  
		  //------------------------------------------------------		  
		  
		  //-1- inizializza i parametri in funzione del tipo conferenza
		  initializeConferenzeParametersByTipoConferenceSpecializzazione(codiceTipoConference);	  
		  
		  // xmfPR: paleo attachments download
		  downloadFilesFromFascicoloPaleo(codiceFascicolo, idFascioloTipoConferenza, listaPaleoRegistryAdapter, esitentiAdapter, isUSR);
		  
		  //------------------------------------------------------
		  return listaPaleoRegistryAdapter;
	  }
	  
	  

	/**
	   * Servirà per le funzionalità di EDITING!!!!
	   * @param codiceFascicolo
	   * @param idConference
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  /*
	  public void managePaleoProtocolliInFascicolo(String codiceFascicolo,Integer idConference)
			  throws ServiceException, IOException, SOAPException {		
		  
		  if(codiceFascicolo==null)
			  throw new ServiceException("Codice Fascicolo Non presente");
		  
		  //------------------------------------------------------		  
		  PaleoRegistryAdapter paleoAdater=null;		  
		  //-1- inizializza i parametri in funzione del tipo conferenza
		  initializeConferenzeParameters(idConference) ;	  
		  //-2- otiene la lista di fascicoli da Paleo
		  it.marche.regione.paleo.services.RespDocProtocolliInfo[]  listProtocolliInFascicolo= getDocumentiProtocolliInFascicolo(codiceFascicolo);  
		  if(listProtocolliInFascicolo==null || listProtocolliInFascicolo.length!=0) {
			  for(RespDocProtocolliInfo item: listProtocolliInFascicolo ) {
				  paleoAdater= new PaleoRegistryAdapter();	
				  paleoAdater.setFkPaleoFascicolo(codiceFascicolo);
				  paleoAdater.setIdPaleoRegistro(item.getCodiceRegistro());				
				  
				  paleoAdater.setIdPaleoNumeroDoc(item.getDocNumber());
				  paleoAdater.setIdPaleoProtocollo(item.getNumeroProtocollo());
				  paleoAdater.setPaleoOggetto(item.getOggetto());
				  paleoAdater.setPaleoSegnaturaProtocollo(item.getSegnaturaProtocollo());
				  if(item.getDataProtocollo()!=null) {
				  	  paleoAdater.setPaleoProtocolloData(getStringFromCalendar(item.getDataProtocollo()));
				  }
				  if(item.getTipoProtocollo()!=null && item.getTipoProtocollo().getValue()!=null)
					  paleoAdater.setPaleoTipoProtocollo(item.getTipoProtocollo().getValue());
				  String[] scodfoc=null;
				  String coddoc=item.getSegnaturaProtocollo();
				  logger.debug("segnatura protocollo:"+coddoc);
				  logger.debug("oggetto:"+item.getOggetto());
				  if(coddoc!=null && !"".equals(coddoc)) {
				    scodfoc=coddoc.split("\\|");
				    paleoAdater.setIdPaleoDoc(Integer.valueOf(scodfoc[0])) ;
				  }  
				//-3-SaVE data in registry				    
				paleoRegistryAdapterRepository.save(paleoAdater);
				//-4- Manage Allegati per Oggetto PaleoProtocollo
				manageAllegati(paleoAdater);
				
			  }				  
		  }
		  //------------------------------------------------------
	  }
	  */
	  
	  public static String getStringFromCalendar(Calendar cal) {
		  Date date = cal.getTime();             
		  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		  return  format1.format(date);		  		  
	  }
   					 

	  
	  private RespDocumentoExt getAllegati(String docNumber , String segnatura, boolean isUSR) 
			  throws ServiceException, IOException, SOAPException {
		  return paleoClientAdapter.getCercaDocumentoProtocollo(docNumber, segnatura, isUSR);		 
	  }
	  
	  private paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt getAllegatiPaleoGiunta(String docNumber , String segnatura) 
			  throws ServiceException, IOException, SOAPException {
		  return paleoClientAdapter.getCercaDocumentoProtocollo(docNumber, segnatura);		 
	  }
	  
	  private RespDocumentoExt cercaDocumentoProtocollo(String inpuPaleoDoc, boolean isUSR) 
			  throws ServiceException, IOException, SOAPException {
		  if(inpuPaleoDoc==null || "".equals(inpuPaleoDoc))
			  throw new ServiceException("Input Non definito");
		  
		  String docNumber=null;
		  String segnatura=null;
		  if(inpuPaleoDoc.lastIndexOf("|")>0) {
			  segnatura=inpuPaleoDoc;			  
		  }else {
			  docNumber=inpuPaleoDoc;
		  }		  
		  return paleoClientAdapter.getCercaDocumentoProtocollo(docNumber, segnatura, isUSR);
	  }
	  
	  private paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt cercaDocumentoProtocolloPaleoGiunta(String inpuPaleoDoc) 
			  throws ServiceException, IOException, SOAPException {
		  if(inpuPaleoDoc==null || "".equals(inpuPaleoDoc))
			  throw new ServiceException("Input Non definito");
		  
		  String docNumber=null;
		  String segnatura=null;
		  if(inpuPaleoDoc.lastIndexOf("|")>0) {
			  segnatura=inpuPaleoDoc;			  
		  }else {
			  docNumber=inpuPaleoDoc;
		  }		  
		  return paleoClientAdapter.getCercaDocumentoProtocollo(docNumber, segnatura);
	  }
	  
	  /**
	   * @param inpuPaleoDoc
	   * @param sFkPaleoFascicolo
	   * @return
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  
	  public  PaleoRegistryAdapter cercaDocumentoPaleo(String inpuPaleoDoc,String sFkPaleoFascicolo,String tipoConferenza)
			  throws ServiceException, IOException, SOAPException {
		  
			boolean isUSR = false; try { isUSR = tipoConferenza.equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS); } catch (Exception takedefault) { }

		  initializeConferenzeParametersByTipoConferenceSpecializzazione(tipoConferenza);	 
		  
		  PaleoRegistryAdapter registry=new PaleoRegistryAdapter(); 
		  RespDocumentoExt response=null;
		  
		  if (isUSR)  {
			  response=cercaDocumentoProtocollo(inpuPaleoDoc, isUSR);
			  
			  if(response!=null) {
				  MessaggioRisultato messaggio= response.getMessaggioRisultato();
				  String codiceMessaggio=messaggio.getTipoRisultato().getValue();
				  if("Error".equals(codiceMessaggio)) {
					  logger.error(messaggio.getDescrizione());
					  throw new PaleoDocumentoNotFoundEx(messaggio.getDescrizione());
				  }
			  
				  String oggetto=response.getOggetto();
				  registry.setFkPaleoFascicolo(sFkPaleoFascicolo);
				  registry.setIdPaleoDoc(new Integer(response.getDocNumber()));
				  registry.setIdPaleoNumeroDoc(response.getDocNumber());
				  //
				  registry.setPaleoProtocolloData( getStringFromCalendar(response.getDataDocumento()));
				  if(response instanceof RespProtocolloArrivoExt)				  
					  registry.setPaleoSegnaturaProtocollo(((RespProtocolloArrivoExt)response).getSegnatura());
				  if(response instanceof RespProtocolloPartenzaExt)	  
					  registry.setPaleoSegnaturaProtocollo(((RespProtocolloPartenzaExt)response).getSegnatura());
				  
				  registry.setPaleoOggetto(response.getOggetto());
			  }
		  } else {
			  paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt responseGiunta = cercaDocumentoProtocolloPaleoGiunta(inpuPaleoDoc);
			  
			  if(responseGiunta!=null) {
				  paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggio= responseGiunta.getMessaggioRisultato();
				  String codiceMessaggio=messaggio.getTipoRisultato().getValue();
				  if("Error".equals(codiceMessaggio)) {
					  logger.error(messaggio.getDescrizione());
					  throw new PaleoDocumentoNotFoundEx(messaggio.getDescrizione());
				  }
			  
				  String oggetto=responseGiunta.getOggetto();
				  registry.setFkPaleoFascicolo(sFkPaleoFascicolo);
				  registry.setIdPaleoDoc(new Integer(responseGiunta.getDocNumber()));
				  registry.setIdPaleoNumeroDoc(responseGiunta.getDocNumber());
				  //
				  registry.setPaleoProtocolloData( getStringFromCalendar(responseGiunta.getDataDocumento()));
				  if(responseGiunta instanceof paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivoExt)				  
					  registry.setPaleoSegnaturaProtocollo(((paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivoExt)responseGiunta).getSegnatura());
				  if(responseGiunta instanceof paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenzaExt)	  
					  registry.setPaleoSegnaturaProtocollo(((paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenzaExt)responseGiunta).getSegnatura());
				  
				  registry.setPaleoOggetto(responseGiunta.getOggetto());
			  }
		  }
		  
		  return registry;
	  }
	  	

	  /**
	   * Mantiene il vincolo di unicità sul Document Number..
	   * TODO: aggiungere al vincolo il tipo conferenza..
	   * in seguito FkPaleoFascicolo dovrà contenere un di e non il codice fascicolo..
	   * @param registry
	   * @return
	   */
	  public PaleoRegistryAdapter storeDocumentoPaleo(PaleoRegistryAdapter registry,Integer idFascicoloTipoConfenenza) {
		  Integer idPaleoDocNumber=null;
		  PaleoRegistryAdapter esistente=null;
		  idPaleoDocNumber=registry.getIdPaleoNumeroDoc();
		  //TODO: aggiungere una chiave: aggiungere tipo fascicolo.. 
		  List<PaleoRegistryAdapter> esitentiAdapter=paleoRegistryAdapterRepository.getlistPaleoAdapterByFascicolo(registry.getFkPaleoFascicolo(),idFascicoloTipoConfenenza);		  
		  //-3-SaVE data in registry		
		  Set<Integer> exixtenstItems = esitentiAdapter.stream()
				    .map(PaleoRegistryAdapter::getIdPaleoNumeroDoc)
				    .collect(Collectors.toSet());
		  
		  if(esitentiAdapter!=null && !esitentiAdapter.isEmpty())
		  for(PaleoRegistryAdapter itemExist : esitentiAdapter) {	  
			  if( Integer.compare(itemExist.getIdPaleoNumeroDoc(),idPaleoDocNumber)==0)
				  esistente=itemExist;
		  }
		  if(esistente==null) {
			  registry.setIdFascicoloTipoConferenza(idFascicoloTipoConfenenza);			  
			  registry= paleoRegistryAdapterRepository.save(registry);
		  }	else {
			  registry.setId(esistente.getId());
		  }		  		  
		  return registry;
	  }
	  
	  /**
	   *  il metodo si occupa anche dello store dell'allegato se NON è esistente (per il dato valore di testata)..
	   * @param strdocpaleoDecoded
	   * @param strCodiceFascicoloDecoded
	   * @return
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  public List<PaleoRegistryAllegatiAdapter>  manageDocumentoPaeleo(String strdocpaleoDecoded,String strCodiceFascicoloDecoded,String strTipoConfenerenza)
			  throws ServiceException, IOException, SOAPException {
		  
			boolean isUSR = false; try { isUSR = strTipoConfenerenza.equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS); } catch (Exception takedefault) { }
			
		  
		  List<PaleoRegistryAllegatiAdapter> allegati = new ArrayList<PaleoRegistryAllegatiAdapter>();
		  Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(strCodiceFascicoloDecoded,strTipoConfenerenza);
		  PaleoRegistryAdapter risultato=cercaDocumentoPaleo(strdocpaleoDecoded, strCodiceFascicoloDecoded, strTipoConfenerenza);
		  if(risultato.getIdFascicoloTipoConferenza()==null && idFascioloTipoConferenza!=null) {
			  risultato.setIdFascicoloTipoConferenza(idFascioloTipoConferenza);  			  
		  }
		  //store del Documento
	      risultato=storeDocumentoPaleo(risultato,idFascioloTipoConferenza);
	      //store degli allegati..
	      //lo store dell'allegato avviene sse NON è esistente..
	      allegati=manageAllegati(risultato, isUSR);		  
	      	      
	      return allegati; 
	  }

	  /**
	   * il metodo provvede a creare velocemente una conferenza 
	   * quando siano stati pre-caricati da FE i doc da 
	   * associare alla conferenza..
	   * 
	   * TODO:
	   * NON salva su Paelo_fascicolo_tipoconferenza
	   * salva su Paelo_Registry_adapter
	   * salva su Paelo_Registry_adapter_allegati
	   * 
	   * @param paleoConferenzaFascicoloDTO metadato inviato dal FE
	   * @return
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  public Integer createConference(PaleoConferenzaFascicoliDTO paleoConferenzaFascicoloDTO) 
			  throws ServiceException, IOException, SOAPException {
		
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();	  
		
		List<IntegFrontieraDocumentDTO> allFocument=null;
		
		//crea la lista dei documenti da caricare
	    List<String> listIdDoc=new ArrayList<String>() ;	    	    
	    List<PaleoDocumentiFascicoloDTO> listaDocumentiDaCaricare= paleoConferenzaFascicoloDTO.getListaFascicoliPaleo();
	    if(listaDocumentiDaCaricare!=null && !listaDocumentiDaCaricare.isEmpty())
	    for(PaleoDocumentiFascicoloDTO itemFrom :listaDocumentiDaCaricare ) {
	    	listIdDoc.add(itemFrom.getCodiceDocumento());	    	
	    }
		//intercetta su Paelo la lista dei documenti da caricare	    
	    //Salva su Paelo_Registry_adapter
		List<PaleoRegistryAllegatiAdapter>  allegati=manageListDocumentoPaeleo( listIdDoc,paleoConferenzaFascicoloDTO.getCodiceFascicolo(), paleoConferenzaFascicoloDTO.getTipoConferenza());
		// Conversione del formato per agganciarsi al caso precedente per la creazioen della conferneza..
		//  PaleoRegistryAllegatiAdapter -> IntegFrontieraDocumentDTO -> frontieraDTO
		for(PaleoRegistryAllegatiAdapter item: allegati) {
			IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
			//Per la Frontiera serve la chiave del PaleoRegistryAllegatiAdapter
			docFrontieraDTO.setId(item.getId());		
			//dovrebbe servire per costr
			//docFrontieraDTO.setId_paleo(String.valueOf(item.getIdPaleoDoc()));
			docFrontieraDTO.setNome(item.getNomeAllegato());
			documentFrontieraDTO.add(docFrontieraDTO);
		}
		//creazoine della conferenza..
		IntegFrontieraDTO frontieraDTO = paleoToFrontieraDTOService.getFrontieraDTOFromIdFascicolo(paleoConferenzaFascicoloDTO.getCodiceFascicolo(),paleoConferenzaFascicoloDTO.getTipoConferenza());
		//sovrascrive la lista dei documenti per l'oggetto...
		frontieraDTO.setListaDocumenti(documentFrontieraDTO);		
		// genera la conferenza		
		Integer idConferenza = createConference(frontieraDTO, paleoConferenzaFascicoloDTO.getTipoConferenza(), frontieraDTO.getListaDocumenti());		
		
		return idConferenza;
	  }
	  
	  
	/**
	 * 
	 * @param listIdDoc
	 * @param fascicolo
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	  public List<PaleoRegistryAllegatiAdapter>  manageListDocumentoPaeleo(List<String> listIdDoc,String fascicolo,String tipoConferenza)
			  throws ServiceException, IOException, SOAPException {
		   List<PaleoRegistryAllegatiAdapter> allegati = new ArrayList<PaleoRegistryAllegatiAdapter>();
		   for(String item : listIdDoc) {
			   allegati .addAll(manageDocumentoPaeleo(item,fascicolo,tipoConferenza));			   
		   }
		   return allegati;		  
	  }	  
	  
	  
	  
	  /**
	   * salva nella tabella paleoAllegatiAdapter tutti i documenti associati al protocollo 
	   * 
	   * @param paleoAdater
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  public  List<PaleoRegistryAllegatiAdapter>  manageAllegati(PaleoRegistryAdapter paleoAdater, boolean isUSR) throws ServiceException, IOException, SOAPException {
		  logger.debug("=============manageAllegati======================");
		  List<PaleoRegistryAllegatiAdapter> listallegati = new ArrayList();
		  Integer idAdapter=paleoAdater.getId();
		  List<PaleoRegistryAllegatiAdapter> allegatiEsistenti=paleoAllegatiAdapterRepository.getlistAllegatiPaleoByIdAdapter(idAdapter);
		  
		  if (isUSR) {
			  RespDocumentoExt risposta=getAllegati(String.valueOf(paleoAdater.getIdPaleoDoc()) , paleoAdater.getPaleoSegnaturaProtocollo(),isUSR);
			  if(risposta!=null) {
				  File docPrincipale=risposta.getDocumentoPrincipale();
				  if(docPrincipale!=null) {
					  logger.debug("id_dile_principale" +docPrincipale.getIdFile());
					  PaleoRegistryAllegatiAdapter esistente=null;
					  PaleoRegistryAllegatiAdapter allegatoAdapter=new PaleoRegistryAllegatiAdapter(); 
					  allegatoAdapter.setIdAllegato(docPrincipale.getIdFile());
					  allegatoAdapter.setNomeAllegato("DocumentoPrincipale-"+docPrincipale.getNome());
					  allegatoAdapter.setFkPaleoAdapter(paleoAdater.getId());
					  if(allegatiEsistenti!=null && !allegatiEsistenti.isEmpty()) {
						  for(PaleoRegistryAllegatiAdapter itemE: allegatiEsistenti) {
							  if(Integer.compare(itemE.getIdAllegato(),allegatoAdapter.getIdAllegato())==0)
								  esistente=itemE;
						  }
					  }
					  if(esistente==null)
						  paleoAllegatiAdapterRepository.save(allegatoAdapter);
					  else
						  allegatoAdapter.setId(esistente.getId());	
					  //Il doc Principale vine aggiunto alla lista degli allegati
					  listallegati.add(allegatoAdapter);
					  //Gestione degli Allegati
					  Allegato[]  allegati=risposta.getAllegati();
					  if(allegati!=null) {
						  for (Allegato item: allegati ) {	
							  if(item.getDocumento()!=null && item.getDocumento().getIdFile()!=null) {
								  //if(paleoAllegatiAdapterRepository.isThereAttachmentId(item.getDocumento().getIdFile()) > 0) continue;
								  
								  logger.debug("id_file "+item.getDocumento().getIdFile());
								  logger.debug(item.getDocumento().getNome());
								  logger.debug(item.getDescrizione());		
								  allegatoAdapter=new PaleoRegistryAllegatiAdapter(); 
								  allegatoAdapter.setIdAllegato(item.getDocumento().getIdFile());
								  allegatoAdapter.setNomeAllegato("Allegato-"+item.getDocumento().getNome());
								  allegatoAdapter.setFkPaleoAdapter(paleoAdater.getId());
								  //veririfca se l'allegato essite tra gli essitenti per il doc paleo							  
								  if(allegatiEsistenti!=null && !allegatiEsistenti.isEmpty()) {
									  for(PaleoRegistryAllegatiAdapter itemE: allegatiEsistenti) {
										  if(Integer.compare(itemE.getIdAllegato(),allegatoAdapter.getIdAllegato())==0)
											  esistente=itemE;
									  }
								  }
								  //Se non esiste vine savato; indipendentemente da tutto vine aggiunto
								  if(esistente==null)
									  allegatoAdapter=paleoAllegatiAdapterRepository.save(allegatoAdapter);
								  else
								  allegatoAdapter.setId(esistente.getId());
							  }
							  //
							  listallegati.add(allegatoAdapter);
						  }//fine FOr!!!						  
					  }else{
						  logger.debug("Non ci sono allegati!");						  
					  }
				  }	  
			  }
		  } else {
			  paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt risposta=
					  getAllegatiPaleoGiunta(String.valueOf(paleoAdater.getIdPaleoDoc()) , paleoAdater.getPaleoSegnaturaProtocollo());
			  if(risposta!=null) {
				  paleoGiunta.it.marche.regione.paleo.services.File docPrincipale=risposta.getDocumentoPrincipale();
				  if(docPrincipale!=null) {
					  logger.debug("id_dile_principale" +docPrincipale.getIdFile());
					  PaleoRegistryAllegatiAdapter esistente=null;
					  PaleoRegistryAllegatiAdapter allegatoAdapter=new PaleoRegistryAllegatiAdapter(); 
					  allegatoAdapter.setIdAllegato(docPrincipale.getIdFile());
					  allegatoAdapter.setNomeAllegato("DocumentoPrincipale-"+docPrincipale.getNome());
					  allegatoAdapter.setFkPaleoAdapter(paleoAdater.getId());
					  if(allegatiEsistenti!=null && !allegatiEsistenti.isEmpty()) {
						  for(PaleoRegistryAllegatiAdapter itemE: allegatiEsistenti) {
							  if(Integer.compare(itemE.getIdAllegato(),allegatoAdapter.getIdAllegato())==0)
								  esistente=itemE;
						  }
					  }
					  if(esistente==null)
						  paleoAllegatiAdapterRepository.save(allegatoAdapter);
					  else
						  allegatoAdapter.setId(esistente.getId());	
					  //Il doc Principale vine aggiunto alla lista degli allegati
					  listallegati.add(allegatoAdapter);
					  //Gestione degli Allegati
					  paleoGiunta.it.marche.regione.paleo.services.Allegato[]  allegati=risposta.getAllegati();
					  if(allegati!=null) {
						  for (paleoGiunta.it.marche.regione.paleo.services.Allegato item: allegati ) {	
							  if(item.getDocumento()!=null && item.getDocumento().getIdFile()!=null) {
								  //if(paleoAllegatiAdapterRepository.isThereAttachmentId(item.getDocumento().getIdFile()) > 0) continue;
								  
								  logger.debug("id_file "+item.getDocumento().getIdFile());
								  logger.debug(item.getDocumento().getNome());
								  logger.debug(item.getDescrizione());		
								  allegatoAdapter=new PaleoRegistryAllegatiAdapter(); 
								  allegatoAdapter.setIdAllegato(item.getDocumento().getIdFile());
								  allegatoAdapter.setNomeAllegato("Allegato-"+item.getDocumento().getNome());
								  allegatoAdapter.setFkPaleoAdapter(paleoAdater.getId());
								  //veririfca se l'allegato essite tra gli essitenti per il doc paleo							  
								  if(allegatiEsistenti!=null && !allegatiEsistenti.isEmpty()) {
									  for(PaleoRegistryAllegatiAdapter itemE: allegatiEsistenti) {
										  if(Integer.compare(itemE.getIdAllegato(),allegatoAdapter.getIdAllegato())==0)
											  esistente=itemE;
									  }
								  }
								  //Se non esiste vine savato; indipendentemente da tutto vine aggiunto
								  if(esistente==null)
									  allegatoAdapter=paleoAllegatiAdapterRepository.save(allegatoAdapter);
								  else
								  allegatoAdapter.setId(esistente.getId());
							  }
							  //
							  listallegati.add(allegatoAdapter);
						  }//fine FOr!!!						  
					  }else{
						  logger.debug("Non ci sono allegati!");						  
					  }
				  }	  
			  }
		  }
		  
		  return listallegati;
	  }
	  
	  
	  
	  
	  /**
	   * 
	   * @param codiceFascicolo
	   * @return
	   * @throws ServiceException
	   * @throws IOException
	   * @throws SOAPException
	   */
	  public RespDocProtocolliInfo[] getDocumentiProtocolliInFascicolo(String codiceFascicolo, boolean isUSR) 
			  throws ServiceException, IOException, SOAPException {		
		  it.marche.regione.paleo.services.RespDocProtocolliInfo[]  listProtocolliInFascicolo=null;
		  BEListOfrespDocProtocolliInfoZA0HwLp5 returnVal =paleoClientAdapter.getGetDocumentiProtocolliInFascicolo(codiceFascicolo, isUSR); 
		  if(returnVal!=null) {
			  return listProtocolliInFascicolo=returnVal.getLista();
		  }			 
		  return null;
	  }

	  public String getRaggruppamentoByIdDocument(Integer idDocument) { 
		  return  paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(idDocument);  
	  }
	  
	public void doFindOperatori(boolean isUSR) throws ServiceException, IOException, SOAPException {
		 logger.debug("@paleo doFindOperatori");	
		 paleoClientAdapter.findOperatori(isUSR);		 
	}	

	/**
	 * TODO: implementare la scrittura verso il protocollo  
	 * @param registroDocumento
	 * @param integDTO
	 * @return 
	 * @throws SOAPException 
	 * @throws IOException 
	 * @throws ServiceException 
	 */
	public RespProtocolloPartenza doSubmitFileToExitProtocolService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToExitProtocolService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToExitProtocolService( registroDocumento,  integDTO, null, null, getAttachments(registroDocumento), isUSR, cfResp);
	}	

	public RespProtocolloPartenza doSubmitFileToExitProtocolService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaCorrispondenti, String oggetto, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToExitProtocolService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToExitProtocolService( registroDocumento,  integDTO, listaCorrispondenti, oggetto, getAttachments(registroDocumento), isUSR, cfResp);		 
	}	

	public RespProtocolloPartenza doSubmitFileToExitProtocolServiceUSR(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaCorrispondenti, String oggetto, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToExitProtocolServiceUSR: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToExitProtocolServiceUSR( registroDocumento,  integDTO, listaCorrispondenti, oggetto, getAttachments(registroDocumento), isUSR, cfResp);		 
	}	
	
	public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza doSubmitFileToExitProtocolServicePaleoGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondenti, String oggetto, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToExitProtocolServiceUSR: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToExitProtocolPaleoGiunta( registroDocumento,  integDTO, listaCorrispondenti, oggetto, getAttachments(registroDocumento), cfResp);		 
	}	

	public RespProtocolloArrivo doSubmitFileToIncomingProtocolService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente responsabile, Corrispondente[] listaCorrispondenti, String oggetto, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToIncomingProtocolService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToIncomingtProtocolService(registroDocumento,  integDTO, responsabile, listaCorrispondenti, oggetto, getAttachments(registroDocumento), isUSR, cfResp);	
	}
	
	public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo doSubmitFileToIncomingProtocolServiceGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente responsabile, paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondenti, String oggetto, 
			String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToIncomingProtocolService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToIncomingtProtocolServiceGiunta(registroDocumento,  integDTO, responsabile, listaCorrispondenti, oggetto, 
				 getAttachments(registroDocumento), cfResp);	
	}
	
	private String[] getAttachments(RegistroDocumento registroDocumento) {
		List<DocumentoAttachment> docs = documentoAttachRepository.findByFkFkDocument(registroDocumento.getDocumento().getIdDocumento());
		if(docs == null || docs.size() == 0) return null;
		
		String[] attachment = new String[docs.size()];
		
		for(int j=0; j<docs.size(); j++)
			attachment[j] = docs.get(j).getNome();
		
		return attachment;
	}
	
	
	/**
	 * Servizio per la sottomissione dei Documenti a Paleo usato coem documentale
	 * @param registroDocumento
	 * @param integDTO
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	public RespDocumento doSubmitFileToIntraDocumentalService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToIntraDocumentalService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToArchiviaDocumentoInterno( registroDocumento,  integDTO, isUSR, cfResp);		 
	}
	
	public paleoGiunta.it.marche.regione.paleo.services.RespDocumento doSubmitFileToIntraDocumentalServicePaleoGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String cfResp) throws ServiceException, IOException, SOAPException {
		 logger.debug("doSubmitFileToIntraDocumentalService: "+registroDocumento.getDocumento().getNome());	
		 return paleoClientAdapter.doSubmitFileToArchiviaDocumentoInternoPaleoGiunta( registroDocumento,  integDTO, cfResp);		 
	}
	
	
	
	 private  String getDescrizioneDocumento(List<IntegFrontieraDocumentDTO> allDocument,String tofind) {
		 String retVal=null;
		 for(IntegFrontieraDocumentDTO item: allDocument) {			 
			 String codice=item.getId_paleo();			 
			 if(codice!=null)
			 if(codice.equals(tofind))
				 retVal=codice;			 
		 }
		 return retVal; 
	 }


	public boolean doProtocolDispatchService(String segnatura, boolean isUSR) throws ServiceException, IOException, SOAPException {
		 logger.debug("doProtocolDispatchService: " + segnatura);	
		 return paleoClientAdapter.doProtocolDispatchService(segnatura, isUSR);		 
	}	
	
	
	// xmfPR: paleo attachments download
	public void refreshFilesFromFascicoloPaleo(Conferenza conferenza) throws Exception {
		List<PaleoRegistryAdapter> registryAdapter	= paleoRegistryAdapterRepository.getRegistroForConference(conferenza.getIdConferenza());
		if(registryAdapter == null || registryAdapter.size() == 0) return;
		
		List<PaleoRegistryAdapter> listaPaleoRegistryAdapter = new ArrayList<PaleoRegistryAdapter>();
		String codiceTipoConference = conferenza.getTipologiaConferenzaSpecializzazione().getCodice();
		
		String codiceFascicolo = registryAdapter.get(0).getFkPaleoFascicolo();
		Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(codiceFascicolo,codiceTipoConference);
		
		logger.debug("@refreshFilesFromFascicoloPaleo: " + codiceFascicolo + " - idconf: " + conferenza.getIdConferenza());
		
		try {
			List<PaleoRegistryAdapter> newfiles = downloadFilesFromFascicoloPaleo(codiceFascicolo
					  , idFascioloTipoConferenza
					  , registryAdapter
					  , listaPaleoRegistryAdapter
					  , false);

			List<IntegFrontieraDocumentDTO> listaAllegati = paleoToFrontieraDTOService.listIntegFrontieraDocumentDTO(codiceFascicolo, true);

			List<PaleoDocumentAllegatiAdapterDTO> listDocumentAllegatiAdapterDTO = PaleoDocumentAllegatiAdapterDTO.fillListDocumentAllegatiAdapterDTO (listaAllegati);		
			createConferenceService.creaDocumentiAllegati(listDocumentAllegatiAdapterDTO, conferenza.getIdConferenza(), conferenza.getCodiceFiscaleResponsabileConferenza());
			
		} catch (Exception e) {
			logger.debug("@refreshFilesFromFascicoloPaleo exception["+conferenza.getIdConferenza()+"]: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw e;
		}
	}
	
	private String getLen(List lst) {
		if(lst == null) return "null";
		return ""+lst.size();
	}


	// xmfPR: paleo attachments download
	public List<PaleoRegistryAdapter> downloadFilesFromFascicoloPaleo(String codiceFascicolo
											  , Integer idFascioloTipoConferenza
											  , List<PaleoRegistryAdapter> esitentiAdapter
											  , List<PaleoRegistryAdapter> listaPaleoRegistryAdapter
											  , boolean isUSR)  throws ServiceException, IOException, SOAPException {
		List<PaleoRegistryAdapter> addedListaPaleoRegistryAdapter = new ArrayList<PaleoRegistryAdapter>(); 

		//-2- otiene la lista di fascicoli da Paleo
		//logger.debug("@paleo attach getDocumentiProtocolliInFascicolo -- BEFORE");
		it.marche.regione.paleo.services.RespDocProtocolliInfo[]  listProtocolliInFascicolo= getDocumentiProtocolliInFascicolo(codiceFascicolo, isUSR);
		
		logger.debug("@paleo attach getDocumentiProtocolliInFascicolo list: " + listProtocolliInFascicolo.length + " -esitentiAdapter" + getLen(esitentiAdapter) + " -listaPaleoRegistryAdapter: " + getLen(listaPaleoRegistryAdapter));
		if(listProtocolliInFascicolo!=null && listProtocolliInFascicolo.length!=0) {
			for(RespDocProtocolliInfo item: listProtocolliInFascicolo ) {
				//xmf: try to find the alrteady dowloaded registry adapter in order to exclude duplicates 
				PaleoRegistryAdapter paleoAdater = null;
				
				Integer docNumber = item.getDocNumber();
				if(docNumber != null)
					paleoAdater = paleoRegistryAdapterRepository.getPaleoAdapterByIdPaleoDocAndIdFascicoloTipoconferenza(docNumber, idFascioloTipoConferenza);
				
				if(paleoAdater == null) {
					paleoAdater= new PaleoRegistryAdapter();
					
					/*
					String[] scodfoc=null;
					String coddoc=null;
					//-----------------------------------------------------
					scodfoc=null;
					coddoc=item.getSegnaturaProtocollo();
					
					//logger.debug("segnatura protocollo:"+coddoc);
					//logger.debug("oggetto:"+item.getOggetto());
					if(coddoc!=null && !"".equals(coddoc))
						scodfoc=coddoc.split("\\|");
	
					//------------------------------------------------------------------				  
					// Sembra esserci un problema sull classe RespDocProtocolliInfo ?!?! - Trovato un bug...
					//------------------------------------------------------------------
					if(scodfoc!=null && !"".equals(scodfoc)) {
					  paleoAdater.setIdPaleoDoc(Integer.valueOf(scodfoc[0])) ;
					}else {
					  paleoAdater.setIdPaleoDoc(item.getDocNumber()) ;
					}
					*/

					// xmf: replaced paleo bug-based docNumber handling. THE DOC NUMBER MUST EXIXST TO CORRECTLY AVOID THE DOCUMENTS DUPLICATION ISSUE!!!! 
					paleoAdater.setIdPaleoDoc(docNumber);
					paleoAdater.setIdFascicoloTipoConferenza(idFascioloTipoConferenza);
					paleoAdater.setFkPaleoFascicolo(codiceFascicolo);
					paleoAdater.setIdPaleoRegistro(item.getCodiceRegistro());				  
					paleoAdater.setIdPaleoNumeroDoc(item.getDocNumber());
					paleoAdater.setIdPaleoProtocollo(item.getNumeroProtocollo());
					paleoAdater.setPaleoOggetto(item.getOggetto());
					paleoAdater.setPaleoSegnaturaProtocollo(item.getSegnaturaProtocollo());
					if(item.getDataProtocollo()!=null) {
					  Calendar cal=item.getDataProtocollo();
					  Date date = cal.getTime();             
					  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					  String dataProtocollo = format1.format(date);   					 
					  paleoAdater.setPaleoProtocolloData(dataProtocollo);
					}
					if(item.getTipoProtocollo()!=null && item.getTipoProtocollo().getValue()!=null)
					  paleoAdater.setPaleoTipoProtocollo(item.getTipoProtocollo().getValue());
	
					//TODO prima di salvere andrebbe verificata la preeesistenza..
					//-3-SaVE data in registry		
					Set<Integer> exixtenstItems = esitentiAdapter.stream()
							.map(PaleoRegistryAdapter::getIdPaleoNumeroDoc)
							.collect(Collectors.toSet());
	
					Integer idPaleoDocNumber=null;
					PaleoRegistryAdapter esistente=null;
					idPaleoDocNumber=paleoAdater.getIdPaleoNumeroDoc();
	
					if(esitentiAdapter!=null && !esitentiAdapter.isEmpty())
						for(PaleoRegistryAdapter itemExist : esitentiAdapter) {	  
						  if( Integer.compare(itemExist.getIdPaleoNumeroDoc(),idPaleoDocNumber)==0)
							  esistente=itemExist;
						}
					
					if(esistente==null) {
						paleoAdater=paleoRegistryAdapterRepository.save(paleoAdater);
						addedListaPaleoRegistryAdapter.add(paleoAdater);
					}
					else
						paleoAdater.setId(esistente.getId());
				}
				
				//TODO dopo la preesistenza dewi doc paelo andrebbe controllata l'esistenza degli allegati..
				//-4- Manage Allegati per Oggetto PaleoProtocollo
				manageAllegati(paleoAdater, isUSR);
				listaPaleoRegistryAdapter.add(paleoAdater);				  
			}
			
			logger.debug("@paleo attach foreach listProtocolliInFascicolo -- AFTER");
		}
		
		return addedListaPaleoRegistryAdapter;
	}


	
}
