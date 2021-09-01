package it.marche.regione.paleo.services;

public class IPaleoServiceProxy implements it.marche.regione.paleo.services.IPaleoService {
  private String _endpoint = null;
  private it.marche.regione.paleo.services.IPaleoService iPaleoService = null;
  
  public IPaleoServiceProxy() {
    _initIPaleoServiceProxy();
  }
  
  public IPaleoServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIPaleoServiceProxy();
  }
  
  private void _initIPaleoServiceProxy() {
    try {
      iPaleoService = (new it.marche.regione.paleo.services.PaleoServiceLocator()).getBasicHttpBinding_IPaleoService();
      if (iPaleoService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iPaleoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iPaleoService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iPaleoService != null)
      ((javax.xml.rpc.Stub)iPaleoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.marche.regione.paleo.services.IPaleoService getIPaleoService() {
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService;
  }
  
  public it.marche.regione.paleo.services.BEListOfstring eliminaClassificazione(it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceFascicolo, java.lang.Integer docNumber) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.eliminaClassificazione(opp, codiceFascicolo, docNumber);
  }
  
  public it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5 findIpa(it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String descrizione, org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Providers.TipoOggettoIPA tipoOggetto) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.findIpa(opp, descrizione, tipoOggetto);
  }
  
  public it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5 getFile(it.marche.regione.paleo.services.OperatorePaleo op, java.lang.Integer idFile) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getFile(op, idFile);
  }
  
  public it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5 creaFascicolo(it.marche.regione.paleo.services.OperatorePaleo op, it.marche.regione.paleo.services.ReqCreaFascicolo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.creaFascicolo(op, richiesta);
  }
  
  public it.marche.regione.paleo.services.RespProtocolloArrivo protocollazioneEntrata(it.marche.regione.paleo.services.ReqProtocolloArrivo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.protocollazioneEntrata(richiesta);
  }
  
  public it.marche.regione.paleo.services.RespProtocolloPartenza protocollazionePartenza(it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.protocollazionePartenza(richiesta);
  }
  
  public it.marche.regione.paleo.services.RespDocumento archiviaDocumentoInterno(it.marche.regione.paleo.services.ReqDocumento richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.archiviaDocumentoInterno(richiesta);
  }
  
  public it.marche.regione.paleo.services.RespAddAllegati addAllegatiDocumentoProtocollo(it.marche.regione.paleo.services.ReqAddAllegati richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.addAllegatiDocumentoProtocollo(richiesta);
  }
  
  public it.marche.regione.paleo.services.RespDocumentoExt cercaDocumentoProtocollo(it.marche.regione.paleo.services.ReqCercaProtocollo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.cercaDocumentoProtocollo(richiesta);
  }
  
  public it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5 cercaProtocolliCorrispondente(it.marche.regione.paleo.services.ReqCercaProtocolliCorrispondente richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.cercaProtocolliCorrispondente(richiesta);
  }
  
  public it.marche.regione.paleo.services.RespSpedisciProtocollo spedisciProtocollo(it.marche.regione.paleo.services.ReqSpedisciProtocollo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.spedisciProtocollo(richiesta);
  }
  
  public it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5 getDocumentiProtocolliInFascicolo(it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getDocumentiProtocolliInFascicolo(richiesta);
  }
  
  public it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 getOperatori(it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getOperatori(operatore);
  }
  
  public it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 findOperatori(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.findOperatori(cognome, codiceFiscale);
  }
  
  public it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5 findUtenti(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.findUtenti(cognome, codiceFiscale);
  }
  
  public it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5 getRagioniTrasmissione(it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getRagioniTrasmissione(operatore);
  }
  
  public it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5 getRegistri(it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getRegistri(operatore);
  }
  
  public it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione(it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getTitolarioClassificazione(operatore);
  }
  
  public it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubrica(it.marche.regione.paleo.services.OperatorePaleo operatore, java.lang.String cognome) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.findRubrica(operatore, cognome);
  }
  
  public it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubricaExt(it.marche.regione.paleo.services.OperatorePaleo operatore, it.marche.regione.paleo.services.ReqFindRubrica richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.findRubricaExt(operatore, richiesta);
  }
  
  public it.marche.regione.paleo.services.Rubrica saveVoceRubrica(it.marche.regione.paleo.services.OperatorePaleo operatore, it.marche.regione.paleo.services.Rubrica voce) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.saveVoceRubrica(operatore, voce);
  }
  
  public it.marche.regione.paleo.services.Registro apriRegistro(it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.apriRegistro(opp, codiceRegistro);
  }
  
  public it.marche.regione.paleo.services.Registro chiudiRegistro(it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.chiudiRegistro(opp, codiceRegistro);
  }
  
  public it.marche.regione.paleo.services.BEBase apriFascicolo(it.marche.regione.paleo.services.OperatorePaleo opp, it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.apriFascicolo(opp, richiesta);
  }
  
  public it.marche.regione.paleo.services.BEBase chiudiFascicolo(it.marche.regione.paleo.services.OperatorePaleo opp, it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.chiudiFascicolo(opp, richiesta);
  }
  
  public it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5 getTipiDatiFascicoli(it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getTipiDatiFascicoli(opp);
  }
  
  public it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5 getSerieArchivisticheFascicoli(it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getSerieArchivisticheFascicoli(opp);
  }
  
  public it.marche.regione.paleo.services.DataGenerica getScadenzaPassword(java.lang.String userid, java.lang.String codAmm) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getScadenzaPassword(userid, codAmm);
  }
  
  public it.marche.regione.paleo.services.BEBase cambiaPassword(it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String oldPwd, java.lang.String newPwd) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.cambiaPassword(opp, oldPwd, newPwd);
  }
  
  public it.marche.regione.paleo.services.BEBase effettuaTrasmissioni(it.marche.regione.paleo.services.OperatorePaleo opp, it.marche.regione.paleo.services.TrasmissioneDoc[] trasmissioni) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.effettuaTrasmissioni(opp, trasmissioni);
  }
  
  public it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5 getUO(java.lang.String codiceRegistro) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.getUO(codiceRegistro);
  }
  
  public it.marche.regione.paleo.services.BEListOfstring inserisciClassificazione(it.marche.regione.paleo.services.OperatorePaleo opp, it.marche.regione.paleo.services.Classificazione2 classificazione, java.lang.Integer docNumber) throws java.rmi.RemoteException{
    if (iPaleoService == null)
      _initIPaleoServiceProxy();
    return iPaleoService.inserisciClassificazione(opp, classificazione, docNumber);
  }
  
  
}