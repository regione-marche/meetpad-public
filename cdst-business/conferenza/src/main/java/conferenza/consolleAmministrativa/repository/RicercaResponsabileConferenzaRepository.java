package conferenza.consolleAmministrativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.consolleAmministrativa.model.RicercaResponsabileConferenza;

public interface RicercaResponsabileConferenzaRepository extends JpaRepository<RicercaResponsabileConferenza, Integer>,
		JpaSpecificationExecutor<RicercaResponsabileConferenza> {

}
