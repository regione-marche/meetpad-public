package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
	public List<Persona> findByNomeContainingOrCognomeContainingAllIgnoreCase(String nome, String cognome, Sort sort);

	public Optional<Persona> findByCodiceFiscaleIgnoreCase(String codiceFiscale);
	
}
