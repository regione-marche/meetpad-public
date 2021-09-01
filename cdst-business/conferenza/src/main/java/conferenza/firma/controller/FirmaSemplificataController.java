package conferenza.firma.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.service.FirmaSemplificataService;
import conferenza.service.UtenteService;
import io.swagger.annotations.Api;

@CrossOrigin
@RestController
@Api(tags = { "Firma API" })
@RequestMapping(value = "/firmasemplificata")
public class FirmaSemplificataController extends FirmaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirmaSemplificataController.class);
	
	
	@Autowired
	FirmaSemplificataService firmaSemplificataService;
	
	@Autowired
	UtenteService utenteService;
	
	/**
	 * Attività post firma:
	 * 1 - salvataggio del file firmato
	 * 2 - unlock del file
	 * 
	 * Attività pre firma:
	 * [VINCOLO] Se Respondabile è l'utente autorizzato.
	 * =>inizia la sessioen di firma
	 * 
	 * 
	 * @param file
	 * @param idDocumento
	 * @param fk_tipo_firma
	 * @param crc
	 * @return
	 * @throws IOException
	 */
	@Override
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	public RispostaIdentifiableDTO unlock(
			@RequestParam MultipartFile file, 	
			@RequestParam Integer idDocumento,
			@RequestParam Integer fk_tipo_firma,
			@RequestParam String crc,
			@RequestParam Integer idConferenza
	) throws IOException {
		
		FirmaDTO firma =new FirmaDTO();
		//Se in prima firma id Documento è null!!
		firma.setIdDocumento(idDocumento);
		//Necessario per creare idDocumento se l'utente è responsabile..
		firma.setIdConferenza(idConferenza);

		//chiamato il metodo per riflessione per gestire il caso d'uso "Semplificato"
		Integer iTipoFirma=firmaSemplificataService.getTipoFirma(firmaSemplificataService.TIPOFIRMA_FSSEMPLIFICATA);		
		firma.setFk_tipo_firma(iTipoFirma);
		
		//..caricamento del MultipartFile per il responsabile..
		firma.setFile(file);
		
		firma.setCrc(crc);

		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		//-------------------------------------------------------------------
        InputStreamResource isresource = new InputStreamResource(file.getInputStream());      
		try {

			firma.setResource(isresource);
			firma.setFileName(file.getOriginalFilename());
			
			firma=firmaSemplificataService.doActionUnlck(firma);			
			risposta.setMsg("File caricato:"+firma.getShot());
			risposta.setCode("200");
			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		//-------------------------------------------------------------------		
		return risposta;	
	}
	
	
	@Override
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public RispostaIdentifiableDTO cancel(@RequestBody FirmaDTO firma) {
		//TODO: solo il responsabile può cancellare
		
		return super.cancel(firma);
	}
	
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	@RequestMapping(value = "/isclosedsession", method = RequestMethod.POST)
	public RispostaIdentifiableDTO isclosedsession(@RequestBody FirmaDTO firma) {
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();		
		try {			
			
			boolean isclosed=firmaSemplificataService.isSigningSessionClosed(firma);
			if(isclosed)
				risposta.setMsg("Firma Chiusa");
			else
				risposta.setMsg("Firma NON Chiusa");
			risposta.setCode("200");			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		return risposta;
	}
	
	@RequestMapping(value = "/addsigner/{signer}", method = RequestMethod.POST)
	public RispostaIdentifiableDTO addsigner(
			@RequestBody FirmaDTO firma,
			@PathVariable Integer signer
		
		) {
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();		
		try {			
			
			boolean isadded=firmaSemplificataService.addParticipantInSigningSession(firma, signer);
			if(isadded)
				risposta.setMsg("OK");
			else
				risposta.setMsg("KO");
			
			risposta.setCode("200");			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		return risposta;
	}	
	
	
}
