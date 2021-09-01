/**
 * BasicHttpBinding_IDomusSismaMISServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BasicHttpBinding_IDomusSismaMISServiceStub extends org.apache.axis.client.Stub implements it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[16];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPratiche");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetPraticheReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfPraticaExtendedX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetPraticheResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetPraticheMIS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetPraticheMISReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfPraticaX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetPraticheMISResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetProtocolliPratica");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPraticaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetProtocolliPraticaAll");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaAllReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaExtendedX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPraticaAllResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateStatoPraticaMIS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UpdateStatoPraticaMISReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateStatoPraticaMISReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfUpdateRespX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "UpdateStatoPraticaMISResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetXmlIstanza");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetXmlIstanzaReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfGetXmlIstanzaRespX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetXmlIstanzaResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetDocumentiProtocollo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetDocumentiProtocolloReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfDocumentoProtocolloX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetDocumentiProtocolloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetDocumentiProtocolloWithoutStream");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetDocumentiProtocolloReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetDocumentiProtocolloWithoutStreamResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetFile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetFileReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfFileWithoutStreamX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetFileResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SendDocumentiMIS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "SendDocumentiMISReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfSendDocumentiMISRespX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "SendDocumentiMISResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetWorkflow");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetWorkflowReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetWorkflowReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfFileX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetWorkflowResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetProtocolliPraticaOOPP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaOOPPReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaOOPPReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaOOPPX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPraticaOOPPResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetIstances");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetIstancesReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIstancesReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfLinkViewIstanzeX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetIstancesResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CheckStatoProtocollo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetInfoProtocolloReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoProtocolloReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfstring"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "CheckStatoProtocolloResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetProcessState");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetIProcessStateReq"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIProcessStateReq.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfStatoProcessoX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProcessStateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SetProcessState");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetInfoOperationState"), org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoOperationState.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfStatoAvanzamentoProcessoX2zTrCF1"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "SetProcessStateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

    }

    public BasicHttpBinding_IDomusSismaMISServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_IDomusSismaMISServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_IDomusSismaMISServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfDocumentoProtocolloX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfFileWithoutStreamX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfPraticaExtendedX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfArrayOfPraticaX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfFileX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfGetXmlIstanzaRespX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfLinkViewIstanzeX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaExtendedX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaOOPPX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfPraticaX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfSendDocumentiMISRespX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfStatoAvanzamentoProcessoX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfStatoProcessoX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfstring");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfUpdateRespX2zTrCF1");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "ResultInfo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.ResultInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "TipoRisultato");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.CommonLibrary.TipoRisultato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfDocumentoProtocollo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocollo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocollo");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocollo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfDocumentoProtocolloWithoutSream");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocolloWithoutSream[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocolloWithoutSream");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocolloWithoutSream");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfFile");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfFileWithoutStream");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "FileWithoutStream");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "FileWithoutStream");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfPratica");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Pratica");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Pratica");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfPraticaExtended");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaExtended");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaExtended");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfProtocolloPratica");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ArrayOfStatoProcesso");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoProcesso");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoProcesso");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "BaseReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.BaseReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocollo");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocollo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocolloWithoutSream");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.DocumentoProtocolloWithoutSream.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "FileWithoutStream");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetDocumentiProtocolloReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetFileReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetInfoOperationState");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoOperationState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetInfoProtocolloReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoProtocolloReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetIProcessStateReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIProcessStateReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetIstancesReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIstancesReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetPraticheMISReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetPraticheReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaAllReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaOOPPReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaOOPPReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetProtocolliPraticaReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetWorkflowReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetWorkflowReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetXmlIstanzaReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetXmlIstanzaResp");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "LinkViewIstanze");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.LinkViewIstanze.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Pratica");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaExtended");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaExtended.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaOOPP");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.PraticaOOPP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "SendDocumentiMISReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "SendDocumentiMISResp");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoAvanzamentoProcesso");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoAvanzamentoProcesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoProcesso");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UpdateResp");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UpdateStatoPraticaMISReq");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateStatoPraticaMISReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

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

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1 getPratiche(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetPratiche");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetPratiche"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaExtendedX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1 getPraticheMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetPraticheMISReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetPraticheMIS");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetPraticheMIS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfPraticaX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1 getProtocolliPratica(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetProtocolliPratica");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPratica"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1 getProtocolliPraticaAll(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaAllReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetProtocolliPraticaAll");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPraticaAll"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaExtendedX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1 updateStatoPraticaMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateStatoPraticaMISReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/UpdateStatoPraticaMIS");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "UpdateStatoPraticaMIS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfUpdateRespX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1 getXmlIstanza(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetXmlIstanzaReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetXmlIstanza");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetXmlIstanza"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfGetXmlIstanzaRespX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1 getDocumentiProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetDocumentiProtocollo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetDocumentiProtocollo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1 getDocumentiProtocolloWithoutStream(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetDocumentiProtocolloReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetDocumentiProtocolloWithoutStream");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetDocumentiProtocolloWithoutStream"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfDocumentoProtocolloWithoutSreamX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1 getFile(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetFileReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetFile");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetFile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfArrayOfFileWithoutStreamX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1 sendDocumentiMIS(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.SendDocumentiMISReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/SendDocumentiMIS");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "SendDocumentiMIS"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfSendDocumentiMISRespX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1 getWorkflow(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetWorkflowReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetWorkflow");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetWorkflow"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfFileX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1 getProtocolliPraticaOOPP(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetProtocolliPraticaOOPPReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetProtocolliPraticaOOPP");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProtocolliPraticaOOPP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfPraticaOOPPX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1 getIstances(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIstancesReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetIstances");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetIstances"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfLinkViewIstanzeX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring checkStatoProtocollo(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoProtocolloReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/CheckStatoProtocollo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "CheckStatoProtocollo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfstring.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1 getProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetIProcessStateReq request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/GetProcessState");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "GetProcessState"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoProcessoX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1 setProcessState(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.GetInfoOperationState request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://regione.marche.it/DomusSismaServices/DomusSismaMIS/IDomusSismaMISService/SetProcessState");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://regione.marche.it/DomusSismaServices/DomusSismaMIS", "SetProcessState"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.CommonLibrary.BEBaseOfStatoAvanzamentoProcessoX2ZTrCF1.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
