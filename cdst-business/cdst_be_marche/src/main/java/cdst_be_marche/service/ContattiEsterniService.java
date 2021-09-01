package cdst_be_marche.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.ContattoSupportoDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaContattoSupportoDTO;
import cdst_be_marche.builder.ConferenzaBuilder;
import cdst_be_marche.model.ContattoSupporto;
import cdst_be_marche.repository.ContattoSupportoRepository;

@Transactional
@Service
public class ContattiEsterniService extends _BaseService {
	
	@Autowired
	ContattoSupportoRepository contattoSuppRepo;
	
	@Autowired
	ConferenzaBuilder confBuilder;
	
	@Autowired
	ConferenzaService confService;

	public ListaContattoSupportoDTO findAllSupportContacts(Integer id) {
		List<ContattoSupporto> listaContatti = this.contattoSuppRepo
				.findByConferenza(this.confService.findConferenzaByIdFiltrata(id));
		ListaContattoSupportoDTO listaContattiDTO = new ListaContattoSupportoDTO();
		listaContatti.stream().forEach(
				contatto -> listaContattiDTO.getList().add(this.confBuilder.buildContattoSupportoDTO(contatto)));
		listaContattiDTO.setTotalNumber(new Long (listaContattiDTO.getList().size()));
		return listaContattiDTO;
	}

	public IdentifiableDTO creaSupportoContatto(ContattoSupportoDTO contattoDTO, Integer idConferenza, Integer idContatto) {
		ContattoSupporto contatto = null;
		if (idContatto != null) {
			contatto = this.contattoSuppRepo.findById(idContatto).orElse(null);
		}
		ContattoSupporto saved =  this.confBuilder.buildContattoSupporto(contattoDTO, contatto, idConferenza);
		saved = this.contattoSuppRepo.save(saved);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdContattoSupporto());
		return identifiableDTO;
	}

	public String eliminaContattoSupporto(Integer id) {
		ContattoSupporto deleted = this.contattoSuppRepo.findById(id).orElse(null);
		String risposta = "nessun contatto da eliminare";
		if (deleted != null) {
			this.contattoSuppRepo.delete(deleted);
			risposta = "ok";
		}
		return risposta;
		
	}
	
	

}
