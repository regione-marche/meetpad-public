package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.model.RegistroDocumentoTipo;

public interface RegistroDocumentoRepository extends JpaRepository<RegistroDocumento, Integer> {
	List<RegistroDocumento> findByDocumentoOrderByDataDesc(Documento documento);
	List<RegistroDocumento> findByDocumentoAndTipoOrderByDataDesc(Documento documento, RegistroDocumentoTipo tipo);
	List<RegistroDocumento> findByDocumentoAndTipoNotOrderByDataDesc(Documento documento, RegistroDocumentoTipo tipo);
	List<RegistroDocumento> findByDocumento(Documento documento);
	
	@Query(nativeQuery = true,value="select r.* from registro_documento r left join documento d on r.fk_documento = d.id_documento " + 
			"where d.fk_conferenza = :conferenceId AND r.tipo = :type AND r.rif_esterno = :externalReference AND d.data_fine is null "
			+ "order by data desc limit 1")
	public RegistroDocumento findByIdConferenceAndTypeAndRifEsterno(Integer conferenceId, String type, String externalReference);
	
	//REstituisce l'ultima riga di ergistro per un dato id documento
	@Query(nativeQuery = true,value="with allrow as (select max(id) as id from registro_documento where fk_documento=?) select rd.* from registro_documento rd inner join allrow aa on aa.id=rd.id")
	public RegistroDocumento  findLastByIdDocumento(Integer idDocumento);
}
