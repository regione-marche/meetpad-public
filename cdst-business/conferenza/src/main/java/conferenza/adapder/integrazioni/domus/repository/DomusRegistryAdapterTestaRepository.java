package conferenza.adapder.integrazioni.domus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.domus.model.DomusConferenceProperties;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterTesta;



public interface DomusRegistryAdapterTestaRepository extends JpaRepository<DomusRegistryAdapterTesta, Integer>{

	@Query(value="select tt.* from domus_registry_adapter_testa tt where tt.fk_codice_comune=? and id_pratica=?" ,nativeQuery = true)
	DomusRegistryAdapterTesta getExistingTestata(String filter1,Integer filter2);
	
	@Query(value="select tt.* from domus_registry_adapter_testa tt where id_pratica=?" ,nativeQuery = true)
	DomusRegistryAdapterTesta getExistingTestataByIdPratica(Integer filter2);
	
	DomusRegistryAdapterTesta findByIdConferenza(Integer idConferenza);
}
