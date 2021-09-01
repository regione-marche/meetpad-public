package conferenza.adapder.integrazioni.indicepa.service;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.indicepa.adapter.IndicePaClientAdapter;


@Service
public class IndicePaService {

	
	private static final Logger logger = LogManager.getLogger(IndicePaService.class.getName());
	

	@Autowired
	IndicePaClientAdapter ipaCleintAdapter;
	

	public String sendPOSTFindInIndicePA(String par1) throws IOException{
		return ipaCleintAdapter.sendPOSTFindInIndicePA(par1);
	}


	public String sendPOSTSelectAmmInIndicePA(String amm) throws IOException {
		return ipaCleintAdapter.sendPOSTSelectAmmInIndicePA(amm);
	}
}
