package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EventoPartecipante;
import conferenza.model.RuoloPartecipante;
import conferenza.model.TipoEvento;

public interface EventoPartecipanteRepository extends JpaRepository<EventoPartecipante, Integer>{

	List<EventoPartecipante> findByTipoEvento(TipoEvento tipoEvento);

	List<EventoPartecipante> findByRuoloPartecipante(RuoloPartecipante ruolo);

}
