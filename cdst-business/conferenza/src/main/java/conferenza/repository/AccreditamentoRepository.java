package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Partecipante;
import conferenza.model.Persona;

public interface AccreditamentoRepository extends JpaRepository<Accreditamento, Integer>{

	List<Accreditamento> findByPersona(Persona persona);
	
	List<Accreditamento> findByPartecipanteAndPersona(Partecipante partecipante, Persona persona);

	List<Accreditamento> findByPartecipante(Partecipante partecipante);

}
