package conferenza.mail;

import java.time.temporal.ChronoUnit;


import org.springframework.stereotype.Component;

import conferenza.model.Mailer;
import conferenza.service._BaseService;
import conferenza.util.DbConst;

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
		
		if (mailer.getFk_tipologia_conferenza_specializzazione().equals(Integer.toString(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_DECISORIA))) {
//			oggettoEmail = "Invito alla partecipazione alla conferenza del "
//					+ formatDate(mailer.getPrima_sessione_simultanea())
//					+ " alle ore "
//					+ mailer.getOrario_conferenza().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString()
//					+ " : costruzione infrastruttura passiva a Banda Ultra Larga";
			oggettoEmail = mailer.getRiferimento_istanza() + " - " +  "Indizione conferenza";
		} else {
			if (mailer.getFk_tipologia_conferenza_specializzazione().equals(Integer.toString(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_PREISTRUTTORIA))) {
//			oggettoEmail = "Invito alla partecipazione alla "
//					+ mailer.getRiferimento_istanza() + " del " + formatDate(mailer.getPrima_sessione_simultanea());
				oggettoEmail = mailer.getRiferimento_istanza() + " - " +  "Indizione conferenza";
			} else {
//				oggettoEmail = "Invito alla partecipazione alla conferenza di servizi relativa all'istanza "
//						+ mailer.getRiferimento_istanza() + " del " + formatDate(mailer.getPrima_sessione_simultanea());
				oggettoEmail = mailer.getRiferimento_istanza() + " - " +  "Indizione conferenza";
			}
		}
		return oggettoEmail;
	}
}
