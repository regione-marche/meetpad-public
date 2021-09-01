package conferenza.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.mail.EmailRepositoryService;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Permesso;
import conferenza.model.PermessoAzione;
import conferenza.model.PermessoRuolo;
import conferenza.model.TipoEvento;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.PermessoAzioneRepository;
import conferenza.repository.PermessoRepository;
import conferenza.repository.PermessoRuoloRepository;
import conferenza.security.PermissionStrategy;
import conferenza.util.DbConst;

@Service
public class PermessoService extends _BaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermessoService.class);

	@Autowired
	PermessoRepository permessoRepository;

	@Autowired
	PermessoAzioneRepository permessoAzioneRepository;

	@Autowired
	PermessoRuoloRepository permessoRuoloRepository;

	@Autowired
	ConferenzaRepository conferenzaRepository;

	@Autowired
	UtenteService utenteService;
	
	@Autowired
	private ApplicationContext appContext;

	/**
	 * <pre>
	 * Se esiste Permesso per ruolo e azione:
	 *  	l'abilitazione è calcolata secondo la strategia indicata nella colonna permission_strategy
	 * Altrimenti:
	 * 		l'abilitazione è false
	 * </pre>
	 * 
	 * @param codicePermessoAzione
	 * @param idConferenza
	 * @return
	 */
	public Boolean isAbilitato(String codicePermessoAzione, Integer idConferenza) {
		Conferenza conferenza = conferenzaRepository.findByIdConferenza(idConferenza);
		if (conferenza != null) {
			PermessoRuolo permessoRuolo = getPermessoRuolo(conferenza);
			PermessoAzione permessoAzione = permessoAzioneRepository.findById(codicePermessoAzione).orElse(null);
			Permesso permesso = null;
			if (permessoAzione != null && permessoRuolo != null) {
				permesso = permessoRepository.findByPermessoRuoloAndPermessoAzione(permessoRuolo, permessoAzione).orElse(null);
				if(permesso != null)
					LOGGER.debug("---PermessoService ---isAbilitato --- permesso: " + permesso.getPermissionStrategy());
			}
			if (permesso != null) {
				PermissionStrategy permissionStrategy = (PermissionStrategy) appContext.getBean(permesso.getPermissionStrategy());
				LOGGER.debug("riga 76 - Il permesso è: " + permissionStrategy.isAbilitato(permessoAzione, conferenza) );
				return permissionStrategy.isAbilitato(permessoAzione, conferenza);
			}
		}
		LOGGER.debug("Se sono qua è perché il permesso è false");
		return false;
	}

	/**
	 * <pre>
	 * Determina il ruolo per definire i permessi nella conferenza. 
	 * Il ruolo dipende sia dal ruolo all'interno della conferenza che dal profilo applicativo con le seguenti regole:
	 * - creatore conferenza				--> CREATORE
	 * - ruolo richiedente					--> RICHIEDENTE
	 * - ruolo responsabileConferenza 		--> RESPONSABILE
	 * - ruolo responsabileProcedimento		--> PARTECIPANTE
	 * - ruolo responsabileUnico			--> PARTECIPANTE
	 * - ruolo soggettoAccreditato			--> PARTECIPANTE
	 * - nessun ruolo, profilo CREATORE CDS	--> NON_DEFINITO
	 * - nessun ruolo, profilo RESPONSABILE	--> NON_DEFINITO
	 * - nessun ruolo, profilo PARTECIPANTE	--> NON_DEFINITO
	 * - nessun ruolo, profilo AMMINISTRATORE SISTEMA				--> AMMINISTRATORE
	 * - nessun ruolo, profilo ADMIN AMMINISTRAZIONE PROCEDENTE 	--> AMMINISTRATORE
	 * - nessun ruolo, profilo ADMIN AMMINISTRAZIONI 				--> AMMINISTRATORE
	 * </pre>
	 * 
	 * @param idConferenza
	 * @return
	 */
	private PermessoRuolo getPermessoRuolo(Conferenza conferenza) {
		Accreditamento accreditamento = utenteService.getAccreditamento(conferenza, false);
		String codicePermessoRuolo = null;
		if (utenteService.isCreatoreConferenza(conferenza)) {
			codicePermessoRuolo = DbConst.PERMESSO_RUOLO_CREATORE;
		} else if (accreditamento != null) {
			if (accreditamento.isRichiedente()) {
				codicePermessoRuolo = DbConst.PERMESSO_RUOLO_RICHIEDENTE;
			} else if (accreditamento.isResponsabileConferenza()) {
				codicePermessoRuolo = DbConst.PERMESSO_RUOLO_RESPONSABILE;
			} else {
				codicePermessoRuolo = DbConst.PERMESSO_RUOLO_PARTECIPANTE;
			}
		} else {
			if (utenteService.isAdminSistema() || utenteService.isAdminAmministrazioneProcedente()
					|| utenteService.isAdminAmministrazioni()) {
				codicePermessoRuolo = DbConst.PERMESSO_RUOLO_AMMINISTRATORE;
			} else {
				//codicePermessoRuolo = DbConst.PERMESSO_RUOLO_NON_DEFINITO;
				codicePermessoRuolo = DbConst.PERMESSO_RUOLO_PARTECIPANTE;
			}
		}
		return permessoRuoloRepository.findById(codicePermessoRuolo).orElse(null);
	}

	/**
	 * Tipi evento previsti per la conferenza in base al ruolo dell'utente collegato
	 * @param idConferenza
	 * @return
	 */
	public List<TipoEvento> findConferenceEventTypes(Integer idConferenza) {
		/*
		 * ciclo sulla tabella permesso_azione dove fk_tipo_evento != null
		 * per ogni permesso_azione:
		 * 		if (isAbilitato(permesso_azione.getCodice, idConferenza)) aggiungo a lista eventi
		 */
		List<PermessoAzione> listaPermessoAzione = permessoAzioneRepository.findByTipoEventoIsNotNull();
		List<TipoEvento> listaTipoEvento = new ArrayList<>();
		for (PermessoAzione permessoAzione: listaPermessoAzione) {
			if(isAbilitato(permessoAzione.getCodice(), idConferenza)) {
				listaTipoEvento.add(permessoAzione.getTipoEvento());
			}
		}
		if(listaTipoEvento!=null) {
			LOGGER.info("****Lista eventi da mostrare****");
			for(int i=0; i<listaTipoEvento.size(); i++) {
				LOGGER.debug("-- Evento da mostrare: " + listaTipoEvento.get(i).getDescrizione());
			}
		}
		
		return listaTipoEvento;
	}
	


}
