package conferenza.protocollo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.model.RegistroDocumento;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.protocollo.model.Protocollo;
import conferenza.protocollo.repository.ObserverRegistryListenerRepository;
import conferenza.protocollo.repository.ProtocolloRepository;
import conferenza.repository.ProtocolloNoBatchRepository;

@Service
public class ProtocolloService {

	private static final Logger logger = LogManager.getLogger(ProtocolloService.class.getName());

	@Autowired
	ProtocolloRepository protocolloRepository;
	
	@Autowired
	ObserverRegistryListenerRepository listnerRepository;

	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolAndSpecializzazione(String par1) {
		return listnerRepository.findAllDocToSubmitToProtocolAndSpecializzazione(par1);
	}

	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol(String par1) {
		return listnerRepository.findAllDocToSubmitToProtocol(par1);
	}

	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolGlobal(String par1) {
		return listnerRepository.findAllDocToSubmitToProtocolGlobal(par1);
	}

	public List<ObserverRegistryListener> findAllDocuments(Integer id_documento) {
		return listnerRepository.findAllDocuments(id_documento);
	}

	public List<Map<String, String>> getRegisterdAsincronousTask() {
		return listnerRepository.getRegisterdAsincronousTask();
	}

	/**
	 * salvataggio nella tabella Protocollo
	 * 
	 * @param registroDocumento
	 * @param body
	 * @param idTipoProtocollo
	 * @param idStatoProtocollo
	 */
	// TODO: verificare id_protocollo_esterno e dataprotocollo
	public Protocollo saveProtocollo(RegistroDocumento registroDocumento, String body, Integer idTipoProtocollo,
			Integer idStatoProtocollo) {

		Protocollo protEsistente = protocolloRepository.findProtocolloByConferenzaAndDocumento(
				registroDocumento.getDocumento().getConferenza().getIdConferenza(),
				registroDocumento.getDocumento().getIdDocumento());
		Protocollo protocollo = new Protocollo();
		if (protEsistente != null && protEsistente.getId() != null && !protEsistente.getId().equals(new Integer(0))) {
			protocollo.setId(protEsistente.getId());

		}
		protocollo.setFk_protocollo(registroDocumento.getId());
		protocollo.setId_documento(registroDocumento.getDocumento().getIdDocumento());
		protocollo.setFk_tipo_protocollo(idTipoProtocollo);
		protocollo.setFk_Stati_Protocollo(idStatoProtocollo);
		// protocollo.setId_protocollo_esterno(id_protocollo_esterno);
		protocollo.setError(null);
		protocollo.setNumeroProtocollo(body);
		protocollo.setDataProtocollo(new Date());

		return protocolloRepository.save(protocollo);
	}

	public Protocollo saveProtocolloError(RegistroDocumento registroDocumento, String messaggioerrore,
			Integer idTipoProtocollo, Integer idStatoProtocollo) {
		Protocollo protEsistente = protocolloRepository.findProtocolloByConferenzaAndDocumento(
				registroDocumento.getDocumento().getConferenza().getIdConferenza(),
				registroDocumento.getDocumento().getIdDocumento());
		Protocollo protocollo = new Protocollo();

		if (protEsistente != null && protEsistente.getId() != null && !protEsistente.getId().equals(new Integer(0))) {
			protocollo.setId(protEsistente.getId());
		}

		protocollo.setFk_protocollo(registroDocumento.getId());
		protocollo.setId_documento(registroDocumento.getDocumento().getIdDocumento());
		protocollo.setFk_tipo_protocollo(idTipoProtocollo);
		protocollo.setFk_Stati_Protocollo(idStatoProtocollo);
		// protocollo.setId_protocollo_esterno(id_protocollo_esterno);
		protocollo.setError(messaggioerrore);
		protocollo.setDataProtocollo(new Date());

		return protocolloRepository.save(protocollo);
	}

}
