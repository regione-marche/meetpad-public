package conferenza.protocollo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.protocollo.model.ObserverRegistryListener;


public interface ObserverRegistryListenerRepository extends JpaRepository<ObserverRegistryListener, Integer> {

	@Query(nativeQuery = true, value="select * from cdst.view_submit_to_external_protocol where codice=? and fk_conferenza_specializzazione is null")
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol(String codice);
	
	@Query(nativeQuery = true, value="select * from cdst.view_submit_to_external_protocol where codice=?")
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolGlobal(String codice);
	
	@Query(nativeQuery = true, value="select * from cdst.view_submit_to_external_protocol where id_documento=?")
	public List<ObserverRegistryListener> findAllDocuments(Integer id_documento);
	
	@Query(nativeQuery = true,value="select distinct class,priority from cdst.observer_registry order by priority")  
	public List<Map<String,String>> getRegisterdAsincronousTask();
	
	@Query(nativeQuery = true, value="select * from cdst.view_submit_to_external_protocol where codice=? and NOT(fk_conferenza_specializzazione IS NULL)")
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolAndSpecializzazione(String par1);
	
}
