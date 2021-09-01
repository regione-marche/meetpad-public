package conferenza.facade;

public enum STATO_DOCUMENTO {

	IN_BOZZA("DRAFT"),
	FIRMATO("SIGNED"),
	RIFIUTATO("REJECTED");
	
	private String status;
	
	STATO_DOCUMENTO(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
}