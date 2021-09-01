package cdst_be_marche.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Persona;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.TipologiaDocumentoRepository;
import cdst_be_marche.service.DocumentoService;
import cdst_be_marche.service.UtenteService;
import cdst_be_marche.util.DbConst;

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
	
	@Value("${mail.urlDocumentazione}") 
	private String urlDocumentazione;
	
	public void writeDocxFile(String fileName, Conferenza conferenza) throws IOException {
		DocxStamper<DocxContext> stamper = new DocxStamper<>(new DocxStamperConfiguration());
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");            
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
		logger.info("filename: " + fileName);
		InputStream template = this.getClass().getClassLoader().getResourceAsStream(fileName);
		logger.info("template: " + template);
		OutputStream out = response.getOutputStream();
		stamper.stamp(template, creaDocxContext(conferenza), out);
	}
	
	public DocxContext creaDocxContext (Conferenza conferenza) {
		DocxContext context = new DocxContext();
		String ammProc = conferenza.getAmministrazioneProcedente().getDescrizioneEnte();
		context.setAmmProc(
				ammProc != null ? conferenza.getAmministrazioneProcedente().getDescrizioneEnte() : "_________");
		String primaSessioneSimultanea = docService.formatDate(conferenza.getPrimaSessioneSimultanea());
		context.setData(primaSessioneSimultanea != null ? docService.formatDate(conferenza.getPrimaSessioneSimultanea())
				: "_________");
		String entePartecipante = utenteService.getAccreditamento(conferenza).getPartecipante().getEnte().getDescrizioneEnte();
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
		context.setTipologiaConferenza(messageSource.getMessage(conferenza.getTipologiaConferenza().getDescrizione(), null,
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
		if (doc != null) {
			context.setNumeroProtocollo(doc.getNumeroProtocollo());
			context.setDataProtocollo(docService.formatDate(doc.getDataProtocollo()));
		} else {
			context.setNumeroProtocollo("_________");
			context.setDataProtocollo("_________");
		}
		return context;
	}

	public List<String> elencoEntiPartecipanti(Conferenza conferenza) {
		return conferenza.getPartecipantes().stream()
				.filter(p -> !p.getRuoloPartecipante().getCodice()
						.equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)))
				.map(p -> p.getDescEnteCompetente()).collect(Collectors.toList());
	}
}
