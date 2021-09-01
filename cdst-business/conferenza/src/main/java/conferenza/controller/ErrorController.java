package conferenza.controller;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import conferenza.exception.ExpiredException;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotAuthorizedUser;
import conferenza.exception.NotEditableException;
import conferenza.exception.NotFoundEx;
import conferenza.exception.ParseDateException;
import conferenza.exception.UnprocessableEntityException;

@CrossOrigin
@ControllerAdvice
@RestController
public class ErrorController extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	HttpServletRequest httpRequest;

	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler(NotFoundEx.class)
	public final ResponseEntity<RispostaNoData> handleNotFoundException(NotFoundEx ex, WebRequest request) {
		  Errore error = new Errore("404", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("404");
		  risposta.setMsg(messageSource.getMessage("conferenza.notfound", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
		}

	@ExceptionHandler(NotAuthorizedUser.class)
	public final ResponseEntity<RispostaNoData> handleNotAuthorizedUser(NotAuthorizedUser ex, WebRequest request) {
		  Errore error = new Errore("403", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("403");
		  risposta.setMsg(messageSource.getMessage("utente.notAuthorized", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
		}	
	
	@ExceptionHandler(InvalidFieldEx.class)
	public final ResponseEntity<RispostaNoData> handleInvalidFieldException(InvalidFieldEx ex, WebRequest request) {
		RispostaNoData risposta = new RispostaNoData();
		risposta.setCode("422");
		risposta.setErrors(ex.getErrors());
		return new ResponseEntity<>(risposta, HttpStatus.OK);
		}
	
	@ExceptionHandler(ParseDateException.class)
	public final ResponseEntity<RispostaNoData> handleParseException(ParseDateException ex, WebRequest request) {
		  Errore error = new Errore("400", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("400");
		  risposta.setMsg(messageSource.getMessage("parse.error", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
		}
	
	@ExceptionHandler(NotEditableException.class)
	public final ResponseEntity<RispostaNoData> handleNotEditableException(NotEditableException ex, WebRequest request) {
		Errore error = new Errore("403.3", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("403.3");
		  risposta.setMsg(messageSource.getMessage("not.editable", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
		}

	@ExceptionHandler(UnprocessableEntityException.class)
	public final ResponseEntity<RispostaNoData> handleNotEditableException(UnprocessableEntityException ex, WebRequest request) {
		Errore error = new Errore("422.1", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("422");
		  risposta.setMsg(messageSource.getMessage("not.processable", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
	}
	
	@ExceptionHandler(ExpiredException.class)
	public final ResponseEntity<RispostaNoData> handleExpiredException(ExpiredException ex, WebRequest request) {
		  Errore error = new Errore("410", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("410");
		  risposta.setMsg(messageSource.getMessage("link.expired", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.OK);
		}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<RispostaNoData> handleExpiredException(RuntimeException ex) {
		String errorID = "ID: " + new BigInteger(130, new SecureRandom()).toString(32);
		logger.error("Errore: " + errorID);
		ex.printStackTrace();
		Errore error = new Errore("500", ex.getMessage());
		RispostaNoData risposta = new RispostaNoData();
		risposta.getErrors().add(error);
		risposta.setCode("500");
		String msg = errorID;
		if(ex.getMessage()!= null)
			msg = msg + " Errore: " + ex.getMessage();
		risposta.setMsg(msg);
		return new ResponseEntity<>(risposta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
