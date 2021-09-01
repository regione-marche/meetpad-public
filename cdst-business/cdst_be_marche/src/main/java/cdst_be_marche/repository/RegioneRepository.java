package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Regione;

public interface RegioneRepository extends JpaRepository<Regione, String>{
	
	public Optional<Regione> findByCodice(String id);
	
	public List<Regione> findByDescrizioneContainingIgnoreCase(String search, Sort sort);
	
}
