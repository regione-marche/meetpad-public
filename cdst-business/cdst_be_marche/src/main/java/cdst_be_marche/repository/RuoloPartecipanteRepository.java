package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.RuoloPartecipante;

public interface RuoloPartecipanteRepository extends JpaRepository<RuoloPartecipante, String> {
	
	public Optional<RuoloPartecipante> findByCodice (String codice);

}
