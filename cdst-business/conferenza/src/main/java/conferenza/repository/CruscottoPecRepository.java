package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.CruscottoPec;
import java.lang.String;
import java.util.List;

public interface CruscottoPecRepository extends JpaRepository<CruscottoPec, Integer>{
	
	List<CruscottoPec> findByCodiceStatoPec(String codicestatopec);
	
	List<CruscottoPec> findByEmailDestinatarioAndIdConferenzaAndCodiceTipoEventoAndCodiceStatoPec(
			String emaildestinatario, Integer idConferenza, String codiceTipoEvento, String codicestatopec);

}
