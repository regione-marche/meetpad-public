package conferenza.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Attivita;
import conferenza.model.MailHashes;
import conferenza.model.TipologiaPratica;
import conferenza.model.bean._Typological;

public interface MailHashesRepository extends JpaRepository<MailHashes, String>{

	List<MailHashes> findAll();

}
