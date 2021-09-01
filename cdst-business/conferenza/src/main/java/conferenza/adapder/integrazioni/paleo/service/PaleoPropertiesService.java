package conferenza.adapder.integrazioni.paleo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.paleo.model.PaleoTipoConferenceProperties;
import conferenza.adapder.integrazioni.paleo.repository.PaleoTipoConferencePropertiesRepository;

@Service
public class PaleoPropertiesService {

	private static final Logger logger = LoggerFactory.getLogger(PaleoPropertiesService.class.getName());
	
	@Autowired 
	PaleoTipoConferencePropertiesRepository paleoTipoConferencePropertiesRepository;
	  
	public List<PaleoTipoConferenceProperties> getListPaleoTipoConferenceProperties(String codice, String ambiente) {
		return  paleoTipoConferencePropertiesRepository.getParameterByTipoConferenceSpecializzazioneAndAmbiente(codice, ambiente);
	}
	
	public PaleoTipoConferenceProperties getByPropertyName(String paramName) {
		PaleoTipoConferenceProperties prop = paleoTipoConferencePropertiesRepository.findByPropertiesName(paramName);
		
		if(prop == null) {
			prop = new PaleoTipoConferenceProperties();
			prop.setPropertiesName(paramName);
		}
		
		return prop;
	}
	
	public String getParameterValue(String nome, String ambiente) {
		return paleoTipoConferencePropertiesRepository.getParameterValue(nome, ambiente);
	}
	
	
	public void save(PaleoTipoConferenceProperties conf) {
		paleoTipoConferencePropertiesRepository.save(conf);
	}
	
}
