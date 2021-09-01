package cdst_be_marche.auditor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import cdst_be_marche.service.UtenteService;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Autowired
	UtenteService utenteService;
	
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
		String cf = utenteService.getCodiceFiscaleUtente();
		return Optional.of(cf);
	}

}
