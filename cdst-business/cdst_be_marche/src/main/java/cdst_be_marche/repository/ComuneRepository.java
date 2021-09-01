package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Comune;
import cdst_be_marche.model.Provincia;

public interface ComuneRepository extends JpaRepository<Comune, String>{

	List<Comune> findByProvincia(Provincia prov, Sort sort);
	
	List<Comune> findByProvinciaAndDescrizioneContainingIgnoreCase(Provincia prov, String search, Sort sort);

	List<Comune> findByDescrizioneContainingIgnoreCase(String search, Sort sort);

}
