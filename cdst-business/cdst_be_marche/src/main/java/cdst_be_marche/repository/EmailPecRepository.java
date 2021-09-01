package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.EmailPec;

public interface EmailPecRepository extends JpaRepository<EmailPec, String>{
	
	Optional<EmailPec> findByEmail(String email);

}
