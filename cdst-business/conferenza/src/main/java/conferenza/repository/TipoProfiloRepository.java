package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipoProfilo;

public interface TipoProfiloRepository extends JpaRepository<TipoProfilo, String>{

	Optional<TipoProfilo> findByCodice(String codice);
	Optional<TipoProfilo> findByDescrizione(String descrizione);
	
}
