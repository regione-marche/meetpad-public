package conferenza.documentazionecondivisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.documentazionecondivisa.model.OOAdapter;
import conferenza.model.Documento;



public interface OOAdapterRepository extends JpaRepository<OOAdapter, Integer> {
	

	@Query(nativeQuery = true,value="select * from oo_adapter where fk_registro=? order by id desc")
	public List<OOAdapter> getRegisterdAsincronousTask(Integer idREgistro);
	
	
	@Query(nativeQuery = true,value="select rd.fk_documento from registro_documento rd where rd.id=? order by id desc")
	public Integer getFkDocumentoByRegistroDocumento(Integer idREgistro);

	//select * from oo_adapter where fk_documento =619
	@Query(nativeQuery = true,value="select * from oo_adapter where fk_documento =?")
	public OOAdapter getAdapterByIdDoc(Integer idDocument);
	
	@Query(nativeQuery = true,value="select dd.nome from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=?")
    public String getNomeDocumentoByToken(String token);
	
	@Query(nativeQuery = true,value="select dd.id_documento from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento left  join token_registro_documento trd on trd.fk_registro=rd.id where 1=1 and trd.token=?")
    public Integer getIdDocumentoByToken(String token);	
}
