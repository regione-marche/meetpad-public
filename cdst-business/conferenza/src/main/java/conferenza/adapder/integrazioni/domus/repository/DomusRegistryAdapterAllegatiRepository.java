package conferenza.adapder.integrazioni.domus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.domus.model.DomusConferenceProperties;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterAllegati;



public interface DomusRegistryAdapterAllegatiRepository extends JpaRepository<DomusRegistryAdapterAllegati, Integer>{

	
	/**
	 * 
  select distinct tt.id_conferenza, rd.id registro,rd.rif_esterno, tt.id testata, pp.id protocollo,cast (aa.id as character varying(255)) allegato,aa.id_file file, aa.nome_file
  from cdst.domus_registry_adapter_allegati aa  
  inner join domus_registry_adapter_protocolli pp on pp.id=aa.fk_protocollo 
  inner join domus_registry_adapter_testa tt on tt.id=pp.fk_testata  
  inner  join registro_documento rd on (rd.rif_esterno= cast (aa.id as character varying(255)) and rd.rif_esterno is not null)
  where 1=1
  --and aa.id_file=36616
  --and pp.id_documento='35407' 
  and  rd.id =263
	 * 
	 * @param idRegistro
	 * @return
	 */
	@Query(value="select distinct aa.id_file from cdst.domus_registry_adapter_allegati aa inner join domus_registry_adapter_protocolli pp on pp.id=aa.fk_protocollo inner join domus_registry_adapter_testa tt on tt.id=pp.fk_testata inner join registro_documento rd on (rd.rif_esterno= cast (aa.id as character varying(255)) and rd.rif_esterno is not null) where 1=1  and  rd.id =?" ,nativeQuery = true)
	public Integer  getidFielFromIdRegistro(Integer idRegistro);	

	//select pp.signature from domus_registry_adapter_protocolli pp inner join domus_registry_adapter_allegati aa on aa.fk_protocollo=pp.id inner join registro_documento rd on rd.rif_esterno=aa.id::text where 1=1 and rd.tipo='ALLEGATIDOMUS' and rd.id=918
	@Query(value="select pp.signature from domus_registry_adapter_protocolli pp inner join domus_registry_adapter_allegati aa on aa.fk_protocollo=pp.id inner join registro_documento rd on rd.rif_esterno=aa.id::::text where 1=1 and rd.tipo='ALLEGATIDOMUS' and rd.id=?" ,nativeQuery = true)
	public String  getRaggruppamentoByIdRegistro(Integer idRegistro);	
	
	public DomusRegistryAdapterAllegati findByFkProtocolloAndIdFile(Integer fkProtocollo, Integer idFile);
	
}
