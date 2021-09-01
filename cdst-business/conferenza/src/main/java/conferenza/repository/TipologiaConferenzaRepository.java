package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipologiaConferenza;

public interface TipologiaConferenzaRepository extends JpaRepository<TipologiaConferenza, String> {

	Optional<TipologiaConferenza> findByDescrizione(String tipologiaConferenza);

}
