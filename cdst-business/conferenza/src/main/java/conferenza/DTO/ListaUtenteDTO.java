package conferenza.DTO;

import conferenza.DTO.bean.Lista;
import io.swagger.annotations.ApiModel;

@ApiModel(value="UserList")
public class ListaUtenteDTO extends Lista<UtenteDTO>{}
