package conferenza.adapder.integrazioni.domus.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1;
import org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocollo;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocolloWithoutSream;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaResp;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.DomusSismaMISServiceLocator;

import conferenza.adapder.integrazioni.domus.DomusConfigurator;
import conferenza.adapder.integrazioni.domus.repository.DomusConferencePropertiesRepository;
import it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService;


/***
 * 
 *  - 
 *  -
 *  - 
 * 
 * 
 * @author guideluc
 *
 */
@Service
public class DomusClientAdapterService {
	private static final Logger logger = LoggerFactory.getLogger(DomusClientAdapterService.class.getName());
	private java.lang.String BasicHttpBinding_IDomusService_address = "http://arturo3.regionemarche.intra/DomusSismaServices/DomusSismaMISService.svc";
	
	
	@Autowired
	DomusConfigurator domusConfigurator;
	
	@Autowired
	DomusConferencePropertiesRepository conferencePropertiesRepository;
	
	
	Boolean conProtocolli=true;
	String password=null;
	String stato="ConferenzaRegionale";	
	String userID=null;

	public static boolean isDebugEnabled = false;
	
	/**
	 * TODO: leggere la configuraizone da database!?!?!
	 */
	public void init(){
		this.password=domusConfigurator.getDomusConnectionPassword();
		this.stato=domusConfigurator.getDomusConnectionStatus();
		this.userID=domusConfigurator.getDomusConnectionUser();		
		BasicHttpBinding_IDomusService_address=domusConfigurator.getDomusConnectionUrl();
	}
	
	public Pratica[]  getPraticheTEST(){
		Pratica[] pratiche =new Pratica[1] ;
		Pratica pratica = new Pratica();
		pratica.setCodiceFascicolo("490.40.20/2017/USR/146");
		pratica.setDestinazioneUso("Residenziale");
		pratica.setIdRichiesta(new Integer(226));		
		ProtocolloPratica[] protocolli= new ProtocolloPratica[1];
		ProtocolloPratica protocollo=new ProtocolloPratica();
		protocollo.setDocNumber("38782");
		protocollo.setGenereValidita("Originale");
		protocollo.setSegnatura("0009597|12/10/2017|MARCHEUSR|RMA|P|490.40.20/2017/USR/146");
		protocolli[0]=protocollo;
		pratica.setProtocolli(protocolli );
		pratiche[0]=pratica;
		return pratiche;
	}
	
	
	
	private IDomusSismaMISService getPort() throws ServiceException, MalformedURLException{
		String isXmlDebugOn = conferencePropertiesRepository.getParameterValue("isXmlDebugOn", "DEV");
		DomusClientAdapterService.isDebugEnabled = "yes".equalsIgnoreCase(isXmlDebugOn);
		
		URL url=new URL(BasicHttpBinding_IDomusService_address) ;
		DomusSismaMISServiceLocator locator = new DomusSismaMISServiceLocator();
		locator.getEngine().setOption("sendMultiRefs", false);
		IDomusSismaMISService port = locator.getBasicHttpBinding_IDomusSismaMISService(url);

		Integer timeout = new Integer(domusConfigurator.getDomusConnectionTimeout());
		Stub stub = (org.apache.axis.client.Stub) port;
		stub._setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, timeout);
		
		stub.setTimeout(timeout);
		
//		stub._getCall().setTimeout(timeout);
		 
//		stub._getService().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout);
//  		stub._getService().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout);
		 
		return port;		
	}
	
	/**
	 * 
	 * @param comune
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public Pratica[]  getPraticheMIS(String comune)	throws ServiceException, RemoteException, MalformedURLException{
		//-----------------------------------------------------------------------------		
		this.init();
		//-----------------------------------------------------------------------------		
		//ottine la port in maniera centralizzata per i problemi di timeout..
		IDomusSismaMISService port = getPort();
		//-----------------------------------------------------------------------------
		GetPraticheMISReq request=new GetPraticheMISReq();
		request.setComune(comune);		
		request.setConProtocolli(this.conProtocolli);		
		request.setPassword(this.password);		
		request.setStato(this.stato);		
		request.setUserID(this.userID);	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		//String date1 = format1.format(new Date());
		//logger.debug("START : "+date1);
		long startt = System.currentTimeMillis();
		
		BEBaseOfArrayOfPraticaX2ZTrCF1 result=null;
		try{
			try {
				result=port.getPraticheMIS(request);
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@domus getPraticheMIS request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@domus getPraticheMIS response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
			
			if(result == null || result.getResult() == null)
				logger.debug("@domus getPraticheMIS CALL: "+(System.currentTimeMillis() - startt) + " comune: "+comune+" ["+BasicHttpBinding_IDomusService_address+"]: NULL!");
			else
				logger.debug("@domus getPraticheMIS CALL: "+(System.currentTimeMillis() - startt) + " comune: "+comune+" ["+BasicHttpBinding_IDomusService_address+"]: " + result.getResult().length + " records found!");
		}catch(Throwable ex){
			logger.debug("@domus getPraticheMIS exception CALL : "+(System.currentTimeMillis() - startt) + " comune: "+comune+" ["+BasicHttpBinding_IDomusService_address+"] - " + ex.getMessage() + " "  + Arrays.toString(ex.getStackTrace()));
			throw new ServiceException(ex.getMessage());			
		}

		//tira dentro lo stream!!! - Chiama il metodo DocProcolloPaleo...
		//port.getDocumentiProtocollo(request);				
		Pratica[] listaPratiche=null;		
		if(result!=null){
			listaPratiche=result.getResult();
//			if(listaPratiche!=null && listaPratiche.length > 0)	
//				for(Pratica item: listaPratiche){
//					//@TODO: chiarirsi perche a volte la Pratica è null!
//					if(item!=null){
//						dump(item);
//						
//						//TODO: occorre prelevare l'oggetto per settare i default!
//						GetXmlIstanzaResp xml=null;
//						try {
//							xml=getXmlIstanza(item.getIdRichiesta());
//							logger.debug("BEGIN XML: " + xml.getXmlIstanza());
//						} catch (IOException e) {
//							logger.debug("@domus exception getXmlIstanza : "+(System.currentTimeMillis() - startt) + " comune: "+comune+" ["+BasicHttpBinding_IDomusService_address+"] - " + e.getMessage() + " "  + Arrays.toString(e.getStackTrace()));
//						}
//					}else{
//						logger.debug("CALL getPraticheMIS Pratica Null ??!?!?: comune: "+comune+" ["+BasicHttpBinding_IDomusService_address+"]");
//					}				
//					//@TODO: decommentare per ottenere una sola proatica..
//					//break;
//			}else{
//				logger.debug("Non ci sono pratiche");				
//			}		
		}	

		return listaPratiche;
	}

	public PraticaExtended[] getProtocolliPratica(Integer idPratica)	throws ServiceException, RemoteException, MalformedURLException{
		PraticaExtended pratica = getProtocolloPraticaById(idPratica);
			
		if(pratica != null)
			return new PraticaExtended[] { pratica };
		
		return null;
	}
	
	public PraticaExtended getProtocolloPraticaById(Integer idPratica)	throws ServiceException, RemoteException, MalformedURLException{
		//-----------------------------------------------------------------------------		
		this.init();
		//-----------------------------------------------------------------------------		
		//ottine la port in maniera centralizzata per i problemi di timeout..
		IDomusSismaMISService port = getPort(); 
		//-----------------------------------------------------------------------------
		GetProtocolliPraticaAllReq request=new GetProtocolliPraticaAllReq();
		request.setIdRichiesta(idPratica);		
		request.setPassword(this.password);		
		request.setUserID(this.userID);	
		long startt = System.currentTimeMillis();
		
		BEBaseOfPraticaExtendedX2ZTrCF1 result=null;
		try{
			try {
				result=port.getProtocolliPraticaAll(request);
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@domus getProtocolloPraticaById request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@domus getProtocolloPraticaById response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
			
			if(result == null || result.getResult() == null)
				logger.debug("@domus getProtocolloPraticaById CALL: "+(System.currentTimeMillis() - startt) + " idpratica: "+idPratica+" ["+BasicHttpBinding_IDomusService_address+"]: NULL!");
			else
				logger.debug("@domus getProtocolloPraticaById CALL: "+(System.currentTimeMillis() - startt) + " idpratica: "+idPratica+" ["+BasicHttpBinding_IDomusService_address+"]: record found!");
		}catch(Throwable ex){
			logger.debug("@domus getProtocolloPraticaById exception CALL: "+(System.currentTimeMillis() - startt) + " idpratica: "+idPratica+" ["+BasicHttpBinding_IDomusService_address+"] - " + ex.getMessage() + " "  + Arrays.toString(ex.getStackTrace()));
			throw new ServiceException(ex.getMessage());			
		}

		if(result!=null)
			return result.getResult();
		
		return null;
	}
	
	private void dump(Pratica item ){
		logger.debug("getCodiceFascicolo  "+item.getCodiceFascicolo());
		logger.debug("getDestinazioneUso "+item.getDestinazioneUso());
		logger.debug("getStato "+item.getStato());
		logger.debug("getIdRichiesta "+item.getIdRichiesta());
		ProtocolloPratica[] protocolli = item.getProtocolli();
		for(ProtocolloPratica protocollo: protocolli){
			logger.debug("getDocNumber "+protocollo.getDocNumber());
			logger.debug("getGenereValidita "+protocollo.getGenereValidita());
			logger.debug("getSegnatura "+protocollo.getSegnatura());
			logger.debug("getTipoDocumento "+protocollo.getTipoDocumento());
		}
	}
	
	
	public GetXmlIstanzaResp   getXmlIstanza(Integer idRichiesta)	throws ServiceException, RemoteException, MalformedURLException {
		
		this.init();		
		IDomusSismaMISService port = getPort(); 
		
		//---------------------------------------------------------------
		//
		//---------------------------------------------------------------
		GetXmlIstanzaReq request =new GetXmlIstanzaReq();	
		request .setIdRichiesta(idRichiesta);;				
		//---------------------------------------------------------------
		//Parametri autorizzazione
		request.setUserID(this.userID);
		request.setPassword(this.password);		
		//---------------------------------------------------------------		
		//
		//---------------------------------------------------------------
		BEBaseOfGetXmlIstanzaRespX2ZTrCF1 result = null;		
		
		try {
			result=port.getXmlIstanza(request);
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@domus getXmlIstanza request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@domus getXmlIstanza response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		
		GetXmlIstanzaResp risposta=result.getResult();		
				
		return risposta;
	}	
	
	/**
	 * @throws ServiceException 
	 * @throws IOException 
	 * 
	 */
	public Pratica[]  test() 
			throws ServiceException, IOException{
		//---------------------------------------------------------		
		//TEST VALUE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//---------------------------------------------------------				
		String comune="043007";				
		//tira dentro lo stream!!! - Chiama il metodo DocProcolloPaleo...
		//port.getDocumentiProtocollo(request);
		
		return getPraticheMIS(comune);
		
	}
	
	public DocumentoProtocollo[]  getDocumentiProtocollo(Integer docNumber) throws ServiceException, RemoteException, MalformedURLException{						
		this.init();		
		//DomusSismaMISServiceLocator locator = new DomusSismaMISServiceLocator();
		//IDomusSismaMISService port = locator.getBasicHttpBinding_IDomusSismaMISService();
		IDomusSismaMISService port = getPort(); 
		//---------------------------------------------------------------------------
		GetDocumentiProtocolloReq request=new GetDocumentiProtocolloReq();
		//---------------------------------------------------------------------------
		logger.debug("getDocumentiProtocollo .  docNumber" +docNumber);
		
		
		request.setDocNumber(docNumber);
		//---------------------------------------------------------------------------
		//Parametri autorizzazione
		request.setUserID(this.userID);
		request.setPassword(this.password);		
		
		BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1 result=null;		
		if(docNumber!=null) {
			try {
				result=port.getDocumentiProtocollo(request);
			} catch (Exception e) {
				throw e;
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@domus getDocumentiProtocollo request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@domus getDocumentiProtocollo response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
		}			
		else
			return null;
		
		return result.getResult();				
	}
	
	public DocumentoProtocolloWithoutSream[]   getDocumentiProtocolloWithoutStream(Integer docNumber) throws ServiceException, RemoteException, MalformedURLException{						
		this.init();		
		//---------------------------------------------------------------------------
		IDomusSismaMISService port = getPort(); 
		//---------------------------------------------------------------------------
		GetDocumentiProtocolloReq request=new GetDocumentiProtocolloReq();
		//---------------------------------------------------------------------------
		logger.debug("getDocumentiProtocollo .  docNumber" +docNumber);
		
		
		request.setDocNumber(docNumber);
		//---------------------------------------------------------------------------
		//Parametri autorizzazione
		request.setUserID(this.userID);
		request.setPassword(this.password);		
		
		BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1 result=null;		
		if(docNumber!=null) {
			try {
				result=port.getDocumentiProtocolloWithoutStream(request);
			} catch (Exception e) {
				throw e;
			}
			finally {
				if(isDebugEnabled) {
					Stub stub = (org.apache.axis.client.Stub) port;
					logger.debug("@domus getDocumentiProtocolloWithoutStream request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
					logger.debug("@domus getDocumentiProtocolloWithoutStream response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
				}
			}
		}			
		else
			return null;
		
		return result.getResult();				
	}
	
	/**
	 * 
	 * @param idFile
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws ServiceException
	 */
	public FileWithoutStream[]  getFile(Integer idFile) 
			throws RemoteException, MalformedURLException, ServiceException{
		//inizilizzazione dei parametri
		this.init();	
		IDomusSismaMISService port = getPort(); 
		GetFileReq request=new GetFileReq();
		//---------------------------------------------------------------------------
		//Parametri autorizzazione
		request.setUserID(this.userID);
		request.setPassword(this.password);		
		//---------------------------------------------------------------------------
		request.setIdFile(idFile);
		//---------------------------------------------------------------------------
		BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1 result=null;
		
		try {
			result=port.getFile(request);
		}
		finally {
			if(isDebugEnabled) {
				Stub stub = (org.apache.axis.client.Stub) port;
				logger.debug("@domus getFile request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@domus getFile response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
		
		if(result==null)
			return null;
		return result.getResult();
		
	}
	
}
