package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.TipoProfilo;

public interface TipoProfiloRepository extends JpaRepository<TipoProfilo, Integer>{

	Optional<TipoProfilo> findByCodice(String codice);
	
	}
