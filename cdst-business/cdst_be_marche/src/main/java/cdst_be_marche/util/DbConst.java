package cdst_be_marche.util;

public class DbConst {
	
	// Stato conferenza
	public final static int STATO_COMPILAZIONE = 1;
	public final static int STATO_BOZZA = 2;
	public final static int STATO_VALUTAZIONE = 3;
	public final static int STATO_CHIUSA = 4;
	public final static int STATO_ARCHIVIATA = 5;
	
	// Step (scheda) compilazione conferenza
	public final static int STEP_1 = 1;
	public final static int STEP_2 = 2;
	public final static int STEP_3 = 3;
	public final static int STEP_4 = 4;
	public final static int STEP_RIEPILOGO = 5;
	
	// Tipo profilo applicativo
	public final static int TIPO_PROFILO_CREATORE_CDS = 1;
	public final static int TIPO_PROFILO_RESPONSABILE_CONFERENZA = 2;
	public final static int TIPO_PROFILO_PARTECIPANTE = 3;
	public final static int TIPO_PROFILO_ADMIN_SISTEMA = 4;
	public final static int TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE = 5;
	public final static int TIPO_PROFILO_ADMIN_AMMINISTRAZIONI = 6;	
	
	// Tipologia conferenza
	public final static int TIPOLOGIA_CONFERENZA_SEMPLIFICATA = 1;
	public final static int TIPOLOGIA_CONFERENZA_SIMULTANEA = 2;
	public final static int TIPOLOGIA_CONFERENZA_REGIONALE = 3;
	public final static int TIPOLOGIA_CONFERENZA_BANDA_LARGA = 4;
	public final static int TIPOLOGIA_CONFERENZA_ISTRUTTORIA = 5;
	
	// Ruolo persona
	public final static int RUOLO_PERSONA_RICHIEDENTE = 1;
	public final static int RUOLO_PERSONA_RESPONSABILE_CONFERENZA = 2;
	public final static int RUOLO_PERSONA_RESPONSABILE_PROCEDIMENTO = 3;
	public final static int RUOLO_PERSONA_RESPONSABILE_UNICO = 4;
	public final static int RUOLO_PERSONA_SOGGETTO_ACCREDIDATO = 5;
	
	// Ruolo partecipante
	public final static int RUOLO_PARTECIPANTE_RICHIEDENTE = 1;
	public final static int RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE = 2;
	public final static int RUOLO_PARTECIPANTE_AMMINISTRAZIONE_COMPETENTE = 3;
	public final static int RUOLO_PARTECIPANTE_AMMINISTRAZIONE_INTERESSATA = 4;
	
	// Tipologia Documentazione
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA = "DOCUMENTAZIONE_AGGIUNTIVA";
	public final static String TIPOLOGIA_DOCUMENTO_INTERAZIONI = "INTERAZIONI";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE = "DOCUMENTO_INDIZIONE";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO = "DOCUMENTO_ACCREDITAMENTO";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA = "DOCUMENTO_PRE_ISTRUTTORIA";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_VERBALE_RIUNIONE = "VERBALE_RIUNIONE";
	public final static String TIPOLOGIA_DOCUMENTO_DETERMINA = "DETERMINA";
	
	// Tipologia Evento
	public final static int TIPOLOGIA_EVENTO_CREAZIONE_CONFERENZA = 1;
	public final static int TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA = 2;
	public final static int TIPOLOGIA_EVENTO_RICHIESTA_INTEGRAZIONE = 3;
	public final static int TIPOLOGIA_EVENTO_CHIUSURA_INTEGRAZIONI = 4;
	public final static int TIPOLOGIA_EVENTO_RICHIESTA_UNICA_INTEGRAZIONE = 5;
	public final static int TIPOLOGIA_EVENTO_INVIO_INTEGRAZIONI = 6;
	public final static int TIPOLOGIA_EVENTO_COMUNICAZIONE_GENERICA = 7;
	public final static int TIPOLOGIA_EVENTO_ASSOCIAZIONE_CONFERENZA = 8;
	public final static int TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE = 9;
	public final static int TIPOLOGIA_EVENTO_CHIUSURA_CONFERENZA = 10;
	
	// EmailStatus
	public final static String EMAIL_STATUS_ACCETTAZIONE = "ACCETTAZIONE";
	public final static String EMAIL_STATUS_AVVISODINONACCETTAZIONE = "AVVISODINONACCETTAZIONE";
	public final static String EMAIL_STATUS_CONSEGNA = "CONSEGNA";
	public final static String EMAIL_STATUS_ERRORETRASMISSIONE = "ERRORETRASMISSIONE";
	public final static String EMAIL_STATUS_INOLTRATO = "INOLTRATO";
	
	// Oggetto Evento
	public final static int OGGETTO_EVENTO_RICHIESTA_INTEGRAZIONI = 1;
	public final static int OGGETTO_EVENTO_COMUNICAZIONE_GENERICA = 2;
	public final static int OGGETTO_EVENTO_TRASMISSIONE_PARERE = 3;
	public final static int OGGETTO_EVENTO_ALTRO = 4;
	public final static int OGGETTO_EVENTO_VERBALE_CONFERENZA = 5;
	public final static int OGGETTO_EVENTO_CHIUSURA_CONFERENZA = 6;

	/*
	 * VALORI PREDEFINITI PER BANDA ULTRA LARGA E ISTRUTTORIA RELATIVAMENTE AI CAMPI TIPOLOGIA PRATICA, ATTIVITA, AZIONE
	 */
	public final static int TIPOLOGIA_PRATICA_BANDA_ULTRA_LARGA = 4;
	public final static int ATTIVITA_BANDA_ULTRA_LARGA = 30;
	public final static int AZIONE_BANDA_ULTRA_LARGA = 179;
	
	//Ente richiedente
	public final static int ENTE_RICHIEDENTE = 0;
	
	// Registro Documento Fonte
	public final static String REGISTRO_DOCUMENTO_FONTE_MEETPAD = "MEETPAD";
	public final static String REGISTRO_DOCUMENTO_FONTE_SUAP = "SUAP";
	
	// Registro Documento Tipo
	public final static String REGISTRO_DOCUMENTO_TIPO_FILESYSTEM = "FS";
	public final static String REGISTRO_DOCUMENTO_TIPO_ALFRESCO = "ALFRESCO";

	// Eventi calendario
	public final static int EVENTI_CALENDARIO_DATA_TERMINE = 1;
	public final static int EVENTI_CALENDARIO_ESPRESSIONE_PARERI = 2;
	public final static int EVENTI_CALENDARIO_TERMINE_RICHIESTA_INTEGRAZIONI = 3;
	public final static int EVENTI_CALENDARIO_PRIMA_SESSIONE_SIMULTANEA = 4;
	
	// Campi errore validazione
	public final static String CAMPO_ERRORE_ESPRESSIONE_PARERI = "definition.instance.endOpinionDate";
	public final static String CAMPO_ERRORE_TERMINE_RICHIESTA_INTEGRAZIONI = "definition.instance.endIntegrationDate";
	public final static String CAMPO_ERRORE_SESSIONE_SIMULTANEA = "definition.instance.firstSessionDate";
	public final static String CAMPO_ERRORE_DATA_TERMINE = "definition.instance.expirationDate";
	
	// tipo protocollo
	public final static int TIPO_PROTOCOLLO_CONFERENZA_SUAP = 1;
	public final static int TIPO_PROTOCOLLO_SUAP = 2;
	
	// stato protocollo
	public final static int STATO_PROTOCOLLO_PROTOCOLLO_SOLO_MEETPAD = -1;
	public final static int STATO_PROTOCOLLO_DA_PROTOCOLLARE_ESTERNAMENTE = 0;
	public final static int STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO = 1;
	public final static int STATO_PROTOCOLLO_PROTOCOLLATA = 3;
	public final static int STATO_PROTOCOLLO_IN_ERRORE = 5;
	

}
