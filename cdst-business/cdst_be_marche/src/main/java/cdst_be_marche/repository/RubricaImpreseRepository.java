package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.RubricaImprese;
import cdst_be_marche.model.TipologiaConferenza;

public interface RubricaImpreseRepository extends JpaRepository<RubricaImprese, Integer>{

	List<RubricaImprese> findByTipologiaConferenza(TipologiaConferenza tipologiaConferenza);

}
