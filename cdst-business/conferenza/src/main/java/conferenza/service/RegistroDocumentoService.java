package conferenza.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import conferenza.adapder.alfresco.AlfrescoHelper;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.exception.FileStorageException;
import conferenza.file.FileStorageProperties;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.model.TokenRegistroDocumento;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.TokenRegistroDocumentoRepository;
import conferenza.util.DbConst;

@Transactional
@Service
public class RegistroDocumentoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroDocumentoService.class);

	private final String downloadContextPath;
	private final int tokenExpireMinutes;

	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	DocumentAdapterService documentAdapterService;

	@Autowired
	FileSystemService fileSystemService;
	
	@Autowired
	private AlfrescoHelper alfrescoHelper;
	
	@Autowired
	private PaleoAdapterService paleoService;

	@Autowired
	public RegistroDocumentoService(FileStorageProperties fileStorageProperties) {
		this.downloadContextPath = fileStorageProperties.getDownloadContextPath();
		this.tokenExpireMinutes = new Integer(fileStorageProperties.getTokenExpireMinutes()).intValue();
	}
	
	/**
findLastByIdDocumento
	 * @return
	 */
	public RegistroDocumento getRegistroDocumentoByIdDocumento(Integer idDocumento) {
		return registroDocumentoRepository.findLastByIdDocumento(idDocumento);		
	}
	

	protected Resource loadFileAsResourceFromAlfresco(String adapterKey) {
		try {
			return alfrescoHelper.loadFileAsResource(adapterKey);
		} catch (IOException e) {
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		}
	}
	
	protected Resource loadFileAsResourceFromPaleo(Integer idRegistro) {
		try {
			return   paleoService.getFileStreamAsResourceByIdRegistro(idRegistro);
		} catch (IOException e) {
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		} catch (ServiceException e) {
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		} catch (SOAPException e) {
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param registro
	 * @return
	 */
	public Resource loadFileAsResource(RegistroDocumento registro) {
		if (DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM.equals(registro.getTipo().getCodice())) {
			return fileSystemService.loadFileAsResourceFromFileSystem(registro.getRiferimentoEsterno());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO.equals(registro.getTipo().getCodice())) {
			return loadFileAsResourceFromAlfresco(registro.getRiferimentoEsterno());
		} else
			throw new FileStorageException("Tipologia registro non supportata: " + registro.getTipo());
	}
	

	public Resource loadFileAsResource(String token) {
		RegistroDocumento registro = getRegistroDocumentoByToken(token);
		return documentAdapterService.loadFileAsResource(registro);
	}
	
	public String getFileNameDocumento(String token) {
		String fileName= null;
		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(token);
		if(tokenRegistroDocumento != null) {
			Documento documento = tokenRegistroDocumento.getRegistroDocumento().getDocumento();
			fileName = documento.getNome();
		}
		return fileName;
	}

	/**
	 * restituisce l'ultima versione del registro documento per il documento
	 * relativo. (il registro documento collegato al token potrebbe non essere il
	 * più recente e quindi viene utilizzato il metodo
	 * documentoService.getRegistroDocumento che restituisce l'ultima versione)
	 * 
	 * @param tokenFile
	 * @return
	 */
	private RegistroDocumento getRegistroDocumentoByToken(String tokenFile) {
		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);
		Documento documento = tokenRegistroDocumento.getRegistroDocumento().getDocumento();
		return documentoService.getRegistroDocumento(documento);
	}

	public String getTokenRegistroDocumento(RegistroDocumento registroDocumento) {
		TokenRegistroDocumento token = tokenRegistroDocumentoRepository
				.save(new TokenRegistroDocumento(registroDocumento, computeScadenza()));
		return token.getToken();
	}
	
	public void refreshTokenValidate(String token) {
		LOGGER.debug("RegistroDocumentoService - riga 143 - token: " + token);
		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(token);
		LOGGER.debug("RegistroDocumentoService - riga 145 - tokenRegistroDocumento(TOKEN): " + tokenRegistroDocumento.getToken());
		tokenRegistroDocumento.setScadenza(computeScadenza());
		tokenRegistroDocumentoRepository.save(tokenRegistroDocumento);
	}

	private Date computeScadenza() {
		return DateUtils.addMinutes(new Date(), tokenExpireMinutes);
	}

	/**
	 * 
	 * @return
	 */
	private UriComponentsBuilder getUriComponentsBuilder(){
		UriComponentsBuilder ucb =UriComponentsBuilder.fromUriString("/downloadFile/");		
		try{
			ucb = ServletUriComponentsBuilder.fromCurrentContextPath();		
			if (downloadContextPath != null && !downloadContextPath.isEmpty()) {
				ucb = UriComponentsBuilder.fromHttpUrl(downloadContextPath);
			}
		}catch (Exception e) {
			LOGGER.debug("[resolveFileDownloadUri] : java.lang.IllegalStateException: No current ServletRequestAttributes ! ");
		}
		return ucb;
	}
	
	
	public String resolveFileDownloadUri(RegistroDocumento registroDocumento) {
		if (registroDocumento != null) {
			if (!registroDocumento.getTipo().getCodice().equals(DbConst.REGISTRO_DOCUMENTO_TIPO_URL)) {
				UriComponentsBuilder ucb = getUriComponentsBuilder();
				return ucb.path("/downloadFile/").path(getTokenRegistroDocumento(registroDocumento)).toUriString();
			} else {
				return registroDocumento.getRiferimentoEsterno();
			}
		}
		return null;
	}

	public Resource loadDocumentoAsResource(Integer idDocumento) {
		Documento documento = documentoRepository.findById(idDocumento).orElse(null);
		Resource resource = null;
		if (documento != null) {
			RegistroDocumento registroDocumento = documentoService.getRegistroDocumento(documento);
			if (registroDocumento != null) {
				resource = documentAdapterService.loadFileAsResource(registroDocumento);
			}
		}
		
		return resource;
	}
	
	public File loadFileFromDocumento(Documento documento) {
		Resource resource = null;
		if (documento != null) {
			RegistroDocumento registroDocumento = documentoService.getRegistroDocumento(documento);
			if (registroDocumento != null) {
				resource = documentAdapterService.loadFileAsResource(registroDocumento);
			}
		}

		File fileIndizione = null;
		try {
			if (resource != null)
				fileIndizione = resource.getFile();
		} catch (IOException e) {
			LOGGER.error("load file indizione: " + e.getMessage());
			e.printStackTrace();
		}

		return fileIndizione;
	}

	public Resource loadZipFileAsResourceByDocIDs(String docIDs) {
		
		Resource resource = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sdf.format(new Date());
		String zipFile = "documenti_protocollo_" + now +".zip";
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(baos);
		String[] docs = docIDs.split(",");
		int count = 0;
        try {
            for (int i=0; i < docs.length; i++) {
    			Resource res = documentAdapterService.loadFileAsResourceByRegistry(Integer.decode(docs[i]));
    			InputStream is = res.getInputStream();
    			String filename = res.getFilename();
    			boolean filenameOK = false;
    			do {
	    			try {
	    				zos.putNextEntry(new ZipEntry(filename));
	    				filenameOK = true;
	    				count++;
	    			} catch (ZipException e) {
	    				String estensione = filename.substring(filename.lastIndexOf("."), filename.length());
	    				String nome = filename.substring(0, filename.lastIndexOf("."));
	    				filename = nome + "(1)" + estensione;
	    			}
    			} while (filenameOK == false);
    			
    	        int len;
    	        while ((len = is.read(buffer)) > 0) {
    	        	zos.write(buffer, 0, len);
    	        }
    	        zos.closeEntry();
    	        is.close();
            }

            zos.close();
            LOGGER.debug("File zip generato correttamente - contiene " + count + " files");
            //baos.close();	- non serve
            
            return new ByteArrayResource(baos.toByteArray()) {
    			@SuppressWarnings("unused")
    			@Override
    			public String getFilename() {	
    				return zipFile;
    			}
    		};
        }
        catch (Exception e) {
        	LOGGER.error("Errore nella creazione del file zip. Doc Ids=" + docIDs + " Errore: " + e.getMessage());
        }
        return resource;
	}

}