package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean._Typological;

@Entity
public class Impresa implements Serializable, _Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7622540887898529925L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_IMPRESA")
	private Integer id;
	
	@Column(name = "DENOMINAZIONE")
	private String denominazione;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "PARTITA_IVA")
	private String partitaIva;
	
	@Column(name = "INDIRIZZO")
	private String indirizzo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FORMA_GIURIDICA")
	private FormaGiuridica formaGiuridica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_REGIONE")
	private Regione regione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROVINCIA")
	private Provincia provincia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_COMUNE")
	private Comune comune;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public FormaGiuridica getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(FormaGiuridica formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public Regione getRegione() {
		return regione;
	}

	public void setRegione(Regione regione) {
		this.regione = regione;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	@Override
	public String getCodice() {
		return getId().toString();
	}

	@Override
	public String getDescrizione() {
		return getDenominazione();
	}

}
