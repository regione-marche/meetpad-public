package conferenza.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.report.model.ReportParameter;



public interface ReportParameterRepository  extends JpaRepository<ReportParameter, Integer>{

	
	@Query(nativeQuery = true,value="SELECT * FROM REPORT_PARAMETER WHERE 1=1 AND FK_REPORT=?")
	public List<ReportParameter> getListParameterById(Integer id);

	
	@Query(nativeQuery = true,value="SELECT * FROM REPORT_PARAMETER WHERE 1=1 and parametronome like 'condizione%'  AND FK_REPORT=? order by id desc")
	public List<ReportParameter> getListConditionParameterById(Integer id);
	
	@Query(nativeQuery = true,value="SELECT * FROM REPORT_PARAMETER WHERE 1=1 and parametronome like 'anno%'  AND FK_REPORT=? order by id desc")
	public List<ReportParameter> getParametroAnnoParameterById(Integer id);
	
}
