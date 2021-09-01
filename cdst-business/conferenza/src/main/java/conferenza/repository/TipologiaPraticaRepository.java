package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TipologiaPratica;

public interface TipologiaPraticaRepository extends JpaRepository<TipologiaPratica, String>{

	TipologiaPratica findByCodice(String codice);

}
