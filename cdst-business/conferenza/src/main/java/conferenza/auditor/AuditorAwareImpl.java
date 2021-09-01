package conferenza.auditor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import conferenza.properties.AutenticazioneProperties;
import conferenza.service.UtenteService;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Autowired
	UtenteService utenteService;

	@Autowired
	AutenticazioneProperties autenticazioneProperties;
	
	@Override
	public Optional<String> getCurrentAuditor() {
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		return authentication.getName();*/
		/*
		 * TODO: implementare anche la verifica dell'autenticazione quando sarà definito il meccanismo di autenticazione
		 */
		String cf;
		if (autenticazioneProperties.getUtenteAuditFittizio()) {
			cf = autenticazioneProperties.getCfUtenteAuditFittizio();
		}
		else {
			cf = utenteService.getCodiceFiscaleUtente();
		}
		return Optional.of(cf);
	}

}
