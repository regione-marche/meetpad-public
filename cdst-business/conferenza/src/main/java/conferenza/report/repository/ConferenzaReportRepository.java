package conferenza.report.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.report.model.ConferenzaSvolta;

public interface  ConferenzaReportRepository extends JpaRepository<Conferenza, Integer>, JpaSpecificationExecutor<Conferenza>{

	@Query(nativeQuery = true,value="SELECT * FROM CONFERENZA WHERE 1=1")
	public List<Conferenza> getListConferenza();
	
	/**
		select cc.* from conferenza cc  inner join tipologia_conferenza_specializzazione tt on tt.codice=cc.fk_tipologia_conferenza_specializzazione where 1=1 and tt.descrizione='tipologiaConferenzaSpec.semplificata' 

			"1"	"tipologiaConferenzaSpec.semplificata"
			"2"	"tipologiaConferenzaSpec.simultanea"
			"3"	"tipologiaConferenzaSpec.regionale"
			"4"	"tipologiaConferenzaSpec.BULDecisoriaSimultanea"
			"5"	"tipologiaConferenzaSpec.BUListruttoriaSimultanea"

	 */

	/**
	 * 
	 * @param tipologia
	 * @return
	 */
	@Query(nativeQuery = true,value="select cc.* from conferenza cc  inner join tipologia_conferenza_specializzazione tt on tt.codice=cc.fk_tipologia_conferenza_specializzazione where 1=1 and tt.descrizione=?")
	public List<Conferenza> getListConferenzaByTipologiaConferenzaSpec(String tipologia);
	
	@Query(nativeQuery = true,value="select * from conferenze_svolte(?, ?)")
	public List<Object[]> getListConferenzeSvolte(java.sql.Timestamp dataInizio, java.sql.Timestamp dataFine);
	
}
