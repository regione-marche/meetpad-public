package conferenza.report.DTO;

import conferenza.DTO.bean.Lista;

public class ListaReportDTO extends Lista<ReportDTO>{
	
	
	public static ListaReportDTO fillListaReportDTO( java.util.List<ReportDTO> listaDTO) {
		ListaReportDTO outList=new ListaReportDTO();
		for(ReportDTO item : listaDTO ) {
			outList.getList().add(item);			
		}
		outList.setTotalNumber(new Long(listaDTO.size()));
		return outList;
	}	
	
}
