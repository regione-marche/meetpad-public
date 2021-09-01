package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.VotazioneTipo;

public interface VotazioneTipoRepository extends JpaRepository<VotazioneTipo, String>{

	public Optional<VotazioneTipo> findByCodice (String codice);
}
