package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.PermessoAzione;

public interface PermessoAzioneRepository extends JpaRepository<PermessoAzione, String> {

	List<PermessoAzione> findByTipoEventoIsNotNull();

}
