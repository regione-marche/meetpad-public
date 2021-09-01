package conferenza.adapder.integrazioni.firma.calamaio.adapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.xml.rpc.ServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.client.Stub;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.IFirmaAdapter;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.service.RegistroDocumentoService;
import it.marche.regione.calamaio.services.MultiSignResult;
import it.marche.regione.calamaio.services.SignResult;
import it.marche.regione.calamaio.services.SignServicesLocator;
import it.marche.regione.calamaio.services.SignServicesPortType;


@Component("conferenza.adapder.integrazioni.firma.calamaio.adapter.CalamaioClientAdapter")
public class CalamaioClientAdapter implements IFirmaAdapter {

	private static final Logger logger = LogManager.getLogger(CalamaioClientAdapter.class.getName());

	private static String CALAMAIO_XPATH_RESULT_DOCUMENT="//documents/document/documentData";
	
	@Value("${calamaio.remoteService}") 
	private String remoteService;
	
	@Value("${calamaio.configurationId}") 
	String configurationId;
	
	@Value("${calamaio.defaultDomain}") 
	String defaultDomain;
	
	@Value("${calamaio.soapCallTimeout:60000}") 
	String soapCallTimeout;
	
	@Value("${calamaio.debugCalls:N}") 
	String debugCalls;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	RegistroFirmaAdapterRepository registroFirmaDocumentoRepository;
	
	@Autowired
	DocumentoRepository docRepository;
	
	@Override
	public FirmaDTO firma(FirmaDTO par1) {
		String calamaiorequest=null;;
		try {
			calamaiorequest = fillCalamaioXMLRequest(par1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(calamaiorequest!=null && !calamaiorequest.isEmpty())
			par1.setCallbackbody(calamaiorequest);
		return par1;
	}

	@Override
	public Resource getFile(FirmaDTO par1) {
		Resource resource =null;
		String inputStreamFile=null;

		try {
			resource = new ByteArrayResource(getDocumentByteByXpath(par1.getCallbackbody()));
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		return resource;
	}


	/**
		<appletRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="xmlSignRequest.xsd">
		  <externalSessionId></externalSessionId>
		  <userAuthentication>
		    <userCode />
		    <applicationCode>openAct</applicationCode>
		  </userAuthentication>
		  <configurationIdentifier>pknet.pades</configurationIdentifier>
		  <documents>
		    <document>
		      <documentIdentifier>Documento_test_firma_digitale</documentIdentifier>
		      <documentData>file in base 64!!!</documentData>
		    </document>
		  </documents>
		</appletRequest>
	 * @param par1
	 * @return
	 * @throws IOException 
	 */
	public String fillCalamaioXMLRequest(FirmaDTO par1) throws IOException{
		
		String  configurationIdentifier = "pknet.cades";
		Integer version = 1;
		if (par1.getCalamaioRemota() == null || par1.getCalamaioRemota().equals("")) {
			if (par1.getPadesCades() != null && par1.getPadesCades()) {
				configurationIdentifier = "pknet.pades";
			}
		} else {
			Documento doc = docRepository.findById(par1.getIdDocumento()).orElse(null);
			if (par1.getIdDocumento() != null) {
				version = docRepository.getCountRegisterForDocument(par1.getIdDocumento());
			}
			
			if (doc != null && doc.getNome() != null) {
				if (doc.getNome().endsWith("pdf")) {
					configurationIdentifier = "pknet.pades";
				} 
			}
		}
		
		
		String calamaioXMLRequest="<appletRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"xmlSignRequest.xsd\">"+
		  "<externalSessionId></externalSessionId>"+
		  "<userAuthentication>"+
		    "<userCode />"+
		 //  "<applicationCode>openAct</applicationCode>"+
		 "<applicationCode>Paleo</applicationCode>"+
		  "</userAuthentication>"+
		  "<configurationIdentifier>" + configurationIdentifier + "</configurationIdentifier>"+
		  "<documents>"+
		    "<document>"+
		     // "<documentIdentifier>firma_doc_"+par1.getIdDocumento()+"_"+ version + "</documentIdentifier>"+
		     "<documentIdentifier>" + par1.getFileName() + "###-1</documentIdentifier>"+
		     "<configurationIdentifierCurDoc>" + configurationIdentifier +"</configurationIdentifierCurDoc>" +
		      "<documentData>"+getBase64File(par1)+"</documentData>"+
		    "</document>"+
		  "</documents>"+
		"</appletRequest>";		
		
		
		logger.debug("calamaioXMLRequest : "+calamaioXMLRequest);
		
		return calamaioXMLRequest;
	}

	/**
	 * 
	 * @param par1
	 * @return
	 * @throws IOException 
	 */
	private String getBase64File(FirmaDTO par1) throws IOException {
		return Base64.getEncoder().encodeToString(getBase64FileBuffer(par1));
	}
	
	private byte[] getBase64FileBuffer(FirmaDTO par1) throws IOException {
		String sOut=null;
		String sCurrentLine=null;
		StringBuilder contentBuilder = new StringBuilder();
		
		RegistroFirmaAdapter registroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(par1.getIdDocumento()); 
		
		Resource risorsa=getRegistroDocumento(registroFirmaAdapter.getFkRegistro()); 
		InputStream is = new FileInputStream(risorsa.getFile()); 
		BufferedReader br = new BufferedReader(new InputStreamReader(is));         
        while ((sCurrentLine = br.readLine()) != null){
            contentBuilder.append(sCurrentLine).append("\n");
        }
        sOut=contentBuilder.toString();
        br.close();
        
        return sOut.getBytes();        
	}	
	
	
	
	/**
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<appletResponse>
    <externalSessionId></externalSessionId>
    <signResult>Success</signResult>
    <behaviorWhenErrors>halt</behaviorWhenErrors>
    <documents>
        <document>
            <documentIdentifier>Documento_test_firma_digitale</documentIdentifier>
            <documentData> file firmato in base 64</documentData>
            <result>OK</result>
            <signType>PAdES</signType>
        </document>
    </documents>
    <errorList/>
</appletResponse>

CALAMAIO_XPATH_RESULT_DOCUMENT="//documents/document/documentData"

	 * @return
	 * @throws XPathExpressionException 
	 */
	private String getDocumentByXpath(String  xml) 
			throws XPathExpressionException{		
		String decodedString =null;
		byte[] decodedBytes = getDocumentByteByXpath(xml); 
        Charset charset = Charset.forName("UTF8");
		decodedString = new String(decodedBytes,charset);	   
		return decodedString;
	}
	
	private byte[] getDocumentByteByXpath(String  xml) 
			throws XPathExpressionException{
		
		byte[] decodedBytes =null;
		
		if(xml==null || xml.isEmpty() )
			throw new XPathExpressionException("Documento Vuoto");
		
		 //l'xml di input è passato in base 64!
	     byte[] decodedBytesXML = Base64.getDecoder().decode(xml);
	     xml = new String(decodedBytesXML);

		
		String decodedString =null;
		 InputSource inputXML = new InputSource( new StringReader( xml ) );         
	     XPath xPath = XPathFactory.newInstance().newXPath();	         
	     String result = xPath.evaluate(CALAMAIO_XPATH_RESULT_DOCUMENT, inputXML);
	     if(result!=null && !result.isEmpty()){
		     decodedBytes = Base64.getDecoder().decode(result);
	     }
		return decodedBytes;
	}
	
	
	private Resource getRegistroDocumento(Integer fkRegistro) 
			throws IOException {
		Optional<RegistroDocumento> optregistro = registroDocumentoRepository.findById(fkRegistro);
		if(optregistro==null)
			throw new IOException("Risorsa non trovata nel registro");
		RegistroDocumento registro=optregistro.get();
		Resource resource = null;
		resource = registroDocumentoService.loadFileAsResource(registro);
		return resource;
	}
	
	
	
	
	
	
	
	
	private SignServicesPortType getPort() throws ServiceException, MalformedURLException{
		URL url=new URL(remoteService) ;
		SignServicesLocator locator = new SignServicesLocator();
		//locator.getEngine().setOption("sendMultiRefs", false);
		SignServicesPortType port = locator.getSignServicesPort(url);

		Integer timeout = Integer.parseInt(soapCallTimeout);
		Stub stub = (org.apache.axis.client.Stub) port;
		stub._setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_CONNECTION_TIMEOUT_KEY, timeout); 
		stub._setProperty(org.apache.axis.components.net.DefaultCommonsHTTPClientProperties.CONNECTION_DEFAULT_SO_TIMEOUT_KEY, timeout);
		stub.setTimeout(timeout);
		return port;		
	}
	
	public byte[] checkHcmSignatureCalamaio(
	        String user,
	        String password,
	        String otp,
	        String domain,
	        byte[] document,
	        String tipoFirma) throws Exception {
		
		SignServicesPortType port = null;
		
		try {
			port = getPort();
			
			if(domain == null || "".equals(domain) || "false".equalsIgnoreCase(domain))
				domain = defaultDomain;
			
			//URL wsdlURL = new URL(remoteServiceWSDL);
	        //SignServices ss = new SignServices(wsdlURL);
			//logger.debug("@calamaio - checkHcmSignatureCalamaio remoteServiceWSDL: " + remoteService);
	        //SignServicesPortType port = ss.getSignServicesPort();
			
			logger.debug("@calamaio["+user+ "] - @calamaioCasdesPades[" + tipoFirma + "] - checkHcmSignatureCalamaio call ...");
			//SignResult hsmSignatureRes = port.hsmSignature(configurationId, user, password, otp, domain, document);
			SignResult hsmSignatureRes = port.hsmSignature(tipoFirma, user, password, otp, domain, document);
			logger.debug("@calamaio["+user+"] - checkHcmSignatureCalamaio result ["+user+"]: " + hsmSignatureRes.isSuccess());
			
			if(hsmSignatureRes.isSuccess())
				return hsmSignatureRes.getSignedDocument();
			else
				throw new Exception(hsmSignatureRes.getErrorDescription());
		}
		catch(Exception ex) {
			logger.debug("@calamaio["+user+"] - checkHcmSignatureCalamaio exception: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
			throw ex;
		}
		finally {
			if("Y".equalsIgnoreCase(debugCalls)) {
				logger.debug("@calamaio["+user+"] request: " + ((org.apache.axis.client.Stub) port)._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@calamaio["+user+"] response: " + ((org.apache.axis.client.Stub) port)._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
	}
	

	public byte[][] checkHcmMultipleSignatureCalamaio(
	        String user,
	        String password,
	        String otp,
	        String domain,
	        byte[][] documents,
	        Boolean cadesPades) throws Exception {
		
		SignServicesPortType port = null;
		
		try {
			port = getPort();
			
			if(domain == null || "".equals(domain) || "false".equalsIgnoreCase(domain))
				domain = defaultDomain;
			
			//URL wsdlURL = new URL(remoteServiceWSDL);
	        //SignServices ss = new SignServices(wsdlURL);
			//logger.debug("@calamaio - checkHcmMultipleSignatureCalamaio remoteServiceWSDL: " + remoteService);
	        //SignServicesPortType port = ss.getSignServicesPort();
			
			
			//MultiSignResult hsmMultipleSignatureRes = port.hsmMultiSignature(configurationId, user, password, otp, domain, documents);
			String configurationCadesPades = "aruba.remote.cades";
			if (cadesPades != null && cadesPades) {
				configurationCadesPades = "aruba.remote.pades";
			} 
			logger.debug("@calamaio["+user+"] - @calamaioCasdesPades[" + configurationCadesPades  + "] - checkHcmMultipleSignatureCalamaio call ..." );
			MultiSignResult hsmMultipleSignatureRes = port.hsmMultiSignature(configurationCadesPades, user, password, otp, domain, documents);
			logger.debug("@calamaio["+user+"] - checkHcmMultipleSignatureCalamaio result ["+user+"]: " + hsmMultipleSignatureRes.isSuccess());
			
			if(hsmMultipleSignatureRes.isSuccess()) {
				return hsmMultipleSignatureRes.getSignedDocuments();
			} else {
				throw new Exception(hsmMultipleSignatureRes.getErrorDescription());
			}
		}
		catch(Exception ex) {
			logger.debug("@calamaio["+user+"] - checkHcmMultipleSignatureCalamaio exception: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
			throw ex;
		}
		finally {
			if("Y".equalsIgnoreCase(debugCalls)) {
				logger.debug("@calamaio["+user+"] request: " + ((org.apache.axis.client.Stub) port)._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.debug("@calamaio["+user+"] response: " + ((org.apache.axis.client.Stub) port)._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			}
		}
	}

}
