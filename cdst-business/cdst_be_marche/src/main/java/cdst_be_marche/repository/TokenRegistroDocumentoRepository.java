package cdst_be_marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.TokenRegistroDocumento;

public interface TokenRegistroDocumentoRepository extends JpaRepository<TokenRegistroDocumento, Integer> {

	TokenRegistroDocumento findByToken(String token);

}
