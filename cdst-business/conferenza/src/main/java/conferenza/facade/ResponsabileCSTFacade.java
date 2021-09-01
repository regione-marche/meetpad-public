package conferenza.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.ResponsabileCST;
import conferenza.exception.ResponsabileCSTException;
import conferenza.model.Persona;
import conferenza.model.ResponsabileConferenza;
import conferenza.service.ResponsabileCSTService;
import conferenza.service.UtenteService;

@Component
public class ResponsabileCSTFacade {
	
	@Autowired
	private ResponsabileCSTService responsabileCSTService;
	
	@Autowired
	UtenteService utenteService;
	
	public Collection<ResponsabileCST> getResponsabiliCST(){
		try {
			Collection<ResponsabileCST> responsabiliCandidatoAllaFirma = new ArrayList<>();
			for(ResponsabileConferenza responsabileConferenza : responsabileCSTService.getResponsabili()) {
				Persona persona = responsabileConferenza.getPersona();
				ResponsabileCST responsabileCST = getResponsabileCSTByPersona(persona);
				responsabiliCandidatoAllaFirma.add(responsabileCST);
			}
			return responsabiliCandidatoAllaFirma;
		} catch (RuntimeException e) {
			throw new ResponsabileCSTException("Errore interno nel Sistema.");
		}
	}

	public void abilitaAllaFirmaResponsabileById(String idResponsabileCST) {
		try {
			responsabileCSTService.abilitaResponsabileAllaFirma(getResponsabileConferenza(idResponsabileCST));
		} catch (RuntimeException e) {
			throw new ResponsabileCSTException("Errore interno nel Sistema.");
		}
	}
	
	public void disabilitaAllaFirmaResponsabileById(String idResponsabileCST) {
		try {
			responsabileCSTService.disabilitaResponsabileAllaFirma(getResponsabileConferenza(idResponsabileCST));
		} catch (RuntimeException e) {
			throw new ResponsabileCSTException("Errore interno nel Sistema.");
		}
	}
	
	
	public Collection<ResponsabileCST> getResponsabiliCSTFirmatari() {
		try {
			
			Collection<ResponsabileCST> responsabiliFirmatari = new ArrayList<>();
			for(ResponsabileConferenza responsabileConferenza : responsabileCSTService.getResponsabiliFirmatari()) {
				Persona persona = responsabileConferenza.getPersona();
				ResponsabileCST responsabileCST = getResponsabileCSTByPersona(persona);
				
				//il valore true che sto andando ad impostare, deve venire dall'oggetto entity ResponsabileConferenza
				responsabileCST.setFirmatario(true);
				responsabiliFirmatari.add(responsabileCST);
			}
			return responsabiliFirmatari;
		} catch (RuntimeException e) {
			throw new ResponsabileCSTException("Errore interno nel Sistema.");
		}
		
	}
	
	private ResponsabileConferenza getResponsabileConferenza(String idResponsabileCST) {
		Optional<ResponsabileConferenza> optional = responsabileCSTService.getResponsabileById(Integer.parseInt(idResponsabileCST));
		return optional.get();
	}
	
	private ResponsabileCST getResponsabileCSTByPersona(Persona persona) {
		String nome = persona.getNome();
		String cognome = persona.getCognome();
		return new ResponsabileCST(nome, cognome);
	}

	

	

}
