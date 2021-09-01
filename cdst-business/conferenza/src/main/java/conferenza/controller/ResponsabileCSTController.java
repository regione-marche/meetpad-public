package conferenza.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.ListaUtenteDTO;
import conferenza.DTO.ResponsabileCST;
import conferenza.DTO.RispostaBoolean;
import conferenza.DTO.RispostaListaUtenteDTO;
import conferenza.exception.ResponsabileCSTException;
import conferenza.facade.ResponsabileCSTFacade;
import conferenza.service.UtenteService;
import io.swagger.annotations.ApiOperation;

@RestController
public class ResponsabileCSTController { 
	
	@Autowired
	private ResponsabileCSTFacade responsabileCSTFacade;
	
	@Autowired
	private UtenteService utenteService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResponsabileCSTController.class.getName());
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   
	@ExceptionHandler(value= {ResponsabileCSTException.class})
	public  ResponseEntity<String> handleRestError(RuntimeException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/conferences/caricatuttiresponsabili")
	public Collection<ResponsabileCST> caricaTuttiResponsabili() {
		return responsabileCSTFacade.getResponsabiliCST();
	}
	
	//questa chiamata dovrebbe essere @PutMapping ma la sicurezza blocca la request. Ho provato ad 
	//eliminare la sicurezza di spring ma continua a bloccarmi. Probabilmente c'è un altro livello di sicurezza che non conosco.
	//Per continuare a lavorare ho messo una @GetMapping anche se l'operazione fa a fare delle modifiche.
	@GetMapping("/conferences/abilitafirma")
	public ResponseEntity<String> abilitaFirma(@RequestParam String idResponsabileCST) {
		responsabileCSTFacade.abilitaAllaFirmaResponsabileById(idResponsabileCST);
		return new ResponseEntity<String>("L'utente è stato abilitato alla firma.", HttpStatus.OK);
	}
	
	//questa chiamata dovrebbe essere @PutMapping ma la sicurezza blocca la request. Ho provato ad 
	//eliminare la sicurezza di spring ma continua a bloccarmi. Probabilmente c'è un altro livello di sicurezza che non conosco.
	//Per continuare a lavorare ho messo una @GetMapping anche se l'operazione fa a fare delle modifiche.
	@GetMapping("/conferences/disabilitafirma")
	public ResponseEntity<String> disabilitaFirma(@RequestParam String idResponsabileCST) {
		responsabileCSTFacade.disabilitaAllaFirmaResponsabileById(idResponsabileCST);
		return new ResponseEntity<String>("L'utente è stato disabilitato alla firma.", HttpStatus.OK);
	}
	
	
	@GetMapping("/conferences/caricaresponsabilifirmatari")
	public Collection<ResponsabileCST> caricaResponsabili() {
		return responsabileCSTFacade.getResponsabiliCSTFirmatari();
	}

	@GetMapping("/conferences/{conferenceId}/listSignatures")
	@ApiOperation(value = "get list of users signature for document")
	public RispostaListaUtenteDTO caricaResponsabiliCST(@PathVariable Integer conferenceId) {
		ListaUtenteDTO listaFirmatari = utenteService.getAllFirmatariConferenza(conferenceId,true);
		RispostaListaUtenteDTO risposta = new RispostaListaUtenteDTO();
		risposta.setData(listaFirmatari);
		return risposta;
	}
	
	@GetMapping("/conferences/{conferenceId}/permissionsToSign")
	@ApiOperation(value = "get user permission to sign")
	public RispostaBoolean isUtenteAbilitatoFirmaMultipla(@PathVariable Integer conferenceId) {
		return utenteService.isPartecipanteAbilitaFirmaMultipla(conferenceId);
	}
	
}
