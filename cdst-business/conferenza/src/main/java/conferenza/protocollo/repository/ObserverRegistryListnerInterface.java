package conferenza.protocollo.repository;

import org.springframework.stereotype.Component;

import conferenza.DTO.RispostaJson;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;


public interface ObserverRegistryListnerInterface {

	public void doAsincronousTask();

	RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, Object object,
			String idTipoEvento);
	
}
