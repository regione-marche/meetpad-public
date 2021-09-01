package cdst_be_marche.protocollo.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.model.RegistroDocumento;
import cdst_be_marche.protocollo.model.ObserverRegistryListener;
import cdst_be_marche.protocollo.model.Protocollo;
import cdst_be_marche.protocollo.repository.ObserverRegistryListenerRepository;
import cdst_be_marche.protocollo.repository.ProtocolloRepository;


@Service
public class ProtocolloService {

	private static final Logger logger = LogManager.getLogger(ProtocolloService.class.getName());

	@Autowired
	ProtocolloRepository protocolloRepository;
	
	@Autowired
	ObserverRegistryListenerRepository listnerRepository;
	
	
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol(String par1) {		
		return listnerRepository.findAllDocToSubmitToProtocol(par1);
	}
	
	public List<String> getRegisterdAsincronousTask(){
		return listnerRepository.getRegisterdAsincronousTask();		
	}

	/**
	 * salvataggio nella tabella Protocollo
	 * @param registroDocumento
	 * @param body
	 * @param idTipoProtocollo
	 * @param idStatoProtocollo
	 */
	//TODO: verificare id_protocollo_esterno e dataprotocollo
	public void saveProtocollo(RegistroDocumento registroDocumento, String body, Integer idTipoProtocollo,
			Integer idStatoProtocollo) {
		Protocollo protocollo = new Protocollo();
		protocollo.setFk_protocollo_meetpad(registroDocumento.getId());
		protocollo.setId_documento(registroDocumento.getDocumento().getIdDocumento());
		protocollo.setFk_tipo_protocollo(idTipoProtocollo);
		protocollo.setFk_Stati_Protocollo(idStatoProtocollo);
		//protocollo.setId_protocollo_esterno(id_protocollo_esterno);
		protocollo.setNumeroProtocollo(body);
		protocollo.setDataProtocollo(new Date());
		protocolloRepository.save(protocollo);
	}
	
}
