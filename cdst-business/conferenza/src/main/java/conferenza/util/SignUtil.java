package conferenza.util;

import org.springframework.beans.factory.annotation.Autowired;

import conferenza.DTO.PersonaDTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.FirmaAdapter;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.model.Accreditamento;
import conferenza.model.Documento;
import conferenza.model.Persona;

public class SignUtil {

	@Autowired
	FirmaAdapter firmaAdapter;
	
	/*
	 * Metodo per verificare se l'utente richiedente è un firmatario
	 * 
	 */
	public static Boolean isFirmatario(Documento doc, String codiceFiscaleRichiedente){
		
		Boolean isFirmatario = false;
		for ( Accreditamento firmatario : doc.getFirmatari()) {
			if(firmatario.getPersona().getCodiceFiscale().equalsIgnoreCase(codiceFiscaleRichiedente)) {
				isFirmatario = true;
				break;
			}
		}

		return isFirmatario;
	}
	
//	Optional<Persona> personaOpt = personaRepository.findByCodiceFiscaleIgnoreCase(cfUtenteRichiedente);
	
//	Persona persona = personaOpt.get();
//	
//	List<Accreditamento> accreditamentiPersona = accreditamentoRepo.findByPersona(persona);
//	List<Accreditamento> accreditamentiDocumento = doc.getFirmatari();
//	
//	
//	for(int i=0; i<accreditamenti.size(); i++){
//		Integer idAccr = accreditamenti.get(i).getId();
//		
//	}
	
}
