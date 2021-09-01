package conferenza.validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.EnteRicercaGeograficaDTO;
import conferenza.DTO.bean.Errore;

@Component
public class EnteValidator {
	
	@Autowired
	HttpServletRequest request;
	
	public List<Errore> validateEnteRicercaGeografica(EnteRicercaGeograficaDTO ricerca, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		if (ricerca.getValue() == null || ricerca.getValue().isEmpty()) {
			String msg = messageSource.getMessage("ente.ricerca.geografica.value.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.geographicalSearch.value", msg));
		}
		if (ricerca.getRegione() == null || ricerca.getRegione().isEmpty()) {
			String msg = messageSource.getMessage("ente.ricerca.geografica.regione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.geographicalSearch.regione", msg));
		}
		return errors;
	}

}
