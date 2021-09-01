package conferenza.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.report.model.ReportAdapter;
import conferenza.report.repository.ReportAdapterRepository;

/**
 * @author guideluc
 *
 */
@Service
public class ReportServiceAdapter {
	
	String token;

	@Autowired
	ReportAdapterRepository reportAdapterRepository;
	
	public void store(ReportAdapter adapter) {
		reportAdapterRepository.save(adapter);		
	}
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
	
	
}
