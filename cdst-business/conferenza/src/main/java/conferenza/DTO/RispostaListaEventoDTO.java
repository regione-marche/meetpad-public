package conferenza.DTO;

import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="EventsListResponse")
public class RispostaListaEventoDTO extends Risposta<ListaEventoDTO> {
}