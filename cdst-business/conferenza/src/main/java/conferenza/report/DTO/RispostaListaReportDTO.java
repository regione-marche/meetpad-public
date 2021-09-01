package conferenza.report.DTO;

import conferenza.DTO.ListaPartecipanteDTO;
import conferenza.DTO.bean.Risposta;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ReportListResponse")
public class RispostaListaReportDTO extends Risposta<ListaReportDTO> {}
