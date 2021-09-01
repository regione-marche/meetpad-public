package conferenza.mail;

import conferenza.model.Mailer;

public interface EmailStrategy {

	public String generateSubject(Mailer mailer);
}
