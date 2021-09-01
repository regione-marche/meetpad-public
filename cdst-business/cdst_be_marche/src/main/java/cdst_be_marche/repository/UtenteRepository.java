package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

	Optional<Utente> findByCodiceFiscaleIgnoreCase(String codice);

}
