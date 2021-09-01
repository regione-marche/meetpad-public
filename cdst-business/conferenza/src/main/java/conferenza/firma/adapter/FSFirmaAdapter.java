package conferenza.firma.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import conferenza.firma.DTO.FirmaDTO;

@Component("conferenza.firma.adapter.FSFirmaAdapter")
public class FSFirmaAdapter extends FirmaClientAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FSFirmaAdapter.class.getName());
		
	@Override
	public FirmaDTO firma(FirmaDTO par1) {		
		logger.debug("[FSFirmaAdapter.firma] - idDocumento  "+par1.getIdDocumento());		
		// TODO Auto-generated method stub	
		return par1;
	}

	@Override
	public Resource getFile(FirmaDTO par1) {
		logger.debug("[FSFirmaAdapter.getFile] - idDocumento  "+par1.getIdDocumento());		
		return par1.getResource();
	}




	
	
}
