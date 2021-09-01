package conferenza.builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import conferenza.DTO.ProtocolloDTO;
import conferenza.consolleAmministrativa.DTO.ProtocolloPreviewDTO;
import conferenza.model.Protocollo;
import conferenza.util.DbConst;


@Component
public class ProtocollazoneBuilder extends _BaseBuilder {

	public ProtocolloDTO buildProtocolloDTO(Protocollo protocollo, String evento) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

		ProtocolloDTO protocolloDTO = new ProtocolloDTO();

		protocolloDTO.setIdProtocollo(protocollo.getId());
		protocolloDTO.setIdConferenza(protocollo.getDocumento().getConferenza().getIdConferenza());
		protocolloDTO.setError(protocollo.getError());
		protocolloDTO.setDocumentName(protocollo.getDocumento().getNome());
		protocolloDTO.setEvento(evento);
		protocolloDTO.setIdDocNumber(protocollo.getDocumento().getIdDocumento());
		protocolloDTO.setProtocolNumber(protocollo.getNumeroProtocollo());
		protocolloDTO.setProtocolDate(protocollo.getDataProtocollo());
		protocolloDTO.setProtocolState(decodeProtocolState(protocollo.getStatoProtocollo()));
		
		return protocolloDTO;
	}
	
	public ProtocolloPreviewDTO buildProtocolloPreviewDTO(Protocollo protocollo, String evento) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

		ProtocolloPreviewDTO protocolloDTO = new ProtocolloPreviewDTO();

		protocolloDTO.setIdProtocollo(protocollo.getId());
		protocolloDTO.setIdConferenza(protocollo.getDocumento().getConferenza().getIdConferenza());
		protocolloDTO.setError(protocollo.getError());
		protocolloDTO.setDocumentName(protocollo.getDocumento().getNome());
		protocolloDTO.setEvento(evento);
		protocolloDTO.setIdDocNumber(protocollo.getDocumento().getIdDocumento());
		protocolloDTO.setProtocolNumber(protocollo.getNumeroProtocollo());
		protocolloDTO.setProtocolDate(protocollo.getDataProtocollo());
		protocolloDTO.setProtocolState(decodeProtocolState(protocollo.getStatoProtocollo()));
		
		return protocolloDTO;
	}

	private String decodeProtocolState(int statoProtocollo) {
		String result = "";
		switch (statoProtocollo) {
			case DbConst.STATO_PROTOCOLLO_DA_PROTOCOLLARE_ESTERNAMENTE:
				result = DbConst.STATO_PROTOCOLLO_DA_PROTOCOLLARE_ESTERNAMENTE_DESC;
				break;
			case DbConst.STATO_PROTOCOLLO_PROTOCOLLO_SOLO_CONFERENZA:
				result = DbConst.STATO_PROTOCOLLO_PROTOCOLLO_SOLO_CONFERENZA_DESC;
				break;
			case DbConst.STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO:
				result = DbConst.STATO_PROTOCOLLO_PROTOCOLLAZIONE_IN_CORSO_DESC;
				break;
			case DbConst.STATO_PROTOCOLLO_PROTOCOLLATA:
				result = DbConst.STATO_PROTOCOLLO_PROTOCOLLATA_DESC;
				break;
			case DbConst.STATO_PROTOCOLLO_IN_ERRORE:
				result = DbConst.STATO_PROTOCOLLO_IN_ERRORE_DESC;
				break;
		}
		return result;
	}
}
