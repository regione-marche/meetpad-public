package cdst_be_marche.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.TockenConference;



public interface TockenRepository extends JpaRepository<TockenConference , Integer> {

	@Query(nativeQuery = true)
	TockenConference findByTKN(String tkn1,String tkn2);

	@Query(nativeQuery = true)
	Conferenza findInfoVideoconferenza(Integer idConferenza);
}
