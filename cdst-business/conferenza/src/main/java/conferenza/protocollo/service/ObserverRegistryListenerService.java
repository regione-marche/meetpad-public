package conferenza.protocollo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.domus.service.DomusToFrontieraDTOService;
import conferenza.adapder.integrazioni.paleo.service.PaleoToFrontieraDTOService;
import conferenza.model.Documento;
import conferenza.model.TipoEvento;
import conferenza.model.TipologiaDocumento;
import conferenza.protocollo.ProtocolloConfigurator;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.TipoEventoRepository;

@Service
public class ObserverRegistryListenerService {
	
	@Autowired
	DocumentoRepository docRepo;
	
	@Autowired
	ProtocolloConfigurator protocolloConfigurator;
	
	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	PaleoToFrontieraDTOService paleoPropertiesService;
	
	public IntegProtocolloDTO getUtenteProtocollante(ObserverRegistryListener item, IntegProtocolloDTO integDTO) {
		Documento documento = docRepo.findById(item.getId_documento()).orElse(null);
		if (documento != null && documento.getOwner() != null) {
			integDTO.setCf_utente(documento.getOwner().getPersona().getCodiceFiscale());
			integDTO.setNome_utente(documento.getOwner().getPersona().getNome());
			integDTO.setCognome_utente(documento.getOwner().getPersona().getCognome());
		}
		TipoEvento tipoEvento = tipoEventoRepo.findById(item.getId_tipo_evento()).orElse(null);
		if (tipoEvento != null) {
			TipologiaDocumento tipoDoc = tipoEvento.getTipologiaDocumento();
			integDTO.setTipologia_documento(tipoDoc.getCodice()+"-"+tipoDoc.getDescrizione());
			integDTO.setSubject(tipoEvento.getDescrizione());
		}
		
		//inserisci parametri di configuraione
		//TODO verificare se andrà esteso ad altri tipi di integrazioni ad esempio domus
		integDTO.setCodiceRegistro(paleoPropertiesService.getByIdTipoConferenzaAndNomeProperties(documento.getConferenza().getTipologiaConferenzaSpecializzazione().getCodice(), "codice.registro", "GRM"));
		integDTO.setCodiceRubricaDestinatario(paleoPropertiesService.getByIdTipoConferenzaAndNomeProperties(documento.getConferenza().getTipologiaConferenzaSpecializzazione().getCodice(), "codice.rubrica.destinatario", "GRMA"));
		
		integDTO.setId_conferenza(item.getId_conferenza());
		return integDTO;
	}

}
