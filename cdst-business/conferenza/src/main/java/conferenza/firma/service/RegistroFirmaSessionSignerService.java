package conferenza.firma.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.model.RegistroFirmaSessionSigner;
import conferenza.firma.repository.RegistroFirmaSessionSignerRepository;

@Service
public class RegistroFirmaSessionSignerService {

	@Autowired
	RegistroFirmaSessionSignerRepository registroFirmaSessionSignerRepository;
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	public boolean isClosableSession(FirmaDTO firma) {
		
		List<RegistroFirmaSessionSigner>  signers=getListSignerWaitingFor(firma);
		if(signers==null || signers.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean isSessionClosed(FirmaDTO firma) {
		
		List<RegistroFirmaSessionSigner>  signers=getListSignerWaitingFor(firma);
		if(signers==null || signers.isEmpty())
			return true;
		
		return false;
	}

	/**
	 * 
	 * @param firma
	 * @return
	 */
	public  List<RegistroFirmaSessionSigner> getListSignerWaitingFor(FirmaDTO firma){		
		List<RegistroFirmaSessionSigner>  signers=registroFirmaSessionSignerRepository.getMissingSessionSignerRepository(firma.getSessioneFirma());
		return signers;
	}
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	private String getSigningSession(FirmaDTO firma) {
		if(firma.getSessioneFirma()!=null)
			return firma.getSessioneFirma();
		
		if(firma.getIdDocumento()!=null)
			return registroFirmaSessionSignerRepository.getLastSigningSession(firma.getIdDocumento());
		
		return null;
	}
	
	/**
	 * 
	 * @param firma
	 * @param signer
	 * @throws IOException 
	 */
	public void addSignerInSession(FirmaDTO firma,Integer signer) 
			throws IOException {
		
		String lSessionFirma=null;
		//[CASO USO] - occorre conoscere la session o l'id documento per ricavarla..
		if(firma.getSessioneFirma()==null && firma.getIdDocumento()==null)
			throw new IOException("Impossibile aggiungere un utente alla sessione");
		if(signer==null)
			throw new IOException("Impossibile aggiungere un utente nullo alla sessione");
		
		///
		if(firma.getSessioneFirma()!=null || firma.getIdDocumento()!=null) {
			lSessionFirma=getSigningSession(firma);
			if(lSessionFirma==null)
				throw new IOException("Non trovata alcuna sessione");
			
			firma.setSessioneFirma(lSessionFirma);
		}	
			
		RegistroFirmaSessionSigner sessionsigner = new RegistroFirmaSessionSigner();
		sessionsigner.setOwner(firma.getIdUser());
		sessionsigner.setFkSessione(firma.getSessioneFirma());
		sessionsigner.setSigner(signer);
		registroFirmaSessionSignerRepository.save(sessionsigner);
		
	}
	
	
}
