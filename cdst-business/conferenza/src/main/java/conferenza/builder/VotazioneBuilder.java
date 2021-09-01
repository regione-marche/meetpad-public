package conferenza.builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.VotazioneDTO;
import conferenza.DTO.VotoDTO;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Votazione;
import conferenza.model.VotazioneVoto;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.VotazioneCriterioRepository;
import conferenza.repository.VotazioneEsitoRepository;
import conferenza.repository.VotazioneRepository;
import conferenza.repository.VotazioneStatoRepository;
import conferenza.repository.VotazioneTipoRepository;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Component
public class VotazioneBuilder extends _BaseBuilder {
	
	  @Autowired
	ConferenzaRepository conferenzaRepository;

	@Autowired
	VotazioneRepository votazioneRepository;

	@Autowired
	VotazioneEsitoRepository votazioneEsitoRepository;

	@Autowired
	VotazioneStatoRepository votazioneStatoRepository;

	@Autowired
	VotazioneCriterioRepository votazioneCriterioRepository;

	@Autowired
	VotazioneTipoRepository votazioneTipoRepository;

	@Autowired
	UtenteService utenteService;

	/**
	 * Crea un oggetto Votazione a partire da un DTO
	 * 
	 * @author stdiana
	 * @param votazioneDTO
	 * @return
	 */
	public Votazione buildVotazione(VotazioneDTO votazioneDTO, Integer conferenceId) {
		Optional<Votazione> votaz;
		Votazione votazione;
		if (votazioneDTO.getId() == null) {
			votazione = new Votazione();
		} else {
			votaz = votazioneRepository.findById(votazioneDTO.getId());
			votazione = votaz.get();
		}

		if(votazioneDTO.getOggetto() != null) {
			votazione.setArgomento(votazioneDTO.getOggetto());
		}
		
		Conferenza conferenza = conferenzaRepository.findByIdConferenza(conferenceId);
		votazione.setConferenza(conferenza);
		
		if(votazioneDTO.getDataScadenzaVotazione() != null) {
			votazione.setDataScadenza(votazioneDTO.getDataScadenzaVotazione());
		}
		
		if (votazioneDTO.getEsitoVotazione() != null) {
			votazione.setEsitoVotazione(votazioneEsitoRepository.findById(votazioneDTO.getEsitoVotazione().getKey()).orElse(null));
		}
		
		if(votazioneDTO.getStatoVotazione() != null) {
			votazione.setStatoVotazione(votazioneStatoRepository.findById(votazioneDTO.getStatoVotazione().getKey()).orElse(null));
		}
					
		if(votazioneDTO.getCriterioVotazione() != null) {
			votazione.setCriterioVotazione(votazioneCriterioRepository.findById(votazioneDTO.getCriterioVotazione().getKey()).orElse(null));
		}
		
		if(votazioneDTO.getTipoVoto() != null) {
			votazione.setTipoVotazione(votazioneTipoRepository.findById(votazioneDTO.getTipoVoto().getKey()).orElse(null));
		}
		
		if (votazioneDTO.getDataVotazione() != null) {
			votazione.getDati().setData(votazioneDTO.getDataVotazione());
		}

		return votazione;
	}

	/**
	 * Crea un oggetto Voto a partire da un DTO
	 * 
	 * @author stdiana
	 * @param votazioneDTO
	 * @return
	 */
	public VotazioneVoto buildVotazioneVoto(Votazione votazione, VotoDTO votoDTO) {

		VotazioneVoto voto = new VotazioneVoto();
		voto.setVoto(votoDTO.getVoto());
		voto.setMotivazione(votoDTO.getMotivazione());
		voto.setVotazione(votazione);

		
		String cfUtente = utenteService.getCodiceFiscaleUtente();
		Accreditamento accreditamento = utenteService.getAccreditamento(votazione.getConferenza(), cfUtente);
		if(accreditamento != null) {
			voto.setPartecipante(accreditamento.getPartecipante());
		}

		voto.setDataVoto(new Date());
		return voto;
	}

	/**
	 * Crea un oggetto Votazione a partire da un DTO
	 * 
	 * @author stdiana
	 * @param votazione
	 * @return
	 */
	public VotazioneDTO buildVotazioneDTO(Votazione votazione) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

		VotazioneDTO votazioneDTO = new VotazioneDTO();

		votazioneDTO.setId(votazione.getIdVotazione());
		votazioneDTO.setOggetto(votazione.getArgomento());
		votazioneDTO.setCriterioVotazione(createNotNullLabelValue(votazione.getCriterioVotazione()));
		votazioneDTO.setDataScadenzaVotazione(votazione.getDataScadenza());
		votazioneDTO.setEsitoVotazione(createNotNullLabelValue(votazione.getEsitoVotazione()));
		votazioneDTO.setStatoVotazione(createNotNullLabelValue(votazione.getStatoVotazione()));
		votazioneDTO.setTipoVoto(createNotNullLabelValue(votazione.getTipoVotazione()));
		votazioneDTO.setEsitoVotazione(createNotNullLabelValue(votazione.getEsitoVotazione()));

		List<VotazioneVoto> votazioneVotoList = votazione.getVoti();
		votazioneVotoList.forEach(votazioneVoto -> {
			VotoDTO votoDTO = new VotoDTO();
			votoDTO.setDataVotazione(dateFormat.format(votazioneVoto.getDataVoto()));
			votoDTO.setMotivazione(votazioneVoto.getMotivazione());

			votoDTO.setPartecipante(createNotNullLabelValue(votazioneVoto.getPartecipante()));
			votoDTO.setVoto(votazioneVoto.getVoto());
			votazioneDTO.addVoto(votoDTO);
		});

		if(votazione.getDati() != null) {
			votazioneDTO.setDataVotazione( votazione.getDati().getData() );
		}
		
		
		votazioneDTO.setCanClose(canClose(votazione));
		votazioneDTO.setCanDelete(canDelete(votazione));
		votazioneDTO.setCanEdit(canEdit(votazione));
		votazioneDTO.setCanStart(canStart(votazione));
		votazioneDTO.setCanVote(canVote(votazione));
		votazioneDTO.setCanEvaluate(canEvaluate(votazione));

		return votazioneDTO;
	}

	public Boolean hasVoted(Votazione votazione) {

		Boolean hasVoted = false;
		String codiceFiscaleUtenteAutenticato = utenteService.getCodiceFiscaleUtente();
		Accreditamento accreditamento = utenteService.getAccreditamento(votazione.getConferenza(), codiceFiscaleUtenteAutenticato);
		
		for(VotazioneVoto voto : votazione.getVoti()){
			if(voto.getPartecipante().getIdPartecipante() == accreditamento.getPartecipante().getIdPartecipante()) {
				hasVoted = true;
				break;
			}
		};
		
		return hasVoted;
	}

	public Boolean canVote(Votazione votazione) {
		if (!isResponsabileOrCreatore(votazione.getConferenza()) && votazione.getStatoVotazione().getCodice().equals(DbConst.VOTAZIONE_STATO_IN_CORSO))
			return !hasVoted(votazione);
		else {
			return false;
		}
	}

	public Boolean canDelete(Votazione votazione) {
		if (isResponsabileOrCreatore(votazione.getConferenza()))
			return true;
		else
			return false;
	}

	public Boolean canClose(Votazione votazione) {
		if (isResponsabileOrCreatore(votazione.getConferenza())
				&& votazione.getStatoVotazione().getCodice().equals(DbConst.VOTAZIONE_STATO_IN_CORSO))
			return true;
		else
			return false;
	}

	public Boolean canStart(Votazione votazione) {
		if (isResponsabileOrCreatore(votazione.getConferenza())
				&& votazione.getStatoVotazione().getCodice().equals(DbConst.VOTAZIONE_STATO_PREPARAZIONE))
			return true;
		else
			return false;
	}

	public Boolean canEdit(Votazione votazione) {
		if (isResponsabileOrCreatore(votazione.getConferenza()) && !canEvaluate(votazione))
			return true;
		else
			return false;
	}

	public Boolean canEvaluate(Votazione votazione) {
		if (isResponsabileOrCreatore(votazione.getConferenza())
				&& votazione.getStatoVotazione().getCodice().equals(DbConst.VOTAZIONE_STATO_TERMINATA)
				&& votazione.getCriterioVotazione().getCodice().equals(DbConst.VOTAZIONE_CRITERIO_VALUTAZIONE))
			return true;
		else
			return false;
	}

	public Boolean isResponsabileOrCreatore(Conferenza conferenza) {

		//xmf: -String codiceFiscale = utenteService.getAuthenticatedUserAsResponsibleOfConference(conferenza).getProfilo().getTipoProfilo().getCodice();
		String codiceFiscale = utenteService.getAuthenticatedUserAsResponsibleOfConference(conferenza).getCodiceFiscale();
		return codiceFiscale.equalsIgnoreCase(conferenza.getCodiceFiscaleCreatoreConferenza()) || 
				codiceFiscale.equalsIgnoreCase(conferenza.getCodiceFiscaleResponsabileConferenza());
		
	}

}
