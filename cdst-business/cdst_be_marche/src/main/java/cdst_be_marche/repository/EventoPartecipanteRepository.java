package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.EventoPartecipante;
import cdst_be_marche.model.RuoloPartecipante;
import cdst_be_marche.model.TipoEvento;

public interface EventoPartecipanteRepository extends JpaRepository<EventoPartecipante, Integer>{

	List<EventoPartecipante> findByTipoEvento(TipoEvento tipoEvento);

	List<EventoPartecipante> findByRuoloPartecipante(RuoloPartecipante ruolo);

}
