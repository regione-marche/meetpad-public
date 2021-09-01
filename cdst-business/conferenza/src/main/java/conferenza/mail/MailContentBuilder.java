package conferenza.mail;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import conferenza.exception.NotFoundEx;
import conferenza.mail.bean.MailMetadata;
import conferenza.model.Conferenza;
import conferenza.model.Template;
import conferenza.model.TipoEvento;
import conferenza.model.TipologiaConferenza;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.TemplateRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipologiaConferenzaRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.service.ConferenzaService;
import conferenza.util.DbConst;
import javassist.NotFoundException;

@Service
public class MailContentBuilder {

	protected TemplateEngine templateEngine;
	protected String templateName;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailContentBuilder.class);


	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	TemplateRepository templateRepo;

	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipologiaConfSpecRepo;
	
	@Autowired
	ConferenzaService conferenzaService;

	/**
	 * 
	 * @param metadati
	 * @return
	 */
	public String build_indizione(MailMetadata metadati) {
		Context context = new Context();
		context.setVariable("message", metadati.getMessage());
		context.setVariable("accreditamento", metadati.getUrl_accreditamento());
		context.setVariable("tkn1", metadati.getParametri().get("token1"));
		context.setVariable("tkn2", metadati.getParametri().get("token2"));
		context.setVariable("conferenza", metadati.getUrl_videoconferenza());
		context.setVariable("otp", metadati.getParametri().get("otp"));
		context.setVariable("idConferenza", metadati.getParametri().get("idConferenza"));
		context.setVariable("amministrazioneProcedente", metadati.getParametri().get("amministrazioneProcedente"));
		context.setVariable("tipoConferenza", metadati.getParametri().get("tipoConferenza"));
		context.setVariable("riferimentoIstanza", metadati.getParametri().get("riferimentoIstanza"));
		context.setVariable("oggettoDetermina", metadati.getParametri().get("oggettoDetermina"));
		context.setVariable("ruolo", metadati.getParametri().get("ruolo"));
		context.setVariable("competenza", metadati.getParametri().get("competenza"));
		context.setVariable("termineRichiestaIntegrazioni",
				metadati.getParametri().get("termineRichiestaIntegrazioni"));
		context.setVariable("termineEspressioneDeterminazioni",
				metadati.getParametri().get("termineEspressioneDeterminazioni"));
		context.setVariable("data_termine", metadati.getParametri().get("data_termine"));
		context.setVariable("dataSessioneSimultanea", metadati.getParametri().get("dataSessioneSimultanea"));
		context.setVariable("indirizzoSessioneOperationalMeeting", metadati.getParametri().get("indirizzoSessioneSimultanea"));
		String indirizzoSessioneSimultanea = (String)metadati.getParametri().get("indirizzoSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("capSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("comuneSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("provinciaSessioneSimultanea");
		context.setVariable("indirizzoSessioneSimultanea", indirizzoSessioneSimultanea);
		context.setVariable("urlAccreditamento", metadati.getUrl_accreditamento());
		context.setVariable("urlVideoconferenza", metadati.getUrl_videoconferenza());
		context.setVariable("descrizione_ente", metadati.getParametri().get("descrizione_ente"));
		context.setVariable("orarioConferenza", metadati.getParametri().get("orarioConferenza"));
		context.setVariable("urlDocumentazione", metadati.getUrlDocumentazione());
		context.setVariable("listaContattiSupporto", metadati.getParametri().get("listaContattiSupporto"));
		
		templateName = (String) metadati.getParametri().get("template");
		return templateEngine.process(templateName, context);

	}

	public String build(String message) {

		Context context = new Context();
		context.setVariable("message", message);
		return templateEngine.process("template_indizione", context);

	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public String buildTemplateEmail(MailMetadata metadati) {
		Context context = new Context();
		context.setVariable("idConferenza", metadati.getParametri().get("idConferenza"));
		context.setVariable("mittente", metadati.getParametri().get("mittente"));
		context.setVariable("destinatario", metadati.getParametri().get("destinatario"));
		context.setVariable("amministrazioneProcedente", metadati.getParametri().get("amministrazioneProcedente"));
		context.setVariable("tipoEvento", metadati.getParametri().get("tipoEvento"));
		context.setVariable("dataEvento", metadati.getParametri().get("dataEvento"));
		context.setVariable("riferimentoIstanza", metadati.getParametri().get("riferimentoIstanza"));
		context.setVariable("oggettoDetermina", metadati.getParametri().get("oggettoDetermina"));
		context.setVariable("corpoEvento", metadati.getCorpoEvento());
		context.setVariable("utente",  metadati.getParametri().get("utente"));
		context.setVariable("impresa",  metadati.getParametri().get("impresa"));
		context.setVariable("urlAccessoCDST",  metadati.getParametri().get("urlAccessoCDST"));
		context.setVariable("tipoDataModifica",  metadati.getParametri().get("tipoDataModifica"));
		context.setVariable("dataModifica",  metadati.getParametri().get("dataModifica"));
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(metadati.getCodiceTipoEvento()).orElse(null);
		TipologiaConferenzaSpecializzazione tipologiaConferenzaSpec = this.tipologiaConfSpecRepo.findById(metadati.getTipologiaConferenza())
				.orElse(null);
		
		//xmf: fixes batch user issues with this method
		//Conferenza conferenza = this.conferenzaService.findConferenzaByIdFiltrata(metadati.getIdConferenza());
		Conferenza conferenza = this.conferenzaService.findConferenceById(metadati.getIdConferenza());
		if(conferenza !=null) {
			LOGGER.info("MailContentBuilder - buildTemplateEmail - la conferenza è:" + conferenza.getIdConferenza() + " la tipologia è: " + tipologiaConferenzaSpec );
		}
		Template template;
		if (tipoEvento != null && tipologiaConferenzaSpec != null) {
			if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())) {

				template = this.templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEventoAndAzione(tipologiaConferenzaSpec, tipoEvento, conferenza.getAzione()).orElse(null);
				// xmf: fallback for default template without fk_azione
				if(template == null)
					template = this.templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEventoAndAzione(tipologiaConferenzaSpec, tipoEvento, null).orElse(null);									
			
//			Template template = this.templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEvento(tipologiaConferenzaSpec, tipoEvento)
//					.orElse(null);
			} else
			{
				template = this.templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEvento(tipologiaConferenzaSpec, tipoEvento)
						.orElse(null);
			}
			
			
			
			if (template == null) {
				throw new NotFoundEx("template not found");
			}
			templateName = template.getNomeTemplate();
		}
		return templateEngine.process(templateName, context);
	}

	public String build_firmatario(MailMetadata metadati) {
		Context context = new Context();
		context.setVariable("message", metadati.getMessage());
		context.setVariable("destinatario", metadati.getParametri().get("destinatario"));
		context.setVariable("tkn1", metadati.getParametri().get("token1"));
		context.setVariable("tkn2", metadati.getParametri().get("token2"));
		context.setVariable("conferenza", metadati.getUrl_videoconferenza());
		context.setVariable("idConferenza", metadati.getParametri().get("idConferenza"));
		context.setVariable("tipoConferenza", metadati.getParametri().get("tipoConferenza"));
		context.setVariable("riferimentoIstanza", metadati.getParametri().get("riferimentoIstanza"));
		context.setVariable("urlDocumentazione", metadati.getUrlDocumentazione());
		
		templateName = "template_comunicazione_firma_multipla.html";//(String) metadati.getParametri().get("template");
		return templateEngine.process(templateName, context);

	}
}