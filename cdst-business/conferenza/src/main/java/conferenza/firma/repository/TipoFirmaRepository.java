package conferenza.firma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.TipoFirma;

public interface  TipoFirmaRepository  extends JpaRepository<TipoFirma, Integer> {

	
	@Query(nativeQuery = true,value="select * from cdst.tipo_firma  where id=?")
	public TipoFirma getTipoFirma(Integer id);
	
}
