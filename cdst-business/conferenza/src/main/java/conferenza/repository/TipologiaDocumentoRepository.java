package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.SezioneDocumentazione;
import conferenza.model.TipologiaDocumento;

public interface TipologiaDocumentoRepository extends JpaRepository<TipologiaDocumento, String> {

	List<TipologiaDocumento> findByFlagUploadConsolle(Boolean flag);

	List<TipologiaDocumento> findBySezioneDocumentazione(SezioneDocumentazione sezioneDoc);

}
