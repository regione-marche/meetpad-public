package cdst_be_marche.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Impresa;

public interface ImpresaRepository extends JpaRepository<Impresa, Integer>{

	Optional<Impresa> findByCodiceFiscaleOrPartitaIvaIgnoreCase(String cf, String partitaIva);

}
