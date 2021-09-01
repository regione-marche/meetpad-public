package conferenza.adapder.integrazioni.domus.model;

public interface DomusPratica {
	
	public String getNomeIntestatario();
	public String getCognomeIntestatario();
	public String getCfIntestatario();
	public String getPecIntestatario();
	public String getComuneIntestatario();
	public String getIndirizzoIntestatario();
	
	public String getNomeRichiedente();
	public String getCognomeRichiedente();
	public String getCfRichiedente();
	public String getPecRichiedente();
	
	public String getNomeComune();
	public String getCodiceIstat();
	public String getAddress();
	public String getFoglioMappale();
	
	public String getIdPratica();	
	public String getOggettoPratica();
}
