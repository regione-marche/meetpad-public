package conferenza.adapder.integrazioni.paleo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paleo_fascicolo_tipoconferenza")
public class PaleoFascicoloTipoConference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	//è null se il praemtero isdefault is S	
	@Column(name = "ID_TIPO_CONFERENZA")
	private String idTipoConderenza;

	//è null se il praemtero isdefault is S	
	@Column(name = "ID_FASCICOLO")
	private String idFascicolo;
	
	@Column(name = "NOME_FASCICOLO")
	private String nomeFascicolo;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdTipoConderenza() {
		return idTipoConderenza;
	}

	public void setIdTipoConderenza(String idTipoConderenza) {
		this.idTipoConderenza = idTipoConderenza;
	}

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}
	
	
}
