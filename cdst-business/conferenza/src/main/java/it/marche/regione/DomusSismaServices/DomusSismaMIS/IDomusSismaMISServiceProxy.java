package it.marche.regione.DomusSismaServices.DomusSismaMIS;

public class IDomusSismaMISServiceProxy implements it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService {
  private String _endpoint = null;
  private it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService iDomusSismaMISService = null;
  
  public IDomusSismaMISServiceProxy() {
    _initIDomusSismaMISServiceProxy();
  }
  
  public IDomusSismaMISServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIDomusSismaMISServiceProxy();
  }
  
  private void _initIDomusSismaMISServiceProxy() {
    try {
      iDomusSismaMISService = (new org.tempuri.DomusSismaMISServiceLocator()).getBasicHttpBinding_IDomusSismaMISService();
      if (iDomusSismaMISService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iDomusSismaMISService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iDomusSismaMISService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iDomusSismaMISService != null)
      ((javax.xml.rpc.Stub)iDomusSismaMISService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService getIDomusSismaMISService() {
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService;
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1 getPratiche(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getPratiche(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1 getPraticheMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getPraticheMIS(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1 getProtocolliPratica(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getProtocolliPratica(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1 getProtocolliPraticaAll(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getProtocolliPraticaAll(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1 updateStatoPraticaMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateStatoPraticaMISReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.updateStatoPraticaMIS(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1 getXmlIstanza(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getXmlIstanza(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1 getDocumentiProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getDocumentiProtocollo(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1 getDocumentiProtocolloWithoutStream(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getDocumentiProtocolloWithoutStream(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1 getFile(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getFile(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1 sendDocumentiMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.sendDocumentiMIS(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1 getWorkflow(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetWorkflowReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getWorkflow(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1 getProtocolliPraticaOOPP(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaOOPPReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getProtocolliPraticaOOPP(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1 getIstances(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIstancesReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getIstances(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring checkStatoProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoProtocolloReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.checkStatoProtocollo(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1 getProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIProcessStateReq request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.getProcessState(request);
  }
  
  public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1 setProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoOperationState request) throws java.rmi.RemoteException{
    if (iDomusSismaMISService == null)
      _initIDomusSismaMISServiceProxy();
    return iDomusSismaMISService.setProcessState(request);
  }
  
  
}