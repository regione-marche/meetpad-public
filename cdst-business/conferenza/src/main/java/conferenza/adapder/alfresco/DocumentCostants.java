package conferenza.adapder.alfresco;

import org.apache.commons.lang3.StringUtils;

public class DocumentCostants {
	public static final String CODICE_TIPO_ENTE_COMUNE_SUAP = "COMSUAP";
	
	public static final String CODICE_TIPO_ENTE_REGIONE_SUAP = "REGSUAP";
	
	
	
	public static final Long HACK_ID  = -1L;
	
	public static final String HACK_STRING  = "_";
	
	public static final String MENU_ITEMS = "menuItems";
	
	public static final String BREADCRUMB_MENU_ITEMS = "breadcrumbMenuItems";

	public static final String EMPTY_VALUE = StringUtils.EMPTY;

	public static final String SUAP = "SUAP";

	public static final String SI = "Si";

	public static final String NO = "No";

	public static final String SEPARATOR = "-";

	public static final String MESSAGE = "message";

	public static final String RETURN_URL = "returnUrl";

	public static final String MESSAGE_ERROR = "messageError";

	public static final String ON = "ON";

	public static final String OFF = "OFF";

	public static final String PROCESS = "process";

	public static final String SUCCESS = "success";

	public static final String SHARE = "share";

	public static final String ADMIN = "admin";

	public static final String ERROR_SECURITY_PERMISSION = "error-security-permission";
	public static final String ERROR_SECURITY_PERMISSION_PROCESS = "error-security-permission-process";
	public static final String ERROR_GENERIC = "error-generic";

	public static final String MESSAGE_GENERIC = "message-generic";

	public static final String URL_REDIRECT_PARAMETER = "urlredirect";

	public static final String MAIL_SUAP_PROP = "mail.account";

	public static final String PEC_SUAP_PROP = "pec.account";

	/* MESSAGGI ALL'UTENTE */
	public static final String GENERAL_BINDING_RESULT = "org.springframework.validation.BindingResult.";
	
	public static final String ERROR_MESSAGE_DATI_PERSONALI = "Il consenso al trattamento dei dati personali è obbligatorio";
	public static final String ERROR_MESSAGE_KEY_DATI_PERSONALI = "generic.check.privacy.error";
	public static final String ERROR_MESSAGE_KEY_COMUNICAZIONI = "generic.check.comunication.error";
	public static final String ERROR_MESSAGE_CAMPI_OBBLIGATORI = "Verificare i campi obbligatori";
	public static final String ERROR_MESSAGE_CAMPI_NUMERICI = "Richiesto valore numerico";
	public static final String ERROR_MESSAGE_INTEGRATIONS_SIBAR_PROTOCOL = "integrations.fault.protocol.error";

	public static final String FIELD_ERROR_MESSAGE_CAMPO_OBBLIGATORIO = "generic.cannot.be.empty";

	public static final String SUCCESS_MESSAGE = "Operazione eseguita con successo";
	public static final String SUCCESS_MESSAGE_DOCUMENTS_NEEDED = "Operazione eseguita con successo. Procedere al caricamento della documentazione richiesta";
	public static final String SUCCESS_MESSAGE_WITHOUT_PROTOCOL = "Operazione eseguita con successo. Protocollo momentaneamente non disponibile.";

	public static final String EMAIL_SEPARATOR = ";";

	public static final String PEC = "pec";

	public static final String DASH_SEPARATOR = "&nbsp;&nbsp;&nbsp;";
	public static final String BR_SEPARATOR = "<br/>";

	public static final String MAINTENANCE_MODE = "maintenance_mode";

	// temporary file upload path
	public static final String TEMPORARY_UPLOAD_FOLDER = "tmp/";

	public static final String DATA_SEPARATOR = "___";
	
	public static final String SESSION_USER_ATTRIBUTE = "SessionUser";
	
	public final static String DOES_NOT_HAVE_NECESSARY_PERMISSIONS = "non hai i permessi necessari per visualizzare la pagina";
	public final static String NOT_AUTHENTICATED = "non sei autenticato";
	public static final String USERNAME_CAMBIO_UTENTE = "USERNAME_CAMBIO_UTENTE";

}
