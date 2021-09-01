package conferenza.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.PersonaDTO;
import conferenza.DTO.ProfiloDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.consolleAmministrativa.DTO.UtenteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.UtentePreviewDTO;
import conferenza.model.Profilo;
import conferenza.model.Utente;
import conferenza.repository.ProfiloRepository;

@Component
public class UtenteBuilder extends _BaseBuilder {
	
	@Autowired
	ProfiloRepository profiloRepo;

	/**
	 * Crea un oggetto UtenteDTO a partire da un oggetto Utente
	 * 
	 * @param utente
	 * @return
	 * 
	 * @author arosina
	 */
	public UtenteDTO buildUtenteDTO(Utente utente) {
		UtenteDTO utenteDTO = new UtenteDTO();
		if (utente == null) {
			utenteDTO = null;
		} else {
			getMapper().map(utente, utenteDTO);
			utenteDTO.setProfilo(createNotNullLabelValue(utente.getProfilo().getTipoProfilo()));
		}
		return utenteDTO;
	}

	public Utente buildUtente(UtenteDTO utenteDTO) {
		Utente utente = new Utente();
		getMapper().map(utenteDTO, utente);
		utente.setProfilo(profiloRepo.findById(Integer.parseInt(utenteDTO.getProfilo().getKey())).orElse(null));
		return utente;
	}

	public ProfiloDTO buildProfiloToProfiloDTO(Profilo profilo) {
		ProfiloDTO profiloDTO = new ProfiloDTO();
		profiloDTO.setId(profilo.getId());
		profiloDTO.setTipoProfilo(createNotNullLabelValue(profilo.getTipoProfilo()));
		profiloDTO.setAmministrazioneProcedente(createNotNullLabelValue(profilo.getAmministrazioneProcedente()));
		return profiloDTO;
	}

	public UtenteDTO buildPersonaDTOToUtenteDTO(PersonaDTO personaDTO) {
		UtenteDTO utenteDTO = new UtenteDTO();
		utenteDTO.setNome(personaDTO.getNome());
		utenteDTO.setCognome(personaDTO.getCognome());
		utenteDTO.setCodiceFiscale(personaDTO.getCodiceFiscale());
		utenteDTO.setEmail(personaDTO.getEmail());
		return utenteDTO;
	}

	public UtentePreviewDTO buildUtentePreviewDTO(Utente utente) {
		UtentePreviewDTO utenteDTO = new UtentePreviewDTO();
		utenteDTO.setIdUtente(utente.getIdUtente());
		utenteDTO.setNome(utente.getNome());
		utenteDTO.setCognome(utente.getCognome());
		utenteDTO.setCodiceFiscale(utente.getCodiceFiscale());
		utenteDTO.setAmministrazioneProcedente(createNotNullLabelValue(utente.getProfilo().getAmministrazioneProcedente()));
		return utenteDTO;
	}

	public UtenteCompletoDTO buildUtenteCompletoDTO(Utente utente) {
		UtenteCompletoDTO utenteDTO = null;
		if (utente != null) {
			utenteDTO = new UtenteCompletoDTO();
			utenteDTO.setIdUtente(utente.getIdUtente());
			utenteDTO.setNome(utente.getNome());
			utenteDTO.setCognome(utente.getCognome());
			utenteDTO.setCodiceFiscale(utente.getCodiceFiscale());
			utenteDTO.setAmministrazioneProcedente(
					createNotNullLabelValue(utente.getProfilo().getAmministrazioneProcedente()));
			utenteDTO.setEmail(utente.getEmail());
			utenteDTO.setDelegaCreazioneAltreAmministrazioni(utente.getDelegaCreazioneAltreAmministrazioni());
			utenteDTO.setFlagFirmatario(utente.getFlagFirmatario());
			Profilo profilo = profiloRepo.findById(utente.getProfilo().getId()).orElse(null);
			utenteDTO.setProfilo(
					new LabelValue(Integer.toString(profilo.getId()), profilo.getTipoProfilo().getDescrizione()));

			/*
			// TODO: COMPILE LIST OF GROUPS
			List<GrouppoUtentiDTO> listGruppoUtenti = new ArrayList<GrouppoUtentiDTO>();
			for(GruppoUtenti gruppo : utente.getGruppoUtenti()) {
				listGruppoUtenti.add(buildUtenteCompletoDTO(gruppo.getUtente()));
			}
			utenteDTO.setUtenti(listGruppoUtenti);
			*/
		}
		return utenteDTO;
	}

	public Utente buildUtenteCompletoDTOToUtente(UtenteCompletoDTO utenteDTO) {
		Utente utente = new Utente();
		utente.setNome(utenteDTO.getNome());
		utente.setCognome(utenteDTO.getCognome());
		utente.setCodiceFiscale(utenteDTO.getCodiceFiscale());
		utente.setEmail(utenteDTO.getEmail());
		utente.setDelegaCreazioneAltreAmministrazioni(utenteDTO.getDelegaCreazioneAltreAmministrazioni());
		utente.setProfilo(profiloRepo.findById(Integer.parseInt(utenteDTO.getProfilo().getKey())).orElse(null));
		return utente;
	}	

}
