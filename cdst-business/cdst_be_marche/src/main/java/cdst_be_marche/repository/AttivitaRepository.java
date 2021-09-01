package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Attivita;
import cdst_be_marche.model.TipologiaPratica;
import cdst_be_marche.model.bean._Typological;

public interface AttivitaRepository extends JpaRepository<Attivita, String>{

	Attivita findByCodice(String codiceAttivita);

	List<Attivita> findByTipologiaPratica(TipologiaPratica tipologiaPratica, Sort sort);

}
