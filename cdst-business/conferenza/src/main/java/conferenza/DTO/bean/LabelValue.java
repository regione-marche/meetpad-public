package conferenza.DTO.bean;

import io.swagger.annotations.ApiModel;

@ApiModel(value="LabelValue")
public class LabelValue {
	
	private String key;
	private String value;
	
	public LabelValue() {}
	
	public LabelValue(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		String key = getKey() != null ? getKey() : "";
		String value = getValue() != null ? getValue() : "";
		return key.concat(": ").concat(value);
	}

}
