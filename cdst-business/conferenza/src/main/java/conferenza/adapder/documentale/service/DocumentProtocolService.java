package conferenza.adapder.documentale.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.RispostaJson;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.ProtocolloConfigurator;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryAudit;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.protocollo.repository.ObserverRegistryAuditRepository;
import conferenza.protocollo.repository.ObserverRegistryListnerInterface;
import conferenza.protocollo.service.ObserverRegistryListenerService;
import conferenza.protocollo.service.ProtocolloService;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.service.DocumentoService;
import conferenza.service.JWTsService;
import conferenza.service.UtenteService;


@Service
@Transactional
@Component("DocumentProtocolServiceInterface")
public abstract class DocumentProtocolService  implements ObserverRegistryListnerInterface{

	private static final Logger logger = LogManager.getLogger(DocumentProtocolService.class.getName());
	
	@Autowired
	UtenteService utenteService;

	@Autowired
	JWTsService jwtService;

	@Autowired
	protected ProtocolloService protocolloService;

	@Autowired
	private ObserverRegistryAuditRepository auditRepository;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	DocumentoService documentoService;

	@Autowired
	ProtocolloConfigurator protocolloConfigurator;

	@Autowired
	ObserverRegistryListenerService observerRegistryListenerService;
	
	
	/**
	 * 
	 * @param idDocument
	 */
	protected ObserverRegistryAudit saveObserverRegistryAudit(ObserverRegistryListener submittedDoc,
			RispostaJson protocollo) {
		ObserverRegistryAudit audit = new ObserverRegistryAudit();
		audit.setFk_ObserverRegistry(submittedDoc.getId_observer_registry());
		audit.setId_evento(submittedDoc.getId_evento());
		if (protocollo != null) {
			audit.setCodiceErrore(protocollo.getCode());
			audit.setDescrizioneErrore(protocollo.getStatus());
		}
		audit = auditRepository.save(audit);
		return audit;
	}
	
	/**
	 * 
	 */
	public void submitProtocol() {
		List<ObserverRegistryListener> listed = findAllDocToSubmitToProtocol();
		for (ObserverRegistryListener item : listed) {
			RegistroDocumento registroDocumento = registroDocumentoRepository.findById(item.getId_registro())
					.orElse(null);
			if (registroDocumento != null) {
				IntegProtocolloDTO  integDTO=IntegProtocolloDTO.fillIntegProtocolloDTO(item);
				integDTO = observerRegistryListenerService.getUtenteProtocollante(item, integDTO);				
				//TODO reistrare il protocollo
				
				//Aggiungo l'id_tipo_evento per aggiungerlo all'oggetto
				String idTipoEvento = item.getId_tipo_evento();
				logger.debug("L'idTipoEvento che mi serve per l'oggetto è: " + idTipoEvento);
				RispostaJson protocollo=submitDocToProtocol(registroDocumento,integDTO,null, idTipoEvento);
				//Commento il vecchio metodo
				//RispostaJson protocollo=submitDocToProtocol(registroDocumento,integDTO,null);
				//Fine modifica mia

				logger.debug("submitProtocol: "+protocollo.getMessage());
				
				if(protocollo.getStatus().equals("protocollato")) {
					ObserverRegistryAudit auditId = saveObserverRegistryAudit(item, protocollo);
				}
				
			}
		}
	}

	public abstract RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO,Object object, String idTipoEvento) ;

	public abstract List<ObserverRegistryListener> findAllDocToSubmitToProtocol() ;

	
	@Override
	public void doAsincronousTask() {
		this.submitProtocol(); 		
	}

	
}
