package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.RicercaConferenza;

public interface RicercaConferenzaRepository
		extends JpaRepository<RicercaConferenza, Integer>, JpaSpecificationExecutor<RicercaConferenza> {

}
