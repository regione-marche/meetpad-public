package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>, JpaSpecificationExecutor<Persona> {
	
	public List<Persona> findByNomeContainingOrCognomeContainingAllIgnoreCase(String nome, String cognome, Sort sort);

	public Optional<Persona> findByCodiceFiscaleIgnoreCase(String codiceFiscale);
	
	public List<Persona> findByNomeContainingOrCognomeContainingAllIgnoreCase(String nome, String cognome);
	
}
