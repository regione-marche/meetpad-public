package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.Impresa;

public interface ImpresaRepository extends JpaRepository<Impresa, Integer>, JpaSpecificationExecutor<Impresa>{

	Optional<Impresa> findByCodiceFiscaleOrPartitaIvaIgnoreCase(String cf, String partitaIva);

	Optional<Impresa> findByPartitaIvaIgnoreCase(String partitaIva);
	
	Optional<Impresa> findByCodiceFiscaleIgnoreCase(String codiceFiscale);

	List<Impresa> findByDenominazioneContainingIgnoreCase(String key, Sort sort);

}
