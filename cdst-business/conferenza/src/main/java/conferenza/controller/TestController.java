package conferenza.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.bean.RispostaNoData;
import conferenza.mail.EmailRepositoryService;
import conferenza.mail.JavaMailPecSenderConfigurator;
import conferenza.mail.JavaMailSenderConfigurator;
import conferenza.mail.MailContentBuilder;
import conferenza.mail.bean.MailMetadata;
import conferenza.mail.bean.MailStatus;
import conferenza.model.Conferenza;
import conferenza.model.Mailer;
import conferenza.model.ReportEmailSample;
import conferenza.repository.ConferenzaRepository;
import conferenza.service.ConferenzaService;
import conferenza.service.JWTsService;
import io.swagger.annotations.Api;

	

@CrossOrigin
@RestController
@Api(tags = { "Conferences API" })
public class TestController {

		private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

		@Autowired
		ConferenzaService confservice;

		@Autowired
		JavaMailSenderConfigurator mailerconfig;

		@Autowired
		MailContentBuilder mailbuilder;

		@Autowired
		EmailRepositoryService mailerRepository;

		@Autowired
		HttpServletRequest request;

		@Autowired
		JWTsService jwtutils;
		
		@Autowired
		ConferenzaRepository conferenzaRepository;
		
		@Autowired
		JavaMailPecSenderConfigurator mailPecSenderConfigurator;
		
		/**
		 * 
		 * @param idconferenza
		 * @return
		 */
		@RequestMapping(value = "/test/emailindizione/{idconferenza}", method = RequestMethod.GET)
		public RispostaNoData emailindette(
				@PathVariable int idconferenza
		) {

		    // -------------------------------------------
			String subject = "[Conferenza] Indizione conferenza";
			String from = "prova@eng.it";
			String textmessage = "Messaggio di Prova \n procedere all'autoaccreditamento 2 ";
			String to = "mariateresa.dibiase@pec.it";
			to = "guido.deluca@postaraffaello.it";
			String cc = "guido.deluca.eng@gmail.com";			
		    // -------------------------------------------
			
			MailMetadata mailmetadata = new MailMetadata(null);
			
			
			List<Mailer> mailerList =  mailerRepository.findAllMailerByConference(new Integer(idconferenza));
			for(Mailer mailer : mailerList) {				
				to = mailer.getEmail();				
			}
			


			mailmetadata.setIdConferenza(idconferenza);
			mailmetadata.setFrom(from);
			mailmetadata.setTo(to);
			if(cc!=null)
				mailmetadata.setCc(cc);
			mailmetadata.setSubject(subject);
			mailmetadata.setMessage(textmessage);

			//-----------------------------------------------------------
			mailerRepository.sendMailIndizioneByMetadata(mailmetadata);

			RispostaNoData risposta = new RispostaNoData();
			risposta.setMsg(textmessage);
			return risposta;
		}		

		
		@RequestMapping(value = "/test/checkemailstatusall", method = RequestMethod.GET)
		public RispostaNoData checkemailstatusall(
		) {
				

			 List<Conferenza> conferenze = conferenzaRepository.findAll();
			 for(Conferenza conferenza: conferenze) {			 
				 LOGGER.debug("asyncCheckMailStatusConference :[id conferenza]"+conferenza.getIdConferenza() );
				 mailerRepository.checkEmailStatus(conferenza.getIdConferenza());			 
			 }				 
				
			RispostaNoData risposta = new RispostaNoData();
			risposta.setMsg("checkemailstatusall ok");
			return risposta;
		 }
		
		//sendMailIndizioneForTransportError()
		@RequestMapping(value = "/test/checkemailerror", method = RequestMethod.GET)
		public RispostaNoData checkemailerror(
		) {
				
			mailerRepository.sendMailIndizioneForTransportError();

			RispostaNoData risposta = new RispostaNoData();
			risposta.setMsg("checkemailsendMailIndizioneForTransportErrorstatusall ok");
			return risposta;
		 }		
		
		
		/**
		 * 
		 * @param idconferenza
		 * @return
		 */
		@RequestMapping(value = "/test/checkemailstatus/{pConferenceId}", method = RequestMethod.GET)
		public List<ReportEmailSample> checkemailstatus(
				@PathVariable int pConferenceId
		) {
			List<ReportEmailSample> report=null;
			//-----------------------------------------------------------
			String textmessage=null;
			//-----------------------------------------------------------
			try {
				mailerRepository.checkEmailStatus(new Integer(pConferenceId));				
				report= mailerRepository.reportEmailByConference(pConferenceId);				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return report;
		}	
		
		
		
		@RequestMapping(value = "/test/mailordinaria", method = RequestMethod.GET)
		public RispostaNoData checksslemailstatus() {		
			
			
			//try {
			//	mailerRepository.checkAndestoreAllStatus();
			//} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			//List<Conferenza> conferenze = conferenzaRepository.findAll();
			//for(Conferenza conferenza: conferenze) {			 
			//	 LOGGER.debug("asyncCheckMailStatusConference :[id conferenza]"+conferenza.getIdConferenza() );
			//	 mailerRepository.checkEmailStatus(conferenza.getIdConferenza());		
			//}	
			
			mailerRepository.sendMailOrdinariaForNonAccettazionePec();
			
			RispostaNoData risposta = new RispostaNoData();
			String textmessage="[TEST]: sendMailOrdinariaForNonAccettazionePec";
			risposta.setMsg(textmessage);
			return risposta;			
		}
		
		/**
		 * 
		 * @param idconferenza
		 * @return
		 */
		@RequestMapping(value = "/test/checksslemailstatus/{pMessageId}", method = RequestMethod.GET)
		public RispostaNoData checksslemailstatus(
				@PathVariable String pMessageId
		) {
			//-----------------------------------------------------------
			pMessageId="<"+pMessageId+">";			
			String textmessage="";
			MailMetadata mailmetadata = new MailMetadata(null);
			Date statusDate=null;
			int dtCompare =0;
			//-----------------------------------------------------------
			try {
				
				List<MailStatus> listMailStatus=mailerRepository.getSSLMailStatus(pMessageId);
				for(MailStatus status : listMailStatus ) {									
					LOGGER.debug(" STATUS:--> "+status.getMessageStatus());
					Date statusDate2=status.getMessageSentDate();
					if(statusDate!=null) {
						dtCompare =statusDate.compareTo(statusDate2);
						if(dtCompare<0) {
							statusDate=statusDate2;
							textmessage=status.getMessageStatus();
						}	
					}else {
						statusDate=statusDate2;
						textmessage=status.getMessageStatus();
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}

			RispostaNoData risposta = new RispostaNoData();
			risposta.setMsg(textmessage);
			return risposta;
		}		
		
	/**
	 * Invio mail di test per testare il server mail 
	 * @return
	 */
	@RequestMapping(value = "/test/sendTestMail/{email}/{oggetto}/{corpo}", method = RequestMethod.GET)
	public RispostaNoData sendTestMail(@PathVariable String email, @PathVariable String oggetto,
			@PathVariable String corpo) {
		//mailerRepository.sendTestEmail(email, oggetto, corpo);
		Message[] messages = null;
		try {
			Store store = mailerRepository.getPecStore();
			store.connect();
			// FOLDER
			Folder inbox = store.getFolder(mailPecSenderConfigurator.getPop3Folder());
			inbox.open(Folder.READ_ONLY);
			// get the list of inbox messages
			messages =  inbox.getMessages();
			
			Message message = messages[1002];
			Enumeration<Header> headers = message.getAllHeaders();

			while(headers.hasMoreElements()) {
				Header header = headers.nextElement();
				LOGGER.debug("header: " + header.getName() + " --> " + header.getValue());
			}
			
			MimeMultipart content = (MimeMultipart) message.getContent();
			String text = getTextFromMimeMultipart(content);
			String MOTIVO_NON_ACCETTAZIONE_NON_PEC = "il dominio emarche.it puo' spedire solamente verso domini di posta elettronica certificata";
			LOGGER.debug("non accettazione per PEC: " + text.contains(MOTIVO_NON_ACCETTAZIONE_NON_PEC));

			inbox.close(true);
			store.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg("mail inviata");
		return risposta;
	}
	
	/**
	 * Invio mail di test per testare il server mail 
	 * @return
	 */
	@RequestMapping(value = "/test/mailNonAccettazione", method = RequestMethod.GET)
	public RispostaNoData mailNonAccettazione() {
		Message[] messages = null;
		try {
			Store store = mailerRepository.getPecStore();
			store.connect();
			// FOLDER
			Folder inbox = store.getFolder(mailPecSenderConfigurator.getPop3Folder());
			inbox.open(Folder.READ_ONLY);
			// get the list of inbox messages
			messages = inbox.getMessages();

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				if (message == null) {
					LOGGER.debug("messaggio numero: " + i + " null");
				} else {
					String[] ricevute = message.getHeader("X-ricevuta");
					if (ricevute != null && ricevute.length > 0) {
						String xricevuta = message.getHeader("X-ricevuta")[0];
						MimeMultipart content = (MimeMultipart) message.getContent();
						String text = getTextFromMimeMultipart(content).replaceAll("\r\n|\r|\n", " ");

						String MOTIVO_NON_ACCETTAZIONE_NON_PEC = "il dominio emarche.it puo' spedire solamente verso domini di posta elettronica certificata";
						if (xricevuta.equals("non-accettazione")) {
							String destinatario = text
									.substring(text.indexOf("indirizzato a:") + 14, text.indexOf("è stato rilevato"))
									.trim();
							LOGGER.debug(xricevuta + " -- " + destinatario + " -- " + text);
						}
					}
				}
			}

			inbox.close(true);
			store.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg("mail inviata");
		return risposta;
	}
	
	public static void main(String[] args) {
		String text = "Il giorno 16/11/2018 alle ore 17:20:41 (+0100) nel messaggio \"[MeetPAD] Indizione conferenza\" proveniente da \"meetpad@emarche.it\" ed indirizzato a:    regione.marche@prova.it  è stato rilevato un problema che ne impedisce l'accettazione:   a causa di      - il dominio emarche.it puo' spedire solamente verso domini di posta elettronica certificata, l'indirizzo regione.marche@prova.it e' stato rifiutato Il messaggio non è stato accettato. Identificativo messaggio: 20181116172041.gfTFvN@emarche.it ";
		String destinatario = text.substring(text.indexOf("indirizzato a:")+14, text.indexOf("è stato rilevato")).trim();
		System.out.println(destinatario);
	}
	
	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws Exception {
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + html;
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
}
