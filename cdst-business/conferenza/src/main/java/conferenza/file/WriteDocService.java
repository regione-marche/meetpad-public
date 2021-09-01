package conferenza.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.api.commentprocessor.ICommentProcessor;
import org.wickedsource.docxstamper.api.coordinates.ParagraphCoordinates;
import org.wickedsource.docxstamper.api.coordinates.RunCoordinates;

import conferenza.DTO.PersonaDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.builder.PartecipanteBuilder;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.EmailPartecipante;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.service.DocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Service
public class WriteDocService {
	
	private static final Logger logger = LoggerFactory.getLogger(WriteDocService.class.getName());
	
	@Autowired
	DocumentoService docService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	PersonaRepository personaRepo;
	
	@Autowired
	DocumentoRepository docRepo;
	
	@Autowired
	TipologiaDocumentoRepository tipoDocRepo;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	PartecipanteBuilder partBuilder;
	
	@Value("${mail.urlDocumentazione}") 
	private String urlDocumentazione;
	
	@Value("${emailPartecipantiTemplateDaEscludere.email}")
	private String email;
	
	
	
	
	
	public void writeDocxFile(String fileName, Conferenza conferenza) throws Exception {
		DocxStamper<DocxContext> stamper = new DocxStamper<>(new DocxStamperConfiguration());
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");            
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
		OutputStream out = response.getOutputStream();
		logger.debug("########## FILENAME VALE: " + fileName);
		InputStream template = this.getClass().getClassLoader().getResourceAsStream(fileName);
		logger.debug("@@@@@@@@@ TEMPLATE VALE: " + template);
		WordprocessingMLPackage entiListTemplate = WordprocessingMLPackage.load(template);
		
		logger.debug("@template writeDocxFile: " + template + " - " + fileName);
		
		DocxContext context = creaDocxContext(conferenza);
		
		// @ENTE_LISTA_NOME@
		List<DocxContextPartecipante> partecipants = context.getPartecipantes();
		if(partecipants != null || partecipants.size() > 0) {
			fillInEntiList(entiListTemplate.getMainDocumentPart(), partecipants, "@ENTE_LISTA_NOME@");
		}
		
		stamper.stamp(entiListTemplate, context, out);
	}
	
	private void fillInEntiList(Object template
			, List<?> list
			, String placeHolder) throws Docx4JException, JAXBException {
		
		List<Object> tables = getAllElementFromObject(template, Tbl.class);

		Tbl tempTable = getTemplateTable(tables, placeHolder);
		if (tempTable == null) return;
		
		List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
		if (rows.size() == 0) return;
		
		Tr templateRow = (Tr) rows.get(0);
		
		if(list.size() == 0) {
			Map<String, String> map = new HashMap<String, String>() {{
			    put("@LISTA_UTENTI_ENTE_NOME@", null);
			    put("@LISTA_UTENTI_ENTE_PEC@", null);
			}};
			
			addRowToTable(tempTable, templateRow, map);
			tempTable.getContent().remove(templateRow);
			return;
		}
		
		String[] listMail2Esclude = email.split("[;]");
		
	
	
		if(list.get(0) instanceof DocxContextPartecipante) {
			for(DocxContextPartecipante partecipant : (List<DocxContextPartecipante>)list) {
				Map<String, String> map = new HashMap<String, String>() {{
				    put("@ENTE_LISTA_NOME@", partecipant.getDescEnte());
				    put("@ENTE_LISTA_PEC@", partecipant.getPec());
				}};
				
				/*
				Ing./Dott. @LISTA_UTENTI_ENTE_NOME@
				PEC: @LISTA_UTENTI_ENTE_PEC@
				*/
				boolean isPresent = false;
				for(int i=0; i<listMail2Esclude.length; i++) {
					if(listMail2Esclude[i].equalsIgnoreCase(partecipant.getPec())) {
						isPresent = true;
						logger.debug("Ho trovato la mail di un partecipante presente nella parte statica: " + partecipant.getPec());
					}
				}
				
				if(!isPresent)
					fillInEntiList(addRowToTable(tempTable, templateRow, map), partecipant.getListaUtente(), "@LISTA_UTENTI_ENTE_NOME@");
			}
		}
		else if(list.get(0) instanceof PersonaRuoloConferenzaDTO) {
			for(PersonaRuoloConferenzaDTO persona : (List<PersonaRuoloConferenzaDTO>)list) {
				Map<String, String> map = new HashMap<String, String>() {{
				    put("@LISTA_UTENTI_ENTE_NOME@", persona.getNome() + " " + persona.getCognome());
				    put("@LISTA_UTENTI_ENTE_PEC@", persona.getEmail() == null ? "" : persona.getEmail());
				}};
				
				addRowToTable(tempTable, templateRow, map);
			}
		}
		
		tempTable.getContent().remove(templateRow);
	}
	
	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
		
		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}

		}
		return result;
	}
	
	private Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
		if(tables == null) return null;

		for(Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = getAllElementFromObject(tbl, Text.class);
			String allTableTexts = "";
			for(Object text : textElements)
				allTableTexts += ((Text)text).getValue();
			
			if(allTableTexts.contains(templateKey))
				return (Tbl)tbl;
		}
		return null;
	}
	
	private static Object addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
		List<?> textElements = getAllElementFromObject(workingRow, Text.class);
		for (Object object : textElements) {
			Text text = (Text) object;
			String str = text.getValue();
			
			for(Iterator<String> i = replacements.keySet().iterator(); i.hasNext(); ) {
				String tag = i.next();
				String tagValue = replacements.get(tag);

				if(str.indexOf(tag) > -1) {
					if (tagValue != null)
						text.setValue(str.replaceAll(tag, tagValue));
					else
						text.setValue("");
				}
			}
		}

		reviewtable.getContent().add(workingRow);
		
		return workingRow;
	}
	
	public DocxContext creaDocxContext (Conferenza conferenza) {
		
		
		DocxContext context = new DocxContext();
		String ammProc = conferenza.getAmministrazioneProcedente().getDescrizioneEnte();
		context.setAmmProc(
				ammProc != null ? conferenza.getAmministrazioneProcedente().getDescrizioneEnte() : "_________");
		String primaSessioneSimultanea = docService.formatDate(conferenza.getPrimaSessioneSimultanea());
		context.setData(primaSessioneSimultanea != null ? docService.formatDate(conferenza.getPrimaSessioneSimultanea())
				: "_________");
		String entePartecipante = utenteService.getAccreditamento(conferenza, true).getPartecipante().getEnte().getDescrizioneEnte();
		context.setEntePartecipante(entePartecipante != null ? entePartecipante : "_________");
		Integer idConferenza = conferenza.getIdConferenza();
		context.setIdConferenza(idConferenza != null ? idConferenza : Integer.parseInt("_________"));
		String luogo = conferenza.getIndirizzoSessioneSimultanea();
		context.setLuogo(luogo != null ? luogo : "_________");
		String oggetto = conferenza.getOggettoDeterminazione();
		context.setOggetto(oggetto != null ? oggetto : "_________");
		Time orarioConf = conferenza.getOrarioConferenza();
		context.setOra(orarioConf != null ? orarioConf.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString() : "_________");
		String riferimentoIstanza = conferenza.getRiferimentoIstanza();
		context.setRiferimentoIstanza(riferimentoIstanza != null ? riferimentoIstanza : "_________");
		String termineEspressionePareri = docService.formatDate(conferenza.getTermineEspressionePareri());
		context.setTermineEspressionePareri(termineEspressionePareri != null ? termineEspressionePareri : "_________");
		String termineRichiestaIntegrazioni = docService.formatDate(conferenza.getTermineRichiestaIntegrazioniConferenza());
		context.setTermineRichiestaIntegrazioni(termineRichiestaIntegrazioni != null ? termineRichiestaIntegrazioni : "_________");
		context.setTipologiaConferenza(messageSource.getMessage(conferenza.getTipologiaConferenzaSpecializzazione().getDescrizione(), null,
				"_________", request.getLocale()));
		context.setUrlDocumentazione(urlDocumentazione);
		String cognomeRichiedente = conferenza.getCognomeRichiedente();
		context.setCognomeRichiedente(cognomeRichiedente != null ? cognomeRichiedente : "_________");
		String nomeRichiedente = conferenza.getNomeRichiedente();
		context.setNomeRichiedente(nomeRichiedente != null ? nomeRichiedente : "_________");
		Persona responsabile = personaRepo
				.findByCodiceFiscaleIgnoreCase(conferenza.getCodiceFiscaleResponsabileConferenza()).orElse(null);
		if (responsabile != null) {
			context.setCognomeResponsabile(responsabile.getCognome());
			context.setNomeResponsabile(responsabile.getNome());
		} else {
			context.setCognomeResponsabile("_________");
			context.setNomeResponsabile("_________");
		}
		String denominazione = conferenza.getImpresaDenominazione();
		context.setDenominazione(denominazione != null ? denominazione : "_________");
		context.setEntiInvitati(elencoEntiPartecipanti(conferenza));
		String dataAvvio = docService.formatDate(conferenza.getDataAvvio());
		context.setDataAvvio(dataAvvio != null ? dataAvvio : "_________");
		Documento doc = docRepo.findByConferenzaAndTipologiaDocumento(conferenza,
				tipoDocRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE).get()).orElse(null);
		if (doc != null && doc.getNumeroProtocollo() != null) {
			context.setNumeroProtocollo(doc.getNumeroProtocollo());
			context.setDataProtocollo(docService.formatDate(doc.getDataProtocollo()));
		} else {
			context.setNumeroProtocollo("_________");
			context.setDataProtocollo("_________");
		}
		
		String codiceFiscaleRichiedente = conferenza.getCodiceFiscaleRichiedente();
		context.setCodiceFiscaleRichiedente(codiceFiscaleRichiedente != null && !codiceFiscaleRichiedente.isEmpty() ? codiceFiscaleRichiedente : "_________");
		
		String localizzazioneIndirizzo = conferenza.getLocalizzazioneIndirizzo();
		context.setLocalizzazioneIndirizzo(localizzazioneIndirizzo != null && !localizzazioneIndirizzo.isEmpty() ? localizzazioneIndirizzo : "_________");
		
		String localizzazioneComune = null;
		try { localizzazioneComune = conferenza.getLocalizzazioneComune().getProvincia().getDescrizione(); } catch (Exception ignore) { }
		context.setLocalizzazioneComune(localizzazioneComune != null && !localizzazioneComune.isEmpty() ? localizzazioneComune : "_________");

		String localizzazioneProvincia = null;
		try { localizzazioneProvincia = conferenza.getLocalizzazioneComune().getProvincia().getDescrizione(); } catch (Exception ignore) { }
		context.setLocalizzazioneProvincia(localizzazioneProvincia != null && !localizzazioneProvincia.isEmpty() ? localizzazioneProvincia : "_________");

		String dateYear = null;
		String dateMonth = null;
		String dateDay = null;
		try {
			dateDay = primaSessioneSimultanea.substring(0, 2);
			dateMonth = primaSessioneSimultanea.substring(3, 5);
			dateYear = primaSessioneSimultanea.substring(6);
		} catch (Exception skip) {}
		context.setDataAnno(dateYear != null && !dateYear.isEmpty() ? dateYear : "____");
		context.setDataMese(dateMonth != null && !dateMonth.isEmpty() ? dateMonth : "____");
		context.setDataGiorno(dateDay != null && !dateDay.isEmpty() ? dateDay : "____");
		
		String pec = conferenza.getPec();
		context.setPecRichiedente(pec != null && !pec.isEmpty() ? pec : "_________");

		// the DOCX template sublist issue has been resolved
		List<Partecipante> listaEnti = conferenza.getPartecipantes();
		List<DocxContextPartecipante> listaPatecipanti = listaEnti.stream()
				.filter(p -> !p.getRuoloPartecipante().getCodice().equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE))
							&& !p.getRuoloPartecipante().getCodice().equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)) )
				.map(e -> buildPartecipanteDTO(e)).collect(Collectors.toList());
		
		// ----EVENTUALMENTE PROVARE QUESTA SOLUZIONE PER LA LISTA PARTECIPANTI DA MOSTRARE
		List<String> listaPartecipanteDaMostrare = new ArrayList<String>();
		for(int i=0; i<listaPatecipanti.size(); i++) {

			String partecipante = listaPatecipanti.get(i).getDescEnte() + "\n" + "PEC: " + listaPatecipanti.get(i).getPec() + "\n";
			listaPartecipanteDaMostrare.add(partecipante);
		}
		context.setPartecipantiInizioTemplate(listaPartecipanteDaMostrare);
		// ----
		
		context.setPartecipantes(listaPatecipanti);
		
		String foglioMappale = conferenza.getFoglioMappale();
		context.setFoglioMappale(foglioMappale != null && !foglioMappale.isEmpty() ? foglioMappale : "_________");
		
		
		String siglaProvincia = conferenza.getImpresaProvincia().getSigla();
		context.setSiglaProvincia(siglaProvincia == null || siglaProvincia.isEmpty() ? "_________" : siglaProvincia);
		
		return context;
	}
	
	public DocxContextPartecipante buildPartecipanteDTO(Partecipante partecipante) {
		DocxContextPartecipante partecipanteContext = new DocxContextPartecipante();
		partecipanteContext.setDescEnte(partecipante.getDescEnteCompetente());
		partecipanteContext.setPec(partecipante.getPecEnteCompetente());
		partecipanteContext.setCf(partecipante.getCfEnteCompetente());
		
		//partecipanteDTO.setEnte(createNotNullLabelValue(partecipante.getEnte()));
		partecipanteContext.setRuolo(partecipante.getRuoloPartecipante().getDescrizione());
		partecipanteContext.setEsprimeParere(partecipante.getEsprimeDeterminazione());
		partecipanteContext.setCompetenza(partecipante.getCompetenza());
		if (partecipante.getAltreEmail().size() != 0) {
			for (EmailPartecipante email : partecipante.getAltreEmail()) {
				partecipanteContext.getAltreEmail().add(email.getEmail());
			}
		}
		
		List<Accreditamento> listaAccreditati = partecipante.getAccreditati();
		for (Accreditamento accreditamento : listaAccreditati) {
			if (accreditamento.getDataFine() == null && accreditamento.getFlagInvitato())
				partecipanteContext.getListaUtente()
						.add(this.partBuilder.buildAccreditamentoToPersonaRuoloDTO(accreditamento));
		}
		
		return partecipanteContext;
	}
	
	public List<String> elencoEntiPartecipanti(Conferenza conferenza) {
		return conferenza.getPartecipantes().stream()
				.filter(p -> !p.getRuoloPartecipante().getCodice()
						.equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)))
				.map(p -> p.getDescEnteCompetente()).collect(Collectors.toList());
	}

	
	public List<String> elencoPecEntiPartecipanti(Conferenza conferenza) {
		return conferenza.getPartecipantes().stream()
				.filter(p -> !p.getRuoloPartecipante().getCodice()
						.equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)))
				.map(p -> p.getPecEnteCompetente()).collect(Collectors.toList());
	}
	
	public void aggiornaModelloFile(String fileNameInput, String pathOutput, Conferenza conferenza) {
		DocxStamper<DocxContext> stamper = new DocxStamper<>(new DocxStamperConfiguration());
		InputStream template = getClass().getClassLoader().getResourceAsStream(fileNameInput);
		OutputStream out = null;
		FileSystemResource resource = new FileSystemResource(pathOutput);
		try {
			out = resource.getOutputStream();

			
			WordprocessingMLPackage entiListTemplate = WordprocessingMLPackage.load(template);
			
			logger.debug("template: " + template);
			
			DocxContext context = creaDocxContext(conferenza);
			
			// @ENTE_LISTA_NOME@
			List<DocxContextPartecipante> partecipants = context.getPartecipantes();
			if(partecipants != null || partecipants.size() > 0) {
				fillInEntiList(entiListTemplate.getMainDocumentPart(), partecipants, "@ENTE_LISTA_NOME@");
			}
			
			stamper.stamp(entiListTemplate, context, out);
		} catch (Exception e) {
			logger.debug("@template aggiornaModelloFile exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
		}
		
	}
	
}
