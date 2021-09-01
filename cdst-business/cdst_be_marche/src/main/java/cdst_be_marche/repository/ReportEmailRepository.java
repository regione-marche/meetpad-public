package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cdst_be_marche.model.MailError;
import cdst_be_marche.model.ReportEmail;
import cdst_be_marche.model.ReportEmailSample;
import cdst_be_marche.repository.bean.ReportMailSampleBean;



public interface ReportEmailRepository extends JpaRepository <ReportEmail, Integer> {

	public  List<ReportMailSampleBean> findIdMessaggioByConferenceId(Integer idConferenza);
	public  List<ReportEmailSample> reportEmailByConference(Integer idConferenza);
	
	@Query(nativeQuery = true)
	public  List<ReportEmailSample> getReportEmailSampleIdMessaggio(String idMessaggio,String statoEmail);
	
	@Query(nativeQuery = true)
	public  List<MailError>  findAllMailInError(Integer idConferenza,String emailDestinatario);
}
