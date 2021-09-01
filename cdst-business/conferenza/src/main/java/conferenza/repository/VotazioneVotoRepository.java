package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Votazione;
import conferenza.model.VotazioneVoto;

public interface VotazioneVotoRepository extends JpaRepository<VotazioneVoto, Integer>{

}
