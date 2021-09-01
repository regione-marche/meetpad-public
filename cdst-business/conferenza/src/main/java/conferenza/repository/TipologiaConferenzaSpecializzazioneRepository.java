package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface TipologiaConferenzaSpecializzazioneRepository extends JpaRepository<TipologiaConferenzaSpecializzazione, String> {

	Optional<TipologiaConferenzaSpecializzazione> findByDescrizione(String tipologiaConferenzaSpecializzazione);

}
