package conferenza.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Attivita;
import conferenza.model.Azione;

public interface AzioneRepository extends JpaRepository<Azione, String>{

	List<Azione> findByAttivita(Attivita attivita, Sort sort);

}
