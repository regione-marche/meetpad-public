package conferenza.DTO;

import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "UserResponse")
public class RispostaUtenteDTO extends Risposta<UtenteDTO> {
}