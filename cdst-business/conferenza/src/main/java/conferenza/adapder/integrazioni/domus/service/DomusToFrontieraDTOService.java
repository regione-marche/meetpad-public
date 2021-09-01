package conferenza.adapder.integrazioni.domus.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraEntiDTO;
import conferenza.adapder.integrazioni.domus.DomusConfigurator;
import conferenza.adapder.integrazioni.domus.model.DomusConferenceProperties;
import conferenza.adapder.integrazioni.domus.repository.DomusConferencePropertiesRepository;

@Service
public class DomusToFrontieraDTOService  {
	
	@Autowired
	DomusConfigurator domusConfiguration;
	
	@Autowired
	DomusConferencePropertiesRepository conferencePropertiesRepository;
	
	public static String TIPO_CONFERENZA_DOMUS_DEFUALT="1";
	
	
	private String getTipoConferencePaleoEntityProperties(String idTipoConferenza,String nomeProperties ) {
		String retVal=null;
		String ambiente =domusConfiguration.getDomusAmbiente();
		List<DomusConferenceProperties> listproperties=conferencePropertiesRepository.getParameterByTipoConferenceSpecializzazione(idTipoConferenza,nomeProperties, ambiente );
		if(listproperties!=null && !listproperties.isEmpty())
			retVal=listproperties.get(0).getPropertiesValue();
		return retVal;
	}
	
	private String getTipoConferenza(String tipoConferenza) {
		TIPO_CONFERENZA_DOMUS_DEFUALT=domusConfiguration.getDomusTipoConferenzaDefualt();
		return TIPO_CONFERENZA_DOMUS_DEFUALT;		
	}
	
	private String getTipoConferencePaleoEntityPropertiesRaggruppamento(String idTipoConferenza, String nomeProperties,String raggruppamento) {
		String retVal=null;
		String ambiente =domusConfiguration.getDomusAmbiente();
		List<DomusConferenceProperties> listproperties=conferencePropertiesRepository.getParameterByTipoConferenceSpecializzazioneRaggruppamento(idTipoConferenza,nomeProperties, ambiente,raggruppamento );
		if(listproperties!=null && !listproperties.isEmpty())
			retVal=listproperties.get(0).getPropertiesValue();
		return retVal;
	}
	
	/**
	 * Ritorna una lista con un solo elemento..per il momento
	 * @TODO aggiungere più partecipanti..
	 * @param fascicolo
	 * @param raggruppamento
	 * @return
	 */
	public List<IntegFrontieraEntiDTO> getListaIntegFrontieraEntiDTO( String tipoConferenza,String raggruppamento) {
		 List<IntegFrontieraEntiDTO> lista = new ArrayList<IntegFrontieraEntiDTO>(); 
		 getIntegFrontieraEntiDTO(tipoConferenza,raggruppamento).ifPresent( result -> {
			 lista.add(result);
		 });
		 return lista;
	}	
	
	
	public Optional<IntegFrontieraEntiDTO> getIntegFrontieraEntiDTO(String tipoConferenza,String raggruppamento) {
		IntegFrontieraEntiDTO enteDTO=new IntegFrontieraEntiDTO();
		String sEnteId=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_id" ,raggruppamento);
		if(sEnteId == null) {
			return Optional.empty();
		}
		BigInteger ente_id=new BigInteger(sEnteId) ;
		String ente_nome=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_nome",raggruppamento );
		String ente_pec=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_pec" ,raggruppamento);
		String ente_codice_comune=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_codice_comune" ,raggruppamento);
		String ente_codice_provincia=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_codice_provincia" ,raggruppamento);
		String ente_codice_regione=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_codice_regione" ,raggruppamento);
		String ente_comune=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_comune" ,raggruppamento);
		String ente_provincia=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_provincia" ,raggruppamento);
		String ente_regione=getTipoConferencePaleoEntityPropertiesRaggruppamento(getTipoConferenza(tipoConferenza) ,"ente_regione" ,raggruppamento);
		
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
	
	/* TODO check frontiera */
	public IntegFrontieraDTO getFrontieraDTOFromTipoconferenza(String tipoConferenzaAssociata,IntegFrontieraDTO frontieraDTO) {
		
		if(frontieraDTO==null)
			frontieraDTO= new IntegFrontieraDTO();		

		//-----------------------
		//@TODO
		//Documenti
		//-----------------------
		//Integer idFascioloTipoConferenza=paleoRegistryAdapterRepository.getIdFascicoloTipoConferenza(fascicolo,tipoConferenzaAssociata);
		//List<IntegFrontieraDocumentDTO> listaDocumenti=listIntegFrontieraDocumentDTO(fascicolo,idFascioloTipoConferenza);
		//frontieraDTO.setListaDocumenti(listaDocumenti);

		//-----------------------
		//ENTI
		IntegFrontieraEntiDTO enteProcedente=null;
		Optional<IntegFrontieraEntiDTO> enteProcedenteOptional = getIntegFrontieraEntiDTO(tipoConferenzaAssociata,"enteProcedente");
		if(enteProcedenteOptional.isPresent()) {
			enteProcedente = enteProcedenteOptional.get();
		}
		List<IntegFrontieraEntiDTO> entiPartecipanti= getListaIntegFrontieraEntiDTO(tipoConferenzaAssociata,"entiPartecipanti") ;
				
		//-----------------------
		//ENTE
		//Integer ente_id=new Integer(getTipoConferencePaleoEntityProperties(getTipoConferenzaByFascicolo(fascicolo) ,"ente_id" )) ;
		Integer ente_id=null;
		String ente_nome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"ente_nome" );
		String ente_pec=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"ente_pec" );
		//-----------------------
		//RESPONSABILE
		//-----------------------
		Integer id_responsabile=new Integer(getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"id_responsabile" )) ;
		String responsabile_cf=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"responsabile_cf" );
		String responsabile_cognome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"responsabile_cognome" );
		String responsabile_mail=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"responsabile_mail" );
		String responsabile_nome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"responsabile_nome" );
		String responsabile_pec=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"responsabile_pec" );
		
		//-----------------------
		//RICHIEDENTE
		//-----------------------		
		Integer id_richiedented=new Integer(getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"id_richiedented" ));
		String richiedente_cf=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_cf" );
		String richiedente_cognome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_cognome" );
		String richiedente_comune_istat=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_cf" );
		String richiedente_comune_nome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_comune_istat" );
		String richiedente_mail=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_mail" );
		String richiedente_nome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_nome" );
		String richiedente_pec=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_pec" );
		String richiedente_provincia_istat=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_provincia_istat" );
		String richiedente_provincia_nome=getTipoConferencePaleoEntityProperties(getTipoConferenza(tipoConferenzaAssociata) ,"richiedente_provincia_nome" );
		
		
		//-----------------------
		//
		//-----------------------
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

		return frontieraDTO;
		
	}
}
