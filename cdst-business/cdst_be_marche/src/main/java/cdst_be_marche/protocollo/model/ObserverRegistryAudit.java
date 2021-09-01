package cdst_be_marche.protocollo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "observer_registry_audit")
public class ObserverRegistryAudit   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	Integer id;
	@Column(name ="fk_ObserverRegistry")
	Integer fk_ObserverRegistry;
	@Column(name ="id_evento")
	Integer id_evento;
	@Column(name = "codice_errore")
	private String codiceErrore;
	@Column(name = "descrizione_errore")
	private String descrizioneErrore;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFk_ObserverRegistry() {
		return fk_ObserverRegistry;
	}
	public void setFk_ObserverRegistry(Integer fk_ObserverRegistry) {
		this.fk_ObserverRegistry = fk_ObserverRegistry;
	}
	public Integer getId_evento() {
		return id_evento;
	}
	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}
	
	
}
