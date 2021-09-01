package cdst_be_marche.builder;

import org.springframework.stereotype.Component;

import cdst_be_marche.DTO.UtenteDTO;
import cdst_be_marche.model.Utente;

@Component
public class UtenteBuilder extends _BaseBuilder {

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

		getMapper().map(utente, utenteDTO);
		utenteDTO.setProfilo(createNotNullLabelValue(utente.getProfilo().getTipoProfilo()));

		return utenteDTO;
	}

}
