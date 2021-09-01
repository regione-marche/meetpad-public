package cdst_be_marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.EmailStatus;

public interface EmailStatusRepository extends JpaRepository<EmailStatus, String>{

}
