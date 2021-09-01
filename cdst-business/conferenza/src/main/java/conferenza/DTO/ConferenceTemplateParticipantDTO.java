package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

/**
 * <pre>
 * "participant": {
 *     "competence": [ // se lista vuota => input di tipo testuale
 *         {
 *             "key": "number",
 *             "value": "string"
 *         }
 *     ]
 * }
 * </pre>
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateParticipantDTO")
public class ConferenceTemplateParticipantDTO {

	@JsonProperty("competence")
	private List<LabelValue> competence = new ArrayList<>();

	public List<LabelValue> getCompetence() {
		return competence;
	}

	public void setCompetence(List<LabelValue> competence) {
		this.competence = competence;
	}

}
