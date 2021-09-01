package cdst_be_marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cdst_be_marche.model.RicercaConferenza;

public interface RicercaConferenzaRepository
		extends JpaRepository<RicercaConferenza, Integer>, JpaSpecificationExecutor<RicercaConferenza> {

}
