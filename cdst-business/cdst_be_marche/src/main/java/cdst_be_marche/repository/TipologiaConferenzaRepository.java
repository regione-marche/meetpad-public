package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.TipologiaConferenza;

public interface TipologiaConferenzaRepository extends JpaRepository<TipologiaConferenza, String> {

	Optional<TipologiaConferenza> findByDescrizione(String tipologiaConferenza);

}
