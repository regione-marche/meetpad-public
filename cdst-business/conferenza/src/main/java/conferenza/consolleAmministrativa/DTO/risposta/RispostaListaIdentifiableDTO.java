package conferenza.consolleAmministrativa.DTO.risposta;

import conferenza.DTO.bean.Risposta;
import conferenza.consolleAmministrativa.DTO.lista.ListaIdentifiableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="IdentifiableListResponse")
public class RispostaListaIdentifiableDTO extends Risposta<ListaIdentifiableDTO> {
}