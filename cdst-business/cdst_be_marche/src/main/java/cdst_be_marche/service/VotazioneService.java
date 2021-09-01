package cdst_be_marche.service;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.mail.MailContentBuilder;
import cdst_be_marche.mail.bean.MailMetadata;
import cdst_be_marche.repository.MailerRepository;
import cdst_be_marche.votazione.MailVotazioneContemptBuilder;

@Service
public class VotazioneService {
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	EmailRepositoryService mailerRepository;
	
	@Autowired
	MailVotazioneContemptBuilder mailbuilder;
	
	
	public void sendEmail(MailMetadata metadati) {
		int otp = otpService.generateOTP(metadati.getCodice_fiscale());
		Map<String, String> parametri=metadati.getParametri();
		parametri.put("otp", String.valueOf(otp));
		String votazione=(String)metadati.getParametri().get("votazione");
		String urlVotazione=getUrlVotazione(votazione);
		parametri.put("votazione", urlVotazione);
		mailbuilder.build_votazione(metadati);
		mailerRepository.sendMailVotazione(metadati);
	}
	
	
	/**
	 * @TODO: ottenere l'indirizzo della votazione
	 * @param idVotazione
	 * @return
	 */
	public String getUrlVotazione(String idVotazione) {		
		return null;
	}
}
