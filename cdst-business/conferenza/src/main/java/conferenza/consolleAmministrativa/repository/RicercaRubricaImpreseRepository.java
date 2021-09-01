package conferenza.consolleAmministrativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.consolleAmministrativa.model.RicercaRubricaImprese;

public interface RicercaRubricaImpreseRepository
		extends JpaRepository<RicercaRubricaImprese, Integer>, JpaSpecificationExecutor<RicercaRubricaImprese> {

}
