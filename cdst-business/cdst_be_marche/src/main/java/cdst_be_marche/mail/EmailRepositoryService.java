package cdst_be_marche.mail;

import java.io.File;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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
import javax.mail.internet.AddressException;
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
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPMessage;

import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.mail.bean.MailMetadata;
import cdst_be_marche.mail.bean.MailStatus;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.EmailPec;
import cdst_be_marche.model.Evento;
import cdst_be_marche.model.EventoPartecipante;
import cdst_be_marche.model.MailError;
import cdst_be_marche.model.Mailer;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RegistroEmailAllegato;
import cdst_be_marche.model.RegistroEmailDettaglio;
import cdst_be_marche.model.RegistroEmailTestata;
import cdst_be_marche.model.ReportEmailSample;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.model.TipologiaConferenza;
import cdst_be_marche.model.TockenConference;
import cdst_be_marche.repository.EmailPecRepository;
import cdst_be_marche.repository.EventoPartecipanteRepository;
import cdst_be_marche.repository.MailerRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.RegistroEmailAllegatoRepository;
import cdst_be_marche.repository.RegistroEmailDettaglioRepository;
import cdst_be_marche.repository.RegistroEmailTestataRepository;
import cdst_be_marche.repository.ReportEmailRepository;
import cdst_be_marche.repository.TemplateRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.repository.TipologiaConferenzaRepository;
import cdst_be_marche.repository.bean.ReportMailSampleBean;
import cdst_be_marche.service.RegistroDocumentoService;
import cdst_be_marche.service.TockenConferenceService;
import cdst_be_marche.service._BaseService;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.util.RandomUtil;

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
	TipologiaConferenzaRepository tipoConfRepo;

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
	@Qualifier("EmailStrategyAlias")
	EmailStrategy emailStrategy;
	
	@Value("${mail.from}") 
	private String from;

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
						LOGGER.info("sendMailIndizioneForTransportError errore.getId_messaggio ["
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

		LOGGER.info("--//============================================");
		LOGGER.info("[ATTENZIONE] --implementare il metodo [doMessageNotificaResponsabile] !!!!!!");
		LOGGER.info("--//============================================");
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
	public void sendMailIndizioneForConference(Integer idConference, String from, String textmessage, String subject,
			String tokenaccreditamento, Documento documento) {
		List<Mailer> mailerList = findAllMailerByConference(idConference);
		sendMailIndizioneToList(mailerList, from, textmessage, subject, tokenaccreditamento, documento);
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
		// ---------------------------------------
		metadati.setFaseConcerenza("INDIZIONE");
		metadati.setCodiceTipoEvento(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA));
		// ---------------------------------------
		LOGGER.info("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");
		String message = mailbuilder.build_indizione(metadati);
		metadati.setMessage(message);
		// ---------------------------------------
		sendMail(metadati);
		// ---------------------------------------
	}

	/**
	 * TODO: il metodo alla fine userà solo sendSSLMail
	 * 
	 * @param metadati
	 * @throws MessagingException
	 */
	public void sendMail(MailMetadata metadati) {
		try {
			// ---------------------------------------
			String messageId = null;
			if (!isPec(metadati)) {
				try {
					messageId = sendMailByMetadata(metadati);
					metadati.setMessageId(messageId);
				} catch (MessagingException e) {
					LOGGER.info("Errore [" + e.getMessage() + "]");
					e.printStackTrace();
				}
			} else {
				messageId = this.sendSSLMail(metadati);
				if (messageId == null)
					throw new Exception("Attenzione: l'email deve essere valorizzata e deve essere una PEC.");

				metadati.setMessageId(messageId);
				LOGGER.info(" metadati.getMessageId " + metadati.getMessageId());
			}
			// ---------------------------------------
			// STORE REGISTRO - DATI TESTATA
			// dopo il livello trasporto occorre salvare sul registro l'operazione
			if (messageId != null) {
				registerInitialMessag(metadati, DbConst.EMAIL_STATUS_INOLTRATO, "Trasmissione");
			}
			else { 
				registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE, "Problemi di trasmissione");
			}
		} catch (Exception e) {
			registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE, e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean isPec(MailMetadata metadati) {
		if (metadati.getTo() != null) {
			EmailPec emailPec = emailPecRepository.findByEmail(metadati.getTo()).orElse(null);
			if (emailPec != null) {
				return emailPec.getPec();
			}
		}
		return mailSenderConfigurator.isDefaultFormatPec();
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
		RegistroEmailTestata registroTestata = registraMessaggioTestata(metadati);
		registraMessaggioAllegati(metadati, registroTestata);
		registraMessaggioDettaglio(metadati, STATOMESSAGGIO, nota, new Date(), registroTestata);
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
		LOGGER.info("[doAfterSendEmail]: operazine dopo sent emailemail per [" + metadati.getTo() + ","
				+ metadati.getIdConferenza() + "]");
	}

	/**
	 * TODO: occorre settare una tabella di log delle email in errore
	 */
	public void doAErrorSendEmail(MailMetadata metadati) {
		LOGGER.info("[doAErrorSendEmail]: problemi nella trasmissione dell'email per [" + metadati.getTo() + ","
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
			baseUrlIndizione = "http://as.meetpad-dev.eng/meet-pad";
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
		String tipologiaConferenza = messageSource.getMessage(mail.getDescrizione_tipologia_conferenza(), null,
				mail.getDescrizione_tipologia_conferenza(), Locale.ITALIAN);
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
		if (mail.getOrario_conferenza() != null) {
			parametri.put("orarioConferenza",
					mail.getOrario_conferenza().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString());
		}

		parametri.put("token1", token.getTKN1());
		parametri.put("token2", token.getTKN2());

		TipologiaConferenza tipologiaConferenza2 = this.tipoConfRepo
				.findByDescrizione(mail.getDescrizione_tipologia_conferenza()).orElse(null);
		TipoEvento tipoEvento = this.tipoEventoRepo
				.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA)).orElse(null);
		if (tipologiaConferenza2 != null && tipoEvento != null) {
			String nomeTemplate = this.templateRepo
					.findByTipologiaConferenzaAndTipoEvento(tipologiaConferenza2, tipoEvento).orElse(null)
					.getNomeTemplate();
			parametri.put("template", nomeTemplate);
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
			sendMailIndizioneByMetadata(metadati);
			// sendMail(from, mail.getEmail(),subject, textmessage,null);
		}
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

	public void sendMailEvento(Evento evento, Documento documento, List<LabelValue> listaDestinatari) throws MessagingException {
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(evento.getTipoEvento().getCodice()).get();
		List<EventoPartecipante> lista = this.eventoPartRepo.findByTipoEvento(tipoEvento);
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		listaDestinatari.stream()
				.forEach(l -> listaPartecipanti.add(this.partRepo.findById(Integer.parseInt(l.getKey())).orElse(null)));
		for (EventoPartecipante eventoPart : lista) {
			evento.getConferenza().getPartecipantes().stream().filter(
					p -> p.getRuoloPartecipante().getCodice().equals(eventoPart.getRuoloPartecipante().getCodice()))
					.forEach(p -> listaPartecipanti.add(p));
		}
		for (Partecipante partecipante : listaPartecipanti) {
			if (partecipante != null) {
				if (!partecipante.isRichiedente()) {
					sendEmailEvento(evento, partecipante.getDescrizione(), partecipante.getPecEnteCompetente(),
							documento);
				}
				partecipante.getAccreditati().stream().filter(a -> a.getFlagAccreditato())
						.forEach(a -> sendEmailEvento(evento,
								a.getPersona().getCognome() + " " + a.getPersona().getNome(), a.getPersona().getEmail(),
								documento));
				partecipante.getAltreEmail().stream()
						.forEach(e -> sendEmailEvento(evento, partecipante.getDescrizione(), e.getEmail(), documento));
			}
		}
	}
	
	public void sendMailEventoByMetadata(MailMetadata metadati, Evento evento) {
		LOGGER.info("sendMailEventoByMetadata [" + metadati.getTo() + "]");
		metadati.setFaseConcerenza(evento.getTipoEvento().getDescrizione());
		metadati.setCodiceTipoEvento(evento.getTipoEvento().getCodice());
		metadati.setCorpoEvento(evento.getCorpo());
		String message = mailbuilder.buildTemplateEmail(metadati);
		metadati.setMessage(message);
		// ----------------------------------------------
		sendMail(metadati);
		// ----------------------------------------------
	}

	private void sendEmailEvento(Evento evento, String destinatario, String email, Documento documento) {
		MailMetadata metadata = new MailMetadata(getFakeRecipientAddress());
		//metadata.setFrom(evento.getMittente().getPecEnteCompetente());
		metadata.setFrom(from);
		metadata.setSubject(evento.getOggettoEvento().getDescrizione());
		metadata.setTo(email);
		metadata.setIdConferenza(evento.getConferenza().getIdConferenza());
		Map<String, String> parametri = metadata.getParametri();
		parametri.put("idConferenza", Integer.toString(evento.getConferenza().getIdConferenza()));
		parametri.put("mittente", evento.getMittente() != null ? evento.getMittente().getDescrizione() : null);
		parametri.put("tipoEvento", evento.getTipoEvento().getDescrizione());
		parametri.put("dataEvento", formatDate(evento.getData()));
		parametri.put("destinatario", destinatario);

		metadata.setMittente(evento.getMittente() != null ? evento.getMittente().getDescrizione() : null);
		metadata.setDestinatario(destinatario);
		metadata.setTipologiaConferenza(evento.getConferenza().getTipologiaConferenza().getCodice());
		
		parametri.put("riferimentoIstanza", evento.getConferenza().getRiferimentoIstanza());
		parametri.put("oggettoDetermina", evento.getConferenza().getOggettoDeterminazione());
		
		/*
		 * TODO: il documento se presente è sempre allegato,
		 * per cui non è utilizzato attualmente il flag TipoEvento.flagAllegato (indica se è previsto o meno l'allegato)
		 */
		if (evento.getTipoEvento().getFlagAllegato()) {
			if (documento != null) {
				metadata.getAttachments().add(documento);
			}
		}

		sendMailEventoByMetadata(metadata, evento);

	}

	/**
	 * @deprecated
	 * @param metadati
	 * @return
	 * @throws MessagingException
	 */
	public String sendMailByMetadata(MailMetadata metadati) throws MessagingException {

		String message = metadati.getMessage();
		LOGGER.info("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");

		meansender = mailSenderConfigurator.javaMailSender();
		mm = mailSenderConfigurator.createMimeMessage();
		helper = new MimeMessageHelper(mm, true, "UTF-8");
		try {
			helper.setFrom(metadati.getFrom());
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
			LOGGER.info(e.getMessage());
		}
		// -------------------------------------
		try {
			meansender.send(mm);
			// dopo la trasmission occorre settare il database..
			doAfterSendEmail(metadati);
		} catch (Exception e) {
			LOGGER.info(
					"[Errore]: problemi nella trasmissione dell'email per [" + metadati.getTo() + "]" + e.getMessage());
			doAErrorSendEmail(metadati);
		}

		return mm.getMessageID();
	}

	/*
	 * public void sendMailByMetadata(MailMetadata metadati, String message) {
	 * 
	 * LOGGER.info("sendMailIndizioneByMetadata [" + metadati.getTo() + "]");
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
	 * LOGGER.info(e.getMessage()); } // ------------------------------------- try {
	 * meansender.send(mm); // dopo la trasmission occorre settare il database..
	 * doAfterSendEmail(metadati); } catch (Exception e) { LOGGER.info(
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
			// LOGGER.info("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			if ("Subject".equals(h.getName())) {
				String sbj = h.getValue();
				if (sbj.lastIndexOf("CONSEGNA:") == 0) {
					// return "CONSEGNA";
					return DbConst.EMAIL_STATUS_CONSEGNA;
				} else if (sbj.lastIndexOf("ACCETTAZIONE:") == 0) {
					// return "ACCETTAZIONE";
					return DbConst.EMAIL_STATUS_ACCETTAZIONE;
				} else if (sbj.lastIndexOf("AVVISO DI NON ACCETTAZIONE:") == 0) {
					dumpMineMessage(message);
					// return "AVVISO DI NON ACCETTAZIONE";
					return DbConst.EMAIL_STATUS_AVVISODINONACCETTAZIONE;
				} else if (sbj.lastIndexOf("Creazione nuova casella posta certificata") == 0) {
					dumpMineMessage(message);
				} else {
					LOGGER.info("STATO IS : " + h.getValue());
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	public String getFromHeadersMessageId(Message message) throws MessagingException {
		Enumeration<Header> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			// LOGGER.info("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			if ("X-Riferimento-Message-ID".equals(h.getName())) {
				String mid = h.getValue();
				return mid;
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
			// LOGGER.info("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
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
			LOGGER.info("Header : " + " " + h.getName() + " -- " + h.getValue());
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
		LOGGER.info("-----------  DUMP MEIME MESSAGE  -----------BEGIN ");
		dumpHeadersMessage(message);
		LOGGER.info("From : " + message.getFrom()[0]);
		LOGGER.info("Recipients : " + message.getAllRecipients()[0]);
		LOGGER.info("Subject : " + message.getSubject());
		LOGGER.info("Sent Date : " + message.getSentDate());
		LOGGER.info("-----------  DUMP MEIME MESSAGE  -----------END ");

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
							LOGGER.info(" [ERROR]: " + e.getMessage());
							throw new Exception(e);
						} else
							LOGGER.info(
									" [ERRORE GESTITO]: duplicate key value violates unique constraint - cancellazione su SMTP non ancora avvenuta!");
				}
				// ---------------------------------------------------
				LOGGER.info(" STATUS:--> " + status.getMessageStatus());
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
				status.getMessageSentDate(), null, registroTestata);
		repositoryRegistroEmailDettaglio.save(dettaglio);
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
			LOGGER.info("No messages found.");

		if (messages.length > 0) {
			// inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
			// inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
			// inbox.setFlags(messages, new Flags(Flags.Flag.DRAFT), true);
			inbox.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
		}

		if (messages.length > 0)
			LOGGER.info("messages found: " + messages.length);
		for (int i = 0; i < messages.length; i++) {
			// ---------------------------------------
			// TODO: commentare dopo il debug
			// bdumpMineMessage(messages[i]);
			// ---------------------------------------
			String messageId = getFromHeadersMessageId(messages[i]);

			if (pMessageId != null && !"".equals(pMessageId) && pMessageId.equals(messageId)) {
				retStatus = new MailStatus();
				String stato = getFromHeadersStatus(messages[i]);
				LOGGER.info("messageId : " + messageId);
				LOGGER.info(" stato " + stato);
				Date msgSentDate = getFromMessageSentDate(messages[i]);
				// E' l'id del messaggio generato dal server SMTP per la notifica..
				String lsMessageIdServer = getFromMessageIdServer(messages[i]);

				LOGGER.info(" msgSentDate " + msgSentDate);
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

	// =======================================================================================
	/**
	 * 
	 * @throws Exception
	 */
	public List<MailStatus> listAllPop3SSLMail() throws Exception {

		// ============================================
		List<MailStatus> listMailStatus = new ArrayList<MailStatus>();
		MailStatus retStatus = new MailStatus();
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
		Store store = session.getStore(urln);
		store.connect();
		// FOLDER
		Folder inbox = store.getFolder(mailPecSenderConfigurator.getPop3Folder());
		inbox.open(Folder.READ_ONLY);
		// get the list of inbox messages
		Message[] messages = inbox.getMessages();
		if (messages.length == 0)
			LOGGER.info("No messages found.");
		if (messages.length > 0)
			LOGGER.info("messages found: " + messages.length);
		for (int i = 0; i < messages.length; i++) {
			retStatus = new MailStatus();
			// ---------------------------------------
			LOGGER.info(i + " Message " + (i + 1));
			// ---------------------------------------
			/**
			 * Address[] addrs=messages[i].getAllRecipients(); for( Address a: addrs) {
			 * LOGGER.info("Recipients : " + a.toString()); } LOGGER.info("Recipients : " +
			 * messages[i].getAllRecipients()[0]);
			 */
			// ---------------------------------------
			String messageId = getFromHeadersMessageId(messages[i]);
			LOGGER.info("messageId : " + messageId);
			String stato = getFromHeadersStatus(messages[i]);
			LOGGER.info(" stato " + stato);
			// ---------------------------------------
			/*
			 * Enumeration<Header> headers = messages[i].getAllHeaders();
			 * while(headers.hasMoreElements()) { Header h =(Header)headers.nextElement();
			 * LOGGER.info("Header : "+" "+h.getName()+" -- " + h.getValue() ) ;
			 * 
			 * if("X-Riferimento-Message-ID".equals(h.getName()) ){
			 * LOGGER.info(" X-Riferimento-Message-ID  "+h.getValue() ); String
			 * stato=getFromHeadersStatus(messages[i]); LOGGER.info(" stato "+stato ); } }
			 */
			// ---------------------------------------
			if (messageId != null && !"".equals(messageId) && stato != null) {
				retStatus = new MailStatus();
				stato = getFromHeadersStatus(messages[i]);
				LOGGER.info("messageId : " + messageId);
				LOGGER.info(" stato " + stato);
				Date msgSentDate = getFromMessageSentDate(messages[i]);
				String lsMessageIdServer = getFromMessageIdServer(messages[i]);
				LOGGER.info(" msgSentDate " + msgSentDate);
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
		List<MailStatus> lisMailStatus = listAllPop3SSLMail();
		verifyAndStoredStatusList(lisMailStatus);
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

		// MeetPADMimeMessage mmsg=new MeetPADMimeMessage(session);
		// retMessageId=mmsg.getMessageID();
		// if null generate Message id
		MimeMessage msg = new MimeMessage(session);

		// @TODO: decommentare dopo il debug!!!
		if (metadati.getMessageId() != null)
			// serve in caso di ritrasmissioni del messagio!?!?
			retMessageId = metadati.getMessageId();
		else {
			retMessageId = msg.getMessageID();
			LOGGER.info("---------------------------------------------");
			if (retMessageId == null || "".equals(retMessageId))
				LOGGER.info("Message-Id is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			else
				LOGGER.info("Message-Id is :" + retMessageId);
			LOGGER.info("---------------------------------------------");
			// -----------------------------------------------------------------------------------
			if (retMessageId == null || "".equals(retMessageId)) {
				msg = new MeetPADMimeMessage(session);
				retMessageId = formatCustomMessageID(metadati);
				((MeetPADMimeMessage) msg).setIdMessage(retMessageId);
				metadati.setMessageId(retMessageId);
				LOGGER.info("[MeetPADMimeMessage] - Message-id :" + retMessageId);
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
		LOGGER.info("---------------------------------------------");
		LOGGER.info("[sendSSLMail] - Message is ready");
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
		LOGGER.info("[sendSSLMail] - Host " + mailPecSenderConfigurator.getHost());
		LOGGER.info("[sendSSLMail] - From " + mailPecSenderConfigurator.getFrom());
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
	 * @return custom message-id della forma Message-ID: < "random"."id
	 *         confereza"."email del destinatario senza dominio".meetpad@meetpad.it>
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
		return "<" + s1 + "." + idConfrnc + "." + pTOList[0] + ".meetpad@meetpad.it>";
	}

	// =======================================================================================
	// SSL Method - END
	// =======================================================================================
	class MeetPADMimeMessage extends MimeMessage {

		private String idMessage;

		public MeetPADMimeMessage(Session session) {
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

	public static void main(String[] args) {
		try {
			InternetAddress.parse(null, false);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}