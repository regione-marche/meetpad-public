package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.Persona;

public interface AccreditamentoRepository extends JpaRepository<Accreditamento, Integer>{

	List<Accreditamento> findByPersona(Persona persona);
	
	List<Accreditamento> findByPartecipanteAndPersona(Partecipante partecipante, Persona persona);

	List<Accreditamento> findByPartecipante(Partecipante partecipante);

}
