package conferenza.chatbot.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.chatbot.model.Message;
import conferenza.chatbot.model.Response;
import conferenza.chatbot.model.Richieste;
import conferenza.chatbot.service.MessageService;
import conferenza.chatbot.service.RichiesteService;

@RestController
@RequestMapping("/api/customer")
public class RestWebController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private RichiesteService richiesteService;
	
	String[] articoli = {" il "," lo "," la "," i "," gli "," le "," un "," uno "," una "," un'"," del "," dello "," della "," dei "," degli "," delle ", " di "};
	String[] pronomi_personali = {" io "," me "," tu "," lui "," lei "," noi "," voi "," loro ", " essi ", " ti ", " te ", };
	String[] pronomi_possessivi = {" mio "," tuo "," vostro "," nostro "," loro "};
	String[] pronomi_dimostrativi = {" questo "," codesto "," quello "," stesso "," medesimo "};
	String[] pronomi_interrogativi = {" chi "," che cosa "," quando "," che "," come ", " com'è ", " come è "};
	String[] pronomi_relativo = {" il quale "," la quale "," i quali ", " quale "};
	String[] pronomi_indefiniti = {" qualcuno "," qualche "," nessuno "," alcuni "," alcuna "};
	String[] preposizioni = {" di "," a "," da "," in "," con "," su ", " per "," tra "," fra "};
	String[] preposizioni_improprie = {" davanti "," dietro "," dopo "," fuori "," lontano "," lungo "," mediante "," prima "," sopra "," sotto "};
	String[] congiunzioni_semplici = {" e "," né "," ne "," se "," o "," ma "," anche "};
	String[] congiunzioni_composte = {" e pure "," eppure "," né anche "," ne anche "," neanche "," se bene "," sebbene "," a fin che ", " affinché "," affinche "};
	String[] congiunzioni_congiuntive = {" dopo che "," anche se "," in modo da ","dopo che ",};
	String[] punteggiatura = {"*",":","(",")","{","}","[","]",",",";","?","!"};
	
	List<Message> cust = new ArrayList<Message>();

	@GetMapping(value = "/all")
	public Response getResource() {
		Response response = new Response("Done", cust);
		return response;
	}

	@PostMapping(value = "/question")
	public Response postCustomer(@RequestBody Message message, HttpServletRequest request, HttpServletResponse httpResponse) {

		String domanda = " " + message.getDomanda() + " ";
		
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(domanda);
	    while (m.find() && ((boolean)request.getSession().getAttribute("CHATBOTSTATE") == true)) {
	        System.out.println(m.group());
	        //SALVO SU DB LA DOMANDA NON GESTITA
	        String d = (String)request.getSession().getAttribute("CHATBOTQUESTION");
	        
	        Richieste ric = new Richieste();
	        ric.setData(Calendar.getInstance().getTime());
	        ric.setDomanda(d);
	        ric.setEmail(m.group());
	        richiesteService.save(ric);
       
			request.getSession().setAttribute("CHATBOTQUESTION", "");		
			request.getSession().setAttribute("CHATBOTSTATE", false);			
			Message mess = new Message();
			mess.setRisposta("Ho registrato la tua domanda ed invieremo quanto prima la risposta alla tua mail , grazie !");				
			Response response = new Response("Success", mess);
			return response;
	    }
		
		// integrare ricerca		
		for (int r = 0; r < punteggiatura.length; r++) {
			domanda = domanda.replaceAll("\\" + punteggiatura[r] , " ");
		}		
		for (int r = 0; r < articoli.length; r++) {
			domanda = domanda.replaceAll(articoli[r] , " ");
		}
		for (int r = 0; r < pronomi_personali.length; r++) {
			domanda = domanda.replaceAll(pronomi_personali[r] , " ");
		}
		for (int r = 0; r < pronomi_possessivi.length; r++) {
			domanda = domanda.replaceAll(pronomi_possessivi[r] , " ");
		}
		for (int r = 0; r < pronomi_dimostrativi.length; r++) {
			domanda = domanda.replaceAll(pronomi_dimostrativi[r] , " ");
		}
		for (int r = 0; r < pronomi_interrogativi.length; r++) {
			domanda = domanda.replaceAll(pronomi_interrogativi[r] , " ");
		}
		for (int r = 0; r < pronomi_relativo.length; r++) {
			domanda = domanda.replaceAll(pronomi_relativo[r] , " ");
		}
		for (int r = 0; r < pronomi_indefiniti.length; r++) {
			domanda = domanda.replaceAll(pronomi_indefiniti[r] , " ");
		}
		for (int r = 0; r < preposizioni.length; r++) {
			domanda = domanda.replaceAll(preposizioni[r] , " ");
		}
		for (int r = 0; r < preposizioni_improprie.length; r++) {
			domanda = domanda.replaceAll(preposizioni_improprie[r] , " ");
		}
		for (int r = 0; r < congiunzioni_semplici.length; r++) {
			domanda = domanda.replaceAll(congiunzioni_semplici[r] , " ");
		}
		for (int r = 0; r < congiunzioni_composte.length; r++) {
			domanda = domanda.replaceAll(congiunzioni_composte[r] , " ");
		}
		for (int r = 0; r < congiunzioni_congiuntive.length; r++) {
			domanda = domanda.replaceAll(congiunzioni_congiuntive[r] , " ");
		}
		domanda = domanda.trim().replaceAll(" +", " ");
		String[] splited = domanda.split("\\s+");

		String risposta = this.messageService.findRisposta(splited);		
		if(risposta == null || risposta.equals("")){
			risposta = "Mi dispiace ma non ho trovato una risposta corretta per la tua domanda, prova a riformularla o scrivi la tua email e provvederò ad inviarti la risposta alla "
     				+ "tua casella di posta,  grazie !";
			request.getSession().setAttribute("CHATBOTQUESTION", message.getDomanda());		
			request.getSession().setAttribute("CHATBOTSTATE", true);	
		}	     		
 		Message ms = new Message();
		ms.setRisposta(risposta);				
		Response response = new Response("Success", ms);
		return response;
	}
	
	@PostMapping(value = "/insert")
	public Response postInsert(@RequestBody Message mess) {
		Message msg = new Message();
        msg.setDomanda(mess.getDomanda());
        msg.setRisposta(mess.getRisposta());
        messageService.save(msg);
		Response response = new Response("Success", mess);
		return response;
	}
	
	@PostMapping(value = "/update")
	public Response update(@RequestBody Message mess) {
		Message msg = new Message();
        msg.setDomanda(mess.getDomanda());
        msg.setRisposta(mess.getRisposta());
        msg.setId(mess.getId());
        messageService.deleteById(mess.getId());
        messageService.save(msg);
		Response response = new Response("Success", mess);
		return response;
	}
	
	@PostMapping(value = "/getAllDomande")
	public List<Message> getAllDomande() {		
		List<Message> lista = this.messageService.findAll();
		return lista;
	}
	
	@PostMapping(value = "/getAllRichieste")
	public List<Richieste> getAllRichieste() {		
		List<Richieste> lista = this.richiesteService.findAll();
		return lista;
	}
	
	@PostMapping(value = "/deleteRichiesta")
	public Response deleteRichiesta(@RequestBody String id) {
		Long l = Long.parseLong(id.replaceAll("\"", ""));
		this.richiesteService.deleteById(l.intValue());
		return null;
	}
	
	@PostMapping(value = "/deleteDomanda")
	public Response deletedomanda(@RequestBody String id) {
		Long l = Long.parseLong(id.replaceAll("\"", ""));
		this.messageService.deleteById(l.intValue());
		return null;
	}
	
	
}