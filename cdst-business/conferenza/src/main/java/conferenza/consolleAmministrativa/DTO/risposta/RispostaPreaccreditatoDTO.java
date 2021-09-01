package conferenza.consolleAmministrativa.DTO.risposta;

import conferenza.DTO.bean.Risposta;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreaccreditationResponse")
public class RispostaPreaccreditatoDTO extends Risposta<PreaccreditatoDTO> {
}