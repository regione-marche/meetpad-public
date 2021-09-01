package cdst_be_marche.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.bean._Typological;
import cdst_be_marche.repository.ConferenzaRepository;

public class _BaseService {
	
	public SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ConferenzaRepository confRepo;

	public String mapColonneConferenza(String colonna) {
		if (colonna.equals("id"))
			colonna = "idConferenza";
		if (colonna.equals("termineProcedimento"))
			colonna = "dataTermine";
		if (colonna.equals("terminePerIntegrazione"))
			colonna = "termineRichiestaIntegrazioniConferenza";
		if (colonna.equals("terminePerLaDetermina"))
			colonna = "termineEspressionePareri";
		if (colonna.equals("termineProssimaSessione"))
			colonna = "primaSessioneSimultanea";
		if (colonna.equals("tipologiaConferenza"))
			colonna = "tipologiaConferenza";
		if (colonna.equals("termineProcedimento"))
			colonna = "dataTermine";
		if (colonna.equals("stato"))
			colonna = "stato";
		if (colonna.equals("richiedente"))
			colonna = "codiceFiscaleRichiedente";
		return colonna;
	}
	
	protected LabelValue createNotNullLabelValue(_Typological typological) {
		if (typological != null) {
			String descrizione = messageSource.getMessage(typological.getDescrizione(), null,
					typological.getDescrizione(), request.getLocale());
			return new LabelValue(typological.getCodice(), descrizione);
		}
		return null;
	}
	
	public Conferenza findConferenceById(Integer id) {
		return this.confRepo.findById(id).orElse(null);
	}
	
	public String formatDate(Date data) {
		if (data != null) {
			return simpleDate.format(data);
		}
		return null;
	}
}
