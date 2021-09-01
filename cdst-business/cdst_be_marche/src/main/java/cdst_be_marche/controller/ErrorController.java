package cdst_be_marche.controller;

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

import cdst_be_marche.DTO.bean.Errore;
import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.exception.InvalidFieldEx;
import cdst_be_marche.exception.NotAuthorizedUser;
import cdst_be_marche.exception.NotEditableException;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.exception.ParseDateException;
import cdst_be_marche.exception.UnprocessableEntityException;

@CrossOrigin
@ControllerAdvice
@RestController
public class ErrorController extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	HttpServletRequest httpRequest;
	
	@ExceptionHandler(NotFoundEx.class)
	public final ResponseEntity<RispostaNoData> handleNotFoundException(NotFoundEx ex, WebRequest request) {
		  Errore error = new Errore("001", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("404");
		  risposta.setMsg(messageSource.getMessage("conferenza.notfound", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.NOT_FOUND);
		}

	@ExceptionHandler(NotAuthorizedUser.class)
	public final ResponseEntity<RispostaNoData> handleNotAuthorizedUser(NotAuthorizedUser ex, WebRequest request) {
		  Errore error = new Errore("401", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("401");
		  risposta.setMsg(messageSource.getMessage("utente.notAuthorized", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.UNAUTHORIZED);
		}	
	
	@ExceptionHandler(InvalidFieldEx.class)
	public final ResponseEntity<RispostaNoData> handleInvalidFieldException(InvalidFieldEx ex, WebRequest request) {
		RispostaNoData risposta = new RispostaNoData();
		risposta.setCode("404");
		risposta.setErrors(ex.getErrors());
		return new ResponseEntity<>(risposta, HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(ParseDateException.class)
	public final ResponseEntity<RispostaNoData> handleParseException(ParseDateException ex, WebRequest request) {
		  Errore error = new Errore("002", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("400");
		  risposta.setMsg(messageSource.getMessage("parse.error", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.NOT_FOUND);
		}
	
	@ExceptionHandler(NotEditableException.class)
	public final ResponseEntity<RispostaNoData> handleNotEditableException(NotEditableException ex, WebRequest request) {
		Errore error = new Errore("010", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("403.3");
		  risposta.setMsg(messageSource.getMessage("not.editable", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.NOT_FOUND);
		}

	@ExceptionHandler(UnprocessableEntityException.class)
	public final ResponseEntity<RispostaNoData> handleNotEditableException(UnprocessableEntityException ex, WebRequest request) {
		Errore error = new Errore("422.1", ex.getMessage());
		  RispostaNoData risposta = new RispostaNoData();
		  risposta.getErrors().add(error);
		  risposta.setCode("422");
		  risposta.setMsg(messageSource.getMessage("not.processable", null, httpRequest.getLocale()));
		  return new ResponseEntity<>(risposta, HttpStatus.NOT_FOUND);
	}
	
}
