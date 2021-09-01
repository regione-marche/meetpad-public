package conferenza.adapder.integrazioni.domus.DTO;

import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.domus.DTO.bean.*;
import conferenza.util.DbConst;

import java.util.ArrayList;
import java.util.List;

import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomusDTO {

	
	@JsonProperty("idConferenza")
	private Integer idConferenza;
	
	@JsonProperty("pratiche")
	List<DomusPraticaDTO> pratiche;

	
	
	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public List<DomusPraticaDTO> getPratiche() {
		return pratiche;
	}

	public void setPratiche(List<DomusPraticaDTO> pratiche) {
		this.pratiche = pratiche;
	}

	public static List<IntegFrontieraDTO> fillListIntegFrontieraDTO(String comune,Pratica[] pratiche) {
		
		List<IntegFrontieraDTO>  listya=new ArrayList<IntegFrontieraDTO>();
		// 
		IntegFrontieraDTO frontieraDTO=new IntegFrontieraDTO();
		for(Pratica item:pratiche){
			frontieraDTO=fillIntegFrontieraDTO( comune, item);
			listya.add(frontieraDTO);
		}		
		return listya;
	}
	
	public static IntegFrontieraDTO fillIntegFrontieraDTO(String comune,PraticaExtended pratica, String attivita) {		
		IntegFrontieraDTO frontieraDTO=new IntegFrontieraDTO();
		frontieraDTO.setId_fascicolo(pratica.getCodiceFascicolo());
		frontieraDTO.setId_procedimento(pratica.getIdRichiesta());	
		frontieraDTO.setNome_procedimento(fillNomeProcedimento(comune,pratica, attivita));
			
		return frontieraDTO;
	}

	public static IntegFrontieraDTO fillIntegFrontieraDTO(String comune,Pratica pratica) {		
		IntegFrontieraDTO frontieraDTO=new IntegFrontieraDTO();
		frontieraDTO.setId_fascicolo(pratica.getCodiceFascicolo());
		frontieraDTO.setId_procedimento(pratica.getIdRichiesta());	
	    frontieraDTO.setNome_procedimento(fillNomeProcedimento(comune,pratica));
		
		return frontieraDTO;
	}

	
	
	private static String fillNomeProcedimento(String comune, Pratica item) {
		String nomeprocedimento="[DOMUS] - Comune - "+comune+" - Codice Fascicolo  - "+item.getCodiceFascicolo() +"- Richiesta - "+item.getIdRichiesta();
		return nomeprocedimento;
	}
	
	private static String fillNomeProcedimento(String comune, PraticaExtended item, String attivita) {
		String nomeprocedimento = "";
		if (attivita.equals(DbConst.ATTIVITA_RICOSTRUZIONE_PRIVATA)) {
			nomeprocedimento="[DOMUS] - Intestatario - "+item.getIntestatario() +" - Codice Fascicolo  - "+item.getCodiceFascicolo() +"- Richiesta - "+item.getIdRichiesta();
		} else {
			if (item.getComuneIntervento() != null) {
				nomeprocedimento="[DOMUS] - Comune - "+item.getComuneIntervento().trim()+" - Codice Fascicolo  - "+item.getCodiceFascicolo() +"- Richiesta - "+item.getIdRichiesta();
			} else {
				nomeprocedimento="[DOMUS] - Codice Fascicolo  - "+item.getCodiceFascicolo() +"- Richiesta - "+item.getIdRichiesta();
			}
			
		}
		return nomeprocedimento;
	}
	
	
	
}
