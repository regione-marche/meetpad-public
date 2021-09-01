package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Ente;

public interface EnteRepository extends JpaRepository<Ente, Integer>{
	
	List<Ente> findByFlagAmministrazioneProcedente(Boolean flagamministrazioneprocedente);
	List<Ente> findByDescrizioneEnteContainingIgnoreCase(String search, Sort sort);
	Optional<Ente> findByCodiceFiscaleEnte(String codiceFiscale);
}
