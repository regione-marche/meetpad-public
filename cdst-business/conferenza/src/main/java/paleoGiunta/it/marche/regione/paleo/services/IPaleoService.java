/**
 * IPaleoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public interface IPaleoService extends java.rmi.Remote {
    public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo protocollazioneEntrata(paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza protocollazionePartenza(paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.RespDocumento archiviaDocumentoInterno(paleoGiunta.it.marche.regione.paleo.services.ReqDocumento richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfTupleOfintint5F2DSckg addVersioneDocumento(paleoGiunta.it.marche.regione.paleo.services.ReqAddVersione req) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati addAllegatiDocumentoProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt cercaDocumentoProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5 cercaProtocolliCorrispondente(paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocolliCorrispondente richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo spedisciProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqSpedisciProtocollo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5 getDocumentiProtocolliInFascicolo(paleoGiunta.it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 getOperatoriAttivi() throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 getOperatori(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 findOperatori(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5 findUtenti(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5 getRagioniTrasmissione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5 getRegistri(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubrica(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, java.lang.String cognome) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubricaExt(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, paleoGiunta.it.marche.regione.paleo.services.ReqFindRubrica richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.Rubrica saveVoceRubrica(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, paleoGiunta.it.marche.regione.paleo.services.Rubrica voce) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.Registro apriRegistro(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.Registro chiudiRegistro(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBase apriFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBase chiudiFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5 getTipiDatiFascicoli(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5 getSerieArchivisticheFascicoli(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.DataGenerica getScadenzaPassword(java.lang.String userid, java.lang.String codAmm) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBase cambiaPassword(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String oldPwd, java.lang.String newPwd) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBase effettuaTrasmissioni(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.TrasmissioneDoc[] trasmissioni, java.lang.Boolean inviaMail) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5 getUO(java.lang.String codiceRegistro) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfstring inserisciClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.Classificazione2 classificazione, java.lang.Integer docNumber) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfstring eliminaClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceFascicolo, java.lang.Integer docNumber) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5 findIpa(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String descrizione, org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Providers.TipoOggettoIPA tipoOggetto) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5 getFile(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo op, java.lang.Integer idFile) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5 creaFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo op, paleoGiunta.it.marche.regione.paleo.services.ReqCreaFascicolo richiesta) throws java.rmi.RemoteException;
    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5 getFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo op, java.lang.String codiceFascicolo) throws java.rmi.RemoteException;
}
