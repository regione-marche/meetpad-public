package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.ContattoSupporto;

public interface ContattoSupportoRepository extends JpaRepository<ContattoSupporto, Integer>{

	List<ContattoSupporto> findByConferenza(Conferenza conferenza);

}
