package cdst_be_marche.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Typological implements _Typological, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3380249828794829965L;

	@Id
	@Column(name = "CODICE")
	private String codice;

	@Column(name = "DESCRIZIONE")
	private String descrizione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


}
