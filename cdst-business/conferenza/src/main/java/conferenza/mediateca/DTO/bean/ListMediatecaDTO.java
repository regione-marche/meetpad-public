package conferenza.mediateca.DTO.bean;

import conferenza.DTO.bean.Lista;
import conferenza.mediateca.DTO.MediatecaDTO;



public class ListMediatecaDTO extends Lista<MediatecaDTO>{

	public static ListMediatecaDTO fillListMediatecaDTO( java.util.List<MediatecaDTO> listaDTO, Long totalNumber) {
		ListMediatecaDTO outList=new ListMediatecaDTO();
		for(MediatecaDTO item : listaDTO ) {
			outList.getList().add(item);			
		}
		outList.setTotalNumber(totalNumber);
		return outList;
	}	
}
