package conferenza.adapder.integrazioni.domus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.domus.model.DomusConferenceProperties;

public interface DomusConferencePropertiesRepository extends JpaRepository<DomusConferenceProperties, Integer>{

	@Query(value="select pp.* from domus_conferenza_properties pp where id_tipo_conferenza=?" ,nativeQuery = true)
	List<DomusConferenceProperties> getParameterByTipoConferenceSpecializzazione(String filter1);

	@Query(value="select pp.* from domus_conferenza_properties pp where id_tipo_conferenza=? and ambiente=?" ,nativeQuery = true)
	List<DomusConferenceProperties> getParameterByTipoConferenceSpecializzazioneAndAmbiente(String tipoConferenza, String ambiente);

	//select * from paleo_tipoconferenza_properties where id_tipo_conferenza='4' and nome_properties='paleo.url' and ambiente='DEV'
	@Query(value="select * from domus_conferenza_properties where id_tipo_conferenza=? and nome_properties=? and ambiente=? and tipo_properties=nome_properties" ,nativeQuery = true)
	List<DomusConferenceProperties> getParameterByTipoConferenceSpecializzazione(String idTipoConferenza,String nomeProperties,String ambiente );

	@Query(value="select * from domus_conferenza_properties where id_tipo_conferenza=? and nome_properties=? and ambiente=? and tipo_properties=?" ,nativeQuery = true)
	List<DomusConferenceProperties> getParameterByTipoConferenceSpecializzazioneRaggruppamento(
			String idTipoConferenza,String nomeProperties, String ambiente, String raggruppamento
	);	
	
	@Query(value="select valore_properties from domus_conferenza_properties where nome_properties=? and ambiente=?" ,nativeQuery = true)
	String getParameterValue(String nomeProperties,String ambiente);
	
	
}
