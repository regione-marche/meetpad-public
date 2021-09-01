package cdst_be_marche.adapder.integrazioni.suap.persistance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.adapder.integrazioni.suap.model.IntegSuapFrontieraConferenza;

public interface IntegFrontieraConferenzaRepository extends JpaRepository<IntegSuapFrontieraConferenza, Integer>{

	Optional<IntegSuapFrontieraConferenza> findByIdPratica(Integer suapPraticaId);

}
