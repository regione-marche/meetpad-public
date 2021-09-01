package conferenza.documentazionecondivisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import conferenza.documentazionecondivisa.model.OOAdapterVersioni;


public interface OOAdapterVersioniRepository extends JpaRepository<OOAdapterVersioni, Integer> {

	//select * from oo_adapter_versioni where token ='PBqfVaOva319HK2X' order by id desc;
	@Query(nativeQuery = true,value="select * from oo_adapter_versioni where token = ? order by id desc")
	public List<OOAdapterVersioni> getListVersioniByToken(String  token);

	@Query(nativeQuery = true,value="select rd.id from token_registro_documento trd inner join registro_documento rd on rd.id=trd.fk_registro where 1=1 and trd.token=?")
	public Integer getFkRegistroByToken(String  token);	
	
	//@Query(nativeQuery = true,value="select rd.id from token_registro_documento trd inner join registro_documento rd on rd.id=trd.fk_registro where 1=1 and trd.token=?")
	//public Integer getFkRegistroByToken(String  token);	
	
	
	@Query(nativeQuery = true,value="select distinct rd.fk_documento from token_registro_documento trd inner join registro_documento rd on rd.id=trd.fk_registro where 1=1 and trd.token=?")
	public Integer getIdDocumentoByToken(String  token);	
	
	
	//select dd.nome  from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1  and trd.token='PBqfVaOva319HK2X' order by id desc	
	@Query(nativeQuery = true,value="select dd.nome  from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1  and trd.token=? order by id desc")
	public String  getNomeDocumentByToken(String  token);
	
	
	@Query(nativeQuery = true,value="with testata as ( select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=? order by id desc), ultimaversione as (select max(av.id) id from  oo_adapter_versioni  av inner join oo_adapter aa on aa.id=av.fk_adapter inner join testata trd on trd.id_documento=aa.fk_documento) select av.* from  oo_adapter_versioni av inner join ultimaversione uu on uu.id=av.id")
	public OOAdapterVersioni  getLastVersioneByToken(String  token);
	
	@Deprecated
	@Query(nativeQuery = true,value="with testata as (select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=? order by id desc),registromax as (select max(rd.id ) id from registro_documento rd  inner join testata tt on  rd.fk_documento=tt.id_documento and rd.tipo='FS' order by id desc),tkregistromax as (select max( rd.id_token) id_token from token_registro_documento rd inner join registromax mm on mm.id=rd.fk_registro),tkregistro as (select rd.* from token_registro_documento rd inner join tkregistromax mm on  rd.id_token=mm.id_token)select token from tkregistro")
	public String  getLastDownloadableTokenByCurrentToken(String  token);
	
	@Query(nativeQuery = true,value="with testata as(select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=? order by id desc),lastvooersione as (select max(vv.id) as id from oo_adapter_versioni vv inner join oo_adapter aa on aa.id=vv.fk_adapter inner join testata tt on aa.fk_documento=tt.id_documento where id_oo_file is not null order by id asc)select id_oo_file from oo_adapter_versioni vv inner join lastvooersione ll on ll.id=vv.id")
	public Integer  getLastIdOOFileByCurrentToken(String  token);
	
	/**
	 * a prescindere viene controllato che il
	 * token_registro_documento.token sia l'ultimo..
	 * altrimenti significa che qualcuno ha usato quel TOKEN!?!?!?!
	 * TODO gestire il locke dei file e la messaggistica..  
	 * @param token
	 * @return
	 */	
	@Query(nativeQuery = true,value="with testata as (select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=? order by id desc), ultimaversione as (select max(av.id) id ,aa.fk_documento from oo_adapter_versioni av inner join oo_adapter aa on aa.id=av.fk_adapter inner join testata trd on trd.id_documento=aa.fk_documento group by fk_documento) select trd.token from oo_adapter_versioni av inner join 	ultimaversione uu on uu.id=av.id inner join documento dd on dd.id_documento=uu.fk_documento inner join registro_documento   rd on dd.id_documento=rd.fk_documento inner  join token_registro_documento trd on trd.fk_registro=rd.id and av.url_oo like '%'|| trd.token order by trd.id_token desc")
	public String  getLastGeneratedTokenByKnownToken(String  token);
	
	/**
	 * Ultimo token non ancora versionato..per permettere la scrittura collaborativa..
		with 
		testata as (select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id 
					where 1=1 and trd.token='b7fALTseNp1AHQbY' order by id desc)
		,registromax as (select min(trd.id_token ) id_token from registro_documento rd  inner join testata tt on  rd.fk_documento=tt.id_documento inner join token_registro_documento trd on trd.fk_registro=rd.id  left join oo_adapter_versioni vv on vv.token =trd.token where trd.fk_registro is not null and vv.token is null)
		,tkregistromax as (select rd.token from token_registro_documento rd inner join registromax mm on mm.id_token=rd.id_token)
		select * from tkregistromax
	 */
	@Query(nativeQuery = true,value="with testata as (select dd.id_documento,dd.nome ,rd.id,rd.rif_esterno,rd.tipo, rd.fonte,trd.token,trd.scadenza from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=? order by id desc),registromax as (select min(trd.id_token ) id_token from registro_documento rd  inner join testata tt on  rd.fk_documento=tt.id_documento inner join token_registro_documento trd on trd.fk_registro=rd.id  left join oo_adapter_versioni vv on vv.token =trd.token where trd.fk_registro is not null and vv.token is null),tkregistromax as (select rd.token from token_registro_documento rd inner join registromax mm on mm.id_token=rd.id_token) select * from tkregistromax")
	public String  getNotVersionedTokenByKnownToken(String  token);
	
	
	
}
