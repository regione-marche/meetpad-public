package cdst_be_marche.mail;

import org.springframework.stereotype.Component;

import cdst_be_marche.model.Mailer;
import cdst_be_marche.service._BaseService;

@Component("regione_lazio")
public class EmailStrategyRegioneLazio extends _BaseService implements EmailStrategy {

	@Override
	public String generateSubject(Mailer mailer) {
		String oggettoEmail = "Invito alla partecipazione alla conferenza di servizi relativa all'istanza "
				+ mailer.getRiferimento_istanza() + " del " + formatDate(mailer.getPrima_sessione_simultanea());
		return oggettoEmail;
	}

}
