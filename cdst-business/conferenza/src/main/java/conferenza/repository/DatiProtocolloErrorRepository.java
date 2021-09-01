package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.DatiProtocolloError;

public interface DatiProtocolloErrorRepository extends JpaRepository<DatiProtocolloError, Integer>,JpaSpecificationExecutor<DatiProtocolloError>{
	
	@Query(nativeQuery = true, value="select * from cdst.view_error_protocol")
	public List<DatiProtocolloError> findAllProtocolError();
	
	@Query(nativeQuery = true, value="select * from cdst.view_error_protocol where id_conferenza=?")
	public List<DatiProtocolloError> findAllProtocolErrorByConferenza(Integer idConferenza);

}
