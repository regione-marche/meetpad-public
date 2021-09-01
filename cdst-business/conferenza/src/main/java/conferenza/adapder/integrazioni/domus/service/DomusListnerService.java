package conferenza.adapder.integrazioni.domus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.RispostaJson;
import conferenza.adapder.integrazioni.domus.model.DomusComune;
import conferenza.adapder.integrazioni.domus.repository.DomusComuneRepository;
import conferenza.model.RegistroDocumento;
import conferenza.properties.AutenticazioneProperties;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.repository.ObserverRegistryListnerInterface;


/**
 * 
 * Il metodo asincrono può essere chiamato 
 * SSE è settato il paramtero:getUtenteAuditFittizio=true
 * viceversa c'è un errore di AUDIT!
 * 
 * 
 * 
 * @author guideluc
 *
 */
@Service
@Transactional
@Component("DomusListnerInterface")
public class DomusListnerService extends DomusService implements ObserverRegistryListnerInterface{

	private static final Logger logger = LoggerFactory.getLogger(DomusListnerService.class.getName());
	
	@Autowired
	DomusComuneRepository domusComuneRepository;

	@Autowired
	AutenticazioneProperties autenticazioneProperties;

	
	public synchronized void doAsincronousTaskToCreateDomusConference(){
		doAsincronousTaskToCreateDomusConference(true);
	}
	
	/**
	 * 
	 */
	public synchronized void doAsincronousTaskToCreateDomusConference(boolean slowdown){
		HashMap hashMap = new HashMap();		
		List<Integer> conferenze=new ArrayList();
		List<DomusComune> comuni = domusComuneRepository.findAll();
		for(DomusComune comune:comuni){
			try {
				conferenze = super.doActionCreateDomusConferences(comune.getCodiceComune(),super.tipologiaConferenza,null);
				hashMap.put(comune.getCodiceComune(), conferenze);
			} catch (ServiceException | IOException e) {
				e.printStackTrace();
			}
			
			if(comune.getCodiceComune().startsWith("#")) 
				domusComuneRepository.delete(comune);
			
			if(slowdown)
				try { wait(5000); } catch (InterruptedException skip) { }
		}
		
		logger.info("@domus Esecuzione di  doAsincronousTaskToCreateDomusConference terminata!");
		
		//DUMP dei risultati!
		Set keyset=null;
		if(hashMap.keySet()!=null && !hashMap.isEmpty()){
			keyset=hashMap.keySet();
			Iterator iter=keyset.iterator();
			while(iter.hasNext()){
				String codicecomune=(String) iter.next();
				logger.debug("itera per comune: "+codicecomune);
				List<Integer> outlist= (List<Integer>) hashMap.get(codicecomune);
				if(outlist!=null && !outlist.isEmpty())
				for(Integer id:outlist){
					logger.debug("@domus creata la conferenza "+id);
					try {
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}		
		
	}
	
	/**
	 * Il metodo è chiamato per riflessione leggendo la observer registry..
	 * però può essere chiamata solo da un contesto demone..
	 * ovvero per il quale sia stato individuato un autenticazione di default..
	 */
	public void doAsincronousTask(){
		
		if (autenticazioneProperties.getUtenteAuditFittizio()==false) {
			logger.info("L'integrazione con domus può essere eseguita solo su un contesto demone! ovvero per getUtenteAuditFittizio=true!!!");
			return;			
		}
		
		doAsincronousTaskToCreateDomusConference();
	}

	@Override
	public RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO,
			Object object, String idTipoEvento) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
