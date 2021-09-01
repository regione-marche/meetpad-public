package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.Ente;
import conferenza.model.SelezioneTipologiaConferenza;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.model.Utente;

public interface SelezioneTipologiaConferenzaRepository extends JpaRepository<SelezioneTipologiaConferenza, Integer>, JpaSpecificationExecutor<SelezioneTipologiaConferenza> {

	public List<SelezioneTipologiaConferenza> findByUtente(Utente utente);

}
