package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Protocollo;

public interface  ProtocolloNoBatchRepository extends JpaRepository<Protocollo, Integer>,JpaSpecificationExecutor<Protocollo>{

	@Query(nativeQuery = true, value="select p2.*from protocollo p2 inner join documento d2 on p2.id_documento =d2.id_documento where 1 = 1 and p2.id in (select max(p.id ) from protocollo p inner join documento d on p.id_documento  = d.id_documento where d.fk_conferenza = ? and p.fk_stati_protocollo IN ('5') group by p.id_documento, d.fk_conferenza)")
	public List<Protocollo> findProtocolloByConferenza(int confernza);
	
	@Query(nativeQuery = true, value="select p2.* from protocollo p2 inner join documento d2 on p2.id_documento =d2.id_documento where 1 = 1 and d2.fk_conferenza = ? and p2.fk_stati_protocollo in ('1','3')")
	public List<Protocollo> findProtocolloNoErrorByConferenza(int confernza);
	
	@Query(nativeQuery = true, value="select p2.* from protocollo p2 where p2.id = ?")
	public Protocollo findByIdProtocollo (Integer id);
	
	@Query(nativeQuery = true, value="select p2.*from protocollo p2 inner join documento d2 on p2.id_documento =d2.id_documento where 1 = 1 and p2.id in (select max(p.id ) from protocollo p inner join documento d on p.id_documento  = d.id_documento where d.fk_conferenza = ? and d.id_documento = ? and p.fk_stati_protocollo IN ('5') group by p.id_documento, d.fk_conferenza)")
	public Protocollo findErroryIdConferenzaAndDocumento (Integer conferenza, Integer documento);
}
