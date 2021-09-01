package conferenza.adapder.integrazioni.paleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.paleo.model.PaleoTipoConferenceProperties;


public interface PaleoTipoConferencePropertiesRepository extends JpaRepository<PaleoTipoConferenceProperties, Integer>{

	@Query(value="select pp.* from paleo_tipoconferenza_properties pp where id_tipo_conferenza=?" ,nativeQuery = true)
	List<PaleoTipoConferenceProperties> getParameterByTipoConferenceSpecializzazione(String filter1);

	@Query(value="select pp.* from paleo_tipoconferenza_properties pp where id_tipo_conferenza=? and ambiente=?" ,nativeQuery = true)
	List<PaleoTipoConferenceProperties> getParameterByTipoConferenceSpecializzazioneAndAmbiente(String tipoConferenza, String ambiente);

	//select * from paleo_tipoconferenza_properties where id_tipo_conferenza='4' and nome_properties='paleo.url' and ambiente='DEV'
	@Query(value="select * from paleo_tipoconferenza_properties where id_tipo_conferenza=? and nome_properties=? and ambiente=? and tipo_properties=nome_properties" ,nativeQuery = true)
	List<PaleoTipoConferenceProperties> getParameterByTipoConferenceSpecializzazione(String idTipoConferenza,String nomeProperties,String ambiente );

	@Query(value="select * from paleo_tipoconferenza_properties where id_tipo_conferenza=? and nome_properties=? and ambiente=?" ,nativeQuery = true)
	List<PaleoTipoConferenceProperties> getByIdTipoConferenzaAndNomePropertiesAndAmbiente(String idTipoConferenza,String nomeProperties,String ambiente );

	@Query(value="select * from paleo_tipoconferenza_properties where id_tipo_conferenza=? and nome_properties=? and ambiente=? and tipo_properties=?" ,nativeQuery = true)
	List<PaleoTipoConferenceProperties> getParameterByTipoConferenceSpecializzazioneRaggruppamento(
			String idTipoConferenza,String nomeProperties, String ambiente, String raggruppamento
	);
	
	@Query(value="select valore_properties from paleo_tipoconferenza_properties where nome_properties=? and ambiente=?" ,nativeQuery = true)
	String getParameterValue(String nomeProperties,String ambiente);
	
	PaleoTipoConferenceProperties findByPropertiesName(String name);
}
