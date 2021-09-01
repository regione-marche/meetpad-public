package cdst_be_marche.model;

import java.io.Serializable;
import javax.persistence.*;

import cdst_be_marche.model.bean._Typological;

import java.util.List;

/**
 * The persistent class for the ente database table.
 * 
 */
@Entity
public class Ente implements Serializable, _Typological{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183691904614000001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ENTE")
	private Integer idEnte;

	@Column(name = "DESCRIZIONE_ENTE")
	private String descrizioneEnte;

	@Column(name = "PEC_ENTE")
	private String pecEnte;

	@Column(name = "CODICE_FISCALE_ENTE")
	private String codiceFiscaleEnte;

	@Column(name = "FLAG_AMM_PROCEDENTE")
	private Boolean flagAmministrazioneProcedente;

	@Column(name = "FLAG_AMMINISTRAZIONE_PRINCIPALE")
	private Boolean flagAmministrazionePrincipale;
	
	@OneToMany(mappedBy = "ente")
	private List<Partecipante> partecipantes;

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}

	public String getPecEnte() {
		return pecEnte;
	}

	public void setPecEnte(String pecEnte) {
		this.pecEnte = pecEnte;
	}

	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}

	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}

	public Boolean getFlagAmministrazioneProcedente() {
		return flagAmministrazioneProcedente;
	}

	public void setFlagAmministrazioneProcedente(Boolean flagAmministrazioneProcedente) {
		this.flagAmministrazioneProcedente = flagAmministrazioneProcedente;
	}

	public Boolean getFlagAmministrazionePrincipale() {
		return flagAmministrazionePrincipale;
	}

	public void setFlagAmministrazionePrincipale(Boolean flagAmministrazionePrincipale) {
		this.flagAmministrazionePrincipale = flagAmministrazionePrincipale;
	}

	public List<Partecipante> getPartecipantes() {
		return this.partecipantes;
	}

	public void setPartecipantes(List<Partecipante> partecipantes) {
		this.partecipantes = partecipantes;
	}

	public Partecipante addPartecipante(Partecipante partecipante) {
		getPartecipantes().add(partecipante);
		partecipante.setEnte(this);

		return partecipante;
	}

	public Partecipante removePartecipante(Partecipante partecipante) {
		getPartecipantes().remove(partecipante);
		partecipante.setEnte(null);

		return partecipante;
	}

	@Override
	public String getCodice() {
		return getIdEnte().toString();
	}

	@Override
	public String getDescrizione() {
		return getDescrizioneEnte();
	}

}