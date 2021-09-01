package conferenza.adapder.integrazioni.domus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterProtocolli;



public interface DomusRegistryAdapterProtocolliRepository extends JpaRepository<DomusRegistryAdapterProtocolli, Integer>{

	public DomusRegistryAdapterProtocolli findByFkTestataAndIdDocumentoAndSignature(Integer fkTestata, String idDocumento, String signature);
	
}
