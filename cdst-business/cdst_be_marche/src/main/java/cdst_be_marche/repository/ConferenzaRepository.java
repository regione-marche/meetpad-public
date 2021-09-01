package cdst_be_marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cdst_be_marche.model.Conferenza;

public interface ConferenzaRepository extends JpaRepository<Conferenza, Integer>, JpaSpecificationExecutor<Conferenza>{
	
	public Conferenza findByIdConferenza (Integer id);

}
