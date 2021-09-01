package cdst_be_marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.Documento;
import cdst_be_marche.model.RegistroDocumento;

public interface RegistroDocumentoRepository extends JpaRepository<RegistroDocumento, Integer> {
	List<RegistroDocumento> findByDocumento(Documento documento);
}
