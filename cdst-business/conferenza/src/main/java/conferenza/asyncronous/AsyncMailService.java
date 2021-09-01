package conferenza.asyncronous;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.paleo.model.PaleoTipoConferenceProperties;
import conferenza.adapder.integrazioni.paleo.service.PaleoPropertiesService;
import conferenza.mail.EmailRepositoryService;
import conferenza.model.Conferenza;
import conferenza.model.MailHashes;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.MailHashesRepository;

@Service
public class AsyncMailService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncMailService.class);
	
	@Autowired
	ConferenzaRepository conferenzaRepository;
	@Autowired
	EmailRepositoryService emailRepository;

	@Autowired
	MailHashesRepository mailHashesRepository;
	
	@Autowired 
	PaleoPropertiesService paleoPropertiesService;

	public static String pop3SslMessageSearchId = null;
	public static String pop3SslMessageReadSchema = "";
	public static Date lastPOP3sslMessageDate = null;
    public static final SimpleDateFormat dateFormat =  new SimpleDateFormat("yyy-MM-dd HH:mm:ss.S");

	static public int messageReadHashesArray[] = new int[0];
	static public int messageReadHashesArraySize = 0;
	static public Exception m_readNOMailRetrievedException = new Exception("POP ssl returned no new mails to read!");
	static public Exception m_readMailEofException = new Exception();

	 @Async
	 public CompletableFuture<Boolean> asyncCheckMailStatus() throws Exception {
		 Boolean retBool=new Boolean(true);
		 asyncCheckMailStatusAll();		
		 Thread.sleep(1000L);
		 return CompletableFuture.completedFuture(retBool);
	 }

	 @Async
	 public CompletableFuture<Boolean> asyncChechReinoltorEmailInErrore() 
	 throws InterruptedException {
		 Boolean retBool=new Boolean(true);
		 chechReinoltorEmailInErrore();		
		 Thread.sleep(1000L);
		 return CompletableFuture.completedFuture(retBool);
	 }
	 
	 
	 public void chechReinoltorEmailInErrore() {
		 emailRepository.sendMailIndizioneForTransportError();		 
	 }
	 
	 
	 /**
	  * Effettua un controllo sulle email in POP3 ed allinea il DB se non sono presenti gli stati..
	  * 
	  */
	 public void asyncCheckMailStatusAll() throws Exception {
		PaleoTipoConferenceProperties lastPop3SslMessageProp = paleoPropertiesService.getByPropertyName("lastPop3SslMessage");
		try { lastPOP3sslMessageDate = dateFormat.parse(lastPop3SslMessageProp.getPropertiesValue()); } catch (Exception e) { }
		PaleoTipoConferenceProperties pop3SslMessageReadSchemaProp = paleoPropertiesService.getByPropertyName("pop3SslMessageReadSchema");
		try { pop3SslMessageReadSchema = pop3SslMessageReadSchemaProp.getPropertiesValue(); } catch (Exception e) {  }
		PaleoTipoConferenceProperties pop3SslMessageSearchIdProp = paleoPropertiesService.getByPropertyName("pop3SslMessageSearchId");
		try { pop3SslMessageSearchId = pop3SslMessageSearchIdProp.getPropertiesValue(); } catch (Exception e) {  }

		getMailHashes();

		// retry for EOF issue
		while(true) {
			try {
				emailRepository.checkAndestoreAllStatus();
				
				break;
			} catch (Exception e) {
		    	putMailHashes();
				
				if(m_readMailEofException != e)
					throw e;

				continue;
			}
		}

    	putMailHashes();
    	
    	if(lastPOP3sslMessageDate != null) {
			lastPop3SslMessageProp.setPropertiesValue(dateFormat.format(lastPOP3sslMessageDate));
			paleoPropertiesService.save(lastPop3SslMessageProp);
    	}
	 }
	 
	 
	 public void asyncCheckMailStatusAllConference() {
		 List<Conferenza> conferenze = conferenzaRepository.findAll();
		 for(Conferenza conferenza: conferenze) {			 
			 asyncCheckMailStatusConference(conferenza.getIdConferenza());			 
		 }				 
	 }

	 public void asyncCheckMailStatusConference(Integer pConferenceId) {
		 LOGGER.debug("asyncCheckMailStatusConference :[id conferenza]"+pConferenceId );
		 emailRepository.checkEmailStatus(pConferenceId);				 
	 }

	public void asyncReinoltroNonAccettazionePec() {
		emailRepository.sendMailOrdinariaForNonAccettazionePec();
	}
	
	public int[] getMailHashes() {
		messageReadHashesArraySize = 0;
		
		List<MailHashes> mhs = mailHashesRepository.findAll();
		if(mhs == null || mhs.size() != 1) 
			return AsyncMailService.messageReadHashesArray;
		
		byte[] arr = mhs.get(0).getData();
		if(arr == null || arr.length == 0)
			return AsyncMailService.messageReadHashesArray;
		
		messageReadHashesArraySize = arr.length / 4;
		
		LOGGER.debug("@mail hashes read:"+messageReadHashesArraySize);
		
		ByteBuffer bb = ByteBuffer.wrap(arr);
		//bb.order(ByteOrder.LITTLE_ENDIAN);
		int[] res = new int[(int)(arr.length / 4f * 1.2f)];
		
		try {
			for(int i=0; i<arr.length;i+=4) {
				res[i/4] = bb.getInt(i);
			}
		} catch (Exception e) {
			LOGGER.debug("@mail cannot read hashes array exception: " + e.getMessage() + " for: " + Arrays.toString(e.getStackTrace()));
			return AsyncMailService.messageReadHashesArray;
		}
		
		AsyncMailService.messageReadHashesArray = res;
		
		return res;
	}
	
	public void putMailHashes() {
		List<MailHashes> mhs = mailHashesRepository.findAll();
		if(mhs == null || mhs.size() != 1) 
			return;
		
		int[] newarr = new int[messageReadHashesArraySize];
		for(int i = 0;i < messageReadHashesArraySize;i++)
			newarr[i] = messageReadHashesArray[i];
		
		messageReadHashesArray = newarr;
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(messageReadHashesArraySize * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(messageReadHashesArray);
        
        byte[] array = byteBuffer.array();		
		
		mhs.get(0).setData(array);
		
		LOGGER.debug("@mail hashes write:"+messageReadHashesArraySize);
		
		mailHashesRepository.save(mhs.get(0));
	}
	
}
