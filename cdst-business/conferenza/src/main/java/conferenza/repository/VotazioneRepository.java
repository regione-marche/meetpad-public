package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Votazione;

public interface VotazioneRepository extends JpaRepository<Votazione, Integer>{

}
