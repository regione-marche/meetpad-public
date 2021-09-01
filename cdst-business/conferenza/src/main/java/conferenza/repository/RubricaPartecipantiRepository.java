package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Ente;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface RubricaPartecipantiRepository extends JpaRepository<RubricaPartecipanti, Integer>{

	List<RubricaPartecipanti> findByTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione);

	Optional<RubricaPartecipanti> findByTipologiaConferenzaSpecializzazioneAndEnte(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione,
			Ente ente);

}
