package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Persona;
import conferenza.model.RubricaDelegati;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface RubricaDelegatiRepository extends JpaRepository<RubricaDelegati, Integer>{

	List<RubricaDelegati> findByTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);
	
	Optional<RubricaDelegati> findByPersonaAndTipologiaConferenzaSpecializzazione(Persona persona,
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);

	List<RubricaDelegati> findByPersona(Persona persona);
}
