package conferenza.chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import conferenza.chatbot.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
