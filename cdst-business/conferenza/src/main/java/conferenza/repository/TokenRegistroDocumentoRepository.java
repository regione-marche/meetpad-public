package conferenza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import conferenza.model.TokenRegistroDocumento;

public interface TokenRegistroDocumentoRepository extends JpaRepository<TokenRegistroDocumento, Integer> {

	TokenRegistroDocumento findByToken(String token);

}
