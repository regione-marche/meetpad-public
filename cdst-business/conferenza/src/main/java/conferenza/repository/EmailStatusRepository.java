package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EmailStatus;

public interface EmailStatusRepository extends JpaRepository<EmailStatus, String>{

}
