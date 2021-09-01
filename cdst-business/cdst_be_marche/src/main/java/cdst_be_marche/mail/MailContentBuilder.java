package cdst_be_marche.mail;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import cdst_be_marche.mail.bean.MailMetadata;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.model.TipologiaConferenza;
import cdst_be_marche.repository.TemplateRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.repository.TipologiaConferenzaRepository;

@Service
public class MailContentBuilder {

	protected TemplateEngine templateEngine;
	protected String templateName;

	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	TemplateRepository templateRepo;

	@Autowired
	TipologiaConferenzaRepository tipologiaConfRepo;

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
		String indirizzoSessioneSimultanea = (String)metadati.getParametri().get("indirizzoSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("capSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("comuneSessioneSimultanea")+", "+
				(String)metadati.getParametri().get("provinciaSessioneSimultanea");
		context.setVariable("indirizzoSessioneSimultanea", indirizzoSessioneSimultanea);
		context.setVariable("urlAccreditamento", metadati.getUrl_accreditamento());
		context.setVariable("urlVideoconferenza", metadati.getUrl_videoconferenza());
		context.setVariable("descrizione_ente", metadati.getParametri().get("descrizione_ente"));
		context.setVariable("orarioConferenza", metadati.getParametri().get("orarioConferenza"));
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
		context.setVariable("tipoEvento", metadati.getParametri().get("tipoEvento"));
		context.setVariable("dataEvento", metadati.getParametri().get("dataEvento"));
		context.setVariable("riferimentoIstanza", metadati.getParametri().get("riferimentoIstanza"));
		context.setVariable("oggettoDetermina", metadati.getParametri().get("oggettoDetermina"));
		context.setVariable("corpoEvento", metadati.getCorpoEvento());
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(metadati.getCodiceTipoEvento()).orElse(null);
		TipologiaConferenza tipologiaConferenza = this.tipologiaConfRepo.findById(metadati.getTipologiaConferenza())
				.orElse(null);
		if (tipoEvento != null && tipologiaConferenza != null)
			templateName = this.templateRepo.findByTipologiaConferenzaAndTipoEvento(tipologiaConferenza, tipoEvento)
					.orElse(null).getNomeTemplate();
		return templateEngine.process(templateName, context);
	}

}