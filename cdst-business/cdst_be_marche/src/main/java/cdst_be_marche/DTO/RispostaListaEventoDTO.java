package cdst_be_marche.DTO;

import cdst_be_marche.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="EventsListResponse")
public class RispostaListaEventoDTO extends Risposta<ListaEventoDTO> {
}