package cdst_be_marche.DTO;

import cdst_be_marche.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "UserResponse")
public class RispostaUtenteDTO extends Risposta<UtenteDTO> {
}