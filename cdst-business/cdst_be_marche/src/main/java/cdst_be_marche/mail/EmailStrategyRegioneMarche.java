package cdst_be_marche.mail;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import cdst_be_marche.model.Mailer;
import cdst_be_marche.service._BaseService;
import cdst_be_marche.util.DbConst;

@Component("regione_marche")
/**
 * componente specifico per regione marche
 * @author arosina
 *
 */
public class EmailStrategyRegioneMarche extends _BaseService implements EmailStrategy {

	@Override
	public String generateSubject(Mailer mailer) {
		String oggettoEmail = null;
		if (mailer.getFk_tipologia_conferenza().equals(Integer.toString(DbConst.TIPOLOGIA_CONFERENZA_BANDA_LARGA))) {
			oggettoEmail = "Invito alla partecipazione alla conferenza del "
					+ formatDate(mailer.getPrima_sessione_simultanea())
					+ " alle ore "
					+ mailer.getOrario_conferenza().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString()
					+ " : costruzione infrastruttura passiva a Banda Ultra Larga";
		} else {
			oggettoEmail = "Invito alla partecipazione alla conferenza di servizi relativa all'istanza "
					+ mailer.getRiferimento_istanza() + " del " + formatDate(mailer.getPrima_sessione_simultanea());
		}
		return oggettoEmail;
	}
}
