package cdst_be_marche;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cdst_be_marche.asyncronous.AsyncMailService;
import cdst_be_marche.mail.JavaMailPecSenderConfigurator;
import cdst_be_marche.protocollo.service.ProtocolloService;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    AsyncMailService asyncMailService;
    
	@Autowired
	JavaMailPecSenderConfigurator mailPecSenderConfigurator;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	ProtocolloService observerService;
	
	/**
	 * 3600000 = 1 ora
	 */
    @Scheduled(fixedRate = 3600000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        
        //1)
        if (mailPecSenderConfigurator.isEnableAsyncCheckMailStatus()) {
        	asyncMailService.asyncCheckMailStatusAll();
        }
        
        //2)
        if (mailPecSenderConfigurator.isEnableAsyncReinoltroMailInErrore()) {
        	asyncMailService.chechReinoltorEmailInErrore();
        }
        
        /**
         * 
         */
        List<String>  taskAsincroni= observerService.getRegisterdAsincronousTask();
        for( String item : taskAsincroni) {
	        //DLG:TO VERIFY
	        String methodName = "doAsincronousTask"; // the method to be called
	        Object bean = appContext.getBean(item);//defined by the @Component params
	        Method method;
			try {
				logger.info("[scheduleTaskWithFixedRate] : "+item+"." + methodName);
				method = bean.getClass().getMethod(methodName);
		        if (method != null) {
		            method.invoke(bean);//java reflection ..
		        }  			
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
        }
    }
    
    /**
     * 1800000 = 1/2 ora
     */
    @Scheduled(fixedRate = 1800000)
    public void scheduleTaskProtocollazione() {
        logger.info("Protocollazione SUAP :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        //TODO: implementare
    }

    public void scheduleTaskWithFixedDelay() {}

    public void scheduleTaskWithInitialDelay() {}

    //@Scheduled(cron = "0 * * * * ?")
    //public void scheduleTaskWithCronExpression() {
    //    logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    //    
    //}
	
}
