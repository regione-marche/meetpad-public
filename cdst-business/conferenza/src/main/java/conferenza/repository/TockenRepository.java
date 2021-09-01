package conferenza.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.model.TockenConference;



public interface TockenRepository extends JpaRepository<TockenConference , Integer> {

	@Query(nativeQuery = true)
	TockenConference findByTKN(String tkn1,String tkn2);

	@Query(nativeQuery = true)
	Conferenza findInfoVideoconferenza(Integer idConferenza);
}
