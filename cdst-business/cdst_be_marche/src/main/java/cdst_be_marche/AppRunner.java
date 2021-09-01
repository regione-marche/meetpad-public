package cdst_be_marche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cdst_be_marche.asyncronous.AsyncMailService;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final AsyncMailService asyncMailService;

    public AppRunner(AsyncMailService pasyncMailService) {
        this.asyncMailService = pasyncMailService;
    }

    
    @Override
    public void run(String... args) throws Exception {
    	logger.info("[AppRunner!!!] ");
    }
    /*
     * @TODO: verificare la migliore prestazione tra
     * questo 
    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<Boolean>  action1 = asyncMailService.asyncCheckMailStatus();

        CompletableFuture<Boolean>  action2 = asyncMailService.asyncChechReinoltorEmailInErrore();
        
        //--------------------------------------------------
        // Wait until they are all done
        //--------------------------------------------------        
        //1) Verifica lo stato di ricezione delle email
        CompletableFuture.allOf(action1).join();

        //2) Verifica lo stato delle email per cui non è avvenuta la trasmissione        
        CompletableFuture.allOf(action2).join();
        
        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("[asyncCheckMailStatus] --> " + action1.get());
        logger.info("[asyncChechReinoltorEmailInErrore]--> " + action1.get());
        //logger.info("--> " + page2.get());
        //logger.info("--> " + page3.get());

    }
    */

}