package conferenza.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.EnteDTO;
import conferenza.model.Ente;
import conferenza.repository.ComuneRepository;
import conferenza.repository.EnteTipologiaAmministrativaRepository;
import conferenza.repository.EnteTipologiaIstatRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegioneRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.util.DbConst;

@Component
public class EnteBuilder extends _BaseBuilder{
	
	@Autowired
	RegioneRepository regioneRepo;
	
	@Autowired
	ProvinciaRepository provinciaRepo;
	
	@Autowired
	ComuneRepository comuneRepo;
	
	@Autowired
	EnteTipologiaIstatRepository enteTipoIstatRepo;
	
	@Autowired
	EnteTipologiaAmministrativaRepository enteTipoAmmRepo;
		
	public EnteDTO enteToEnteDTO(Ente ente) {
		EnteDTO enteDTO = null;
		if (ente != null) {
			enteDTO = new EnteDTO();
			enteDTO.setNome(ente.getDescrizioneEnte());
			enteDTO.setDescrizione(ente.getDescrizioneEnte());
			enteDTO.setCodiceFiscale(ente.getCodiceFiscaleEnte());
			enteDTO.setPec(ente.getPecEnte());
			enteDTO.setId(ente.getIdEnte());
			enteDTO.setFlagAmmProc(ente.getFlagAmministrazioneProcedente());
			enteDTO.setFlagAmmPrincipale(ente.getFlagAmministrazionePrincipale());
			enteDTO.setCodiceIpa(ente.getCodiceIpa());
			enteDTO.setCodiceUfficio(ente.getCodiceUfficio());
			enteDTO.setRegione(createNotNullLabelValue(ente.getRegione()));
			enteDTO.setProvincia(createNotNullLabelValue(ente.getProvincia()));
			enteDTO.setComune(createNotNullLabelValue(ente.getComune()));
			enteDTO.setTipologiaIstat(createNotNullLabelValue(ente.getEnteTipoIstat()));
			enteDTO.setTipologiaAmm(createNotNullLabelValue(ente.getEnteTipoAmm()));
			enteDTO.setDescrizioneComune(ente.getDescrizioneComune());
		}
		return enteDTO;
	}
	
	public Ente enteDTOToEnte(EnteDTO enteDTO) {
		Ente ente = new Ente();
		ente.setDescrizioneEnte(enteDTO.getDescrizione());
		ente.setCodiceFiscaleEnte(enteDTO.getCodiceFiscale());
		ente.setFlagAmministrazioneProcedente(enteDTO.getFlagAmmProc());
		ente.setFlagAmministrazionePrincipale(enteDTO.getFlagAmmPrincipale());
		if (enteDTO.getId() != null) {
			ente.setIdEnte(enteDTO.getId());
		}
		ente.setPecEnte(enteDTO.getPec());
		ente.setCodiceIpa(enteDTO.getCodiceIpa());
		ente.setCodiceUfficio(enteDTO.getCodiceUfficio());
		ente.setDescrizioneComune(enteDTO.getDescrizioneComune());
		if (enteDTO.getTipologiaIstat() != null) {
			ente.setEnteTipoIstat(enteTipoIstatRepo.findById(enteDTO.getTipologiaIstat().getKey()).orElse(null));
		}
		if (enteDTO.getTipologiaAmm() != null) {
			ente.setEnteTipoAmm(enteTipoAmmRepo.findById(enteDTO.getTipologiaAmm().getKey()).orElse(null));
		}
		if(enteDTO.getRegione() != null) {
			ente.setRegione(regioneRepo.findById(enteDTO.getRegione().getKey()).orElse(null));
		}
		if(enteDTO.getProvincia() != null) {
			ente.setProvincia(provinciaRepo.findById(enteDTO.getProvincia().getKey()).orElse(null));
		}
		if(enteDTO.getComune() != null) {
			ente.setComune(comuneRepo.findById(enteDTO.getComune().getKey()).orElse(null));
		}
		return ente;
	}

}
