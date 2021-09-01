package conferenza.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.model.ResponsabileConferenza;
import conferenza.repository.ResponsabileConferenzaRepository;

@Service
public class ResponsabileCSTService {
	
	@Autowired
	private ResponsabileConferenzaRepository responsabileConferenzaRepo;

	public Collection<ResponsabileConferenza> getResponsabili() {
		return responsabileConferenzaRepo.findAll();
	}

	public Optional<ResponsabileConferenza> getResponsabileById(int idResponsabileCST) {
		return responsabileConferenzaRepo.findById(idResponsabileCST);
	}

	/**
	 * Questo metodo, ancora da implementare, imposta il campo firmatario a true.
	 * Il metodo, allo stato dell'arte, non può essere implementato perchè allo sviluppatore mancano informazioni.
	 * @param responsabileConferenza
	 */
	public void abilitaResponsabileAllaFirma(ResponsabileConferenza responsabileConferenza) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Questo metodo, ancora da implementare, imposta il campo firmatario a false.
	 * Il metodo, allo stato dell'arte, non può essere implementato perchè allo sviluppatore mancano informazioni.
	 * @param responsabileConferenza
	 */
	public void disabilitaResponsabileAllaFirma(ResponsabileConferenza responsabileConferenza) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Questo metodo, ancora da implementare, ritorna la lista di tutti i responsabili con il campo di abilitazione firma impostato a true.
	 * Il metodo, allo stato dell'arte, non può essere implementato perchè allo sviluppatore mancano informazioni.
	 * @param responsabileConferenza
	 */
	public Collection<ResponsabileConferenza> getResponsabiliFirmatari() {
		// TODO Auto-generated method stub
		return new ArrayList<ResponsabileConferenza>();
	}

}
