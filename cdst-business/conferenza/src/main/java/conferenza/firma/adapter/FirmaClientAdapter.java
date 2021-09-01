package conferenza.firma.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import conferenza.firma.DTO.FirmaDTO;

@Service
public abstract class FirmaClientAdapter  implements IFirmaAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(FirmaAdapter.class.getName());
	
	
	public FirmaDTO firma(FirmaDTO par1) {
		
		logger.debug("[FirmaClientAdapter.firma]");
		
		return par1;
	}
	
	public Resource getFile(FirmaDTO par1) {
		logger.debug("[FirmaClientAdapter.getFile]");
		return null;
	}
}
