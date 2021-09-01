package conferenza.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Evento;
import conferenza.model.Partecipante;
import conferenza.model.Protocollo;

public interface EventoRepository extends JpaRepository<Evento, Integer>, JpaSpecificationExecutor<Evento>{

	List<Evento> findByConferenza(Conferenza conferenza, Pageable pageable);

	List<Evento> findByMittente(Partecipante partecipante);

	List<Evento> findByDocumento(Documento documento);
	
	Evento findByConferenzaAndDocumento(Conferenza conferenza, Documento documento);
	
	List<Evento> findListaByConferenzaAndDocumento(Conferenza conferenza, Documento documento);
	
	@Query(nativeQuery = true, value="select coalesce(max(e.id_evento),0) as id_evento from evento e inner join documento d on e.fk_documento  = d.id_documento inner join conferenza c on d.fk_conferenza  = c.id_conferenza where 1 = 1 and c.id_conferenza  = ? and d.id_documento  = ? group by fk_modifica_data")
	public List<Integer> getMaxEventoByConferenzaAndDocumentoAndModificaData(int confernza, int documento);
}
