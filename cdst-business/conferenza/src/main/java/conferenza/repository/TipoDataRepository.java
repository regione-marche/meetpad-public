package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipoData;

public interface TipoDataRepository extends JpaRepository<TipoData, String>{
	
}
