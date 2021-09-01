package conferenza.adapder.documentale.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import conferenza.DTO.AlfrescoDocumentAdapterDTO;
import conferenza.DTO.DocumentAdapterDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.alfresco.AlfrescoHelper;
import conferenza.adapder.integrazioni.domus.service.DomusConferenceService;
import conferenza.adapder.integrazioni.domus.service.DomusService;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.adapder.integrazioni.paleo.service.PaleoConferenceService;
import conferenza.exception.FileStorageException;
import conferenza.model.AlfrescoDocumentAdapter;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.model.TipologiaDocumento;
import conferenza.repository.AlfrescoDocumentAdapterRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.RegistroDocumentoTipoRepository;
import conferenza.service.FileSystemService;
import conferenza.service.RegistroDocumentoService;
import conferenza.util.DbConst;

@Service
public class DocumentAdapterService {

	private static final Logger logger = LoggerFactory.getLogger(DomusConferenceService.class.getName());

	@Autowired
	ConferenzaRepository conferenzaRepository;

	@Autowired
	PaleoConferenceService paleoConferenceService;

	@Autowired
	AlfrescoDocumentAdapterRepository alfrescoRepository;
	
	@Autowired
	private AlfrescoHelper alfrescoHelper;
	
	@Autowired
	PaleoAdapterService paleoService;

	@Autowired
	DomusService domusService;	
	
	@Autowired
	DomusConferenceService domusConferenceService;	
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	RegistroDocumentoTipoRepository registroDocumentoTipoRepository;
	
	@Autowired
	private FileSystemService fileSystemService;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	public static String getTipoDocumento(RegistroDocumento registro) {
		return  registro.getTipo().getCodice();
	}		
	
	public AlfrescoDocumentAdapter storeAlfrescoDocument(DocumentAdapterDTO adapterDTO) {
		if (adapterDTO instanceof AlfrescoDocumentAdapterDTO)
			return storeAlfrescoDocument(adapterDTO) ;
		
		return null;
	}

		
	
	
	public AlfrescoDocumentAdapter storeAlfrescoDocument(AlfrescoDocumentAdapter alfrescoDocument) {
		alfrescoDocument=alfrescoRepository.save(alfrescoDocument);
		return alfrescoDocument;
	}
	
	
	public AlfrescoDocumentAdapter storeAlfrescoDocument(AlfrescoDocumentAdapterDTO alfrescoDTO) {
		return storeAlfrescoDocument(AlfrescoDocumentAdapter.fillAfrescoDocumentAdapter(alfrescoDTO));		
	}

	
	public List<RegistroDocumento> getListRegistroDocumentoByDocument(Documento documento){
		List<RegistroDocumento> lista=registroDocumentoRepository.findByDocumento(documento);
		return lista;
	}

	/**
	 * 
	 * @param documentoDTO - servono id doc ed tipo doc
	 * @return
	 */
	public List<RegistroDocumento> getListRegistroDocumentoByDocument(DocumentoDTO documentoDTO){
		Documento documento =new Documento();
		documento.setIdDocumento(documentoDTO.getId());
		LabelValue label= documentoDTO.getTipoDocumento();
		TipologiaDocumento tipologiaDocumento=new TipologiaDocumento();
		tipologiaDocumento.setCodice(label.getKey());
		tipologiaDocumento.setDescrizione(label.getValue());
		documento.setTipologiaDocumento(tipologiaDocumento);
		List<RegistroDocumento> lista=getListRegistroDocumentoByDocument(documento);
		return lista;
	}	
	
	public RegistroDocumento getRegistroDocumento(Integer idRegistro){	
		Optional<RegistroDocumento> ord=registroDocumentoRepository.findById(idRegistro);
		if(ord==null)
			return null;
		return ord.get();
	}
	
	/**
		Intercetta le tipologie:  
		REGISTRO_DOCUMENTO_TIPO_FILESYSTEM
		REGISTRO_DOCUMENTO_TIPO_ALFRESCO
		REGISTRO_DOCUMENTO_TIPO_PALEO
		
		in entrambi i casi usa i seguenti parametri:
			registro.getTipo().getCodice()
			registro.getRiferimentoEsterno()
			
	 * @param registro
	 * @return Resource - Stream del File
	 */
	public Resource loadFileAsResourceByRegistry(RegistroDocumento registro) {		
		return loadFileAsResource(registro);
	}		
	
	
	public Resource loadFileAsResourceByIdRegistry(Integer idRegistro) {
		RegistroDocumento registro=getRegistroDocumento(idRegistro);	
		return loadFileAsResourceByRegistry(registro);
	}		
	
	/**
	 * Recupera la risorsa a partire dal solo id documento 
	 * @param idDocumento
	 * @return
	 */
	public Resource loadFileAsResourceByRegistry(Integer idDocumento) {
		RegistroDocumento registro=registroDocumentoService.getRegistroDocumentoByIdDocumento(idDocumento);
		return loadFileAsResourceByRegistry(registro);		
	}
	
	
	
	/**
	 * Per popolare le fieldset della lista dei documenti..
	 * dipende dalla tipologia di documento..per PAleo è fornita a partire dal registro.. 
	 * @param registro
	 * @return
	 */
	public String getRaggruppamentoByIdDocument(RegistroDocumento registro) {
		if (DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM.equals(registro.getTipo().getCodice())) {
			return null;
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_URL.equals(registro.getTipo().getCodice())) {
			return null;
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO.equals(registro.getTipo().getCodice())) {
			return null;
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIPALEO.equals(registro.getTipo().getCodice())) {
			return  paleoService.getRaggruppamentoByIdDocument(registro.getDocumento().getIdDocumento()); 
	    } else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE.equals(registro.getTipo().getCodice())) {
			   return  null;
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIDOMUS.equals(registro.getTipo().getCodice())) {
				   return  domusService.getRaggruppamentoByIdRegistro(registro.getId());
		} 			
		else
			throw new FileStorageException("Tipologia registro non supportata: " + registro.getTipo());
	}	
	
	public static String getCodiceTipoDocumentazione(ModalitaSalvataggioFile modalita) { 
		if (modalita != null) {
			if (modalita.equals(ModalitaSalvataggioFile.Filesystem)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM;
			} else if (modalita.equals(ModalitaSalvataggioFile.Alfresco)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO;
			} else if (modalita.equals(ModalitaSalvataggioFile.Paleo)) {
					return DbConst.REGISTRO_DOCUMENTO_TIPO_PALEO;
			} else if (modalita.equals(ModalitaSalvataggioFile.AllegatiPaleo)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIPALEO;
			} else if (modalita.equals(ModalitaSalvataggioFile.OnlyOffice)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE;
		    } else if (modalita.equals(ModalitaSalvataggioFile.Url)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_URL;
			} else if (modalita.equals(ModalitaSalvataggioFile.AllegatiDomus)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIDOMUS;
			}		
		}		
		return null;
	}
	
	public static String getCodiceFonte(FonteFile fonte) {
		if (fonte != null) {
			if (fonte.equals(FonteFile.conferenza)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_CONFERENZA;
			} else if (fonte.equals(FonteFile.suap)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_SUAP;
			} else if (fonte.equals(FonteFile.paleo)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_PALEO;
			}else if (fonte.equals(FonteFile.allegatipaleo)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_ALLEGATIPALEO;
			}else if (fonte.equals(FonteFile.OnlyOffice)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_ONLYOFFICE;
			}else if (fonte.equals(FonteFile.allegatidomus)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_DOMUS;
			}			

			
		}
		return null;
	}	
		
	/**
	 * TODO: nel caso di tipo REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE dato che ancora non
	 * funziona il download da OnlyOffice si prende l'ultima versione diversa da Only Office
	 * 
	 * @param registro
	 * @return
	 */
	public Resource loadFileAsResource(RegistroDocumento registro) {
		if (DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM.equals(registro.getTipo().getCodice())) {
			return fileSystemService.loadFileAsResourceFromFileSystem(registro.getRiferimentoEsterno());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO.equals(registro.getTipo().getCodice())) {
			return loadFileAsResourceFromAlfresco(registro.getRiferimentoEsterno());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIPALEO.equals(registro.getTipo().getCodice())) {
			return loadFileAsResourceFromPaleo(registro.getId());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE.equals(registro.getTipo().getCodice())) {
			return loadLastNotOnlyOfficeAsResource(registro.getDocumento());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALLEGATIDOMUS.equals(registro.getTipo().getCodice())) {
			return loadFileAsResourceFromDomus(registro.getId());
	
		} else
			throw new FileStorageException("Tipologia registro non supportata: " + registro.getTipo());
	}
	
	/**
	 * caricamento della Resource relativa all'ultima versione di registro di tipo diverso da OnlyOffice relativa al documento
	 * @param documento
	 * @return
	 */
	private Resource loadLastNotOnlyOfficeAsResource(Documento documento) {
		List<RegistroDocumento> registriFS = registroDocumentoRepository.findByDocumentoAndTipoNotOrderByDataDesc(documento,
				registroDocumentoTipoRepository.findById(DbConst.REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE).get());
		for (RegistroDocumento registroDocumento : registriFS) {
			return fileSystemService.loadFileAsResourceFromFileSystem(registroDocumento.getRiferimentoEsterno());
		}
		return null;
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
			logger.error("@loadFileAsResourceFromPaleo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		} catch (ServiceException e) {
			logger.error("@loadFileAsResourceFromPaleo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		} catch (SOAPException e) {
			logger.error("@loadFileAsResourceFromPaleo exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		}
	}
	
	
	protected Resource loadFileAsResourceFromDomus(Integer idRegistro) {
		try {
			return   domusService.getFileStreamAsResourceByIdRegistro(idRegistro);
		} catch (IOException | ServiceException | SOAPException e) {
			logger.error("@loadFileAsResourceFromDomus exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		}
	}
	
	public Boolean syncronize(Integer conferenceId) {
		try {
			logger.error("@syncronize confid: " + conferenceId);
			Conferenza conferenza = conferenzaRepository.findByIdConferenza(conferenceId);
			
			paleoConferenceService.syncronize(conferenza);
			domusConferenceService.syncronize(conferenza);

			logger.error("@syncronize OK");
		}catch (Throwable e) {
			logger.error("@syncronize exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			return false;
		}		
		
		return true;
	}
	
}
