package cdst_be_marche.mail;

import cdst_be_marche.model.Mailer;

public interface EmailStrategy {

	public String generateSubject(Mailer mailer);
}
