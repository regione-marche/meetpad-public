package conferenza.adapder.integrazioni.domus.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocollo;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocolloWithoutSream;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaResp;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended;
import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import conferenza.DTO.ContattoSupportoDTO;
import conferenza.adapder.alfresco.DocumentUtils;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.domus.DomusConfigurator;
import conferenza.adapder.integrazioni.domus.DTO.DomusDTO;
import conferenza.adapder.integrazioni.domus.adapter.DomusClientAdapterService;
import conferenza.adapder.integrazioni.domus.model.DomusComune;
import conferenza.adapder.integrazioni.domus.model.DomusPratica;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterAllegati;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterProtocolli;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterTesta;
import conferenza.adapder.integrazioni.domus.model.DomusXmlIstanza;
import conferenza.adapder.integrazioni.domus.repository.DomusComuneRepository;
import conferenza.adapder.integrazioni.domus.repository.DomusRegistryAdapterAllegatiRepository;
import conferenza.adapder.integrazioni.domus.repository.DomusRegistryAdapterProtocolliRepository;
import conferenza.adapder.integrazioni.domus.repository.DomusRegistryAdapterTestaRepository;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.adapder.integrazioni.paleo.service.PaleoDocumentaleIntraProtocolService;
import conferenza.builder.ConferenzaBuilder;
import conferenza.model.Conferenza;
import conferenza.model.ContattoSupporto;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.ContattoSupportoRepository;
import conferenza.service.RegistroDocumentoService;
import conferenza.util.DbConst;
import conferenza.util.RandomUtil;
import it.marche.regione.paleo.services.OperatorePaleo;
import it.marche.regione.paleo.services.TrasmissioneUtente;


@Service
public class DomusService {

	private static final Logger logger = LoggerFactory.getLogger(DomusService.class.getName());

	private static final String DOMUSTEMPFILE = "temp/";
	
	
	String tipologiaConferenza=String.valueOf( DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
	
	
	@Autowired
	DomusConfigurator domusConfigurator;
		
	@Autowired
	DomusClientAdapterService domusAdapterService;
	
	@Autowired
	DomusConferenceService domusConferenceService;
	
	@Autowired
	DomusToFrontieraDTOService domusToFrontieraDTOService; 
	
	@Autowired
	DomusRegistryAdapterTestaRepository domusRegistryAdapterTestaRepository;
	@Autowired
	DomusRegistryAdapterProtocolliRepository domusRegistryAdapterProtocolliRepository;
	@Autowired
	DomusRegistryAdapterAllegatiRepository domusRegistryAdapterAllegatiRepository;
	
	@Autowired
	DomusComuneRepository domusComuneREpository;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	PaleoDocumentaleIntraProtocolService paleoDocumentaleService;
	
	@Autowired
	PaleoAdapterService paleoAdapterClient;
	
	@Autowired
	ConferenzaRepository conferenzaRepo;
	
	@Autowired
	ConferenzaBuilder confBuilder;
	
	@Autowired
	ContattoSupportoRepository contattoSuppRepo;
	
	public Pratica[]  getPraticheMIS(String comune)	throws ServiceException, RemoteException, MalformedURLException{		
		return   domusAdapterService.getPraticheMIS(comune);		
	}
	
	public PraticaExtended getProtocolliPraticaById(Integer idproatica)	throws ServiceException, RemoteException, MalformedURLException{		
		return domusAdapterService.getProtocolloPraticaById(idproatica);
	}

	public Pratica getPratica(Integer idPratica, String comune) throws RemoteException, MalformedURLException, ServiceException {
		
		Pratica[]  pratiche=  getPraticheMIS(comune);
		if(pratiche != null);
			for(Pratica pratica:pratiche){		
				if(pratica != null && pratica.getIdRichiesta().equals(idPratica)) {
					return pratica;	
				}
					
			}
				
		return null;
	}

	
	/**
	 * 
	 * @return
	 */
	public List<Integer> doActionCreateConferenceAllComuni(String tipologiaConferenza,List<IntegFrontieraDocumentDTO> listaAllegati)throws ServiceException, IOException{
		List<Integer> conferenzeIds =new ArrayList<Integer>();		
		List<DomusComune> listaComuni= domusComuneREpository.findAll();
		for(DomusComune ocomune : listaComuni)
			doActionCreateDomusConferences(ocomune.getCodiceComune(),tipologiaConferenza,listaAllegati);
		return null;		
	}
	
	
/**
 * 
 * @param comune
 * @param tipologiaConferenza
 * @param listaAllegati
 * @return Lista di idConferenza generate per il dato comune.
 * @throws ServiceException
 * @throws IOException
 */
	public  List<Integer> doActionCreateDomusConferences(String comune,String tipologiaConferenza,List<IntegFrontieraDocumentDTO> listaAllegati) 
			throws ServiceException, IOException{
		
		List<Integer> conferenzeIds =new ArrayList<Integer>();
		//Pratica[]  pratiche = null;
		if(comune.startsWith("#")) {
			PraticaExtended[]  pratiche = null;
			pratiche = domusAdapterService.getProtocolliPratica(Integer.parseInt(comune.substring(1)));
			if(pratiche==null){
				logger.debug("@domus nessuna pratica per il comune: "+comune);
				return null;
			}
						
		
			for(PraticaExtended pratica:pratiche){			
				if(pratica!=null){
					logger.debug("@domus pratica per il comune: "+comune+" pratica: "+pratica.getIdRichiesta());

					//creazione della riga di teestata		
					// xmf TODO: get comune when comune is #codicepratica
					
					String tipologia = "Ricostruzionae";
					String azione = parse(pratica.getOrdinanza());
					String attivita = "";
					String pecEsistente = "";
					String codiceFiscaleEsistente = "";
					
					if (pratica.getOrdinanza().equals(DbConst.ORDINANZA_4) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_8) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_13) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_19) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_100))  {
						attivita = DbConst.ATTIVITA_RICOSTRUZIONE_PRIVATA;
																		
					} else {
						attivita = DbConst.ATTIVITA_RICOSTRUZIONE_PUBBLICA;
						if (pratica.getRichiedente() != null) { 
							Integer maxConferenzaByCodiceFiscaleAndPec = conferenzaRepo.
									findMaxConferenzaByIntestatario(pratica.getRichiedente().trim());
							if (maxConferenzaByCodiceFiscaleAndPec != null &&
									!maxConferenzaByCodiceFiscaleAndPec.equals(new Integer(0))) {
								Optional<Conferenza> confMax = conferenzaRepo.findById(maxConferenzaByCodiceFiscaleAndPec);
								if (confMax != null) {
									Conferenza conf = confMax.get();
									if (conf.getCodiceFiscaleRichiedente() != null) {
										codiceFiscaleEsistente = conf.getCodiceFiscaleRichiedente();
									}
									if (conf.getPec() != null) {
										pecEsistente = conf.getPec();
									}
								}
							}
						}
					}
					
					
					DomusRegistryAdapterTesta testata =storeRegistroTestata(comune,pratica.getIdRichiesta()); 		
					logger.debug("testata IdConferenza "+testata.getIdConferenza());							
					if (testata.getIdConferenza() == null) {
						DomusPratica xmlIstanza = null;
						String lastXmlresponse = "";
						IntegFrontieraDTO frontieraDTO = null;

						long startt = System.currentTimeMillis();
						try {
							GetXmlIstanzaResp resp = domusAdapterService.getXmlIstanza(pratica.getIdRichiesta());

							int endstr = -1;
							lastXmlresponse = resp.getXmlIstanza();
							if (lastXmlresponse != null) {
								xmlIstanza = DomusXmlIstanza.fromString(lastXmlresponse);

								logger.debug("@domus nuova conferenza da aggiungere per il comune: " + comune + "-"
										+ xmlIstanza.getCodiceIstat() + " pratica: " + pratica.getIdRichiesta());
								
								if (attivita == null) {
									frontieraDTO = DomusDTO.fillIntegFrontieraDTO(xmlIstanza.getCodiceIstat(), pratica);
								} else {
									frontieraDTO = DomusDTO.fillIntegFrontieraDTO(xmlIstanza.getCodiceIstat(), pratica, attivita);
								}
								
								// --------------------------------------------------------------
								// @TODO: gennearosarnataro
								// Completa le informazioni per la creazione della conferenza..
								frontieraDTO = domusToFrontieraDTOService
										.getFrontieraDTOFromTipoconferenza(tipologiaConferenza, frontieraDTO);

								endstr = lastXmlresponse.lastIndexOf("<dd:dataDescription");
								if (endstr > -1) // xmf: bugfix NOT ALWAYS THAT TAG EXISTS!
									lastXmlresponse = lastXmlresponse.substring(0, endstr);

								logger.debug("@domus import XML[" + lastXmlresponse + "]");

								if (xmlIstanza.getCfRichiedente() != null && !"".equals(xmlIstanza.getCfRichiedente())) {
									// Mapping DomusPratica -> IntegFrontieraDTO
									frontieraDTO.setRichiedente_nome(xmlIstanza.getNomeRichiedente());
									frontieraDTO.setRichiedente_cognome(xmlIstanza.getCognomeRichiedente());
									frontieraDTO.setRichiedente_cf(xmlIstanza.getCfRichiedente());
									frontieraDTO.setRichiedente_pec(xmlIstanza.getPecRichiedente());
								} else {
									if (attivita != null && attivita.equals(DbConst.ATTIVITA_RICOSTRUZIONE_PUBBLICA) ) {
										frontieraDTO.setRichiedente_nome(" ");
										if (pratica.getRichiedente() != null) {
											frontieraDTO.setRichiedente_cognome(pratica.getRichiedente().trim());
										} else {
											frontieraDTO.setRichiedente_cognome("");
										}
										
										if (codiceFiscaleEsistente != null) {
											frontieraDTO.setRichiedente_cf(codiceFiscaleEsistente);
										} else {
											if (pratica.getCodiceFiscaleRichiedente() != null &&
													!pratica.getCodiceFiscaleRichiedente().equals("")) {
												frontieraDTO.setRichiedente_cf(pratica.getCodiceFiscaleRichiedente().trim());
											} else {
												frontieraDTO.setRichiedente_cf("");
											}
										}
										
										if (pecEsistente != null) {
											frontieraDTO.setRichiedente_pec(pecEsistente);
										} else {
											if (pratica.getPecRichiedente() != null &&
													!pratica.getPecRichiedente().equals("")) {
												frontieraDTO.setRichiedente_pec(pratica.getPecRichiedente().trim());
											} else {
												frontieraDTO.setRichiedente_pec("");
											}
										}
																			
									} else {
										logger.debug("richiedente non valorizzato usato il default!");
									}
									
								}
								
								frontieraDTO.setAttivita(attivita);
								frontieraDTO.setTipologia(DbConst.TIPOLOGIA_RICOSTRUZIONE_COD);
								frontieraDTO.setAzione(azione);

							}

						} catch (Exception e1) {
							logger.debug("@domus XML dataDescription exception[" + lastXmlresponse + "] : "
									+ (System.currentTimeMillis() - startt) + " Domus API error retrieving comune: "
									+ comune + " - " + e1.getMessage() + " "
									+ Arrays.toString(e1.getStackTrace()).replaceAll(",", "\n"));
							continue;
						}

						// --------------------------------------------------------------
						// Creazione della conferenza..
						Integer idConferenza = createConference(frontieraDTO, tipologiaConferenza, listaAllegati,
								xmlIstanza.getOggettoPratica());

						ContattoSupporto contatto = null;

						String contattiSupporto = domusConfigurator.getContattiSupporto();

						if (contattiSupporto != null) {
							String[] list = contattiSupporto.split("[|]");

							for (int i = 0; i < list.length; i++) {
								String[] contattoarray = list[i].split(";");
								ContattoSupportoDTO contattoDTO = new ContattoSupportoDTO();

								logger.info("DomusService - creazione contatto supporto");
								if (contattoarray[0].length() > 0)
									contattoDTO.setNome(contattoarray[0]);
								if (contattoarray[1].length() > 0)
									contattoDTO.setCognome(contattoarray[1]);
								if (contattoarray[2].length() > 0)
									contattoDTO.setEmail(contattoarray[2]);
								if (contattoarray[3].length() > 0)
									contattoDTO.setTelefono(contattoarray[3]);

								ContattoSupporto saved = this.confBuilder.buildContattoSupporto(contattoDTO, contatto,
										idConferenza);
								saved = this.contattoSuppRepo.save(saved);

								logger.info("DomusService - salvato contatto supporto");
							}

						}

						logger.debug("Domus nuova conferenza aggiunta per il comune: " + comune + " pratica: "
								+ pratica.getIdRichiesta() + " conferenza: " + idConferenza);

						// --------------------------------------------------------------
						// Aggiunta dell'impresa alla conferenza creata..
						try {
							if (xmlIstanza != null && idConferenza != null)
								if (attivita == null || (attivita != null && attivita.equals(DbConst.ATTIVITA_RICOSTRUZIONE_PRIVATA))) {
									fillIntestatarioConferenza(xmlIstanza, idConferenza);
								} else {
									fillIntestatarioConferenza(xmlIstanza, idConferenza, pratica,codiceFiscaleEsistente);
								}
								
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						// aggiorno la testata
						testata.setIdConferenza(idConferenza);
						domusRegistryAdapterTestaRepository.save(testata);

						// --------------------------------------------------------
						//
						// --------------------------------------------------------
						List<Documento> listaDocumenti = null;
						try {
							listaDocumenti = storeAllegatiPraticheMISWithoutStream(pratica, idConferenza, testata,
									frontieraDTO.getResponsabile_cf());

						} catch (Exception e) {
							e.printStackTrace();
							logger.debug("Problemi con gli allegati per  la conferenza:" + idConferenza);
						}
						conferenzeIds.add(idConferenza);
					}
					
				}else{
					logger.debug("PRATICA NULL PER "+comune);					
				}
			}
		}
		else {
			Pratica[]  pratiche = null;
			pratiche = getPraticheMIS(comune); //domusAdapterService.getPraticheTEST();  //getPraticheMIS(comune);
			if(pratiche==null){
				logger.debug("@domus nessuna pratica per il comune: "+comune);
				return null;
			}
						
		
			for(Pratica pratica:pratiche){			
				if(pratica!=null){
					logger.debug("@domus pratica per il comune: "+comune+" pratica: "+pratica.getIdRichiesta());

					//creazione della riga di teestata		
					// xmf TODO: get comune when comune is #codicepratica
					
					/*
					String tipologia = "Ricostruzionae";
					String azione = parse(pratica.getOrdinanza());
					String attivita = "";
					
					if (pratica.getOrdinanza().equals(DbConst.ORDINANZA_4) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_8) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_13) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_19) ||
						pratica.getOrdinanza().equals(DbConst.ORDINANZA_100))  {
						attivita = "31";
						
					} else {
						attivita = "32";
					}
					*/
					
					DomusRegistryAdapterTesta testata =storeRegistroTestata(comune,pratica.getIdRichiesta()); 		
					logger.debug("testata IdConferenza "+testata.getIdConferenza());							
					if (testata.getIdConferenza() == null) {
						DomusPratica xmlIstanza = null;
						String lastXmlresponse = "";
						IntegFrontieraDTO frontieraDTO = null;

						long startt = System.currentTimeMillis();
						try {
							GetXmlIstanzaResp resp = domusAdapterService.getXmlIstanza(pratica.getIdRichiesta());

							int endstr = -1;
							lastXmlresponse = resp.getXmlIstanza();
							if (lastXmlresponse != null) {
								xmlIstanza = DomusXmlIstanza.fromString(lastXmlresponse);

								logger.debug("@domus nuova conferenza da aggiungere per il comune: " + comune + "-"
										+ xmlIstanza.getCodiceIstat() + " pratica: " + pratica.getIdRichiesta());
								frontieraDTO = DomusDTO.fillIntegFrontieraDTO(xmlIstanza.getCodiceIstat(), pratica);
								// --------------------------------------------------------------
								// @TODO: gennearosarnataro
								// Completa le informazioni per la creazione della conferenza..
								frontieraDTO = domusToFrontieraDTOService
										.getFrontieraDTOFromTipoconferenza(tipologiaConferenza, frontieraDTO);

								endstr = lastXmlresponse.lastIndexOf("<dd:dataDescription");
								if (endstr > -1) // xmf: bugfix NOT ALWAYS THAT TAG EXISTS!
									lastXmlresponse = lastXmlresponse.substring(0, endstr);

								logger.debug("@domus import XML[" + lastXmlresponse + "]");

								if (xmlIstanza.getCfRichiedente() != null && !"".equals(xmlIstanza.getCfRichiedente())) {
									// Mapping DomusPratica -> IntegFrontieraDTO
									frontieraDTO.setRichiedente_nome(xmlIstanza.getNomeRichiedente());
									frontieraDTO.setRichiedente_cognome(xmlIstanza.getCognomeRichiedente());
									frontieraDTO.setRichiedente_cf(xmlIstanza.getCfRichiedente());
									frontieraDTO.setRichiedente_pec(xmlIstanza.getPecRichiedente());
								} else {
									logger.debug("richiedente non valorizzato usato il default!");
								}
								
								/*
								frontieraDTO.setAttivita(attivita);
								frontieraDTO.setTipologia(DbConst.TIPOLOGIA_RICOSTRUZIONE_COD);
								frontieraDTO.setAzione(azione);
								*/

							}

						} catch (Exception e1) {
							logger.debug("@domus XML dataDescription exception[" + lastXmlresponse + "] : "
									+ (System.currentTimeMillis() - startt) + " Domus API error retrieving comune: "
									+ comune + " - " + e1.getMessage() + " "
									+ Arrays.toString(e1.getStackTrace()).replaceAll(",", "\n"));
							continue;
						}

						// --------------------------------------------------------------
						// Creazione della conferenza..
						Integer idConferenza = createConference(frontieraDTO, tipologiaConferenza, listaAllegati,
								xmlIstanza.getOggettoPratica());

						ContattoSupporto contatto = null;

						String contattiSupporto = domusConfigurator.getContattiSupporto();

						if (contattiSupporto != null) {
							String[] list = contattiSupporto.split("[|]");

							for (int i = 0; i < list.length; i++) {
								String[] contattoarray = list[i].split(";");
								ContattoSupportoDTO contattoDTO = new ContattoSupportoDTO();

								logger.info("DomusService - creazione contatto supporto");
								if (contattoarray[0].length() > 0)
									contattoDTO.setNome(contattoarray[0]);
								if (contattoarray[1].length() > 0)
									contattoDTO.setCognome(contattoarray[1]);
								if (contattoarray[2].length() > 0)
									contattoDTO.setEmail(contattoarray[2]);
								if (contattoarray[3].length() > 0)
									contattoDTO.setTelefono(contattoarray[3]);

								ContattoSupporto saved = this.confBuilder.buildContattoSupporto(contattoDTO, contatto,
										idConferenza);
								saved = this.contattoSuppRepo.save(saved);

								logger.info("DomusService - salvato contatto supporto");
							}

						}

						logger.debug("Domus nuova conferenza aggiunta per il comune: " + comune + " pratica: "
								+ pratica.getIdRichiesta() + " conferenza: " + idConferenza);

						// --------------------------------------------------------------
						// Aggiunta dell'impresa alla conferenza creata..
						try {
							if (xmlIstanza != null && idConferenza != null)
								fillIntestatarioConferenza(xmlIstanza, idConferenza);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						// aggiorno la testata
						testata.setIdConferenza(idConferenza);
						domusRegistryAdapterTestaRepository.save(testata);

						// --------------------------------------------------------
						//
						// --------------------------------------------------------
						List<Documento> listaDocumenti = null;
						try {
							listaDocumenti = storeAllegatiPraticheMISWithoutStream(pratica, idConferenza, testata,
									frontieraDTO.getResponsabile_cf());

						} catch (Exception e) {
							e.printStackTrace();
							logger.debug("Problemi con gli allegati per  la conferenza:" + idConferenza);
						}
						conferenzeIds.add(idConferenza);
					}
					
				}else{
					logger.debug("PRATICA NULL PER "+comune);					
				}
			}
		}
			
		
		
		return conferenzeIds;		
	}


	private void fillIntestatarioConferenza(DomusPratica xmlIstanza, Integer idConferenza) throws Exception {
		domusConferenceService.fillIntestatarioConferenza(xmlIstanza,idConferenza,null,null);
	
	}
	
	private void fillIntestatarioConferenza(DomusPratica xmlIstanza, Integer idConferenza, 
			PraticaExtended pratica, String codiceFiscale) throws Exception {
		domusConferenceService.fillIntestatarioConferenza(xmlIstanza,idConferenza,pratica, codiceFiscale);
	
	}

	/**
	 * 
	 * @param comune
	 * @param idPratica
	 */
	private DomusRegistryAdapterTesta storeRegistroTestata(			
			String comune,
			Integer idPratica){		
		DomusRegistryAdapterTesta testata =new DomusRegistryAdapterTesta();
		//testata=domusRegistryAdapterTestaRepository.getExistingTestata(comune, idPratica);
		testata=domusRegistryAdapterTestaRepository.getExistingTestataByIdPratica(idPratica);
		if(testata!=null){
			return testata;			
		}
		else{
			testata =new DomusRegistryAdapterTesta();
			testata.setIdPratica(idPratica);
			testata.setFk_CodiceComune(comune);
			testata.setDtIns(new Date());
			domusRegistryAdapterTestaRepository.save(testata);			
			return testata;
		}		
	}
	
	
	/**
	 * 
	 * @param comune
	 * @param idPratica
	 * @param tipologiaConferenza
	 * @param listaAllegati
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public  Integer doActionCreateDomusConferencesByComunepratica(
			String comune,
			Integer idPratica,
			String tipologiaConferenza,List<IntegFrontieraDocumentDTO> listaAllegati
			) throws ServiceException, IOException{
		Integer idConferenza=null;
		Pratica[]  pratiche=  getPraticheMIS(comune); //domusAdapterService.getPraticheTEST();  //getPraticheMIS(comune);
		if(pratiche != null)
			for(Pratica pratica:pratiche){
				if(pratica!=null){			
					if( Integer.compare(pratica.getIdRichiesta(),idPratica)==0){					
						//creazione della riga di teestata
						DomusRegistryAdapterTesta testata =storeRegistroTestata(comune,idPratica); 				
						if(testata.getIdConferenza()==null){				
							IntegFrontieraDTO frontieraDTO = DomusDTO.fillIntegFrontieraDTO(comune,pratica);
							//Completa le informazioni per la creazione della conferenza..
							frontieraDTO=domusToFrontieraDTOService.getFrontieraDTOFromTipoconferenza(tipologiaConferenza, frontieraDTO);
							//Creazione della conferenza..SSE Non è stata già creata..
							idConferenza=createConference(frontieraDTO,tipologiaConferenza, listaAllegati,null);
							//aggiornamento della conferenza..					
							testata.setIdConferenza(idConferenza);
							domusRegistryAdapterTestaRepository.save(testata);	
							List<Documento> listaDocumenti= storeAllegatiPraticheMIS(comune, pratica , idConferenza);
						}
						
					}
				}else{
					logger.debug("PRATICA NULL PER "+comune+" id pratica:" +idPratica);				
				}	
			}

		return idConferenza;		
	}	
		
	 /**
	  *  
	  * @param frontieraDTO
	  * @return
	  * @throws IOException
	  */
	 private Integer createConference(IntegFrontieraDTO frontieraDTO, String tipologiaConferenza, List<IntegFrontieraDocumentDTO> listaAllegati,String oggettoDeterminazione) 
			 throws IOException {
			Integer idConferenza = null;
		    idConferenza = domusConferenceService.persistConference( frontieraDTO, tipologiaConferenza, listaAllegati,oggettoDeterminazione); 			    
			return idConferenza;		 		 
	 } 
	 
	 /**
	  * 
	  * @param comune
	  * @param pratica
	  * @param idConferenza
	  * @return
	  * @throws ServiceException
	  * @throws IOException
	  */
	
	 //------------------------------------------------------------------------------	 
	 /**
	 * @throws IOException 
	  * 
	  * 
	  */
	 //public java.io.File stroreFileInFS(File dd,ProtocolloPratica protocollopratica,DocumentoProtocolloWithoutSream doc) throws IOException{
	 public java.io.File stroreFileInFS(
			 String nomeFile,
			 byte[] buffer 
			) throws IOException{	 		 	
		 	List<Documento> listaDocumenti=new ArrayList<Documento>();		 	
			logger.debug("Nome File"+nomeFile);							
			String pattempfile=DOMUSTEMPFILE+RandomUtil.getRandomToken();
			//-------------------------------------------------------------------
			java.io.File dir =new java.io.File(pattempfile);
			dir.mkdirs();				
			//-------------------------------------------------------------------
			pattempfile+= "/"+nomeFile;
			//-------------------------------------------------------------------						
			logger.debug("pattempfile: "+pattempfile);			
			java.io.File file = new java.io.File(pattempfile);		
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			outStream.write(buffer);
			outStream.close();
			return file;
	 }	
	 
	 /**
	  * 
	  * @param isPrincipale
	  * @param docnumberdomus
	  * @param pNomeFile
	  * @param estensioneFile
	  * @return
	  */
	 private String fillNomeFile(boolean isPrincipale,String docnumberdomus,String pNomeFile,String estensioneFile){
		 	String nomeFile=null;
			if(isPrincipale==true)
				nomeFile=docnumberdomus+"-" +"documentoprincipale-"+pNomeFile;	
			else	
				nomeFile=docnumberdomus+"-" +pNomeFile;		 
			return nomeFile;
	 }
	 
	 
	 /**
	  * 
	  * @param fkTestata
	  * @param protocollopratica
	  * @return
	  */
	 private DomusRegistryAdapterProtocolli storeDomusRegistryAdapterProtocolli(Integer fkTestata, ProtocolloPratica protocollopratica){	
		 
		 	DomusRegistryAdapterProtocolli registroProtocollo = domusRegistryAdapterProtocolliRepository.findByFkTestataAndIdDocumentoAndSignature(fkTestata, protocollopratica.getDocNumber(), protocollopratica.getSegnatura());
		 	if(registroProtocollo == null) {
		 		registroProtocollo=new DomusRegistryAdapterProtocolli();
				registroProtocollo.setFkTestata(fkTestata);
				registroProtocollo.setDtIns(new Date());
				registroProtocollo.setIdDocuemto(protocollopratica.getDocNumber());
				registroProtocollo.setSignature(protocollopratica.getSegnatura());
				registroProtocollo=domusRegistryAdapterProtocolliRepository.save(registroProtocollo);
		 	}
			
			return registroProtocollo;
	 }  

	/*
	 * se esiste già l'allegato nel repository allora restituisco null ad indicare che non è stato creato niente di nuovo
	 */
	private DomusRegistryAdapterAllegati storeDomusRegistryAdapterAllegati(Integer fkProtocollo, DocumentoProtocolloWithoutSream documentodomus) {

		DomusRegistryAdapterAllegati registroAllegati = domusRegistryAdapterAllegatiRepository.findByFkProtocolloAndIdFile(fkProtocollo, documentodomus.getFile().getIdFile());
		if (registroAllegati == null) {
			registroAllegati = new DomusRegistryAdapterAllegati();
			registroAllegati.setFkProtocollo(fkProtocollo);
			registroAllegati.setIsPrincipale(documentodomus.getIsPrincipale() == true ? "S" : "N");
			registroAllegati.setIdFile(documentodomus.getFile().getIdFile());
			registroAllegati.setNomeFile(documentodomus.getFile().getNome() + "." + documentodomus.getFile().getEstensione());
			registroAllegati = domusRegistryAdapterAllegatiRepository.save(registroAllegati);
		}
		
		return registroAllegati;
	}

	
	/**
	 *  
	 * @param listaDocumenti
	 */
	private void saveRegistroDocumentoReference(List<Documento> listaDocumenti, DomusRegistryAdapterAllegati registroAllegati){
			
			for(Documento item: listaDocumenti){
				if(item!=null){
					Integer fkRegistroDocumento=getFkRegistroDocumento(item.getIdDocumento());
					if(fkRegistroDocumento!=null){
						registroAllegati.setFkRegistroDocumento(fkRegistroDocumento);
						registroAllegati.setDtIns(new Date());
						domusRegistryAdapterAllegatiRepository.save(registroAllegati);
					}
				}				
			}			
	}	 
	 private Integer getFkRegistroDocumento(Integer idDocumento) {
		RegistroDocumento rf=registroDocumentoService.getRegistroDocumentoByIdDocumento(idDocumento);
		if(rf!=null)
			rf.getId();
		return null;
	}

	 
	//------------------------------------------------------------------------------
	 /**
	  * 
	  * @param pratica
	  * @param idConferenza
	  * @return
	  * @throws ServiceException
	  * @throws IOException
	  */
	 @Transactional(noRollbackFor = Exception.class)
	 public List<Documento>   storeAllegatiPraticheMISWithoutStream(Pratica pratica ,Integer idConferenza,DomusRegistryAdapterTesta testata,String cfResponsabile)	throws ServiceException, IOException{
			List<Documento> listaDocumenti=new ArrayList<Documento>();
			List<DomusRegistryAdapterAllegati> listaAllegatiDomus=new ArrayList<DomusRegistryAdapterAllegati>();
			ProtocolloPratica[] protocolli=pratica.getProtocolli();
			for(ProtocolloPratica protocollopratica: protocolli){				
				DomusRegistryAdapterProtocolli registroProtocollo=storeDomusRegistryAdapterProtocolli(testata.getId(),protocollopratica);								
				if(protocollopratica.getDocNumber()!=null){
					//chaiamata rest!
					DocumentoProtocolloWithoutSream[]  docprotocolli= getDocumentiProtocolloWithoutStream(new Integer(protocollopratica.getDocNumber()));
					//----------------------------------------------------------------------------
					//Storicizzzazione degli allegati sul registro
					//----------------------------------------------------------------------------
					if(docprotocolli!=null)
					for(DocumentoProtocolloWithoutSream doc: docprotocolli){
						 if(doc!=null){
							 //Salvataggio dei File Per riferimento
							DomusRegistryAdapterAllegati registroAllegati=storeDomusRegistryAdapterAllegati(registroProtocollo.getId(), doc);
							listaAllegatiDomus.add(registroAllegati);							 
						 } 
						 
						 // xmf: send pec by paleo
						 //sendPecViaPaleo(conf, registroProtocollo.getSignature());
					}															
				}//end if
			}
			//..data la lista degli allegati domus viene  
			domusConferenceService.creaDocumentiByreference(listaAllegatiDomus,idConferenza,cfResponsabile);
			return listaDocumenti;
	}
	 
	/**
	 * @TODO: impementare:
	 * public String getRaggruppamentoByIdDocument(RegistroDocumento registro) 
	 * per nuovo tipo REGISTRO_DOCUMENTO_DOMUS .. che per il momento è getito come file systeme per la persistenza..
	 *  
	 *  
	 *  
	 * @param comune
	 * @param pratica
	 * @param idConferenza
	 * @throws ServiceException
	 * @throws IOException
	 */
	public List<Documento>   storeAllegatiPraticheMIS(String comune,Pratica pratica ,Integer idConferenza)	throws ServiceException, IOException{		
		List<Documento> listaDocumenti=new ArrayList<Documento>();
		
		ProtocolloPratica[] protocolli=pratica.getProtocolli();
		for(ProtocolloPratica protocollopratica: protocolli){
			DocumentoProtocollo[] documentoprotocollo=null;
			if(protocollopratica.getDocNumber()!=null){
				//chaiamata rest!
				DocumentoProtocollo[]  docprotocolli= getDocumentiProtocollo(new Integer(protocollopratica.getDocNumber())); 
				if(docprotocolli!=null)
				for(DocumentoProtocollo doc: docprotocolli){
					File dd=doc.getFile();
					String nomeFile=dd.getNome();
					nomeFile=nomeFile.replaceAll( "[0-9]%#&\\$", "");
					nomeFile=nomeFile.replaceAll( "\\|+", "-");
					nomeFile=nomeFile.replaceAll( "\\/+", "-");
					logger.debug("Nome File"+nomeFile);				
					
					String pattempfile=DOMUSTEMPFILE+RandomUtil.getRandomToken();
					java.io.File dir =new java.io.File(pattempfile);
					dir.mkdirs();	
					
					String signature=protocollopratica.getSegnatura();
					if(signature!=null &&  signature.contains("ID: "))
						signature=signature.replaceAll("ID: ", "");
					signature=signature.replaceAll( "[0-9]%#&\\|\\/\\$", "");
					signature=signature.replaceAll( "\\|+", "-");
					signature=signature.replaceAll( "\\/+", "-");

					
					if(doc.getIsPrincipale()==true)
					pattempfile+="/"+signature+"-" +"documentoprincipale-"+nomeFile+"."+dd.getEstensione();	
					else	
					pattempfile+="/"+signature+"-" +nomeFile+"."+dd.getEstensione();
					
					pattempfile=pattempfile.replaceAll( "[0-9]%#&\\$", "");
					pattempfile=pattempfile.replaceAll("\\s+", "");
					logger.debug("pattempfile: "+pattempfile);
					
					byte[] buffer = dd.getStream() ;
					
					java.io.File file = new java.io.File(pattempfile);		
					file.createNewFile();
					OutputStream outStream = new FileOutputStream(file);
					outStream.write(buffer);
					outStream.close();
					Documento documento=domusConferenceService.storeFile(file, nomeFile, idConferenza) ;		
					listaDocumenti.add(documento);
				}					
			}//end if	
		}
		return listaDocumenti;
	}


	/**
	 * 
	 * @param bFile
	 * @param fileDest
	 */
	private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {
	        FileOutputStream fileOuputStream = null;
	        try {
	            fileOuputStream = new FileOutputStream(fileDest);
	            fileOuputStream.write(bFile);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fileOuputStream != null) {
	                try {
	                    fileOuputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	}
	

	
	public Pratica[]  getPraticheDomus(String comune) throws RemoteException, MalformedURLException, ServiceException {		
		return domusAdapterService.getPraticheMIS(comune);
	}

	
	
	public GetXmlIstanzaResp getXMLInstanza(Integer idRichiesta) throws ServiceException, RemoteException, MalformedURLException {		
		return domusAdapterService.getXmlIstanza(idRichiesta);
	}
	
	
	public DocumentoProtocollo[]  getDocumentiProtocollo(Integer docNumber) throws ServiceException, RemoteException, MalformedURLException{
		return domusAdapterService.getDocumentiProtocollo(docNumber);
	}
	
	public DocumentoProtocolloWithoutSream[]   getDocumentiProtocolloWithoutStream(Integer docNumber) throws ServiceException, RemoteException, MalformedURLException{	
		return domusAdapterService.getDocumentiProtocolloWithoutStream(docNumber);
	}
	
	public FileWithoutStream[]  getFile(Integer idFile) throws RemoteException, MalformedURLException, ServiceException {
		return domusAdapterService.getFile(idFile);		
	}


	/**
	 * @TODO
	 * @param idRegistro
	 * dato l'id registro si ottiene l'idFile dell'allegato..e quindi lo stream del file
	 * @return
	 */
	public Resource getFileStreamAsResourceByIdRegistro(Integer idRegistro) throws ServiceException, IOException, SOAPException {
		//--------------------------------------------
		//
		//--------------------------------------------
		byte[] buffer = null;
		//--------------------------------------------
		Integer idFile=domusRegistryAdapterAllegatiRepository.getidFielFromIdRegistro(idRegistro);
		FileWithoutStream[]  listFfWS=getFile(idFile);
		
		if(listFfWS!=null && listFfWS[0]!=null){
			buffer=listFfWS[0].getStream();			
		}else{
			throw new ServiceException("Non c'è Stream per il File di Id: "+ idFile);
			
		}		
		
		if(buffer==null)
			return null;
		
		return new ByteArrayResource(buffer) {
				@SuppressWarnings("unused")
				@Override
				public String getFilename() {	
					String retval=listFfWS[0].getNome();
					if (listFfWS[0].getEstensione() != null)
						retval = listFfWS[0].getNome().concat(".").concat(listFfWS[0].getEstensione());
					return retval;
				}
			};
	}


	/**
	 * @TODO implementare deve ritornare la signature del protocollo per l'allegato registrato..
	 * @param idRegistro
	 * @return
	 */
	public String getRaggruppamentoByIdRegistro(Integer idRegistro) {
		// TODO Auto-generated method stub
		return domusRegistryAdapterAllegatiRepository.getRaggruppamentoByIdRegistro(idRegistro);
	}
	
	
	public void downloadDocumentById(HttpServletResponse response, String id)
			throws IOException, NumberFormatException, ServiceException {

		if (id == null)
			throw new IOException();

		FileWithoutStream[] lFileWithoutStream = getFile(new Integer(id));

		byte[] buffer = lFileWithoutStream[0].getStream();
		if (buffer != null) {
			InputStream stream = new ByteArrayInputStream(buffer);
			String mimeType = lFileWithoutStream[0].getMimeType();
			logger.debug("mimetype : " + mimeType);
			response.setContentType(mimeType);
			String filename = DocumentUtils.getFilename(lFileWithoutStream[0].getNome());
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
			response.setContentLength(buffer.length);
			// closes both streams.
			FileCopyUtils.copy(stream, response.getOutputStream());
		}
	}
	
	private String parse(String ordinanza) {
		String result = null;
		switch (ordinanza) {
			case DbConst.ORDINANZA_4:
			case DbConst.ORDINANZA_8:
				result = DbConst.ORDINANZA_4_8_COD;
				break;
			case DbConst.ORDINANZA_13:
				result = DbConst.ORDINANZA_13_COD;
				break;
			case DbConst.ORDINANZA_19:
				result = DbConst.ORDINANZA_19_COD;
				break;
			case DbConst.ORDINANZA_100:
				result = DbConst.ORDINANZA_100_COD;
				break;
			case DbConst.ORDINANZA_23:
			case DbConst.ORDINANZA_32:
				result = DbConst.ORDINANZA_23_32_COD;
				break;
			case DbConst.ORDINANZA_27:
				result = DbConst.ORDINANZA_27_COD;
				break;
			case DbConst.ORDINANZA_33:
				result = DbConst.ORDINANZA_33_COD;
				break;
			case DbConst.ORDINANZA_37:
				result = DbConst.ORDINANZA_37_COD;
				break;
			case DbConst.ORDINANZA_48:
				result = DbConst.ORDINANZA_48_COD;
				break;
			case DbConst.ORDINANZA_56:
				result = DbConst.ORDINANZA_56_COD;
				break;
			case DbConst.ORDINANZA_64:
				result = DbConst.ORDINANZA_64_COD;
				break;
			case DbConst.ORDINANZA_77:
				result = DbConst.ORDINANZA_77_COD;
				break;
			case DbConst.ORDINANZA_14:
				result = DbConst.ORDINANZA_14_COD;
				break;
			default: break;
		}
		
		return result;
	}
    
}
