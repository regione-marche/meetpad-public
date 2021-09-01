package conferenza.adapder.integrazioni.paleo.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.DocumentAdapterDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;

public class PaleoDocumentAllegatiAdapterDTO extends DocumentAdapterDTO {

	
	@JsonProperty("ID")
	private Integer id;
	
	//Intrinsecamente contiene il tipo conferenza
	//se è null non è stata creata la conferenza
	//..registroDocummenti>idConderenza>fk_tipollogica_conferenza
	@JsonProperty("ID_ALLEGATO")
	private Integer idAllegato;
	
	//questo serve per il download
	@JsonProperty("FK_PALEO_ADAPTER")
	private Integer fkPaleoAdapter;

	//questo serve per il download
	@JsonProperty("NOME_ALLEGATO")
	private String nomeAllegato;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Integer getFkPaleoAdapter() {
		return fkPaleoAdapter;
	}

	public void setFkPaleoAdapter(Integer fkPaleoAdapter) {
		this.fkPaleoAdapter = fkPaleoAdapter;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public static List<PaleoDocumentAllegatiAdapterDTO> fillListDocumentAllegatiAdapterDTO(
			List<IntegFrontieraDocumentDTO> listaDocumenti) {
		List<PaleoDocumentAllegatiAdapterDTO> lista=new ArrayList<PaleoDocumentAllegatiAdapterDTO>();
		for(IntegFrontieraDocumentDTO item: listaDocumenti) {
			PaleoDocumentAllegatiAdapterDTO allegato=new PaleoDocumentAllegatiAdapterDTO();
			allegato.setId(item.getId());
			allegato.setName(item.getNome());
			lista.add(allegato);
		}
		
		return lista;
	}
	
	
	
}
