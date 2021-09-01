package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.EnteAppoggioCsv;

public interface EnteAppoggioCSVRepository extends JpaRepository<EnteAppoggioCsv, String>{

	Optional<EnteAppoggioCsv> findByCf(String codiceFiscaleEnte);

}
