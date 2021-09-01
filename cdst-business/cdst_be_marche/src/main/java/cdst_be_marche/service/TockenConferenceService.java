package cdst_be_marche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.TockenConference;
import cdst_be_marche.repository.TockenRepository;

@Service
public class TockenConferenceService {

	@Autowired
	TockenRepository tockenRepository;
	
	public TockenConference saveTocken(String email,Integer ente,Integer idPartecipante, Integer conferenza,String cf,String fase) {
		TockenConference tocken=new TockenConference(email,ente,idPartecipante,conferenza,cf,fase); 
		TockenConference saved =  tockenRepository.save(tocken);		
		return saved;
	}
	
	public TockenConference  checkTockenConference(String tkn1,String tkn2) {
		return tockenRepository.findByTKN(tkn1,tkn2);
	}
	
	
	/**
	 * 
	 * @param idConferenza
	 * @return
	 */
	public String findInfoVideoconferenza(Integer idConferenza) {
		String pUrl=null;		
		Conferenza cc = tockenRepository.findInfoVideoconferenza(idConferenza);
		pUrl=cc.getIndirizzoSessioneSimultanea();		
		if(pUrl!=null &&  pUrl.lastIndexOf("http")<0)
			pUrl=null;		
		return pUrl;
	}
	
}
