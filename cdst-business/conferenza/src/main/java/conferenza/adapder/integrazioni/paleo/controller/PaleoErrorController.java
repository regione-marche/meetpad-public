package conferenza.adapder.integrazioni.paleo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.adapder.integrazioni.paleo.exception.PaleoDocumentoNotFoundEx;
import conferenza.adapder.integrazioni.paleo.exception.PaleoFascicoloNotFoundEx;

@CrossOrigin
@ControllerAdvice
@RestController
public class PaleoErrorController extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	HttpServletRequest httpRequest;

	@ExceptionHandler(PaleoFascicoloNotFoundEx.class)
	public final ResponseEntity<RispostaNoData> handlePaleoFascicoloNotFoundException(PaleoFascicoloNotFoundEx ex, WebRequest request) {
		Errore error = new Errore("001", ex.getMessage());
		RispostaNoData risposta = new RispostaNoData();
		risposta.getErrors().add(error);
		risposta.setCode("404");
		risposta.setMsg(messageSource.getMessage("paleo.fascicolo.notfound", null, httpRequest.getLocale()));
		return new ResponseEntity<>(risposta, HttpStatus.OK);
	}

	@ExceptionHandler(PaleoDocumentoNotFoundEx.class)
	public final ResponseEntity<RispostaNoData> handlePaleoDocumentoNotFoundException(PaleoDocumentoNotFoundEx ex, WebRequest request) {
		Errore error = new Errore("001", ex.getMessage());
		RispostaNoData risposta = new RispostaNoData();
		risposta.getErrors().add(error);
		risposta.setCode("404");
		risposta.setMsg(messageSource.getMessage("paleo.documento.notfound", null, httpRequest.getLocale()));
		return new ResponseEntity<>(risposta, HttpStatus.OK);
	}
	
}
