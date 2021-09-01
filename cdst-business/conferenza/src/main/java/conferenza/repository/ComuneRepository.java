package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Comune;
import conferenza.model.Provincia;

public interface ComuneRepository extends JpaRepository<Comune, String>{

	List<Comune> findByProvincia(Provincia prov, Sort sort);
	
	List<Comune> findByProvinciaAndDescrizioneContainingIgnoreCase(Provincia prov, String search, Sort sort);

	List<Comune> findByDescrizioneContainingIgnoreCase(String search, Sort sort);
	
	List<Comune> findByDescrizioneIgnoreCase(String search, Sort sort);

	Optional<Comune> findByDescrizioneIgnoreCaseAndProvincia(String comune, Provincia provincia);

}
