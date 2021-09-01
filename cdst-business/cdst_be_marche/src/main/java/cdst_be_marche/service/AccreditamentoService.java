package cdst_be_marche.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.AccreditamentoDTO;
import cdst_be_marche.DTO.AccreditamentoFileDTO;
import cdst_be_marche.DTO.AccreditamentoInfoDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaAccreditamentoDTO;
import cdst_be_marche.builder.DocumentoBuilder;
import cdst_be_marche.builder.PartecipanteBuilder;
import cdst_be_marche.exception.NotAuthorizedUser;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.exception.UnprocessableEntityException;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.Persona;
import cdst_be_marche.model.TockenConference;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.RegistroDocumentoRepository;
import cdst_be_marche.repository.RuoloPersonaRepository;
import cdst_be_marche.repository.TipologiaDocumentoRepository;

@Transactional
@Service
public class AccreditamentoService extends _BaseService {

	@Autowired
	UtenteService utenteService;

	@Autowired
	AccreditamentoRepository accreditamentoRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	ConferenzaRepository conferenzaRepo;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	RuoloPersonaRepository ruoloPersonaRepo;

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	ParticipantService partecipanteService;

	@Autowired
	TockenConferenceService tockenService;

	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	TipologiaDocumentoRepository tipoDocumentoRepo;

	public AccreditamentoInfoDTO findAccreditamento(String token1, String token2) {
		AccreditamentoDTO accreditamentoDTO = null;
		AccreditamentoInfoDTO accreditamentoInfoDTO = new AccreditamentoInfoDTO();
		String cf = utenteService.getCodiceFiscaleUtente();
		if (token1 != null && token2 != null) {
			TockenConference tocken = tockenService.checkTockenConference(token1, token2);
			if (tocken != null) {
				Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(tocken.getIdConferenza());
				if (conferenza == null) {
					throw new NotFoundEx("the conference with that id doesn't exist");
				}
				Partecipante partecipante = this.partecipanteRepo.findById(tocken.getIdPartecipante()).orElse(null);
				if (partecipante == null) {
					throw new NotFoundEx("the participant with that id doesn't exist");
				}
				if (!conferenza.getIdConferenza().equals(partecipante.getConferenza().getIdConferenza())) {
					throw new NotFoundEx("the participant is not in that conference");
				}
				Persona persona = this.personaRepo.findByCodiceFiscaleIgnoreCase(cf).orElse(null);
				if (persona != null) {
					List<Accreditamento> accreditamenti = this.accreditamentoRepo
							.findByPartecipanteAndPersona(partecipante, persona);
					Accreditamento accreditamento = null;
					if (!accreditamenti.isEmpty()) {
						accreditamento = accreditamenti.get(0);
					}
					accreditamentoDTO = this.partecipanteBuilder.buildAccrToAccrDTO(accreditamento);
				}

				accreditamentoInfoDTO.setAccreditamento(accreditamentoDTO);
				accreditamentoInfoDTO.setFlagAutoAccreditamento(partecipanteBuilder.tipologiaNonRichiedeAccreditamento(conferenza));
				accreditamentoInfoDTO.setIdConferenza(conferenza.getIdConferenza());
			} else {
				throw new NotAuthorizedUser("Token non validi");
			}
		}

		return accreditamentoInfoDTO;
	}

	public IdentifiableDTO createAccreditamento(AccreditamentoFileDTO fileDTO) {
		TockenConference tocken = tockenService.checkTockenConference(fileDTO.getToken1(), fileDTO.getToken2());
		if (tocken == null) {
			throw new NotAuthorizedUser("Token non validi");
		}
		Partecipante partecipante = this.partecipanteRepo.findById(tocken.getIdPartecipante()).get();
		if (!this.utenteService.getCodiceFiscaleUtente().equals(fileDTO.getCodiceFiscale())) {
			throw new UnprocessableEntityException("Il codice fiscale inserito non corrisponde a quello dell'utente connesso");
		}
		Accreditamento saved = this.accreditamentoRepo
				.save(this.partecipanteBuilder.buildAccreditamentoFromFile(fileDTO, partecipante, null));
		if (fileDTO.getFile() != null) {
			creaDocumentoAccreditamento(fileDTO, partecipante, saved);
		}
		this.partecipanteService.creaUtentiAccreditati(saved.getPartecipante().getConferenza());
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	private void eliminaDocumentoAccreditamento(Accreditamento saved) {
		Documento documento = partecipanteBuilder.documentoAccreditamento(saved);
		if (documento != null) {
			documento.setDataFine(new Date());
			documentoRepo.save(documento);
		}
	}

	private void creaDocumentoAccreditamento(AccreditamentoFileDTO fileDTO, Partecipante partecipante,
			Accreditamento accreditamento) {
		if (partecipanteBuilder.documentoAccreditamento(accreditamento) == null) {
			Documento documento = this.documentoBuilder.buildDocumentoAccreditamento(fileDTO, partecipante, accreditamento);
			Documento documentoSaved = this.documentoRepo.save(documento);
			documentoService.creaRegistroDocumento(fileDTO.getFile(), documentoSaved);
		}
	}

	public IdentifiableDTO updateAccreditamento(AccreditamentoFileDTO fileDTO, Integer id) {
		Accreditamento accreditamento = this.accreditamentoRepo.findById(id).orElse(null);
		if (accreditamento == null) {
			throw new NotFoundEx("the accreditation doesn't exist");
		}
		Accreditamento saved = this.accreditamentoRepo
				.save(this.partecipanteBuilder.buildAccreditamentoFromFile(fileDTO, accreditamento.getPartecipante(),
						accreditamento));
		if (fileDTO.getFile() != null) {
			eliminaDocumentoAccreditamento(saved);
			creaDocumentoAccreditamento(fileDTO, saved.getPartecipante(), saved);
		}
		this.partecipanteService.creaUtentiAccreditati(saved.getPartecipante().getConferenza());
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	public String deleteAccreditamento(Integer id) {
		Accreditamento accreditamento = this.accreditamentoRepo.findById(id).get();
		accreditamento.setDataFine(new Date());
		this.accreditamentoRepo.save(accreditamento);
		return "ok";

	}

	/**
	 * Devono essere restituiti tutti gli accreditamenti in qualsiasi stato
	 * 
	 * @param idConferenza
	 * @return
	 */
	public ListaAccreditamentoDTO findAccreditamentiConferenza(Integer idConferenza) {
		ListaAccreditamentoDTO lista = new ListaAccreditamentoDTO();
		lista.setTotalNumber(new Long(lista.getList().size()));
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(idConferenza);
		if (conferenza == null) {
			throw new NotFoundEx("the conference doesn't exist");
		}

		for (Partecipante partecipante : conferenza.getPartecipantes()) {
			for (Accreditamento accreditamento : partecipante.getAccreditati()) {
				lista.getList().add(this.partecipanteBuilder.buildAccrToAccrDTO(accreditamento));
			}
			lista.setTotalNumber(new Long(lista.getList().size()));
		}
		return lista;
	}

	public IdentifiableDTO patchAccreditamento(Integer id, Integer idAcc, AccreditamentoDTO accreditamentoDTO) {
		String cf = this.utenteService.getCodiceFiscaleUtente();
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(id);
		if (!conferenza.getCodiceFiscaleResponsabileConferenza().equals(cf)) {
			throw new NotAuthorizedUser("Utente non dispone di autorizzazioni per l'applicazione");
		}
		Accreditamento accreditamento = this.accreditamentoRepo.findById(idAcc).orElse(null);
		if (accreditamento == null) {
			throw new NotFoundEx("the accreditation doesn't exist");
		}
		if (accreditamentoDTO.getFlagAccreditato().equals(Boolean.TRUE)) {
			accreditamento.setFlagAccreditato(Boolean.TRUE);
		}
		if (accreditamentoDTO.getFlagRifiuto().equals(Boolean.TRUE)) {
			accreditamento.setFlagRifiuto(Boolean.TRUE);
			accreditamento.setDescrizioneRifiuto(accreditamentoDTO.getDescrizioneRifiuto());
		}
		Accreditamento saved = this.accreditamentoRepo.save(accreditamento);
		this.partecipanteService.creaUtentiAccreditati(conferenza);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

}
