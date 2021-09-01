package conferenza.asyncronous;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import conferenza.mail.EmailRepositoryService;
import conferenza.model.Conferenza;
import conferenza.repository.ConferenzaRepository;

@Service
public class AsyncProtocolDocuments {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProtocolDocuments.class);
	

}
