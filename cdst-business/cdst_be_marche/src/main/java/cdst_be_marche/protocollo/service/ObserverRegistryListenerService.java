package cdst_be_marche.protocollo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.model.Documento;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.model.TipologiaDocumento;
import cdst_be_marche.protocollo.ProtocolloConfigurator;
import cdst_be_marche.protocollo.DTO.IntegProtocolloDTO;
import cdst_be_marche.protocollo.model.ObserverRegistryListener;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.TipoEventoRepository;

@Service
public class ObserverRegistryListenerService {
	
	@Autowired
	DocumentoRepository docRepo;
	
	@Autowired
	ProtocolloConfigurator protocolloConfigurator;
	
	@Autowired
	TipoEventoRepository tipoEventoRepo;

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
		}
		integDTO.setId_conferenza(item.getId_conferenza());
		return integDTO;
	}

}
