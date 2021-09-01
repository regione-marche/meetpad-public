package conferenza.adapder.integrazioni.paleo.adapter;

import java.io.FileInputStream;

import java.io.IOException;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAdapterRepository;
import conferenza.adapder.integrazioni.paleo.service.PaleoPropertiesService;
import conferenza.model.Conferenza;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.repository.UtenteRepository;
import it.marche.regione.paleo.services.Allegato;
import it.marche.regione.paleo.services.BEBase;
import it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5;
import it.marche.regione.paleo.services.Classificazione;
import it.marche.regione.paleo.services.Corrispondente;
import it.marche.regione.paleo.services.DatiCorrispondente;
import it.marche.regione.paleo.services.DatiNuovoFascicolo;
import it.marche.regione.paleo.services.DatiProcedimento;
import it.marche.regione.paleo.services.File;
import it.marche.regione.paleo.services.IPaleoService;
import it.marche.regione.paleo.services.OperatorePaleo;
import it.marche.regione.paleo.services.PaleoServiceLocator;
import it.marche.regione.paleo.services.ReqAddAllegati;
import it.marche.regione.paleo.services.ReqCercaProtocollo;
import it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo;
import it.marche.regione.paleo.services.ReqDocumento;
import it.marche.regione.paleo.services.ReqFindRubrica;
import it.marche.regione.paleo.services.ReqProtocolloArrivo;
import it.marche.regione.paleo.services.ReqProtocolloPartenza;
import it.marche.regione.paleo.services.ReqSpedisciProtocollo;
import it.marche.regione.paleo.services.RespAddAllegati;
import it.marche.regione.paleo.services.RespDocumento;
import it.marche.regione.paleo.services.RespDocumentoExt;
import it.marche.regione.paleo.services.RespProtocolloArrivo;
import it.marche.regione.paleo.services.RespProtocolloPartenza;
import it.marche.regione.paleo.services.RespSpedisciProtocollo;
import it.marche.regione.paleo.services.TipoOriginale;
import it.marche.regione.paleo.services.TipoVoceRubrica;
import it.marche.regione.paleo.services.Trasmissione;
import it.marche.regione.paleo.services.TrasmissioneDoc;
import it.marche.regione.paleo.services.TrasmissioneRuolo;
import it.marche.regione.paleo.services.TrasmissioneUtente;

/**
	  <operatore xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
		<CodiceUO>INF</CodiceUO>
		<Cognome>INF</Cognome>
		<Nome>POINT</Nome>
		<Ruolo>PROTOCOLLISTA</Ruolo>
	  </operatore>
	  
 * @author guideluc
 *
 */
@Service
public class PaleoSoapClientAdapter {

	public static boolean isDebugEnabled = false;
	
	private static final Logger logger = LoggerFactory.getLogger(PaleoSoapClientAdapter.class.getName());
	private java.lang.String BasicHttpBinding_IPaleoService_address = "https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc";
	
	@Autowired 
	PaleoClientConfiguration clientConfiguration;
	
	@Autowired 
	DocumentAdapterService documentAdapterService;
	
	@Autowired
	PaleoRegistryAdapterRepository paleoRegistryAdapterRepository; 
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Autowired 
	PaleoPropertiesService paleoPropertiesService;
	
	/**
	 * 
	 * @return
	 * @throws SOAPException
	 */
	private SOAPHeaderElement getSoapHeaderAuthorization(boolean isUSR) 
			throws SOAPException {
		if(isUSR) {
			String user=clientConfiguration.getUrlLoginUserUSR();
			String password=clientConfiguration.getUrlLoginPswdUSR();
			return getSoapHeaderAuthorization(user,password) ;
			
		}
		
		String user=clientConfiguration.getUrlLoginUser();
		String password=clientConfiguration.getUrlLoginPswd();
		
		logger.debug("@paleo auth " + user + " / " + password);
		
		return getSoapHeaderAuthorization(user,password) ;
	}
	
	/**
	 * 
	 * @return
	 * @throws SOAPException
	 */
	private SOAPHeaderElement getSoapHeaderAuthorization(String user,String password) 
			throws SOAPException {
		   SOAPHeaderElement header = new SOAPHeaderElement(
				    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
			    header.setPrefix("o");
			    header.setMustUnderstand(false);
			    SOAPElement node0=header.addChildElement("UsernameToken");
				SOAPElement node1 = node0.addChildElement("Username");
				node1.addTextNode(user);
				SOAPElement node2 = node0.addChildElement("Password");
				node2.addTextNode(password);
				return header;
	}	
	
	/**
	 * Set dei timeout
	 * @param lUrl
	 * @return
	 * @throws IOException
	 */
	private java.net.URL getPortAddress(String lUrl) throws IOException{
		logger.debug("@paleo portAddress: " + lUrl);

		java.net.URL portAddress = new java.net.URL(lUrl);
        URLConnection urlConn = portAddress.openConnection();
        urlConn.setConnectTimeout(3000000);
        urlConn.setReadTimeout(3000000);
        urlConn.setAllowUserInteraction(false);         
        urlConn.setDoOutput(true);
        
		logger.debug("@paleo portAddress OK");
		
        return portAddress;
	}

	private java.net.URL getPortAddress(boolean isUSR) throws IOException{
		String isXmlDebugOn = paleoPropertiesService.getParameterValue("isXmlDebugOn", "DEV");
		PaleoSoapClientAdapter.isDebugEnabled = "yes".equalsIgnoreCase(isXmlDebugOn);
		
		if(isUSR)
	        return getPortAddress(clientConfiguration.getUrlUSR()) ;
			
        return getPortAddress(clientConfiguration.getUrl()) ;
	}	
	
	
	private OperatorePaleo getOperatorePaleo(boolean isUSR) {
		if(isUSR) {
			OperatorePaleo operatore=new OperatorePaleo();
			operatore.setCodiceUO(clientConfiguration.getOperatoreUSRCodiceUO());
			operatore.setCognome(clientConfiguration.getOperatoreUSRCognome());
			operatore.setNome(clientConfiguration.getOperatoreUSRNome());
			operatore.setRuolo(clientConfiguration.getOperatoreUSRRuolo());
			operatore.setCodiceRuolo(clientConfiguration.getOperatoreUSRRuolo());
			return operatore;		
			
		}
		
		OperatorePaleo operatore=new OperatorePaleo();
		operatore.setCodiceUO(clientConfiguration.getOperatoreCodiceUO());
		operatore.setCognome(clientConfiguration.getOperatoreCognome());
		operatore.setNome(clientConfiguration.getOperatoreNome());
		operatore.setRuolo(clientConfiguration.getOperatoreRuolo());
		return operatore;		
	}
	
	
	private paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo getOperatorePaleoPaleoGiunta() {
		
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore=new paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo();
		operatore.setCodiceUO(clientConfiguration.getOperatoreCodiceUO());
		operatore.setCognome(clientConfiguration.getOperatoreCognome());
		operatore.setNome(clientConfiguration.getOperatoreNome());
		operatore.setRuolo(clientConfiguration.getOperatoreRuolo());
		return operatore;		
	}
	
	public it.marche.regione.paleo.services.RegistroInfo[] doCall_GetRegistri(boolean isUSR) 
			throws ServiceException, SOAPException, IOException {
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		return doCall_GetRegistri(operatore, isUSR) ;
		
	}
	
	public it.marche.regione.paleo.services.RegistroInfo[] doCall_GetRegistri(OperatorePaleo operatore, boolean isUSR) 
			throws ServiceException, SOAPException, IOException {
	    
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);	
								
		BEListOfRegistroInfoZA0HwLp5 retVal= port.getRegistri(operatore);	
		return retVal.getLista();		
	}	

	public java.lang.String getBasicHttpBinding_IPaleoService_address() {
		return BasicHttpBinding_IPaleoService_address;
	}

	public void setBasicHttpBinding_IPaleoService_address(java.lang.String basicHttpBinding_IPaleoService_address) {
		BasicHttpBinding_IPaleoService_address = basicHttpBinding_IPaleoService_address;
	}
	
	//GetTitolarioClassificazione
	//GetSerieArchivisticheFascicoli

	public BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione(boolean isUSR) 
			throws ServiceException, IOException, SOAPException {
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);	
								
		return port.getTitolarioClassificazione(operatore)	;						
	}
	
	public BEListOfSerieArchivisticaZA0HwLp5 getSerieArchivisticheFascicoli(boolean isUSR) 
			throws ServiceException, IOException, SOAPException {
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);	
								
		BEListOfSerieArchivisticaZA0HwLp5 res = null;
		try {
			res = port.getSerieArchivisticheFascicoli(operatore);
		} catch (Exception e) {
			throw e;
		}
		finally {
			Stub stub = (org.apache.axis.client.Stub) port;
			logger.debug("@paleo getSerieArchivisticheFascicoli request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
			logger.debug("@paleo getSerieArchivisticheFascicoli response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
		}
		return res;
	}	
	
	//GetDocumentiProtocolliInFascicolo
	public BEListOfrespDocProtocolliInfoZA0HwLp5 getGetDocumentiProtocolliInFascicolo(String codiceFascicolo, boolean isUSR) 
			throws ServiceException, IOException, SOAPException {
		
		logger.debug("@paleo getGetDocumentiProtocolliInFascicolo in...");
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		
		logger.debug("@paleo getGetDocumentiProtocolliInFascicolo operatore: " + operatore);
		
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		logger.debug("@paleo getGetDocumentiProtocolliInFascicolo getBasicHttpBinding_IPaleoService: " + port);
		
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);	
								
		Boolean soloVerificaFascicolo=new Boolean(false);
		Boolean soloPubblici=new Boolean(false);
		
		ReqDocProtocolliInFascicolo richiesta=new ReqDocProtocolliInFascicolo(codiceFascicolo, operatore, soloPubblici, soloVerificaFascicolo);

		BEListOfrespDocProtocolliInfoZA0HwLp5 res = null;
		try {
			res = port.getDocumentiProtocolliInFascicolo(richiesta);						
		} catch (Exception e) {
			logger.debug("@paleo getDocumentiProtocolliInFascicolo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo getDocumentiProtocolliInFascicolo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo getDocumentiProtocolliInFascicolo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
		
	}	
	
	
	public RespDocumentoExt getCercaDocumentoProtocollo(String docNumber,String segnatura, boolean isUSR) throws ServiceException, IOException, SOAPException {
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);	
		
		ReqCercaProtocollo richiesta =new ReqCercaProtocollo();
		richiesta.setOperatore(operatore);
		richiesta.setDocNumber(docNumber);
	
		richiesta.setSegnatura(segnatura);
		richiesta.setWithouthStream(new Boolean(true));
		
		
		RespDocumentoExt res = null;
		try {
			res = port.cercaDocumentoProtocollo(richiesta );
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo cercaDocumentoProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo cercaDocumentoProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
		
	}
	
	public paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt getCercaDocumentoProtocollo(String docNumber,String segnatura) throws ServiceException, IOException, SOAPException {
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore=getOperatorePaleoPaleoGiunta();
		
		paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator locator = new paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator();
		paleoGiunta.it.marche.regione.paleo.services.IPaleoService port= locator.getBasicHttpBinding_IPaleoService(getPortAddress(false));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(false) ;
		((Stub) port).setHeader(header);	
		
		paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo richiesta = new paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo();
		richiesta.setOperatore(operatore);
		richiesta.setDocNumber(docNumber);
	
		richiesta.setSegnatura(segnatura);
		richiesta.setWithouthStream(new Boolean(true));
		
		
		paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt res = null;
		try {
			res = port.cercaDocumentoProtocollo(richiesta );
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo cercaDocumentoProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo cercaDocumentoProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
		
	}
	
	
	public BEBaseOfFileZA0HwLp5 getFile(Integer idFilePaleo, boolean isUSR) throws ServiceException, IOException, SOAPException {
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);		
		
		BEBaseOfFileZA0HwLp5 res = null;
		try {
			res = port.getFile(operatore, idFilePaleo);
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo getFile request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo getFile response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}
	
	public Resource getFileStreamAsResource(Integer idFilePaleo, boolean isUSR) throws ServiceException, IOException, SOAPException {
		BEBaseOfFileZA0HwLp5  response=getFile(idFilePaleo, isUSR);
		File file = response.getOggetto();
		return new ByteArrayResource(file.getStream()) {
			@Override
			public String getFilename() {
				String filename = file.getNome();
				if (file.getEstensione() != null)
					filename = filename.concat(".").concat(file.getEstensione());
				return filename;
			}
		};
	}
	
	private String getMd5(byte[] input) throws NoSuchAlgorithmException{
		
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(input);
	    byte[] digest = md.digest();
	    return DatatypeConverter.printHexBinary(digest);
		
	}
	
	/**
	 * 
	 * @param registroDocumento
	 * @param integDTO
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	private ReqProtocolloPartenza getParametriRichiestaEx(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaCorrispondenti, String oggetto, String[] fileAllegati, boolean isUSR, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//====================================================
		ReqProtocolloPartenza richiesta =new ReqProtocolloPartenza();
		//====================================================
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		//====================================================
		Integer idRegistro=registroDocumento.getId();
		//====================================================		
		
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		String rifIstanza = conf.getRiferimentoIstanza();
		String partialObject = null;
		boolean privato=false;
		
		//Se Usr protocolla l'oggetto deve essere sempre in una maniera che già viene passato
		if (!isUSR || oggetto == null || oggetto.isEmpty())  {
			String lsOggetto="";
			if(registroDocumento.getDocumento()!=null) {
				lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
			}
				
			String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
			if(oggetto == null || oggetto.length() == 0) {
				oggetto=integDTO.getSubject()==null?defObj:(rifIstanza + integDTO.getSubject());
			}	
			else 
			{
				//oggetto+=" - " + (integDTO.getSubject()==null?defObj:integDTO.getSubject());
				partialObject=integDTO.getSubject()==null?defObj:(rifIstanza + integDTO.getSubject());
				partialObject+=" - " + oggetto;
			}
		}
		
		//====================================================
		String note="Le pec sono state mandate in automatico";
		//====================================================
		Classificazione classificazione=new Classificazione();
		//---------------------------------------------------		
		//TODO: DLG - sistemare questa Merda!!!!!!!
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";		
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		Classificazione[] classificazioni=new Classificazione[1] ;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer("1");
			Integer idTipoDati=new Integer("2");
			Integer anniConservazione=new Integer(10);
			DatiNuovoFascicolo nuovoFascicolo=new DatiNuovoFascicolo();
			nuovoFascicolo.setCodiceFaldone(codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);		
			classificazione.setNuovoFascicolo(nuovoFascicolo);
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//---------------------------------------------------
		classificazioni[0]=classificazione;
		//====================================================
		//Non valorizato
		DatiProcedimento procedimento=new DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		
		
		// xmf: dest list changed for PROD ====================================================
		if(listaCorrispondenti == null) {
			Corrispondente destinatario=new Corrispondente();
			destinatario.setCodiceRubrica(integDTO.getCodiceRubricaDestinatario());
			listaCorrispondenti = new Corrispondente[1];
			listaCorrispondenti[0]=destinatario;
		}
		
		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		//it.marche.regione.paleo.services
		File documentoPrincipale=new File();
		try {
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			byte[] resourceByteArray = IOUtils.toByteArray(resource.getInputStream());
			documentoPrincipale.setStream( resourceByteArray );
			documentoPrincipale.setImpronta(getMd5(resourceByteArray));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//====================================================
		
		//Creazione Richiesta
		//====================================================
		//
		//====================================================
		//Operatore
		richiesta.setOperatore(operatore);
		richiesta.setCodiceRegistro(integDTO.getCodiceRegistro());
		//		
		richiesta.setDocumentiAllegati(getAllegati(lMagerAsFile, fileAllegati, true));
		//LA richiesta è sul fascicolo già creato..
		richiesta.setClassificazioni(classificazioni);		
		//richiesta.setProcedimento(procedimento);
		//destinatario di default
		richiesta.setDestinatari(listaCorrispondenti);
		//il file è Pubblico per Default!
		richiesta.setPrivato(privato);		
		//Nome della i.ma determina..
		richiesta.setOggetto(oggetto);		
		//set del solo documento principale...
		richiesta.setDocumentoPrincipale(documentoPrincipale);
		//gli allegati NON vengono passati
		//richiesta.setDocumentiAllegati(documentiAllegati);		
		richiesta.setNote(note);
		
		Trasmissione tTrasmissione = getTrasmissione(operatore, isUSR, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);
		
		return richiesta;
	}
	
	private paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza getParametriRichiestaExGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondenti, 
			String oggetto, String[] fileAllegati, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta =new paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza();
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore= getOperatorePaleoPaleoGiunta();
		//====================================================
		Integer idRegistro=registroDocumento.getId();
		//====================================================		
		
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		String rifIstanza = conf.getRiferimentoIstanza();
		String partialObject = null;
		boolean privato=false;
		
		//Se Usr protocolla l'oggetto deve essere sempre in una maniera che già viene passato
		if (oggetto == null || oggetto.isEmpty())  {
			String lsOggetto="";
			if(registroDocumento.getDocumento()!=null) {
				lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
			}
				
			String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
			if(oggetto == null || oggetto.length() == 0) {
				oggetto=integDTO.getSubject()==null?defObj:(rifIstanza + integDTO.getSubject());
			}	
			else 
			{
				//oggetto+=" - " + (integDTO.getSubject()==null?defObj:integDTO.getSubject());
				partialObject=integDTO.getSubject()==null?defObj:(rifIstanza + integDTO.getSubject());
				partialObject+=" - " + oggetto;
			}
		}
		
		//====================================================
		String note="Le pec sono state mandate in automatico";
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.Classificazione classificazione=new paleoGiunta.it.marche.regione.paleo.services.Classificazione();
		//---------------------------------------------------		
		//TODO: DLG - sistemare questa Merda!!!!!!!
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";		
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		paleoGiunta.it.marche.regione.paleo.services.Classificazione[] classificazioni=new paleoGiunta.it.marche.regione.paleo.services.Classificazione[1] ;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer("1");
			Integer idTipoDati=new Integer("2");
			Integer anniConservazione=new Integer(10);
			paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo=new paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo();
			nuovoFascicolo.setCodiceFaldone(codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);		
			classificazione.setNuovoFascicolo(nuovoFascicolo);
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//---------------------------------------------------
		classificazioni[0]=classificazione;
		//====================================================
		//Non valorizato
		DatiProcedimento procedimento=new DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		
		
		// xmf: dest list changed for PROD ====================================================
		if(listaCorrispondenti == null) {
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente destinatario=new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
			destinatario.setCodiceRubrica(integDTO.getCodiceRubricaDestinatario());
			listaCorrispondenti = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente[1];
			listaCorrispondenti[0]=destinatario;
		}
		
		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		//it.marche.regione.paleo.services
		paleoGiunta.it.marche.regione.paleo.services.File documentoPrincipale=new paleoGiunta.it.marche.regione.paleo.services.File();
		try {
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			byte[] resourceByteArray = IOUtils.toByteArray(resource.getInputStream());
			documentoPrincipale.setStream( resourceByteArray );
			documentoPrincipale.setImpronta(getMd5(resourceByteArray));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//====================================================
		
		//Creazione Richiesta
		//====================================================
		//
		//====================================================
		//Operatore
		richiesta.setOperatore(operatore);
		richiesta.setCodiceRegistro(integDTO.getCodiceRegistro());
		//		
		richiesta.setDocumentiAllegati(getAllegatiPaleoGiunta(lMagerAsFile, fileAllegati, true));
		//LA richiesta è sul fascicolo già creato..
		richiesta.setClassificazioni(classificazioni);		
		//richiesta.setProcedimento(procedimento);
		//destinatario di default
		richiesta.setDestinatari(listaCorrispondenti);
		//il file è Pubblico per Default!
		richiesta.setPrivato(privato);		
		//Nome della i.ma determina..
		richiesta.setOggetto(oggetto);		
		//set del solo documento principale...
		richiesta.setDocumentoPrincipale(documentoPrincipale);
		//gli allegati NON vengono passati
		//richiesta.setDocumentiAllegati(documentiAllegati);		
		richiesta.setNote(note);
		
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione tTrasmissione = getTrasmissione(operatore, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);
		
		return richiesta;
	}
	
	/**
	 * 
	 * @param registroDocumento
	 * @param integDTO
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	private ReqProtocolloPartenza getParametriRichiesta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaDestinatari, String oggetto, String[] fileAllegati, boolean isUSR, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//====================================================
		ReqProtocolloPartenza richiesta =new ReqProtocolloPartenza();
		//====================================================
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		
		//====================================================
		Integer idRegistro=registroDocumento.getId();
		//====================================================		
		boolean privato=false;
		String lsOggetto="";
		if(registroDocumento.getDocumento()!=null)
			lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
		String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
		if(oggetto == null || oggetto.length() == 0)
			oggetto=integDTO.getSubject()==null?defObj:integDTO.getSubject();
		//else
			//oggetto+=" - " + (integDTO.getSubject()==null?defObj:integDTO.getSubject());
		logger.debug("oggetto vale: " + oggetto);
		logger.debug("defObj vale: " + defObj);
		logger.debug("integDTO vale: " + integDTO.getSubject());
		//====================================================
		String note="Le pec sono state inviate da Paleo";
		//====================================================
		Classificazione classificazione=new Classificazione();
		//---------------------------------------------------		
		//TODO: DLG - sistemare questa Merda!!!!!!!
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";		
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		Classificazione[] classificazioni=new Classificazione[1] ;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer("1");
			Integer idTipoDati=new Integer("2");
			Integer anniConservazione=new Integer(10);
			DatiNuovoFascicolo nuovoFascicolo=new DatiNuovoFascicolo();
			nuovoFascicolo.setCodiceFaldone(codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);		
			classificazione.setNuovoFascicolo(nuovoFascicolo);
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//---------------------------------------------------
		classificazioni[0]=classificazione;
		//====================================================
		//Non valorizato
		DatiProcedimento procedimento=new DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		//====================================================

		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		//it.marche.regione.paleo.services
		File documentoPrincipale=new File();
		try {
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			byte[] resourceByteArray = IOUtils.toByteArray(resource.getInputStream());
			//documentoPrincipale.setMimeType("application/octet-stream");
			documentoPrincipale.setStream( resourceByteArray );
			documentoPrincipale.setImpronta(getMd5(resourceByteArray));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//====================================================
		
		//Creazione Richiesta
		//====================================================
		//
		//====================================================
		//Operatore
		richiesta.setOperatore(operatore);
		richiesta.setCodiceRegistro(integDTO.getCodiceRegistro());
		//		
		richiesta.setDocumentiAllegati(getAllegati(lMagerAsFile, fileAllegati, true));
		//LA richiesta è sul fascicolo già creato..
		richiesta.setClassificazioni(classificazioni);		
		//richiesta.setProcedimento(procedimento);
		//destinatario di default
		richiesta.setDestinatari(listaDestinatari);
		//il file è Pubblico per Default!
		richiesta.setPrivato(privato);		
		//Nome della i.ma determina..
		richiesta.setOggetto(oggetto);		
		//set del solo documento principale...
		richiesta.setDocumentoPrincipale(documentoPrincipale);
		//gli allegati NON vengono passati
		//richiesta.setDocumentiAllegati(documentiAllegati);	
		richiesta.setDocumentoPrincipaleAcquisitoIntegralmente(true); 
		richiesta.setDocumentoPrincipaleOriginale(TipoOriginale.Digitale);
		richiesta.setNote(note);

		Trasmissione tTrasmissione = getTrasmissione(operatore, isUSR, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);

		return richiesta;
	}
	
	private paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza getParametriRichiestaPaleoGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaDestinatari, String oggetto, String[] fileAllegati,String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta =new paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza();
		//====================================================
		
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore=  getOperatorePaleoPaleoGiunta();
		
		//====================================================
		Integer idRegistro=registroDocumento.getId();
		//====================================================		
		boolean privato=false;
		String lsOggetto="";
		if(registroDocumento.getDocumento()!=null)
			lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
		String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
		if(oggetto == null || oggetto.length() == 0)
			oggetto=integDTO.getSubject()==null?defObj:integDTO.getSubject();
		//else
			//oggetto+=" - " + (integDTO.getSubject()==null?defObj:integDTO.getSubject());
		logger.debug("oggetto vale: " + oggetto);
		logger.debug("defObj vale: " + defObj);
		logger.debug("integDTO vale: " + integDTO.getSubject());
		//====================================================
		String note="Le pec sono state inviate da Paleo";
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.Classificazione classificazione=new paleoGiunta.it.marche.regione.paleo.services.Classificazione();
		//---------------------------------------------------		
		//TODO: DLG - sistemare questa Merda!!!!!!!
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";		
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		paleoGiunta.it.marche.regione.paleo.services.Classificazione[] classificazioni=new paleoGiunta.it.marche.regione.paleo.services.Classificazione[1] ;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer("1");
			Integer idTipoDati=new Integer("2");
			Integer anniConservazione=new Integer(10);
			paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo=new paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo();
			nuovoFascicolo.setCodiceFaldone(codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);		
			classificazione.setNuovoFascicolo(nuovoFascicolo);
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//---------------------------------------------------
		classificazioni[0]=classificazione;
		//====================================================
		//Non valorizato
		paleoGiunta.it.marche.regione.paleo.services.DatiProcedimento procedimento=new paleoGiunta.it.marche.regione.paleo.services.DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		//====================================================

		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		//it.marche.regione.paleo.services
		paleoGiunta.it.marche.regione.paleo.services.File documentoPrincipale=new paleoGiunta.it.marche.regione.paleo.services.File();
		try {
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			byte[] resourceByteArray = IOUtils.toByteArray(resource.getInputStream());
			//documentoPrincipale.setMimeType("application/octet-stream");
			documentoPrincipale.setStream( resourceByteArray );
			documentoPrincipale.setImpronta(getMd5(resourceByteArray));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//====================================================
		
		//Creazione Richiesta
		//====================================================
		//
		//====================================================
		//Operatore
		richiesta.setOperatore(operatore);
		richiesta.setCodiceRegistro(integDTO.getCodiceRegistro());
		//		
		richiesta.setDocumentiAllegati(getAllegatiPaleoGiunta(lMagerAsFile, fileAllegati, true));
		//LA richiesta è sul fascicolo già creato..
		richiesta.setClassificazioni(classificazioni);		
		//richiesta.setProcedimento(procedimento);
		//destinatario di default
		richiesta.setDestinatari(listaDestinatari);
		//il file è Pubblico per Default!
		richiesta.setPrivato(privato);		
		//Nome della i.ma determina..
		richiesta.setOggetto(oggetto);		
		//set del solo documento principale...
		richiesta.setDocumentoPrincipale(documentoPrincipale);
		//gli allegati NON vengono passati
		//richiesta.setDocumentiAllegati(documentiAllegati);	
		richiesta.setDocumentoPrincipaleAcquisitoIntegralmente(true); 
		richiesta.setDocumentoPrincipaleOriginale(paleoGiunta.it.marche.regione.paleo.services.TipoOriginale.Digitale);
		richiesta.setNote(note);

		
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore2=  getOperatorePaleoPaleoGiunta();
		
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione tTrasmissione = getTrasmissione(operatore2, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);
		return richiesta;
	}
	
	public RespProtocolloPartenza  doSubmitFileToExitProtocolService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaCorrispondenti, String oggetto, String[] allegati, boolean isUSR, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);		
		//==================================================================================
		ReqProtocolloPartenza richiesta=getParametriRichiestaEx(registroDocumento, integDTO, listaCorrispondenti, oggetto, allegati, isUSR, cfResp); 
		//==================================================================================
		
		RespProtocolloPartenza res = null;
		try {
			res = port.protocollazionePartenza(richiesta );
			
			if(!"error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue()))			
				addAllegatiDocumentoProtocollo(registroDocumento.getId(), res.getDocNumber(), res.getSegnatura(), isUSR, allegati);
			
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo protocollazionePartenza request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo protocollazionePartenza response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}

	/**
	 * 
	 * 
	 * @param registroDocumento
	 * @param integDTO
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	public RespProtocolloArrivo doSubmitFileToIncomingtProtocolService(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente responsabile, Corrispondente[] listaCorrispondenti, String oggetto, String[] allegati, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);		
		//==================================================================================
		ReqProtocolloPartenza richiesta=getParametriRichiestaEx(registroDocumento, integDTO, listaCorrispondenti, oggetto, allegati, isUSR, cfResp);
		//==================================================================================
		//DLG ..sembra ci sia stata una regressione?!?!?!
		//serie di set..
		ReqProtocolloArrivo richiestaArrivo =fillReqProtocolloArrivo(richiesta, responsabile);

		RespProtocolloArrivo res = null;
		try {
			res = port.protocollazioneEntrata(richiestaArrivo);
			
			if(!"error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue()))			
				addAllegatiDocumentoProtocollo(registroDocumento.getId(), res.getDocNumber(), res.getSegnatura(), isUSR, allegati);
			
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo protocollazioneEntrata request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo protocollazioneEntrata response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}	
	
	public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo doSubmitFileToIncomingtProtocolServiceGiunta(RegistroDocumento registroDocumento, 
			IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente responsabile, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondenti, String oggetto, 
			String[] allegati, String cfResp) 
					throws ServiceException, IOException, SOAPException {
		//==================================================================================
		paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator locator = new paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator();
		paleoGiunta.it.marche.regione.paleo.services.IPaleoService port= locator.getBasicHttpBinding_IPaleoService(getPortAddress(false));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(false);
		((Stub) port).setHeader(header);		
		//==================================================================================
		paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta=getParametriRichiestaExGiunta(registroDocumento, integDTO, 
				listaCorrispondenti, oggetto, allegati,  cfResp);
		//==================================================================================
		//DLG ..sembra ci sia stata una regressione?!?!?!
		//serie di set..
		paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo richiestaArrivo =fillReqProtocolloArrivoGiunta(richiesta, responsabile);

		paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo res = null;
		try {
			res = port.protocollazioneEntrata(richiestaArrivo);
			
			if(!"error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue()))			
				addAllegatiDocumentoProtocolloGiunta(registroDocumento.getId(), res.getDocNumber(), res.getSegnatura(), allegati);
			
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo protocollazioneEntrata request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo protocollazioneEntrata response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}	
	
	/**
	    private java.util.Calendar dataArrivo;
	    private java.util.Calendar dataProtocolloMittente;
	    private it.marche.regione.paleo.services.Corrispondente mittente;
	    private java.lang.String protocolloMittente;    
	 */
	public ReqProtocolloArrivo fillReqProtocolloArrivo(ReqProtocolloPartenza richiesta, Corrispondente mittente){
		ReqProtocolloArrivo richiestaArrivo =new ReqProtocolloArrivo();
		//===================================================================
		Date datanow=new Date();
		Calendar calendarDateNow = null;
		Calendar dataArrivo=null;
		Calendar dataProtocolloMittente=null;
		try {			
			calendarDateNow = Calendar.getInstance();
			calendarDateNow.setTime(datanow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if(calendarDateNow!=null){
			dataArrivo=calendarDateNow;
			dataProtocolloMittente=calendarDateNow;
		}
		//===================================================================
		boolean privato=false;
		boolean acquisitoIntegralmente=true;
		String tipoDocumento=richiesta.getTipoDocumento();
		Trasmissione trasmissione=richiesta.getTrasmissione();
		
		//xmf: RESPONSABILE
//		Corrispondente mittente=new Corrispondente();
//		if(richiesta.getDestinatari().length > 0) {
//			mittente.setCodiceRubrica(richiesta.getDestinatari()[0].getCodiceRubrica());
//		}
		richiestaArrivo.setCodiceRegistro(richiesta.getCodiceRegistro());
		
		//===================================================================
		richiestaArrivo.setClassificazioni(richiesta.getClassificazioni());
		richiestaArrivo.setDataArrivo(dataArrivo);		
		richiestaArrivo.setDataProtocolloMittente(dataProtocolloMittente);
		
		richiestaArrivo.setDocumentoPrincipale(richiesta.getDocumentoPrincipale());
		//richiestaArrivo.setDocumentiAllegati(richiesta.getDocumentiAllegati());
		richiestaArrivo.setMittente(mittente);
		richiestaArrivo.setDocumentoPrincipaleAcquisitoIntegralmente(acquisitoIntegralmente);		
		richiestaArrivo.setDocumentoPrincipaleOriginale(TipoOriginale.Digitale);
		richiestaArrivo.setNote(richiesta.getNote());
		richiestaArrivo.setOperatore(richiesta.getOperatore());
		
		richiestaArrivo.setOggetto(richiesta.getOggetto());
		richiestaArrivo.setPrivato(privato);		
		richiestaArrivo.setTipoDocumento(tipoDocumento);		
		richiestaArrivo.setTrasmissione(trasmissione);
		return richiestaArrivo;
	} 
	
	public paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo fillReqProtocolloArrivoGiunta
		(paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta, 
	     paleoGiunta.it.marche.regione.paleo.services.Corrispondente mittente){
		
		paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo richiestaArrivo =new paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo();
		//===================================================================
		Date datanow=new Date();
		Calendar calendarDateNow = null;
		Calendar dataArrivo=null;
		Calendar dataProtocolloMittente=null;
		try {			
			calendarDateNow = Calendar.getInstance();
			calendarDateNow.setTime(datanow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		if(calendarDateNow!=null){
			dataArrivo=calendarDateNow;
			dataProtocolloMittente=calendarDateNow;
		}
		//===================================================================
		boolean privato=false;
		boolean acquisitoIntegralmente=true;
		String tipoDocumento=richiesta.getTipoDocumento();
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione trasmissione=richiesta.getTrasmissione();
		
		//xmf: RESPONSABILE
//		Corrispondente mittente=new Corrispondente();
//		if(richiesta.getDestinatari().length > 0) {
//			mittente.setCodiceRubrica(richiesta.getDestinatari()[0].getCodiceRubrica());
//		}
		richiestaArrivo.setCodiceRegistro(richiesta.getCodiceRegistro());
		
		//===================================================================
		richiestaArrivo.setClassificazioni(richiesta.getClassificazioni());
		richiestaArrivo.setDataArrivo(dataArrivo);		
		richiestaArrivo.setDataProtocolloMittente(dataProtocolloMittente);
		
		richiestaArrivo.setDocumentoPrincipale(richiesta.getDocumentoPrincipale());
		//richiestaArrivo.setDocumentiAllegati(richiesta.getDocumentiAllegati());
		richiestaArrivo.setMittente(mittente);
		richiestaArrivo.setDocumentoPrincipaleAcquisitoIntegralmente(acquisitoIntegralmente);		
		richiestaArrivo.setDocumentoPrincipaleOriginale(paleoGiunta.it.marche.regione.paleo.services.TipoOriginale.Digitale);
		richiestaArrivo.setNote(richiesta.getNote());
		richiestaArrivo.setOperatore(richiesta.getOperatore());
		
		richiestaArrivo.setOggetto(richiesta.getOggetto());
		richiestaArrivo.setPrivato(privato);		
		richiestaArrivo.setTipoDocumento(tipoDocumento);		
		richiestaArrivo.setTrasmissione(trasmissione);
		return richiestaArrivo;
	} 
	
	
	
	/**
	 * Paleo Vine usato come Documentale:
	 * do Submit File To Archivia Documento Interno
	 * Le altre possibilità sono per la Protocollazione.
	 * Quindi si hanno due alternative per PAleo: aut Sistema protocollante, aut documentale
	 * 
	 * L'uso come Documentale è un Parametro
	 * La configurazione si fà tramite la tabella 
	 * 			paleo_registry_filter
	 * vengono specificati:
	 * 1) la tipologia di conferenza (specializzazione)
	 * 2) il tipo di filtro
	 * 3) il codice di riferimento sull'observer registry per individuare la classe che deve farne il marshaler
	 * 4) la tipologia di documento
	 * 
	 * 
	 * @param registroDocumento
	 * @param integDTO
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	public RespDocumento doSubmitFileToArchiviaDocumentoInterno(RegistroDocumento registroDocumento,
			IntegProtocolloDTO integDTO, boolean isUSR, String cfResp) throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);		
		//==================================================================================
		ReqDocumento richiesta=archiviaDocumentoInterno(registroDocumento, integDTO, isUSR, cfResp); 		
		//==================================================================================
		//serie di set..
		RespDocumento res = null;
		try {
			res = port.archiviaDocumentoInterno(richiesta);
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo archiviaDocumentoInterno request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo archiviaDocumentoInterno response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}		
	
	
	
	public paleoGiunta.it.marche.regione.paleo.services.RespDocumento doSubmitFileToArchiviaDocumentoInternoPaleoGiunta(RegistroDocumento registroDocumento,
			IntegProtocolloDTO integDTO, String cfResp) throws ServiceException, IOException, SOAPException {
		//==================================================================================
		paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator locator = new paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator();
		paleoGiunta.it.marche.regione.paleo.services.IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(clientConfiguration.getUrl()));							
		//SET SOAP - HEADER AUTHORIZATION
		String user=clientConfiguration.getUrlLoginUser();
		String password=clientConfiguration.getUrlLoginPswd();
		
		logger.debug("@paleo auth " + user + " / " + password);		
	    SOAPHeaderElement header = getSoapHeaderAuthorization(user,password);
		((Stub) port).setHeader(header);		
		//==================================================================================
		paleoGiunta.it.marche.regione.paleo.services.ReqDocumento richiesta=archiviaDocumentoInternoPaleoGiunta(registroDocumento, integDTO, cfResp); 		
		//==================================================================================
		//serie di set..
		paleoGiunta.it.marche.regione.paleo.services.RespDocumento res = null;
		try {
			res = port.archiviaDocumentoInterno(richiesta);
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo archiviaDocumentoInterno request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo archiviaDocumentoInterno response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}		
	
	/**
	 * 
	 * @param registroDocumento
	 * @param integDTO -codiceFascicolo =f(IntegProtocolloDTO.getProtocollo)
	 * @throws ServiceException
	 * @throws IOException
	 * @throws SOAPException
	 */
	public ReqDocumento archiviaDocumentoInterno(
			RegistroDocumento registroDocumento
			,IntegProtocolloDTO integDTO,
			boolean isUSR,
			String cfResp
			) throws ServiceException, IOException, SOAPException {
		
		ReqDocumento richiesta=new ReqDocumento();
		//==============================================
		//DEFAULT!!!!!
		//==============================================				
		boolean privato=false;
		boolean documentoPrincipaleAcquisitoIntegralmente=true;
		String serieArchivistica="1";
		String  tipoDati="2";
		//==============================================	
		TipoOriginale documentoPrincipaleOriginale=TipoOriginale.Digitale;
		//==============================================
		Classificazione[] classificazioni=null;
		Allegato[] documentiAllegati=null;
		String note=null;		
		String oggetto=null;
		DatiProcedimento procedimento=null;
		String tipoDocumento=null;
		Trasmissione trasmissione=null;
		
		
		//====================================================
		//[OPERATRORE]
		//operatore=getOperatorePaleo();
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		//====================================================
		//[ID REGISTRO]
		Integer idRegistro=registroDocumento.getId();
		//==============================================		
		
		String lsOggetto="";
		if(registroDocumento.getDocumento()!=null)
			lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
		String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
		oggetto=integDTO.getSubject()==null?defObj:integDTO.getSubject();
		//====================================================
		note="Le pec sono state mandater in automatico";
		//====================================================
		Classificazione classificazione=new Classificazione();
		//---------------------------------------------------
		//il codice protocollo è usato impropriamente - contiene il fascicolo!?!!?
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		classificazioni=new Classificazione[2] ;
		classificazioni[0]=classificazione;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer(serieArchivistica);
			Integer idTipoDati=new Integer(tipoDati);
			Integer anniConservazione=new Integer(10);
			DatiNuovoFascicolo nuovoFascicolo=new DatiNuovoFascicolo();
			logger.debug("codiceClassifica: "+ codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);	
			classificazione=new Classificazione();
			classificazione.setNuovoFascicolo(nuovoFascicolo);
			//---------------------------------------------------
			classificazioni[1]=classificazione;
			richiesta.setClassificazioni(classificazioni);		
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//====================================================
		//[PROCEDIMENTO] Non valorizato
		procedimento=new DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		//====================================================
		Corrispondente destinatario=new Corrispondente();
		String codiceRubrica="MeetPAD1";
		DatiCorrispondente corrispondenteOccasionale=new DatiCorrispondente();
		corrispondenteOccasionale.setEmail("corrispondente.occasionale@meetpad.it");
		corrispondenteOccasionale.setNome("Corrispondente");
		corrispondenteOccasionale.setCognome("Occasionale");
		corrispondenteOccasionale.setTipo(TipoVoceRubrica.Indefinito);
		destinatario.setCorrispondenteOccasionale(corrispondenteOccasionale);
		//
		Corrispondente[] destinatari=new Corrispondente[1];
		destinatari[0]=destinatario;		
		//====================================================
		File documentoPrincipale= null;
		Resource resource=null;
		try {
			resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
			java.io.File lMagerAsFile=resource.getFile();
			//it.marche.regione.paleo.services
			documentoPrincipale=new File();
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			documentoPrincipale.setStream(IOUtils.toByteArray(resource.getInputStream()));
			documentoPrincipale.setImpronta(getMd5(IOUtils.toByteArray(resource.getInputStream())));
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		//====================================================
		//ALLEGATO
		//====================================================
		Allegato allegato =new Allegato();
		allegato.setNumeroPagine(1);
		allegato.setDescrizione("Allegato1");
		File documento=documentoPrincipale;
		allegato.setDocumento(documento);
		documentiAllegati=new Allegato[1];
		documentiAllegati[0]=allegato;
		
		//==============================================
		//TRASMISSIONE
		//==============================================
//		trasmissione=new Trasmissione();
//		trasmissione.setNoteGenerali("Note Generali");
//		trasmissione.setSegueCartaceo(false);
//		TrasmissioneRuolo[] trasmissioniRuolo=new TrasmissioneRuolo[1];
//		TrasmissioneRuolo truolo=new TrasmissioneRuolo();
//		truolo.setCodiceUODestinataria("INF");
//		truolo.setRuoloDestinatario("Impiegato");
//		truolo.setRagione("Inoltro_a_ruolo");
//		
//		//==============================================
//		//DATA RUOLO
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//		String dateInString = "01-01-2099 00:00:00";
//		Calendar calendar = Calendar.getInstance();
//		try {
//			 calendar.set(2019, 01, 01);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		truolo.setDataScadenza(calendar);
//		//==============================================
//		trasmissioniRuolo[0]=truolo;
//		trasmissione.setTrasmissioniRuolo(trasmissioniRuolo);
		
		Trasmissione tTrasmissione = getTrasmissione(operatore, isUSR, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);

		
		//==============================================
		//[REQ DOCUMENTO] -> richiesta!!!
		//==============================================
		//set del documento Principale
		if(documentoPrincipale!=null) {			
			richiesta.setDocumentoPrincipale(documentoPrincipale);
			richiesta.setDocumentoPrincipaleAcquisitoIntegralmente(documentoPrincipaleAcquisitoIntegralmente);
			richiesta.setDocumentoPrincipaleOriginale(documentoPrincipaleOriginale);
		}
		
		//Allegati
		//richiesta.setDocumentiAllegati(documentiAllegati);
		richiesta.setNote(note);
		richiesta.setOggetto(oggetto);
		richiesta.setOperatore(operatore);
		richiesta.setPrivato(privato);
		//Non lo valorizzo!
		//richiesta.setProcedimento(procedimento);
		if(tipoDocumento!=null)
			richiesta.setTipoDocumento(tipoDocumento);
		if(trasmissione!=null)
			richiesta.setTrasmissione(trasmissione);
		
		//==============================================		
		return richiesta;		
	}
	
	public paleoGiunta.it.marche.regione.paleo.services.ReqDocumento archiviaDocumentoInternoPaleoGiunta(
			RegistroDocumento registroDocumento
			,IntegProtocolloDTO integDTO,
			String cfResp
			) throws ServiceException, IOException, SOAPException {
		
		paleoGiunta.it.marche.regione.paleo.services.ReqDocumento richiesta=new paleoGiunta.it.marche.regione.paleo.services.ReqDocumento();
		//==============================================
		//DEFAULT!!!!!
		//==============================================				
		boolean privato=false;
		boolean documentoPrincipaleAcquisitoIntegralmente=true;
		String serieArchivistica="1";
		String  tipoDati="2";
		//==============================================	
		paleoGiunta.it.marche.regione.paleo.services.TipoOriginale documentoPrincipaleOriginale=paleoGiunta.it.marche.regione.paleo.services.TipoOriginale.Digitale;
		//==============================================
		paleoGiunta.it.marche.regione.paleo.services.Classificazione[] classificazioni=null;
		paleoGiunta.it.marche.regione.paleo.services.Allegato[] documentiAllegati=null;
		String note=null;		
		String oggetto=null;
		paleoGiunta.it.marche.regione.paleo.services.DatiProcedimento procedimento=null;
		String tipoDocumento=null;
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione trasmissione=null;
		
		
		//====================================================
		//[OPERATRORE]
		//operatore=getOperatorePaleo();
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore=getOperatorePaleoPaleoGiunta();
		//====================================================
		//[ID REGISTRO]
		Integer idRegistro=registroDocumento.getId();
		//==============================================		
		
		String lsOggetto="";
		if(registroDocumento.getDocumento()!=null)
			lsOggetto=paleoRegistryAdapterRepository.getRaggruppamentoByIdDocument(registroDocumento.getDocumento().getIdDocumento());
		String defObj="conferenza- registro-"+registroDocumento.getId()+"- doc - " +registroDocumento.getDocumento().getNome()+" - "+lsOggetto;
		oggetto=integDTO.getSubject()==null?defObj:integDTO.getSubject();
		//====================================================
		note="Le pec sono state mandater in automatico";
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.Classificazione classificazione=new paleoGiunta.it.marche.regione.paleo.services.Classificazione();
		//---------------------------------------------------
		//il codice protocollo è usato impropriamente - contiene il fascicolo!?!!?
		//List<String> codiciFascicolo=paleoRegistryAdapterRepository.getFascicoloByIdRegistro(registroDocumento.getId());  //"150.30.130/2011/INF/55";
		//String codiceFascicolo = codiciFascicolo.isEmpty() ? null : codiciFascicolo.get(0);
		
		String codiceFascicolo = integDTO.getCodiceProtocollo();
		logger.debug("Fascicolo da Protocollare: "+ codiceFascicolo);
		//---------------------------------------------------
		classificazione.setCodiceFascicolo(codiceFascicolo);
		classificazioni=new paleoGiunta.it.marche.regione.paleo.services.Classificazione[2] ;
		classificazioni[0]=classificazione;
		//---------------------------------------------------
		//Dati Nuovo fascicolo per Classificazione.. 
		//---------------------------------------------------
		if(codiceFascicolo!=null) {
			String codiceClassifica=null;
			String[] ccSplit=codiceFascicolo.split("[/]");
			codiceClassifica=ccSplit[0];
			Integer idSerieArchivistica=new Integer(serieArchivistica);
			Integer idTipoDati=new Integer(tipoDati);
			Integer anniConservazione=new Integer(10);
			paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo=new paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo();
			logger.debug("codiceClassifica: "+ codiceClassifica);
			nuovoFascicolo.setCodiceClassifica(codiceClassifica);
			nuovoFascicolo.setCustode(operatore);
			nuovoFascicolo.setDescrizione("FascicoloDaWS");		
			nuovoFascicolo.setIdSerieArchivistica(idSerieArchivistica);		
			nuovoFascicolo.setIdTipoDati(idTipoDati);
			nuovoFascicolo.setAnniConservazione(anniConservazione);	
			classificazione=new paleoGiunta.it.marche.regione.paleo.services.Classificazione();
			classificazione.setNuovoFascicolo(nuovoFascicolo);
			//---------------------------------------------------
			classificazioni[1]=classificazione;
			richiesta.setClassificazioni(classificazioni);		
		}else {
			logger.debug("Controllare: il codice fascicolo è null?!!?");
		}
		//====================================================
		//[PROCEDIMENTO] Non valorizato
		procedimento=new paleoGiunta.it.marche.regione.paleo.services.DatiProcedimento();
		Integer numeroProcedimento=null;
		String codiceTipoProcedimento=null;
		String statoProcedimento=null;
		procedimento.setNumeroProcedimento(numeroProcedimento);		
		procedimento.setCodiceTipoProcedimento(codiceTipoProcedimento);		
		procedimento.setStatoProcedimento(statoProcedimento);
		//====================================================
		Corrispondente destinatario=new Corrispondente();
		String codiceRubrica="MeetPAD1";
		DatiCorrispondente corrispondenteOccasionale=new DatiCorrispondente();
		corrispondenteOccasionale.setEmail("corrispondente.occasionale@meetpad.it");
		corrispondenteOccasionale.setNome("Corrispondente");
		corrispondenteOccasionale.setCognome("Occasionale");
		corrispondenteOccasionale.setTipo(TipoVoceRubrica.Indefinito);
		destinatario.setCorrispondenteOccasionale(corrispondenteOccasionale);
		//
		Corrispondente[] destinatari=new Corrispondente[1];
		destinatari[0]=destinatario;		
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.File documentoPrincipale= null;
		Resource resource=null;
		try {
			resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
			java.io.File lMagerAsFile=resource.getFile();
			//it.marche.regione.paleo.services
			documentoPrincipale=new paleoGiunta.it.marche.regione.paleo.services.File();
			documentoPrincipale.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
			documentoPrincipale.setNome(lMagerAsFile.getName());
			documentoPrincipale.setStream(IOUtils.toByteArray(resource.getInputStream()));
			documentoPrincipale.setImpronta(getMd5(IOUtils.toByteArray(resource.getInputStream())));
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		//====================================================
		//ALLEGATO
		//====================================================
		paleoGiunta.it.marche.regione.paleo.services.Allegato allegato =new paleoGiunta.it.marche.regione.paleo.services.Allegato();
		allegato.setNumeroPagine(1);
		allegato.setDescrizione("Allegato1");
		paleoGiunta.it.marche.regione.paleo.services.File documento=documentoPrincipale;
		allegato.setDocumento(documento);
		documentiAllegati=new paleoGiunta.it.marche.regione.paleo.services.Allegato[1];
		documentiAllegati[0]=allegato;
		
		//==============================================
		//TRASMISSIONE
		//==============================================
//		trasmissione=new Trasmissione();
//		trasmissione.setNoteGenerali("Note Generali");
//		trasmissione.setSegueCartaceo(false);
//		TrasmissioneRuolo[] trasmissioniRuolo=new TrasmissioneRuolo[1];
//		TrasmissioneRuolo truolo=new TrasmissioneRuolo();
//		truolo.setCodiceUODestinataria("INF");
//		truolo.setRuoloDestinatario("Impiegato");
//		truolo.setRagione("Inoltro_a_ruolo");
//		
//		//==============================================
//		//DATA RUOLO
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//		String dateInString = "01-01-2099 00:00:00";
//		Calendar calendar = Calendar.getInstance();
//		try {
//			 calendar.set(2019, 01, 01);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		truolo.setDataScadenza(calendar);
//		//==============================================
//		trasmissioniRuolo[0]=truolo;
//		trasmissione.setTrasmissioniRuolo(trasmissioniRuolo);
		paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore2 = getOperatorePaleoPaleoGiunta();
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione tTrasmissione = getTrasmissione(operatore2, cfResp);
		if(tTrasmissione != null)
			richiesta.setTrasmissione(tTrasmissione);

		
		//==============================================
		//[REQ DOCUMENTO] -> richiesta!!!
		//==============================================
		//set del documento Principale
		if(documentoPrincipale!=null) {			
			richiesta.setDocumentoPrincipale(documentoPrincipale);
			richiesta.setDocumentoPrincipaleAcquisitoIntegralmente(documentoPrincipaleAcquisitoIntegralmente);
			richiesta.setDocumentoPrincipaleOriginale(documentoPrincipaleOriginale);
		}
		
		//Allegati
		//richiesta.setDocumentiAllegati(documentiAllegati);
		richiesta.setNote(note);
		richiesta.setOggetto(oggetto);
		richiesta.setOperatore(operatore);
		richiesta.setPrivato(privato);
		//Non lo valorizzo!
		//richiesta.setProcedimento(procedimento);
		if(tipoDocumento!=null)
			richiesta.setTipoDocumento(tipoDocumento);
		if(trasmissione!=null)
			richiesta.setTrasmissione(trasmissione);
		
		//==============================================		
		return richiesta;		
	}

	public RespProtocolloPartenza  doSubmitFileToExitProtocolServiceUSR(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Corrispondente[] listaCorrispondenti, String oggetto, String[] allegati, boolean isUSR, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
			PaleoServiceLocator locator = new PaleoServiceLocator();
	        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));			
	        
	        
	        
			Integer timeout = 300000;
			Stub stub = (org.apache.axis.client.Stub) port;
			stub._setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, timeout); 
			stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, timeout); 
			stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, timeout);
			
			stub.setTimeout(timeout);
			
			//SET SOAP - HEADER AUTHORIZATION 
		    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
			((Stub) port).setHeader(header);		
			//==================================================================================
			ReqProtocolloPartenza richiesta=getParametriRichiesta(registroDocumento, integDTO, listaCorrispondenti, oggetto, allegati, isUSR, cfResp); 
			//==================================================================================
			RespProtocolloPartenza res = null;
			try {
				res = port.protocollazionePartenza(richiesta );

				if(!"error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue()))			
					addAllegatiDocumentoProtocollo(registroDocumento.getId(), res.getDocNumber(), res.getSegnatura(), isUSR, allegati);
				
			} catch (Exception e) {
				throw e;
			}
			finally {
				if(isDebugEnabled) {
					logger.debug("@paleo protocollazionePartenza request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@paleo protocollazionePartenza response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
			return res;
	}
	
	public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza  doSubmitFileToExitProtocolPaleoGiunta(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, 
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondenti, String oggetto, String[] allegati, String cfResp) 
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
		    paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator locator = new paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator();
		    paleoGiunta.it.marche.regione.paleo.services.IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(clientConfiguration.getUrl()));			
	        
	        
	        
			Integer timeout = 300000;
			Stub stub = (org.apache.axis.client.Stub) port;
			stub._setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, timeout); 
			stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, timeout); 
			stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, timeout);
			
			stub.setTimeout(timeout);
			
			//SET SOAP - HEADER AUTHORIZATION 
			String user=clientConfiguration.getUrlLoginUser();
			String password=clientConfiguration.getUrlLoginPswd();
			
			logger.debug("@paleo auth " + user + " / " + password);
			
			
		    SOAPHeaderElement header = getSoapHeaderAuthorization(user,password);
			((Stub) port).setHeader(header);		
			//==================================================================================
			paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta=getParametriRichiestaPaleoGiunta(registroDocumento, integDTO, listaCorrispondenti, oggetto, allegati, cfResp); 
			//==================================================================================
			paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza res = null;
			try {
				res = port.protocollazionePartenza(richiesta);

				if(!"error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue()))			
					addAllegatiDocumentoProtocollo(registroDocumento.getId(), res.getDocNumber(), res.getSegnatura(), false, allegati);
				
			} catch (Exception e) {
				throw e;
			}
			finally {
				if(isDebugEnabled) {
					logger.debug("@paleo protocollazionePartenza request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@paleo protocollazionePartenza response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
			return res;
	}
	
	
	public boolean doProtocolDispatchService(String segnatura, boolean isUSR)
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
        
		Integer timeout = 300000;
		Stub stub = (org.apache.axis.client.Stub) port;
		stub._setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, timeout);
		
		stub.setTimeout(timeout);
		
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);		
		//==================================================================================
		ReqSpedisciProtocollo reqSpedisciProtocollo = new ReqSpedisciProtocollo();
		reqSpedisciProtocollo.setSegnatura(segnatura);
		reqSpedisciProtocollo.setOperatore(getOperatorePaleo(isUSR));
		//==================================================================================

		RespSpedisciProtocollo res = null;
		try {
			res = res = port.spedisciProtocollo(reqSpedisciProtocollo);
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				logger.debug("@paleo spedisciProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo spedisciProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		
		return !"Error".equalsIgnoreCase(res.getMessaggioRisultato().getTipoRisultato().getValue());			
	}
	
	public void findOperatori(boolean isUSR)
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));
        
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR) ;
		((Stub) port).setHeader(header);		

		Object res = null;
		try {
			res = port.findOperatori("AAAA", "BBB");
			logger.debug("@paleo findOperatori: " + res.toString());	
		} catch (Exception e) {
			throw e;
		}
		finally {
			Stub stub = (org.apache.axis.client.Stub) port;
			logger.debug("@paleo findOperatori request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
			logger.debug("@paleo findOperatori response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
		}
	}
	
	// TO BE USED IN CASE OF SEPARATED CALL TO effettuaTrasmissioni
	public BEBase doTrasmissione(int docNum, boolean isUSR) 
			throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);
		
		//==================================================================================
		OperatorePaleo opp = getOperatorePaleo(isUSR);
		
		TrasmissioneRuolo[] trasmRuolo = new TrasmissioneRuolo[] {};
		
		TrasmissioneUtente[] trasmUtente = new TrasmissioneUtente[1];
		TrasmissioneUtente utente = new TrasmissioneUtente();
		utente.setOperatoreDestinatario(opp);
		trasmUtente[1] = utente;
		
		TrasmissioneDoc[] trasmissioni = new TrasmissioneDoc[1];
		trasmissioni[0].setDocNumber(docNum);
		trasmissioni[0].setInvioOriginaleCartaceo(false);
		trasmissioni[0].setNoteGenerali(null);
		trasmissioni[0].setSegueCartaceo(false);
		trasmissioni[0].setTrasmissioniRuolo(trasmRuolo);
		trasmissioni[0].setTrasmissioniUtente(trasmUtente);
		//==================================================================================
		
		BEBase res = null;
		try {
			res = port.effettuaTrasmissioni(opp, trasmissioni);
		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo effettuaTrasmissioni request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo effettuaTrasmissioni response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}

		return res;
	}

	private Trasmissione getTrasmissione(OperatorePaleo operatore, boolean isUSR, String cfResp) {
		String ragione = isUSR?clientConfiguration.getTrasmRagioneUSR():clientConfiguration.getTrasmRagione();
		if(ragione == null && "NOINVIO".equalsIgnoreCase(ragione))
			return null;
		
		Optional<Utente> user = utenteRepository.findByCodiceFiscaleIgnoreCase(cfResp);
		
		String nomeUtenteResponsabileCds = null;
		String cognomeUtenteResponsabileCds = null;
		
		if(user!=null) {
		  nomeUtenteResponsabileCds = user.get().getNome();
		  cognomeUtenteResponsabileCds = user.get().getCognome();
		  logger.info("PaleoSoapClientAdapter - getTrasmissione - nome responsabile: " + nomeUtenteResponsabileCds);
		  logger.info("PaleoSoapClientAdapter - getTrasmissione - cognome responsabile: " + cognomeUtenteResponsabileCds);
		}
		
		String ruoloDestinatario = isUSR?clientConfiguration.getTrasmRuoloDestinatarioUSR():clientConfiguration.getTrasmRuoloDestinatario();
		String note = isUSR?clientConfiguration.getTrasmNoteGeneraliUSR():clientConfiguration.getTrasmNoteGenerali();
		
		Trasmissione trasmissione=null;
		trasmissione=new Trasmissione();
		trasmissione.setNoteGenerali(note);
		trasmissione.setSegueCartaceo(false);
		TrasmissioneRuolo[] trasmissioniRuolo=new TrasmissioneRuolo[1];
		TrasmissioneRuolo truolo=new TrasmissioneRuolo();
		truolo.setCodiceUODestinataria(isUSR?clientConfiguration.getOperatoreUSRCodiceUO():clientConfiguration.getOperatoreCodiceUO());
		truolo.setRuoloDestinatario(ruoloDestinatario);
		truolo.setRagione(ragione);
		
		truolo.setDataScadenza(Calendar.getInstance());
		trasmissioniRuolo[0]=truolo;
		trasmissione.setTrasmissioniRuolo(trasmissioniRuolo);
		
		TrasmissioneUtente[] trasmUtente = null;
		String trasmUtenti = isUSR?clientConfiguration.getTrasmUtentiUSR():clientConfiguration.getTrasmUtenti();

		if("TRASMISSIONE_DISABILITATA".equalsIgnoreCase(trasmUtenti))
			return null;

		if(trasmUtenti != null && !"DEFAULT".equalsIgnoreCase(trasmUtenti)) {
			String[] list = trasmUtenti.split("[|]");
			trasmUtente = new TrasmissioneUtente[list.length];
			
			for(int i=0; i<list.length; i++) {
				String[] opparray = list[i].split(";");
				operatore = new OperatorePaleo();
				
				if(isUSR) {
					logger.info("PaleoSoapClientAdapter - getTrasmissione - USR, inizio set campi ");
					if(opparray[0].length()>0) operatore.setCodiceUO(opparray[0]);
					if(opparray[1].length()>0) operatore.setCognome(opparray[1]);
					if(opparray[2].length()>0) operatore.setNome(opparray[2]);
					if(opparray[3].length()>0) operatore.setRuolo(opparray[3]);
					if(opparray[4].length()>0) operatore.setCodiceRuolo(opparray[4]);
					logger.info("PaleoSoapClientAdapter - getTrasmissione - USR, fine set campi ");
				}
				else {
						logger.info("PaleoSoapClientAdapter - getTrasmissione - Ambiente, inizio set campi ");
						if(opparray[0].length()>0) operatore.setCodiceUO(opparray[0]);
						if(opparray[1].length()>0) 
							operatore.setCognome(opparray[1]);
						else
							operatore.setCognome(cognomeUtenteResponsabileCds);
						if(opparray[2].length()>0) 
							operatore.setNome(opparray[2]);
						else
							operatore.setNome(nomeUtenteResponsabileCds);
						if(opparray[3].length()>0) operatore.setRuolo(opparray[3]);
						if(opparray[4].length()>0) operatore.setCodiceRuolo(opparray[4]);
						logger.info("PaleoSoapClientAdapter - getTrasmissione - Ambiente, fine set campi ");
				}
				
				
				TrasmissioneUtente utente = new TrasmissioneUtente();
				utente.setOperatoreDestinatario(operatore);
				if(opparray[5].length()>0) utente.setRagione(opparray[5]);
				
				trasmUtente[i] = utente;
			}
		}
		else {
			TrasmissioneUtente utente = new TrasmissioneUtente();
			utente.setOperatoreDestinatario(operatore);
			utente.setRagione(ragione);
			trasmUtente = new TrasmissioneUtente[] { utente };
		}		
		
		trasmissione.setTrasmissioniUtente(trasmUtente);
		
		return trasmissione; 
	}	
	
	private paleoGiunta.it.marche.regione.paleo.services.Trasmissione getTrasmissione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, String cfResp) {
		String ragione = clientConfiguration.getTrasmRagione();
		if(ragione == null && "NOINVIO".equalsIgnoreCase(ragione))
			return null;
		
		Optional<Utente> user = utenteRepository.findByCodiceFiscaleIgnoreCase(cfResp);
		
		String nomeUtenteResponsabileCds = null;
		String cognomeUtenteResponsabileCds = null;
		
		if(user!=null) {
		  nomeUtenteResponsabileCds = user.get().getNome();
		  cognomeUtenteResponsabileCds = user.get().getCognome();
		  logger.info("PaleoSoapClientAdapter - getTrasmissione - nome responsabile: " + nomeUtenteResponsabileCds);
		  logger.info("PaleoSoapClientAdapter - getTrasmissione - cognome responsabile: " + cognomeUtenteResponsabileCds);
		}
		
		String ruoloDestinatario = clientConfiguration.getTrasmRuoloDestinatario();
		String note = clientConfiguration.getTrasmNoteGenerali();
		
		paleoGiunta.it.marche.regione.paleo.services.Trasmissione trasmissione=null;
		trasmissione=new paleoGiunta.it.marche.regione.paleo.services.Trasmissione();
		trasmissione.setNoteGenerali(note);
		trasmissione.setSegueCartaceo(false);
		paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] trasmissioniRuolo=new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[1];
		paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo truolo=new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo();
		truolo.setCodiceUODestinataria(clientConfiguration.getOperatoreCodiceUO());
		truolo.setRuoloDestinatario(ruoloDestinatario);
		truolo.setRagione(ragione);
		
		truolo.setDataScadenza(Calendar.getInstance());
		trasmissioniRuolo[0]=truolo;
		trasmissione.setTrasmissioniRuolo(trasmissioniRuolo);
		
		paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] trasmUtente = null;
		String trasmUtenti = clientConfiguration.getTrasmUtenti();

		if("TRASMISSIONE_DISABILITATA".equalsIgnoreCase(trasmUtenti))
			return null;

		if(trasmUtenti != null && !"DEFAULT".equalsIgnoreCase(trasmUtenti)) {
			String[] list = trasmUtenti.split("[|]");
			trasmUtente = new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[list.length];
			
			for(int i=0; i<list.length; i++) {
				String[] opparray = list[i].split(";");
				operatore = new paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo();
				
				
				logger.info("PaleoSoapClientAdapter - getTrasmissione - Ambiente, inizio set campi ");
				if(opparray[0].length()>0) operatore.setCodiceUO(opparray[0]);
				if(opparray[1].length()>0) 
					operatore.setCognome(opparray[1]);
				else
					operatore.setCognome(cognomeUtenteResponsabileCds);
				if(opparray[2].length()>0) 
					operatore.setNome(opparray[2]);
				else
					operatore.setNome(nomeUtenteResponsabileCds);
				if(opparray[3].length()>0) operatore.setRuolo(opparray[3]);
				if(opparray[4].length()>0) operatore.setCodiceRuolo(opparray[4]);
				logger.info("PaleoSoapClientAdapter - getTrasmissione - Ambiente, fine set campi ");
				
				paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente utente = new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente();
				utente.setOperatoreDestinatario(operatore);
				if(opparray[5].length()>0) utente.setRagione(opparray[5]);
				
				trasmUtente[i] = utente;
			}
		}
		else {
			paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente utente = new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente();
			utente.setOperatoreDestinatario(operatore);
			utente.setRagione(ragione);
			trasmUtente = new paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] { utente };
		}		
		
		trasmissione.setTrasmissioniUtente(trasmUtente);
		
		return trasmissione; 
	}
	
	

	private Allegato[] getAllegati(java.io.File lMagerAsFile, String[] fileAllegati, boolean isJustDeclare) {
		if(fileAllegati == null) return null;
		
		Allegato[] allegati = new Allegato[fileAllegati.length];
		for(int j=0; j < fileAllegati.length; j++) {
			String fileAllegato = fileAllegati[j];
			
			try {
				allegati[j] = new Allegato();
				
				File documentoAllegato = new File();
				
				documentoAllegato.setNome(fileAllegato);
				byte[] resourceByteArray = IOUtils.toByteArray(new FileInputStream(lMagerAsFile.getParent() + "/" + fileAllegato));

				if(!isJustDeclare) {
					documentoAllegato.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
					allegati[j].setDescrizione(FilenameUtils.getBaseName(fileAllegato));
					documentoAllegato.setStream(resourceByteArray);
				}
				
				documentoAllegato.setImpronta(getMd5(resourceByteArray));
				
				allegati[j].setDocumento(documentoAllegato);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return allegati;
	}
	
	private paleoGiunta.it.marche.regione.paleo.services.Allegato[] getAllegatiPaleoGiunta(java.io.File lMagerAsFile, String[] fileAllegati, boolean isJustDeclare) {
		if(fileAllegati == null) return null;
		
		paleoGiunta.it.marche.regione.paleo.services.Allegato[] allegati = new paleoGiunta.it.marche.regione.paleo.services.Allegato[fileAllegati.length];
		for(int j=0; j < fileAllegati.length; j++) {
			String fileAllegato = fileAllegati[j];
			
			try {
				allegati[j] = new paleoGiunta.it.marche.regione.paleo.services.Allegato();
				
				paleoGiunta.it.marche.regione.paleo.services.File documentoAllegato = new paleoGiunta.it.marche.regione.paleo.services.File();
				
				documentoAllegato.setNome(fileAllegato);
				byte[] resourceByteArray = IOUtils.toByteArray(new FileInputStream(lMagerAsFile.getParent() + "/" + fileAllegato));

				if(!isJustDeclare) {
					documentoAllegato.setEstensione(FilenameUtils.getExtension(lMagerAsFile.getPath()));
					allegati[j].setDescrizione(FilenameUtils.getBaseName(fileAllegato));
					documentoAllegato.setStream(resourceByteArray);
				}
				
				documentoAllegato.setImpronta(getMd5(resourceByteArray));
				
				allegati[j].setDocumento(documentoAllegato);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return allegati;
	}

	public void addAllegatiDocumentoProtocollo(int idRegistro, Integer docNumber, String segnaturaProtocollo, boolean isUSR, String[] fileAllegati) 
			throws ServiceException, IOException, SOAPException {
		
		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		Allegato[] allegatiList = getAllegati(lMagerAsFile, fileAllegati, false);
		if(allegatiList == null) 
			return;
		
		for(Allegato allegato : allegatiList) {
			Allegato[] allegatiListOfOne = new Allegato[1];
			allegatiListOfOne[0] = allegato;
		
			//==================================================================================
			PaleoServiceLocator locator = new PaleoServiceLocator();
	        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
			//SET SOAP - HEADER AUTHORIZATION 
		    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
			((Stub) port).setHeader(header);
			//==================================================================================
			OperatorePaleo operatore=getOperatorePaleo(isUSR);
			
			ReqAddAllegati allegati = new ReqAddAllegati();
			
			allegati.setOperatore(operatore);
			allegati.setDocNumber(docNumber);
			allegati.setSegnaturaProtocollo(segnaturaProtocollo);
			allegati.setAllegati(allegatiListOfOne);
	
			RespAddAllegati res = null;
			try {
				res = port.addAllegatiDocumentoProtocollo(allegati);
				
				logger.debug("@paleo addAllegatiDocumentoProtocollo result: " + res.getMessaggioRisultato().getTipoRisultato().getValue() + " - " + res.getMessaggioRisultato().getDescrizione());
			} catch (Exception e) {
				logger.debug("@paleo addAllegatiDocumentoProtocollo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@paleo addAllegatiDocumentoProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@paleo addAllegatiDocumentoProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
		}
	}
	
	public void addAllegatiDocumentoProtocolloGiunta(int idRegistro, Integer docNumber, String segnaturaProtocollo, String[] fileAllegati) 
			throws ServiceException, IOException, SOAPException {
		
		Resource resource=documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
		java.io.File lMagerAsFile=resource.getFile();
		paleoGiunta.it.marche.regione.paleo.services.Allegato[] allegatiList = getAllegatiPaleoGiunta(lMagerAsFile, fileAllegati, false);
		if(allegatiList == null) 
			return;
		
		for(paleoGiunta.it.marche.regione.paleo.services.Allegato allegato : allegatiList) {
			paleoGiunta.it.marche.regione.paleo.services.Allegato[] allegatiListOfOne = new paleoGiunta.it.marche.regione.paleo.services.Allegato[1];
			allegatiListOfOne[0] = allegato;
		
			//==================================================================================
			paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator locator = new paleoGiunta.it.marche.regione.paleo.services.PaleoServiceLocator();
			paleoGiunta.it.marche.regione.paleo.services.IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(false));							
			//SET SOAP - HEADER AUTHORIZATION 
		    SOAPHeaderElement header = getSoapHeaderAuthorization(false);
			((Stub) port).setHeader(header);
			//==================================================================================
			paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore=getOperatorePaleoPaleoGiunta();
			
			paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati allegati = new paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati();
			
			allegati.setOperatore(operatore);
			allegati.setDocNumber(docNumber);
			allegati.setSegnaturaProtocollo(segnaturaProtocollo);
			allegati.setAllegati(allegatiListOfOne);
	
			paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati res = null;
			try {
				res = port.addAllegatiDocumentoProtocollo(allegati);
				
				logger.debug("@paleo addAllegatiDocumentoProtocollo result: " + res.getMessaggioRisultato().getTipoRisultato().getValue() + " - " + res.getMessaggioRisultato().getDescrizione());
			} catch (Exception e) {
				logger.debug("@paleo addAllegatiDocumentoProtocollo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@paleo addAllegatiDocumentoProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@paleo addAllegatiDocumentoProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
		}
	}

	public BEListOfRubricaZA0HwLp5 findRubricaPaleo (ReqFindRubrica richiesta, boolean isUSR) throws ServiceException, IOException, SOAPException {
		//==================================================================================
		PaleoServiceLocator locator = new PaleoServiceLocator();
        IPaleoService port=locator.getBasicHttpBinding_IPaleoService(getPortAddress(isUSR));							
		//SET SOAP - HEADER AUTHORIZATION 
	    SOAPHeaderElement header = getSoapHeaderAuthorization(isUSR);
		((Stub) port).setHeader(header);		
		//==================================================================================
		
		OperatorePaleo operatore=getOperatorePaleo(isUSR);
		
		BEListOfRubricaZA0HwLp5 res = null;
		try {
			
			res = port.findRubricaExt(operatore, richiesta);

		} catch (Exception e) {
			throw e;
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@paleo ricerca rubrica request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@paleo ricercarubrica response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		return res;
	}	
}
