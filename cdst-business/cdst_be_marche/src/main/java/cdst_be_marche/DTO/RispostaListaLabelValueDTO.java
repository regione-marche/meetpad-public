package cdst_be_marche.DTO;

import cdst_be_marche.DTO.bean.ListaLabelValue;
import cdst_be_marche.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="LabelValueListResponse")
public class RispostaListaLabelValueDTO extends Risposta<ListaLabelValue> {
}