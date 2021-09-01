package conferenza.facade;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.ListaDocumentoFirmaDTO;
import conferenza.DTO.RicercaDocumentoDTO;
import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.adapder.integrazioni.firma.calamaio.service.CalamaioService;
import conferenza.builder.DocumentoBuilder;
import conferenza.exception.DocumentoException;
import conferenza.model.Documento;
import conferenza.service.DocumentoFirmaMultiplaService;
import conferenza.service.DocumentoService;

@Component
public class DocumentFacade {
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private CalamaioService calamaioService;
	
	@Autowired
	private DocumentoFirmaMultiplaService documentoFirmaMultiplaService;
	
	@Autowired
	private DocumentoBuilder documentoBuilder;

	@Transactional
	//metodo sotto AOP
	public void uploadDocumento(String idDocumento, MultipartFile file, String idResponsabileCST) {
		try {
			documentoService.uploadFile(file);
			impostaDocumento(idDocumento, STATO_DOCUMENTO.IN_BOZZA);
		}catch(RuntimeException e) {
			throw new DocumentoException("Errore interno nel Sistema.", e);
		}
	}
	
	@Transactional
	public void firmaDocumenti(String idResponsabileCST, Collection<Integer> idsDocumento,
			String calamaioRemoteUsername,
			String calamaioRemotePassword,
			String calamaioRemoteOTP,
			String calamaioRemoteDomain,
			String calamaioRemoteDocumentId,
			Boolean cadesPades) {
		documentoFirmaMultiplaService.firmaDocumento(idsDocumento, calamaioRemoteUsername, 
				calamaioRemotePassword, calamaioRemoteOTP, calamaioRemoteDomain, calamaioRemoteDocumentId,cadesPades);
	}
	
	@Transactional
	public void rifiutaDocumenti(String idResponsabileCST, Collection<Integer> idsDocumento) {
		try {
			for(Integer idDocumento: idsDocumento) 
				impostaDocumento(idDocumento.toString(), STATO_DOCUMENTO.RIFIUTATO);
		}catch(RuntimeException e) {
			throw new DocumentoException("Errore interno nel Sistema.", e);
		}
	}

	public ListaDocumentoFirmaDTO getDocumentiInBozza(RicercaDocumentoDTO documento) {
		return getDocumentiFirmatiByStato(documento, STATO_DOCUMENTO.IN_BOZZA);
	}

	public ListaDocumentoFirmaDTO getDocumentiFirmati(RicercaDocumentoDTO documento) {
		return getDocumentiFirmatiByStato(documento, STATO_DOCUMENTO.FIRMATO);
	}
	
	public ListaDocumentoFirmaDTO getDocumentiRifiutati(RicercaDocumentoDTO documento) {
		return getDocumentiFirmatiByStato(documento, STATO_DOCUMENTO.RIFIUTATO);
	}

	private Documento impostaDocumento(String idDocumento, STATO_DOCUMENTO stato) {
		Optional<Documento> optional = documentoService.getDocumentoById(Integer.valueOf(idDocumento));
		Documento documento = optional.get();
		
		String statoDocumento = stato.getStatus();
		
		// devo aggiungere il documento come da firmare con firma multipla
		documentoFirmaMultiplaService.aggiornaFirmaMultipla(documento, null, statoDocumento, false);

		return documento;
	}
	
	private ListaDocumentoFirmaDTO getDocumentiFirmatiByStato(RicercaDocumentoDTO documento, STATO_DOCUMENTO stato){
		try {
			documento.setStato(stato.getStatus());
			return documentoService.getDocumentoByIdResponsabileAndStato(documento, stato);
		}catch(RuntimeException e) {
			throw new DocumentoException("Errore interno nel Sistema.", e);
		}
	}


}
