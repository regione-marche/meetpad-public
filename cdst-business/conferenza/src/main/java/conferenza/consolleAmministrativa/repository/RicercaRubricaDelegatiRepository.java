package conferenza.consolleAmministrativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.consolleAmministrativa.model.RicercaRubricaDelegati;

public interface RicercaRubricaDelegatiRepository
		extends JpaRepository<RicercaRubricaDelegati, Integer>, JpaSpecificationExecutor<RicercaRubricaDelegati> {

}
