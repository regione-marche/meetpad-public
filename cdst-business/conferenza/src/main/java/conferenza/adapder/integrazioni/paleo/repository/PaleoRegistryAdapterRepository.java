package conferenza.adapder.integrazioni.paleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;


public interface PaleoRegistryAdapterRepository  extends JpaRepository<PaleoRegistryAdapter, Integer>{
	
	@Query(value="select id from paleo_fascicolo_tipoconferenza where id_fascicolo=? and id_tipo_conferenza=?  " ,nativeQuery = true)
	Integer getIdFascicoloTipoConferenza(String fascicolo,String tipoconferenza);
	
	/**
	 * 
	 * @param filter1
	 * @return
	 */
	//@Deprecated
	//@Query(value="select * from paleo_registry_adapter where id_paleo_doc is NOT  null and fk_paleo_fascicolo=?" ,nativeQuery = true)
	//List<PaleoRegistryAdapter> getlistPaleoAdapterByFascicolo(String filter1);	
	
	// xmf: finds existing registry adapter
	@Query(value="SELECT * " + 
			"	FROM paleo_registry_adapter" + 
			"	WHERE id_paleo_doc=? AND id_fascicolo_tipoconferenza=?" ,nativeQuery = true)
	PaleoRegistryAdapter getPaleoAdapterByIdPaleoDocAndIdFascicoloTipoconferenza(Integer IdPaleoDoc, Integer IdFascicoloTipoconferenza);
	
	@Query(value="select * from paleo_registry_adapter where id_paleo_doc is NOT  null and fk_paleo_fascicolo=? and ID_FASCICOLO_TIPOCONFERENZA=?" ,nativeQuery = true)
	List<PaleoRegistryAdapter> getlistPaleoAdapterByFascicolo(String filter1,Integer filter2);	
	
	
	//select rd.id as id_registro from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza where tipo='PALEO' and rif_esterno='5997' and cc.id_conferenza='239'
	@Query(value="with rifunici as (select rd.rif_esterno as id_registro from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza where tipo='PALEO' group by rd.rif_esterno having count(rif_esterno)=1) select rd.id as id_registro ,rif_esterno from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza inner join rifunici rr on rr.id_registro=rd.rif_esterno where tipo='PALEO' and rif_esterno=? and cc.id_conferenza=? " ,nativeQuery = true)
	Integer getIdRegistroByFascicoloConferenza(String sIdPaleo,Integer iIdConferenza);

	
	@Query(value="select ra.id_allegato from paleo_registry_allegati_adapter ra inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter  inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar) where 1=1 and rd.fk_documento=?",nativeQuery = true)
	Integer getIdAllegatoByIdDocument(Integer idDocument);
	
	@Query(value="select ra.id_allegato from paleo_registry_allegati_adapter ra inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter  inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar)  where 1=1 and rd.id=?",nativeQuery = true)
	Integer getIdAllegatoByIdRegistro(Integer idRegistro);	

	
	@Query(value="select dd.fk_conferenza from documento dd  inner join registro_documento rd on dd.id_documento=rd.fk_documento where   rd.id=?",nativeQuery = true)
	Integer getIdConferenceByIdRegistro(Integer idRegistro);

	@Query(value="select dd.fk_conferenza from documento dd  inner join registro_documento rd on dd.id_documento=rd.fk_documento where   rd.fk_documento=?",nativeQuery = true)
	Integer getIdConferenceByIdDocument(Integer idDocument);
	
	@Query(value="select distinct pa.fk_paleo_fascicolo || ' | '|| pa.id_paleo_doc || ' | '|| pa.paleo_protocollo_data  || ' | '|| pa.paleo_tipo_protocollo from paleo_registry_allegati_adapter ra  inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar)  where 1=1 and rd.fk_documento=?",nativeQuery = true)
	String getRaggruppamentoByIdDocument(Integer idDocument);
	
	@Query(value="select pra.* from paleo_registry_adapter pra where pra.paleo_segnatura_protocollo=?",nativeQuery = true)	
	Integer getIdAdapterBySegnaturaProtocollo(String codiceDocumento);
	
	@Query(value=" select pra.* from paleo_registry_adapter pra where pra.id_paleo_numerodoc=?",nativeQuery = true)
	Integer getIdAdapterByDocumentNumber(Integer codiceDocumento);
	
	@Deprecated
	@Query(value="select pa.fk_paleo_fascicolo from paleo_registry_allegati_adapter ra inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar)  where 1=1 and rd.rif_esterno=?" ,nativeQuery = true)
	String getFascicoloByRifExternoRegistro(String rifExterno);
	
	/**
with paleo as (
	select ptc.id_fascicolo,cc.id_conferenza
	from registro_documento rd 
	inner join documento dd on dd.id_documento=rd.fk_documento
	inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza 
	inner join paleo_fascicolo_tipoconferenza ptc on ptc.id_tipo_conferenza=cc.fk_tipologia_conferenza_specializzazione	
	inner join paleo_registry_adapter pra on pra.id_fascicolo_tipoconferenza=ptc.id inner join paleo_registry_allegati_adapter aa on aa.fk_paleo_adapter=pra.id
),confenzapp as (
	select ptc.id_fascicolo,cc.id_conferenza,rd.id
	from registro_documento rd 
	inner join documento dd on dd.id_documento=rd.fk_documento
	inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza 
	inner join paleo_fascicolo_tipoconferenza ptc on ptc.id_tipo_conferenza=cc.fk_tipologia_conferenza_specializzazione	
)
select distinct  pp.id_fascicolo from paleo pp inner join confenzapp cc on cc.id_conferenza=pp.id_conferenza where cc.id= 398


	 * @param idRegistro
	 * @return
	 */
	@Query(value="with paleo as (select ptc.id_fascicolo,cc.id_conferenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza inner join paleo_fascicolo_tipoconferenza ptc on ptc.id_tipo_conferenza=cc.fk_tipologia_conferenza_specializzazione inner join paleo_registry_adapter pra on pra.id_fascicolo_tipoconferenza=ptc.id inner join paleo_registry_allegati_adapter aa on aa.fk_paleo_adapter=pra.id),confenzapp as (select ptc.id_fascicolo,cc.id_conferenza,rd.id from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza inner join paleo_fascicolo_tipoconferenza ptc on ptc.id_tipo_conferenza=cc.fk_tipologia_conferenza_specializzazione)select distinct  pp.id_fascicolo from paleo pp inner join confenzapp cc on cc.id_conferenza=pp.id_conferenza where cc.id= ?" ,nativeQuery = true)
	List<String> getFascicoloByIdRegistro(Integer idRegistro);
	
	
	//@Query(value="select pa.id,rd.id as fk_registro_documenti,pa.id_paleo_doc,pa.id_paleo_numerodoc,pa.id_paleo_protocollo,pa.fk_paleo_fascicolo,pa.id_paleo_registro,pa.paleo_tipo_protocollo,pa.paleo_installazione,pa.paleo_installazione,pa.paleo_segnatura_protocollo from paleo_registry_allegati_adapter ra inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar)  where 1=1 and rd.rif_esterno=?",nativeQuery = true)
	@Query(nativeQuery = true)
	PaleoRegistryAdapter getAdapterByByRifExternoREgistro(String rifExterno);
	
	/**
	 * 
	 * @param idRegistro
	 * @return
	 */
	@Query(value="select cc.fk_tipologia_conferenza_specializzazione from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc  on dd.fk_conferenza = cc.id_conferenza where 1=1 and rd.id=?" ,nativeQuery = true)
	String getTipologiaConferenzaByIdRegistro(Integer idRegistro);
	
	/**
	 * 
	 * @param idConferenza
	 * @return
	 */
	@Query(value="select DISTINCT pa.* " + 
			"    from" + 
			"        paleo_registry_allegati_adapter ra  " + 
			"		inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter " + 
			"		inner join registro_documento rd  on rd.rif_esterno=cast(ra.id as varchar)  " + 
			"		inner join documento d on d.id_documento=rd.fk_documento	" + 
			"    where d.fk_conferenza=?" ,nativeQuery = true)
	List<PaleoRegistryAdapter> getRegistroForConference(Integer idConference);
	
}
