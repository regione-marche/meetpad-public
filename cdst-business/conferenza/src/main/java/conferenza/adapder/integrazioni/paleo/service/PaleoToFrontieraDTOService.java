package conferenza.adapder.integrazioni.paleo.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraEntiDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.model.*;
import conferenza.adapder.integrazioni.paleo.repository.PaleoFascicoloTipoConferenceRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAdapterRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAllegatiAdapterRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoTipoConferencePropertiesRepository;

@Service
public class PaleoToFrontieraDTOService {

	@Autowired 
	PaleoRegistryAdapterRepository paleoRegistryAdapterRepository;

	@Autowired 
	PaleoRegistryAllegatiAdapterRepository paleoRegistryAllegatiAdapterRepository;
	
	@Autowired 
	PaleoFascicoloTipoConferenceRepository paleoFascicoloTipoConferenceRepository; 
	
	@Autowired 
	PaleoTipoConferencePropertiesRepository conferencePropertiesRepository; 
	
	@Autowired 
	PaleoClientConfiguration paleoClientConfiguration;
	
	public String getByIdTipoConferenzaAndNomeProperties(String idTipoConferenza,String nomeProperties, String defaultValue ) {
		String retVal=defaultValue;
		String ambiente =paleoClientConfiguration.getAmbiente();
		List<PaleoTipoConferenceProperties> listproperties=conferencePropertiesRepository.getByIdTipoConferenzaAndNomePropertiesAndAmbiente(idTipoConferenza,nomeProperties, ambiente );
		if(listproperties!=null && !listproperties.isEmpty())
			retVal=listproperties.get(0).getPropertiesValue();
		return retVal;
	}
	
	private String getTipoConferencePaleoEntityProperties(String idTipoConferenza,String nomeProperties, String defaultValue ) {
		String retVal=defaultValue;
		String ambiente =paleoClientConfiguration.getAmbiente();
		List<PaleoTipoConferenceProperties> listproperties=conferencePropertiesRepository.getParameterByTipoConferenceSpecializzazione(idTipoConferenza,nomeProperties, ambiente );
		if(listproperties!=null && !listproperties.isEmpty())
			retVal=listproperties.get(0).getPropertiesValue();
		return retVal;
	}
	
	private String getTipoConferencePaleoEntityProperties(String idTipoConferenza,String nomeProperties ) {
		String retVal=null;
		String ambiente =paleoClientConfiguration.getAmbiente();
		List<PaleoTipoConferenceProperties> listproperties=conferencePropertiesRepository.getParameterByTipoConferenceSpecializzazione(idTipoConferenza,nomeProperties, ambiente );
		if(listproperties!=null && !listproperties.isEmpty())
			retVal=listproperties.get(0).getPropertiesValue();
		return retVal;
	}
	
	private String getTipoConferenzaByFascicolo(String fascicolo,String tipoConferenza) {
		return paleoFascicoloTipoConferenceRepository.getTipoConferenza(fascicolo,tipoConferenza);		
	}
	
	/**
	 * 
	 * @param fascicolo
	 * @return
	 */
	public List<IntegFrontieraDocumentDTO> listIntegFrontieraDocumentDTO(String fascicolo,Integer idFascicoloTipoConferenza) {
		return listIntegFrontieraDocumentDTO(fascicolo, false);
	}
	
	public List<IntegFrontieraDocumentDTO> listIntegFrontieraDocumentDTO(String fascicolo, boolean getOnlyNotStored) {
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();
		
		List<PaleoRegistryAllegatiAdapter> listaAdapter;
		if(getOnlyNotStored)
			listaAdapter = paleoRegistryAllegatiAdapterRepository.getNotStoredAttachments(fascicolo);
		else
			listaAdapter = paleoRegistryAllegatiAdapterRepository.getlistAllegatiPaleoAdapter(fascicolo);
		
		for(PaleoRegistryAllegatiAdapter item: listaAdapter) {
			IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
			//Per la Frontiera serve la chiave del PaleoRegistryAdapter
			docFrontieraDTO.setId(item.getId());		
			//dovrebbe servire per costr
			docFrontieraDTO.setNome(item.getNomeAllegato());
			documentFrontieraDTO.add(docFrontieraDTO);
		}
		
		return documentFrontieraDTO;
	}
	
	
	/**
	 * 
	 * @param fascicolo
	 * @return
	 */
	public List<IntegFrontieraDocumentDTO> listAllegatiInIntegFrontieraDocumentDTO(String fascicolo) {
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();
		List<PaleoRegistryAllegatiAdapter>  listaAllegatiAdapter=paleoRegistryAllegatiAdapterRepository.getlistAllegatiPaleoAdapter(fascicolo);
		for(PaleoRegistryAllegatiAdapter item: listaAllegatiAdapter) {
			IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
			//Per la Frontiera serve la chiave del PaleoRegistryAdapter
			docFrontieraDTO.setId(item.getId());		
			//dovrebbe servire per costr
			//docFrontieraDTO.setId_paleo(String.valueOf(item.getIdPaleoDoc()));
			docFrontieraDTO.setNome(item.getNomeAllegato());
			documentFrontieraDTO.add(docFrontieraDTO);
		}
		
		return documentFrontieraDTO;
	}	
	
	/**
	 * Esempio: utilizzo quando si passino dei filtri
	 * @param idAdapter
	 * @return
	 */
	public List<IntegFrontieraDocumentDTO> listAllegatiInIntegFrontieraByidAdapter(Integer idAdapter) {
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();
		List<PaleoRegistryAllegatiAdapter>  listaAllegatiAdapter=paleoRegistryAllegatiAdapterRepository.getlistAllegatiPaleoByIdAdapter(idAdapter);
		for(PaleoRegistryAllegatiAdapter item: listaAllegatiAdapter) {
			IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
			//Per la Frontiera serve la chiave del PaleoRegistryAdapter
			docFrontieraDTO.setId(item.getId());		
			//dovrebbe servire per costr
			//docFrontieraDTO.setId_paleo(String.valueOf(item.getIdPaleoDoc()));
			docFrontieraDTO.setNome(item.getNomeAllegato());
			documentFrontieraDTO.add(docFrontieraDTO);
		}		
		return documentFrontieraDTO;
	}	
	
	/**
	 * 
	 * @param listaAllegati
	 * @return
	 */
	public List<IntegFrontieraDocumentDTO> listAllegatiInIntegFrontieraByFilterList(List<IntegFrontieraDocumentDTO> listaAllegati){
		List<IntegFrontieraDocumentDTO> documentFrontieraDTO=new ArrayList<>();
		for(IntegFrontieraDocumentDTO itemAllegati: listaAllegati) {
			List<PaleoRegistryAllegatiAdapter>  listaAllegatiAdapter=paleoRegistryAllegatiAdapterRepository.getlistAllegatiPaleoByIdAdapter(itemAllegati.getId());
			for(PaleoRegistryAllegatiAdapter item: listaAllegatiAdapter) {
				IntegFrontieraDocumentDTO docFrontieraDTO=new IntegFrontieraDocumentDTO();
				docFrontieraDTO.setId(item.getId());		
				docFrontieraDTO.setNome(item.getNomeAllegato());
				documentFrontieraDTO.add(docFrontieraDTO);
			}							
		}
		return documentFrontieraDTO;
	}
	
	
	
	private String getTipoConferencePaleoEntityPropertiesRaggruppamento(String idTipoConferenza, String nomeProperties,String raggruppamento) {
			String retVal=null;
			String ambiente =paleoClientConfiguration.getAmbiente();
			List<PaleoTipoConferenceProperties> listproperties=conferencePropertiesRepository.getParameterByTipoConferenceSpecializzazioneRaggruppamento(idTipoConferenza,nomeProperties, ambiente,raggruppamento );
			if(listproperties!=null && !listproperties.isEmpty())
				retVal=listproperties.get(0).getPropertiesValue();
			return retVal;
	}

	
	public Optional<IntegFrontieraEntiDTO> getIntegFrontieraEntiDTO(String fascicolo,String tipoConferenza,String raggruppamento) {
		IntegFrontieraEntiDTO enteDTO=new IntegFrontieraEntiDTO();
		
		String sEnteId = getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_id" ,raggruppamento);
		if(sEnteId==null) {
			return Optional.empty();
		}
		BigInteger ente_id=new BigInteger(sEnteId) ;
		String ente_nome=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_nome",raggruppamento );
		String ente_pec=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_pec" ,raggruppamento);
		String ente_codice_comune=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_codice_comune" ,raggruppamento);
		String ente_codice_provincia=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_codice_provincia" ,raggruppamento);
		String ente_codice_regione=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_codice_regione" ,raggruppamento);
		String ente_comune=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_comune" ,raggruppamento);
		String ente_provincia=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_provincia" ,raggruppamento);
		String ente_regione=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenzaByFascicolo(fascicolo,tipoConferenza) ,"ente_regione" ,raggruppamento);
		
		
		enteDTO.setId(ente_id);
		enteDTO.setEnte_codice_comune(ente_codice_comune);
		enteDTO.setEnte_codice_provincia(ente_codice_provincia);
		enteDTO.setEnte_codice_regione(ente_codice_regione);
		enteDTO.setEnte_comune(ente_comune);
		enteDTO.setEnte_nome(ente_nome);
		enteDTO.setEnte_pec(ente_pec);
		enteDTO.setEnte_provincia(ente_provincia);
		enteDTO.setEnte_regione(ente_regione);
		enteDTO.setId_pratica(new Integer(0));
		return Optional.of(enteDTO);
	}
	
	/**
	 * Ritorna una lista con un solo elemento..per il momento
	 * @TODO aggiungere più partecipanti..
	 * @param fascicolo
	 * @param raggruppamento
	 * @return
	 */
	public List<IntegFrontieraEntiDTO> getListaIntegFrontieraEntiDTO(String fascicolo,String tipoConferenza,String raggruppamento) {
		 List<IntegFrontieraEntiDTO> lista = new ArrayList<IntegFrontieraEntiDTO>(); 
		 getIntegFrontieraEntiDTO(fascicolo,tipoConferenza,raggruppamento).ifPresent( enteDTO -> lista.add(enteDTO) );
		 return lista;
	}
	

	/**
	 * 
	 * @param paleoRegistryAdapter
	 * @return
	 */
	public IntegFrontieraDTO getFrontieraDTOFromPaleo(PaleoRegistryAdapter paleoRegistryAdapter) {
		IntegFrontieraDTO frontieraDTO= null;		
		String fascicolo=paleoRegistryAdapter.getFkPaleoFascicolo();
		Integer idTipoConferenza=paleoRegistryAdapter.getIdFascicoloTipoConferenza();
		String tipoConferneza= paleoFascicoloTipoConferenceRepository.getTipoConferenzaById(idTipoConferenza);
		frontieraDTO=  getFrontieraDTOFromIdFascicolo(fascicolo,tipoConferneza) ;		
		return frontieraDTO;
	}

	/**
	 * itera su più fascicoli
	 * @param listPaleoRegistryAdapter
	 */
	public void getFrontieraDTOFromPaleo(List<PaleoFascicoloTipoConference> listPaleoRegistryAdapter) {
		for(PaleoFascicoloTipoConference item :listPaleoRegistryAdapter ) {			
			List<PaleoRegistryAdapter>  listaPaleoRegistryAdapter= paleoRegistryAdapterRepository.getlistPaleoAdapterByFascicolo(item.getIdFascicolo(),item.getId());
			for(PaleoRegistryAdapter item2 :listaPaleoRegistryAdapter ) {
				getFrontieraDTOFromPaleo(item2);
			}
		}		
	}
	
	
	public IntegFrontieraDTO getFrontieraDTOFromIdFascicolo(String fascicolo,String tipoConferenzaAssociata) {			
		IntegFrontieraDTO frontieraDTO= new IntegFrontieraDTO();		

		//Pratica/Fascicolo
		frontieraDTO.setId_fascicolo(fascicolo);
		//Procedimento
		frontieraDTO.setId_procedimento(new Integer(0));
		String nome_procedimento="Paleo["+fascicolo+"] "+ paleoFascicoloTipoConferenceRepository.getNomeFascicolo(fascicolo,tipoConferenzaAssociata);		
		frontieraDTO.setNome_procedimento(nome_procedimento);	
		//Documenti
		Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(fascicolo,tipoConferenzaAssociata);
		List<IntegFrontieraDocumentDTO> listaDocumenti=listIntegFrontieraDocumentDTO(fascicolo,idFascioloTipoConferenza);
		frontieraDTO.setListaDocumenti(listaDocumenti);

		
		IntegFrontieraEntiDTO enteProcedente=getIntegFrontieraEntiDTO(fascicolo,tipoConferenzaAssociata,"enteProcedente").get();
		List<IntegFrontieraEntiDTO> entiPartecipanti= getListaIntegFrontieraEntiDTO(fascicolo,tipoConferenzaAssociata,"entiPartecipanti") ;
				
		//Integer ente_id=new Integer(getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo) ,"ente_id" )) ;
		Integer ente_id=null;
		String ente_nome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"ente_nome" );
		String ente_pec=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"ente_pec" );
		//-----------------------
		Integer id_responsabile=new Integer(getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"id_responsabile" )) ;
		String responsabile_cf=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"responsabile_cf" );
		String responsabile_cognome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"responsabile_cognome" );
		String responsabile_mail=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"responsabile_mail" );
		String responsabile_nome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"responsabile_nome" );
		String responsabile_pec=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"responsabile_pec" );
		String richiedente_cf=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_cf" );
		String richiedente_cognome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_cognome" );
		String richiedente_comune_istat=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_comune_istat" );
		String richiedente_comune_nome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_provincia_nome" );
		String richiedente_mail=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_mail" );
		String richiedente_nome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_nome" );
		String richiedente_pec=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_pec" );
		String richiedente_provincia_istat=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_provincia_istat" );
		String richiedente_provincia_nome=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_provincia_nome" );
		String richiedente_tipologia_pratica=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_tipologia" , null);
		String richiedente_attivita=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_attivita", null);
		String richiedente_azione=getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo,tipoConferenzaAssociata) ,"richiedente_azione" , null);
		
		//Ente		
		frontieraDTO.setEnte_id(ente_id);
		frontieraDTO.setEnte_nome(ente_nome);		
		frontieraDTO.setEnte_pec(ente_pec);		
		frontieraDTO.setEnteProcedente(enteProcedente);		
		frontieraDTO.setEntiPartecipanti(entiPartecipanti);		
		//Responsabile		
		frontieraDTO.setId_responsabile(id_responsabile);		
		frontieraDTO.setResponsabile_cf(responsabile_cf);		
		frontieraDTO.setResponsabile_cognome(responsabile_cognome);		
		frontieraDTO.setResponsabile_mail(responsabile_mail);		
		frontieraDTO.setResponsabile_nome(responsabile_nome);		
		frontieraDTO.setResponsabile_pec(responsabile_pec);		
		//Richiedente
		frontieraDTO.setRichiedente_cf(richiedente_cf);		
		frontieraDTO.setRichiedente_cognome(richiedente_cognome);		
		frontieraDTO.setRichiedente_comune_istat(richiedente_comune_istat);		
		frontieraDTO.setRichiedente_comune_nome(richiedente_comune_nome);		
		frontieraDTO.setRichiedente_mail(richiedente_mail);		
		frontieraDTO.setRichiedente_nome(richiedente_nome);		
		frontieraDTO.setRichiedente_pec(richiedente_pec);		
		frontieraDTO.setRichiedente_provincia_istat(richiedente_provincia_istat);		
		frontieraDTO.setRichiedente_provincia_nome(richiedente_provincia_nome);				
		frontieraDTO.setTipologia(richiedente_tipologia_pratica);		
		frontieraDTO.setAttivita(richiedente_attivita);		
		frontieraDTO.setAzione(richiedente_azione);
		return frontieraDTO;
		
	}
	
		
	
}
