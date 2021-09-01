package conferenza.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.AccreditamentoAnteprimaDTO;
import conferenza.DTO.AccreditamentoDTO;
import conferenza.DTO.AccreditamentoFileDTO;
import conferenza.DTO.AccreditamentoInfoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaAccreditamentoDTO;
import conferenza.DTO.ListaPartecipanteConAccreditatiDTO;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.PartecipanteConAccreditatiDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.builder.DocumentoBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.exception.ExpiredException;
import conferenza.exception.NotAuthorizedUser;
import conferenza.exception.NotFoundEx;
import conferenza.exception.UnprocessableEntityException;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.ModificaData;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.TipoData;
import conferenza.model.TockenConference;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.ModificaDataRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.TipoDataRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.util.DbConst;

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
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	ConferenzaService confService;

	@Autowired
	TipoDataRepository tipoDataRepo;
	
	@Autowired
	ModificaDataRepository modificaDaaRepo;
	
	public AccreditamentoInfoDTO findAccreditamento(String token1, String token2) {
		AccreditamentoDTO accreditamentoDTO = new AccreditamentoDTO();
		AccreditamentoInfoDTO accreditamentoInfoDTO = new AccreditamentoInfoDTO();
		String cf = utenteService.getCodiceFiscaleUtente();
		// codice fiscale sempre impostato
		accreditamentoDTO.setCodiceFiscale(cf);
		if (token1 != null && token2 != null) {
			TockenConference tocken = tockenService.checkTockenConference(token1, token2);
			if (tocken != null) {
				Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(tocken.getIdConferenza());
				if (conferenza == null) {
					throw new NotFoundEx("the conference with that id doesn't exist");
				}

				// xmf added modifica data event
				Date simultaneousDate =conferenza.getPrimaSessioneSimultanea();
				
				//TipoData tipoData = tipoDataRepo.findById(DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA).get();
				//ModificaData modificaData = modificaDaaRepo.findOneByConferenzaAndTipoData(conferenza, tipoData);
				//if(modificaData != null)
				//	simultaneousDate = modificaData.getData();
				//else
				//simultaneousDate = conferenza.getPrimaSessioneSimultanea();
					
				if (simultaneousDate.before(new Date())) {					
					if(!DateUtils.isSameDay(conferenza.getPrimaSessioneSimultanea(), new Date()))
						throw new ExpiredException("link expired");
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
					// se esiste la persona imposto i dati della persona
					accreditamentoDTO.setCognome(persona.getCognome());
					accreditamentoDTO.setNome(persona.getNome());
					accreditamentoDTO.setEmail(persona.getEmail());
					List<Accreditamento> accreditamenti = this.accreditamentoRepo
							.findByPartecipanteAndPersona(partecipante, persona);
					Accreditamento accreditamento = null;
					if (!accreditamenti.isEmpty()) {
						accreditamento = accreditamenti.get(0);
						accreditamentoDTO = this.partecipanteBuilder.buildAccrToAccrDTO(accreditamento);
					}
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

	public AccreditamentoAnteprimaDTO createAccreditamento(AccreditamentoFileDTO fileDTO) {
		TockenConference tocken = tockenService.checkTockenConference(fileDTO.getToken1(), fileDTO.getToken2());
		if (tocken == null) {
			throw new NotAuthorizedUser("Token non validi");
		}
		AccreditamentoAnteprimaDTO accreditamentoAnteprimaDTO = new AccreditamentoAnteprimaDTO();
		Partecipante partecipante = this.partecipanteRepo.findById(tocken.getIdPartecipante()).get();
		if (!this.utenteService.getCodiceFiscaleUtente().equals(fileDTO.getCodiceFiscale())) {
			throw new UnprocessableEntityException("Il codice fiscale inserito non corrisponde a quello dell'utente connesso");
		}
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(fileDTO.getCodiceFiscale()).orElse(null);
		if (persona != null) {
			List<Accreditamento> listaAcc = new ArrayList<>();
			 listaAcc = accreditamentoRepo.findByPartecipanteAndPersona(partecipante, persona);
			 if (listaAcc.size() != 0) {
				 accreditamentoAnteprimaDTO.setId(listaAcc.get(0).getId());
				 accreditamentoAnteprimaDTO.setFlagAccreditato(listaAcc.get(0).getFlagAccreditato());
				 return accreditamentoAnteprimaDTO;
			 }
		}
		Accreditamento saved = this.accreditamentoRepo
				.save(this.partecipanteBuilder.buildAccreditamentoFromFile(fileDTO, partecipante, null));
		if (fileDTO.getFile() != null) {
			creaDocumentoAccreditamento(fileDTO, partecipante, saved);
		}
		this.partecipanteService.creaUtentiAccreditati(saved.getPartecipante().getConferenza());
		if (!saved.getFlagAccreditato()) {
			Boolean isResponsabile = Boolean.TRUE;
			eventoService.creaEventoAccreditamento(saved,
					Integer.toString(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_ACCREDITAMENTO), isResponsabile);
		}
		accreditamentoAnteprimaDTO.setId(saved.getId());
		accreditamentoAnteprimaDTO.setFlagAccreditato(saved.getFlagAccreditato());
		return accreditamentoAnteprimaDTO;
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
			DocumentoFileDTO docDTO = new DocumentoFileDTO();
			docDTO.setFile(fileDTO.getFile());
			documentoService.creaRegistroDocumento(docDTO, documentoSaved);
		}
	}

	public AccreditamentoAnteprimaDTO updateAccreditamento(AccreditamentoFileDTO fileDTO, Integer id) {
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
		AccreditamentoAnteprimaDTO accreditamentoAnteprimaDTO = new AccreditamentoAnteprimaDTO();
		accreditamentoAnteprimaDTO.setId(saved.getId());
		accreditamentoAnteprimaDTO.setFlagAccreditato(saved.getFlagAccreditato());
		return accreditamentoAnteprimaDTO;
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
		if (!conferenza.getCodiceFiscaleResponsabileConferenza().equals(cf) && !conferenza.getCodiceFiscaleCreatoreConferenza().equals(cf)) {
			throw new NotAuthorizedUser("Utente non dispone di autorizzazioni per l'applicazione");
		}
		Accreditamento accreditamento = this.accreditamentoRepo.findById(idAcc).orElse(null);
		if (accreditamento == null) {
			throw new NotFoundEx("the accreditation doesn't exist");
		}
		if (accreditamentoDTO.getFlagAccreditato().equals(Boolean.TRUE)) {
			accreditamento.setFlagAccreditato(Boolean.TRUE);
			eventoService.creaEventoAccreditamento(accreditamento,
					Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONFERMA_ACCREDITAMENTO), Boolean.FALSE);
		}
		if (accreditamentoDTO.getFlagRifiuto().equals(Boolean.TRUE)) {
			accreditamento.setFlagRifiuto(Boolean.TRUE);
			accreditamento.setDescrizioneRifiuto(accreditamentoDTO.getDescrizioneRifiuto());
			eventoService.creaEventoAccreditamento(accreditamento,
					Integer.toString(DbConst.TIPOLOGIA_EVENTO_RIFIUTA_ACCREDITAMENTO), Boolean.FALSE);
		}
		Accreditamento saved = this.accreditamentoRepo.save(accreditamento);
		this.partecipanteService.creaUtentiAccreditati(conferenza);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	public void scambioPuntamentiAccreditati(Integer idAccNuovo, Integer idAccVecchio) {
		Accreditamento accNuovo = accreditamentoRepo.findById(idAccNuovo).orElse(null);
		Accreditamento accVecchio = accreditamentoRepo.findById(idAccVecchio).orElse(null);
		documentoRepo.findByOwner(accVecchio).stream().forEach(d -> {
			d.setOwner(accNuovo);
			documentoRepo.save(d);
		});
	}
	
	public Accreditamento creaAccreditamentoReponsabile(Persona persona, Partecipante partecipante) {
		Accreditamento accreditamento = new Accreditamento();
		accreditamento.setPartecipante(partecipante);
		accreditamento.setFlagAccreditato(Boolean.TRUE);
		accreditamento.setFlagInvitato(Boolean.TRUE);
		accreditamento.setPersona(persona);
		accreditamento.setRuoloPersona(ruoloPersonaRepo
				.findById(Integer.toString(DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA)).orElse(null));
		return accreditamentoRepo.save(accreditamento);
	}

	public ListaPartecipanteConAccreditatiDTO findPartecipanteConAccreditati(Integer idConference) {
		Conferenza conferenza = confService.findConferenzaByIdFiltrata(idConference);
		List<Partecipante> listaPartecipanti = conferenza.getPartecipantes();
		ListaPartecipanteConAccreditatiDTO lista = new ListaPartecipanteConAccreditatiDTO();
		for (Partecipante partecipante : listaPartecipanti) {
			PartecipanteConAccreditatiDTO dto = new PartecipanteConAccreditatiDTO();
			List<Accreditamento> listaAccreditati = partecipante.getAccreditati();
			dto.setPartecipante(createNotNullLabelValue(partecipante));
			for (Accreditamento accreditamento : listaAccreditati) {
				dto.getListaAccreditamenti().add(new LabelValue(Integer.toString(accreditamento.getId()),
						accreditamento.getPersona().getCognome() + " " + accreditamento.getPersona().getNome()));
			}
			lista.getList().add(dto);
		}
		lista.setTotalNumber(Long.valueOf(lista.getList().size()));
		return lista;
	}

	public Accreditamento findAccreditamentoByConfAndCodFiscale(Conferenza conferenza,
			String codiceFiscale) {
		Accreditamento accreditamento = null;
		for (Partecipante part: conferenza.getPartecipantes()) {
			for (Accreditamento accr: part.getAccreditati()) {
				if (accr.getPersona().getCodiceFiscale().equals(codiceFiscale)) {
					accreditamento = accr;
				}
			}
		}
		return accreditamento;
	}

}
