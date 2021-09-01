package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.RegistroEmailTestata;

public interface RegistroEmailTestataRepository extends JpaRepository<RegistroEmailTestata, Integer> {

	List<RegistroEmailTestata> findByIdMessaggio(String messageId);

}
