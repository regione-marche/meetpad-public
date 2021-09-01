package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Provincia;
import conferenza.model.Regione;

public interface ProvinciaRepository extends JpaRepository<Provincia, String>{
	
	public Optional<Provincia> findByCodice (String idProv);

	public List<Provincia> findByRegione(Regione regione, Sort sort);
	
	public List<Provincia> findByRegioneAndDescrizioneContainingIgnoreCase(Regione regione, String search, Sort sort);

	public List<Provincia> findByDescrizioneContainingIgnoreCase(String search, Sort sort);

	public Optional<Provincia> findBySigla(String provincia);
	
}
