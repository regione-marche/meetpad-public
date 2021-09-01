package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.RubricaRichiedenti;
import cdst_be_marche.model.TipologiaConferenza;

public interface RubricaRichiedentiRepository extends JpaRepository<RubricaRichiedenti, Integer>{

	List<RubricaRichiedenti> findByTipologiaConferenza(TipologiaConferenza tipologiaConferenza);

}
