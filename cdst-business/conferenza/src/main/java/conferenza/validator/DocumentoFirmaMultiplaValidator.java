package conferenza.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.bean.Errore;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.repository.DocumentoFirmaMultiplaRepository;

@Component
public class DocumentoFirmaMultiplaValidator {
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	DocumentoFirmaMultiplaRepository documentoFirmaMultiplaRepository;
	
	public List<Errore> validateDocumentoFirma(Integer idDocumento, Integer idResponsabileFirma, boolean isNew,  MessageSource messageSource) {
		
		List<Errore> errors = new ArrayList<>();
		Optional<DocumentoFirmaMultipla> documentoFirma = documentoFirmaMultiplaRepository.findByIdDocumentoAndIdResponsabileFirma(idDocumento, idResponsabileFirma);
		if (!isNew && !documentoFirma.isPresent()) {
			String msg = messageSource.getMessage("documento.conferenza.nonPresente", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("idConference", msg));
		}
		if (idResponsabileFirma == null) {
			String msg = messageSource.getMessage("documento.tipoDocumento.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.type", msg));
		}
		return errors;
	}

}
