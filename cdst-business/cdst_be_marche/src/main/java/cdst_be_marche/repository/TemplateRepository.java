package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Template;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.model.TipologiaConferenza;

public interface TemplateRepository extends JpaRepository<Template, Integer>{

	Optional<Template> findByTipologiaConferenzaAndTipoEvento(TipologiaConferenza tipologiaConferenza, TipoEvento tipoEvento);

	List<Template> findByTipoEvento(TipoEvento tipoEvento);

}
