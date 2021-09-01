package conferenza.adapder.integrazioni.paleo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.adapder.integrazioni.createconference.model.IntegFrontieraConferenza;
import conferenza.model.Conferenza;



public interface IntegPaleoFrontieraConferenzaRepository  extends JpaRepository<IntegFrontieraConferenza, Integer>{
	
	IntegFrontieraConferenza findByConferenza(Conferenza conferenza);
	
}
