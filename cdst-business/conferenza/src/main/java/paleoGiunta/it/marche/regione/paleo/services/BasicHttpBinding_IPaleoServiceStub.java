/**
 * BasicHttpBinding_IPaleoServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

import java.rmi.RemoteException;

public class BasicHttpBinding_IPaleoServiceStub extends org.apache.axis.client.Stub implements paleoGiunta.it.marche.regione.paleo.services.IPaleoService {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[32];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("EliminaClassificazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "codiceFascicolo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "docNumber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfstring"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfstring.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "EliminaClassificazioneResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindIpa");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "descrizione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "tipoOggetto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Providers", "TipoOggettoIPA"), org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Providers.TipoOggettoIPA.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfIpaEntryRz2BRIZ5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindIpaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetFile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "op"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "idFile"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBaseOfFileZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetFileResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CreaFascicolo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "op"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCreaFascicolo"), paleoGiunta.it.marche.regione.paleo.services.ReqCreaFascicolo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBaseOfFascicoloInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CreaFascicoloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ProtocollazioneEntrata");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocolloArrivo"), paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloArrivo"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocollazioneEntrataResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ProtocollazionePartenza");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocolloPartenza"), paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloPartenza"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocollazionePartenzaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ArchiviaDocumentoInterno");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocumento"), paleoGiunta.it.marche.regione.paleo.services.ReqDocumento.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocumento"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespDocumento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArchiviaDocumentoInternoResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AddAllegatiDocumentoProtocollo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqAddAllegati"), paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respAddAllegati"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AddAllegatiDocumentoProtocolloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CercaDocumentoProtocollo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocollo"), paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RespDocumentoExt"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CercaDocumentoProtocolloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CercaProtocolliCorrispondente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocolliCorrispondente"), paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocolliCorrispondente.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfrespProtocolloInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CercaProtocolliCorrispondenteResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SpedisciProtocollo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqSpedisciProtocollo"), paleoGiunta.it.marche.regione.paleo.services.ReqSpedisciProtocollo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respSpedisciProtocollo"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SpedisciProtocolloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetDocumentiProtocolliInFascicolo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocProtocolliInFascicolo"), paleoGiunta.it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfrespDocProtocolliInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetDocumentiProtocolliInFascicoloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetOperatori");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfOperatorePaleoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetOperatoriResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindOperatori");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "cognome"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "codiceFiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfOperatorePaleoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindOperatoriResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindUtenti");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "cognome"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "codiceFiscale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfUtentePaleoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindUtentiResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRagioniTrasmissione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRagioneTrasmissioneZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetRagioniTrasmissioneResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetRegistri");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRegistroInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetRegistriResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTitolarioClassificazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfTitolarioInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetTitolarioClassificazioneResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindRubrica");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Cognome"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRubricaZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindRubricaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("FindRubricaExt");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqFindRubrica"), paleoGiunta.it.marche.regione.paleo.services.ReqFindRubrica.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRubricaZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindRubricaExtResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SaveVoceRubrica");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "operatore"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "voce"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Rubrica"), paleoGiunta.it.marche.regione.paleo.services.Rubrica.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Rubrica"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.Rubrica.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SaveVoceRubricaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ApriRegistro");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRegistro"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Registro"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.Registro.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ApriRegistroResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ChiudiRegistro");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRegistro"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Registro"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.Registro.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ChiudiRegistroResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ApriFascicolo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqApriChiudiFascicolo"), paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ApriFascicoloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ChiudiFascicolo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "richiesta"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqApriChiudiFascicolo"), paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ChiudiFascicoloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTipiDatiFascicoli");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfTipoDatiFascicoloZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetTipiDatiFascicoliResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetSerieArchivisticheFascicoli");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfSerieArchivisticaZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetSerieArchivisticheFascicoliResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetScadenzaPassword");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodAmm"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataGenerica"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.DataGenerica.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetScadenzaPasswordResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CambiaPassword");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OldPwd"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NewPwd"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CambiaPasswordResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("EffettuaTrasmissioni");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "trasmissioni"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTrasmissioneDoc"), paleoGiunta.it.marche.regione.paleo.services.TrasmissioneDoc[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneDoc"));
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "EffettuaTrasmissioniResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUO");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "codiceRegistro"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfUOInfoZA0hwLp5"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetUOResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("InserisciClassificazione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "opp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"), paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "classificazione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione2"), paleoGiunta.it.marche.regione.paleo.services.Classificazione2.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "docNumber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfstring"));
        oper.setReturnClass(paleoGiunta.it.marche.regione.paleo.services.BEListOfstring.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "InserisciClassificazioneResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

    }

    public BasicHttpBinding_IPaleoServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_IPaleoServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_IPaleoServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Allegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.AllegatoInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfAllegato");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Allegato[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfAllegatoInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.AllegatoInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfClassificazione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Classificazione[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfCorrispondente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Corrispondente[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondente");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondente");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfDestinatarioInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DestinatarioInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfDestinatarioInfoInterop");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DestinatarioInfoInterop[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfMessaggioInteropInfo2");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.MessaggioInteropInfo2[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfMessaggioPostaInfo2");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.MessaggioPostaInfo2[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfOperatorePaleo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfRagioneTrasmissione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RagioneTrasmissione[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RagioneTrasmissione");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RagioneTrasmissione");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfRegistroInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RegistroInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RegistroInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RegistroInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfrespDocProtocolliInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespDocProtocolliInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocProtocolliInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocProtocolliInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfrespProtocolloInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfRubrica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Rubrica[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Rubrica");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Rubrica");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfSerieArchivistica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.SerieArchivistica[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SerieArchivistica");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SerieArchivistica");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTipoDatiFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoDatiFascicolo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDatiFascicolo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDatiFascicolo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTitolarioInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TitolarioInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TitolarioInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TitolarioInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTrasmissioneDoc");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneDoc[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneDoc");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneDoc");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTrasmissioneRuolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfTrasmissioneUtente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfUOInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.UOInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UOInfo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UOInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArrayOfUtentePaleo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.UtentePaleo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UtentePaleo");
            qName2 = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UtentePaleo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBaseOfFascicoloInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBaseOfFileZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfIpaEntryRz2BRIZ5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfOperatorePaleoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRagioneTrasmissioneZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRegistroInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfrespDocProtocolliInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfrespProtocolloInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfRubricaZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfSerieArchivisticaZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfstring");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfstring.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfTipoDatiFascicoloZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfTitolarioInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfUOInfoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfUtentePaleoZA0hwLp5");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Classificazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione2");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Classificazione2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Corrispondente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataGenerica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DataGenerica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiCorrispondente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiEmergenza");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DatiEmergenza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiNuovoFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DatiNuovoFascicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiProcedimento");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DatiProcedimento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DestinatarioInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.DestinatarioInfoInterop.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FascicoloInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.FascicoloInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "File");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.File.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.MessaggioInteropInfo2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.MessaggioPostaInfo2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioRisultato");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Messaggio");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Messaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProcedimentoInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ProcedimentoInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RagioneTrasmissione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RagioneTrasmissione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Registro");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Registro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RegistroInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RegistroInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqAddAllegati");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqApriChiudiFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocolliCorrispondente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocolliCorrispondente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocollo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCreaFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqCreaFascicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocProtocolliInFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocumento");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqFindRubrica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqFindRubrica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocollo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocolloArrivo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocolloPartenza");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqSpedisciProtocollo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.ReqSpedisciProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respAddAllegati");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocProtocolliInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespDocProtocolliInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocumento");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RespDocumentoExt");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocollo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloArrivo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloArrivoExt");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivoExt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloExt");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloExt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloPartenza");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloPartenzaExt");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenzaExt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respSpedisciProtocollo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Rubrica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Rubrica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SerieArchivistica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.SerieArchivistica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDatiFascicolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoDatiFascicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDestinatarioTrasmissione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoDestinatarioTrasmissione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoOriginale");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoOriginale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoProcedimento");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoProcedimento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoRisultato");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoRisultato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoVoceRubrica");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TipoVoceRubrica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TitolarioInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TitolarioInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Trasmissione");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.Trasmissione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneDoc");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneDoc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UOInfo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.UOInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UtentePaleo");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.it.marche.regione.paleo.services.UtentePaleo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoDirezioneMessaggio");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoDirezioneMessaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoMessaggioInterop");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoMessaggioInterop.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoStatoSpedizione");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoStatoSpedizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "ArrayOfIpaEntry");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "IpaEntry");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "IpaEntry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "IpaEntry");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoDocumento");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "MessaggioClass");
            cachedSerQNames.add(qName);
            cls = paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.MessaggioClass.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoProtocollo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoStatoProtocollo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Providers", "TipoOggettoIPA");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Providers.TipoOggettoIPA.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfstring eliminaClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceFascicolo, java.lang.Integer docNumber) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/EliminaClassificazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "EliminaClassificazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, codiceFascicolo, docNumber});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfstring) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfstring) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfstring.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5 findIpa(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String descrizione, org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Providers.TipoOggettoIPA tipoOggetto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/FindIpa");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindIpa"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, descrizione, tipoOggetto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfIpaEntryRz2BRIZ5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5 getFile(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo op, java.lang.Integer idFile) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetFile");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetFile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {op, idFile});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5 creaFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo op, paleoGiunta.it.marche.regione.paleo.services.ReqCreaFascicolo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/CreaFascicolo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CreaFascicolo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {op, richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBaseOfFascicoloInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo protocollazioneEntrata(paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloArrivo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ProtocollazioneEntrata");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocollazioneEntrata"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza protocollazionePartenza(paleoGiunta.it.marche.regione.paleo.services.ReqProtocolloPartenza richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ProtocollazionePartenza");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocollazionePartenza"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespDocumento archiviaDocumentoInterno(paleoGiunta.it.marche.regione.paleo.services.ReqDocumento richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ArchiviaDocumentoInterno");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ArchiviaDocumentoInterno"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespDocumento) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespDocumento) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespDocumento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati addAllegatiDocumentoProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqAddAllegati richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/AddAllegatiDocumentoProtocollo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AddAllegatiDocumentoProtocollo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespAddAllegati.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt cercaDocumentoProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocollo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/CercaDocumentoProtocollo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CercaDocumentoProtocollo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespDocumentoExt.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5 cercaProtocolliCorrispondente(paleoGiunta.it.marche.regione.paleo.services.ReqCercaProtocolliCorrispondente richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/CercaProtocolliCorrispondente");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CercaProtocolliCorrispondente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfrespProtocolloInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo spedisciProtocollo(paleoGiunta.it.marche.regione.paleo.services.ReqSpedisciProtocollo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/SpedisciProtocollo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SpedisciProtocollo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.RespSpedisciProtocollo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5 getDocumentiProtocolliInFascicolo(paleoGiunta.it.marche.regione.paleo.services.ReqDocProtocolliInFascicolo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetDocumentiProtocolliInFascicolo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetDocumentiProtocolliInFascicolo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 getOperatori(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetOperatori");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetOperatori"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5 findOperatori(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/FindOperatori");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindOperatori"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cognome, codiceFiscale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfOperatorePaleoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5 findUtenti(java.lang.String cognome, java.lang.String codiceFiscale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/FindUtenti");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindUtenti"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cognome, codiceFiscale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfUtentePaleoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5 getRagioniTrasmissione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetRagioniTrasmissione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetRagioniTrasmissione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfRagioneTrasmissioneZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5 getRegistri(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetRegistri");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetRegistri"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfRegistroInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetTitolarioClassificazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetTitolarioClassificazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubrica(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, java.lang.String cognome) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/FindRubrica");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindRubrica"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore, cognome});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5 findRubricaExt(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, paleoGiunta.it.marche.regione.paleo.services.ReqFindRubrica richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/FindRubricaExt");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FindRubricaExt"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore, richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfRubricaZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.Rubrica saveVoceRubrica(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatore, paleoGiunta.it.marche.regione.paleo.services.Rubrica voce) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/SaveVoceRubrica");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SaveVoceRubrica"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operatore, voce});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.Rubrica) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.Rubrica) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.Rubrica.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.Registro apriRegistro(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ApriRegistro");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ApriRegistro"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, codiceRegistro});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.Registro) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.Registro) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.Registro.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.Registro chiudiRegistro(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String codiceRegistro) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ChiudiRegistro");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ChiudiRegistro"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, codiceRegistro});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.Registro) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.Registro) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.Registro.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBase apriFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ApriFascicolo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ApriFascicolo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBase chiudiFascicolo(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.ReqApriChiudiFascicolo richiesta) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/ChiudiFascicolo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ChiudiFascicolo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, richiesta});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5 getTipiDatiFascicoli(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetTipiDatiFascicoli");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetTipiDatiFascicoli"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfTipoDatiFascicoloZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5 getSerieArchivisticheFascicoli(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetSerieArchivisticheFascicoli");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        //DLG!!!!!
        _call.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetSerieArchivisticheFascicoli"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.DataGenerica getScadenzaPassword(java.lang.String userid, java.lang.String codAmm) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetScadenzaPassword");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetScadenzaPassword"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userid, codAmm});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.DataGenerica) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.DataGenerica) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.DataGenerica.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBase cambiaPassword(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, java.lang.String oldPwd, java.lang.String newPwd) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/CambiaPassword");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CambiaPassword"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, oldPwd, newPwd});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEBase effettuaTrasmissioni(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.TrasmissioneDoc[] trasmissioni,Boolean inviaMail) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/EffettuaTrasmissioni");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "EffettuaTrasmissioni"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, trasmissioni,inviaMail});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEBase) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEBase.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5 getUO(java.lang.String codiceRegistro) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/GetUO");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GetUO"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codiceRegistro});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfUOInfoZA0HwLp5.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public paleoGiunta.it.marche.regione.paleo.services.BEListOfstring inserisciClassificazione(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo opp, paleoGiunta.it.marche.regione.paleo.services.Classificazione2 classificazione, java.lang.Integer docNumber) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://paleo.regione.marche.it/services/IPaleoService/InserisciClassificazione");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "InserisciClassificazione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opp, classificazione, docNumber});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfstring) _resp;
            } catch (java.lang.Exception _exception) {
                return (paleoGiunta.it.marche.regione.paleo.services.BEListOfstring) org.apache.axis.utils.JavaUtils.convert(_resp, paleoGiunta.it.marche.regione.paleo.services.BEListOfstring.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

	@Override
	public BEBaseOfTupleOfintint5F2DSckg addVersioneDocumento(ReqAddVersione req) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BEListOfOperatorePaleoZA0HwLp5 getOperatoriAttivi() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public BEBaseOfFascicoloInfoZA0HwLp5 getFascicolo(OperatorePaleo op, String codiceFascicolo)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
