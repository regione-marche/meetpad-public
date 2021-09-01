package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Modello;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface ModelloRepository extends JpaRepository<Modello, String>{

	List<Modello> findByTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConfSpec);

	List<Modello> findByDescrizione(String string);

}
