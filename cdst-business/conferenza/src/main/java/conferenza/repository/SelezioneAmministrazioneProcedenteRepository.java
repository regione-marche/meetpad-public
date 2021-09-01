package conferenza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import conferenza.model.SelezioneAmministrazioneProcedente;
import conferenza.model.Ente;
import conferenza.model.Utente;

public interface SelezioneAmministrazioneProcedenteRepository extends JpaRepository<SelezioneAmministrazioneProcedente, Integer>, JpaSpecificationExecutor<SelezioneAmministrazioneProcedente> {
	
	public List<SelezioneAmministrazioneProcedente> findByEnte(Ente ente);

	public List<SelezioneAmministrazioneProcedente> findByUtente(Utente utente);

	public Optional<SelezioneAmministrazioneProcedente> findByEnteAndUtente(Ente ente, Utente utente);

}
