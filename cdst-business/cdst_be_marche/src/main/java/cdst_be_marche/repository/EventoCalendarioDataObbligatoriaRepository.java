package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.EventoCalendarioDataObbligatoria;
import cdst_be_marche.model.TipologiaConferenza;

public interface EventoCalendarioDataObbligatoriaRepository
		extends JpaRepository<EventoCalendarioDataObbligatoria, Integer> {
	
	List<EventoCalendarioDataObbligatoria> findByTipologiaConferenza(TipologiaConferenza tipologiaConferenza); 

}
