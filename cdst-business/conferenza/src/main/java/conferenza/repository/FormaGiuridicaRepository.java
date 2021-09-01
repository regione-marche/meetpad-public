package conferenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.FormaGiuridica;

public interface FormaGiuridicaRepository extends JpaRepository<FormaGiuridica, String>{
	
	public Optional<FormaGiuridica> findByCodice(String codice);

}
