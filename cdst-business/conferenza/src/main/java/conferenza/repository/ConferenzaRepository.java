package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Impresa;

public interface ConferenzaRepository extends JpaRepository<Conferenza, Integer>, JpaSpecificationExecutor<Conferenza>{
	
	public Conferenza findByIdConferenza (Integer id);

	public List<Conferenza> findByAmministrazioneProcedente(Ente ente);

	public List<Conferenza> findByCodiceFiscaleResponsabileConferenza(String codiceFiscaleResponsabileConferenza);

	public List<Conferenza> findByImpresa(Impresa impresa);

	public List<Conferenza> findByCodiceFiscaleCreatoreConferenza(String codiceFiscaleCreatoreConferenza);
	
	@Query(nativeQuery = true, value="select coalesce(max(c.id_conferenza ),0) as id_conferenza from conferenza c where cognome_richiedente  = ?")
	public Integer findMaxConferenzaByIntestatario(String richiedente);
}
