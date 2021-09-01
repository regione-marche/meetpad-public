package cdst_be_marche.asyncronous;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.repository.ConferenzaRepository;

@Service
public class AsyncProtocolDocuments {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProtocolDocuments.class);
	

}
