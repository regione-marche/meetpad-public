package conferenza.DTO;

import conferenza.DTO.bean.ListaLabelValue;
import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="LabelValueListResponse")
public class RispostaListaLabelValueDTO extends Risposta<ListaLabelValue> {
}