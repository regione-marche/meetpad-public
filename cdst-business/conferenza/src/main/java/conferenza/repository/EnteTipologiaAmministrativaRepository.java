package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EnteTipologiaAmministrativa;

public interface EnteTipologiaAmministrativaRepository extends JpaRepository<EnteTipologiaAmministrativa, String>{

	Optional<EnteTipologiaAmministrativa> findByDescrizione(String descrizione);

}
