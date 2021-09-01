package conferenza.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import conferenza.report.model.Report;



public interface ReportRepository extends JpaRepository<Report, Integer>{

	@Query(nativeQuery = true,value="select rr.*  from cdst.report rr where rr.codice=?")
	public Report getReportByCodice(String codice);


	@Query(nativeQuery = true,value="select rr.*  from cdst.report rr where rr.visibilita='PUBLIC' and rr.tiporeport=?")
	public List<Report> getPublicReportListByTiporeport(String tiporeport);
	
	public List<Report> findByVisibilita(String visibility);
}
