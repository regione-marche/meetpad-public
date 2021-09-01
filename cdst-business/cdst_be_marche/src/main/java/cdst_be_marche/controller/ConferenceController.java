package cdst_be_marche.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cdst_be_marche.DTO.ConferenzaCompletaDTO;
import cdst_be_marche.DTO.ConferenzaDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaConferenzaAnteprimaDTO;
import cdst_be_marche.DTO.RicercaAvanzataDTO;
import cdst_be_marche.DTO.RicercaSempliceDTO;
import cdst_be_marche.DTO.RicercaUnifyDTO;
import cdst_be_marche.DTO.RispostaConferenzaCompletaDTO;
import cdst_be_marche.DTO.RispostaIdentifiableDTO;
import cdst_be_marche.DTO.RispostaListaConferenzaAnteprimaDTO;
import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.mail.JavaMailSenderConfigurator;
import cdst_be_marche.mail.MailContentBuilder;
import cdst_be_marche.mail.bean.MailMetadata;
import cdst_be_marche.model.TockenConference;
import cdst_be_marche.service.ConferenzaService;
import cdst_be_marche.service.JWTsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Conferences API" })
public class ConferenceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

	@Autowired
	ConferenzaService confservice;

	@Autowired
	JavaMailSenderConfigurator mailerconfig;

	@Autowired
	MailContentBuilder mailbuilder;

	@Autowired
	EmailRepositoryService mailerRepository;

	@Autowired
	HttpServletRequest request;

	@Autowired
	JWTsService jwtutils;

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */	
	@GetMapping("/conferences/{id}")
	@ApiOperation(value = "conference identified by 'id' with messages and events")
	public RispostaConferenzaCompletaDTO ricercaVal(@PathVariable int id) {
		LOGGER.debug("Class: ConferenceController - Method: getConferenzaDettaglio");
		ConferenzaCompletaDTO conferenza = this.confservice.findByIdVal(id);
		RispostaConferenzaCompletaDTO risposta = new RispostaConferenzaCompletaDTO();
		risposta.setData(conferenza);
		return risposta;
	}

	/**
	 * Crea una nuova conferenza come clone della conferenza identificata da 'id'.
	 * La nuova conferenza avrà le seguenti caratteristiche:
	 * - stato = 'in compilazione'
	 * - creatore = utente collegato
	 * - resposabile = responsabile conferenza clonata
	 * - informazioni di testata, documenti, partecipanti copiati dalla conferenza clonata
	 * @param id
	 * @param locale
	 * @return
	 */	
	@GetMapping("/conferences/{id}/clone")
	@ApiOperation(value = "create new conference cloning conference identified by 'id'")
	public RispostaIdentifiableDTO cloneConferenza(@PathVariable int id) {
		LOGGER.debug("Class: ConferenceController - Method: cloneConferenza");
		IdentifiableDTO identifiableDTO = this.confservice.clonaConferenza(id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param ricerca
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/simple")
	@ApiOperation(value = "list of 1 conference identified by 'id'")
	public RispostaListaConferenzaAnteprimaDTO ricercaSemplice(RicercaSempliceDTO ricerca) {
		LOGGER.debug("Class: ConferenceController - Method: getConferenzaSemplice");
		ListaConferenzaAnteprimaDTO lista = this.confservice.findByIdSemp(ricerca);
		RispostaListaConferenzaAnteprimaDTO risposta = new RispostaListaConferenzaAnteprimaDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * 
	 * @param ricercaAvDTO
	 * @param locale
	 * @return
	 */
	@GetMapping(value = "/conferences/advanced", produces = "application/json")
	@ApiOperation(value = "list of conferences meet the search parameters in query string")
	public RispostaListaConferenzaAnteprimaDTO ricercaAvanzata(RicercaAvanzataDTO ricercaAvDTO) {
		LOGGER.debug("Class: ConferenceController - Method: getConferenzaAvanzata");
		ListaConferenzaAnteprimaDTO lista = this.confservice.findAllAv(ricercaAvDTO);
		RispostaListaConferenzaAnteprimaDTO risposta = new RispostaListaConferenzaAnteprimaDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	@GetMapping(value = "/conferences/unifyResearch", produces = "application/json")
	@ApiOperation(value = "list of conferences meet the only one search parameter in query string")
	public RispostaListaConferenzaAnteprimaDTO ricercaAvanzata(RicercaUnifyDTO ricerca) {
		LOGGER.debug("Class: ConferenceController - Method: getConferenzaAvanzata");
		ListaConferenzaAnteprimaDTO lista = this.confservice.findUnifyResearch(ricerca);
		RispostaListaConferenzaAnteprimaDTO risposta = new RispostaListaConferenzaAnteprimaDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	/**
	 * 
	 * @param conference
	 * @param locale
	 * @return
	 */
	@PostMapping("/conferences")
	@ApiOperation(value = "insert conference")
	public RispostaIdentifiableDTO creaConferenza(@RequestBody ConferenzaDTO conference) {
		LOGGER.debug("Class: ConferenceController - Method: postConferenza");
		IdentifiableDTO identifiableDTO = this.confservice.creaConferenza(conference);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param conference
	 * @param id
	 * @param locale
	 * @return
	 */
	@PutMapping("/conferences/{id}")
	@ApiOperation(value = "modify conference")
	public RispostaIdentifiableDTO modificaConferenza(@RequestBody ConferenzaDTO conference, @PathVariable Integer id) {
		LOGGER.debug("Class ConferenceController - Method: putConferenza");
		IdentifiableDTO identifiableDTO = this.confservice.modificaConferenza(conference, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@DeleteMapping("/conferences/{id}")
	@ApiOperation(value = "delete conference identified by 'id'")
	public RispostaNoData eliminaConferenza(@PathVariable Integer id) {
		LOGGER.debug("Class: ConferenceController - Method: deleteConferenza");
		String esito = this.confservice.eliminaConferenza(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

	/**
	 * 
	 * @param conference
	 * @param id
	 * @param locale
	 * @return
	 */
	@PatchMapping("/conferences/{id}")
	@ApiOperation(value = "modify only a  few fields of conference")
	public RispostaIdentifiableDTO patchConferenza(@RequestBody ConferenzaDTO conference, @PathVariable Integer id) {
		LOGGER.debug("Class: ConferenceController - Method: patchConferenza");
		IdentifiableDTO identifiableDTO = this.confservice.patchConferenza(conference, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}

	/**
	 * @TODO: gestione degli errori
	 * @return
	 */
	/**
	 * 
	 * @param tkn1
	 * @param tkn2
	 * @return
	 */
	@RequestMapping(value = "/conferences/autoaccreditamento/{tkn1}/{tkn2}/", method = RequestMethod.GET)
	public RispostaNoData autoaccreditamento(@PathVariable String tkn1, @PathVariable String tkn2) {

		String textmessage = "Messaggio di Prova ";
		boolean isTocken = mailerRepository.checkTocken(tkn1, tkn2);
		if (isTocken == true)
			textmessage = "token ok!";
		else
			textmessage = "token error!";
		// se il tocken è OK ..allora si procede per la VERIFICA
		// dell'autoaccreditamento..
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(textmessage);
		return risposta;
	}

	/**
	 * @TODO: gestione degli errori
	 * @return
	 */
	/**
	 * 
	 * @param idconferenza
	 * @return
	 */
	@RequestMapping(value = "/conferences/emlindizionebycnfrc/{idconferenza}", method = RequestMethod.GET)
	public RispostaNoData emailindettebyconferenceId(@PathVariable int idconferenza) {
		String subject = "Indizione conferenza ";
		String from = "MeetPAD@eng.it";
		String textmessage = "Messaggio di Prova ";
		// --------------------------------------------
		mailerRepository.sendMailForIndizioneConference(from, textmessage, subject, new Integer(idconferenza),null);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(textmessage);
		return risposta;
	}

	/**
	 * 
	 * @param idconferenza
	 * @return
	 */
	@RequestMapping(value = "/conferences/emailindizione/{idconferenza}", method = RequestMethod.GET)
	public RispostaNoData emailindette(
			@PathVariable int idconferenza) {

		MailMetadata mailmetadata = new MailMetadata(null);

		String subject = "[MeetPAD] Indizione conferenza";
		String from = "MeetPAD@eng.it";
		String textmessage = "Messaggio di Prova \n procedere all'autoaccreditamento  ";

		// -------------------------------------------
		//occorre implementare su FE l'url di accreditamento..
		//di seguito l'url al metodo per testare i token
		// -------------------------------------------
		TockenConference tocken = mailerRepository.getTocken("guido.deluca@eng.it", new Integer(1),  new Integer(1), new Integer(idconferenza),
				null, "Indizione");
		String urlTocken = "http://127.0.0.1:8082/conferences/autoaccreditamento/" + tocken.getTKN1() + "/"
				+ tocken.getTKN2() + "/";
		textmessage = textmessage + "\n" + urlTocken + "\n";
		// -------------------------------------------
		
		// -------------------------------------------
		//link criptato alla videoconferenza..o semplicemente per conferenza 
		String url_videoconferenza = mailerRepository.getInfoVideoconferenza(tocken.getIdConferenza());
		// -------------------------------------------
		mailmetadata.setFrom(from);
		mailmetadata.setSubject(subject);
		mailmetadata.setMessage(textmessage);
		mailmetadata.setUrl_accreditamento(urlTocken);
		mailmetadata.setToken_accreditamento(tocken.getTKN2());
		mailmetadata.setUrl_videoconferenza(url_videoconferenza);
		mailmetadata.setIdConferenza(tocken.getIdConferenza());

		// -------------------------------------------
		//modificare la chiamata per tutte le indette..
		//mailerRepository.sendMailAllIndette(from, textmessage, subject);
		// -------------------------------------		
		//TODO: occorrre parametrizzare anche subject...
		mailerRepository.sendMailIndizioneForConference(new Integer(idconferenza),mailerconfig.getFrom(),textmessage,subject,tocken.getTKN2(), null);
		//@deprecated!!!
		//mailerRepository.sendMailIndizione(mailmetadata);

		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(textmessage);
		return risposta;
	}

	/**
	 * Viene passata una stringa con 3 valori separati dal carattere DOT!!! dalla
	 * seconda sottostringa viene preso il codice fiscale.. Esempio:
	 * input="eyJ4NXQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJraWQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOlsiOTVFSFI4ckx6ZHlNa1lDNUxZRWhtOVpUT2ZnYSIsImh0dHA6XC9cL29yZy53c28yLmFwaW1ndFwvZ2F0ZXdheSJdLCJzdWIiOiJNTlRGTkM3MkQ1MkU3ODNPIiwiYXpwIjoiOTVFSFI4ckx6ZHlNa1lDNUxZRWhtOVpUT2ZnYSIsImFtciI6W10sImlzcyI6Imh0dHBzOlwvXC93c28yLm1lZXRwYWQtZGV2LmVuZzo5NDQ3XC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNTM5MTk1MTk1LCJpYXQiOjE1MzkxOTE1OTUsIm5vbmNlIjoiZWVlNWJhY2MtZDUzNi00M2IxLWFiY2UtYjBiOWI5ZGE4Yjc0In0.olgivDUJTXG_3pGbm6wwg0MUJtcm0rf7gS-sWS7vkZHFPXFoRLOacxSJ97khAOch9UDYOK_ecwkZ6fcW3mILU24W_NDlZ5H1HbRceb_zjVI8XRS03RXmMbDUBdC1Thue9vIDKjAtjnQNaBycOZHpwOz-dhagt0kN4K8RbiHOCQF3gdGdEb1L1ZRqh98Ksvn6yVuigjHJzxf54-r0SRafDjr26lD8y4I7xeNoppjPGx2hxkrHxj0jp4hNqGccgfaPe46PDbXBS_oSYOGx7bIbP-IN1BR3fOjyJUtplDXaZ_wzTVmpW4Z6642oVVjsFdmTv2B1IefhUeUAlicI7CaHjQ";
	 * http://127.0.0.1:8082/conferences/token/eyJ4NXQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJraWQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOlsiOTVFSFI4ckx6ZHlNa1lDNUxZRWhtOVpUT2ZnYSIsImh0dHA6XC9cL29yZy53c28yLmFwaW1ndFwvZ2F0ZXdheSJdLCJzdWIiOiJNTlRGTkM3MkQ1MkU3ODNPIiwiYXpwIjoiOTVFSFI4ckx6ZHlNa1lDNUxZRWhtOVpUT2ZnYSIsImFtciI6W10sImlzcyI6Imh0dHBzOlwvXC93c28yLm1lZXRwYWQtZGV2LmVuZzo5NDQ3XC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNTM5MTk1MTk1LCJpYXQiOjE1MzkxOTE1OTUsIm5vbmNlIjoiZWVlNWJhY2MtZDUzNi00M2IxLWFiY2UtYjBiOWI5ZGE4Yjc0In0.olgivDUJTXG_3pGbm6wwg0MUJtcm0rf7gS-sWS7vkZHFPXFoRLOacxSJ97khAOch9UDYOK_ecwkZ6fcW3mILU24W_NDlZ5H1HbRceb_zjVI8XRS03RXmMbDUBdC1Thue9vIDKjAtjnQNaBycOZHpwOz-dhagt0kN4K8RbiHOCQF3gdGdEb1L1ZRqh98Ksvn6yVuigjHJzxf54-r0SRafDjr26lD8y4I7xeNoppjPGx2hxkrHxj0jp4hNqGccgfaPe46PDbXBS_oSYOGx7bIbP-IN1BR3fOjyJUtplDXaZ_wzTVmpW4Z6642oVVjsFdmTv2B1IefhUeUAlicI7CaHjQ
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/conferences/token/{input}", method = RequestMethod.GET)
	public RispostaNoData getcodcicefiscalefromtocken(@PathVariable String input) {
		List el = new ArrayList<>();
		String textmessage = null;
		RispostaNoData risposta = new RispostaNoData();
		try {
			String cf = jwtutils.getCodiceFiscaleFromInput(input);
			textmessage = cf;
		} catch (Exception e) {
			e.printStackTrace();
			el.add(e.getMessage());
			risposta.setErrors(el);
			return risposta;
		}
		risposta.setMsg(textmessage);
		return risposta;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/conferences/infoh", method = RequestMethod.GET)
	public RispostaNoData getinfoh(HttpServletRequest request) {
		String textmessage = "{";
		int c = 0;
		Enumeration enumh = request.getHeaderNames();
		while (enumh.hasMoreElements()) {
			String valn = (String) enumh.nextElement();
			String vals = request.getHeader(valn);
			if (c > 0)
				textmessage = textmessage + ",";
			textmessage = textmessage + "[" + valn + "=" + vals + "]";
			c++;
		}
		textmessage = textmessage + "}";
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(textmessage);
		return risposta;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/conferences/verifytoken/{input}", method = RequestMethod.GET)
	public RispostaNoData verifytoken(@PathVariable String input) {
		List el = new ArrayList<>();
		String textmessage = null;
		RispostaNoData risposta = new RispostaNoData();
		try {
			boolean isvalid = jwtutils.checkToken(input);
			textmessage = "tokenvalido";
		} catch (Exception e) {
			e.printStackTrace();
			el.add(e.getMessage());
			risposta.setErrors(el);
			return risposta;
		}
		risposta.setMsg(textmessage);
		return risposta;
	}

}
