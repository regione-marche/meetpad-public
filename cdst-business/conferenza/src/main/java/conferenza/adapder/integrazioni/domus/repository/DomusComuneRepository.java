package conferenza.adapder.integrazioni.domus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.domus.model.DomusComune;



public interface DomusComuneRepository extends JpaRepository<DomusComune, Integer>{

	@Query(value = "DELETE domus_comune WHERE codice_comune='?'", nativeQuery = true)
	void deleteComune(String codice);
	
}
