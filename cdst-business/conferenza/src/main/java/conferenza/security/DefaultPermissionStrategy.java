package conferenza.security;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import conferenza.model.Conferenza;
import conferenza.model.Evento;
import conferenza.model.ModificaData;
import conferenza.util.DbConst;

@Component("default_permission_strategy")
public class DefaultPermissionStrategy extends PermissionStrategy {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPermissionStrategy.class);
	
	public int getStatoConferenza(Conferenza conferenza) {
		return Integer.parseInt(conferenza.getStato().getCodice());
	}
	
	public int getTipoConferenza(Conferenza conferenza) {
		return Integer
				.parseInt(conferenza.getTipologiaConferenzaSpecializzazione().getTipologiaConferenza().getCodice());
	}
	
	/**
	 * !(state === ConferenceState.CLOSED || state === ConferenceState.ARCHIVIED)
	 */
	public Boolean isAbilitato_CONFERENZA_EDIT(Conferenza conferenza) {
		/* S.D. - Modifica test per MAC 19 */
		return /*getStatoConferenza(conferenza) != DbConst.STATO_CHIUSA
				&& */getStatoConferenza(conferenza) != DbConst.STATO_ARCHIVIATA;
	}
	
	/**
	 * !( state === ConferenceState.JUDGMENT || state === ConferenceState.CLOSED ||
	 * state === ConferenceState.ARCHIVIED || state === ConferenceState.DRAFT );
	 */
	public Boolean isAbilitato_CONFERENZA_DELETE(Conferenza conferenza) {
		return getStatoConferenza(conferenza) != DbConst.STATO_VALUTAZIONE
				&& getStatoConferenza(conferenza) != DbConst.STATO_CHIUSA
				&& getStatoConferenza(conferenza) != DbConst.STATO_ARCHIVIATA
				&& getStatoConferenza(conferenza) != DbConst.STATO_BOZZA;
	}
	
	/**
	 * if (type.key === ConferenceType.PRELIMINARY) { return true; }
	 * 
	 * return !( state === ConferenceState.COMPILING || state ===
	 * ConferenceState.CLOSED || state === ConferenceState.ARCHIVIED || state ===
	 * ConferenceState.DRAFT );
	 */
	public Boolean isAbilitato_CONFERENZA_CLONE(Conferenza conferenza) {
		return (getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_ISTRUTTORIA_SIMULTANEA)
				|| (getStatoConferenza(conferenza) != DbConst.STATO_COMPILAZIONE
						&& getStatoConferenza(conferenza) != DbConst.STATO_CHIUSA
						&& getStatoConferenza(conferenza) != DbConst.STATO_ARCHIVIATA
						&& getStatoConferenza(conferenza) != DbConst.STATO_BOZZA);
	}
	
	/**
	 * address !== null && address !== undefined && (address.indexOf('http') > -1 ||
	 * address.indexOf('https') > -1)
	 */
	public Boolean isAbilitato_CONFERENZA_SKYPE(Conferenza conferenza) {
		String address = conferenza.getIndirizzoSessioneSimultanea();
		return address != null && !address.trim().equals("") && (address.contains("http") || address.contains("https"));
	}
	
	/**
	 * (ConferenceType.BROADBAND || ConferenceType.PRELIMINARY ||
	 * ConferenceType.CONCURRENTLY) && isFirstSimultaneousSessionExpired() &&
	 * !CONFERENCE_MEMO
	 */
	public Boolean isAbilitato_INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE(Conferenza conferenza) {
		//Cambiare la prima parte tra parentesi e mettere solo che sia diverso da semplificata
//		return (getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_DECISORIA_SIMULTANEA
//				|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_ISTRUTTORIA_SIMULTANEA
//				|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_SIMULTANEA)
		return (getTipoConferenza(conferenza) != DbConst.TIPOLOGIA_CONFERENZA_SEMPLIFICATA)
				&& isExpiredDate(conferenza, DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE, conferenza);
	}
	
	public Boolean isAbilitato_INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO(Conferenza conferenza) {
		// Modifica per debug: occorre capire quale condizione non è true
		LOGGER.debug("DefaultPermissionStrategy.isAbilitato_INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO");
		LOGGER.debug("ID Conferenza: " + conferenza.getIdConferenza());
		int getTipoConferenza = getTipoConferenza(conferenza);
		LOGGER.debug("getTipoConferenza="+getTipoConferenza);
		boolean isExpiredDate = isExpiredDate(conferenza, DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA);
		LOGGER.debug("isExpiredDate="+isExpiredDate);
		boolean eventExists = eventExists(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO, conferenza);
		LOGGER.debug("eventExists="+eventExists);
		return (getTipoConferenza == DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS && isExpiredDate && !eventExists);
//		return (getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS)
//				&& isExpiredDate(conferenza, DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA)
//				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO, conferenza);
	}
	
	/**
	 * !CONFERENCE_CLOSING && CONFERENCE_MEMO && ConferenceType.PRELIMINARY)
	 */
	public Boolean isAbilitato_INSERT_EVENTO_CHIUSURA_CONFERENZA(Conferenza conferenza) {
		//Rimuovere l'ultimo and. Andrebbe aggiunto anche l'and isExpiredDate(conferenza, DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA) ?
		//Da excel sembrerebbe di sì, ma forse, non essendoci caricato il verbale, non è necessario
		return !eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_CONFERENZA, conferenza)
				&& ((eventExists(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE, conferenza)) 
					|| (eventExists(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO, conferenza)));
				//&& getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_ISTRUTTORIA_SIMULTANEA;
	}
	
	/**
	 * !isEndIntegrationDateExpired && !INTEGRATION_CLOSED &&
	 * !INTEGRATION_ONLY_ONE_REQUEST
	 */
	public Boolean isAbilitato_INSERT_EVENTO_CHIUSURA_INTEGRAZIONI(Conferenza conferenza) {
		//Sembra ok, ma se non c'è la data? Capire richiesta integrazioni
		return !isExpiredDate(conferenza, DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_INTEGRAZIONI, conferenza)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_UNICA_INTEGRAZIONE, conferenza);
	}
	
	/**
	 * ConferenceState.JUDGMENT && !CONFERENCE_CLOSING
	 */
	public Boolean isAbilitato_INSERT_EVENTO_COMUNICAZIONE_GENERICA(Conferenza conferenza) {
		//OK
		return getStatoConferenza(conferenza) == DbConst.STATO_VALUTAZIONE
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_CONFERENZA, conferenza);
	}
	
	/**
	 * ((ConferenceType.SIMPLIFY && isEndOpinionDateExpired()) ||
	 * (ConferenceType.CONCURRENTLY && isFirstSimultaneousSessionExpired())) &&
	 * !isClosed() && !FINAL_RESOLUTION
	 */
	public Boolean isAbilitato_INSERT_EVENTO_DETERMINAZIONE_FINALE(Conferenza conferenza) {
		//Sembra ok
		return ((getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_SEMPLIFICATA
				&& isExpiredDate(conferenza, DbConst.DATA_TERMINE_ESPRESSIONE_PARERI) )
				|| (((getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_SIMULTANEA
						|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_DECISORIA_SIMULTANEA
						|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_ISTRUTTORIA_SIMULTANEA
						|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_REGIONALE
						|| getTipoConferenza(conferenza) == DbConst.TIPOLOGIA_CONFERENZA_DOMUS)
						&& dataExpired(conferenza.getPrimaSessioneSimultanea())))
						&& getStatoConferenza(conferenza) != DbConst.STATO_CHIUSA
						&& !eventExists(DbConst.TIPOLOGIA_EVENTO_DETERMINAZIONE_FINALE, conferenza));
	}
	
	/**
	 * !isEndOpinionDateExpired()
	 */
	public Boolean isAbilitato_INSERT_EVENTO_ESPRESSIONE_PARERI(Conferenza conferenza) {
		//Dall'excel risulta che si ha solo per il partecipante e solo quando si passa in stato valutazione. Lasciamo così
		return !isExpiredDate(conferenza, DbConst.DATA_TERMINE_ESPRESSIONE_PARERI);
	}
	
	/**
	 * !IsEndIntegrationDateExpired() && INTEGRATION_ONLY_ONE_REQUEST &&
	 * !INTEGRATION_SEND
	 */
	public Boolean isAbilitato_INSERT_EVENTO_INVIA_INTEGRAZIONI(Conferenza conferenza) {
		//OK
		return !isExpiredDate(conferenza, DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA)
				&& eventExists(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_UNICA_INTEGRAZIONE, conferenza)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_INVIO_INTEGRAZIONI, conferenza);
	}
	
	/**
	 * !isEndIntegrationDateExpired() && !INTEGRATION_CLOSED
	 */
	public Boolean isAbilitato_INSERT_EVENTO_RICHIESTA_INTEGRAZIONI(Conferenza conferenza) {
		//OK. Aggiunto lo stato valutazione
		return getStatoConferenza(conferenza) == DbConst.STATO_VALUTAZIONE
				&& !isExpiredDate(conferenza, DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_INTEGRAZIONI, conferenza);
	}
	
	/**
	 * !isEndIntegrationDateExpired && INTEGRATION_CLOSED &&
	 * !INTEGRATION_ONLY_ONE_REQUEST
	 */
	public Boolean isAbilitato_INSERT_EVENTO_RICHIESTA_UNICA_INTEGRAZIONI(Conferenza conferenza) {
		//Forse è giusto
		return !isExpiredDate(conferenza, DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA)
				&& eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_INTEGRAZIONI, conferenza)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_UNICA_INTEGRAZIONE, conferenza);
	}
	
	/**
	 * INTEGRATION_SEND && !INTEGRATION_REGISTERED
	 */
	public Boolean isAbilitato_INSERT_EVENTO_TRASMISSIONE_INTEGRAZIONI_PROTOCOLLATE(Conferenza conferenza) {
		return eventExists(DbConst.TIPOLOGIA_EVENTO_INVIO_INTEGRAZIONI, conferenza)
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_TRASMISSIONE_INTEGRAZIONI, conferenza);
	}
	
	/**
	 * cONFERENZA IN VALUTAZIONE E ON COCLUSA
	 */
	public Boolean isAbilitato_INSERT_EVENTO_MODIFICA_DATA(Conferenza conferenza) {
		//Sembra corretto qua e sbagliato nell'excel
		return  getStatoConferenza(conferenza) == DbConst.STATO_VALUTAZIONE
				&& !eventExists(DbConst.TIPOLOGIA_EVENTO_CHIUSURA_CONFERENZA, conferenza);
	}
	
	private boolean isExpiredDate(Conferenza conferenza, String tipoData) {
		
		Date dateToCheck = null; 
		switch (tipoData) {
		case DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA:
			dateToCheck = conferenza.getPrimaSessioneSimultanea();
			break;
		case DbConst.DATA_TERMINE:
			dateToCheck = conferenza.getDataTermine();
			break;
		case DbConst.DATA_TERMINE_ESPRESSIONE_PARERI:
			dateToCheck = conferenza.getTermineEspressionePareri();
			break;
		case DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA:
			dateToCheck = conferenza.getTermineRichiestaIntegrazioniConferenza();
			break;
		}
		
		/*
		if(conferenza.getModificaData() != null && conferenza.getModificaData().size() > 0) {
			Collections.sort(conferenza.getModificaData(), (d1, d2) -> {
				return d2.getIdModificaData() - d1.getIdModificaData();
			});
		}
		
		for(ModificaData modificaData : conferenza.getModificaData()){
			if(modificaData.getTipoData().getCodice().equals( tipoData )){
				dateToCheck = modificaData.getDataNew();
				break;
			}
		}
		*/
		return dataExpired(dateToCheck);
	}
	
	private boolean eventExists(int codiceTipoEvento, Conferenza conferenza) {
		for (Evento evento : conferenza.getEventi()) {
			if (evento.getTipoEvento().getCodice().equals(Integer.toString(codiceTipoEvento))) {
				return true;
			}
		}
		return false;
	}

	private boolean dataExpired(Date date) {
		/**
		 * data <= oggi
		 */
		Date oggi = new Date();
		Date today = DateUtils.truncate(oggi, Calendar.DATE);
		return date.compareTo(today) <= 0;
	}

}
