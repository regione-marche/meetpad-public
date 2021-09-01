package conferenza.protocollo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.protocollo.model.Protocollo;



public interface ProtocolloRepository extends JpaRepository<Protocollo, Integer> {
	
	@Query(nativeQuery = true, value="select p2.*from protocollo p2 inner join documento d2 on p2.id_documento =d2.id_documento where 1 = 1 and p2.id in (select max(p.id ) from protocollo p inner join documento d on p.id_documento  = d.id_documento where d.fk_conferenza = ? and d.id_documento = ? group by p.id_documento, d.fk_conferenza)")
	public Protocollo findProtocolloByConferenzaAndDocumento(int confernza, int idDocumento);

}
