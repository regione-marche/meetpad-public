package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Conferenza;
import conferenza.model.ContattoSupporto;

public interface ContattoSupportoRepository extends JpaRepository<ContattoSupporto, Integer>{

	List<ContattoSupporto> findByConferenza(Conferenza conferenza);

}
