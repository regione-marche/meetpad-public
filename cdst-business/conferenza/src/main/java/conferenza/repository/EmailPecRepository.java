package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EmailPec;

public interface EmailPecRepository extends JpaRepository<EmailPec, String>{
	
	Optional<EmailPec> findByEmail(String email);

}
