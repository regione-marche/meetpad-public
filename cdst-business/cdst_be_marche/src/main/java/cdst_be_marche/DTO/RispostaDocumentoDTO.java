package cdst_be_marche.DTO;

import cdst_be_marche.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="DocumentResponse")
public class RispostaDocumentoDTO extends Risposta<DocumentoDTO> {
}