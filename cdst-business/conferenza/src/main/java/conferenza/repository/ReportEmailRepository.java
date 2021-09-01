package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.MailError;
import conferenza.model.ReportEmail;
import conferenza.model.ReportEmailSample;
import conferenza.repository.bean.ReportMailSampleBean;



public interface ReportEmailRepository extends JpaRepository <ReportEmail, Integer> {

	public  List<ReportMailSampleBean> findIdMessaggioByConferenceId(Integer idConferenza);
	public  List<ReportEmailSample> reportEmailByConference(Integer idConferenza);
	
	@Query(nativeQuery = true)
	public  List<ReportEmailSample> getReportEmailSampleIdMessaggio(String idMessaggio,String statoEmail);
	
	@Query(nativeQuery = true)
	public  List<MailError>  findAllMailInError(Integer idConferenza,String emailDestinatario);
}
