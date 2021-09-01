package conferenza.adapder.integrazioni.domus.DTO.bean;

import java.util.List;

import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import com.fasterxml.jackson.annotation.JsonProperty;
import conferenza.DTO.DocumentoDTO;
import conferenza.model.Documento;

public class DomusPraticaDTO {


	@JsonProperty("pratica")
	Pratica pratica;
	
	
	@JsonProperty("documenti")
	List<DocumentoDTO> documenti;
	

	public Pratica getPratica() {
		return pratica;
	}

	public void setPratica(Pratica pratica) {
		this.pratica = pratica;
	}

	public List<DocumentoDTO> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<DocumentoDTO> documenti) {
		this.documenti = documenti;
	}
	
	
	public static DocumentoDTO fillDocumentoDTO(Documento model) {
		DocumentoDTO dto = new DocumentoDTO();
		dto.setId(model.getIdDocumento());
		
		return dto;
	}
	
	
}
