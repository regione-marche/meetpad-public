package conferenza.DTO;

public class ResponsabileCST{

	private String nome;
	private String cognome;
	private boolean firmatario;
	
	public ResponsabileCST(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public boolean isFirmatario() {
		return firmatario;
	}

	public void setFirmatario(boolean firmatario) {
		this.firmatario = firmatario;
	}
	
	
	
}
