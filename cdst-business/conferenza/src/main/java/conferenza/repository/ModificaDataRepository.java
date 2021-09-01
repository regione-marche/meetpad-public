package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.model.ModificaData;
import conferenza.model.TipoData;
import conferenza.protocollo.model.ObserverRegistryListener;

public interface ModificaDataRepository extends JpaRepository<ModificaData, Integer>{
	
	public ModificaData findOneByConferenzaAndTipoData(Conferenza conferenza, TipoData tipoData);
	
	@Query(nativeQuery = true, value="select  COALESCE(max(ordine),0)  from cdst.modifica_data where 1= 1 and fk_conferenza = ?")
	public Integer findMaxOrdineModificaData(int idConferenza);
	
	@Query(nativeQuery = true, value="select md.* from evento e inner join modifica_data md\r\n on e.fk_modifica_data  = md.id_modifica_data where 1 = 1 AND e.fk_documento = ?")
	public List<ModificaData> findModificaDataByIdDocumento(int idDocumento);
	
}
