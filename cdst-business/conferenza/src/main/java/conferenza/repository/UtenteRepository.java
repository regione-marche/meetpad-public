package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Profilo;
import conferenza.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>, JpaSpecificationExecutor<Utente> {

	Optional<Utente> findByCodiceFiscaleIgnoreCase(String codice);
	Optional<Utente> findByCodiceFiscaleIgnoreCaseAndProfilo(String codice, Profilo profilo);

	List<Utente> findByProfilo(Profilo profilo);
	
	Optional<Utente> findById(Integer idUser);

	
	@Query(value="select u.* from cdst.profilo p inner join cdst.utente u on p.id_profilo=u.fk_profilo where p.fk_ente=?" ,nativeQuery = true)
	List<Utente> getOtherUsersFromEnte(Integer idEnte);

	@Query(value="select u.* from cdst.profilo p inner join cdst.utente u on p.id_profilo=u.fk_profilo where not(u.id_utente=?) AND (p.fk_tipo_profilo='1' or p.fk_tipo_profilo='2') ORDER BY u.cognome, u.nome" ,nativeQuery = true)
	List<Utente> getAllConferenzaCreatorUsers(Integer id2esclude);
	
	@Query(value="select u.* \r\n" + 
			"from cdst.partecipante p \r\n" + 
			"join cdst.ente e on e.id_ente = p.fk_ente \r\n" + 
			"join cdst.profilo pr on pr.fk_ente = e.id_ente \r\n" + 
			"join cdst.utente u on u.fk_profilo = pr.id_profilo \r\n" + 
			"where p.fk_conferenza = ? and p.fk_ruolo_partecipante ='2' and u.flag_firmatario = ? \r\n" + 
			"ORDER BY u.cognome, u.nome" ,nativeQuery = true)
	List<Utente> getAllFirmatariConferenza(Integer idConferenza, boolean flagFirmatario);
}
