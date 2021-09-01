package conferenza.mediateca.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.mediateca.model.Mediateca;


public interface MediatecaRepository extends JpaRepository<Mediateca, Integer> {

  /**
   * 	
   * @param filtro1 
   * @param filtro2 = filtro1 ..
   * @return
   */
  @Query(nativeQuery = true,value="select cc.id_conferenza,\r\n" + 
			"CAST (cc.oggetto_determinazione AS character varying(255)) as oggetto,\r\n" + 
			"dd.id_documento, dd.nome as nome_documento,\r\n" + 
			"rr.id as id_registro,rr.rif_esterno as path,\r\n" + 
			"'' token,\r\n" + 
			"dd.md5\r\n" + 
			"from documento dd\r\n" + 
			"inner join tipologia_documento tt on dd.fk_tipologia_documento=tt.codice\r\n" + 
			"inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza\r\n" + 
			"inner join registro_documento rr on rr.fk_documento=dd.id_documento\r\n" + 
			"where 1=1\r\n" + 
			"and tt.codice in ('VIDEO')\r\n" + 
			"and (upper(dd.nome) like upper(concat('%',?,'%')) or upper(cc.oggetto_determinazione) like upper(concat('%',?,'%')))\r\n" + 
			"and rr.tipo='FS'", 
			countQuery = "select count(*) from (select cc.id_conferenza,\r\n" + 
					"CAST (cc.oggetto_determinazione AS character varying(255)) as oggetto,\r\n" + 
					"dd.id_documento, dd.nome as nome_documento,\r\n" + 
					"rr.id as id_registro,rr.rif_esterno as path,\r\n" + 
					"'' token\r\n" + 
					"from documento dd\r\n" + 
					"inner join tipologia_documento tt on dd.fk_tipologia_documento=tt.codice\r\n" + 
					"inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza\r\n" + 
					"inner join registro_documento rr on rr.fk_documento=dd.id_documento\r\n" + 
					"where 1=1\r\n" + 
					"and tt.codice in ('VIDEO')\r\n" + 
					"and (upper(dd.nome) like upper(concat('%',?,'%')) or upper(cc.oggetto_determinazione) like upper(concat('%',?,'%')))\r\n" + 
					"and rr.tipo='FS')as totaleElementi")
	public Page<Mediateca> getListRecordMediateca(String  filtro1,String  filtro2, Pageable page);
}
