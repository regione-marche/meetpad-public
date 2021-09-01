package conferenza.validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.VotazioneDTO;
import conferenza.DTO.bean.Errore;
import conferenza.builder.VotazioneBuilder;
import conferenza.exception.NotAuthorizedUser;
import conferenza.model.Conferenza;
import conferenza.model.Votazione;
import conferenza.repository.VotazioneRepository;
import conferenza.util.DbConst;

@Component
public class VotazioneValidator {

	@Autowired
	VotazioneRepository votazioneRepository;
	
	@Autowired
	VotazioneBuilder votazioneBuilder;
	
	@Autowired
	HttpServletRequest request;

	private void checkIfUserIsResponsibleOrCreator(Integer votazioneId) {
		
		if(votazioneId != null){
			Votazione votazione = this.votazioneRepository.findById(votazioneId).get();
			if(votazione != null && votazione.getConferenza() != null && !votazioneBuilder.isResponsabileOrCreatore(votazione.getConferenza()) ) {
				throw new NotAuthorizedUser("the user authority must to be of type creator or responsible to create voting");	
			}
		}/*else {
			throw new NotAuthorizedUser("the user authority must to be of type creator or responsible to create voting");	
		}*/
	}
	
	/**
	 * validazione per la rimozione di una votazione
	 * 
	 * @param votazioneDTO
	 * @param locale
	 * @return
	 */
	public List<Errore> validateDeleteVotazione(Integer votazioneId) {
		List<Errore> errors = new ArrayList<>();
		
		this.checkIfUserIsResponsibleOrCreator(votazioneId);			
		return errors;
	}
	
	/**
	 * validazione per la creazione di una votazione
	 * 
	 * @param votazioneDTO
	 * @param locale
	 * @return
	 */
	public List<Errore> validateCreateVotazione(VotazioneDTO votazioneDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
			
		if(votazioneDTO == null){
			String msg = messageSource.getMessage("votazione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("votazione", msg));	
			return errors;				
		}else {
			if(votazioneDTO.getTipoVoto() == null){
				String msg = messageSource.getMessage("votazione.votingType", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("votingType", msg));	
				return errors;				
			}	
			
			if(votazioneDTO.getDataScadenzaVotazione() == null){
				String msg = messageSource.getMessage("votazione.endVotingDate", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("endVotingDate", msg));	
				return errors;				
			}	
			
			if(votazioneDTO.getTipoVoto().getKey().equals(DbConst.VOTAZIONE_TIPO_CALENDARIZZAZIONE) && votazioneDTO.getDataVotazione() == null) {
				String msg = messageSource.getMessage("votazione.votingDate", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("votingDate", msg));	
				return errors;		
			}
			
			this.checkIfUserIsResponsibleOrCreator(votazioneDTO.getId());
		}
				
			
		return errors;
	}
	
	/**
	 * validazione per la modifica di una votazione
	 * 
	 * @param votazioneDTO
	 * @param locale
	 * @return
	 */
	public List<Errore> validateEditVotazione(VotazioneDTO votazioneDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();

		if(votazioneDTO == null || votazioneDTO.getId() == null){
			String msg = messageSource.getMessage("votazione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("votazione", msg));	
			return errors;				
		}
		
		Votazione votazione = votazioneRepository.findById(votazioneDTO.getId()).orElse(null);
		if(votazione == null){
			String msg = messageSource.getMessage("votazione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("votazione", msg));	
			return errors;
		}
		
		this.checkIfUserIsResponsibleOrCreator(votazioneDTO.getId());
				
		return errors;
	}
	
	/**
	 * validazione per l'aggiunta di un voto ad una votazione
	 * 
	 * @param votazioneDTO
	 * @param locale
	 * @return
	 */
	public List<Errore> validateAddVoto(Integer votingId, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		
		if(votingId == null){
			String msg = messageSource.getMessage("votazione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("votazione", msg));	
			return errors;				
		}
		
		Votazione votazione = votazioneRepository.findById(votingId).orElse(null);
		if(votazione == null){
			String msg = messageSource.getMessage("votazione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("votazione", msg));	
			return errors;
		}else {
			if(!votazione.getStatoVotazione().getCodice().equals(DbConst.VOTAZIONE_STATO_IN_CORSO)) {
				throw new NotAuthorizedUser("the user can't vote now, the voting is not open");
			}
		}
		
		
				
		return errors;
	}

}
