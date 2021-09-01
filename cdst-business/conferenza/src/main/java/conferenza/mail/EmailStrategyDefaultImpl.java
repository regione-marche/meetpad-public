package conferenza.mail;

import org.springframework.stereotype.Component;

import conferenza.model.Mailer;
import conferenza.service._BaseService;

@Component("default")
public class EmailStrategyDefaultImpl extends _BaseService implements EmailStrategy {

	@Override
	public String generateSubject(Mailer mailer) {
		String oggettoEmail = "Invito alla partecipazione alla conferenza di servizi relativa all'istanza "
				+ mailer.getRiferimento_istanza() + " del " + formatDate(mailer.getPrima_sessione_simultanea());
		return oggettoEmail;
	}

}
