package conferenza.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaProtocolloDTO;
import conferenza.DTO.ProtocolloDTO;
import conferenza.builder.ProtocollazoneBuilder;
import conferenza.consolleAmministrativa.DTO.lista.ListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaUtentePreviewDTO;
import conferenza.model.DatiProtocolloError;
import conferenza.model.Evento;
import conferenza.model.Protocollo;
import conferenza.repository.DatiProtocolloErrorRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.ProtocolloNoBatchRepository;
import conferenza.util.DbConst;


@Service
public class ProtocollazioneService {
	

	 @Autowired
	 ProtocolloNoBatchRepository protocolloRepo;

	 @Autowired
	 ProtocollazoneBuilder protocolloBuilder;
	 
	 @Autowired
	 EventoRepository eventRepo;
	 
	 @Autowired
	 DatiProtocolloErrorRepository datiProtErrorRepo;
	
	/**
	 * Devono essere restituiti tutti i protocolli della conferenza
	 * 
	 * @param idConferenza
	 * @return
	 */
	public ListaProtocolloDTO findProtocolliConferenza(Integer idConferenza) {
		
		ListaProtocolloDTO lista = new ListaProtocolloDTO();
		List<ProtocolloDTO> daEliminare = new ArrayList<ProtocolloDTO>();
		
		List<Protocollo> protocolList = this.protocolloRepo.findProtocolloByConferenza(idConferenza);
		
		for(Protocollo protocollo:protocolList){
			String evento = "";
			List<Integer> maxEventoGroupByModData = this.eventRepo.getMaxEventoByConferenzaAndDocumentoAndModificaData(protocollo.getDocumento().getConferenza().getIdConferenza(), 
					protocollo.getDocumento().getIdDocumento());
			
			if (maxEventoGroupByModData != null
					&& maxEventoGroupByModData.size() >= 1) {
				int i = 0;
				for (Integer idEv : maxEventoGroupByModData) {
					Optional<Evento> eOptional = this.eventRepo.findById(idEv);
					if (eOptional != null) {
						Evento e = eOptional.get();
						if (evento == null || evento.equals("") ) {
							evento = e.getTipoEvento().getDescrizione();
						}
						if (e.getModificaData() != null) {
							evento += " " + e.getModificaData().getTipoData().getDescrizione();
							
							if (i < maxEventoGroupByModData.size() - 1) {
								evento += ", ";
							}
						}
					}
				}
			} 
			
			lista.getList().add(this.protocolloBuilder.buildProtocolloDTO(protocollo,evento));
		}
		
		List<Protocollo> protocolListNoError = this.protocolloRepo.findProtocolloNoErrorByConferenza(idConferenza);
		
		
		for(Protocollo protocollo: protocolListNoError){
			if (lista.getList() != null && lista.getList().size() > 0) {
				for (ProtocolloDTO pDTO :  lista.getList()) {
					if (pDTO.getIdDocNumber().equals(protocollo.getDocumento().getIdDocumento())&&
							pDTO.getProtocolState().equals(DbConst.STATO_PROTOCOLLO_IN_ERRORE_DESC)) {
						daEliminare.add(pDTO);
					}
				}
			}
			List<Evento> listaEventi = this.eventRepo.findListaByConferenzaAndDocumento(protocollo.getDocumento().getConferenza(), 
					protocollo.getDocumento());
			
			String evento = "";
			if (listaEventi != null && listaEventi.size() > 1) {
				evento = listaEventi.get(0).getTipoEvento().getDescrizione();
				int i = 0;
				for (Evento e: listaEventi) {
					
					if (e.getModificaData() != null) {
						evento += " " + e.getModificaData().getTipoData().getDescrizione();
						
						if (i < listaEventi.size() - 1) {
							evento += ", ";
						}
					}
					i++;
				}
			
			} else {
				if (listaEventi != null && listaEventi.size() == 1) {
					evento = listaEventi.get(0).getTipoEvento().getDescrizione();
					if (listaEventi.get(0).getModificaData() != null) {
						evento += " " + listaEventi.get(0).getModificaData().getTipoData().getDescrizione();
					}
				}
			}
			
			lista.getList().add(this.protocolloBuilder.buildProtocolloDTO(protocollo, evento));
		}
		
		//Eliminazione protocolli andati in errore che sono stati protocollati
		if (daEliminare.size() > 0) {
			lista.getList().removeAll(daEliminare);
		}
		 
		lista.setTotalNumber(new Long(lista.getList().size()));
		return lista;
		
	}
	
	public IdentifiableDTO patchProtocollo(Integer id) {
		Protocollo prot = protocolloRepo.findByIdProtocollo(id);
	    prot.setStatoProtocollo(DbConst.STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO);
	    prot.setError(null);
			
	    Protocollo saved = this.protocolloRepo.save(prot);

		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	public ListaProtocolloPreviewDTO findProtocolliError(String value) {
		ListaProtocolloPreviewDTO result = new ListaProtocolloPreviewDTO();
		if (value == null || value.equals("")) {
			List<DatiProtocolloError> listaProtocolli = datiProtErrorRepo.findAllProtocolError();
			
			if (listaProtocolli != null &&
					listaProtocolli.size() > 0) {
				for (DatiProtocolloError dpe : listaProtocolli) {
					Protocollo protErrore = protocolloRepo.findErroryIdConferenzaAndDocumento(dpe.getId_conferenza(),
							dpe.getId_documento());
					if (protErrore != null) {
						String evento = "";
						List<Integer> maxEventoGroupByModData = this.eventRepo.getMaxEventoByConferenzaAndDocumentoAndModificaData(dpe.getId_conferenza(), 
								dpe.getId_documento());
						
						if (maxEventoGroupByModData != null
								&& maxEventoGroupByModData.size() >= 1) {
							int i = 0;
							for (Integer idEv : maxEventoGroupByModData) {
								Optional<Evento> eOptional = this.eventRepo.findById(idEv);
								if (eOptional != null) {
									Evento e = eOptional.get();
									if (evento == null || evento.equals("") ) {
										evento = e.getTipoEvento().getDescrizione();
									}
									if (e.getModificaData() != null) {
										evento += " " + e.getModificaData().getTipoData().getDescrizione();
										
										if (i < maxEventoGroupByModData.size() - 1) {
											evento += ", ";
										}
									}
								}
							}
						}
						
						result.getList().add(this.protocolloBuilder.buildProtocolloPreviewDTO(protErrore,evento));
					}
				}
			}
		} else {
			List<DatiProtocolloError> listaProtocolli = datiProtErrorRepo.findAllProtocolErrorByConferenza(Integer.parseInt(value));
			
			if (listaProtocolli != null &&
					listaProtocolli.size() > 0) {
				for (DatiProtocolloError dpe : listaProtocolli) {
					Protocollo protErrore = protocolloRepo.findErroryIdConferenzaAndDocumento(dpe.getId_conferenza(),
							dpe.getId_documento());
					
					if(protErrore != null) {
						String evento = "";
						List<Integer> maxEventoGroupByModData = this.eventRepo.getMaxEventoByConferenzaAndDocumentoAndModificaData(dpe.getId_conferenza(), 
								dpe.getId_documento());
						
						if (maxEventoGroupByModData != null
								&& maxEventoGroupByModData.size() >= 1) {
							int i = 0;
							for (Integer idEv : maxEventoGroupByModData) {
								Optional<Evento> eOptional = this.eventRepo.findById(idEv);
								if (eOptional != null) {
									Evento e = eOptional.get();
									if (evento == null || evento.equals("") ) {
										evento = e.getTipoEvento().getDescrizione();
									}
									if (e.getModificaData() != null) {
										evento += " " + e.getModificaData().getTipoData().getDescrizione();
										
										if (i < maxEventoGroupByModData.size() - 1) {
											evento += ", ";
										}
									}
								}
							}
						}
						result.getList().add(this.protocolloBuilder.buildProtocolloPreviewDTO(protErrore,evento));
					}
					
					
					
				}
			}
			
		}
		
		
		return result;
	}

}
