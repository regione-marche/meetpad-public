package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer>, JpaSpecificationExecutor<Evento>{

	List<Evento> findByConferenza(Conferenza conferenza, Pageable pageable);

}
