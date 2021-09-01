package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EmailPartecipante;

public interface EmailPartecipanteRepository extends JpaRepository<EmailPartecipante, Integer>{

}
