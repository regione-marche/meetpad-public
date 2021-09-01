package conferenza.firma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.firma.model.bean.Firma;

public interface FirmaRepository extends JpaRepository<Firma, Integer> {

	@Query(nativeQuery = true,value="with \r\n" + 
			"maxsessionid as(select max(rfa.id) as id,rfa.token,rfa.id_documento from registro_firma_adapter rfa where 1=1 group by rfa.token,rfa.id_documento )  \r\n" + 
			",registro_firma as (select rfa.* from registro_firma_adapter rfa inner join maxsessionid mm on mm.id=rfa.id where 1=1 and not rfa.stato = 'DELETED'	)	\r\n" + 
			"select distinct \r\n" + 
			"rd.fk_documento as id_documento,\r\n" + 
			"2 as fk_tipo_firma,\r\n" + 
			"rfa.token as sessione_firma ,\r\n" + 
			"rfa.shot, \r\n" + 
			"rfa.id_user ,\r\n" + 
			"rfa.crc,\r\n" + 
			"CASE  WHEN rfa.stato is NULL THEN 'UNLOCKED' ELSE rfa.stato END STATO\r\n" + 
			",dd.fk_conferenza as id_conferenza "+
			"from token_registro_documento trd \r\n" + 
			"inner join registro_documento rd on rd.id=trd.fk_registro\r\n" + 
			"inner join documento dd on dd.id_documento=rd.fk_documento\r\n" + 
			"left join  registro_firma rfa on rfa.id_documento=rd.fk_documento\r\n" + 
			"where 1=1\r\n" + 
			"and dd.fk_conferenza=?")
	public List<Firma> getListFirmaByConference(Integer  idConferenza);
}
