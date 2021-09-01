package conferenza.documentazionecondivisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.documentazionecondivisa.model.OOUser;



public interface OOUserRepository extends JpaRepository<OOUser, Integer> {

	//select rd.id as id_registro from registro_documento rd inner join documento dd on dd.id_documento=rd.fk_documento inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza where tipo='PALEO' and rif_esterno='5997' and cc.id_conferenza='239'
	@Query(value="select * from oo_user where  cf_user=?" ,nativeQuery = true)
	OOUser getOOUserByCF(String codiceFiscale);
}
