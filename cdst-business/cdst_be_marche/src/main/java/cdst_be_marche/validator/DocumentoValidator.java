package cdst_be_marche.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.DTO.bean.Errore;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.TokenRegistroDocumento;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.TokenRegistroDocumentoRepository;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.util.JsonUtil;

@Component
public class DocumentoValidator {

	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	HttpServletRequest request;

	public List<Errore> validateCreaDocumento(DocumentoFileDTO documentoDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		if (documentoDTO.getFile() == null || documentoDTO.getFile().isEmpty()) {
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
		}
		if (documentoDTO.getNomeFile() == null || documentoDTO.getNomeFile().isEmpty()) {
			String msg = messageSource.getMessage("documento.nomeFile.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.fileName", msg));
		}
		if (documentoDTO.getTipoDocumento().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
			if (documentoDTO.getNumeroProtocollo() == null || documentoDTO.getFile().isEmpty()) {
				String msg = messageSource.getMessage("documento.protocolNumber.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("documento.protocolNumber", msg));
			}
			if (documentoDTO.getDataProtocollo() == null || documentoDTO.getDataProtocollo().isEmpty()) {
				String msg = messageSource.getMessage("documento.protocolDate.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("documento.protocolDate", msg));
			}
		}
		errors = validateFieldDocument(errors, documentoDTO, messageSource);
		return errors;
	}

	public List<Errore> validateTokenDocumento(String tokenFile, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		if (tokenFile == null || tokenFile.isEmpty()) {
			String msg = messageSource.getMessage("documento.token.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("token", msg));
		} else {
			if (validateVisibilitaDocumento(tokenFile, messageSource)) {
				TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);
				if (tokenRegistroDocumento == null) {
					String msg = messageSource.getMessage("documento.token.notValid", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("token", msg));
				} else {
					Date scadenza = tokenRegistroDocumento.getScadenza();
					if (scadenza.compareTo(new Date()) < 0) {
						String msg = messageSource.getMessage("documento.token.expired", null,
								request.getLocale());
						errors.add(Errore.getErrorSingleField("token", msg));
					}
				}
			}
		}
		return errors;
	}

	private boolean validateVisibilitaDocumento(String tokenFile, MessageSource messageSource) {
		// TODO implementare la visibilita in base all'utente collegato
		return true;
	}

	public List<Errore> validateModificaDocumento(DocumentoFileDTO documentoDTO, MessageSource messageSource, int id) {
		Documento documento = this.documentoRepository.findById(id).orElse(null);
		if (documento == null) {
			throw new NotFoundEx("document not found");
		}
		List<Errore> errors = new ArrayList<>();
		errors = validateFieldDocument(errors, documentoDTO, messageSource);
		return errors;
	}

	public List<Errore> validateFieldDocument(List<Errore> errors, DocumentoFileDTO documentoDTO,
			MessageSource messageSource) {
		if (documentoDTO.getVisibilitaPartecipanti() != null && !documentoDTO.getVisibilitaPartecipanti().isEmpty()) {
			if (!JsonUtil.validateListLabelValue(documentoDTO.getVisibilitaPartecipanti())) {
				String msg = messageSource.getMessage("documento.visibility.jsonNonValido", null,
						request.getLocale());
				errors.add(Errore.getErrorSingleField("visibility", msg));
			}
			if (documentoDTO.getTipoDocumento() == null) {
				String msg = messageSource.getMessage("documento.tipoDocumento.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("document.type", msg));
			}
		}
		return errors;
	}

	public List<Errore> validateConferenza(Integer idConferenza, boolean indizione, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		Optional<Conferenza> conferenza = confRepo.findById(idConferenza);
		if (!conferenza.isPresent()) {
			String msg = messageSource.getMessage("documento.conferenza.nonPresente", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("idConference", msg));
		} else if (indizione
				&& !conferenza.get().getStato().getCodice().equals(new Integer(DbConst.STATO_BOZZA).toString())) {
			String msg = messageSource.getMessage("documento.indizione.statoNonValido", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.type", msg));
		}
		return errors;
	}

}
