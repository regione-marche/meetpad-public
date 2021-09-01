package conferenza.consolleAmministrativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.consolleAmministrativa.model.RicercaRubricaPreaccreditati;

public interface RicercaRubricaPreaccreditatiRepository  extends JpaRepository<RicercaRubricaPreaccreditati, Integer>,
JpaSpecificationExecutor<RicercaRubricaPreaccreditati> {

}
