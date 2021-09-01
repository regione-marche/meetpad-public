package conferenza.firma.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.DTO.RegistroFirmaSessioneDTO;
import conferenza.firma.model.RegistroFirmaSessione;
import conferenza.firma.repository.RegistroFirmaSessionRepository;
import conferenza.util.RandomUtil;

@Service
public class FirmaSessionService {

	private static final Logger logger = LoggerFactory.getLogger(FirmaSessionService.class.getName());
	
	
	@Autowired
	RegistroFirmaSessionRepository registroFirmaSessionRepository;
	
	private  String  generateToken() {
		return RandomUtil.getRandomToken();		
	}
	
	/**
	 * Verifica se è iun corso una sessione di firma:
	 * 1 - se è in corso aggiorna il token..
	 * 2 - se non è in corso la inizia..
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public RegistroFirmaSessione initializeFirmaSession(RegistroFirmaSessioneDTO dto) 
			throws Exception {
		
		if(dto.getFkDocumento()==null)
			throw new Exception("La Sessione di firma non è possibile senza la selezione di un documento!");
		
		RegistroFirmaSessione firmaSessione= registroFirmaSessionRepository.getLastRegistroFirmaSessioni(dto.getFkDocumento());
		if(firmaSessione!=null)
			dto.setToken(firmaSessione.getToken());
		else {
			dto.setToken(generateToken());
			dto.setDtSessioneBegin(new Date());
			firmaSessione =RegistroFirmaSessioneDTO.getRegistroFirmaSessione(dto);
			//la sessione viene salvata..
			registroFirmaSessionRepository.save(firmaSessione);			
		} 
		return firmaSessione;
	} 
	
	public boolean cancel(FirmaDTO firma) {
		RegistroFirmaSessione firmaSessione= registroFirmaSessionRepository.getRegistroFirmaSessioniByToken(firma.getSessioneFirma());
		firmaSessione.setDtSessioneEnd(new Date());
		try {
			registroFirmaSessionRepository.save(firmaSessione);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			return true;
		}						
	}
	
}
