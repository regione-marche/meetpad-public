package cdst_be_marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.TipologiaPratica;

public interface TipologiaPraticaRepository extends JpaRepository<TipologiaPratica, String>{

	TipologiaPratica findByCodice(String codice);

}
