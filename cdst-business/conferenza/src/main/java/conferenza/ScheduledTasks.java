package conferenza;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import conferenza.adapder.integrazioni.domus.DomusConfigurator;
import conferenza.adapder.integrazioni.domus.service.DomusListnerService;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.asyncronous.AsyncMailService;
import conferenza.mail.JavaMailPecSenderConfigurator;
import conferenza.protocollo.ProtocolloConfigurator;
import conferenza.protocollo.service.ProtocolloService;

@Component
@EnableAsync
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Autowired
    AsyncMailService asyncMailService;
    
	@Autowired
	JavaMailPecSenderConfigurator mailPecSenderConfigurator;
	@Autowired
	ProtocolloConfigurator protocolloConfigurator;
	
	@Autowired
	DomusConfigurator domusConfigurator;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	ProtocolloService observerService;
	
	@Autowired
	DomusListnerService domusServiceTask;
	
	@Value("${schedulerEnabled:false}")
	Boolean isSchedulerEnabled;
	
	@Autowired
	PaleoAdapterService paleoAdapterService;
	
	/**
	 * 3600000 = 1 ora
	 * 600000 = 10 min
	 * 300000 = 5 min
	 * 120000 = 2 min
	 */
	@Async
    @Scheduled(fixedRate = 190 * 60 * 1000, initialDelay = 15 * 60 * 1000) 
    public void scheduleTaskWithFixedRate() {
		if(isSchedulerEnabled) {
			if (mailPecSenderConfigurator.isEnableAsyncCheckMailStatus()) {
				try {
					logger.debug("@mail asyncCheckMailStatusAll start ["+AsyncMailService.lastPOP3sslMessageDate+", " + AsyncMailService.pop3SslMessageSearchId+", "+AsyncMailService.pop3SslMessageReadSchema+"]");
		        	asyncMailService.asyncCheckMailStatusAll();
					logger.debug("@mail asyncCheckMailStatusAll end ["+AsyncMailService.lastPOP3sslMessageDate+", " + AsyncMailService.pop3SslMessageSearchId+", "+AsyncMailService.pop3SslMessageReadSchema+"]");
				} catch (Exception e) {
					logger.debug("@mail asyncCheckMailStatusAll exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
				} 
	        }
	        
	        if (mailPecSenderConfigurator.isEnableAsyncReinoltroNonAccettazionePec()) {
				try {
					logger.debug("@mail asyncReinoltroNonAccettazionePec start");
		        	asyncMailService.asyncReinoltroNonAccettazionePec();
					logger.debug("@mail asyncReinoltroNonAccettazionePec end");
				} catch (Exception e) {
					logger.debug("@mail asyncReinoltroNonAccettazionePec exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
				} 
	        }

	        if (mailPecSenderConfigurator.isEnableAsyncReinoltroMailInErrore()) {
				try {
					logger.debug("@mail chechReinoltorEmailInErrore start");
		        	asyncMailService.chechReinoltorEmailInErrore();
					logger.debug("@mail chechReinoltorEmailInErrore start");
				} catch (Exception e) {
					logger.debug("@mail chechReinoltorEmailInErrore exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
				} 
	        }

			logger.debug("@mail fixed Rate terminated - {}", dateTimeFormatter.format(LocalDateTime.now()) );
		}
    }
    
    /**
     * starts after 10 minutes and runs every 20 minutes
     */
    @Async
    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay=Long.MAX_VALUE)
    public void scheduleTaskProtocollazionePaleo() {
    	//boolean isDev = "DEV".contentEquals(domusConfigurator.getDomusAmbiente());

    	if(isSchedulerEnabled && "Y".equals(protocolloConfigurator.getEnableservice())) {
    		while(true) {
    			try {
    				logger.debug("@paleo Protocollazione start time - {" + dateTimeFormatter.format(LocalDateTime.now()) + "}");
	            	
    				Map<String,String> alreadyProcessed = new HashMap<String,String>();
    				List<Map<String,String>> taskAsincroni= observerService.getRegisterdAsincronousTask();
	    	        for(Map<String,String> map : taskAsincroni) {
	    	        	String item = map.get("class");
	    	        	
	    	        	if(alreadyProcessed.containsKey(item))
	    	        		continue;
	    	        	alreadyProcessed.put(item, "");
	    	        	
	    	        	
	    		        //DLG:TO VERIFY
	    		        String methodName = "doAsincronousTask"; // the method to be called
	    		        Object bean = appContext.getBean(item);//defined by the @Component params
	    		        Method method;
	    				try {
	    					logger.debug("[scheduleTaskWithFixedRate] : "+item+"." + methodName);
	    					method = bean.getClass().getMethod(methodName);
	    			        if (method != null) {
	    			            method.invoke(bean);//java reflection ..
	    			        }  			
	    				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	    					logger.debug("scheduleTaskProtocollazionePaleo exception!");
	    					e.printStackTrace();
	    				}
	    	        }
	    	        
    				logger.debug("@paleo Protocollazione completed: Execution Time - {" + dateTimeFormatter.format(LocalDateTime.now()) + "}");
				} catch (Throwable keepalive) {
					logger.debug("@scheduleTaskProtocollazionePaleo keepalive exception: " + keepalive.getMessage() + " " +Arrays.toString(keepalive.getStackTrace()));
	            	keepalive.printStackTrace();
				}

    			try {
        			Thread.sleep(10 * 60 * 1000); // 10 minutes before next run!
				} catch (Exception e) {
	            	System.out.println("@scheduleTaskProtocollazionePaleo sleep exception: " + e.getMessage());
				}
            }
    		
    	}
        
    }
    
    /**
     * starts after 1 hour and runs every 1/2 a day
     */
    @Async
    @Scheduled(initialDelay = 10 * 60 * 1000, fixedDelay=Long.MAX_VALUE)
    public void scheduleTaskDomus() {
    	if(isSchedulerEnabled && "Y".equals(domusConfigurator.getDomusEnableAsincronous())) {
    		while(true) {
    			try {
		    		logger.debug("Creazione conferenze da Domus : Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		            //TODO: implementare
		            //TODO: testare ..per autenticazione:  utente-fittizio: false..verificare i problemi nel non utilizzo di utenti fittizi
	    			domusServiceTask.doAsincronousTaskToCreateDomusConference();        
				} catch (Throwable keepalive) {
					logger.debug("@scheduleTaskDomus keepalive exception: " + keepalive.getMessage() + " " +Arrays.toString(keepalive.getStackTrace()));
	            	keepalive.printStackTrace();
				}
    			
    			try {
        			Thread.sleep(12 * 60 * 60 * 1000); // runs every 1/2 day
				} catch (Exception e) {
	            	System.out.println("@scheduleTaskDomus sleep exception: " + e.getMessage());
				}
            }
	    	//logger.debug("Creazione conferenze terminata : Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    	}
        
    }
}
