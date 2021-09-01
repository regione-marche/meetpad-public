package conferenza.adapder.integrazioni.suap.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import conferenza.DTO.RispostaJson;
import conferenza.adapder.integrazioni.suap.IntegSuapAdapter;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
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
import conferenza.util.DbConst;

@Service
@Component("SuapObserverRegistryListnerInterface")
public class IntegProtocolService implements ObserverRegistryListnerInterface{
	private static final Logger logger = LogManager.getLogger(IntegProtocolService.class.getName());
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	JWTsService jwtService;
	
	@Autowired
	private ProtocolloService protocolloService;
	
	@Autowired
	private ObserverRegistryAuditRepository auditRepository;
	
	@Autowired
	IntegSuapAdapter suapAdapter;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	ProtocolloConfigurator protocolloConfigurator;
	
	@Autowired
	ObserverRegistryListenerService observerRegistryListenerService;
	
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		String par1="SCRITTURA-PROTOCOLLO";
		return protocolloService.findAllDocToSubmitToProtocol(par1);
	}
	
	/**
	 * 
	 * @param registroDocumento
	 */
	private RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento,IntegProtocolloDTO integDTO,String access_token) {
		RispostaJson protocollo=null;
		ResponseEntity<String> response = null;
		try {
			//response conterra il protocollo
			response = suapAdapter.uploadSingleFile(registroDocumento, integDTO);
			Integer idTipoProtocollo = DbConst.TIPO_PROTOCOLLO_SUAP;
			Integer idStatoProtocollo = DbConst.STATO_PROTOCOLLO_PROTOCOLLATA;
			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				idStatoProtocollo = DbConst.STATO_PROTOCOLLO_IN_ERRORE;
			}
			String codice_protocolloJson = response.getBody();
			ObjectMapper mapper = new ObjectMapper();
			protocollo = mapper.readValue(codice_protocolloJson, new TypeReference<RispostaJson>() {
			});
			protocolloService.saveProtocollo(registroDocumento, protocollo.getMessage(), idTipoProtocollo,
					idStatoProtocollo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return protocollo;
	}

	/**
	 * 
	 * @param idDocument
	 */
	private ObserverRegistryAudit saveObserverRegistryAudit(ObserverRegistryListener submittedDoc,
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
				RispostaJson protocollo=submitDocToProtocol(registroDocumento,integDTO,null);
				logger.debug("submitProtocol: "+protocollo.getMessage());
				ObserverRegistryAudit auditId = saveObserverRegistryAudit(item, protocollo);
			}
		}
	}	
	
	public ResponseEntity<String>  testRestClient(IntegProtocolloDTO integDTO) 
			throws IOException {

		return suapAdapter.uploadSingleFileSincronous(integDTO,null);		
	}

	@Override
	public void doAsincronousTask() {
		this.submitProtocol(); 		
	}

	@Override
	public RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO,
			Object object, String idTipoEvento) {
		// TODO Auto-generated method stub
		return null;
	}
}
