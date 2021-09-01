package conferenza.util;

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
	public final static int TIPOLOGIA_CONFERENZA_DECISORIA_SIMULTANEA = 4;
	public final static int TIPOLOGIA_CONFERENZA_ISTRUTTORIA_SIMULTANEA = 5;
	public final static int TIPOLOGIA_CONFERENZA_DOMUS = 6; // xmf? why tipologia DOMUS?
	
	// Tipologia conferenza specializzazione
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_SEMPLIFICATA = 1;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_SIMULTANEA = 2;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_REGIONALE = 3;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_DECISORIA = 4;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_PREISTRUTTORIA = 5;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS = 6;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_INCONTRO_OPERATIVO = 7;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA = 9;
	public final static int TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA = 11;
	
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
	public final static int RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PER_PARERI_INTERNI = 5;
	public final static int RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PER_CONOSCENZA = 6;
	
	// Tipologia Documentazione
	
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENT_ATTACHED = "DOCUMENT_ATTACHED";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA = "DOCUMENTAZIONE_AGGIUNTIVA";
	public final static String TIPOLOGIA_DOCUMENTO_INTERAZIONI = "INTERAZIONI";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE = "DOCUMENTO_INDIZIONE";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO = "DOCUMENTO_ACCREDITAMENTO";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA = "DOCUMENTO_PRE_ISTRUTTORIA";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_VERBALE_RIUNIONE = "VERBALE_RIUNIONE";
	public final static String TIPOLOGIA_DOCUMENTO_DETERMINA = "DETERMINA";
	public final static String TIPOLOGIA_DOCUMENTO_COMUNICAZIONE_GENERICA = "COMUNICAZIONE_GENERICA";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTI_CONDIVISI = "DOCUMENTI_CONDIVISI";
	public final static String TIPOLOGIA_DOCUMENTO_VIDEO = "VIDEO";
	public final static String TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO = "DOCUMENTO_FIRMATO";
	
	//Sezione Documentazione
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTS_ATTACHED = "DOCUMENTS_ATTACHED";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_AGGIUNTIVI = "DOCUMENTI_AGGIUNTIVI";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_INTERAZIONE = "DOCUMENTI_INTERAZIONE";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_INDIZIONE = "DOCUMENTI_INDIZIONE";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_ACCREDITAMENTO = "DOCUMENTI_ACCREDITAMENTO";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_PREISTRUTTORIA = "DOCUMENTI_PREISTRUTTORIA";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_CONDIVISI = "DOCUMENTI_CONDIVISI";
	public final static String SEZIONE_DOCUMENTAZIONE_DOCUMENTI_FIRMA = "DOCUMENTI_FIRMA";
	
	//Categoria Documento
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTAZIONE_CORREDO_ISTANZA = 1;
	public final static int CATEGORIA_DOCUMENTO_ISTANZA = 2;
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTO_INTEGRATIVO = 3;
	public final static int CATEGORIA_DOCUMENTO_NOTA = 4;
	public final static int CATEGORIA_DOCUMENTO_ALTRO = 5;
	public final static int CATEGORIA_DOCUMENTO_COMUNE = 6;
	public final static int CATEGORIA_DOCUMENTO_PROVINCIA = 7;
	public final static int CATEGORIA_DOCUMENTO_RICHIESTA_INTEGRAZIONI = 8;
	public final static int CATEGORIA_DOCUMENTO_PARERE = 9;
	public final static int CATEGORIA_DOCUMENTO_RICHIESTA_UNICA_INTEGRAZIONI = 10;
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTAZIONE_INTEGRATIVA = 11;
	public final static int CATEGORIA_DOCUMENTO_VERBALE_CONFERENZA = 12;
	public final static int CATEGORIA_DOCUMENTO_DETERMINAZIONE_FINALE = 13;
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTO_INDIZIONE = 14;
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTO_CONDIVISO = 15;
	public final static int CATEGORIA_DOCUMENTO_VIDEO_RIUNIONE = 16;
	public final static int CATEGORIA_DOCUMENTO_DOCUMENTO_FIRMATO = 17;
	public final static int CATEGORIA_DOCUMENTO_APPUNTI = 18;
	
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
	public final static int TIPOLOGIA_EVENTO_ESPRESSIONE_PARERI = 11;
	public final static int TIPOLOGIA_EVENTO_DETERMINAZIONE_FINALE = 12;
	public final static int TIPOLOGIA_EVENTO_TRASMISSIONE_INTEGRAZIONI = 13;
	public final static int TIPOLOGIA_EVENTO_ABILITA_MODIFICA_RICHIEDENTE = 14;
	public final static int TIPOLOGIA_EVENTO_REVOCA_MODIFICA_RICHIEDENTE = 15;
	public final static int TIPOLOGIA_EVENTO_TERMINA_MODIFICA_RICHIEDENTE = 16;
	public final static int TIPOLOGIA_EVENTO_RICHIESTA_ACCREDITAMENTO = 17;
	public final static int TIPOLOGIA_EVENTO_CONFERMA_ACCREDITAMENTO = 18;
	public final static int TIPOLOGIA_EVENTO_RIFIUTA_ACCREDITAMENTO = 19;
	public final static int TIPOLOGIA_EVENTO_INSERIMENTO_DOCUMENTAZIONE_CONDIVISA = 24;
	public final static int TIPOLOGIA_EVENTO_CARICAMENTO_DOCUMENTO_FIRMA = 25;
	public final static int TIPOLOGIA_EVENTO_MODIFICA_DATA = 26;
	public final static int TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO = 31;
	
	// EmailStatus
	public final static String EMAIL_STATUS_ACCETTAZIONE = "ACCETTAZIONE";
	public final static String EMAIL_STATUS_AVVISODINONACCETTAZIONE = "AVVISODINONACCETTAZIONE";
	public final static String EMAIL_STATUS_AVVISODIMANCATACONSEGNA = "AVVISODIMANCATACONSEGNA";
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
	public final static String REGISTRO_DOCUMENTO_FONTE_CONFERENZA = "CONFERENZA";
	public final static String REGISTRO_DOCUMENTO_FONTE_SUAP = "SUAP";
	public final static String REGISTRO_DOCUMENTO_FONTE_PALEO = "PALEO";
	public final static String REGISTRO_DOCUMENTO_FONTE_ALLEGATIPALEO = "ALLEGATIPALEO";
	public final static String REGISTRO_DOCUMENTO_FONTE_ONLYOFFICE= "ONLYOFFICE";
	public final static String REGISTRO_DOCUMENTO_FONTE_DOMUS= "ALLEGATIDOMUS";
	
	// Registro Documento Tipo
	public final static String REGISTRO_DOCUMENTO_TIPO_FILESYSTEM = "FS";
	public final static String REGISTRO_DOCUMENTO_TIPO_ALFRESCO = "ALFRESCO";
	public final static String REGISTRO_DOCUMENTO_TIPO_PALEO = "PALEO";
	public final static String REGISTRO_DOCUMENTO_TIPO_ALLEGATIPALEO = "ALLEGATIPALEO";
	public final static String REGISTRO_DOCUMENTO_TIPO_ONLYOFFICE = "ONLYOFFICE";
	public final static String REGISTRO_DOCUMENTO_TIPO_URL = "URL";
	public final static String REGISTRO_DOCUMENTO_TIPO_ALLEGATIDOMUS = "ALLEGATIDOMUS";
	
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
	public final static int TIPO_PROTOCOLLO_PALEO = 3;
	
	// tipo data modificabile
	public final static String DATA_TERMINE = "DATA_TERMINE";
	public final static String DATA_PRIMA_SESSIONE_SIMULTANEA = "DATA_PRIMA_SESSIONE_SIMULTANEA";
	public final static String DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA = "DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA";
	public final static String DATA_TERMINE_ESPRESSIONE_PARERI = "DATA_TERMINE_ESPRESSIONE_PARERI";
		
	// stato protocollo
	public final static int STATO_PROTOCOLLO_PROTOCOLLO_SOLO_CONFERENZA = -1;
	public final static int STATO_PROTOCOLLO_DA_PROTOCOLLARE_ESTERNAMENTE = 0;
	public final static int STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO = 1;
	public final static int STATO_PROTOCOLLO_PROTOCOLLATA = 3;
	public final static int STATO_PROTOCOLLO_IN_ERRORE = 5;
	
	public final static String STATO_PROTOCOLLO_PROTOCOLLO_SOLO_CONFERENZA_DESC = "Protocollo solo Conferenza";
	public final static String STATO_PROTOCOLLO_DA_PROTOCOLLARE_ESTERNAMENTE_DESC = "Da protocollare esternamente";
	public final static String STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO_DESC = "Protocollazione in corso";
	public final static String STATO_PROTOCOLLO_PROTOCOLLATA_DESC = "Protocollata";
	public final static String STATO_PROTOCOLLO_IN_ERRORE_DESC = "In Errore";
	
	// permesso_azione
	public final static String PERMESSO_AZIONE_CONFERENZA_EDIT = "CONFERENZA_EDIT";
	public final static String PERMESSO_AZIONE_CONFERENZA_CLONE = "CONFERENZA_CLONE";
	public final static String PERMESSO_AZIONE_CONFERENZA_DELETE = "CONFERENZA_DELETE";
	public final static String PERMESSO_AZIONE_CONFERENZA_SKYPE = "CONFERENZA_SKYPE";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_CHIUSURA_CONFERENZA = "INSERT_EVENTO_CHIUSURA_CONFERENZA";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_CHIUSURA_INTEGRAZIONI = "INSERT_EVENTO_CHIUSURA_INTEGRAZIONI";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_RICHIESTA_UNICA_INTEGRAZIONI = "INSERT_EVENTO_RICHIESTA_UNICA_INTEGRAZIONI";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_COMUNICAZIONE_GENERICA = "INSERT_EVENTO_COMUNICAZIONE_GENERICA";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_TRASMISSIONE_INTEGRAZIONI_PROTOCOLLATE = "INSERT_EVENTO_TRASMISSIONE_INTEGRAZIONI_PROTOCOLLATE";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_DETERMINAZIONE_FINALE = "INSERT_EVENTO_DETERMINAZIONE_FINALE";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE = "INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_RICHIESTA_INTEGRAZIONI = "INSERT_EVENTO_RICHIESTA_INTEGRAZIONI";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_ESPRESSIONE_PARERI = "INSERT_EVENTO_ESPRESSIONE_PARERI";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_INVIA_INTEGRAZIONI = "INSERT_EVENTO_INVIA_INTEGRAZIONI";
	public final static String PERMESSO_AZIONE_INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO = "INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO";
	
	// permesso_ruolo
	public final static String PERMESSO_RUOLO_NON_DEFINITO = "NON_DEFINITO";
	public final static String PERMESSO_RUOLO_RICHIEDENTE = "RICHIEDENTE";
	public final static String PERMESSO_RUOLO_RESPONSABILE = "RESPONSABILE";
	public final static String PERMESSO_RUOLO_CREATORE = "CREATORE";
	public final static String PERMESSO_RUOLO_PARTECIPANTE = "PARTECIPANTE";
	public final static String PERMESSO_RUOLO_AMMINISTRATORE = "AMMINISTRATORE";
	
	// votazione_criterio
	public final static String VOTAZIONE_CRITERIO_EVENTO = "EVENTO";
	public final static String VOTAZIONE_CRITERIO_MAGGIORANZA = "MAGGIORANZA";
	public final static String VOTAZIONE_CRITERIO_UNANIMITA = "UNANIMITA";
	public final static String VOTAZIONE_CRITERIO_VALUTAZIONE = "VALUTAZIONE";

	// votazione_esito
	public final static String VOTAZIONE_ESITO_APPROVATA = "APPROVATA";
	public final static String VOTAZIONE_ESITO_NON_APPROVATA = "NON_APPROVATA";

	// votazione_stato
	public final static String VOTAZIONE_STATO_PREPARAZIONE = "PREPARAZIONE";
	public final static String VOTAZIONE_STATO_IN_CORSO = "IN_CORSO";
	public final static String VOTAZIONE_STATO_TERMINATA = "TERMINATA";
	public final static String VOTAZIONE_STATO_ESITO_IMPOSTATO = "ESITO_IMPOSTATO";

	// votazione_tipo
	public final static String VOTAZIONE_TIPO_VOTAZIONE = "VOTAZIONE";
	public final static String VOTAZIONE_TIPO_CALENDARIZZAZIONE = "CALENDARIZZAZIONE";
	public final static String VOTAZIONE_TIPO_RILEVAZIONE_PRESENZE = "RILEVAZIONE_PRESENZE";
	
	//Stati firma
	
	public final static String LOCKED = "LOCKED";
	public final static String UNLOCKED = "UNLOCKED";
	public final static String SIGNING = "SIGNING";
	public final static String SIGNED = "SIGNED";
	public final static String COMPLETED = "COMPLETED";
	public final static String DELETED = "DELETED";
	
	//Ordinanza
	public final static String ORDINANZA_4 = "4";
	public final static String ORDINANZA_8 = "8";
	public final static String ORDINANZA_13 = "13";
	public final static String ORDINANZA_14 = "14";
	public final static String ORDINANZA_19 = "19";
	public final static String ORDINANZA_100 = "100";
	public final static String ORDINANZA_23 = "23";
	public final static String ORDINANZA_32 = "32";
	public final static String ORDINANZA_27 = "27";
	public final static String ORDINANZA_33 = "33";
	public final static String ORDINANZA_37 = "37";
	public final static String ORDINANZA_48 = "48";
	public final static String ORDINANZA_56 = "56";
	public final static String ORDINANZA_77 = "77";
	public final static String ORDINANZA_64 = "64";
	
	//Tipologie pratica
	public final static String TIPOLOGIA_RICOSTRUZIONE = "Ricostruzione";
	public final static String TIPOLOGIA_RICOSTRUZIONE_COD = "5";
	
	//azioni
	public final static String ORDINANZA_4_8_COD = "200";
	public final static String ORDINANZA_13_COD = "201";
	public final static String ORDINANZA_19_COD = "202";
	public final static String ORDINANZA_23_32_COD = "210";
	public final static String ORDINANZA_27_COD = "211";
	public final static String ORDINANZA_33_COD = "212";
	public final static String ORDINANZA_37_COD = "213";
	public final static String ORDINANZA_48_COD = "214";
	public final static String ORDINANZA_56_COD = "215";
	public final static String ORDINANZA_77_COD = "216";
	public final static String ORDINANZA_100_COD = "217";
	public final static String ORDINANZA_64_COD = "218";
	public final static String ORDINANZA_14_COD = "219";
	
	//attivita
	public final static String ATTIVITA_RICOSTRUZIONE_PRIVATA = "31";
	public final static String ATTIVITA_RICOSTRUZIONE_PUBBLICA = "32";
	

}
