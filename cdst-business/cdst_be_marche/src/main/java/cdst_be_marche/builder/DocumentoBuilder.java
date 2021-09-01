package cdst_be_marche.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cdst_be_marche.DTO.AccreditamentoFileDTO;
import cdst_be_marche.DTO.DocumentazioneDTO;
import cdst_be_marche.DTO.DocumentoDTO;
import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.exception.ParseDateException;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RegistroDocumento;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.CategoriaDocumentoRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.RegistroDocumentoFonteRepository;
import cdst_be_marche.repository.RegistroDocumentoRepository;
import cdst_be_marche.repository.RegistroDocumentoTipoRepository;
import cdst_be_marche.repository.TipologiaDocumentoRepository;
import cdst_be_marche.service.RegistroDocumentoService;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.util.JsonUtil;

@Component
public class DocumentoBuilder extends _BaseBuilder {

	private SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	CategoriaDocumentoRepository categoriaDocumentoRepository;

	@Autowired
	TipologiaDocumentoRepository tipologiaDocumentoRepository;
	
	@Autowired
	RegistroDocumentoFonteRepository registroDocumentoFonteRepository;
	
	@Autowired
	RegistroDocumentoTipoRepository registroDocumentoTipoRepository;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	ConferenzaRepository conferenzaRepository;

	@Autowired
	private RegistroDocumentoService registroDocumentoService;

	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	AccreditamentoRepository accreditamentoRepo;

	public DocumentoDTO buildDocumentoDTO(DocumentoFileDTO documentoFileDTO) {
		DocumentoDTO documentoDTO = new DocumentoDTO();
		documentoDTO.setNomeFile(documentoFileDTO.getNomeFile());
		if (documentoFileDTO.getCategoria() != null) {
			documentoDTO.setCategoria(createNotNullLabelValue(
					categoriaDocumentoRepository.findById(documentoFileDTO.getCategoria()).orElse(null)));
		}
		documentoDTO.setTipoDocumento(createNotNullLabelValue(
				tipologiaDocumentoRepository.findById(documentoFileDTO.getTipoDocumento()).orElse(null)));
		if (documentoFileDTO.getVisibilitaPartecipanti() != null) {
			documentoDTO
			.setVisibilitaPartecipanti(JsonUtil.jsonToListLabelValue(documentoFileDTO.getVisibilitaPartecipanti()));
		}	
		documentoDTO.setNumeroProtocollo(documentoFileDTO.getNumeroProtocollo());
		documentoDTO.setDataProtocollo(documentoFileDTO.getDataProtocollo());
		documentoDTO.setNumeroInventario(documentoFileDTO.getNumeroInventario());
		return documentoDTO;
	}

	/**
	 * build di un Documento sia per la fase di creazione che per la fase di
	 * modifica.
	 * 
	 * @param documentoDTO
	 * @param conferenza     passare null per mantenere il valore attuale inalterato
	 *                       in fase di modifica
	 * @param accreditamento passare null per mantenere il valore attuale inalterato
	 *                       in fase di modifica
	 * @return
	 */
	public Documento buildDocumento(DocumentoDTO documentoDTO, Conferenza conferenza, Accreditamento accreditamento) {
		Documento documento;
		if (documentoDTO.getId() == null) {
			documento = new Documento();
		} else {
			documento = this.documentoRepository.findById(documentoDTO.getId()).get();
		}
		documento.setNome(documentoDTO.getNomeFile());
		if (documentoDTO.getCategoria() != null)
			documento.setCategoriaDocumento(
					categoriaDocumentoRepository.findById(documentoDTO.getCategoria().getKey()).orElse(null));
		if (documentoDTO.getTipoDocumento() != null)
			documento.setTipologiaDocumento(
					tipologiaDocumentoRepository.findById(documentoDTO.getTipoDocumento().getKey()).orElse(null));
		documento.setVisibilitaRistretta(!documentoDTO.getVisibilitaPartecipanti().isEmpty());
		if (documentoDTO.getNumeroProtocollo() != null) {
			documento.setNumeroProtocollo(documentoDTO.getNumeroProtocollo());
		}
		if (documentoDTO.getDataProtocollo() != null) {
			documento.setDataProtocollo(fromStringToDate(documentoDTO.getDataProtocollo()));
		}
		if (documentoDTO.getNumeroInventario() != null) {
			documento.setNumeroInventario(documentoDTO.getNumeroInventario());
		}
		if (accreditamento != null) {
			documento.setOwner(accreditamento);
		}
		if (conferenza != null) {
			documento.setConferenza(conferenza);
		}
		return documento;
	}

	public RegistroDocumento buildRegistroDocumento(String riferimentoEsterno, Documento documentoSaved, String codiceFonte, String codiceTipo) {
		RegistroDocumento registroDocumento = new RegistroDocumento();
		registroDocumento.setData(new Date());
		registroDocumento.setRiferimentoEsterno(riferimentoEsterno);
		registroDocumento.setFonte(registroDocumentoFonteRepository.findById(codiceFonte).orElse(null));
		registroDocumento.setTipo(registroDocumentoTipoRepository.findById(codiceTipo).orElse(null));
		registroDocumento.setDocumento(documentoSaved);
		return registroDocumento;
	}

	public DocumentoDTO buildDocumentoDTO(Documento documento) {
		if (documento != null) {
		DocumentoDTO documentoDTO = new DocumentoDTO();
		documentoDTO.setId(documento.getIdDocumento());
		documentoDTO.setNomeFile(documento.getNome());
		documentoDTO.setCategoria(createNotNullLabelValue(documento.getCategoriaDocumento()));
		documentoDTO.setTipoDocumento(createNotNullLabelValue(documento.getTipologiaDocumento()));
		documentoDTO.setNumeroProtocollo(documento.getNumeroProtocollo());
		documentoDTO.setDataProtocollo(fromDateToString(documento.getDataProtocollo()));
		documentoDTO.setNumeroInventario(documento.getNumeroInventario());
		for (Partecipante partecipante : documento.getVisibilitaPartecipanti()) {
			documentoDTO.getVisibilitaPartecipanti().add(createNotNullLabelValue(partecipante));
		}
		documentoDTO.setUrl(registroDocumentoService.resolveFileDownloadUri(getRegistroDocumento(documento)));
		return documentoDTO;
		}
		return null;
	}

	public RegistroDocumento getRegistroDocumento(Documento documento) {
		// TODO attualmente viene preso il primo e l'unico tra i registri documento
		// associati al documento. Quando saranno presenti più registri per ogni
		// documento andrà implementata la logica di recuper del registro in base al
		// documentale scelto
		registroDocumentoRepository.findByDocumento(documento);
		for (RegistroDocumento registroDocumento : registroDocumentoRepository.findByDocumento(documento)) {
			return registroDocumento;
		}
		return null;
	}

	public Documento buildDocumentoAccreditamento(AccreditamentoFileDTO fileDTO, Partecipante partecipante,
			Accreditamento accreditamento) {
		Documento documento = new Documento();
		documento.setNome("Accreditamento" + fileDTO.getNome() + fileDTO.getCognome());
		documento.setConferenza(
				this.conferenzaRepository.findByIdConferenza(partecipante.getConferenza().getIdConferenza()));
		documento.setTipologiaDocumento(tipologiaDocumentoRepository
				.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO).orElse(null));
		documento.getVisibilitaPartecipanti().add(partecipante);
		documento.setOwner(this.accreditamentoRepo.findById(accreditamento.getId()).orElse(null));
		return documento;
	}

	private Date fromStringToDate(String str) {
		if (str != null && !str.isEmpty()) {
			try {
				return simpleDate.parse(str);
			} catch (ParseException e) {
				throw new ParseDateException(e.getMessage());

			}
		} else
			return null;

	}
	
	private String fromDateToString (Date data) {
		if (data != null) {
			return simpleDate.format(data);
		}
		return null;
	}

	public DocumentazioneDTO buildDocumentazioneDTO(List<Documento> listaDocumentiOwner) {
		DocumentazioneDTO documentazioneDTO = new DocumentazioneDTO();
		listaDocumentiOwner.stream()
				.filter(d -> d.getTipologiaDocumento().getCodice()
						.equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA))
				.forEach(d -> documentazioneDTO.getDocumentiAggiuntivi().add(buildDocumentoDTO(d)));
		listaDocumentiOwner.stream()
				.filter(d -> d.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_INTERAZIONI))
				.forEach(d -> documentazioneDTO.getDocumentiInterazione().add(buildDocumentoDTO(d)));
		listaDocumentiOwner.stream().filter(
				d -> d.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE))
				.forEach(d -> documentazioneDTO.getDocumentiIndizione().add(buildDocumentoDTO(d)));
		listaDocumentiOwner.stream()
				.filter(d -> d.getTipologiaDocumento().getCodice()
						.equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA))
				.forEach(d -> documentazioneDTO.getDocumentiPreIstruttoria().add(buildDocumentoDTO(d)));
		listaDocumentiOwner.stream()
				.filter(d -> d.getTipologiaDocumento().getCodice()
						.equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_VERBALE_RIUNIONE))
				.forEach(d -> documentazioneDTO.getDocumentiInterazione().add(buildDocumentoDTO(d)));
		listaDocumentiOwner.stream()
				.filter(d -> d.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DETERMINA))
				.forEach(d -> documentazioneDTO.getDocumentiInterazione().add(buildDocumentoDTO(d)));
		return documentazioneDTO;
	}

}
