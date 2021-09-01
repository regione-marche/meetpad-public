package conferenza.adapder.integrazioni.paleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAllegatiAdapter;


/**

 * @author guideluc
 *
 */
public interface PaleoRegistryAllegatiAdapterRepository extends JpaRepository<PaleoRegistryAllegatiAdapter, Integer>{
	
	@Query(value="select ra.* from paleo_registry_allegati_adapter ra left join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter where 1=1 and ra.id_allegato is not null and pa.fk_paleo_fascicolo=?" ,nativeQuery = true)
	List<PaleoRegistryAllegatiAdapter> getlistAllegatiPaleoAdapter(String fascicolo);
	
	@Query(value="select ra.id,ra.fk_paleo_adapter,ra.id_allegato,ra.nome_allegato from paleo_registry_allegati_adapter ra left join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter  where 1=1 and ra.id_allegato is not null and pa.id=?" ,nativeQuery = true)
	List<PaleoRegistryAllegatiAdapter> getlistAllegatiPaleoByIdAdapter(Integer idAdapter);
	
	@Query(value="select ra.* " + 
			"    from" + 
			"        paleo_registry_allegati_adapter ra  " + 
			"		inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter " + 
			"		inner join registro_documento rd  on rd.rif_esterno=cast(ra.id as varchar)  " + 
			"		inner join documento d on d.id_documento=rd.fk_documento	" + 
			"    where d.fk_conferenza=?" ,nativeQuery = true)
	List<PaleoRegistryAllegatiAdapter> getAttachmentsForConference(Integer idConference);
	
	@Query(value="	select ra.*" + 
			"	from paleo_registry_allegati_adapter ra " + 
			"		inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter " + 
			"		left join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar) " + 
			"	where rd.id is null and ra.id_allegato is not null and pa.fk_paleo_fascicolo=?" ,nativeQuery = true)
	List<PaleoRegistryAllegatiAdapter> getNotStoredAttachments(String fascicolo);
	
	@Query(value="SELECT count(id_allegato) FROM cdst.paleo_registry_allegati_adapter WHERE id_allegato=?" ,nativeQuery = true)
	Integer isThereAttachmentId(Integer iddoc);

}
