package conferenza.DTO;

import conferenza.DTO.bean.Lista;
import io.swagger.annotations.ApiModel;

@ApiModel(value="EventsList")
public class ListaEventoDTO extends Lista<EventoDTO>{}
