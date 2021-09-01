package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Ente;
import conferenza.model.Persona;
import conferenza.model.ResponsabileConferenza;

public interface ResponsabileConferenzaRepository extends JpaRepository<ResponsabileConferenza, Integer>, JpaSpecificationExecutor<ResponsabileConferenza> {
	
	public List<ResponsabileConferenza> findAllByEnte(Ente ente);

	public Optional<ResponsabileConferenza> findByEnteAndPersona(Ente ente, Persona persona);

	public List<ResponsabileConferenza> findByEnte(Ente ente);

}
