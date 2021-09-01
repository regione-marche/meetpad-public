package conferenza.votazione;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import conferenza.mail.MailContentBuilder;
import conferenza.mail.bean.MailMetadata;

@Service
public class MailVotazioneContemptBuilder {
    
	protected TemplateEngine templateEngine;
    protected String templateName;
 
	String templateNameDefault="template_votazione";
	
	public MailVotazioneContemptBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
    /**
     * 
     * @param metadati
     * @return
     */
    public String build_votazione(MailMetadata metadati) {    	
        Context context = new Context();
        context.setVariable("message", metadati.getMessage());
        context.setVariable("otp", metadati.getParametri().get("otp"));
        context.setVariable("conferenza",metadati.getUrl_videoconferenza());    
        if(templateName==null)
        	templateName=templateNameDefault;
        return templateEngine.process(templateName, context);   	
    }

}
