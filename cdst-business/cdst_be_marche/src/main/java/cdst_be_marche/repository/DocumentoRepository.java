package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.TipologiaDocumento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer>, JpaSpecificationExecutor<Documento> {

	Optional<Documento> findByTipologiaDocumentoAndOwner(TipologiaDocumento tipologiaDocumento, Accreditamento owner);
	
	List<Documento> findByTipologiaDocumentoAndConferenza(TipologiaDocumento tipologiaDocumento, Conferenza conferenza);

	Optional<Documento> findByConferenzaAndTipologiaDocumento(Conferenza conferenza,
			TipologiaDocumento tipologiaDocumento);

}
