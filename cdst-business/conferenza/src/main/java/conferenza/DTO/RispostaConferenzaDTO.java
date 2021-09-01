package conferenza.DTO;

import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ConferenceResponse")
public class RispostaConferenzaDTO extends Risposta<ConferenzaDTO> {
}