/**
 * IDomusSismaMISService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.DomusSismaServices.DomusSismaMIS;

public interface IDomusSismaMISService extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1 getPratiche(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1 getPraticheMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1 getProtocolliPratica(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1 getProtocolliPraticaAll(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1 updateStatoPraticaMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateStatoPraticaMISReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1 getXmlIstanza(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1 getDocumentiProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1 getDocumentiProtocolloWithoutStream(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1 getFile(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1 sendDocumentiMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1 getWorkflow(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetWorkflowReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1 getProtocolliPraticaOOPP(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaOOPPReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1 getIstances(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIstancesReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring checkStatoProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoProtocolloReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1 getProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIProcessStateReq request) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1 setProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoOperationState request) throws java.rmi.RemoteException;
}
