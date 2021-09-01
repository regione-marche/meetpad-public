package conferenza.DTO;

import conferenza.DTO.bean.Lista;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ParticipantList")
public class ListaPartecipanteDTO extends Lista<PartecipanteDTO>{}
