package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import conferenza.model.GruppoUtenti;

public interface GruppoUtentiRepository extends JpaRepository<GruppoUtenti, String>{

	GruppoUtenti findByFkUtenteAndFkUtenteResponsabile(Integer fkUtente, Integer fkUtenteResponsabile);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM GruppoUtenti WHERE fk_utente = ?1")
    int deleteAllUserGroups(Integer fk_utente);

}
