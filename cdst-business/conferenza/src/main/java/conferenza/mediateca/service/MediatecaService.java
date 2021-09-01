package conferenza.mediateca.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import conferenza.DTO.ConferenzaAnteprimaDTO;
import conferenza.DTO.PageableDTO;
import conferenza.firma.service.FirmaService;
import conferenza.mediateca.DTO.MediatecaDTO;
import conferenza.mediateca.DTO.bean.ListMediatecaDTO;
import conferenza.mediateca.model.Mediateca;
import conferenza.mediateca.repository.MediatecaRepository;
import conferenza.model.RegistroDocumento;
import conferenza.model.TokenRegistroDocumento;
import conferenza.repository.TokenRegistroDocumentoRepository;
import conferenza.service.RegistroDocumentoService;

@Service
public class MediatecaService {

	private static final Logger logger = LoggerFactory.getLogger(MediatecaService.class.getName());
	
	@Autowired
	MediatecaRepository mediatecaRepository;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;		
	
	/**
	 * 
	 * @param filtro1 = testo da ricercare tra oggetto e nomefile..
	 * @return
	 */
	public ListMediatecaDTO getListRecordMediateca(PageableDTO ricerca, String  filtro1){
		Pageable page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()), mapColonneRicerca(ricerca.getColonnaOrdinamento()));
		Page<Mediateca> toCompletePage = mediatecaRepository.getListRecordMediateca(filtro1, filtro1, page);
		List<Mediateca> toComplete = toCompletePage.getContent();
		toComplete = getListRecordMediateca(toComplete);
		List<MediatecaDTO> listadto = MediatecaDTO.fillMediatecaDTO(toComplete);
		return ListMediatecaDTO.fillListMediatecaDTO(listadto, toCompletePage.getTotalElements());
	}
	
	
	private String mapColonneRicerca(String colonnaOrdinamento) {
		if (colonnaOrdinamento.equals("id")) {
			colonnaOrdinamento = "id_documento";
		}
		if (colonnaOrdinamento.equals("name")) {
			colonnaOrdinamento = "nome";
		}
		if (colonnaOrdinamento.equals("conferenceId")) {
			colonnaOrdinamento = "fk_conferenza";
		}
		if (colonnaOrdinamento.equals("conferenceSubject")) {
			colonnaOrdinamento = "cc.oggetto_determinazione";
		}
		return colonnaOrdinamento;
	}


	private List<Mediateca> getListRecordMediateca(List<Mediateca> toComplete ){
		
		for(Mediateca item: toComplete) {
			RegistroDocumento registro=registroDocumentoService.getRegistroDocumentoByIdDocumento(item.getId_documento());
			Date data=new Date();
			Date today = new Date(); 
			SimpleDateFormat formattedDate = new SimpleDateFormat("yyyyMMdd"); 
			Calendar c = Calendar.getInstance(); 
			c.add(Calendar.DATE, 1);
			//String tomorrow = (String)(formattedDate.format(c.getTime()));
			String url=registroDocumentoService.resolveFileDownloadUri(registro);
			//TokenRegistroDocumento entity =new TokenRegistroDocumento(registro,c.getTime());
			//entity=tokenRegistroDocumentoRepository.save(entity);
			String lsToken=null;//entity.getToken();
			item.setToken(lsToken);
			item.setPath(url);
		}
		
		return toComplete;
	}
}
