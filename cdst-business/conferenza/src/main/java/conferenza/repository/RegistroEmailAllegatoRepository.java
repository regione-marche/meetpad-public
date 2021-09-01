package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.RegistroEmailAllegato;
import conferenza.model.RegistroEmailTestata;
import java.util.List;

public interface RegistroEmailAllegatoRepository extends JpaRepository<RegistroEmailAllegato, Integer>{
	
	List<RegistroEmailAllegato> findByRegistroEmailTestata(RegistroEmailTestata registroemailtestata);

}
