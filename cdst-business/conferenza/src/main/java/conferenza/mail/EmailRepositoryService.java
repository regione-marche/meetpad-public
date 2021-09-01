package conferenza.mail;

import java.io.File;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.mail.smtp.SMTPMessage;

import conferenza.ScheduledTasks;
import conferenza.DTO.bean.LabelValue;
import conferenza.asyncronous.AsyncMailService;
import conferenza.exception.NotFoundEx;
import conferenza.mail.bean.MailMetadata;
import conferenza.mail.bean.MailStatus;
import conferenza.model.Accreditamento;
import conferenza.model.Azione;
import conferenza.model.Conferenza;
import conferenza.model.ContattoSupporto;
import conferenza.model.CruscottoPec;
import conferenza.model.Documento;
import conferenza.model.EmailPartecipante;
import conferenza.model.EmailPec;
import conferenza.model.Evento;
import conferenza.model.EventoPartecipante;
import conferenza.model.MailError;
import conferenza.model.Mailer;
import conferenza.model.ModificaData;
import conferenza.model.ModificaDataReport;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.RegistroEmailAllegato;
import conferenza.model.RegistroEmailDettaglio;
import conferenza.model.RegistroEmailTestata;
import conferenza.model.ReportEmailSample;
import conferenza.model.Template;
import conferenza.model.TipoEvento;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.model.TockenConference;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.AzioneRepository;
import conferenza.repository.ContattoSupportoRepository;
import conferenza.repository.CruscottoPecRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EmailPartecipanteRepository;
import conferenza.repository.EmailPecRepository;
import conferenza.repository.EventoPartecipanteRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.MailerRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.RegistroEmailAllegatoRepository;
import conferenza.repository.RegistroEmailDettaglioRepository;
import conferenza.repository.RegistroEmailTestataRepository;
import conferenza.repository.ReportEmailRepository;
import conferenza.repository.TemplateRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.UtenteRepository;
import conferenza.repository.bean.ReportMailSampleBean;
import conferenza.service.ConferenzaService;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.TockenConferenceService;
import conferenza.service.UtenteService;
import conferenza.service._BaseService;
import conferenza.util.DbConst;
import conferenza.util.RandomUtil;

@Service
public class EmailRepositoryService extends _BaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailRepositoryService.class);
	
	@Autowired
	MailerRepository mailerRepository;

	@Autowired
	MailContentBuilder mailbuilder;

	@Autowired
	JavaMailSenderConfigurator mailSenderConfigurator;

	@Autowired
	JavaMailPecSenderConfigurator mailPecSenderConfigurator;

	@Autowired
	TockenConferenceService tockenService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	RegistroEmailTestataRepository repositoryRegistroEmailTestata;

	@Autowired
	RegistroEmailDettaglioRepository repositoryRegistroEmailDettaglio;

	@Autowired
	ReportEmailRepository reportEmailRepository;

	@Autowired
	TemplateRepository templateRepo;

	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipoConfSpecRepo;

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	EventoPartecipanteRepository eventoPartRepo;

	@Autowired
	EmailPecRepository emailPecRepository;

	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	RegistroEmailAllegatoRepository registroAllegatiRepo;
		
	@Autowired
	PartecipanteRepository partRepo;	
		
	@Autowired
	UtenteService utenteService;
		
	@Autowired
	@Qualifier("EmailStrategyAlias")
	EmailStrategy emailStrategy;
	
	@Autowired
	CruscottoPecRepository cruscottoRepo;
	
	@Autowired
	EmailPartecipanteRepository emailPartRepo;
	
	@Autowired
	EventoRepository eventoRepo;
	
	@Autowired
	DocumentoRepository docRepo;
	
	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	AccreditamentoRepository accrRepo;
	
	@Autowired
	ContattoSupportoRepository contattoSuppRepo;
	
	@Autowired
	ConferenzaService conferenzaService;
	
	@Autowired
	AzioneRepository azioneRepo;
	
	/*
	@Autowired
	ConferenzaService confService;
*/
	@Value("${mail.from}") 
	private String from;
	
	@Value("${mail.urlAccessoCDST}") 
	private String urlAccessoCDST;
	
	@Value("${mail.urlDocumentazione}") 
	private String urlDocumentazione;

	//@Value("${descrizioneAmmProcedente.descrizione}")
	//private String descrizione;
	
	MimeMessage mm = null;
	JavaMailSender meansender = null;
	MimeMessageHelper helper = null;

	String mittenteDefault = null;

	/*
	 * private void init() { mittenteDefault=mailSenderConfigurator.getFrom(); }
	 * 
	 * public EmailRepositoryService() { init() ; }
	 */

	// =============================================================================================
	// Gestione degli ERRORI

	// =============================================================================================
	public List<Mailer> findAllMailerByError() {
		return mailerRepository.findAllMailerInError();
	}

	/**
	 * 
	 * @param idConference
	 * @param from                - è il from di default
	 * @param textmessage         - non viene utilizzato
	 * @param subject             - verificare il subject
	 * @param tokenaccreditamento verificare il token di accreditamento
	 */
	public void sendMailIndizioneForTransportError() {

		mittenteDefault = mailSenderConfigurator.getFrom();

		List<Mailer> mailerList = findAllMailerByError();
		for (Mailer mail : mailerList) {
			if (mail == null)
				continue;
			// Livello - RAPPRESENTAZIONE dell'infromazione
			MailMetadata metadati = formatTestoEmailIndizione(mail, this.mittenteDefault, null,
					(mail.getOggetto_determinazione() == null ? " OGGETTO DETERMINA NON DEFINITO "
							: mail.getOggetto_determinazione()),
					null);
			// va ricercata per la data conferenza e per il dato partecipante l'id messaggio
			// in errore
			List<MailError> listaMessaggiInErrore = getListMessaggiInErrore(mail.getId_conferenza(), mail.getEmail());
			for (MailError errore : listaMessaggiInErrore) {
				metadati.setMessageId(errore.getId_messaggio());
				// @TODO: implementare la gestione del numero massimo di tentativi prima della
				// notifica al responsabile
				if (errore.getTentativi() <= 3) {
					if (errore.getEmail_destinatario() != null && !"".equals(errore.getEmail_destinatario())) {
						LOGGER.debug("sendMailIndizioneForTransportError errore.getId_messaggio ["
								+ errore.getId_messaggio() + "]");
						// Livello - TRASPORTO dell'infromazione
						sendMailIndizioneByMetadata(metadati);
					}
				} else {
					doMessageNotificaResponsabile(metadati);

				}
			}

		}
	}

	/**
	 * @TODO: implementare il metodo occorre scrivere il messaggio sul registro
	 *        degli eventio in modo che un processo batch possa notificarlo al
	 *        cruscotto del responsabile..
	 * @param metadati
	 */
	private void doMessageNotificaResponsabile(MailMetadata metadati) {

		LOGGER.debug("--//============================================");
		LOGGER.debug("[ATTENZIONE] --implementare il metodo [doMessageNotificaResponsabile] !!!!!!");
		LOGGER.debug("--//============================================");
	}

	/**
	 * Ritorna la lista dei messaggi in errore
	 * 
	 * @param id_conferenza
	 * @param email
	 * @return
	 */
	private List<MailError> getListMessaggiInErrore(Integer id_conferenza, String email) {
		return reportEmailRepository.findAllMailInError(id_conferenza, email);
	}

	// ===========================================================
	// Utilizzo delle interfacce Native Query
	// ===========================================================
	// la query tira gli inviti per gli auto-accreditati
	public List<Mailer> findAllMailerDTO() {
		return mailerRepository.findAllMailerDTO();
	}

	// la query tira gli inviti per gli auto-accreditati
	public List<Mailer> findAllMailerByConference(Integer id) {
		return mailerRepository.findAllMailerByConference(id);
	}

	/**
	 * Per una data conferenza indetta..Spedisce le mail a tutti i partecipanti
	 * 
	 * @param from
	 * @param textmessage
	 * @param registroDocumento
	 */
	@Async("threadPoolTaskExecutor")
	@Transactional
	public void sendMailIndizioneForConference(Integer idConference, String from, String textmessage, String subject,
			String tokenaccreditamento, Documento documento) {
		List<Mailer> mailerList = mailerRepository.findAllMailerByConferenceForPaleo(idConference);
		sendMailIndizioneToList(mailerList, from, textmessage, subject, tokenaccreditamento, documento);
	}
	
	public void sendMailIndizioneForConferenceMailSpecifica(Integer idConference, String from, String textmessage,
			String subject, String tokenaccreditamento, Documento documento, String mailSpecifica,
			Partecipante partecipante) {

		List<Mailer> mailerList = findAllMailerByConference(idConference);
		if (!mailerList.stream().anyMatch(m -> m.getEmail().equals(mailSpecifica))) {
			if (partecipante != null) {
				creaEmailPartecipante(mailSpecifica, partecipante);
			}
		}
		List<Mailer> listaFiltrata = findAllMailerByConference(idConference).stream()
				.filter(m -> m.getEmail().equals(mailSpecifica)).collect(Collectors.toList());
		listaFiltrata.stream().forEach(m -> System.out.println("mail " + m.getEmail()));
		sendMailIndizioneToList(listaFiltrata, from, textmessage, subject, tokenaccreditamento, documento);
		for (Mailer mailer : listaFiltrata) {
			System.out.println("mail " + mailer.getEmail());
		}

	}
	
	/**
	 * @TODO: stefano.diana@eng.it 
	 * non sò se c'è un alro metodo del tipo: sendMailIndizioneToOne che ho creato (verificare?!?!)
	 * comunque ho fatto in modo da splittare che venga richiamato da  	 
	 * sendMailIndizioneToList
	 * in modo che chiami un unico metodo sendMailIndizioneToOne
	 * ..nel casso sarebbe da fere..
	 * intanto aggiungo un unica email ad una lista llcreata ad hoc per usare il metodo! 
	 * 
	 * il metodo per ora non è agganciato a nessuno!!?!? 
	 * 
	 * 
	 * @param idConference
	 * @param from
	 * @param textmessage
	 * @param subject
	 * @param tokenaccreditamento
	 * @param documento
	 * @param mailSpecifica
	 * @param partecipante
	 */
	public void sendSingleMailIndizioneForConferenceMailSpecifica(Integer idConference, String from, String textmessage,
			String subject, String tokenaccreditamento, Documento documento, String mailSpecifica,
			Partecipante partecipante, boolean sendnonpec) {
		//
		Mailer newMailer=new Mailer();
		List<Mailer> mailerList = findAllMailerByConference(idConference);
		Integer idEnte=partecipante.getEnte().getIdEnte();
		//Ricerca Parametri comuni alle email per la data conferenza
		for(Mailer mail : mailerList ){
			if(idEnte.compareTo(mail.getId_ente()) ==0){
				//prendo i parametri comuni e quelli differenti verrranno sovrascritti
				newMailer=mail;
				//ne serve solo uno!
				break;
			}
		}
		//A prescindere viene creata l'email partecipante
		EmailPartecipante emailPartecipante=creaEmailPartecipante(mailSpecifica, partecipante);

		
		//vengono sovrascritti i parametri relativi all'email ed alla persona..
		newMailer.setEmail(mailSpecifica);		
		newMailer.setId_partecipante(partecipante.getIdPartecipante());
		newMailer.setCodice_fiscale(partecipante.getCfEnteCompetente());
		newMailer.setDescrizione_ruolo_partecipante(partecipante.getRuoloPartecipante().getDescrizione());
				
		//mando un'unica email!!!!
		sendMailIndizioneToOne(newMailer, from, textmessage, subject, tokenaccreditamento, documento, sendnonpec);		
		//
		LOGGER.debug("mail " + newMailer.getEmail());
		

	}	

	public EmailPartecipante creaEmailPartecipante(String mailSpecifica, Partecipante partecipante) {
		EmailPartecipante emailPart = new EmailPartecipante();
		emailPart.setEmail(mailSpecifica);
		emailPart.setPartecipante(partecipante);
		return emailPartRepo.save(emailPart);
	}

	/**
	 * La query in questocaso non mette in relazione gli accreditati con le altre
	 * tabelle..sono gli inviti per indizione.. per i non auto accreditati..
	 * 
	 * @param id
	 * @return
	 */
	public List<Mailer> findAllInvitiIndizioneByConference(Integer id) {
		return mailerRepository.findAllInvitiIndizioneByConference(id);
	}

	// ===========================================================
	//
	// ===========================================================

	/**
	 * @deprecated
	 * @param from
	 * @param textmessage
	 */
	public void sendMailForIndizioneConference(String from, String textmessage, String subject, Integer idConferenza,
			String tokenaccreditamento) {
		List<Mailer> mailerList = findAllMailerByConference(idConferenza);
		sendMailIndizioneToList(mailerList, from, textmessage, subject, tokenaccreditamento, null);
	}

	/**
	 * Metrodo Generico: per tutti gli invii
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param textmessage
	 */
	public void sendMailIndizioneByMetadata(MailMetadata metadati) {
		sendMailIndizioneByMetadata(metadati, false);
	}
	
	public void sendMailIndizioneByMetadata(MailMetadata metadati, boolean sendnonpec) {
		// ---------------------------------------
		metadati.setFaseConcerenza("INDIZIONE");
		metadati.setCodiceTipoEvento(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA));
		// ---------------------------------------
		LOGGER.debug("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");
		String message = mailbuilder.build_indizione(metadati);
		metadati.setMessage(message);
		// ---------------------------------------
		sendMail(metadati, sendnonpec);
		// ---------------------------------------
	}

	/**
	 * TODO: il metodo alla fine userà solo sendSSLMail
	 * 
	 * @param metadati
	 * @throws MessagingException
	 */
	public void sendMail(MailMetadata metadati, boolean forcesendnonpec) {
		if (mailSenderConfigurator.isSendEnabled()) {
			try {
				// ---------------------------------------
				String messageId = null;
				if (forcesendnonpec || !isPec(metadati)) {
					LOGGER.debug("@sendmail - nonpec: [" + metadati.getTo() + "]: " + forcesendnonpec);
					
					try {
						messageId = sendMailByMetadata(metadati);
						metadati.setMessageId(messageId);
					} catch (MessagingException e) {
						LOGGER.debug("Errore [" + e.getMessage() + "]");
						e.printStackTrace();
					}
				} else {
					LOGGER.debug("@sendmail - PEC: [" + metadati.getTo() + "]: ");
					
					messageId = this.sendSSLMail(metadati);
					if (messageId == null)
						throw new Exception("Attenzione: l'email deve essere valorizzata e deve essere una PEC.");

					metadati.setMessageId(messageId);
					LOGGER.debug(" metadati.getMessageId " + metadati.getMessageId());
					LOGGER.debug("[CDST-114 - EmailRepositoryService - sendMail]");
				}
				// ---------------------------------------
				// STORE REGISTRO - DATI TESTATA
				// dopo il livello trasporto occorre salvare sul registro l'operazione
				if (messageId != null) {
					registerInitialMessag(metadati, DbConst.EMAIL_STATUS_INOLTRATO, "Trasmissione");
				} else {
					registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE,
							"Problemi di trasmissione");
				}
			} catch (Exception e) {
				registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public boolean isPec(MailMetadata metadati) {
		return isPec(metadati.getTo());
	}
	
	public boolean isPec(String mail) {
		if (mail != null) {
			EmailPec emailPec = emailPecRepository.findByEmail(mail).orElse(null);

			if (emailPec != null)
				return emailPec.getPec();
		}
		return mailSenderConfigurator.isDefaultFormatPec();
	}
	
	/**
	 * inserisce o modifica una mail nella tabella che censisce la natura delle mail pec/non pec
	 * @param mail
	 * @param pec
	 */
	public void upsertEmailPec(String mail, Boolean pec) {
		EmailPec emailPec = emailPecRepository.findByEmail(mail).orElse(null);
		if (emailPec == null) {
			emailPec = new EmailPec();
			emailPec.setEmail(mail);
		}

		LOGGER.debug("@emailpec set "+mail+"=" + pec);
		
		emailPec.setPec(pec);
		emailPecRepository.save(emailPec);
	}

	/**
	 * Inserisce la riga iniziale nel registro.. Le righe successivie riguarderanno
	 * solo lil dettaglio..
	 * 
	 * @param metadati
	 * @param STATOMESSAGGIO
	 * @param nota
	 */
	public void registerInitialMessag(MailMetadata metadati, String STATOMESSAGGIO, String nota) {
		try {
			RegistroEmailTestata registroTestata = registraMessaggioTestata(metadati);
			registraMessaggioAllegati(metadati, registroTestata);
			registraMessaggioDettaglio(metadati, STATOMESSAGGIO, nota, new Date(), registroTestata);
		} catch (Exception e) {
			LOGGER.debug("registerInitialMessag: exception - metadati.getMessageId: " + metadati.getMessageId());
		}
	}

	private void registraMessaggioAllegati(MailMetadata metadati, RegistroEmailTestata registroTestata) {
		for (Documento documento: (List<Documento>)metadati.getAttachments()) {
			RegistroEmailAllegato registro = new RegistroEmailAllegato();
			registro.setDocumento(documento);
			registro.setRegistroEmailTestata(registroTestata);
			this.registroAllegatiRepo.save(registro);
		}
		
	}

	/**
	 * 
	 * @param metadati
	 */
	private RegistroEmailTestata registraMessaggioTestata(MailMetadata metadati) {
		// STORE REGISTRO - DATI TESATA
		RegistroEmailTestata ret = RegistroEmailTestata.getRegistroEmailTestata(metadati);
		return repositoryRegistroEmailTestata.save(ret);
	}

	/**
	 * 
	 * @param metadati
	 * @param registroTestata 
	 */
	private void registraMessaggioDettaglio(MailMetadata metadati, String STATOMESSAGGIO, String nota, Date sentDate, RegistroEmailTestata registroTestata) {
		// STORE REGISTRO - DATI DETTAGLIO
		RegistroEmailDettaglio dettaglio = new RegistroEmailDettaglio(metadati.getMessageId(), STATOMESSAGGIO, sentDate,
				nota, registroTestata);
		repositoryRegistroEmailDettaglio.save(dettaglio);

	}

	/**
	 * TODO: occorre settare una tabella di log delle email inviate
	 */
	public void doAfterSendEmail(MailMetadata metadati) {
		LOGGER.debug("[doAfterSendEmail]: operazine dopo sent emailemail per [" + metadati.getTo() + ","
				+ metadati.getIdConferenza() + "]");
	}

	/**
	 * TODO: occorre settare una tabella di log delle email in errore
	 */
	public void doAErrorSendEmail(MailMetadata metadati) {
		LOGGER.debug("[doAErrorSendEmail]: problemi nella trasmissione dell'email per [" + metadati.getTo() + ","
				+ metadati.getIdConferenza() + "]");
	}

	/**
	 * Metrodo Generico: per tutti gli invii
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param textmessage
	 */
	public void sendMailIndizione(MailMetadata metadati) {
		sendMailIndizioneByMetadata(metadati);
	}

	/**
	 * @TODO: eseguire la query ed innoltrare le email
	 * @param metadati
	 */
	public void sendMailVotazione(MailMetadata metadati) {
		// in
		sendMailIndizioneByMetadata(metadati);

	}

	public String getUrlIndizione(Mailer mailer, TockenConference token) {
		String baseUrlIndizione = mailSenderConfigurator.getBaseUrlIndizione();
		if (baseUrlIndizione == null) {
			baseUrlIndizione = "https://meetpad.regione.marche.it/join";
		}
		return baseUrlIndizione + "/" + token.getTKN1() + "/" + token.getTKN2();
	}

	/**
	 * @deprecated Metrodo Generico: per tutti gli invii
	 * @param from
	 * @param to
	 * @param subject
	 * @param textmessage
	 */
	public void sendMail(String from, String to, String subject, String textmessage, String cc) {
		meansender = mailSenderConfigurator.javaMailSender();
		mm = mailSenderConfigurator.createMimeMessage();
		helper = new MimeMessageHelper(mm);
		try {
			helper.setFrom(from);
			helper.setTo(getAddress(to));
			helper.setSubject(subject);
			// in questafase si prevede un unico cc!!
			if (cc != null)
				helper.setCc(getAddress(cc));
			String message = mailbuilder.build(textmessage);
			helper.setText(message, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// -------------------------------------
		meansender.send(mm);
	}

	private String getAddress(String address) {
		return mailSenderConfigurator.isFakeRecipient() ? mailSenderConfigurator.getFakeRecipientAddress() : address;
	}

	private String getFakeRecipientAddress() {
		return mailSenderConfigurator.isFakeRecipient() ? mailSenderConfigurator.getFakeRecipientAddress() : null;
	}

	/**
	 * Spedisce mail per tutte le conferenze per cui c'è un indizione..
	 * 
	 * @param from
	 * @param textmessage
	 */
	public void sendMailAllIndette(String from, String subject, String tokenaccreditamento) {
		List<Mailer> mailerList = findAllMailerDTO();
		sendMailIndizioneToList(mailerList, from, null, subject, tokenaccreditamento, null);
	}

	/**
	 * Il metodo si occupa della rappresentazine dell'informazione lo scopo è quello
	 * di richiamarlo poi tamite i servizzi Asincroni per la ritrasmissione delle
	 * email senza dover persistere il corpo della email
	 * 
	 * @param mail
	 * @param from
	 * @param textmessage
	 * @param subject
	 * @param tokenaccreditamento
	 * @return
	 */
	public MailMetadata formatTestoEmailIndizione(Mailer mail, String from, String textmessage, String subject,
			String tokenaccreditamento) {

		TockenConference token = getTocken(mail.getEmail(), mail.getId_ente(), mail.getId_partecipante(),
				mail.getId_conferenza(), mail.getCodice_fiscale(), "indizione");

		MailMetadata metadati = new MailMetadata(mail, getFakeRecipientAddress(), from, null, subject, textmessage,
				this.getUrlIndizione(mail, token), tokenaccreditamento,
				this.getInfoVideoconferenza(mail.getId_conferenza()));

		// TODO: Locale impostato fisso
		String tipologiaConferenza = messageSource.getMessage(mail.getDescrizione_tipologia_conferenza_specializzazione(), null,
				mail.getDescrizione_tipologia_conferenza_specializzazione(), Locale.ITALIAN);
		String ruoloPersona = messageSource.getMessage(mail.getDescrizione_ruolo_persona(), null,
				mail.getDescrizione_ruolo_persona(), Locale.ITALIAN);
		String oggettoEmail = emailStrategy.generateSubject(mail);
		metadati.setSubject(oggettoEmail);
		// @TODO: aggiungere paremetri exta da visualizzare nell'email...
		Map<String, String> parametri = metadati.getParametri();
		parametri.put("idConferenza", String.valueOf(mail.getId_conferenza()));
		parametri.put("amministrazioneProcedente", String.valueOf(mail.getDescrizione_amministrazione_procedente()));
		parametri.put("tipoConferenza", tipologiaConferenza);
		parametri.put("riferimentoIstanza", mail.getRiferimento_istanza());
		parametri.put("oggettoDetermina", mail.getOggetto_determinazione());
		parametri.put("ruolo", ruoloPersona);
		parametri.put("competenza", (mail.getCompetenza()));
		parametri.put("termineRichiestaIntegrazioni", formatDate(mail.getTermine_richiesta_integrazioni_conferenza()));
		parametri.put("termineEspressioneDeterminazioni", formatDate(mail.getTermine_espressione_pareri()));
		parametri.put("data_termine", formatDate(mail.getData_termine()));
		parametri.put("dataSessioneSimultanea", formatDate(mail.getPrima_sessione_simultanea()));
		parametri.put("indirizzoSessioneSimultanea", mail.getIndirizzo_sessione_simultanea());
		parametri.put("capSessioneSimultanea", mail.getCap_sessione_simultanea());
		parametri.put("comuneSessioneSimultanea", mail.getDescrizione_comune_sessione_simultanea());
		parametri.put("provinciaSessioneSimultanea", mail.getDescrizione_provincia_sessione_simultanea());
		parametri.put("descrizione_ente", mail.getDescrizione_ente());
		
		metadati.setUrlDocumentazione(urlDocumentazione);
		
		if (mail.getOrario_conferenza() != null) {
			parametri.put("orarioConferenza",
					mail.getOrario_conferenza().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString());
		}

		parametri.put("token1", token.getTKN1());
		parametri.put("token2", token.getTKN2());

		Conferenza conferenza = new Conferenza();
		conferenza.setIdConferenza(mail.getId_conferenza());
		List<ContattoSupporto> listaContatti = contattoSuppRepo.findByConferenza(conferenza);
		String[] supportList = { "" };
		listaContatti.forEach(supp -> { 
				supportList[0] += supp.getNome() + " " + supp.getCognome() + ": (tel: " + supp.getTelefono() + ", email: " + supp.getEmail() + ")~";
			});
		parametri.put("listaContattiSupporto", supportList[0]);
		
		TipologiaConferenzaSpecializzazione tipologiaConferenza2 = this.tipoConfSpecRepo
				.findByDescrizione(mail.getDescrizione_tipologia_conferenza_specializzazione()).orElse(null);
		TipoEvento tipoEvento = this.tipoEventoRepo
				.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA)).orElse(null);
		String nomeTemplate;
		
		//Conferenza confObjComplete = this.conferenzaService.findConferenzaByIdFiltrata(mail.getId_conferenza());
		// S.D. - Modificato il 13/07/2020  - Sostituito col metodo sottostante perchè quello di sopra richiede il token di autenticazione
		Conferenza confObjComplete = this.conferenzaService.findConferenceById(mail.getId_conferenza());
		
		Azione azione = this.azioneRepo.findById(confObjComplete.getAzione().getCodice()).orElse(null);
		
		
		if(confObjComplete != null)
			LOGGER.info("EmailRepositoryService - formatTestoEmailIndizione - tipologiaConferenza2: " + confObjComplete.getTipologiaConferenzaSpecializzazione().getCodice() + " e tipo evento vale: " + tipoEvento.getCodice() + " e azione vale: " + azione.getCodice());
		
		if (tipologiaConferenza2 != null && tipoEvento != null) {
			if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(confObjComplete.getTipologiaConferenzaSpecializzazione().getCodice())) {

				Optional<Template> query;
				query =  this.templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEventoAndAzione(tipologiaConferenza2, tipoEvento, azione);
				
				if(query.orElse(null) !=null) {
					LOGGER.info("Ho trovato un record con la triade");
					parametri.put("template", query.orElse(null).getNomeTemplate());
				}
				else {
					LOGGER.info("Entro qua e faccio il findFirst");
					nomeTemplate = this.templateRepo
							.findFirstByTipologiaConferenzaSpecializzazioneAndTipoEvento(tipologiaConferenza2, tipoEvento).orElse(null)
							.getNomeTemplate();
					LOGGER.info("Ho fatto il findFirst e trovo nomeTemplate: " + nomeTemplate);
					parametri.put("template", nomeTemplate);
				}
		}
		else {			
			LOGGER.info("La conferenza non è usr");
			nomeTemplate = this.templateRepo
					.findByTipologiaConferenzaSpecializzazioneAndTipoEvento(tipologiaConferenza2, tipoEvento).orElse(null)
					.getNomeTemplate();
			parametri.put("template", nomeTemplate);
			}
		}
		metadati.setParametri(parametri);

		metadati.setMittente(mail.getDescrizione_amministrazione_procedente());
		metadati.setDestinatario(getDestinatario(mail));

		return metadati;
	}

	private String getDestinatario(Mailer mail) {
		String nome = mail.getNome();
		String cognome = mail.getCognome();
		String ente = mail.getDescrizione_ente();
		if (nome != null && !nome.isEmpty() && cognome != null && !cognome.isEmpty()) {
			return nome.concat(" ").concat(cognome).concat(" [").concat(ente).concat("]");
		} else
			return ente;
	}

	/**
	 * 
	 * @param mailerList
	 * @param from
	 * @param textmessage
	 * @param subject           : prima parte dell'oggetto della email ; la seconda
	 *                          parte è l'oggetto della determina
	 * @param registroDocumento
	 */
	public void sendMailIndizioneToList(List<Mailer> mailerList, String from, String textmessage, String subject,
			String tokenaccreditamento, Documento documento) {		
		for (Mailer mail : mailerList) {
				sendMailIndizioneToOne( mail,  from,  textmessage,  subject,tokenaccreditamento,  documento, false);
		}
	}
	
	
	/**
	 * 
	 * @param mail
	 * @param from
	 * @param textmessage
	 * @param subject
	 * @param tokenaccreditamento
	 * @param documento
	 */
	public void sendMailIndizioneToOne(Mailer mail, String from, String textmessage, String subject,
			String tokenaccreditamento, Documento documento, boolean sendnonpec) {		
			// Livello - RAPPRESENTAZIONE dell'informazione
			MailMetadata metadati = formatTestoEmailIndizione(mail, from, textmessage, subject, tokenaccreditamento);
			/*
			 * TODO: il documento se presente è sempre allegato,
			 * per cui non è utilizzato attualmente il flag TipoEvento.flagAllegato (indica se è previsto o meno l'allegato)
			 */
			if (documento != null) {
				metadati.getAttachments().add(documento);
			}
			// Livello - TRASPORTO dell'informazione
			sendMailIndizioneByMetadata(metadati, sendnonpec);
			// sendMail(from, mail.getEmail(),subject, textmessage,null);
	}
	

	public TockenConference getTocken(String email, Integer ente, Integer idPartecipante, Integer conferenza, String CF,
			String fase) {
		TockenConference tocken = tockenService.saveTocken(email, ente, idPartecipante, conferenza, CF, fase);
		return tocken;
	}

	/**
	 * @TODO: implementare il check dei parametri del token
	 * @param tkn1
	 * @param tkn2
	 * @return
	 */
	public boolean checkTocken(String tkn1, String tkn2) {
		// 1--fare l'update per le righe con tkn1 ed tkn2 ..per monitorare i tentativi
		// di accesso..
		// 2--ottenere l'oggetto token per i due parametri e ritornare true se c'è il
		// match
		TockenConference tocken = tockenService.checkTockenConference(tkn1, tkn2);
		if (tocken != null & tocken.getEmail() != null)
			return true;
		return false;
	}

	/**
	 * select cc.localizzazione_indirizzo from conferenza cc where
	 * cc.id_conferenza=4
	 * 
	 * @param pIdConferenza
	 * @return
	 */
	public String getInfoVideoconferenza(Integer pIdConferenza) {
		return tockenService.findInfoVideoconferenza(pIdConferenza);
	}

	/**
	 * Aggiunge la listaDestinatari alla listaPartecipanti (di tipo Partecipante) e invia mail alla listaPartecipanti
	 * manda mail anche a eventuali firmatari (di tipo Accreditamento) recuperandoli dal documento
	 * @param evento
	 * @param documento
	 * @param listaDestinatari
	 * @param listaPartecipanti
	 * @param utente
	 * @throws MessagingException
	 */
	@Async("threadPoolTaskExecutor")
	@Transactional
	public void sendMailEvento(Integer idEvento, Integer idDocumento, List<LabelValue> listaDestinatari,
			List<Partecipante> listaPartecipanti, Integer idUtente, List<ModificaData> modificheDate) throws MessagingException {
		
		LOGGER.debug("sendMailEvento (Async)");
		LOGGER.debug("idEvento = " + idEvento + " - idDocumento = " + idDocumento);
		listaPartecipanti.stream().forEach(p -> listaDestinatari
				.add(new LabelValue(Integer.toString(p.getIdPartecipante()), p.getDescEnteCompetente())));
		LOGGER.debug("recupero la gli oggetti Evento e Documento completi");
		//Evento eventoPieno = eventoRepo.findById(idEvento).orElse(null);
		Documento docPieno = (idDocumento == null) ? null: docRepo.findById(idDocumento).orElse(null);
		//Utente utentePieno = utenteRepo.findById(idUtente).orElse(null);
		LOGGER.debug("oggetti recuperati");
		if (docPieno != null) {
			for (Accreditamento accr : docPieno.getFirmatari()) {
				sendEmailEvento(idEvento, accr.getPersona().getCognome() + " " + accr.getPersona().getNome(),
						accr.getPersona().getEmail(), idDocumento, idUtente,null);
			}
		}
		
		for (LabelValue labelValue : listaDestinatari) {
			
			Optional<Partecipante> optionaPartecipante = partRepo.findById(Integer.parseInt(labelValue.getKey()));
			if (optionaPartecipante.isPresent()) {
				Partecipante partecipante = optionaPartecipante.get();
				//String descRuoloPartecipante = partecipante.getRuoloPartecipante().getDescrizione();
				//LOGGER.debug("[CDST-114: EmailRepositoryService - sendMailEvento] Il ruolo partecipante è: " + descRuoloPartecipante);
				LOGGER.debug("partecipante pec: "+partecipante.getPecEnteCompetente());
				//if(!descRuoloPartecipante.equals(descrizione)) {
				if (!partecipante.isRichiedente()) {
					sendEmailEvento(idEvento, partecipante.getDescEnteCompetente(),
							partecipante.getPecEnteCompetente(), idDocumento, idUtente,modificheDate);
				}
				partecipante.getAccreditati().stream().filter(a -> a.getFlagAccreditato())
						.forEach(a -> sendEmailEvento(idEvento,
								a.getPersona().getCognome() + " " + a.getPersona().getNome(), a.getPersona().getEmail(),
								idDocumento, idUtente, modificheDate));
				partecipante.getAltreEmail().stream().forEach(e -> sendEmailEvento(idEvento,
						partecipante.getDescEnteCompetente(), e.getEmail(), idDocumento, idUtente,modificheDate));
				//} else
				//	LOGGER.debug("[CDST-114 - EmailRepositoryService - sendMailEvento] Non invio la mail perché il ruolo partecipante è: " + descRuoloPartecipante);
			}
		}
	}
	
	public List<Partecipante> creaDestinatariPerRuoloPart(Evento evento, List<EventoPartecipante> listaEventoPartecipante) {
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		Partecipante partecipanteUtente = utenteService.getAccreditamento(evento.getConferenza(), true).getPartecipante();
		Boolean isAmministrazionePerPareriInterni = (evento.isRichiestaIntegrazione()
				|| evento.isEspressionePareri()) && partecipanteUtente.isAmministrazionePerPareriInterni();
		if (!isAmministrazionePerPareriInterni) {
			for (EventoPartecipante eventoPart : listaEventoPartecipante) {
				evento.getConferenza().getPartecipantes().stream().filter(
						p -> p.getRuoloPartecipante().getCodice().equals(eventoPart.getRuoloPartecipante().getCodice()))
						.forEach(p -> listaPartecipanti.add(p));
			}
		} else {
			for (Partecipante part: evento.getConferenza().getPartecipantes()) {
				if (partecipanteUtente.getEnte().getCodiceOuPadre() != null && (partecipanteUtente.getEnte().getCodiceOuPadre()
						.equals(part.getEnte().getCodiceOuPadre()) || partecipanteUtente.getEnte().getCodiceOuPadre()
						.equals(part.getEnte().getCodiceOu()))) {
					listaPartecipanti.add(part);
				}
			}
		}
		return listaPartecipanti;
	}
	
	// xmf: changed to support scheduler send mail job!
	public List<Partecipante> creaDestinatariPerRuoloPartFromScheduler(Evento evento, List<EventoPartecipante> listaEventoPartecipante) {
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		Partecipante partecipanteUtente = utenteService.getAccreditamentoFromCreator(evento.getConferenza()).getPartecipante();
		Boolean isAmministrazionePerPareriInterni = (evento.isRichiestaIntegrazione()
				|| evento.isEspressionePareri()) && partecipanteUtente.isAmministrazionePerPareriInterni();
		if (!isAmministrazionePerPareriInterni) {
			for (EventoPartecipante eventoPart : listaEventoPartecipante) {
				evento.getConferenza().getPartecipantes().stream().filter(
						p -> p.getRuoloPartecipante().getCodice().equals(eventoPart.getRuoloPartecipante().getCodice()))
						.forEach(p -> listaPartecipanti.add(p));
			}
		} else {
			for (Partecipante part: evento.getConferenza().getPartecipantes()) {
				if(!"2".equals(part.getRuoloPartecipante().getCodice()) // esclude ruoloPartecipante.amministrazioneProcedente 
					&& (partecipanteUtente.getEnte().getCodiceOuPadre() != null && (partecipanteUtente.getEnte().getCodiceOuPadre()
						.equals(part.getEnte().getCodiceOuPadre()) || partecipanteUtente.getEnte().getCodiceOuPadre()
						.equals(part.getEnte().getCodiceOu())))) {
					listaPartecipanti.add(part);
				}
			}
		}
		return listaPartecipanti;
	}

	public void sendMailEventoByMetadata(MailMetadata metadati, Evento evento) {
		LOGGER.debug("sendMailEventoByMetadata [" + metadati.getTo() + "]");
		metadati.setFaseConcerenza(evento.getTipoEvento().getDescrizione());
		metadati.setCodiceTipoEvento(evento.getTipoEvento().getCodice());
		metadati.setCorpoEvento(evento.getCorpo());
		String message = mailbuilder.buildTemplateEmail(metadati);
		metadati.setMessage(message);
		// ----------------------------------------------
		sendMail(metadati, false);
		// ----------------------------------------------
	}

	//private MailMetadata sendEmailEvento(Evento evento, String destinatario, String email, Documento documento, Utente utente) { S.D. firma prec.
	private MailMetadata sendEmailEvento(Integer idEvento, String destinatario, String email, Integer idDocumento, Integer idUtente,List<ModificaData> modificheDate) {
		LOGGER.debug("l'id evento arrivato è " + idEvento);
		LOGGER.debug("l'id docuemento arrivato è " + idDocumento);
		LOGGER.debug("richiamo il metodo sendEmailEvento sottostante");
		return sendEmailEvento(idEvento, destinatario, email, idDocumento, idUtente, true,modificheDate);
	}
	
	//private MailMetadata sendEmailEvento(Evento evento, String destinatario, String email, Documento documento, Utente utente, boolean sendNow) {	S.D. firma prec.
	private MailMetadata sendEmailEvento(Integer idEvento, String destinatario, String email, Integer idDocumento, Integer idUtente, boolean sendNow, List<ModificaData> modificheDate) {

		LOGGER.debug("Recupero l'oggetto Evento completo tramite l'id: " + idEvento);
		Evento evento = eventoRepo.findById(idEvento).orElse(null);
		LOGGER.debug("Recupero il Documento completo tramite l'id: " + idDocumento);
		Documento documento = docRepo.findById(idDocumento).orElse(null);
		LOGGER.debug("Recupero l'Utente completo tramite l'id: " + idUtente);
		Utente utente = utenteRepo.findById(idUtente).orElse(null);
		LOGGER.debug("oggetti recuperati");
		MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
		//metadata.setFrom(evento.getMittente().getPecEnteCompetente());
		if (evento == null) {
			throw new NotFoundEx("Event not found");
		}
		metadata.setFrom(from);
		metadata.setSubject(evento.getConferenza().getRiferimentoIstanza() + " - " + evento.getOggettoEvento().getDescrizione());
		metadata.setTo(email);
		metadata.setIdConferenza(evento.getConferenza().getIdConferenza());
		Map<String, Object> parametri = metadata.getParametri();
		parametri.put("idConferenza", Integer.toString(evento.getConferenza().getIdConferenza()));
		parametri.put("mittente", evento.getMittente() != null ? evento.getMittente().getDescrizione() : null);
		parametri.put("tipoEvento", evento.getTipoEvento().getDescrizione());
		parametri.put("dataEvento", formatDate(evento.getData()));
		parametri.put("destinatario", destinatario);
		parametri.put("amministrazioneProcedente", evento.getConferenza().getAmministrazioneProcedente().getDescrizioneEnte());
		parametri.put("urlAccessoCDST", urlAccessoCDST+evento.getConferenza().getIdConferenza());

		metadata.setMittente(evento.getMittente() != null ? evento.getMittente().getDescrizione() : null);
		metadata.setDestinatario(destinatario);
		metadata.setTipologiaConferenza(evento.getConferenza().getTipologiaConferenzaSpecializzazione().getCodice());
		
		parametri.put("riferimentoIstanza", evento.getConferenza().getRiferimentoIstanza());
		parametri.put("oggettoDetermina", evento.getConferenza().getOggettoDeterminazione());
		parametri.put("utente", utente.getCognome() + " " + utente.getNome());
		parametri.put("impresa", evento.getConferenza().getImpresaDenominazione());

		if (modificheDate != null && modificheDate.size() > 0) {
			
			List<ModificaDataReport> modificheDateReport = new ArrayList<ModificaDataReport>();
			for (int i = 0; i < modificheDate.size(); i++)  {
				ModificaData md = modificheDate.get(i);
				String codiceEvento = "";
				switch (md.getTipoData().getCodice()) {
				    case DbConst.DATA_TERMINE:
				    	codiceEvento = "Data termine";
				    	break;
				    case DbConst.DATA_TERMINE_ESPRESSIONE_PARERI:
				    	codiceEvento = "Termine espressione pareri";
				    	break;
				    case DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA:
				    	codiceEvento = "Termine richiesta integrazioni conferenza";
				    	break;
				    case DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA:
				    	codiceEvento = "Prima sessione simultanea";
				    	break;
				    default:
				    	break;
				}
				modificheDateReport.add(new ModificaDataReport(codiceEvento, formatDate(md.getData()), formatDate(md.getDataNew())));
				/*
				if (i == modificheDate.size() - 1)  {
					tipoModificaData +=  "<SPAN>" + codiceEvento + ": " + formatDate(md.getDataNew()) + "</SPAN>";
				} else {
					tipoModificaData +=  "<SPAN>" + codiceEvento + ": " + formatDate(md.getDataNew()) + "</SPAN><BR>";
				}
				*/
			}
			parametri.put("tipoDataModifica", modificheDateReport);
		} 
		/*
		else {
			parametri.put("tipoDataModifica", evento.getModificaData() != null ? evento.getModificaData().getTipoData().getCodice() : null);
			parametri.put("dataModifica", evento.getModificaData() != null ? formatDate(evento.getModificaData().getDataNew()) : null);
		}
		*/
		
		
		/*
		 * TODO: il documento se presente è sempre allegato,
		 * per cui non è utilizzato attualmente il flag TipoEvento.flagAllegato (indica se è previsto o meno l'allegato)
		 */
		if (evento.getTipoEvento().getFlagAllegato()) {
			if (documento != null) {
				metadata.getAttachments().add(documento);
				// allego anche tutti i documenti allegati
				List<Documento> listaDocAllegati = getAttachments(documento);
				if(!listaDocAllegati.isEmpty())
					metadata.getAttachments().addAll(listaDocAllegati);
			}
		}

		if(sendNow)
			sendMailEventoByMetadata(metadata, evento);

		return metadata;
	}

	private List<Documento> getAttachments(Documento documento) {
		return docRepo.findByfkDocParent(documento.getIdDocumento());
	}
	/**
	 * invio mail di test
	 * @param email
	 * @param oggetto
	 * @param corpo
	 */
	public void sendTestEmail(String email, String oggetto, String corpo) {
		MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
		//metadata.setFrom(evento.getMittente().getPecEnteCompetente());
		metadata.setFrom(from);
		metadata.setSubject(oggetto);
		metadata.setTo(email);
		metadata.setMessage(corpo);
		metadata.setIdConferenza(1);

		LOGGER.debug("sendTestEmail [" + metadata.getTo() + "]");
		try {
			// ---------------------------------------
			String messageId = null;
			if (!isPec(metadata)) {
				try {
					messageId = sendMailByMetadata(metadata);
					metadata.setMessageId(messageId);
				} catch (MessagingException e) {
					LOGGER.debug("Errore [" + e.getMessage() + "]");
					e.printStackTrace();
				}
			} else {
				messageId = this.sendSSLMail(metadata);
				if (messageId == null)
					throw new Exception("Attenzione: l'email deve essere valorizzata e deve essere una PEC.");

				metadata.setMessageId(messageId);
				LOGGER.debug(" metadati.getMessageId " + metadata.getMessageId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reinvio mail prendendo i dati da CruscottoPec
	 * @param allegati 
	 * @param email
	 * @param oggetto
	 * @param corpo
	 */
	public void resendEmail(CruscottoPec cruscottoPec, List<Documento> allegati) {
		MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
		metadata.setFrom(from);
		metadata.setSubject(cruscottoPec.getOggettoMail());
		metadata.setTo(cruscottoPec.getEmailDestinatario());
		metadata.setMessage(cruscottoPec.getCorpoMail());
		metadata.setIdConferenza(cruscottoPec.getIdConferenza());
		metadata.setFaseConcerenza(cruscottoPec.getFaseEmail());
		metadata.setCodiceTipoEvento(cruscottoPec.getCodiceTipoEvento());
		metadata.setMittente(cruscottoPec.getMittente());
		metadata.setDestinatario(cruscottoPec.getDestinatario());
		
		metadata.setAttachments(allegati);

		sendMail(metadata, false);
	}

	/**
	 * @deprecated
	 * @param metadati
	 * @return
	 * @throws MessagingException
	 */
	public String sendMailByMetadata(MailMetadata metadati) throws MessagingException {

		String message = metadati.getMessage();
		LOGGER.debug("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");

		meansender = mailSenderConfigurator.javaMailSender();
		mm = mailSenderConfigurator.createMimeMessage();
		helper = new MimeMessageHelper(mm, true, "UTF-8");
		try {
			helper.setFrom(mailSenderConfigurator.getFrom());
//			helper.setFrom(metadati.getFrom());
			helper.setTo(getAddress(metadati.getTo()));
			helper.setSubject(metadati.getSubject());
			// in questafase si prevede un unico cc!!
			if (metadati.getCc() != null)
				helper.setCc(getAddress(metadati.getCc()));

			// caricamento dello specifico template
			helper.setText(message, true);

			for (Documento documento : (List<Documento>) metadati.getAttachments()) {
				File file = registroDocumentoService.loadFileFromDocumento(documento);
				if (file != null) {
					helper.addAttachment(file.getName(), file);
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		// -------------------------------------
		try {
			meansender.send(mm);
			// dopo la trasmission occorre settare il database..
			doAfterSendEmail(metadati);
		} catch (Exception e) {
			LOGGER.debug(
					"[Errore]: problemi nella trasmissione dell'email per [" + metadati.getTo() + "]" + e.getMessage());
			doAErrorSendEmail(metadati);
		}

		return mm.getMessageID();
	}

	/*
	 * public void sendMailByMetadata(MailMetadata metadati, String message) {
	 * 
	 * LOGGER.debug("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");
	 * 
	 * meansender = mailSenderConfigurator.javaMailSender(); mm =
	 * mailSenderConfigurator.createMimeMessage(); helper = new
	 * MimeMessageHelper(mm); try { helper.setFrom(metadati.getFrom());
	 * helper.setTo(getAddress(metadati.getTo()));
	 * helper.setSubject(metadati.getSubject()); // in questafase si prevede un
	 * unico cc!! if (metadati.getCc() != null)
	 * helper.setCc(getAddress(metadati.getCc()));
	 * 
	 * // caricamento dello specifico template helper.setText(message, true); }
	 * catch (MessagingException e) { e.printStackTrace();
	 * LOGGER.debug(e.getMessage()); } // ------------------------------------- try {
	 * meansender.send(mm); // dopo la trasmission occorre settare il database..
	 * doAfterSendEmail(metadati); } catch (Exception e) { LOGGER.debug(
	 * "[Errore]: problemi nella trasmissione dell'email per [" + metadati.getTo() +
	 * "]" + e.getMessage()); doAErrorSendEmail(metadati); } }
	 */

	// =======================================================================================
	// SSL Method - BEGIN
	// =======================================================================================
	/**
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	public String getFromHeadersStatus(Message message) throws MessagingException {
		Enumeration<Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
//			 LOGGER.debug("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			if ("Subject".equals(h.getName())) {
				String sbj = h.getValue();
				if (sbj.lastIndexOf("CONSEGNA:") == 0) {
					// return "CONSEGNA";
					return DbConst.EMAIL_STATUS_CONSEGNA;
				} else if (sbj.lastIndexOf("ACCETTAZIONE:") == 0) {
					// return "ACCETTAZIONE";
					return DbConst.EMAIL_STATUS_ACCETTAZIONE;
				} else if (sbj.lastIndexOf("AVVISO DI NON ACCETTAZIONE:") == 0) {
//					dumpMineMessage(message);
					// return "AVVISO DI NON ACCETTAZIONE";
					return DbConst.EMAIL_STATUS_AVVISODINONACCETTAZIONE;
				} else if (sbj.lastIndexOf("AVVISO DI MANCATA CONSEGNA:") == 0) {
//					dumpMineMessage(message);
					// return "AVVISO DI MANCATA CONSEGNA";
					return DbConst.EMAIL_STATUS_AVVISODIMANCATACONSEGNA;
				} else if (sbj.lastIndexOf("Creazione nuova casella posta certificata") == 0) {
//					dumpMineMessage(message);
				} else {
					//LOGGER.debug("STATO IS : " + h.getValue());
				}
			}
		}
		return null;
	}

	/**
	 * parametro Message-ID del SMTP Header
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 */

	public String getFromMessageIdServer(Message message) throws MessagingException {
		Enumeration<Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			// LOGGER.debug("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			if ("Message-ID".equals(h.getName())) {
				String mid = h.getValue();
				return mid;
			}
			if ("Message-Id".equals(h.getName())) {
				String mid = h.getValue();
				return mid;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param message
	 * @throws MessagingException
	 */
	public void dumpHeadersMessage(Message message) throws MessagingException {
		Enumeration<Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			LOGGER.debug("Header : " + " " + h.getName() + " -- " + h.getValue());
		}
	}

	public Date getFromMessageSentDate(Message message) throws Exception {
		return message.getSentDate();
	}

	/**
	 * 
	 * @param message
	 * @throws MessagingException
	 */
	public void dumpMineMessage(Message message) throws MessagingException {
		LOGGER.debug("-----------  DUMP MEIME MESSAGE  -----------BEGIN ");
		dumpHeadersMessage(message);
		LOGGER.debug("From : " + message.getFrom()[0]);
		LOGGER.debug("Recipients : " + message.getAllRecipients()[0]);
		LOGGER.debug("Subject : " + message.getSubject());
		LOGGER.debug("Sent Date : " + message.getSentDate());
		LOGGER.debug("-----------  DUMP MEIME MESSAGE  -----------END ");

	}

	// =======================================================================================
	public void checkEmailStatus(Integer pIdConferenza) {
		storeEmailStatus(pIdConferenza);
	}

	/**
	 * 
	 * @param pIdConferenza
	 * @return
	 */
	protected void storeEmailStatus(Integer pIdConferenza) {
		List<MailMetadata> metadataList = getEmailPersistenceStatus(pIdConferenza);
		for (MailMetadata metadata : metadataList) {
			MailStatus retStatus = getEmailTransportStatus(metadata);
		}

	}

	/**
	 * 
	 * @param metadati
	 */
	public List<MailMetadata> getEmailPersistenceStatus(Integer pIdConferenza) {
		List<MailMetadata> metadataList = repositoryReportEmail(pIdConferenza);
		return metadataList;
	}

	/**
	 * ottiene l'ultimo STATO utile per un dato messaggio.. metadati.getMessageId()
	 * metadati.getTo() --ATTENZIONE-- in caso di disallineamento degli orologgi può
	 * capitare che l'orario di trasmissione STATO INOLTRATO sia diverso dagli stati
	 * di consegna ed accettazione..questo può falsare il valore ritornato..
	 * 
	 * @param metadati
	 */
	public MailStatus getEmailTransportStatus(MailMetadata metadati) {

		ArrayList<Message> mesageToBkp = new ArrayList<Message>();
		MailStatus retStatus = null;
		String pMessageId = null;
		pMessageId = metadati.getMessageId();
		List<MailStatus> listMailStatus = null;
		Date statusDate = null;
		int dtCompare = 0;
		String statomessaggio = null;
		try {
			listMailStatus = getSSLMailStatus(pMessageId);
			// messaggi di cui fare il bkp..
			for (MailStatus status : listMailStatus) {
				mesageToBkp.add(status.getMessage());
			}
			for (MailStatus status : listMailStatus) {
				// STORE REGISTRO - DATI DETTAGLIO
				// persistenza del messaggio
				try {
					doPersistenceRegistroEmailDettaglio(status, null);
				} catch (Exception e) {
					String errMeg = e.getMessage();

					if (errMeg != null && !"".equals(errMeg))
						if (errMeg.lastIndexOf("indx_registro_email_dettaglio") < 0) {
							LOGGER.debug(" [ERROR]: " + e.getMessage());
							throw new Exception(e);
						} else
							LOGGER.debug(
									" [ERRORE GESTITO]: duplicate key value violates unique constraint - cancellazione su SMTP non ancora avvenuta!");
				}
				// ---------------------------------------------------
				LOGGER.debug(" STATUS:--> " + status.getMessageStatus());
				Date statusDate2 = status.getMessageSentDate();
				if (statusDate != null) {
					dtCompare = statusDate.compareTo(statusDate2);
					if (dtCompare < 0) {
						statusDate = statusDate2;
						statomessaggio = status.getMessageStatus();
					}
				} else {
					statusDate = statusDate2;
					statomessaggio = status.getMessageStatus();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// BKP dei messaggi una volta memorizzati su database
		Message[] mdgArray = makeMessageArray(mesageToBkp);
		try {
			bkpMailMsg(mdgArray);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		retStatus = new MailStatus();
		retStatus.setMessageId(metadati.getMessageId());
		retStatus.setMessageSentDate(statusDate);
		retStatus.setMessageStatus(statomessaggio);
		// doTransportChangeDir()
		return retStatus;
	}

	public void doTransportChangeDir() {

	}

	public void doPersistenceRegistroEmailDettaglio(MailStatus status, RegistroEmailTestata registroTestata) {
		RegistroEmailDettaglio dettaglio = new RegistroEmailDettaglio(status.getMessageId(), status.getMessageStatus(),
				status.getMessageSentDate(), getNota(status), registroTestata);
		repositoryRegistroEmailDettaglio.save(dettaglio);
	}
	
	/**
	 * DLG: La nota viene utilizzata dalla fista cruscotto_pec per popolare il campo STATUS_MESSAGE
	 * questo campo vontrolla l'invio delle emaill come NON PEC 
	 * QUANDO contiene la sotto-stringa "solamente verso domini di posta elettronica certificata" 
	 * ovvero per 
	 * - il dominio emarche.it puo' spedire solamente verso domini di posta elettronica
	 * 
	 * @param status
	 * @return
	 */
	private String getNota(MailStatus status) {
		
		//DLG: 20190612 - gestione del campo note ..per il dettaglio..
		if(status.getMessageNote()!=null)
			return status.getMessageNote();
		
		if (status.getMessageStatus() != null
				&& (status.getMessageStatus().equals(DbConst.EMAIL_STATUS_AVVISODIMANCATACONSEGNA)
						|| status.getMessageStatus().equals(DbConst.EMAIL_STATUS_AVVISODINONACCETTAZIONE))) {
			try {
				/*
				 * il campo nota nel db è lungo 1000
				 */
				String text = getTextFromMimeMultipart((MimeMultipart) status.getMessage().getContent());
				return text.substring(0, 1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
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

	public List<MailStatus> getSSLMailStatus(String pMessageId) throws Exception {
		List listMailStatus = new ArrayList<>();
		MailStatus retStatus = new MailStatus();
		// mail server connection parameters
		String host = mailSenderConfigurator.getPop3Host();
		String username = mailSenderConfigurator.getFrom();
		String pass = mailSenderConfigurator.getPassword();
		// connect to my pop3 inbox
		Session session = null;
		// Properties
		Properties props = new Properties();
		final String PROTOCOL = "pop3";
		final String PORT = mailSenderConfigurator.getPop3Port();
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", PORT);
		props.setProperty("mail.pop3.socketFactory.port", PORT);
		URLName urln = new URLName(PROTOCOL, host, Integer.parseInt(PORT), null, username, pass);
		session = Session.getInstance(props, null);
		Store store = session.getStore(urln);
		store.connect();
		// FOLDER
		Folder inbox = store.getFolder(mailSenderConfigurator.getPop3Folder());
		inbox.open(Folder.READ_ONLY);
		// get the list of inbox messages
		Message[] messages = inbox.getMessages();
		if (messages.length == 0)
			LOGGER.debug("No messages found.");

		if (messages.length > 0) {
			// inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
			// inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
			// inbox.setFlags(messages, new Flags(Flags.Flag.DRAFT), true);
			inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
		}

		if (messages.length > 0)
			LOGGER.debug("messages found: " + messages.length);
		for (int i = 0; i < messages.length; i++) {
			// ---------------------------------------
			// TODO: commentare dopo il debug
			// bdumpMineMessage(messages[i]);
			// ---------------------------------------
			String messageId = getFromHeadersMessageId(messages[i]);

			if (pMessageId != null && !"".equals(pMessageId) && pMessageId.equals(messageId)) {
				retStatus = new MailStatus();
				String stato = getFromHeadersStatus(messages[i]);
				LOGGER.debug("messageId : " + messageId);
				LOGGER.debug(" stato " + stato);
				Date msgSentDate = getFromMessageSentDate(messages[i]);
				// E' l'id del messaggio generato dal server SMTP per la notifica..
				String lsMessageIdServer = getFromMessageIdServer(messages[i]);

				LOGGER.debug(" msgSentDate " + msgSentDate);
				retStatus.setMessageStatus(stato);
				retStatus.setMessageId(messageId);
				retStatus.setMessageSentDate(msgSentDate);
				retStatus.setMessageIdServer(lsMessageIdServer);
				retStatus.setMessage(messages[i]);
				listMailStatus.add(retStatus);
			}
			// ---------------------------------------
		}
		inbox.close(true);
		store.close();
		return listMailStatus;
	}

	private int findMailHashIndex(int mailid) {
		int[] arr = AsyncMailService.messageReadHashesArray;
		for(int i=0;i < AsyncMailService.messageReadHashesArraySize;i++)
			if(arr[i] == mailid)
				return i;

		return -1;
	}
	

	
	
	public Store getPecStore() throws Exception {

		// ============================================
		// mail server connection parameters
		String host = mailPecSenderConfigurator.getPop3Host();
		String username = mailPecSenderConfigurator.getFrom();
		String pass = mailPecSenderConfigurator.getPassword();
		// connect to my pop3 inbox
		Session session = null;
		// Properties
		Properties props = new Properties();
		final String PROTOCOL = "pop3";
		final String PORT = mailPecSenderConfigurator.getPop3Port();
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", PORT);
		props.setProperty("mail.pop3.socketFactory.port", PORT);
		URLName urln = new URLName(PROTOCOL, host, Integer.parseInt(PORT), null, username, pass);
		session = Session.getInstance(props, null);
		return session.getStore(urln);
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	private boolean existMailStatus(MailStatus status) {
		List<ReportEmailSample> listEmail = reportEmailRepository
				.getReportEmailSampleIdMessaggio(status.getMessageId(), status.getMessageStatus());
		if (listEmail == null || listEmail.isEmpty())
			return false;

		return true;

	}

	/**
	 * 
	 * @param lisMailStatus
	 */
	private void verifyAndStoredStatusList(List<MailStatus> lisMailStatus) {
		for (MailStatus status : lisMailStatus) {
			boolean exist = existMailStatus(status);
			List<RegistroEmailTestata> listaRegistroEmailTestata = repositoryRegistroEmailTestata.findByIdMessaggio(status.getMessageId());
			if (!exist && !listaRegistroEmailTestata.isEmpty()) {
				doPersistenceRegistroEmailDettaglio(status, listaRegistroEmailTestata.get(0));
			}
		}
	}

	
	/**
	 * fà un check degli allineamenti dei messaggi da POP3 ed DB
	 * 
	 * @throws Exception
	 */
	public void checkAndestoreAllStatus() throws Exception {
		List<MailStatus> lisMailStatus = null;
		
		lisMailStatus = listAllPop3SSLMail();
		LOGGER.debug("@mail listAllPop3SSLMail lisMailStatus ENDED!");
		
		verifyAndStoredStatusList(lisMailStatus);
		LOGGER.debug("@mail verifyAndStoredStatusList lisMailStatus ENDED!");
	}

	public Session initSendPecMsg(MailMetadata metadati) {
		String from = metadati.getFrom();

		String cc = metadati.getCc();
		// =================================================

		System.setProperty("javax.net.debug", "all");
		// ============================================================
		Session session;
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", mailPecSenderConfigurator.getHost()); // SMTP Host
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.port", mailPecSenderConfigurator.getPort()); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailPecSenderConfigurator.getFrom(),
						mailPecSenderConfigurator.getPassword());
			}
		};
		session = Session.getInstance(props, auth);

		return session;
	}

	public MimeMessage getMimeMessage(MailMetadata metadati, Session session) throws Exception {
		String retMessageId = null;

		String subject = metadati.getSubject();
		String textmessage = metadati.getMessage();
		String to = metadati.getTo();

		// ConferenzaMimeMessage mmsg=new ConferenzaMimeMessage(session);
		// retMessageId=mmsg.getMessageID();
		// if null generate Message id
		MimeMessage msg = new MimeMessage(session);

		// @TODO: decommentare dopo il debug!!!
		if (metadati.getMessageId() != null)
			// serve in caso di ritrasmissioni del messagio!?!?
			retMessageId = metadati.getMessageId();
		else {
			retMessageId = msg.getMessageID();
			if (retMessageId == null || "".equals(retMessageId))
				LOGGER.debug("Message-Id is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			else
				LOGGER.debug("Message-Id is :" + retMessageId);
			
			// -----------------------------------------------------------------------------------
			if (retMessageId == null || "".equals(retMessageId)) {
				msg = new ConferenzaMimeMessage(session);
				retMessageId = formatCustomMessageID(metadati);
				((ConferenzaMimeMessage) msg).setIdMessage(retMessageId);
				metadati.setMessageId(retMessageId);
				LOGGER.debug("[ConferenzaMimeMessage] - Message-id :" + retMessageId);
			}
		}
		// -----------------------------------------------------------------------------------
		msg.addHeader("Content-type", "text/html; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");
		msg.addHeader("Disposition-Notification-To", mailPecSenderConfigurator.getFrom());

		msg.setFrom(new InternetAddress(mailPecSenderConfigurator.getFrom()));
		msg.setReplyTo(InternetAddress.parse(mailPecSenderConfigurator.getFrom(), false));

		msg.setSubject(subject, "UTF-8");
		// msg.setText(textmessage, "UTF-8");

		@SuppressWarnings("unchecked")
		List<Documento> documentAttachments = metadati.getAttachments();
		List<BodyPart> attachments = getListBodyPartFromFile(documentAttachments);
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		// messageBodyPart.setText(textmessage);
		messageBodyPart.setContent(textmessage, "text/html; charset=UTF-8");
		multipart.addBodyPart(messageBodyPart);
		for (BodyPart bodyPart : attachments) {
			multipart.addBodyPart(bodyPart);
		}

		msg.setContent(multipart);
		msg.setSentDate(new Date());
		if (to != null && !to.isEmpty()) {
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
		}
		return msg;
	}

	public List<BodyPart> getListBodyPartFromFile(List<Documento> documents) throws MessagingException {
		List<BodyPart> ret = new ArrayList<>();
		for (Documento documento : documents) {
			File file = registroDocumentoService.loadFileFromDocumento(documento);
			if (file != null) {
				String name = file.getName();
				BodyPart messageBodyPart = null;
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(name);
				ret.add(messageBodyPart);
			}
		}
		return ret;
	}

	public String getCustomIdMessage(Session session) throws Exception {
		if (session == null)
			throw new Exception("ERRORE-Sessione NON valida!");

		return null;

	}

	/**
	 * Metrodo Generico: per tutti gli invii
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param textmessage
	 * @throws Exception
	 */
	public String sendSSLMail(MailMetadata metadati) throws Exception {
		// ---------------------------------------------------------------------------------
		String retMessageId = null;
		Session session;
		// ---------------------------------------------------------------------------------
		// Costruzione del messaggio SMTP
		// ---------------------------------------------------------------------------------
		// ottiene la sessione dai parametri di configurazione
		session = initSendPecMsg(metadati);
		MimeMessage msg = getMimeMessage(metadati, session);
		// L'id MEssaggio è ritornato all'intrno della costruzione del MimeMessage
		// ed è storicizzato in metadati
		retMessageId = metadati.getMessageId();
		// -----------------------------------------------------------------------------------
		LOGGER.debug("---------------------------------------------");
		LOGGER.debug("[sendSSLMail] - Message is ready");
		// ---------------------------------------------------------------------------------
		// TRasporto del messaggio
		// ---------------------------------------------------------------------------------
		SMTPMessage smtpMessage = new SMTPMessage(msg);
		smtpMessage.setReturnOption(SMTPMessage.RETURN_HDRS);
		smtpMessage
				.setNotifyOptions(SMTPMessage.NOTIFY_DELAY | SMTPMessage.NOTIFY_FAILURE | SMTPMessage.NOTIFY_SUCCESS);

		// Transport.send(smtpMessage);
		Transport trnsport;
		trnsport = session.getTransport("smtps");
		LOGGER.debug("[sendSSLMail] - Host " + mailPecSenderConfigurator.getHost());
		LOGGER.debug("[sendSSLMail] - From " + mailPecSenderConfigurator.getFrom());
		trnsport.connect(mailPecSenderConfigurator.getHost(), mailPecSenderConfigurator.getFrom(),
				mailPecSenderConfigurator.getPassword());
		msg.saveChanges();
		trnsport.sendMessage(msg, msg.getAllRecipients());
		trnsport.close();
		// ============================================================
		return retMessageId;
	}

	public List<MailMetadata> repositoryReportEmail(Integer pIdConferenza) {
		ArrayList<MailMetadata> listReturn = new ArrayList<MailMetadata>();
		List<ReportMailSampleBean> reportList = reportEmailRepository.findIdMessaggioByConferenceId(pIdConferenza);
		for (ReportMailSampleBean report : reportList) {
			MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
			metadata.setIdConferenza(pIdConferenza);
			metadata.setMessageId(report.getId_messaggio());
			metadata.setTo(report.getEmail_destinatario());
			listReturn.add(metadata);
		}
		return listReturn;
	}

	public List<ReportEmailSample> reportEmailByConference(Integer pIdConferenza) {
		return reportEmailRepository.reportEmailByConference(pIdConferenza);

	}

	private Message[] makeMessageArray(ArrayList<Message> msgList) {
		Message[] msgArr = new Message[msgList.size()];
		msgArr = msgList.toArray(msgArr);
		return msgArr;
	}

	/**
	 * 
	 * @param messages
	 * @throws MessagingException
	 */
	public void bkpMailMsg(Message[] messages) throws MessagingException {
		if (messages.length > 0) {

			String username = mailSenderConfigurator.getFrom();
			String pass = mailSenderConfigurator.getPassword();

			// connect to my pop3 inbox
			Session session = null;

			// -----------------------------------------------------
			final String PROTOCOL = "pop3";
			String HOST = mailSenderConfigurator.getPop3Host();
			final String PORT = mailSenderConfigurator.getPop3Port();
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			// Properties
			Properties props = new Properties();
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.port", PORT);
			props.setProperty("mail.pop3.socketFactory.port", PORT);
			session = Session.getInstance(props, null);
			// -----------------------------------------------------
			// connect to my pop3 inbox
			URLName urln = new URLName(PROTOCOL, HOST, Integer.parseInt(PORT), null, username, pass);
			Store store = session.getStore(urln);
			store.connect();
			// FOLDER
			Folder inbox = store.getFolder(mailSenderConfigurator.getPop3Folder());
			inbox.open(Folder.READ_WRITE); // inbox.open(Folder.READ_ONLY);

			// ------------------------------------------------------------
			// ------------------------------------------------------------
			// test
			if (messages.length > 0) {
				inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
				// inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
			}

			inbox.close(true);
			store.close();
		}
	}

	/**
	 * 
	 * @param metadati
	 * @throws Exception
	 */
	public static String formatCustomMessageID(MailMetadata metadati) throws Exception {
		String[] pTOList = null;
		String idConfrnc = null;
		if (metadati == null) {
			throw new Exception("[ERRORE]-formatCustomMessageID metadati è null");
		}
		if (metadati.getIdConferenza() != null) {
			idConfrnc = String.valueOf(metadati.getIdConferenza().intValue());
		} else {
			throw new Exception("[ERRORE]-formatCustomMessageID NON può ritornare null");
		}
		if (metadati.getTo() != null && !metadati.getTo().isEmpty()) {
			pTOList = (metadati.getTo()).split("@");
		} else {
			pTOList = new String[] { "null" };
		}
		String s1 = RandomUtil.getRandomToken();
		String s2 = RandomUtil.getRandomToken();
		return "<" + s1 + "." + idConfrnc + "." + pTOList[0] + ".conferenza@conferenza.it>";
	}

	// =======================================================================================
	// SSL Method - END
	// =======================================================================================
	class ConferenzaMimeMessage extends MimeMessage {

		private String idMessage;

		public ConferenzaMimeMessage(Session session) {
			super(session);
		}

		protected void updateMessageID() throws MessagingException {
			setHeader("Message-ID", this.idMessage);
		}

		public String getIdMessage() {
			return idMessage;
		}

		public void setIdMessage(String idMessage) {
			this.idMessage = idMessage;
		}

	}

	public String getMittenteDefault() {
		return mittenteDefault;
	}

	public void setMittenteDefault(String mittenteDefault) {
		this.mittenteDefault = mittenteDefault;
	}

	String MOTIVO_NON_ACCETTAZIONE_NON_PEC = "solamente verso domini di posta elettronica certificata";

	/**
	 * Invio mail ordinaria per invii pec in stato non-accettazione per indirizzo mail destinatario non pec
	 */
	public void sendMailOrdinariaForNonAccettazionePec() {
		List<CruscottoPec> mailNonAccettazione = cruscottoRepo
				.findByCodiceStatoPec(DbConst.EMAIL_STATUS_AVVISODINONACCETTAZIONE);
		for (CruscottoPec cruscottoPec : mailNonAccettazione) {
			if (cruscottoPec.getStatusMessage() != null
					&& cruscottoPec.getStatusMessage().replaceAll("\r\n|\r|\n", " ").contains(MOTIVO_NON_ACCETTAZIONE_NON_PEC)) {
				LOGGER.debug("@mail asyncReinoltroNonAccettazionePec NON ACCETTAZIONE PER INDIRIZZO NON PEC: " + cruscottoPec.getEmailDestinatario()
						+ " [conferenza: " + cruscottoPec.getIdConferenza() + ", evento: "
						+ cruscottoPec.getCodiceTipoEvento() + "]");
				if (!mailOrdinariaInviata(cruscottoPec)) {
					sendMailOrdinaria(cruscottoPec);
				}
				else {
					LOGGER.debug("Mail ordinaria già inviata: " + cruscottoPec.getEmailDestinatario());
				}
			}
		}
	}

	private void sendMailOrdinaria(CruscottoPec cruscottoPec) {
		
		/*
		 xmf-mail  TODO: here it is been sent just ordinary mails in case PEC "acceptance" have failed 
		 */
		
		LOGGER.debug("@mail asyncReinoltroNonAccettazionePec - reinoltro mail come ordinaria: " + cruscottoPec.getEmailDestinatario());
		/*
		 * mail aggiunta a tabella mail_pec con flag pec = false
		 */
		upsertEmailPec(cruscottoPec.getEmailDestinatario(), Boolean.FALSE);
		
		/*
		 * recupero eventuali allegati
		 */
		RegistroEmailTestata registroemailtestata = repositoryRegistroEmailTestata.findById(cruscottoPec.getId()).orElse(null);
		List<RegistroEmailAllegato> registroEmailAllegatoList = registroAllegatiRepo
				.findByRegistroEmailTestata(registroemailtestata);
		List<Documento> allegati = new ArrayList<>();
		for (RegistroEmailAllegato registroEmailAllegato : registroEmailAllegatoList) {
			allegati.add(registroEmailAllegato.getDocumento());
		}
		resendEmail(cruscottoPec, allegati);
	}

	/**
	 * verifica se esiste una mail corrispondente a quella del cruscotto inviata in modo ordinario
	 * @param cruscottoPec
	 * @return
	 */
	private Boolean mailOrdinariaInviata(CruscottoPec cruscottoPec) {
		List<CruscottoPec> mail = cruscottoRepo
				.findByEmailDestinatarioAndIdConferenzaAndCodiceTipoEventoAndCodiceStatoPec(
						cruscottoPec.getEmailDestinatario(), cruscottoPec.getIdConferenza(),
						cruscottoPec.getCodiceTipoEvento(), DbConst.EMAIL_STATUS_INOLTRATO);
		return !mail.isEmpty();
	}

	@Async("threadPoolTaskExecutor")
	@Transactional
	public void sendMailEventoModificaRichiedente(Integer idEvento, Integer idUtente, boolean isCreatore) {
		LOGGER.debug("sendEmailEvento - recupero gli oggetti dal db");
		Evento eventoPieno = eventoRepo.findById(idEvento).orElse(null);
		Utente utentePieno = utenteRepo.findById(idUtente).orElse(null);
		LOGGER.debug("oggetti recuperati");
		if (isCreatore) {
			Partecipante richiedente = eventoPieno.getConferenza().getPartecipantes().stream()
					.filter(p -> p.getRuoloPartecipante().getCodice()
							.equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)))
					.collect(Collectors.toList()).get(0);
			for (Accreditamento accreditamento : richiedente.getAccreditati()) {
				sendEmailEvento(idEvento,
						accreditamento.getPersona().getCognome() + " " + accreditamento.getPersona().getNome(),
						accreditamento.getPersona().getEmail(), null, idUtente,null);
			}
		} else {
			sendEmailEvento(idEvento, utentePieno.getCognome() + " " + utentePieno.getNome(), utentePieno.getEmail(),
					null, idUtente,null);
		}
	}

	@Async("threadPoolTaskExecutor")
	@Transactional
	public void sendMailEventoAccreditamento(Integer idEvento, Integer idAccreditamento, Boolean isResponsabile, Integer idUtente) {
		List<Persona> listaDestinatari = new ArrayList<>();
		LOGGER.debug("sendMailEventoAccreditamento - recupero l'oggetto evento completo tramite id: " + idEvento);
		Evento eventoPieno = eventoRepo.findById(idEvento).orElse(null);
		LOGGER.debug("Evento recuperato dal db");
		//Utente utentePieno = utenteRepo.findById(utente.getIdUtente()).orElse(null);
		LOGGER.debug("sendMailEventoAccreditamento - recupero l'accreditamento completo tramite id: " + idAccreditamento);
		Accreditamento accrPieno = accrRepo.findById(idAccreditamento).orElse(null);
		LOGGER.debug("Accreditamento recuperato dal db");
		if(isResponsabile) {
			LOGGER.debug("isResponsabile è true");
			Persona personaResponsabile = personaRepo
					.findByCodiceFiscaleIgnoreCase(eventoPieno.getConferenza().getCodiceFiscaleResponsabileConferenza())
					.orElse(null);
			Persona personaCreatore = personaRepo
					.findByCodiceFiscaleIgnoreCase(eventoPieno.getConferenza().getCodiceFiscaleCreatoreConferenza())
					.orElse(null);
			listaDestinatari.add(personaResponsabile);
			listaDestinatari.add(personaCreatore);
		} else {
			LOGGER.debug("isResponsabile è false");
			listaDestinatari.add(accrPieno.getPersona());
		}
		listaDestinatari.stream().forEach(p -> sendEmailEvento(idEvento, p.getCognome() + " "
				+ p.getNome(), p.getEmail(), null, idUtente,null));
	}
	

	/**
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	public String getFromHeadersMessageId(Message message) throws MessagingException {
		//LOGGER.debug("@mail message-"+message.getClass().getCanonicalName());
		//try {
		//	LOGGER.debug("@mail message-"+message.getMessageNumber() + " ; " + message.getLineCount() + " ; " + message.getSize() + " ; " + message.getFolder().getFullName() + " ; " + message.getSubject() + " ; " + message.getContentType() + " ; " + message.getFileName() + " ; " + message.getDescription());
		//} catch (Exception e) {}
		
		Enumeration<Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
//		    LOGGER.debug("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			if ("X-Riferimento-Message-ID".equals(h.getName())) {
				String mid = h.getValue();
				return mid;
			}
		}
		return null;
	}

	// =======================================================================================
	/**
	 * 
	 * @throws Exception
	 */
	public List<MailStatus> listAllPop3SSLMail() throws Exception {
		// ============================================
		List<MailStatus> listMailStatus = new ArrayList<MailStatus>();
		MailStatus retStatus = new MailStatus();
		
		Store store = null;
		Folder inbox = null;
		
		int indexOfMessage = 0;
	    int howmanymailread = 0;
		int IndexSkipped = 0;
		int indexNoId = 0;
	    
		try {
			store = getPecStore();
			LOGGER.debug("@mail store-"+store.getClass().getCanonicalName());
			
			store.connect();
			// FOLDER
			inbox = store.getFolder(mailPecSenderConfigurator.getPop3Folder());
			LOGGER.debug("@mail inbox-"+inbox.getClass().getCanonicalName());
			
			inbox.open(Folder.READ_ONLY);
			
			 // Fetch unseen messages from inbox folder
			Message[] messages = inbox.getMessages();
			LOGGER.debug("@mail messages-"+messages.getClass().getCanonicalName());
			
			//messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.DELETED), false), messages);
			//messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), false), messages);
			//messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.DRAFT), false), messages);
			//if("seen".equalsIgnoreCase(ScheduledTasks.pop3SslMessageReadSchema))
			//messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false), messages);
			//if("flagged".equalsIgnoreCase(ScheduledTasks.pop3SslMessageReadSchema))
			//messages = inbox.search(new FlagTerm(new Flags( Flags.Flag.FLAGGED), false), messages);
			//if("recent".equalsIgnoreCase(ScheduledTasks.pop3SslMessageReadSchema))
			//messages = inbox.search(new FlagTerm(new Flags( Flags.Flag.RECENT), true), messages); NOT WORKING: always 0 mail found!!!!!
			
			String lastMessageDateFormatted = null;
			if(AsyncMailService.lastPOP3sslMessageDate != null) 
				try { lastMessageDateFormatted = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.S").format(AsyncMailService.lastPOP3sslMessageDate); } catch (Throwable dateError) { }
			
			if (messages.length == 0)
				LOGGER.debug("@mail listAllPop3SSLMail-"+AsyncMailService.messageReadHashesArraySize+" - " + "No messages["+lastMessageDateFormatted+"] found!");
			if (messages.length > 0)
				LOGGER.debug("@mail listAllPop3SSLMail-"+AsyncMailService.messageReadHashesArraySize+" - " + "messages["+lastMessageDateFormatted+"] found: " + messages.length);
			
			if(AsyncMailService.messageReadHashesArray == null || AsyncMailService.messageReadHashesArray.length < messages.length) {
				int[] newarr = new int[(int)(messages.length * 1.2f)]; // 20% extra
				LOGGER.debug("@mail messageReadHashesArray resize to: "+newarr.length);
				
				if(AsyncMailService.messageReadHashesArray != null)
					for(int i = 0;i < AsyncMailService.messageReadHashesArraySize;i++)
						newarr[i] = AsyncMailService.messageReadHashesArray[i];
				
				AsyncMailService.messageReadHashesArray = newarr;
			}
			
			for (indexOfMessage = 0; indexOfMessage < messages.length; indexOfMessage++) {
				/*
				String msgHashStr = "";
				Date sd = messages[indexOfMessage].getSentDate();
				Date rd = messages[indexOfMessage].getReceivedDate();
				int sz = messages[indexOfMessage].getSize();
				String sj = messages[indexOfMessage].getSubject();
				
				if(sd != null && rd != null) msgHashStr += "" + sd.getTime() + rd.getTime();
				if(sz > 0 && sj != null && sj.length() != 0) msgHashStr += "" + sz + sj;
				
				if(msgHashStr == null || msgHashStr.length() == 0) continue;
				*/
				
				if(indexOfMessage % 10000 == 0) LOGGER.debug("@mail listAllPop3SSLMail - indexOfMessage: "+indexOfMessage);
				if(findMailHashIndex(messages[indexOfMessage].getMessageNumber()) != -1) {
					if(++IndexSkipped % 100 == 0) LOGGER.debug("@mail SKIPPED Message "+indexOfMessage+" di " + messages.length);
					continue; // already read;
				}
				
				retStatus = new MailStatus();
				
				String messageId = getFromHeadersMessageId(messages[indexOfMessage]);
				String stato = getFromHeadersStatus(messages[indexOfMessage]);

				//String messageId = getFromHeadersMessageId(messages[indexOfMessage]);
				if(messageId == null || messageId.length() == 0) {
					/*if(indexNoId++ % 100 == 0) */LOGGER.debug("@mail noID Message "+indexOfMessage+" di " + messages.length);
					continue;
				}
				
				howmanymailread++;
				
				if(indexOfMessage % 1000 == 0) {
					String mid = null, sdate = null, rdate = null;
					try {mid = ""+messages[indexOfMessage].getMessageNumber();} catch (Exception ignore) { }
					try {sdate = messages[indexOfMessage].getSentDate().toGMTString();} catch (Exception ignore) { }
					try {rdate = messages[indexOfMessage].getReceivedDate().toGMTString();} catch (Exception ignore) { }
					LOGGER.debug("@mail listAllPop3SSLMail - Message ("+indexOfMessage+" di " + messages.length+")["+mid+"]: "  + sdate + " - " + rdate);
				}
				
		    	// prev checks to skip already read messages
				//if(ScheduledTasks.lastPOP3sslMessageDate != null && messages[m_resumeReadMailFromIndex].getSentDate().getTime() < ScheduledTasks.lastPOP3sslMessageDate.getTime()) continue; // skip already read messaged
				
				AsyncMailService.messageReadHashesArray[AsyncMailService.messageReadHashesArraySize++] = messages[indexOfMessage].getMessageNumber();

		    	AsyncMailService.lastPOP3sslMessageDate = messages[indexOfMessage].getSentDate();
				
				if (messageId != null && !"".equals(messageId) /* && stato != null */) {
					String corpodelmessaggio = null;
					corpodelmessaggio = getTextFromMimeMultipart((MimeMultipart)messages[indexOfMessage].getContent());
					Date msgSentDate = getFromMessageSentDate(messages[indexOfMessage]);
					String lsMessageIdServer = getFromMessageIdServer(messages[indexOfMessage]);
					
					//	LOGGER.debug("@mail body-"+corpodelmessaggio.replaceAll("\r\n|\r|\n", " "));
	
					try {
						// THIS IS FOR DEBUG PURPOSES ONLY
						if(messageId != null && AsyncMailService.pop3SslMessageSearchId != null && messageId.contains(AsyncMailService.pop3SslMessageSearchId))
							LOGGER.debug("@mail listAllPop3SSLMail - DEBUG mail found: " + messageId + " - corpodelmessaggio: " + corpodelmessaggio);
						
						if(corpodelmessaggio.contains("Ricevuta di avvenuta consegna") 
								|| corpodelmessaggio.contains("Ricevuta di accettazione"))
							continue;

						String messageNote=corpodelmessaggio.replaceAll("\r\n|\r|\n", " ").contains(MOTIVO_NON_ACCETTAZIONE_NON_PEC)?MOTIVO_NON_ACCETTAZIONE_NON_PEC:null;
	
						if(messageNote == null) {
							if(messageId.indexOf("guido.deluca.conferenza@conferenza.it") > -1)
								continue;

							if(corpodelmessaggio.toLowerCase().indexOf("casella piena") > -1)
								LOGGER.debug("@mail listAllPop3SSLMail["+messageId+"] - not delivered for mailbox full. from: "+messages[indexOfMessage].getFrom()+" - for: " +Arrays.toString(messages[indexOfMessage].getAllRecipients()));
							else
								LOGGER.debug("@mail listAllPop3SSLMail["+messageId+"] - not delivered for unknown reasons : " + messageNote + " from: "+messages[indexOfMessage].getFrom()+" - for: " +Arrays.toString(messages[indexOfMessage].getAllRecipients()));
							continue;
						}
						
						LOGGER.debug("@mail listAllPop3SSLMail - found NON PEC mail : " + messageId + " - date: " + msgSentDate + " from: "+messages[indexOfMessage].getFrom()+" - for: " + Arrays.toString(messages[indexOfMessage].getAllRecipients()));
						
						retStatus.setMessageStatus(stato);
						retStatus.setMessageId(messageId);
						retStatus.setMessageSentDate(msgSentDate);
						retStatus.setMessageIdServer(lsMessageIdServer);
						retStatus.setMessageNote(messageNote);
						retStatus.setMessage(messages[indexOfMessage]);
						listMailStatus.add(retStatus);
					} catch (Exception skip) {
						LOGGER.debug("@mail exception: "+messageId+" - " + Arrays.toString(skip.getStackTrace()) + " for: " + Arrays.toString(messages[indexOfMessage].getAllRecipients()));
					}
				}
				// ---------------------------------------
			} // for

			LOGGER.debug("@mail indexOfMessage: " + indexOfMessage + " - howmanymailread: " + howmanymailread);
			
		} catch (Throwable e) {
			LOGGER.debug("@mail listAllPop3SSLMail loop exception break at: " + indexOfMessage + " - message: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			throw AsyncMailService.m_readMailEofException;
		} finally {
			LOGGER.debug("@mail listAllPop3SSLMail task execution finally of " + indexOfMessage + "[read:"+howmanymailread+",skipped:"+IndexSkipped+",noID:"+indexNoId+"] - " + ScheduledTasks.dateTimeFormatter.format(LocalDateTime.now()) );
			
			if(inbox != null) try { inbox.close(true); } catch (Exception e) { }
			if(store != null) try { store.close(); } catch (Exception e) { }
		}
		
		if(howmanymailread == 0)
			throw AsyncMailService.m_readNOMailRetrievedException;
		
		return listMailStatus;
	}
	
	@Async("threadPoolTaskExecutor")
	@Transactional
	public void sendEmailAFirmatario(Integer idUtente, Integer idDocumento) {
		
		String textmessage = "Gentile [nome cognome firmatario],\r\n" + 
				"sulla piattaforma MeetPAd sono presenti i seguenti documenti in attesa di essere visti e sottoscritti digitalmente:\r\n" + 
				"[ID pratica] [Riferimento Istanza \"es. DOMUS - Comune xxxx - Fascicolo xxxxx ...\"] [Tipo documento/azione \"es. Indizione conferenza, Comunicazione generica...\"]\r\n" + 
				"\r\n" + 
				"Per visualizzare e firmare i documenti\r\n" + 
				"1. Accedere alla piattaforma MeetPAd al link https://meetpad.regione.marche.it\r\n" + 
				"2. Cliccare su Accedi in alto a destra e selezionare Cohesion\r\n" + 
				"3. Inserire le credenziali\r\n" + 
				"4. Cliccare nel menu in alto a sinistra (le tre linee orizzontali) e selezionare la voce \"Alla firma\"\r\n" + 
				"\r\n" + 
				"A quel punto sarà presente l'elenco dei documenti sottoposti alla Sua visione per essere sottoscritti.\r\n" + 
				"\r\n" + 
				"Per qualunque informazione non esiti a contattare l'assistenza.\r\n" + 
				"\r\n" + 
				"Cordiali Saluti";
		sendEmailAFirmatario(idUtente,idDocumento,textmessage);
	}
	
	private void sendEmailAFirmatario(Integer idDocumento, Integer idUtente, String textmessage) {
		LOGGER.debug("sendEmailAFirmatario (Async)");
		LOGGER.debug("idFirmatario = " + idUtente + " - idDocumento = " + idDocumento);
		
		LOGGER.debug("Recupero il Documento completo tramite l'id: " + idDocumento);
		Documento documento = docRepo.findById(idDocumento).orElse(null);
		LOGGER.debug("Recupero l'Utente completo tramite l'id: " + idUtente);
		Utente utente = utenteRepo.findById(idUtente).orElse(null);
		LOGGER.debug("oggetti recuperati");
		MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
		
		String destinatario =  utente.getNome().concat(" ").concat(utente.getCognome());
		
		metadata.setFrom(from);
		//LabelValue tipoDoc = createNotNullLabelValue(documento.getTipologiaDocumento());
		metadata.setSubject(documento.getConferenza().getRiferimentoIstanza() );//+ " - " + createNotNullLabelValue(documento.getTipologiaDocumento()));
		metadata.setTo(utente.getEmail());
		metadata.setIdConferenza(documento.getConferenza().getIdConferenza());
		metadata.setDestinatario(destinatario);
		metadata.setMessage(textmessage);
		Map<String, String> parametri = metadata.getParametri();
		parametri.put("idConferenza", Integer.toString(documento.getConferenza().getIdConferenza()));
		parametri.put("destinatario", destinatario);

		String message = mailbuilder.build_firmatario(metadata);
		metadata.setMessage(message);
		// ----------------------------------------------
		sendMail(metadata, false);
		// ----------------------------------------------

	}	
}
