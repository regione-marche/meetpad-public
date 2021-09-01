package conferenza.chatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.chatbot.model.Richieste;

public interface RichiesteRepository extends JpaRepository<Richieste, Integer>  {

}
