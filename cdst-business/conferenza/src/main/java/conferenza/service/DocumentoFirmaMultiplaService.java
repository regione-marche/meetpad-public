package conferenza.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.bean.Errore;
import conferenza.adapder.integrazioni.firma.calamaio.service.CalamaioService;
import conferenza.builder.DocumentoFirmaMultiplaBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.firma.service.FirmaService;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.model.RegistroDocumento;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoFirmaMultiplaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.StatoRepository;
import conferenza.util.DbConst;
import conferenza.validator.DocumentoFirmaMultiplaValidator;

@Transactional
@Service
public class DocumentoFirmaMultiplaService extends _BaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFirmaMultiplaService.class);

	@Autowired
	DocumentoFirmaMultiplaBuilder documentoFirmaMultiplaBuilder;

	@Autowired
	DocumentoFirmaMultiplaRepository documentoFirmaMultiplaRepository;
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	DocumentoFirmaMultiplaValidator documentoFirmaMultiplaValidator;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	CalamaioService calamaioService;

	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	StatoRepository statoRepo;
	
	@Autowired
	ConferenzaRepository conferenzaRepo;
	
	public DocumentoFirmaMultipla aggiornaFirmaMultipla(Documento documento, Integer idResponsabileFirma, String stato, boolean isNew) {
		if(idResponsabileFirma ==  null) // se è null allora il responsabile di firma è l'utente che ha effettuato la login
			idResponsabileFirma = utenteService.getAuthenticatedUser().getIdUtente();
		
		if(validateDocumentoFirma(documento.getIdDocumento(), idResponsabileFirma, isNew)) {
			DocumentoFirmaMultipla documentoFirma = documentoFirmaMultiplaRepository.findByIdDocumentoAndIdResponsabileFirma(documento.getIdDocumento(), idResponsabileFirma).orElse(null);
			Integer idUtenteCreatore = utenteService.getAuthenticatedUser().getIdUtente();
			DocumentoFirmaMultipla	documentoFirmaMultiSaved = this.documentoFirmaMultiplaRepository.save(this.documentoFirmaMultiplaBuilder.buildDocumentoFirmaMultipla(documentoFirma, documento, idResponsabileFirma, stato, idUtenteCreatore ));
			LOGGER.debug("@docs documentoFirmaMultipla: " + documentoFirmaMultiSaved.getId());
			return documentoFirmaMultiSaved;
		}
		return null;
	}

	public void firmaDocumento(Collection<Integer> idDocumenti,
			String calamaioRemoteUsername,
			String calamaioRemotePassword,
			String calamaioRemoteOTP,
			String calamaioRemoteDomain,
			String calamaioRemoteDocumentId,
			Boolean cadesPades
			) {

		List<Documento> listaDocumenti = documentoService.getListaDocumento(idDocumenti);
		MultipartFile[] files=new MultipartFile[listaDocumenti.size()];

		int i=0;
		for(Documento documento : listaDocumenti) {	
			try {
				Resource resource = registroDocumentoService.loadDocumentoAsResource(documento.getIdDocumento());
				//viene effettuata una copia sul file system prima dell'operazione di storicizzazione..
				File file = resource.getFile();
	
				String fileName = resource.getFilename();
				if (cadesPades == null || (cadesPades != null && !cadesPades)) {
					//fileName =  resource.getFilename()+"." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
					fileName += "." + FirmaService.SIGNED_FILE_EXTENSION;
				}  else {
					fileName += "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
				}
						
				FileInputStream input = new FileInputStream(file);
	
				documento.setNome(fileName);

				files[i]  = new MockMultipartFile("file",fileName, "text/plain", IOUtils.toByteArray(input));
			
				i++;
			}catch (Exception e2) {
				LOGGER.error("@calamaio error: " + e2.getMessage()  + " id_documento: " + documento.getIdDocumento(), e2);
				throw new InternalError("Errore reperimento documento da firmare");
			}
		}
		
		// firmo i documenti
		if(calamaioRemoteDocumentId != null && "undefined".equalsIgnoreCase(calamaioRemoteDocumentId))
			calamaioRemoteDocumentId = null;
		String errorMsg = null;

		boolean isAdditionalSign = calamaioRemoteDocumentId != null && calamaioRemoteDocumentId.length() > 0;

		// firmo i documenti
		try {
			files = calamaioService.checkHcmMultipleSignatureCalamaio(files,
					calamaioRemoteUsername,
					calamaioRemotePassword,
					calamaioRemoteOTP,
					calamaioRemoteDomain,
					isAdditionalSign,
					cadesPades);
		} catch (Exception e) {
			LOGGER.debug("@calamaio error: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			errorMsg = e.getMessage();
			throw new InternalError("Errore firma documenti: " + errorMsg);
		}
	
		// aggiorno lo stato del documento di firma:
		// aggiungo una riga nel registro_documento
		// il documento_firma_multipla, aggiungendo una riga con il nuovo stato, firmato
		// il documento con il nome del file firmato
		// caso in cui ho una nota di indizione come documento da firmare devo mandare avanti lo stato della conferenza
		int j=0;
		for(Documento documento : listaDocumenti) {	
			aggiornaDocumentoFirmato(documento, files[j]);
			aggiornaStatoConferenzaNotaIndizione(documento);
			j++;
		}
		
	}
	
	private void aggiornaStatoConferenzaNotaIndizione(Documento documento) {
		/*
		 * aggiornamento dello stato
		 */
		if(documento.getTipologiaDocumento() != null && documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
			Conferenza conferenza = documento.getConferenza();
			conferenza.setStato(statoRepo.findById(new Integer(DbConst.STATO_VALUTAZIONE).toString()).get());
			conferenzaRepo.save(conferenza);
		}
	}

	private boolean validateDocumentoFirma(Integer idDocumento, Integer idResponsabileFirma, boolean isNew) {
		List<Errore> errors = documentoFirmaMultiplaValidator.validateDocumentoFirma(idDocumento, idResponsabileFirma, isNew,  messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}
	
	private void aggiornaDocumentoFirmato(Documento documento, MultipartFile multipartFile) {
		// cerco il documento in bozza prima di aggiornare lo stato
		DocumentoFirmaMultipla documentoFirma = documentoFirmaMultiplaRepository.findByIdDocumentoAndIdResponsabileFirma(documento.getIdDocumento(), utenteService.getAuthenticatedUser().getIdUtente()).orElse(null);
		Integer idUtenteCreatore = documentoFirma.getIdUtenteCreatore();
		//2 - crea una nuova riga in registro documento - STORE su (registro_documento)
		RegistroDocumento registro=documentoService.creaRegistroDocumento(multipartFile, documento,null,null);
		// creo una nuova riga con riferimento alla nuova riga di registro
		DocumentoFirmaMultipla	documentoFirmaMultiSaved = this.documentoFirmaMultiplaRepository.save(this.documentoFirmaMultiplaBuilder.buildDocumentoFirmaMultipla(registro, documento.getIdDocumento(), utenteService.getAuthenticatedUser().getIdUtente(), STATO_DOCUMENTO.FIRMATO.getStatus(), idUtenteCreatore));
		LOGGER.debug("@docs documentoFirmaMultipla: " + documentoFirmaMultiSaved.getId());
		
		// aggiorno il documento con il nome file firmato
		documentoRepository.save(documento);
	}
	
	public boolean isDocumentoRegistroInBozza(Documento documento) {
		boolean isBozza = false;
		RegistroDocumento registro = documentoService.getRegistroDocumento(documento);
		DocumentoFirmaMultipla docFirma = this.documentoFirmaMultiplaRepository.findByIdDocumentoAndStatoAndIdRegistro(documento.getIdDocumento(), STATO_DOCUMENTO.IN_BOZZA.getStatus(), registro.getId());
		
		if(docFirma != null)
			isBozza = true;
		return isBozza;
	}
	
	public String getStatoDocumento(Documento documento) {
		String statoFirma = null;
		if(documento != null && documento.getIdDocumento() != null) {
			RegistroDocumento registro = documentoService.getRegistroDocumento(documento);
			DocumentoFirmaMultipla docFirma = this.documentoFirmaMultiplaRepository.findByIdDocumentoAndIdRegistro(documento.getIdDocumento(), registro.getId());
			if(docFirma != null) 
				statoFirma = docFirma.getStato();
		}
		return statoFirma;
	}
}
