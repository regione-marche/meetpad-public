package conferenza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.RegistroEmailTestata;

public interface RegistroEmailTestataRepository extends JpaRepository<RegistroEmailTestata, Integer> {

	List<RegistroEmailTestata> findByIdMessaggio(String messageId);

}
