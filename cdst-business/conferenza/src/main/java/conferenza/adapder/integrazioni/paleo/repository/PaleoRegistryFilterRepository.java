package conferenza.adapder.integrazioni.paleo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryFilter;



public interface PaleoRegistryFilterRepository  extends JpaRepository<PaleoRegistryFilter, Integer>{

	
	
	/**
	 * 
	 * @param idRegistro
	 * @return
	 */
	@Query(value="select ff.* from paleo_registry_filter ff where ff.tipofiltro='TIPOCONFERENZA' and ff.codiceriferimento='SCRITTURA-DOCUMENTI-INTERNI-PALEO' and ff.condizione='IN' and ff.fk_tipologia_conferenza=?" ,nativeQuery = true)
	public PaleoRegistryFilter getPaleoRegistryFilterForDocInterniByTipoConferenza(Integer fkTipoConferenza);
	
	
}
