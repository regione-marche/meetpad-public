package conferenza.DTO;

import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="IdentifiableResponse")
public class RispostaIdentifiableDTO extends Risposta<IdentifiableDTO> {
}