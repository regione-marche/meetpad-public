package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import conferenza.model.Mailer;
import conferenza.model.Partecipante;


@Repository
public interface MailerRepository extends JpaRepository<Mailer, Long>{//extends CrudRepository<MailerDTO, String>{

	@Query(nativeQuery = true)
    List<Mailer> findAllMailerDTO();
	
	@Query(nativeQuery = true)
	List<Mailer> findAllMailerByConference(Integer id);
	
	@Query(nativeQuery = true)
	List<Mailer> findAllMailerByConferenceForPaleo(Integer id);
	
	@Query(nativeQuery = true)
	List<Mailer> findAllInvitiIndizioneByConference(Integer id);
	
	@Query(nativeQuery = true)
	List<Mailer> findAllMailerInError();
	
}
