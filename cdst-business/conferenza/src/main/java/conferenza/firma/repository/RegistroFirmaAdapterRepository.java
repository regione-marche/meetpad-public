package conferenza.firma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.bean.Firma;



public interface  RegistroFirmaAdapterRepository extends JpaRepository<RegistroFirmaAdapter, Integer> {

	
	@Query(nativeQuery = true,value="select * from registro_firma_adapter where token= ? and stato = 'UNLOCKED' and id_user = ?")
	public RegistroFirmaAdapter getRegistroFirmaAdapterByTokenAndUserId(String token, Integer idUtente);
	/**
	 * 
	 * @param idDocumento
	 * @return
	 */
	@Query(nativeQuery = true,value="select rfa.* from registro_firma_adapter rfa where id_documento=?")
	public RegistroFirmaAdapter getRegistroFirmaAdapter(Integer idDocumento);
	
	/**
	 * 
	 * @param idDocumento
	 * @return
	 */
	@Query(nativeQuery = true,value="select * from registro_firma_adapter where id_documento=? and stato ='LOCKED'")
	public RegistroFirmaAdapter lokedRegistroFirmaAdapter(Integer idDocumento);

	/**
with lastlock as (
select max(ll.id)as id,ll.stato,ll.token
from registro_firma_adapter ll
where  ll.stato='LOCKED'
group by ll.id,ll.stato,ll.token
),lastunlock as (
select max(ll.id) as id,ll.stato,ll.token from registro_firma_adapter ll where  ll.stato='UNLOCKED' group by ll.id,ll.stato,ll.token  
),closed as (
select max(ll.id) as id,ll.stato,ll.token from registro_firma_adapter ll where  ll.stato='CLOSED' group by ll.id,ll.stato,ll.token 
), lastrecord as (
select ll.* from lastlock ll inner join lastunlock uu on uu.token=ll.token left  join closed cc on uu.token=cc.token 
where 1=1
and cc.id is null
and ll.id > uu.id
and ll.token =?
)
select rfa.* from registro_firma_adapter rfa inner join lastrecord rr on rr.id=rfa.id		
	 */
	@Query(nativeQuery = true,value="with lastlock as (\r\n" + 
			"select max(ll.id)as id,ll.stato,ll.token\r\n" + 
			"from registro_firma_adapter ll\r\n" + 
			"where  ll.stato='LOCKED'\r\n" + 
			"group by ll.stato,ll.token\r\n" + 
			"),lastunlock as (\r\n" + 
			"select max(ll.id) as id,ll.stato,ll.token from registro_firma_adapter ll where  ll.stato='UNLOCKED' group by ll.stato,ll.token \r\n" + 
			"),closed as (\r\n" + 
			"select max(ll.id) as id,ll.stato,ll.token from registro_firma_adapter ll where  ll.stato='CLOSED' group by ll.stato,ll.token \r\n" + 
			"), lastrecord as (\r\n" + 
			"select ll.* from lastlock ll inner join lastunlock uu on uu.token=ll.token left  join closed cc on uu.token=cc.token \r\n" + 
			"where 1=1\r\n" + 
			"and cc.id is null\r\n" + 
			"and ll.id < uu.id\r\n" + 
			"and ll.token =?\r\n" + 
			")\r\n" + 
			"select rfa.* from registro_firma_adapter rfa inner join lastrecord rr on rr.id=rfa.id")
	public RegistroFirmaAdapter lastUnlokedRegistroFirmaAdapter(String token);
	
	
	
			
	@Query(nativeQuery = true,value="with maxsessionid as(\r\n" + 
			"select max(rfa.id) as id,rfa.token,rfa.id_documento from registro_firma_adapter rfa where 1=1\r\n" + 
			"group by 	rfa.token,rfa.id_documento\r\n" + 
			")\r\n" + 
			"select rfa.*\r\n" + 
			"from registro_firma_adapter rfa\r\n" + 
			"inner join maxsessionid mm on mm.id=rfa.id\r\n" + 
			"where mm.id_documento=? and not rfa.stato = 'DELETED'")
	public RegistroFirmaAdapter lastRegistroFirmaAdapterByidDoc(Integer idDocumento);	
	
	
	/**
select min(rfa.fk_registro) as  fk_registro from registro_firma_adapter rfa where token='pB4OifshcOutNujj'
	 */
	@Query(nativeQuery = true,value="select min(rfa.fk_registro) as  fk_registro from registro_firma_adapter rfa where token=?")
	public Integer getIdRegistroDocumentiForResourceBeforeSigningAction(String  sessioneFirma);
	
	
/**
with 
maxsessionid as(select max(rfa.id) as id,rfa.token,rfa.id_documento from registro_firma_adapter rfa where 1=1 group by rfa.token,rfa.id_documento )  
,registro_firma as (select rfa.* from registro_firma_adapter rfa inner join maxsessionid mm on mm.id=rfa.id where 1=1 and rfa.stato = 'LOCKED' and rfa.id_user=2 and rfa.id_documento=241)	
select  rfa.crc from registro_firma rfa
 */
	@Query(nativeQuery = true,value="with \r\n" + 
			"maxsessionid as(select max(rfa.id) as id,rfa.token,rfa.id_documento from registro_firma_adapter rfa where 1=1 group by rfa.token,rfa.id_documento )  \r\n" + 
			",registro_firma as (select rfa.* from registro_firma_adapter rfa inner join maxsessionid mm on mm.id=rfa.id where 1=1 and rfa.stato = 'LOCKED' and rfa.id_user=? and rfa.id_documento=?)	\r\n" + 
			"select  rfa.crc from registro_firma rfa")
	public String getLastCRCForUserDoc(Integer idUser,Integer idDocument);
	
	
/**
select distinct rd.fk_documento
from token_registro_documento trd 
inner join registro_documento rd on rd.id=trd.fk_registro
inner join documento dd on dd.id_documento=rd.fk_documento
where 1=1
and trd.token='byeuupfW4Y7vlRcw'	
 */
	@Query(nativeQuery = true,value="select distinct rd.fk_documento\r\n" + 
			"from token_registro_documento trd \r\n" + 
			"inner join registro_documento rd on rd.id=trd.fk_registro\r\n" + 
			"inner join documento dd on dd.id_documento=rd.fk_documento\r\n" + 
			"where 1=1\r\n" + 
			"and trd.token=?")
	public Integer getDocIdByDownloadToken(String downloadToken);
	
	
}
