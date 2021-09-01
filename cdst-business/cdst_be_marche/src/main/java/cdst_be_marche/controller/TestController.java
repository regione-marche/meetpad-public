package cdst_be_marche.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.mail.JavaMailSenderConfigurator;
import cdst_be_marche.mail.MailContentBuilder;
import cdst_be_marche.mail.bean.MailMetadata;
import cdst_be_marche.mail.bean.MailStatus;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Mailer;
import cdst_be_marche.model.ReportEmailSample;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.service.ConferenzaService;
import cdst_be_marche.service.JWTsService;
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
			String subject = "[MeetPAD] Indizione conferenza";
			String from = "MeetPAD@eng.it";
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
				 LOGGER.info("asyncCheckMailStatusConference :[id conferenza]"+conferenza.getIdConferenza() );
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
					LOGGER.info(" STATUS:--> "+status.getMessageStatus());
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
		

}
