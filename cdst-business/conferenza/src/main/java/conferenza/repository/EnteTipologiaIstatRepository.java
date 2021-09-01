package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EnteTipologiaIstat;

public interface EnteTipologiaIstatRepository extends JpaRepository<EnteTipologiaIstat, String>{

	Optional<EnteTipologiaIstat> findByDescrizione(String descrizione);

}
