package conferenza.adapder.integrazioni.domus.model;

public class DomusUbicazioneIntervento {
	
	private String codiceIstatComune;
	private String nomeComune;
	private String particellaTop;
	private String nomeLuogo;
	private int civico;
	private String cap;
	private String foglio;
	private String mappale;
	
	public DomusUbicazioneIntervento() {
		super();
	}

	public DomusUbicazioneIntervento(String codiceIstatComune, String nomeComune, String particellaTop, String nomeLuogo,
			int civico, String cap,
			String foglio, String mappale) {
		super();
		this.codiceIstatComune = codiceIstatComune;
		this.nomeComune = nomeComune;
		this.particellaTop = particellaTop;
		this.nomeLuogo = nomeLuogo;
		this.civico = civico;
		this.cap = cap;
		this.foglio = foglio;
		this.mappale = mappale;
	}

	public String getCodiceIstatComune() {
		return codiceIstatComune;
	}

	public void setCodiceIstatComune(String codiceIstatComune) {
		this.codiceIstatComune = codiceIstatComune;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	public String getParticellaTop() {
		return particellaTop;
	}

	public void setParticellaTop(String particellaTop) {
		this.particellaTop = particellaTop;
	}

	public String getNomeLuogo() {
		return nomeLuogo;
	}

	public void setNomeLuogo(String nomeLuogo) {
		this.nomeLuogo = nomeLuogo;
	}

	public int getCivico() {
		return civico;
	}

	public void setCivico(int civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	
	

}
