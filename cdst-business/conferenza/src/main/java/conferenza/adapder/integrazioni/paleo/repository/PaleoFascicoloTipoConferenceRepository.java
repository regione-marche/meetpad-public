package conferenza.adapder.integrazioni.paleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.paleo.model.PaleoFascicoloTipoConference;
import conferenza.adapder.integrazioni.paleo.model.PaleoTipoConferenceProperties;



public interface PaleoFascicoloTipoConferenceRepository   extends JpaRepository<PaleoFascicoloTipoConference, Integer>{

	@Query(value="select pp.nome_fascicolo from paleo_fascicolo_tipoconferenza pp where id_fascicolo=? and id_tipo_conferenza=?" ,nativeQuery = true)
    String getNomeFascicolo(String fkPaleoFascicolo,String tipoConferenzaAssociata);

	
	@Query(value="select pp.id_tipo_conferenza from paleo_fascicolo_tipoconferenza pp where id_fascicolo=? and id_tipo_conferenza=?" ,nativeQuery = true)
	String getTipoConferenza(String fkPaleoFascicolo,String tipoConferenzaAssociata );

	@Query(value="select pp.id_tipo_conferenza from paleo_fascicolo_tipoconferenza pp where id=?" ,nativeQuery = true)
	String getTipoConferenzaById(Integer idTipoConferenzaAssociata);
}
