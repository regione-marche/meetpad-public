package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Persona;
import cdst_be_marche.model.ResponsabileConferenza;

public interface ResponsabileConferenzaRepository extends JpaRepository<ResponsabileConferenza, Integer> {
	public List<ResponsabileConferenza> findAllByEnte(Ente ente);

	public Optional<ResponsabileConferenza> findByEnteAndPersona(Ente ente, Persona persona);

}
