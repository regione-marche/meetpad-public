package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Attivita;
import cdst_be_marche.model.Azione;

public interface AzioneRepository extends JpaRepository<Azione, String>{

	List<Azione> findByAttivita(Attivita attivita, Sort sort);

}
