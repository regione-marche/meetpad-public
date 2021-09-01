package conferenza.adapder.integrazioni.paleo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paleo_tipoconferenza_properties")
public class PaleoTipoConferenceProperties {

	private static final long serialVersionUID = 3650316679029356752L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	//è null se il praemtero isdefault is S	
	@Column(name = "ID_TIPO_CONFERENZA")
	private String idTipoConderenza;
	
	//{SVIL,PROD}
	@Column(name = "AMBIENTE")
	private String ambiente;

	@Column(name = "NOME_PROPERTIES")
	private String propertiesName;

	@Column(name = "VALORE_PROPERTIES")
	private String propertiesValue;
	
	@Column(name = "TIPO_VALORE_PROPERTIES")
	private String propertiesValueType;
	
	//
	@Column(name = "TIPO_PROPERTIES")
	private String propertiesType;
	
	
	//{S,N} serve per intercettare il default value
	@Column(name = "ISDEFAULT")
	private String isdefault;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getIdTipoConderenza() {
		return idTipoConderenza;
	}


	public void setIdTipoConderenza(String idTipoConderenza) {
		this.idTipoConderenza = idTipoConderenza;
	}


	public String getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}


	public String getPropertiesName() {
		return propertiesName;
	}


	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}


	public String getPropertiesValue() {
		return propertiesValue;
	}


	public void setPropertiesValue(String propertiesValue) {
		this.propertiesValue = propertiesValue;
	}


	public String getPropertiesValueType() {
		return propertiesValueType;
	}


	public void setPropertiesValueType(String propertiesValueType) {
		this.propertiesValueType = propertiesValueType;
	}


	public String getPropertiesType() {
		return propertiesType;
	}


	public void setPropertiesType(String propertiesType) {
		this.propertiesType = propertiesType;
	}


	public String getIsdefault() {
		return isdefault;
	}


	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	
	
	
}



