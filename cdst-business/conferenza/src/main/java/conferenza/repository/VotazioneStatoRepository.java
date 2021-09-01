package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import conferenza.model.VotazioneStato;

public interface VotazioneStatoRepository extends JpaRepository<VotazioneStato, String> {

}
