package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;

import conferenza.model.Ente;

public interface EnteRepository extends JpaRepository<Ente, Integer>, JpaSpecificationExecutor<Ente>{
	
	List<Ente> findByFlagAmministrazioneProcedente(Boolean flagamministrazioneprocedente);
	List<Ente> findByDescrizioneEnteContainingIgnoreCase(String search, Sort sort);
	List<Ente> findByDescrizioneEnteContainingIgnoreCase(String search);
	List<Ente> findByCodiceFiscaleEnte(String codiceFiscale);
	Optional<Ente> findByCodiceFiscaleEnteAndCodiceUfficio(String codiceFiscaleEnte, String codiceUfficio);
	Optional<Ente> findByCodiceFiscaleEnteAndDescrizioneEnte(String codiceFiscaleEnte, String descrizioneEnte);
	Optional<Ente> findByCodiceFiscaleEnteAndDescrizioneEnteAndCodiceUfficio(String codiceFiscaleEnte, String descrizioneEnte,String codiceUfficio);
	Optional<Ente> findByCodiceIpaAndCodiceUfficio(String codiceIpa, String codiceUfficio);
	
	@Procedure(value = "cdst.updateEnti")
	public String updateEnti();
	
	@Procedure(value = "cdst.updateEntiUffici")
	public String updateEntiUffici();
}
