package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.FormaGiuridica;

public interface FormaGiuridicaRepository extends JpaRepository<FormaGiuridica, String>{
	
	public Optional<FormaGiuridica> findByCodice(String codice);

}
