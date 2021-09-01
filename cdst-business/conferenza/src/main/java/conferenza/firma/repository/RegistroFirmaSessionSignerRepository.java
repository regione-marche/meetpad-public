package conferenza.firma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.firma.model.RegistroFirmaSessionSigner;

public interface RegistroFirmaSessionSignerRepository extends JpaRepository<RegistroFirmaSessionSigner, Integer> {
	
	/**
	 * TODO: implementare
	 * @param idDocumento
	 * @return
	 */
	@Query(nativeQuery = true,value="with sessioni as (select max(aa.id) id ,aa.token from registro_firma_sessione_signer ss inner join registro_firma_adapter aa on ss.fk_sessione=aa.token where 1=1 group by aa.token)select distinct  ss.fk_sessione from registro_firma_sessione_signer ss inner join registro_firma_adapter aa on ss.fk_sessione=aa.token  inner join sessioni bb on bb.id=aa.id where 1=1 and aa.id_documento=?" )
	public String getLastSigningSession(Integer idDocumento);

	/**
	 * 
	 * @param sessioneFirma
	 * @return
	 */
	@Query(nativeQuery = true,value="select ss.* from registro_firma_sessione_signer ss where 1=1 and ss.fk_sessione=?" )
	public List<RegistroFirmaSessionSigner> registroFirmaSessionSignerRepository(String sessioneFirma);

	/**
		select distinct ss.*  from registro_firma_sessione_signer ss  left join registro_firma_adapter aa on ss.fk_sessione=aa.token and ss.signer=aa.id_user where 1=1  and aa.id_user is null and ss.fk_sessione='flSpsv26eQ2pYbXt'
	 */
	@Query(nativeQuery = true,value="select ss.* from registro_firma_sessione_signer ss left join registro_firma_adapter aa on ss.fk_sessione=aa.token and ss.signer=aa.id_user and aa.stato = 'UNLOCKED' where Ss.fk_sessione=? and stato is null" )
	public List<RegistroFirmaSessionSigner> getMissingSessionSignerRepository(String sessioneFirma);
	
}
