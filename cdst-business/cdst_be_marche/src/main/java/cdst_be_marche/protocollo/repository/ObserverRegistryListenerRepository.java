package cdst_be_marche.protocollo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cdst_be_marche.protocollo.model.ObserverRegistryListener;


public interface ObserverRegistryListenerRepository extends JpaRepository<ObserverRegistryListener, Integer> {

	@Query(nativeQuery = true)
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol(String par1);
	
	@Query(nativeQuery = true,value="select distinct class  from cdst.observer_registry")
	public List<String> getRegisterdAsincronousTask();
}
