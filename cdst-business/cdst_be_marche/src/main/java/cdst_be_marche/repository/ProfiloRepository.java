package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Profilo;
import cdst_be_marche.model.TipoProfilo;

public interface ProfiloRepository extends JpaRepository<Profilo, Integer> {

	Optional<Profilo> findByTipoProfiloAndAmministrazioneProcedente(TipoProfilo tipoProfilo, Ente ente);

}
