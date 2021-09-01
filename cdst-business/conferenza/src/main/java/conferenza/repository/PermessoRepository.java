package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Permesso;
import conferenza.model.PermessoAzione;
import conferenza.model.PermessoRuolo;

public interface PermessoRepository extends JpaRepository<Permesso, Integer> {
	
	Optional<Permesso> findByPermessoRuoloAndPermessoAzione(PermessoRuolo permessoRuolo, PermessoAzione permessoAzione);

}
