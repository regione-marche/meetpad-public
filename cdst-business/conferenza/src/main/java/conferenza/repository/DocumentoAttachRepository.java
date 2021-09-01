package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Documento;
import conferenza.model.DocumentoAttachment;

public interface DocumentoAttachRepository extends JpaRepository<DocumentoAttachment, Integer>, JpaSpecificationExecutor<DocumentoAttachment> {

	@Query(value="select " + 
			"ID_DOCUMENTO, " + 
			"fk_documento, " + 
			"DATA_FINE, " + 
			"AUDIT_ID_USR_MOD, " + 
			"AUDIT_CRT_TIME, " + 
			"AUDIT_MOD_TIME, " + 
			"AUDIT_ID_USR_CRT, " + 
			"NOME, " + 
			"FK_TIPOLOGIA_DOCUMENTO, " + 
			"FK_CATEGORIA_DOCUMENTO, " + 
			"VISIBILITA_RISTRETTA, " + 
			"numero_protocollo, " + 
			"data_protocollo, " + 
			"numero_inventario, " + 
			"competenza_territoriale, " + 
			"data_riunione, " + 
			"FK_ACCREDITAMENTO, " + 
			"FK_CONFERENZA, " + 
			"file_conforme, " + 
			"md5" +
		" from documento_attachment where fk_documento=?", nativeQuery = true)
	List<DocumentoAttachment> findByFkFkDocument(Integer idDocPArent);
	
	@Query(value="select " +
			"ID_DOCUMENTO, " + 
			"fk_documento, " + 
			"DATA_FINE, " + 
			"AUDIT_ID_USR_MOD, " + 
			"AUDIT_CRT_TIME, " + 
			"AUDIT_MOD_TIME, " + 
			"AUDIT_ID_USR_CRT, " + 
			"NOME, " + 
			"FK_TIPOLOGIA_DOCUMENTO, " + 
			"FK_CATEGORIA_DOCUMENTO, " + 
			"VISIBILITA_RISTRETTA, " + 
			"numero_protocollo, " + 
			"data_protocollo, " + 
			"numero_inventario, " + 
			"competenza_territoriale, " + 
			"data_riunione, " + 
			"FK_ACCREDITAMENTO, " + 
			"FK_CONFERENZA, " + 
			"file_conforme, " + 
			"md5" +
		" from documento_attachment where fk_documento=?", nativeQuery = true)
	List<Documento> findDocumentsByFkDocument(Integer idDocPArent);


}
