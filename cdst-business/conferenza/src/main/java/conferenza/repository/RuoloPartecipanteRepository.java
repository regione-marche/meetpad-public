package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.RuoloPartecipante;

public interface RuoloPartecipanteRepository extends JpaRepository<RuoloPartecipante, String> {
	
	public Optional<RuoloPartecipante> findByCodice (String codice);

}
