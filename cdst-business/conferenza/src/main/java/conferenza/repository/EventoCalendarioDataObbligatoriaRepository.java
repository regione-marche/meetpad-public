package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EventoCalendarioDataObbligatoria;
import conferenza.model.TipologiaConferenza;

public interface EventoCalendarioDataObbligatoriaRepository
		extends JpaRepository<EventoCalendarioDataObbligatoria, Integer> {
	
	List<EventoCalendarioDataObbligatoria> findByTipologiaConferenza(TipologiaConferenza tipologiaConferenza); 

}
