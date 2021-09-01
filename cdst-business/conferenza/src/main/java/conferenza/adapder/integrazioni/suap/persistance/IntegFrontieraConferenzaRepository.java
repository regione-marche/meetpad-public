package conferenza.adapder.integrazioni.suap.persistance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.adapder.integrazioni.suap.model.IntegSuapFrontieraConferenza;

public interface IntegFrontieraConferenzaRepository extends JpaRepository<IntegSuapFrontieraConferenza, Integer>{

	Optional<IntegSuapFrontieraConferenza> findByIdPratica(Integer suapPraticaId);

}
