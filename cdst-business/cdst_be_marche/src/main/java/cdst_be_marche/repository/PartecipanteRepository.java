package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RuoloPartecipante;

public interface PartecipanteRepository extends JpaRepository<Partecipante, Integer>{

	List<Partecipante> findByEnte(Ente ente);

	List<Partecipante> findByConferenza(Conferenza conferenza);

	List<Partecipante> findByRuoloPartecipanteAndConferenza(RuoloPartecipante ruoloPartecipante,
			Conferenza findByIdConferenza);

}
