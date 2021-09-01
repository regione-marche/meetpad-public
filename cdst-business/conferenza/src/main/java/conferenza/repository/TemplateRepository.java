package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Azione;
import conferenza.model.Template;
import conferenza.model.TipoEvento;
import conferenza.model.TipologiaConferenzaSpecializzazione;

public interface TemplateRepository extends JpaRepository<Template, Integer>{

	Optional<Template> findByTipologiaConferenzaSpecializzazioneAndTipoEvento(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione, TipoEvento tipoEvento);

	List<Template> findByTipoEvento(TipoEvento tipoEvento);
	
	Optional<Template> findByTipologiaConferenzaSpecializzazioneAndTipoEventoAndAzione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione, TipoEvento tipoEvento, Azione azione);

	Optional<Template> findFirstByTipologiaConferenzaSpecializzazioneAndTipoEvento(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione, TipoEvento tipoEvento);
}
