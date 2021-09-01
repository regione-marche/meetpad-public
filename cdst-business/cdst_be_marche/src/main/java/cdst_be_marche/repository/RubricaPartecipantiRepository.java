package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.RubricaPartecipanti;
import cdst_be_marche.model.RuoloPartecipante;
import cdst_be_marche.model.TipologiaConferenza;

public interface RubricaPartecipantiRepository extends JpaRepository<RubricaPartecipanti, Integer>{

	List<RubricaPartecipanti> findByTipologiaConferenza(TipologiaConferenza tipologiaConferenza);

	Optional<RubricaPartecipanti> findByTipologiaConferenzaAndRuoloPartecipante(TipologiaConferenza tipoConf,
			RuoloPartecipante ruoloPartecipante);

}
