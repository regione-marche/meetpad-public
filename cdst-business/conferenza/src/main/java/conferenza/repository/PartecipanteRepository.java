package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Partecipante;
import conferenza.model.RuoloPartecipante;

public interface PartecipanteRepository extends JpaRepository<Partecipante, Integer>{

	List<Partecipante> findByEnte(Ente ente);

	List<Partecipante> findByConferenza(Conferenza conferenza);

	List<Partecipante> findByRuoloPartecipanteAndConferenza(RuoloPartecipante ruoloPartecipante,
			Conferenza findByIdConferenza);

	Optional<Partecipante> findByConferenzaAndEnte(Conferenza conferenza, Ente ente);

}
