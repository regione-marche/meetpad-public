package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, String>{
	
	@Query(value = "SELECT * FROM cdst.tipo_evento WHERE codice NOT IN ('14', '15', '16', '17', '18', '19')", nativeQuery = true)
	List<TipoEvento> findAllTypeNotIn1419();

}
