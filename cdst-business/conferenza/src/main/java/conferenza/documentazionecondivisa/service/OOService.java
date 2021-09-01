package conferenza.documentazionecondivisa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.builder.DocumentoBuilder;
import conferenza.builder.EventoBuilder;
import conferenza.documentazionecondivisa.OOConfiguration;
import conferenza.documentazionecondivisa.DTO.OOAdapterDTO;
import conferenza.documentazionecondivisa.DTO.OOCreateUserResponse;
import conferenza.documentazionecondivisa.DTO.OODocumentDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentEditorConfigDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentEditorConfigUserDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentInfoDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentPermissionsDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentResponseDTO;
import conferenza.documentazionecondivisa.DTO.OOLoginDTO;
import conferenza.documentazionecondivisa.DTO.OOLoginResponseDTO;
import conferenza.documentazionecondivisa.DTO.OOUploadFileReesponseDTO;
import conferenza.documentazionecondivisa.DTO.OOUserDTO;
import conferenza.documentazionecondivisa.DTO.bean.OOUserListResponse;
import conferenza.documentazionecondivisa.DTO.bean.OOUserResponse;
import conferenza.documentazionecondivisa.adapter.OORestClientAdapter;
import conferenza.documentazionecondivisa.model.OOAdapter;
import conferenza.documentazionecondivisa.model.OOAdapterVersioni;
import conferenza.documentazionecondivisa.model.OOUser;
import conferenza.documentazionecondivisa.repository.OOAdapterRepository;
import conferenza.documentazionecondivisa.repository.OOAdapterVersioniRepository;
import conferenza.documentazionecondivisa.repository.OOUserRepository;
import conferenza.exception.MyFileNotFoundException;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Evento;
import conferenza.model.RegistroDocumento;
import conferenza.model.RegistroDocumentoFonte;
import conferenza.model.RegistroDocumentoTipo;
import conferenza.model.TokenRegistroDocumento;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.TokenRegistroDocumentoRepository;
import conferenza.service.DocumentoService;
import conferenza.service.JWTsService;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.JsonUtil;

@Service
public class OOService {

	private static final Logger logger = LoggerFactory.getLogger(OOService.class.getName());
	
	
	@Autowired
	JWTsService jwtService;
	
	@Autowired
	DocumentoBuilder documentoBuilder;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	DocumentoRepository documentoRepository;

	
	@Autowired
	OORestClientAdapter ooRestClient;
	
	@Autowired
	OOConfiguration configuraration;
	
	@Autowired
	OOAdapterRepository adapterRepository;

	@Autowired
	OOAdapterVersioniRepository adapterVersioniRepository;
	
	@Autowired
	OOUserRepository ooUserRepository;
		
	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository; 
	@Autowired
	RegistroDocumentoService registroDocumentoService; 
	
	@Autowired
	DocumentAdapterService documentAdapterService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	EventoRepository eventoRepo;

	@Autowired
	EventoBuilder eventoBuilder;	


	
	
	public OOLoginResponseDTO doAdminGetToken() 
	throws IOException{
		OOLoginDTO login =new OOLoginDTO();
		login.setUserName(configuraration.getAdminUser());
		login.setPassword(configuraration.getAdminPassword());
		String serverUrl=configuraration.getUrlLogin();
		return doAUserGetToken(login) ;
	}
	
			
	public OOLoginResponseDTO doAUserGetToken(OOLoginDTO login) 
			throws IOException{		
		String serverUrl=configuraration.getUrlLogin();
		OOLoginResponseDTO  response=ooRestClient.ooUserGetToken(serverUrl, login);		
		return response;
	}
	
	/**
 
	 * @param codiceFiscale
	 * @return
	 * @throws IOException
	 */
	public String getUserToken(String codiceFiscale) 
			throws IOException {
		String onlyOfficeAccessTocken=null;
		OOLoginResponseDTO  ooLoginResponse =null;
		OOLoginDTO login =new OOLoginDTO(); 
		login.setUserName(codiceFiscale+"@oo.com");
		login.setPassword(codiceFiscale);
		try {
			ooLoginResponse =	doAUserGetToken(login);			
		}catch (Exception e) {
			OOCreateUserResponse createresponse= ooCreateUser(codiceFiscale) ;
			if(createresponse!=null) {
				ooLoginResponse =	doAUserGetToken(login);
			}else {
				throw new IOException("Impossibile creare l'utente:" + codiceFiscale);
			}
		}
		onlyOfficeAccessTocken=ooLoginResponse.getResponse().getToken();
		return onlyOfficeAccessTocken;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getAdminAuthorizationToken() 
			throws IOException{
		OOLoginResponseDTO  ooLoginResponse =doAdminGetToken() ;
		String onlyOfficeAccessTockem=ooLoginResponse.getResponse().getToken();
		return onlyOfficeAccessTockem;
	}
	
	/**
	 * STEP 2 - della creazione utente
	 * @param cf
	 * @return
	 * @throws IOException
	 */
	private OOCreateUserResponse ooCreateUser(String cf) 
			throws IOException{
		String onlyOfficeAccessTockem=getAdminAuthorizationToken() ;
		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTockem);
		String createUserUrl=configuraration.getUrlCreateUser();
		OOUserDTO user=new OOUserDTO();
		user.setFirstname(cf);
		user.setFirstname(cf);
		user.setLastname(cf);
		user.setEmail(cf+"@oo.com");
		return ooRestClient.ooCreateUser(createUserUrl, user, onlyOfficeAccessTockem);
	}
	
	
	private OOUserDTO ooGetUserByUserList(String cf) 
			throws IOException{
		String onlyOfficeAccessTockem=getAdminAuthorizationToken() ;
		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTockem);
		OOUserListResponse response=ooRestClient.ooGetUserList(onlyOfficeAccessTockem);
		List<OOUserResponse> lista=response.getUserList();
		OOUserDTO user=null;
		if(lista!=null && !lista.isEmpty())
		for(OOUserResponse item : lista) {
			if(cf.equals(item.getUserName())) {
				user=new OOUserDTO();
				user.setFirstname(cf);
				user.setFirstname(cf);
				user.setLastname(cf);
				user.setId(item.getId());
				user.setEmail(cf+"@oo.com");
			}			
		}
		
		return user;
	}
	
    /**
     * 	
     * @param fileToSubmit
     * @return
     * @throws Exception
     */
	public OOUploadFileReesponseDTO uploadSingleFile(Resource fileToSubmit,String onlyOfficeAccessTocken) 
			throws Exception{
		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTocken);
		String uploadUrl=configuraration.getUrlUpladFile();	
		logger.debug("url: "+uploadUrl);
		return ooRestClient.uploadSingleFileASincronous(uploadUrl,fileToSubmit,onlyOfficeAccessTocken);		
	}
	
	/**
	 * 
	 * @param fileToSubmit
	 * @return
	 * @throws Exception
	 */
	public OOUploadFileReesponseDTO uploadSingleFileByAdmin(Resource fileToSubmit) 
			throws Exception{
		OOLoginResponseDTO  ooLoginResponse =doAdminGetToken() ;
		String onlyOfficeAccessTocken=ooLoginResponse.getResponse().getToken();
		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTocken);		
		return uploadSingleFile(fileToSubmit,onlyOfficeAccessTocken);		
	}	
	
	public OOUploadFileReesponseDTO uploadSingleFileByUser(Resource fileToSubmit,String codiceFiscale) 
			throws Exception{
		OOLoginDTO login =new OOLoginDTO(); 
		login.setUserName(codiceFiscale+"@oo.com");
		login.setPassword(codiceFiscale);
		OOLoginResponseDTO  ooLoginResponse =	doAUserGetToken(login);
		String onlyOfficeAccessTocken=ooLoginResponse.getResponse().getToken();
		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTocken);		
		return uploadSingleFile(fileToSubmit,onlyOfficeAccessTocken);		
	}	

 
	
	
	public OOUploadFileReesponseDTO uploadTest()			
			throws Exception{
		Resource fileToSubmit =ooRestClient.getDocumentFile();
		return uploadSingleFileByAdmin(fileToSubmit);		
	}
	
	public OOAdapter save(OOAdapterDTO adapterDTO){
		OOAdapter adapter=OOAdapterDTO.fillOOAdapter( adapterDTO);
		return adapterRepository.save(adapter);	
	}
	
	/**
	 * il metodo intecetta un file dalla conferenza e lo sottomette ad OnlyOffice
	 * Quindi viene salvata l'infromazione sulla tabella di frontiera oo_adapter.
	 * l'informazione servirà in seguito
	 * per editare il file su OnlyOffice , ovvero importarlo sulla conferenza
	 * @param idRegistry
	 * @throws Exception
	 */
	public OOAdapter publishOODocument(Integer idRegistry) 
			throws Exception{
		
		OOAdapterDTO adapterDTO = null;
		Integer fkDocumento=adapterRepository.getFkDocumentoByRegistroDocumento(idRegistry);
		adapterDTO = new OOAdapterDTO();					
		adapterDTO.setFk_registro(idRegistry);
		Resource docResource=getConferenceDocumentResource(idRegistry);
		OOUploadFileReesponseDTO outputUpload=uploadSingleFileByAdmin(docResource);
		adapterDTO.setId_doc_oo(String.valueOf(outputUpload.getResponse().getId()));
		adapterDTO.setId_folder_oo(String.valueOf(outputUpload.getResponse().getFolderId()));
		adapterDTO.setId_user_oo(outputUpload.getResponse().getCreatedBy().getId());
		adapterDTO.setFkDocumento(fkDocumento);
		//persistenza dell dato caricato su OnlyOffice
		OOAdapter adapter =null;
		adapter=OOAdapterDTO.fillOOAdapter(adapterDTO);
		return  adapterRepository.save(adapter);		
	}
	

	

	public String getEditOODocumentURL(Integer idREgistro){
		String url=configuraration.getUrlEditFile();
		OOAdapter adapter=null;
		String idDocOO =null;
		List<OOAdapter>  listaoo=adapterRepository.getRegisterdAsincronousTask(idREgistro);
		if(listaoo!=null && !listaoo.isEmpty()){
			adapter=listaoo.get(0);
			idDocOO=adapter.getId_doc_oo();
		}
		if(idDocOO!=null){
			return url.replaceAll("IDDOCOO", idDocOO);			
		}
		
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private String  getCFUtenteOOByRequest() 
			throws IOException{
		String cf =utenteService.getCodiceFiscaleUtente();
		return cf;		
	 }

	
	public String getUserIDOO(String codiceFiscale) throws IOException {
		OOUser oouser = ooUserRepository.getOOUserByCF(codiceFiscale);
		if(oouser==null)
			throw new IOException("oouser not Esistente - verificare creazione utente OO");
		String idoo=oouser.getId_user_oo();
		if(idoo ==null )
			throw new IOException("idoo not Esistente - verificare creazione utente OO");		
		return idoo;
	}
	
	
	public OOCreateUserResponse createOOUserByRequest(String codicefiscale) 
			throws IOException{
		String cf =getCFUtenteOOByRequest() ;
		if(!cf.equals(codicefiscale) )
			logger.debug("codicefiscale "+codicefiscale+ " not Match cf "+cf+"!");
		//	throw new IOException("CF not Match!");
		
		OOCreateUserResponse  ooUserResponse=null;
		String ooUserId=null;
		OOUser user=ooUserRepository.getOOUserByCF(codicefiscale); 
		if(user!=null) {
			ooUserId=user.getId_user_oo();	
		}			
		else if(ooUserId==null) {
			try {
				OOUserDTO userDTO=ooGetUserByUserList(codicefiscale);
				if(userDTO!=null) {
					user.setId_user_oo(userDTO.getFirstname());
					user.setId_user_oo(userDTO.getId());
					ooUserId=userDTO.getId();
				} else {
					ooUserResponse=ooCreateUser(codicefiscale) ;
					ooUserId=ooUserResponse.getResponse().getId();
				}
				
			}catch (Exception e) {
				logger.debug("Errore creazione utente: " + e.getMessage());
				
			}
		}
		
		logger.debug("ooUserId: " + ooUserId);
		//-----------------------------------------------------
		//
		//-----------------------------------------------------		
		String token =getAdminAuthorizationToken(); 
		return setOOUserPassword(ooUserId,codicefiscale,token);		
	}

	/**
	 * STEP 3 della Creazione di un utente:
	 * di default la password NON è creata..
	 * @param ooUserId
	 * @param pwd=cf
	 * @throws IOException 
	 */
	private OOCreateUserResponse setOOUserPassword(String ooUserId, String pwd,String token)
			throws IOException {
		OOLoginDTO user =new OOLoginDTO();
		user.setUserName(pwd+"@oo.com");
		user.setPassword(pwd);
		OOCreateUserResponse userResponse=ooRestClient.ooSetUserPassword(ooUserId, user,token);
		OOUser oouser= new OOUser();
		oouser.setId_user_oo(userResponse.getResponse().getId());
		//La password è uguale al codice fiscale
		oouser.setCf_user(pwd);
		ooUserRepository.save(oouser);	
		logger.debug("Login effettuata Only office");
		return userResponse;		
	}


	/**
	 * Stram dei File dal documentale da cui di vuol prelevare il file (in base alla persistenza su REgistro)
	 * @param idRegistry
	 * @return
	 */
	public Resource getConferenceDocumentResource(Integer idRegistro) {
		return documentAdapterService.loadFileAsResourceByIdRegistry(idRegistro);
	}
	
	/**
	 * TODO: Stream del File caricato e lavorato su OnlyOffice
	 * @param idRegistro
	 * @return
	 */
	public Resource getOODocumentResource(Integer idRegistro) {
		
		return null;
	}

	/**
	 * https://meetpad-test.regione.marche.it/mpad-rest/1.0/downloadFile/09QrOjNKoke7GDmS
	 * @param idDocument
	 * @return
	 */
	public String getOODocumentUrl(String tokenInput) {		
		String lsURL=null;
		
		//1)dato il token di ingresso intecetta l'ultima versione utile di token du FS 
		//String token=adapterVersioniRepository.getLastDownloadableTokenByCurrentToken(tokenInput);
		String token = tokenInput;
		lsURL=configuraration.getUrlDownloadfile()+token;
				
		//2) deve essere  riattivato il token scaduto!!!
		registroDocumentoService.refreshTokenValidate(token);
		
		//OOAdapterVersioni versione =  adapterVersioniRepository.getLastVersioneByToken(token);
		//if(versione!=null)
		//	lsURL=versione.getUrlOO();
		//else
		//lsURL=configuraration.getUrlDownloadfile()+token;
		
		
		return lsURL;
	}	

	public String getOOCallbackUrl() {		
		String lsURL=configuraration.getCallback();
		return lsURL;
	}	
	
	/**
	 * 
	 * @param jwttoken
	 * @return
	 */
	public String getOODOAPIUrl() {
		String dsapi=configuraration.getDockserverapi();
		return dsapi;
	}
	
	/**
	 * 
	 * @param jwttoken
	 * @return
	 */
	public String getCodiceFiscaleFromJWT(String jwttoken) {
		return jwtService.getCodiceFiscaleFromTokenString(jwttoken);
	}
	/**
	 * 
	 * @param token
	 * @throws Exception 
	 */
	public String  initializeEditOODocument(String token,String jwt) 
			throws Exception {
		Integer idRegistry=null;
		OOAdapter adapter=null;
		//1 - ottiene l'id del token_registro_documento
		TokenRegistroDocumento tokenObj= tokenRegistroDocumentoRepository.findByToken( token);
		
		//TODO: potrebbe essere chimato più volte lo stesso token..!!
		//ogni volta deve essere staccato un nuovo token per il Document Server
		String newtoken=null;
		newtoken=adapterVersioniRepository.getNotVersionedTokenByKnownToken(token);
		if(newtoken==null || "".equals(newtoken))
			newtoken=registroDocumentoService.getTokenRegistroDocumento(tokenObj.getRegistroDocumento()) ;
		
		idRegistry=tokenObj.getRegistroDocumento().getId();
		
		//verifica che non ci sia già un documento con quell'id..
		adapter= adapterRepository.getAdapterByIdDoc(tokenObj.getRegistroDocumento().getDocumento().getIdDocumento());
		if(adapter==null) {
			//5 - upload del file su community
			//6 - store su oo_adapter se non è mai stato caricato il doc!?!?			
			adapter=publishOODocument(idRegistry) ;			
		}
		
		//2 - ottiene il bearer dell'utente che si è loggato ed ha fatto richiesta del token!
		String codicefiscale=getCFUtenteOOByRequest(); 		
		//3 - creazione dell'utente oo
		//4 - popolameno oo_user 
		OOCreateUserResponse useroo=null;
		try {
			useroo=createOOUserByRequest(codicefiscale); 
		}catch (Exception e) {
			//TODO per il momento se ci sono problemi nella creazione dell'utente il sistema và avanti..
			logger.debug("Problemi nella creazine dell'utente sul Community Server! " + e.getMessage());
		}	

		//7 - Persistenza su Registro Documento
		RegistroDocumento registro=documentoBuilder.buildRegistroDocumento(
				String.valueOf(adapter.getId()), 
				tokenObj.getRegistroDocumento().getDocumento(), 
				DocumentAdapterService.getCodiceFonte(FonteFile.OnlyOffice), 
				DocumentAdapterService.getCodiceTipoDocumentazione(ModalitaSalvataggioFile.OnlyOffice));
				
		registro=registroDocumentoRepository.save(registro);
		
		//7 - persiste il token su oo_adapter_versioni (le attività a seguire saranno fatte da utente di sistema)
		String lsURL=configuraration.getUrlDownloadfile() +newtoken;
		OOAdapterVersioni versione= null;
		versione=adapterVersioniRepository.getLastVersioneByToken(newtoken);
		//TODO gestire il versionamento
		if(versione==null) {
			versione=new OOAdapterVersioni();
			versione.setIdUserOO(codicefiscale);
			versione.setTOKEN(newtoken);
			versione.setUrlOO(lsURL);
			versione.setFkAdapter(adapter.getId());
			versione.setJwt(jwt);
			adapterVersioniRepository.save(versione);
		}
		//TODO Look Document!!!!!!!!!!!!!!
		return newtoken;
	}
	
	
	private String getCDSTToken(String token) {
		if(token!=null && ! "".equals(token)) {
			if(token.lastIndexOf("-")>0) {
				String[] lvar=token.split("-");
				if(lvar.length >0)
					token=lvar[0];	
			}			
		}
		return token; 
		
	}
	
	
	private void saveEvento(Conferenza conferenza,Documento documento) {
		EventoFileDTO eventoFileDTO= new EventoFileDTO();
		String type="";
		String determinationType="";
		eventoFileDTO.setTipoEvento(type);
		eventoFileDTO.setNote("Edit documento");		
		eventoFileDTO.setTipoParere(determinationType);		
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, documento, null));		
	}
	

	public OOAdapterVersioni saveOODocumentVersione(String tokenOO, String idadapter,String CF,String jwt,File stream ) throws Exception {
		OOAdapterVersioni versione=null;
		OOAdapterVersioni versioneNew=null;
		String token=getCDSTToken(tokenOO);
		//=================================================================
		//Versionamento su File Sistem..
		//=================================================================
		TokenRegistroDocumento tokenObj= tokenRegistroDocumentoRepository.findByToken( token);
		
		//=================================================================
		Integer idDocumento=adapterRepository.getFkDocumentoByRegistroDocumento(tokenObj.getRegistroDocumento().getId());
		
		java.util.Optional<conferenza.model.Documento> documentooptional=documentoRepository.findById(idDocumento);
		Documento documento=documentooptional.get();
		//Questo è nNULL!?!?!
		//Documento documento=tokenObj.getRegistroDocumento().getDocumento();			
		DocumentoDTO docDTO=documentoBuilder.buildDocumentoDTO(documento);		
		FileInputStream input = new FileInputStream(stream);
		MultipartFile multipartFile = new MockMultipartFile("file",
				 stream.getName(), "text/plain", IOUtils.toByteArray(input));		
		RegistroDocumento registro=documentoService.creaRegistroDocumento(multipartFile, documento, ModalitaSalvataggioFile.Filesystem, FonteFile.OnlyOffice);
		String tokenRegistroDocumentoNew=registroDocumentoService.getTokenRegistroDocumento(registro);
		logger.debug("New Token : "+tokenRegistroDocumentoNew);
		//Vecchia versione		
		List<OOAdapterVersioni> versioni= adapterVersioniRepository.getListVersioniByToken(token);
		if(versioni!=null && !versioni.isEmpty())
			versione=versioni.get(0);	
		//Nuova versione
		versioneNew=new OOAdapterVersioni();
		String urlDownload=configuraration.getUrlDownloadfile()+tokenRegistroDocumentoNew;		
		versioneNew.setUrlOO(urlDownload);
		if(versione!=null) {
			versioneNew.setIdUserOO(versione.getIdUserOO());
			versioneNew.setFkAdapter(versione.getFkAdapter());
		}else {			
			versioneNew.setFkAdapter(new Integer(idadapter));
			versioneNew.setIdUserOO(CF);
		}
		versioneNew.setJwt(jwt);
		versioneNew.setTOKEN(token);				
		versioneNew=adapterVersioniRepository.save(versioneNew);		
		
		//=================================================================
		//TODO Creazione Evento Per Protocollazione
		//@ANDREA
		//=================================================================
		//saveEvento(Conferenza conferenza,Documento documento)		
		
		//=================================================================
		//TODO UN-Look Document!!!!!!!!!!!!!!
		//=================================================================
		
		//=================================================================
		//Gestione del versionamento Only Office
		//viene aggiornata la tabella oo_adapter_versioni
		//=================================================================	
		if(versioneNew!=null) {
			try {
				Resource resource =new FileSystemResource(stream);
				OOUploadFileReesponseDTO response = null;//uploadSingleFileByUser(resource,versione.getIdUserOO());
				//TODO: occorre implementare lo SHARE dei file
				//provvisoriamente si caricano sulla directory common per poi accederci con le credenziali dell'utente generico..
				//successivamente saranno file dell'i-mo utente..
				response =uploadSingleFileByAdmin(resource);
				Integer idOOFile=response.getResponse().getId();	
				versioneNew.setIdOOFile(idOOFile);
				versioneNew=adapterVersioniRepository.save(versioneNew);
			}catch (Exception e) {
				logger.debug("Errore: "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		/*OLD!!!!!	
		Resource resource =new FileSystemResource(stream);			    
		if(versione!=null) {			
			OOUploadFileReesponseDTO response =uploadSingleFileByUser(resource,versione.getIdUserOO());
			versioneNew= new OOAdapterVersioni();
			Integer fileId=response.getResponse().getId();		
			String urlDownload=configuraration.getUrlDownloadfile()+fileId;		
			versioneNew.setUrlOO(urlDownload);
			versioneNew.setIdUserOO(versione.getIdUserOO());
			versioneNew.setTOKEN(tokenOO);
			versioneNew.setFkAdapter(versione.getFkAdapter());		
			versioneNew=adapterVersioniRepository.save(versioneNew);
		}
		*/
		return versioneNew;
	} 
	
	public String getNomeDocumentByToken(String token) {		
		return adapterVersioniRepository.getNomeDocumentByToken(token);
	}
	
	
	public void callbackToSaveOODocument() {
		
		
	}

	public  ResponseEntity<byte[]>  ooGetFile(String serverUrl,String onlyOfficeAccessTockem) 
			throws IOException {
		
		return ooRestClient.ooGetFile(serverUrl,onlyOfficeAccessTockem);
	}

	

   private String getNomeDocumentoByToken(String token) {    	
    	return adapterRepository.getNomeDocumentoByToken(token);
   }
	
   private Integer getIdDocumentoByToken(String token) {    	
   	return adapterRepository.getIdDocumentoByToken(token);
   }
   
   public Integer getAdapterByIdDoc(String token) {
	   Integer idDocument=getIdDocumentoByToken(token); 
	   OOAdapter adapter = adapterRepository.getAdapterByIdDoc(idDocument);	
	   if(adapter!=null)
		   return adapter.getId();
	   else
		   return  null;
   }
   
	
	public OODocumentDTO getOOJsonDocument(String tokenInput, String jwt) {

		logger.debug("OOService  - riga 710 - jwt " + jwt);
		logger.debug("OOService - riga 711 - tokenInput: " + tokenInput);
		
		String encodedjwt = tokenInput;
		logger.debug("OOService - riga 714 - encodedjwt1: " + encodedjwt);
		
		
		if(jwt!=null) {
			encodedjwt =Base64.getEncoder().encodeToString(jwt.getBytes());
			logger.debug("OOService - riga 719 - encodedjwt2: " + encodedjwt);
		}
			
		
		// NOME DOCUMENTO
		String nomedocumento = getNomeDocumentoByToken(tokenInput);
		logger.debug("OOService - riga 725 - nomedocumento: " + nomedocumento);
		
		nomedocumento = (nomedocumento == null) ? "nonfornito.docx" : nomedocumento;

		Integer idAdapter = getAdapterByIdDoc(tokenInput);
		logger.debug("OOService - riga 730 - idAdapter: " + idAdapter);

		// usare l'ultimo token generato per il dato documento..
		String token = tokenInput ;//adapterVersioniRepository.getLastGeneratedTokenByKnownToken(tokenInput);
		logger.debug("OOService - riga 734 - token: " + token);
		String codiceFiscale = getCodiceFiscaleFromJWT(jwt);
		logger.debug("OOService - riga 736 - codiceFiscale: " + codiceFiscale);
		// TODO: fare il metofo che generi l'url del file a partire dall'id doc {idcoc}
		String lsUrl = getOODocumentUrl(token);
		logger.debug("OOService - riga 739 - lsUrl: " + lsUrl);
		String useridoo = null;
		try {
			useridoo = getUserIDOO(codiceFiscale);
			if (useridoo == null)
				useridoo = codiceFiscale;
			
			logger.debug("OOService - riga 746 - useridoo: " + useridoo);
		} catch (IOException e) {
			// TODO implementare il messaggio di errore se ci sono problemi in creazione
			// utente..
			e.printStackTrace();
			logger.debug("OOService - riga 751 - errorGetUser: " + e.getMessage());
		}

		OODocumentDTO retjsonVal = new OODocumentDTO();

		OODocumentInfoDTO document = new OODocumentInfoDTO();
		document.setKey(token);
		document.setFileType("docx");
		document.setTitle(nomedocumento);
		document.setUrl(lsUrl);
		retjsonVal.setDocument(document);

		retjsonVal.setDocumentType("text");

		OODocumentEditorConfigDTO editorConfig = new OODocumentEditorConfigDTO();
		logger.debug("OOService - riga 766 - qui ci arrivo");
		editorConfig.setCallbackUrl(getOOCallbackUrl() + "/" + idAdapter + "/" + codiceFiscale + "/" + encodedjwt);
		logger.debug("OOService - riga 768 - CallbackUrl: " + editorConfig.getCallbackUrl());
		OODocumentEditorConfigUserDTO user = new OODocumentEditorConfigUserDTO();
		user.setId(useridoo);
		user.setName(codiceFiscale + "@oo.com");
		editorConfig.setUser(user);
		retjsonVal.setEditorConfig(editorConfig);
		logger.debug("OOService - riga 774 - qui ci arrivo");
		OODocumentPermissionsDTO permissions = new OODocumentPermissionsDTO();
		permissions.setDownload("true");
		permissions.setEdit("true");
		permissions.setReview("edit");
		retjsonVal.setPermissions(permissions);
		if(retjsonVal != null) {
			if(retjsonVal.getDocument() != null)
				logger.debug("OOService - riga 782 - titolo" + retjsonVal.getDocument().getTitle());
		}
		return retjsonVal;
	}

	public static void main(String[] args) {
		OODocumentDTO retjsonVal = new OODocumentDTO();

		OODocumentInfoDTO document = new OODocumentInfoDTO();
		document.setKey("token");
		document.setFileType("docx");
		document.setTitle("nomedocumento");
		document.setUrl("lsUrl");
		retjsonVal.setDocument(document);

		retjsonVal.setDocumentType("text");

		OODocumentEditorConfigDTO editorConfig = new OODocumentEditorConfigDTO();
		editorConfig.setCallbackUrl("callbackUrl");
		OODocumentEditorConfigUserDTO user = new OODocumentEditorConfigUserDTO();
		user.setId("useridoo");
		user.setName("codiceFiscale@oo.com");
		editorConfig.setUser(user);
		retjsonVal.setEditorConfig(editorConfig);

		OODocumentPermissionsDTO permissions = new OODocumentPermissionsDTO();
		permissions.setDownload("true");
		permissions.setEdit("true");
		permissions.setReview("edit");
		retjsonVal.setPermissions(permissions);
		
		String json = JsonUtil.jsonToString(retjsonVal);
		
		System.out.println(json);
	}
	
   /**
    * 
    * @param request
    * @return
    */
   public String getJWT(HttpServletRequest request) {	   
	   return jwtService.getJWT(request);
   }
	
	public Resource loadFileAsResourceFromFileSystem(File file) {
		try {			
			Resource resource = new UrlResource(file.getAbsolutePath());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + file.getAbsolutePath());
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + file.getAbsolutePath(), ex);
		}
	}   
 
	/**
	 * 
	 * @param idOOFile
	 * @return
	 */
	public String getOOCommunityDownloadURL(Integer idOOFile) {
		String url=configuraration.getUrlOOCommunityDownloadfile()+idOOFile;
		return url;
	}
	
	/**
	 * 
	 * @param idOOFile
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<T> getOOCommunityFile(Integer idOOFile,String token) 
			throws IOException {        
        String address=getOOCommunityDownloadURL(idOOFile);
        logger.debug("url:"+address);
        //====================================================================================	        
        ResponseEntity<byte[]> restresponse=ooGetFile(address,token); 
        //====================================================================================
        HttpHeaders responseheaders=restresponse.getHeaders();
        List<String> contentdisposition =responseheaders.get("content-disposition");
        String filename=null;
        for(String item : contentdisposition) {
        	//attachment; filename=tempVGiJ74Juc7sbGZWhSampleDLG.docx
        	logger.debug("contentdisposition " +item);
        	if(item!=null && !"".equals(item)) {
        		filename=item.replace("attachment; filename=", "");
        		filename=filename.replaceAll("\"", "");
        		filename=filename.replaceAll(" ", "");
        	}	
        }	        
        //====================================================================================
        byte[] buffer=restresponse.getBody();
        //====================================================================================
        /**TODO: decommentare per avere la copia sul server del file sul Community Server.. 
    	int read;
        File targetFile = new File(filename);
        OutputStream outStream = new FileOutputStream(targetFile);                    
        int bytesRead=-1;
        try {                
        	int j=0;
            int c;
            for(byte i: buffer) {
            	c=i;               
            	outStream.write(c);
            }
         }finally {
            if (outStream != null) {                	
            	outStream.flush();
            	outStream.close();
            }
         }
         */  
        return  new ResponseEntity(buffer, responseheaders,HttpStatus.OK);
        //====================================================================================                        	        
		
		
	}

    /**
     * @TODO implementare le query
     * @param filetoken
     * @return
     */
	public Integer getIdOOFile(String filetoken) {
		//dato il token ottiene l'id documento
		//dato l'id documento ottine l'ulimo idOODFile salvato
		//		
		Integer idoofile=adapterVersioniRepository.getLastIdOOFileByCurrentToken(filetoken);
		return idoofile;
	}
	
	
}
