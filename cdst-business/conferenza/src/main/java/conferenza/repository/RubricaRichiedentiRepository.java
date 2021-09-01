package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Persona;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface RubricaRichiedentiRepository extends JpaRepository<RubricaRichiedenti, Integer>{

	List<RubricaRichiedenti> findByTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);
	
	List<RubricaRichiedenti> findByImpresaAndPrincipale(RubricaImprese impresa, Boolean principale);
	
	List<RubricaRichiedenti> findByImpresa(RubricaImprese impresa);

	Optional<RubricaRichiedenti> findByPersonaAndTipologiaConferenzaSpecializzazione(Persona persona,
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);

	Optional<RubricaRichiedenti> findByPersonaAndImpresa(Persona persona, RubricaImprese rubricaImp);

	Optional<RubricaRichiedenti> findByPersonaAndImpresaAndTipologiaConferenzaSpecializzazione(Persona persona,
			RubricaImprese rubricaImp, TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);

	List<RubricaRichiedenti> findByImpresaAndTipologiaConferenzaSpecializzazione(RubricaImprese impresa,
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);

	List<RubricaRichiedenti> findByPersona(Persona persona);

}
