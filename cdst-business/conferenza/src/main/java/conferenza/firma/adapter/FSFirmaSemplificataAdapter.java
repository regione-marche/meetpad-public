package conferenza.firma.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.service.FirmaSemplificataService;
import conferenza.model.Documento;
import conferenza.service.DocumentoService;

@Component("conferenza.firma.adapter.FSFirmaSemplificataAdapter")
public class FSFirmaSemplificataAdapter  implements IFirmaAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FSFirmaSemplificataAdapter.class.getName());
	

	
	public FirmaDTO firma(FirmaDTO par1) {		
		logger.debug("[FSFirmaAdapter.firma] - idDocumento  "+par1.getIdDocumento());		
		// TODO Auto-generated method stub	
		return par1;
	}

	
	/**
	 * Durante l'unlock và festito il caso in cui 
	 * Il responsabile carichi il primo documento; 
	 * in quella circostanza idDocument è null..però è fornita la conferenza..
	 * con questo dato và:
	 * 1) Creato il documento.
	 * 2) Registrato su RegistroDocumento
	 * 
	 * 
	 * 
	 */
	
	public Resource getFile(FirmaDTO par1) {

		logger.debug("[FSFirmaAdapter.getFile] - idDocumento  "+par1.getIdDocumento());		
		return par1.getResource();
	}


}
