package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.TipologiaDocumento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer>, JpaSpecificationExecutor<Documento> {

	Optional<Documento> findByTipologiaDocumentoAndOwner(TipologiaDocumento tipologiaDocumento, Accreditamento owner);
	
	List<Documento> findByTipologiaDocumentoAndConferenza(TipologiaDocumento tipologiaDocumento, Conferenza conferenza);

	Optional<Documento> findByConferenzaAndTipologiaDocumento(Conferenza conferenza, TipologiaDocumento tipologiaDocumento);
	
	List<Documento> findByConferenza(Conferenza conferenza);

	List<Documento> findByConferenzaAndDataFineIsNotNull(Conferenza conferenza);

	List<Documento> findByOwner(Accreditamento accreditamento);
	
	List<Documento> findByfkDocParent(Integer idDocumento);
	
	@Query(nativeQuery = true,value="with max_registro as (select max(id) as id_registro, fk_documento as id_documento from registro_documento group by fk_documento )" +
			"select df.fk_responsabile_firma, df.fk_stato ,d.* " +
			"from cdst.documento d inner join cdst.documento_firma_multipla df on d.id_documento = df.fk_documento "+
			"join max_registro r on r.id_documento=df.fk_documento  and r.id_registro=df.fk_registro "+
			"where df.fk_stato = :statoFirma and df.fk_responsabile_firma = :idResponsabile and d.data_fine is null")
	List<Documento> findDocumentoByResposabileFirmaAndStato(String statoFirma, Integer idResponsabile );
	
	
	/**
	select coalesce(Count(*),0) numRegistri
		from registro_documento rd 
	where 1 = 1
		and rd.fk_documento = ?
	 */
	@Query(nativeQuery = true,value="select coalesce(Count(*),0) numRegistri\r\n"
				+ "	from registro_documento rd \r\n"
				+ "	where 1 = 1\r\n"
				+ "	and rd.fk_documento = ?")
	public Integer getCountRegisterForDocument(Integer idDocumento);
}
