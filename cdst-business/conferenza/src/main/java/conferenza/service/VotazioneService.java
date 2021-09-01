package conferenza.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import conferenza.DTO.ListaVotazioneDTO;
import conferenza.DTO.VotazioneDTO;
import conferenza.DTO.VotoDTO;
import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.LabelValue;
import conferenza.builder.VotazioneBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotFoundEx;
import conferenza.mail.EmailRepositoryService;
import conferenza.mail.bean.MailMetadata;
import conferenza.model.Conferenza;
import conferenza.model.Votazione;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.VotazioneRepository;
import conferenza.repository.VotazioneVotoRepository;
import conferenza.util.DbConst;
import conferenza.validator.VotazioneValidator;
import conferenza.votazione.MailVotazioneContemptBuilder;

@Service
public class VotazioneService {
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	EmailRepositoryService mailerRepository;
	
	@Autowired
	MailVotazioneContemptBuilder mailbuilder;
	
	@Autowired
	VotazioneRepository votazioneRepository;

	@Autowired
	VotazioneVotoRepository votazioneVotoRepository;
	
	@Autowired
	VotazioneValidator validator;
	
	@Autowired
	MessageSource messageSource;
	
    @Autowired
    ConferenzaRepository conferenzaRepo;
    
    @Autowired
    VotazioneBuilder votazioneBuilder;
	
	
	public void sendEmail(MailMetadata metadati) {
		int otp = otpService.generateOTP(metadati.getCodice_fiscale());
		Map<String, String> parametri=metadati.getParametri();
		parametri.put("otp", String.valueOf(otp));
		String votazione=(String)metadati.getParametri().get("votazione");
		String urlVotazione=getUrlVotazione(votazione);
		parametri.put("votazione", urlVotazione);
		mailbuilder.build_votazione(metadati);
		mailerRepository.sendMailVotazione(metadati);
	}
	
	
	/**
	 * @TODO: ottenere l'indirizzo della votazione
	 * @param idVotazione
	 * @return
	 */
	public String getUrlVotazione(String idVotazione) {		
		return null;
	}

	public String deleteVotazione(Integer id) {
		
		List<Errore> errors = validator.validateDeleteVotazione(id);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		Votazione votazione = this.votazioneRepository.findById(id).get();
		votazione.setDataFine(new Date());
		this.votazioneRepository.save(votazione);
		return "ok";

	}

	/**
	 * Devono essere restituiti tutti gli accreditamenti in qualsiasi stato
	 * 
	 * @param idConferenza
	 * @return
	 */
	public ListaVotazioneDTO findVotazioniConferenza(Integer idConferenza) {
		ListaVotazioneDTO lista = new ListaVotazioneDTO();
		lista.setTotalNumber(new Long(lista.getList().size()));
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(idConferenza);
		if (conferenza == null) {
			throw new NotFoundEx("the conference doesn't exist");
		}

//		for (Partecipante partecipante : conferenza.getPartecipantes()) {
//			for (Accreditamento accreditamento : partecipante.getAccreditati()) {
//				lista.getList().add(this.partecipanteBuilder.buildAccrToAccrDTO(accreditamento));
//			}
//			lista.setTotalNumber(new Long(lista.getList().size()));
//		}
		for(Votazione votazione:conferenza.getVotazioni()){
			lista.getList().add(this.votazioneBuilder.buildVotazioneDTO(votazione));
		}
		
		return lista;
	}
	
	public VotazioneDTO creaVotazione(Integer conferenceId, VotazioneDTO votazione) {
		
		List<Errore> errors = validator.validateCreateVotazione(votazione, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		//in fase di creazione l'unico stato che può avere la votazione è preparazione
		votazione.setStatoVotazione(new LabelValue(DbConst.VOTAZIONE_STATO_PREPARAZIONE, null));
		
		Votazione saved = this.votazioneRepository.save(votazioneBuilder.buildVotazione(votazione, conferenceId));
		return this.votazioneBuilder.buildVotazioneDTO(saved);

	}

	public VotazioneDTO addVoto(Integer conferenceId, Integer votingId, VotoDTO votoDto) {

		List<Errore> errors = validator.validateAddVoto(votingId, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}

		Optional<Votazione> votazione = this.votazioneRepository.findById(votingId);
		this.votazioneVotoRepository.save(votazioneBuilder.buildVotazioneVoto(votazione.get(), votoDto));
		
		return this.votazioneBuilder.buildVotazioneDTO(this.votazioneRepository.findById(votingId).orElse(null));
	}

	public VotazioneDTO editVotazione(Integer conferenceId, VotazioneDTO votazione) {
				
		List<Errore> errors = validator.validateEditVotazione(votazione, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}

		Votazione saved = this.votazioneRepository.save(votazioneBuilder.buildVotazione(votazione, conferenceId));
		return this.votazioneBuilder.buildVotazioneDTO(saved);
	}
	
}
