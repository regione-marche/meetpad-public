package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.CategoriaDocumento;
import conferenza.model.TipoEvento;

public interface CategoriaDocumentoRepository extends JpaRepository<CategoriaDocumento, String>{
	
	@Query(name = "SELECT * FROM cdst.categoria_documento WHERE fk_tipo_evento isNull", nativeQuery = true)
	List<CategoriaDocumento> findByTipoEventoIsNull();
	
	List<CategoriaDocumento> findByTipoEvento(TipoEvento tipoEvento);

}
