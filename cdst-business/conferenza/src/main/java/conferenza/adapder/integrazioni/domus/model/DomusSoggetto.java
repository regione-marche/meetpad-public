package conferenza.adapder.integrazioni.domus.model;


public class DomusSoggetto {
	
	private String nome;
	private String cognome;
	private String cf;
	private String pec;
	private String denominazioneComune;
	private String indirizzo;
	
	public DomusSoggetto() {
		super();
	}
	
	public DomusSoggetto(String nome, String cognome, String cf, String pec) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.pec = pec;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getDenominazioneComune() {
		return denominazioneComune;
	}

	public void setDenominazioneComune(String denominazioneComune) {
		this.denominazioneComune = denominazioneComune;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	
	

}
