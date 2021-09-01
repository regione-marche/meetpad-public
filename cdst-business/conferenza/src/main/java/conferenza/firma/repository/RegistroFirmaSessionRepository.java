package conferenza.firma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.RegistroFirmaSessione;

public interface RegistroFirmaSessionRepository  extends JpaRepository<RegistroFirmaSessione, Integer> {

	//Lista delle sessioni di firma..per un dato documento..
	@Query(nativeQuery = true,value="select * from registro_firma_adapter_sessione where fk_documento=?")
	public List<RegistroFirmaSessione> getListRegistroFirmaSessioni(Integer idDocumento);
	
	//solo una è la riga per cui dt_sessione_end is null
	@Query(nativeQuery = true,value="select * from registro_firma_adapter_sessione where fk_documento=? and dt_sessione_end is null")
	public RegistroFirmaSessione getLastRegistroFirmaSessioni(Integer idDocumento);
	
	@Query(nativeQuery = true,value="select * from registro_firma_adapter_sessione where token=? ")
	public RegistroFirmaSessione getRegistroFirmaSessioniByToken(String  token);
	
	
	
}
