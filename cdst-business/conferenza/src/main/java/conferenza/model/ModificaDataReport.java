package conferenza.model;

public class ModificaDataReport {
	
	private String codiceTipoData;
	private String dataOld;
	private String dataNew;

	public ModificaDataReport(String codiceTipoData, String dataOld, String dataNew) {
		super();
		this.codiceTipoData = codiceTipoData;
		this.dataOld = dataOld;
		this.dataNew = dataNew;
	}
	
	public String getCodiceTipoData() {
		return codiceTipoData;
	}
	public void setCodiceTipoData(String codiceTipoData) {
		this.codiceTipoData = codiceTipoData;
	}
	public String getDataOld() {
		return dataOld;
	}
	public void setDataOld(String dataOld) {
		this.dataOld = dataOld;
	}
	public String getDataNew() {
		return dataNew;
	}
	public void setDataNew(String dataNew) {
		this.dataNew = dataNew;
	}

	
}
