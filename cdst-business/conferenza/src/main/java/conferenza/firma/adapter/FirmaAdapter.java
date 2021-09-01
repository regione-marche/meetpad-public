package conferenza.firma.adapter;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.CRC32;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.builder.DocumentoBuilder;
import conferenza.firma.FirmaConfigurator;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.DTO.RegistroFirmaAdapterDTO;
import conferenza.firma.DTO.RegistroFirmaSessioneDTO;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.RegistroFirmaSessione;
import conferenza.firma.model.TipoFirma;
import conferenza.firma.model.bean.Firma;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.firma.repository.FirmaRepository;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.firma.repository.TipoFirmaRepository;
import conferenza.firma.service.FirmaService;
import conferenza.firma.service.FirmaSessionService;
import conferenza.service.DocumentoService;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.RandomUtil;
import io.lettuce.core.dynamic.annotation.Value;

import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class FirmaAdapter {

	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	DocumentoRepository documentoRepository;	

	@Autowired
	TipoFirmaRepository tipoFirmaRepository ;
	
	@Autowired
	DocumentoBuilder documentoBuilder;	
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	RegistroFirmaAdapterRepository registroFirmaDocumentoRepository;
	
	@Autowired
	FirmaRepository firmaRepository;
	
	@Autowired
	FirmaClientAdapter client;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	FirmaSessionService firmaSessione;

	@Autowired
	FirmaConfigurator configurator;
	
	@Autowired
	UtenteService utenteService;
	
	//-----------------------------------------------------
	//è il defualt
	private static String FSFIRMA="FIRMAFS";
	public static String STATUS_LOCK="LOCKED";
	public static String STATUS_UNLOCK="UNLOCKED";
	//La Sessione di frima è cancellata!
	public static String STATUS_DELETED="DELETED";
	//private static String STATUS_CANCELLED="CANCELLED";
	private static String STATUS_CLOSED="CLOSED";
	private static String STATUS_DRAFT ="DRAFT";
	private static String STATUS_REJECTED ="REJECTED";
	private static String STATUS_SIGNED ="SIGNED";
	
	private static String FASE_INIZIALE ="PRIMAFIRMA";
	private static String FASE_SUCCESSIVA ="FIRMACONGIUNTA";
	
	private static final Logger logger = LoggerFactory.getLogger(FirmaAdapter.class.getName());
			
	private String marshallerClass;
	
	private Object marshaller;

	
	private boolean locked=false;
	
	private static String SEPARATOR =File.separator;
	private static String relativePath = "uploads"+SEPARATOR+"firma";
	//-----------------------------------------------------
	//-----------------------------------------------------

	
	//-----------------------------------------------------
	
	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	
	//-----------------------------------------------------
	//RIFLESSIONE BEGIN
	//-----------------------------------------------------	
	/**
	 * 
	 * @param registro
	 */	
	public void fillMarshallerClass(Integer  fkTipoFirma) {		
		TipoFirma tipo=tipoFirmaRepository.getTipoFirma(fkTipoFirma);
		this.marshallerClass=tipo.getMarshalling();
	}
	
	public void init(Integer  fkTipoFirma) {
		fillMarshallerClass(fkTipoFirma);
		if(appContext==null)
			logger.debug("appContext is null ?!?!");
		logger.debug("marshallerClass:"+marshallerClass );
        marshaller= appContext.getBean(marshallerClass);//defined by the @Component params
	}
	
	/**
	 * 
	 * @param registro
	 */
	private void init(RegistroFirmaAdapter registro) {
		init(registro.getFk_tipo_firma());
	}
		
	
	
	/**
	 *   Method ss1Method = tClass.getMethod("setString1", new Class[] { String.class });
		 System.out.println("calling setString1 with 'val2'");
		 ss1Method.invoke(t, new Object[] { "val2" });
	 * @param idDocument
	 */
	public Resource clientGetFile(FirmaDTO par1) 
    throws IOException,NoSuchMethodException , SecurityException , IllegalAccessException , IllegalArgumentException , InvocationTargetException
	{
		
		if(par1==null)
			throw new IOException("Il parametro è null");

		if(par1.getResource()==null && par1.getFk_tipo_firma()!=3)
			throw new IOException("La resource  è null");

		
		Resource resource=null;
		String methodName = "getFile"; // the method to be called
        Method method;
		logger.debug("[FirmaAdapter] : "+ marshallerClass +"." + methodName);
		method = marshaller.getClass().getMethod(methodName,new Class[] { FirmaDTO.class });
        if (method != null) {
        	Object value = method.invoke(marshaller,par1);//java reflection ..
        	resource =(Resource)value;
        }  			
		return resource;
	}
	
	public FirmaDTO clientFirma(FirmaDTO par1) {
		FirmaDTO par2=par1; 
		String methodName = "firma"; // the method to be called
        Method method;
		try {
			logger.debug("[FirmaAdapter] : "+ marshallerClass +"." + methodName);
			method = marshaller.getClass().getMethod(methodName,new Class[] { FirmaDTO.class });
	        if (method != null) {
	        	Object value =  method.invoke(marshaller,par1);//java reflection ..	        	
	        	par2=(FirmaDTO)value;
	        }  			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}						
		return par2;
	}
	
	
	//-----------------------------------------------------
	//RIFLESSIONE END
	//-----------------------------------------------------
	

	
	

	
	

	
	/**
	 * @TODO: implementare - ottenere la FASE ossservando la FASE del documento precedente per SESSIONE DI FIRMA su registro_firma_adapter/registro_firma_sessione 
	 * @param idDocumento
	 * @return 
	 */
	public String getFASE(Integer idDocumento) {
		
		return FASE_SUCCESSIVA;
	}	
	
	
	
	
	private DocumentoDTO retrieveDocumentoDTO(Documento documento) {
		DocumentoDTO docDTO=documentoBuilder.buildDocumentoDTO(documento);		
		return docDTO;				
	}
	
	

	
	/**
	 * genera un token di sessione di firma - implementare il meccanismo per generare una sessione di firma 
	 * per i CONTROLLI di  firma CONGIUNTA 
	 * @param idDocumento
	 * @return
	 * @throws Exception 
	 */
	private String getTokenSessioneFirma(Integer idDocumento) throws Exception {		
		RegistroFirmaSessioneDTO dto = new RegistroFirmaSessioneDTO();
		dto.setFkDocumento(idDocumento);		
		RegistroFirmaSessione sessione=firmaSessione.initializeFirmaSession(dto );		
		return sessione.getToken();
	}
	

	//-----------------------------------------------------------------------
	//Azioni del processo di Firma
	//interazioni con il sitema esterno di firma..
	//-----------------------------------------------------------------------
	
	/**
	 * 1 - Viene preparata la firma: creata la riga su "registroFirmaAdapter"
	 * @throws IOException 
	 * 
	 */
	private FirmaDTO doActionBeforeFirma(FirmaDTO dto) 
			throws IOException {
		Resource resource = retrieve(dto.getIdDocumento(),dto.getFk_tipo_firma() ,dto.getIdUser());		
		dto.setResource(resource);
		return dto;		
	}
	
	/**
	 * 
	 * 1 - Viene preparata la firma: creata la riga su "registroFirmaAdapter"
	 * 2 - Viene passato il file al sistema esterno di firma
	 * 
	 * @throws IOException 
	 * 
	 */
	public FirmaDTO doActionSendFileToFirma(FirmaDTO dto) 
			throws IOException {
		init(dto.getFk_tipo_firma());
		dto=doActionBeforeFirma(dto);
		//viene passato il dto allo strumento esterno di firma..
		dto=clientFirma(dto);
		return dto;
	}
	
	/**
	 * 1 - ottiene il file firmato dal sistema di firma esterno..
	 * @param dto
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws IOException 
	 */
	private FirmaDTO doActionRetriveFilePostFirma(FirmaDTO dto) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {		
		init(dto.getFk_tipo_firma());
		
		String calamaioRemota = "";
		if (dto.getCalamaioRemota() != null) {
			calamaioRemota = dto.getCalamaioRemota();
		}
		
		//chiamata al sistema di firma esterno
		logger.debug("@calamaioPostFirma[" + dto.getFileName()+ "] - @calamaioCasdesPades[" + dto.getPadesCades() + "] - "
				+ " @calamaioRemota " + calamaioRemota +  "- doActionRetriveFilePostFirmo call ...");
		Resource resource = clientGetFile(dto);
		/*
		if (dto.getCalamaioRemota() != null &&
				dto.getCalamaioRemota().equals("calamaio")) {
			if (dto.getPadesCades() == null ||(dto.getPadesCades() != null && 
					!dto.getPadesCades())) {
				dto.setFileName(dto.getFileName() + "." + FirmaService.SIGNED_FILE_EXTENSION);
			} else {
				dto.setFileName(dto.getFileName() + "." + FirmaService.SIGNED_PADES_FILE_EXTENSION);
			}
		}
		*/
		logger.debug("@calamaioPostFirmaFileModificato[" + dto.getFileName()+ "] - @calamaioCasdesPades[" + dto.getPadesCades() + "] - "
				+ " @calaaioRemota " + calamaioRemota +  "- doActionRetriveFilePostFirmo call ...");
		dto.setResource(resource);		
		return dto;
	}
	
	public Resource doActionRetriveResourceFilePostFirma(FirmaDTO dto) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {		
		
		Resource result  = null;
		init(dto.getFk_tipo_firma());
		
	
		result  = clientGetFile(dto);	
		
		logger.debug("@calamaioPostFirmaFileResource[" + result +  "- doActionRetriveResourceFilePostFirma call ...");
		return result;
	}

	/**
	 * 1 - Ottiene  il file firmato dal servizio di firma esterno
	 * 2 - storicizza il file firmato 
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	public FirmaDTO doActionPostFirma(FirmaDTO dto) 
			throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//1 - ottiene il file firmato dal sistema di firma esterno..
		dto= doActionRetriveFilePostFirma(dto);
		//2 - storicizza il file ed aggiorna il registro di firma 
		return store(dto);
	}
	
	
	//-----------------------------------------------------------------------

	
	/**
	 * 
	 * @param dto
	 * @throws IOException 
	 */
	public FirmaDTO retrieve(FirmaDTO dto) throws IOException {		
		Resource resource = retrieve(dto.getIdDocumento(),dto.getFk_tipo_firma() ,dto.getIdUser());
		dto.setResource(resource);
		return dto;
	}

	//-----------------------------------------------------------------------
	// LOCK ed UNLOCK
	//-----------------------------------------------------------------------

	private File copyFile(File original,String prefisso,String path) throws IOException {		
		//File fcopied =new File(original.getAbsolutePath()+".copy");		
		//InputStream in = new BufferedInputStream(new FileInputStream(original));		
		Path copied = Paths.get(path + SEPARATOR +  "copy." +prefisso+"-" + original.getName());
		Path originalPath = original.toPath();
		java.nio.file.Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
	    return copied.toFile();
	}
	
	public Resource lock(FirmaDTO dto) 
			throws IOException {
		
		//==================================================
		Integer idDocumento =dto.getIdDocumento();
		Integer fk_tipo_firma=dto.getFk_tipo_firma();
		Integer idUser=dto.getIdUser();
		//==================================================	
		//----------------------------------------------------------------------
		//Verifica della sessione di Firma!!!
		//----------------------------------------------------------------------
		String token=null;
		try {
			 token=getTokenSessioneFirma(idDocumento);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Impossibile iniziare una sesione di Firma");
		}	
		//-----------------------------------------------
		//generazione del prefisso file con SHOT (token è la sessione di firma) 
		//-----------------------------------------------
		String shot =generateShot();
		logger.debug("shot: "+shot);		
		 //----------------------------------------------------------------------
		 String path =relativePath + SEPARATOR + idDocumento + SEPARATOR + token;
		 //----------------------------------------------------------------------
		 makeDir(path) ;
		 //----------------------------------------------------------------------
		 RegistroFirmaAdapter registroFirmaAdapter=null;
		 RegistroFirmaAdapterDTO registroFirmaAdapterDTO=new RegistroFirmaAdapterDTO();		
		 File stream=null;
		 Resource resource=null;
		 Documento documento=null;
		 DocumentoDTO docDTO=null;	
		 //String FASE=FASE_INIZIALE;
		 String CRC =null;		
		 MultipartFile multipartFile =null;		
		 //-----------------------------------------------
		 //retrieve del file -- Retrieve Resource/File (idDocumento) 
		 //questa parte è funzione del flient Adapter
		 //-----------------------------------------------
		 resource =getResource(idDocumento);	

		 //-----------------------------------------------
		 //ottien eil CRC della risorsa..
		 CRC=getCRC(resource.getFile());
		//-----------------------------------------------
		 documento=retrieveDocumentoByid(idDocumento);
		 docDTO=retrieveDocumentoDTO(documento);
		 //viene effettuata una copia sul file system prima dell'operazione di storicizzazione..
		 File ctoCopy=resource.getFile();
		 
		 stream = copyFile(ctoCopy,shot,path);
		 FileInputStream input = new FileInputStream(stream);
		 //
		 multipartFile = new MockMultipartFile("file",stream.getName(), "text/plain", IOUtils.toByteArray(input));
		 //2 - crea una nuova riga in registro documento - STORE su (registro_documento)
		 RegistroDocumento registro=documentoService.creaRegistroDocumento(multipartFile, documento,null,null);
		 //3 - crea una riga in registro_firma_adapter   - STORE su (registro_firma_adapter)	
		 registroFirmaAdapterDTO.setFase( getFASE(idDocumento));
		 registroFirmaAdapterDTO.setFk_tipo_firma(fk_tipo_firma);
		 registroFirmaAdapterDTO.setFkRegistro(registro.getId());
		 registroFirmaAdapterDTO.setIdDocumento(idDocumento);
		 registroFirmaAdapterDTO.setIdUser(idUser);
		 registroFirmaAdapterDTO.setStato(STATUS_LOCK);		 
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setDt_firma_ins(new Date());
	 
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setToken(token);
		 //-----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setShot(shot); 		 
		 registroFirmaAdapterDTO.setCrc(CRC);
		 registroFirmaAdapter = new RegistroFirmaAdapter();
		 registroFirmaAdapter = RegistroFirmaAdapterDTO.getRegistroFirmaAdapter(registroFirmaAdapterDTO);			 
		 registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);
		 
		 //risorsa lockata!
		 return resource;		 
	}

	private FirmaDTO unllock(Integer idDocumento,Integer fk_tipo_firma,Integer idUser,String pathForSave,String shot) 
			throws IOException {
		//sotto queste condizioni si fà la storicizazione..				
		RegistroFirmaAdapter registroFirmaAdapter=null;
		RegistroFirmaAdapterDTO registroFirmaAdapterDTO=new RegistroFirmaAdapterDTO();		
		File stream=null;
		Documento documento=null;
		DocumentoDTO docDTO=null;	
		String FASE=FASE_INIZIALE;
		String CRC =null;	
		
		MultipartFile multipartFile =null;
		 		 
		//-----------------------------------------------
		//SESSIONE DI FIRMA (PREREQUISITO)
		//-----------------------------------------------
		String token=null;
		try {
			 token=getTokenSessioneFirma(idDocumento);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Impossibile iniziare una sesione di Firma");
		}	
		 		 
		 //-----------------------------------------------
		 //retrieve del file -- Retrieve Resource/File (idDocumento) 
		 //questa parte è funzione del client Adapter
		 //-----------------------------------------------
		 File file=new File(pathForSave);
		 //-----------------------------------------------		 
		 //ottien eil CRC della risorsa..
		 CRC=getCRC(file);
		 //-----------------------------------------------		 
		 documento=retrieveDocumentoByid(idDocumento);
		 docDTO=retrieveDocumentoDTO(documento);
		 FileInputStream input = new FileInputStream(file);		 
		 
		 multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", IOUtils.toByteArray(input));
		 //2 - crea una nuova riga in registro documento - STORE su (registro_documento)
		 RegistroDocumento registro=documentoService.creaRegistroDocumento(multipartFile, documento,null,null);
		 //3 - crea una riga in registro_firma_adapter   - STORE su (registro_firma_adapter)	
		 registroFirmaAdapterDTO.setDt_firma_ins(new Date());
		 registroFirmaAdapterDTO.setFase( getFASE(idDocumento));
		 registroFirmaAdapterDTO.setFk_tipo_firma(fk_tipo_firma);
		 registroFirmaAdapterDTO.setFkRegistro(registro.getId());
		 registroFirmaAdapterDTO.setIdDocumento(idDocumento);
		 registroFirmaAdapterDTO.setIdUser(idUser);
		 registroFirmaAdapterDTO.setStato(STATUS_UNLOCK);
			 
		 registroFirmaAdapterDTO.setToken(token);
		 registroFirmaAdapterDTO.setShot(shot);
		 registroFirmaAdapterDTO.setCrc(CRC);
		 registroFirmaAdapter = new RegistroFirmaAdapter();
		 registroFirmaAdapter = RegistroFirmaAdapterDTO.getRegistroFirmaAdapter(registroFirmaAdapterDTO);			 
		 registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);
		 
		 FirmaDTO firma=RegistroFirmaAdapterDTO.getFirmaDTO(registroFirmaAdapterDTO);
		 return firma;
	}

	public void adminUnlock(Integer documentId){
		
		RegistroFirmaAdapter oldRegistroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(documentId);	
		RegistroFirmaAdapter registroFirmaAdapter = new RegistroFirmaAdapter();
		registroFirmaAdapter.setFkRegistro(oldRegistroFirmaAdapter.getFkRegistro());
		registroFirmaAdapter.setStato(this.STATUS_UNLOCK);
		registroFirmaAdapter.setToken(oldRegistroFirmaAdapter.getToken());
		registroFirmaAdapter.setFase(oldRegistroFirmaAdapter.getFase());
		registroFirmaAdapter.setCrc(oldRegistroFirmaAdapter.getCrc());
		registroFirmaAdapter.setFk_tipo_firma(oldRegistroFirmaAdapter.getFk_tipo_firma());
		registroFirmaAdapter.setIdDocumento(oldRegistroFirmaAdapter.getIdDocumento());		
		registroFirmaAdapter.setIdUser( utenteService.getAuthenticatedUser().getIdUtente());
		registroFirmaAdapter.setShot(oldRegistroFirmaAdapter.getShot());
		registroFirmaAdapter.setDt_firma_ins(new Date());	
			 
		registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);
		
	}

	//-----------------------------------------------------------------------
	// PRIVATE METHOD - STORE/RETRIVE file conferenza da firmare e firmati..
	//-----------------------------------------------------------------------	
	/**
	 * @TODO: le svlipluupo qui..ma devono andare a finire a livello di Client Adapter..
	 * 
	 * Ottiene il file da Firmare ..se ci sono le condizioni di firma..
	 * 
	 * @param idDocumento
	 * @param fk_tipo_firma
	 * @param idUser
	 * @return
	 * @throws IOException
	 */
	private Resource retrieve(Integer idDocumento,Integer fk_tipo_firma ,Integer idUser) 
			throws IOException {
		
		RegistroFirmaAdapter registroFirmaAdapter=null;	
		Resource resource=null;
		DocumentoDTO docDTO=null;	
		String FASE=FASE_INIZIALE;
		String CRC =null;
		boolean isEligible= true;
		 //1 - verifica che il documento sia firmabile:
		 //occorre verificare se si è in prima firma o firma successiva..
		 //1.1 - recuperare il tipo registro
		 //1.2 - salvare la FASE  in funzione del tipo registro: 
		 //1.3 - se il documento non è mai stato firmato allora la FASE è in prima firma (PRIMAFIRMA): non esiste registro_firma_adapter
		 //1.4 - se il documento è stato firmato allora la FASE è in seconda firma (FIRMASUCCESSIVA): esiste già una riga in registro_firma_adapter 
		 //RegistroDocumento registropartenza= registroDocumentoService.getRegistroDocumentoByIdDocumento(idDocumento);
		//RegistroDocumentoTipo registroTipodocumento=registropartenza.getTipo();
		//String codiceTiporegistro=registroTipodocumento.getCodice();
		 //-- 
		
		 //CONDIZIONI DI FIRMA - 1 - Consistenza sessione di Firma --------------------------------
		String token=null;
		try {
			token=getTokenSessioneFirma(idDocumento);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Impossibile iniziare una sesione di Firma");
		}			
		 
		//----------------------------------------------------------------
		//Costruzioine dei metadati di Firma
		//----------------------------------------------------------------
		FirmaDTO dto = new FirmaDTO();
		dto.setIdDocumento(idDocumento);
		dto.setFk_tipo_firma(fk_tipo_firma);
		dto.setIdUser(idUser);
		dto.setSessioneFirma(token);

		//----------------------------------------------------------------
		//
		//----------------------------------------------------------------<
		registroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(idDocumento); 
		//--verifica che il file NON sia mai stato lockato 
		if(registroFirmaAdapter==null) {
			this.setLocked(false);
			FASE=FASE_INIZIALE;
		}else {	
			this.locked= isLocked(dto);
			//se locked is true..non si pruò fare il download..
			//TODO : occorre gestire i casi in cui si fà loc del file ma non si procede alla firma..
			if(FASE_INIZIALE.equals(registroFirmaAdapter.getFase())  &&  (locked==false)) {				
				//si recupera l'hash del file "firmato""
				//CRC=getCRCHashingMD5(resource.getFile());
				//ovvero
				CRC=registroFirmaAdapter.getCrc();
			}
		} 
		//----------------------------------------------------------------<		
		//CONDIZIONI DI FIRMA - 2 unlock del file
		//verifica che per il file ci sia la possibilità di essere firmato..ovvero che sia "unloked!!!		
		isEligible=isEligibleToSigned(dto);
		if(isEligible==false) {
			throw new IOException("File is not Eligible to be signed");	
		}
		
		//-------------------------------------------------------------------------
		//STORICIZZAZIONI - LOCK
		//-------------------------------------------------------------------------
		if(locked==false) {						
			resource=lock(dto) ;
		}else {
			throw new IOException("File is locked for signing");			
		}
		//-------------------------------------------------------------------------		
		return resource;
	}

	//===================================================
	//STORE FILE FIRMATO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//===================================================
	/**
	 * @TODO: La store verso CONFERENZA le svlippo qui..ma devono andare a finire a livello di Client Adapter..
	 * @param idDocumento - è il documento "padre" (di riferimento)  in prima firma o in firma collaborativa 
	 * @param fk_tipo_firma
	 * @param idUser
	 * @param resource - è il documento firmato o a cui è stata aggiunta una firma
	 * @throws IOException 
	 */
	public FirmaDTO store(FirmaDTO dto) throws IOException {
		//===================================================
		// From FirmaDTO to Parameters!!!
		//===================================================
		Integer idDocumento= dto.getIdDocumento() ;
		Integer fk_tipo_firma=dto.getFk_tipo_firma() ;
		Integer idUser=dto.getIdUser();
		Resource resource=dto.getResource();
		//===================================================		
		//sotto queste condizioni si fà la storicizazione..				
		RegistroFirmaAdapter registroFirmaAdapter=null;
		RegistroFirmaAdapterDTO registroFirmaAdapterDTO=new RegistroFirmaAdapterDTO();		
		File stream=null;
		Documento documento=null;
		DocumentoDTO docDTO=null;	
		String FASE=FASE_INIZIALE;
		String CRC =null;
		
		
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA' VERIFICA Resource non può essere null !!!...
		//---------------------------------------------------------------------
		if(resource ==null) {
			throw new IOException("tentativo di storicizzare un documento NULL !!!");			
		} 			

		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA' VERIFICA Resource non può essere null !!!...
		//---------------------------------------------------------------------		
		if(dto.getFileName()==null && dto.getFk_tipo_firma()!=3)
			throw new IOException("tentativo di storicizzare un documento con nome file NULL !!!");
		if(dto.getFileName()==null && dto.getFk_tipo_firma()==3){
			dto.setFileName(RandomUtil.getRandomToken());			
		}	
		
		//-----------------------------------------------
		//(PREREQUISITO) - VALIDITA' SESSIONE DI FIRMA 
		//-----------------------------------------------
		String token=null;
		try {
			 token=getTokenSessioneFirma(idDocumento);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Impossibile iniziare una sesione di Firma");
		}
		dto.setSessioneFirma(token);

		//---------------------------------------------------------------------
		//Recupero l'ultima riga di registro per il fato documento...
		registroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(idDocumento);
		

		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  - tentativo di fare unlock di un file NON Lockato!?!?!
		if(registroFirmaAdapter==null)
			throw new IOException("impossibile fare l'unlock di un file se NON ci sono lock");
		
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  -  il documento non è già in stato di UNLOCK!!!
		if(STATUS_UNLOCK.equals(registroFirmaAdapter.getStato()))
			throw new IOException("tentativo di sovrascrivere un file: Impossibile fare l'unlock di un filke già nello stesso stato");
		
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  - VERIFICA CRC!!!...
		//---------------------------------------------------------------------
		//1 - CRC
		String crcOriginario=registroFirmaAdapter.getCrc(); 
		logger.debug("crcOriginario: "+crcOriginario );
		//---------------------------------------------------------------------		
		//ottiene il file dall'inputStream..
		// TODO: occorre intercettare la sessione di firma..
		//---------------------------------------------------------------------	
		String currentDir = System.getProperty("user.dir");
		logger.debug("currentDir:	" + currentDir);
		
		String pathForSave =  currentDir +SEPARATOR + "uploads"+SEPARATOR+"firma"+SEPARATOR +idDocumento+SEPARATOR+token;
		makeDir(pathForSave) ;
		//---------------------------------------------------------------------
		String shot = generateShot();
		pathForSave =  pathForSave+SEPARATOR+ shot+"-"+ dto.getFileName();
		logger.debug("File: pathForSave: "+pathForSave );
		dto.setShot(shot);
		//---------------------------------------------------------------------
		//persistenza del File
        File savedFile = new File(pathForSave);
		InputStream streamResource=resource.getInputStream();
        FileOutputStream out = null;
        try {
        	out = new FileOutputStream(savedFile);
        	int read;
            final byte[] bytes = new byte[1024];
            while ((read = streamResource.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        }catch (Exception e) {
        	e.printStackTrace();
		} 		

		
		//---------------------------------------------------------------------		
		//(PREREQUISITO) - VALIDITA'  - viene passato il crc del file che si è firmato..BEGIN
		//---------------------------------------------------------------------
        /*
		String crcDTO=dto.getCrc();
		if(crcDTO==null)
			throw new IOException("CRC before unlock is null");		

		
		logger.debug("CRC uploaded: "+crcDTO );
		if(crcDTO !=null &&  !crcOriginario.equals(crcDTO)) {
			throw new IOException("tentativo di storicizzare un documento per cui non c'è continuità di CRC");
		}
		*/
		//---------------------------------------------------------------------		
		//(PREREQUISITO) - VALIDITA'  - viene passato il crc del file che si è firmato..END
		//---------------------------------------------------------------------

        
        
		//TODO: verificare come intercettare il CRC del File firmato e confrontarlo con l'originario?!!?
		//prerequisito: i CDC devono essere uguali!!!
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  - VERIFICA CRC!!!...BEGIN        
		//---------------------------------------------------------------------
		//2 - CRC
        /*
		CRC=getCRCHashingMD5(savedFile);
		//prerequisito: il CRC non può essere NULL!!!
		if(CRC ==null) {			
			throw new IOException("tentativo di storicizzare un documento per cui il CRC è NULL");			
		} 		
		if(CRC !=null &&  !crcOriginario.equals(CRC)) {
			logger.debug("CRC: "+CRC );
			logger.debug("tentativo di storicizzare un documento per cui non c'è continuità di CRC!" );
			//throw new IOException("tentativo di storicizzare un documento per cui non c'è continuità di CRC");
		}
		*/
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  - VERIFICA CRC!!!...END        
		//---------------------------------------------------------------------

		
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  -  VERIFICA LOCK!!!...
		//---------------------------------------------------------------------		
		//l'opeazione di store prevede che la retrieve sia stata fatta prima sul file;
		//ovvero
		//verifiche:
		//1 - retrieve da stesso utente
		//2 - lo stato di idDocumento padre deve essere a locked	
		//3 - in caso di problemi rilancia un eccezione ed esce..
		this.locked= verificaLock(dto);
		if(this.locked==true) {
			dto=unllock( idDocumento, fk_tipo_firma, idUser, pathForSave,shot);
		}	
		return dto;
	}

	/**
	 * 
	 * @param pathForSave
	 */
	private void makeDir(String pathForSave) {
		File dir1=new File(pathForSave);
		boolean success =true;
		boolean exists = dir1.exists();		
		if(!exists) {
			logger.debug("DIR - non esiste - pathForSave:	" + pathForSave);
			success = dir1.mkdirs();
		}else {
			logger.debug("DIR - Esiste - pathForSave:	" + pathForSave);			
		}
		if (!success) {
		    // Directory creation failed
			try {
				throw new IOException("tentativo di creare cartelle temporanee fallito !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		dir1.setWritable(true, false);
	}
	

	//===================================================
	//STORE FILE FIRMATO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//===================================================
	/**
		Cancellla la sessione di firma!
	 */
	public FirmaDTO cancel(FirmaDTO dto) throws IOException {
		//-----------------------------------------------
		//(PREREQUISITO) - VALIDITA' SESSIONE DI FIRMA 
		//-----------------------------------------------
		String token=dto.getSessioneFirma();
		if(token==null || token.equals(""))
			throw new IOException("E' necessaria la sessione di firma!");
		dto.setSessioneFirma(token);
		//---------------------------------------------------------------------
		//(PREREQUISITO) - VALIDITA'  -  VERIFICA cancellabilità sessione
		//---------------------------------------------------------------------		
		//1 - La cancelazione di una sessione di firma significa il ripristino del file originario sul registro.. 
		return recoveryOriginalFileBeforeSessionInit(dto);	
	}
	
	/**
	 * data la sessione difirma occorre 
	 * 1 - recuperare l'id del registro del file iniziale.
	 * 2 - persiste il file su nuova riga di registro in modo che la ricerca
	 *     fornista il file originario come file corrente ..
	 * 3 - aggiornare lo stato sul registro
	 * 4 - aggiornare il registro delle sessioni      
	 * @param dto
	 * @throws IOException 
	 */
	private FirmaDTO recoveryOriginalFileBeforeSessionInit(FirmaDTO dto) 
			throws IOException {
		//===================================================
		// From FirmaDTO to Parameters!!!
		//===================================================
		Integer idDocumento= dto.getIdDocumento() ;
		Integer fk_tipo_firma=dto.getFk_tipo_firma() ;
		Integer idUser=dto.getIdUser();
		//===================================================		
		MultipartFile multipartFile =null;		
		//sotto queste condizioni si fà la storicizazione..				
		RegistroFirmaAdapter registroFirmaAdapter=null;
		RegistroFirmaAdapterDTO registroFirmaAdapterDTO=new RegistroFirmaAdapterDTO();		
		File stream=null;
		Documento documento=null;
		DocumentoDTO docDTO=null;	
		String FASE=FASE_INIZIALE;
		String CRC =null;		
		String shot=generateShot();
		
		//--------------------------------------------------
		//inizializza l'oggetto Documento per la registrazione
		documento=retrieveDocumentoByid(idDocumento);
		//@TODO - veririfcare il valore che deve avere la FONTE?!!?
		
		//--------------------------------------------------
		//@TODO:  [STEP] 1 - aggiornamento del [Registro Documenti]
		//--------------------------------------------------
		Integer fkRegistro = getIdRegistroDocumentiForResourceBeforeSigningAction(dto);
		Resource resource = getRegistroDocumento(fkRegistro); 
		FileInputStream input = new FileInputStream(resource.getFile());
		
		//recuperail filename
		String ext1 = FilenameUtils.getExtension(resource.getFilename());		
		multipartFile = new MockMultipartFile("file",shot+"."+ext1, "text/plain", IOUtils.toByteArray(input));
		//2 - crea una nuova riga in registro documento - STORE su (registro_documento)
		RegistroDocumento registro=documentoService.creaRegistroDocumento(multipartFile, documento,null,null);
		
		//-------------------------------------------------------
		//@TODO: [STEP] 2 - aggiornare lo stato del [Registro Firma]
		//-------------------------------------------------------
		registroFirmaAdapterDTO.setFase( getFASE(idDocumento));
		registroFirmaAdapterDTO.setFk_tipo_firma(fk_tipo_firma);
		registroFirmaAdapterDTO.setFkRegistro(registro.getId());
		registroFirmaAdapterDTO.setIdDocumento(idDocumento);
		registroFirmaAdapterDTO.setIdUser(idUser);
		registroFirmaAdapterDTO.setStato(STATUS_DELETED);		 
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setDt_firma_ins(new Date());	
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setToken(dto.getSessioneFirma());
		 //-----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setShot(shot); 		 
		 registroFirmaAdapterDTO.setCrc(CRC);
		 registroFirmaAdapter = new RegistroFirmaAdapter();
		 registroFirmaAdapter = RegistroFirmaAdapterDTO.getRegistroFirmaAdapter(registroFirmaAdapterDTO);			 
		 registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);

		//-------------------------------------------------------		 
		//@TODO: [STEP] 3 - aggiornare lo stato del [Registro Sessione]
		//-------------------------------------------------------
		firmaSessione.cancel(dto);		
		//-------------------------------------------------------
		
		 return dto;
	}
	
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	public String getLastSigningSessionForIdDoc(FirmaDTO firma) {
		RegistroFirmaAdapter rfa = lastRegistroFirmaAdapterByidDoc(firma);
		if(rfa==null)
			return null;
		
		return  rfa.getToken();		
	}
	
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	private Integer getIdRegistroDocumentiForResourceBeforeSigningAction(FirmaDTO firma) {
		String tokenLastSigningSession= getLastSigningSessionForIdDoc(firma); 
		if(tokenLastSigningSession!=null)
			return  registroFirmaDocumentoRepository.getIdRegistroDocumentiForResourceBeforeSigningAction(tokenLastSigningSession);
		return null;
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
	
	//=============================================================================================
	

	/**
	 * Ritorna il CRC di un file...
	 * @param file
	 * @return
	 * @throws IOException
	 */
    private static long checksumMappedFile(File file) throws IOException {    	 
    	FileInputStream inputStream = new FileInputStream(file); 
    	FileChannel fileChannel = inputStream.getChannel();
    	int len = (int) fileChannel.size();
    	MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, len);
    	CRC32 crc = new CRC32();
    	for (int cnt = 0; cnt < len; cnt++) {
    		int i = buffer.get(cnt);
    		crc.update(i);
    	}
    	return crc.getValue();
    }
    /***
     * Ritorna il CRC di un file con hasching MD5...
     * @param file
     * @return
     * @throws IOException
     */
    public static long  checksumMappedFilefMD5(File file) 
    		throws IOException {
    	HashCode md5 = Files.hash(file, Hashing.md5());
    	byte[] md5Bytes = md5.asBytes();
    	String md5Hex = md5.toString();

    	HashCode crc32 = Files.hash(file, Hashing.crc32());
    	int crc32Int = crc32.asInt();

    	// the Checksum API returns a long, but it's padded with 0s for 32-bit CRC
    	// this is the value you would get if using that API directly
    	long checksumResult = crc32.padToLong();    	
    	return checksumResult;
    }

    @Deprecated
    public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
    
    public static String getCRC(MultipartFile multipart) throws IOException {
    	File tempFile = File.createTempFile("meetPAD", "Firma");
    	// ask JVM to delete it if you forgot / can't due exception
    	tempFile.deleteOnExit();
    	// transfer MultipartFile to File
    	multipart.transferTo(tempFile);
    	// do business logic here
    	String crc=getCRC(tempFile);
    	// tidy up
    	tempFile.delete();     	
    	return crc;
    }
    
    public static String getCRC(File file) throws IOException {
    	return Long.toHexString(checksumMappedFile(file));     	
    }
    
    public static String getCRCHashingMD5(File file) throws IOException {
    	return Long.toHexString(checksumMappedFile(file));     	
    }
    
	private  String  generateShot() {
		return RandomUtil.getRandomToken();		
	}
    //===========================================================================
    // WORKFOW - PRIVATE - END
    //===========================================================================
	
	/**
	 * 	
	 * @param dto
	 * @param registro
	 * @return
	 * @throws Exception
	 */
	public RegistroFirmaAdapter initializeSigningsessionRegistroFirmaAdapter(FirmaDTO dto,RegistroDocumento registro) 
			throws Exception {
		 
		
		RegistroFirmaAdapter registroFirmaAdapter=null;
		//================================================================
		String shot =generateShot();
		//inizializza la sessione di firma
		String sessioneFirma=getTokenSessioneFirma(dto.getIdDocumento());
		dto.setSessioneFirma(sessioneFirma);
		//================================================================		
		RegistroFirmaAdapterDTO registroFirmaAdapterDTO = new  RegistroFirmaAdapterDTO();
		registroFirmaAdapterDTO.setFase( getFASE(dto.getIdDocumento()));
		registroFirmaAdapterDTO.setFk_tipo_firma(dto.getFk_tipo_firma());
		registroFirmaAdapterDTO.setFkRegistro(registro.getId());
		registroFirmaAdapterDTO.setIdDocumento(dto.getIdDocumento());
		registroFirmaAdapterDTO.setIdUser(dto.getIdUser());
		registroFirmaAdapterDTO.setStato(STATUS_LOCK);		 
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setDt_firma_ins(new Date());	
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setToken(dto.getSessioneFirma());
		 //-----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setShot(shot); 		 
		 registroFirmaAdapterDTO.setCrc(dto.getCrc());
		 registroFirmaAdapter = new RegistroFirmaAdapter();
		 registroFirmaAdapter = RegistroFirmaAdapterDTO.getRegistroFirmaAdapter(registroFirmaAdapterDTO);			 
		 registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);
		 return registroFirmaAdapter;
  }	 
	
	
    
    /**
     * viene passato l'idDocumento per ottenere l'oggetto logico.. Documento
     * per la Firma è importante però ottenere il file da Firmare associaato al documento   
     * 
     * @param idDocumento
     * @return
     */
	private Documento retrieveDocumentoByid(Integer idDocumento) {
		java.util.Optional<conferenza.model.Documento> documentooptional=documentoRepository.findById(idDocumento);
		Documento documento=documentooptional.get();
		return documento;				
	}
	
	/**
	 * per la Firma è importante però ottenere il file da Firmare associato al documento..ovvero all'ultima riga di registro..
	 * @param idDocumento
	 * @return
	 */
	private Resource getResource(Integer idDocumento) {		
		Resource resource = registroDocumentoService.loadDocumentoAsResource(idDocumento);
		return resource;
		
	}
    
    /**
     * Per una data sessione di firma restituisce l'ultimo recod lockato..
     * ..dal record lockato andrà poi estratto l'id di registro del file da firmare
     * @param FirmaDTO - dto
     * @return RegistroFirmaAdapter 
     */
    private RegistroFirmaAdapter lastUNlokedRegistroFirmaAdapter(FirmaDTO dto) {    	
    	logger.debug("Sessione di Firma: "+dto.getSessioneFirma());
    	RegistroFirmaAdapter  rfa =  this.registroFirmaDocumentoRepository.lastUnlokedRegistroFirmaAdapter(dto.getSessioneFirma()); 
    	return rfa;
    }

    /**
     * 
     * @param FirmaDTO - dto
     * @return
     */
    private RegistroFirmaAdapter lastRegistroFirmaAdapterByidDoc(FirmaDTO dto) {    	
    	RegistroFirmaAdapter rfa = this.registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(dto.getIdDocumento());
    	return rfa;
    };	
    
    //===========================================================================
    // WORKFOW - PUBLIC
    //===========================================================================    
    
    
    /**
     * Verifica che un Documento sia firmabile..
     * @param dto
     */
    public boolean isEligibleToSigned(FirmaDTO dto) {
    	
    	RegistroFirmaAdapter rfa =  lastUNlokedRegistroFirmaAdapter( dto);
    	if(rfa!=null) {    			    		
    		return true;
    	}else {
    		//verifica che non sia il primo..in tal caso è in prima firma
    		rfa = lastRegistroFirmaAdapterByidDoc(dto);
    		if(rfa==null)
    			return true;//è il primo record!!!
    		else {
    			logger.debug("Sessione: "+rfa.getToken());
    			if(STATUS_DELETED.equals(rfa.getStato()) ){
    					logger.debug("La Sessione: "+rfa.getToken()+" è stata cancellata..File is Singnable");
    					return true;		
    			}		
    		}
    		
    	}	
    	
    	return false;
    }
    
    /**
     * Se E' possibile firmare un file viene restituito l'ultima riga 'UNLOCKED' ..
     * PEr la firma congiunta è necessario ottenere l'ultimo file ..firmato..
     *  
     * @param dto
     * @return
     */
    public RegistroFirmaAdapter getEligibleToSigned(FirmaDTO dto) {
    	RegistroFirmaAdapter rfa =  lastUNlokedRegistroFirmaAdapter( dto);
    	if(rfa!=null)
    		return rfa;
    	return null;
    }
    
    
    
	private boolean isLocked(FirmaDTO  dto) {
		boolean isLockeStatus=false;
		
		RegistroFirmaAdapter rfa=lastRegistroFirmaAdapterByidDoc(dto);
		if( STATUS_LOCK.equals(rfa.getStato()))
			isLockeStatus= true;
		
		return isLockeStatus;
	}

	
	/** 
	 * CRITERI:
	 * 1) CONSISTENZA DELLA CATENA DI FIRMA
	 * 2) CONSISTENZA DEGLI STATI
	 * 
	 * 1 - Verifica che il caricamento sia effettato dall'ultimo utente che ha lockato!!!!!
	 * Verifica che un documento sia lockato in firma dall'utente corrente che stà firmando!
	 * 2 -Verifica che lo stato corrente sia LOCKED 
	 * 
	 * @param idDocumento
	 * @throws IOException 
	 */
	private boolean verificaLock(FirmaDTO  dto) throws IOException {
		boolean isLockeStatus= isLocked(dto);

		RegistroFirmaAdapter rfa=lastRegistroFirmaAdapterByidDoc(dto);
		if(isLockeStatus== true)
		if(Integer.compare(rfa.getIdUser(),dto.getIdUser())!=0)
			throw new IOException("il file è in LOCK da altro utente: consistenza della catena di firma"); 
				
		return isLockeStatus;
	}    
	
    /**
     * Ritorna 	L'ultimo CRC per User ed Doc per riga in stato LOCKED
     * @param idUser
     * @param idDocument
     * @return
     */
	public String getLastCRCForUserDoc(Integer idUser,Integer idDocument) {
		return  registroFirmaDocumentoRepository.getLastCRCForUserDoc(idUser,idDocument);
	}
	
	public Integer getDocIdByDownloadToken(String downloadToken) {
		return  registroFirmaDocumentoRepository.getDocIdByDownloadToken(downloadToken);
		
	}	
	
	
	public List<Firma> getListFirmaByConference(Integer  idConferenza){
		return firmaRepository.getListFirmaByConference(idConferenza); 
	}
	
	/*
	 * Metodo per ottenere il proprietario di un documento
	 * 
	 */
	public PersonaDTO getOwnerDoc(Integer idDoc) {
		PersonaDTO proprietario = new PersonaDTO();
		
		FirmaDTO dto = new FirmaDTO();
		dto.setIdDocumento(idDoc);
		
		RegistroFirmaAdapter rfa = lastRegistroFirmaAdapterByidDoc(dto);
		if(rfa!=null){
			Integer idUser = rfa.getIdUser();
			
			Utente utente = utenteService.findById(idUser);
			
			String nome = utente.getNome();
			String cognome = utente.getCognome();
			String cf = utente.getCodiceFiscale();
			
			proprietario.setNome(nome);
			proprietario.setCognome(cognome);
			proprietario.setCodiceFiscale(cf);
		}

				
		return proprietario;
		
	}
	
//	public String getOwnerDoc(Integer idDoc) {
//		
//		String datiProprietario = "";
//		FirmaDTO dto = new FirmaDTO();
//		dto.setIdDocumento(idDoc);
//		
//		RegistroFirmaAdapter rfa = lastRegistroFirmaAdapterByidDoc(dto);
//		if(rfa!=null){
//			Integer idUser = rfa.getIdUser();
//			
//			Utente utente = utenteService.findById(idUser);
//			
//			String nome = utente.getNome();
//			String cognome = utente.getCognome();
//			String cf = utente.getCodiceFiscale();
//			datiProprietario = nome + "," + cognome + "," + cf;
//			
//			
//			//proprietario.setNome(nome);
//			//proprietario.setCognome(cognome);
//			//proprietario.setCodiceFiscale(cf);
//		}
//
//				
//		return datiProprietario;
//		
//	}
	
	public RegistroFirmaAdapter initializeSigningsessionRegistroFirmaMultiAdapter(FirmaDTO dto,RegistroDocumento registro,String stato) 
			throws Exception {
		 
		
		RegistroFirmaAdapter registroFirmaAdapter=null;
		//================================================================
		String shot =generateShot();
		//inizializza la sessione di firma
		String sessioneFirma=getTokenSessioneFirma(dto.getIdDocumento());
		dto.setSessioneFirma(sessioneFirma);
		//================================================================		
		RegistroFirmaAdapterDTO registroFirmaAdapterDTO = new  RegistroFirmaAdapterDTO();
		registroFirmaAdapterDTO.setFase( getFASE(dto.getIdDocumento()));
		registroFirmaAdapterDTO.setFk_tipo_firma(dto.getFk_tipo_firma());
		registroFirmaAdapterDTO.setFkRegistro(registro.getId());
		registroFirmaAdapterDTO.setIdDocumento(dto.getIdDocumento());
		registroFirmaAdapterDTO.setIdUser(dto.getIdUser());
		registroFirmaAdapterDTO.setStato(stato);		 
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setDt_firma_ins(new Date());	
		 //----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setToken(dto.getSessioneFirma());
		 //-----------------------------------------------------------------------
		 registroFirmaAdapterDTO.setShot(shot); 		 
		 registroFirmaAdapterDTO.setCrc(dto.getCrc());
		 registroFirmaAdapter = new RegistroFirmaAdapter();
		 registroFirmaAdapter = RegistroFirmaAdapterDTO.getRegistroFirmaAdapter(registroFirmaAdapterDTO);			 
		 registroFirmaAdapter=registroFirmaDocumentoRepository.save(registroFirmaAdapter);
		 return registroFirmaAdapter;
  }	
}
