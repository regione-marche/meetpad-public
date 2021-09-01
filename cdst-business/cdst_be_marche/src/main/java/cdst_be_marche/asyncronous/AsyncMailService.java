package cdst_be_marche.asyncronous;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.repository.ConferenzaRepository;

@Service
public class AsyncMailService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncMailService.class);
	
	@Autowired
	ConferenzaRepository conferenzaRepository;
	@Autowired
	EmailRepositoryService emailRepository;
	
	 @Async
	 public CompletableFuture<Boolean> asyncCheckMailStatus() 
	 throws InterruptedException {
		 Boolean retBool=new Boolean(true);
		 asyncCheckMailStatusAll();		
		 Thread.sleep(1000L);
		 return CompletableFuture.completedFuture(retBool);
	 }

	 @Async
	 public CompletableFuture<Boolean> asyncChechReinoltorEmailInErrore() 
	 throws InterruptedException {
		 Boolean retBool=new Boolean(true);
		 chechReinoltorEmailInErrore();		
		 Thread.sleep(1000L);
		 return CompletableFuture.completedFuture(retBool);
	 }
	 
	 
	 public void chechReinoltorEmailInErrore() {
		 emailRepository.sendMailIndizioneForTransportError();		 
	 }
	 
	 
	 /**
	  * Effettua un controllo sulle email in POP3 ed allinea il DB se non sono presenti gli stati..
	  * 
	  */
	 public void asyncCheckMailStatusAll() {
		 LOGGER.info(" -- checkAndestoreAllStatus --" );
		 try {
			emailRepository.checkAndestoreAllStatus();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	 }
	 
	 
	 public void asyncCheckMailStatusAllConference() {
		 List<Conferenza> conferenze = conferenzaRepository.findAll();
		 for(Conferenza conferenza: conferenze) {			 
			 asyncCheckMailStatusConference(conferenza.getIdConferenza());			 
		 }				 
	 }

	 public void asyncCheckMailStatusConference(Integer pConferenceId) {
		 LOGGER.info("asyncCheckMailStatusConference :[id conferenza]"+pConferenceId );
		 emailRepository.checkEmailStatus(pConferenceId);				 
	 }
	
}
