package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Ente;
import conferenza.model.Persona;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.RubricaPreaccreditati;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface RubricaPreaccreditatiRepository extends JpaRepository<RubricaPreaccreditati, Integer>{

	List<RubricaPreaccreditati> findByEnte(Ente ente);

	List<RubricaPreaccreditati> findByPersonaAndEnte(Persona persona, Ente ente);
	
	List<RubricaPreaccreditati> findByEnteAndTipologiaConferenzaSpecializzazione(Ente ente, TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);
	
	Optional<RubricaPreaccreditati> findByPersonaAndEnteAndTipologiaConferenzaSpecializzazione(Persona persona, Ente ente, TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);
}
