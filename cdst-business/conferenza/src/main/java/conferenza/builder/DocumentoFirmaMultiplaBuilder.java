package conferenza.builder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFirmaDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.model.Accreditamento;
import conferenza.model.Documento;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.model.Evento;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.DocumentoAttachRepository;
import conferenza.repository.DocumentoFirmaMultiplaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;

@Component
public class DocumentoFirmaMultiplaBuilder extends _BaseBuilder {

	private SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoFirmaMultiplaBuilder.class);

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	private RegistroDocumentoService registroDocumentoService;
	
    @Autowired
    private DocumentAdapterService adapterService;
    
	@Autowired 
	RegistroFirmaAdapterRepository registroFirmaAdapterRepository;
	
	@Autowired
	TipoEventoRepository tipoEventoRepo;
	
	@Autowired
	EventoRepository eventoRepository;

	@Autowired
	DocumentoAttachRepository documentoAttachRepository;

	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	DocumentoFirmaMultiplaRepository documentoFirmaMultiplaepository;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	DocumentoBuilder documentoBuilder;

	private String fromDateToString (Date data) {
		if (data != null) {
			return simpleDate.format(data);
		}
		return null;
	}
	
	public DocumentoFirmaMultipla buildDocumentoFirmaMultipla(DocumentoFirmaMultipla documentoFirmaMulti, Documento documento, Integer idResponsabileFirma, String stato, Integer idUtenteCreatore) {
		
		RegistroDocumento registroDoc = getRegistroDocumento(documento);
		
		boolean inserimento = documentoFirmaMulti == null;
		
		if(inserimento) {
			documentoFirmaMulti = new DocumentoFirmaMultipla();
			documentoFirmaMulti.setDataFirmaIns(new Date());
			documentoFirmaMulti.setIdDocumento(documento.getIdDocumento());
			documentoFirmaMulti.setIdResponsabileFirma(idResponsabileFirma);
			documentoFirmaMulti.setIdUtenteCreatore(idUtenteCreatore);
		}
		else
			documentoFirmaMulti.setDataFirmaVar(new Date());
		documentoFirmaMulti.setStato(stato);
		if(registroDoc!= null)
			documentoFirmaMulti.setIdRegistro(registroDoc.getId());
		
		return documentoFirmaMulti;
	}

	public DocumentoFirmaMultipla buildDocumentoFirmaMultipla(RegistroDocumento registro, Integer idDocumento, Integer idResponsabileFirma, String stato, Integer idUtenteCreatore) {
		DocumentoFirmaMultipla documentoFirmaMulti = new DocumentoFirmaMultipla();
		documentoFirmaMulti = new DocumentoFirmaMultipla();
		documentoFirmaMulti.setDataFirmaIns(new Date());
		documentoFirmaMulti.setIdDocumento(idDocumento);
		documentoFirmaMulti.setIdResponsabileFirma(idResponsabileFirma);
		documentoFirmaMulti.setIdUtenteCreatore(idUtenteCreatore);
		documentoFirmaMulti.setDataFirmaVar(new Date());
		documentoFirmaMulti.setStato(stato);
		documentoFirmaMulti.setIdRegistro(registro.getId());
		
		return documentoFirmaMulti;
	}

	/**
	 * Ad ogni documento sono associate 1 o più righe di registro. 
	 * Il registro che deve essere restituito è l'ultimo inserito in ordine di data. 
	 * @param documento
	 * @return
	 */
	public RegistroDocumento getRegistroDocumento(Documento documento) {
		for (RegistroDocumento registroDocumento : registroDocumentoRepository.findByDocumentoOrderByDataDesc(documento)) {
			return registroDocumento;
		}
		return null;
	}

	public DocumentoFirmaDTO buildDocumentoFirmaDTO(Documento documento, String stato) {
		if (documento != null) {
			DocumentoFirmaDTO documentoDTO = new DocumentoFirmaDTO();
			documentoDTO.setId(documento.getIdDocumento());
			documentoDTO.setNomeFile(documento.getNome());
			documentoDTO.setCategoria(createNotNullLabelValue(documento.getCategoriaDocumento()));
			documentoDTO.setTipoDocumento(createNotNullLabelValue(documento.getTipologiaDocumento()));
			documentoDTO.setNumeroProtocollo(documento.getNumeroProtocollo());
			documentoDTO.setDataProtocollo(fromDateToString(documento.getDataProtocollo()));
			documentoDTO.setNumeroInventario(documento.getNumeroInventario());
			documentoDTO.setCompetenzaTerritoriale(documento.getCompetenzaTerritoriale());
			documentoDTO.setDataRiunione(fromDateToString(documento.getDataRiunione()));
			documentoDTO.setCartella(adapterService.getRaggruppamentoByIdDocument(getRegistroDocumento(documento)));
			// recupero la lista dei partecipanti
			for (Partecipante partecipante : documento.getVisibilitaPartecipanti().stream().distinct()
					.collect(Collectors.toList())) {
				documentoDTO.getVisibilitaPartecipanti().add(createNotNullLabelValue(partecipante));
			}
			for (Accreditamento accreditamento : documento.getFirmatari().stream().distinct()
					.collect(Collectors.toList())) {
				documentoDTO.getFirmatari().add(new LabelValue(Integer.toString(accreditamento.getId()),
						accreditamento.getPersona().getCognome() + " " + accreditamento.getPersona().getNome()));
			}
			RegistroDocumento registro = getRegistroDocumento(documento);
			documentoDTO.setUrl(registroDocumentoService.resolveFileDownloadUri(getRegistroDocumento(documento)));
			LOGGER.info("evento per id_conferenza: " + documento.getConferenza().getIdConferenza() +" idDocumento: "+ documento.getIdDocumento());
			DocumentoFirmaMultipla docFirma = documentoFirmaMultiplaepository.findByIdDocumentoAndStatoAndIdRegistro(documento.getIdDocumento(), stato, registro.getId());
			if(docFirma.getIdUtenteCreatore() != null) {
				Utente utente = utenteService.findById(docFirma.getIdUtenteCreatore());
				
				String nome = utente.getNome();
				String cognome = utente.getCognome();
				String cf = utente.getCodiceFiscale();
				
				PersonaDTO proprietario = new PersonaDTO();
				proprietario .setNome(nome);
				proprietario.setCognome(cognome);
				proprietario.setCodiceFiscale(cf);
				documentoDTO.setProprietario(proprietario);
			}
			documentoDTO.setIdConferenza(documento.getConferenza().getIdConferenza());
			documentoDTO.setRiferimentoIstanza(documento.getConferenza().getRiferimentoIstanza());
			documentoDTO.setDenominazione(documento.getConferenza().getImpresaDenominazione());
			// verifico se esistono documenti allegati, in tal caso li aggiungo
		
			List<Documento> docsAllegati = documentoRepository.findByfkDocParent(documento.getIdDocumento());
			if(docsAllegati != null && !docsAllegati.isEmpty()) {
				List<DocumentoDTO> listaDocAllegati = new ArrayList<>();
				docsAllegati.stream().forEach(item->{
					DocumentoDTO docAllegato = documentoBuilder.buildDocumentoDTO(item);
					listaDocAllegati.add(docAllegato);
				});
				documentoDTO.setListaDocAllegati(listaDocAllegati);
			}
			return documentoDTO;
		}
		return null;
	}

}
