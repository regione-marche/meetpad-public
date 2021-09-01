package conferenza.DTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Identifiable")
public class IdentifiableDTO {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
