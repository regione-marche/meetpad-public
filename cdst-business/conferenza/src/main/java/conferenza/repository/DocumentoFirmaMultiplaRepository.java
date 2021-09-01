package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.Documento;
import conferenza.model.DocumentoFirmaMultipla;

public interface DocumentoFirmaMultiplaRepository extends JpaRepository<DocumentoFirmaMultipla, Integer>, JpaSpecificationExecutor<DocumentoFirmaMultipla> {

	Optional<DocumentoFirmaMultipla> findByIdDocumentoAndIdResponsabileFirma(Integer idDocumento, Integer idResponsabileFirma);
	DocumentoFirmaMultipla findByIdDocumentoAndStatoAndIdRegistro(Integer idDocumento, String stato, Integer idRegistro);
	DocumentoFirmaMultipla findByIdDocumentoAndIdRegistro(Integer idDocumento, Integer idRegistro);
}
