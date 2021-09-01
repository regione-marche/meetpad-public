package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.Ente;
import conferenza.model.Profilo;
import conferenza.model.TipoProfilo;

public interface ProfiloRepository extends JpaRepository<Profilo, Integer> {

	Optional<Profilo> findByTipoProfiloAndAmministrazioneProcedente(TipoProfilo tipoProfilo, Ente ente);

	List<Profilo> findByAmministrazioneProcedente(Ente ente);

	List<Profilo> findByTipoProfilo(TipoProfilo tipoProfilo);

	Optional<Profilo> findByAmministrazioneProcedenteAndTipoProfilo(Ente ammProcendente, TipoProfilo tipoProfilo);

}
