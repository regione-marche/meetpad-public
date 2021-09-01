package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipologiaConferenza;
import conferenza.model.TipologiaConferenzaDataDefinizione;

public interface TipologiaConferenzaDataDefinizioneRepository
		extends JpaRepository<TipologiaConferenzaDataDefinizione, Integer> {

	Optional<TipologiaConferenzaDataDefinizione> findByTipologiaConferenzaAndCampoDataCalcolato(
			TipologiaConferenza tipologiaconferenza, String campoDataCalcolato);

}
