package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Impresa;
import conferenza.model.RubricaImprese;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface RubricaImpreseRepository extends JpaRepository<RubricaImprese, Integer>{

	List<RubricaImprese> findByTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);
	
	Optional<RubricaImprese> findByTipologiaConferenzaSpecializzazioneAndImpresa(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione, Impresa impresa);

	List<RubricaImprese> findByImpresa(Impresa impresa);

}
