package conferenza.firma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_firma")
public class TipoFirma    implements Serializable {
	
	@Id
	@Column(name ="id")
    Integer id ;
	@Column(name ="tipofirma")
	String tipofirma ;
	@Column(name ="descrizione")
	String descrizione ;
	@Column(name ="marshalling")
	String marshalling;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipofirma() {
		return tipofirma;
	}
	public void setTipofirma(String tipofirma) {
		this.tipofirma = tipofirma;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getMarshalling() {
		return marshalling;
	}
	public void setMarshalling(String marshalling) {
		this.marshalling = marshalling;
	}
	
	
	
	
}
