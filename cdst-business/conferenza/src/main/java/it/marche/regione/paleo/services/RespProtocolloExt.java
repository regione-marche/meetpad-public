/**
 * RespProtocolloExt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespProtocolloExt  extends it.marche.regione.paleo.services.RespDocumentoExt  implements java.io.Serializable {
    private java.lang.Boolean annullato;

    private java.util.Calendar data;

    private java.util.Calendar dataProtocollazione;

    private java.lang.String numero;

    private java.lang.String registro;

    private java.lang.String segnatura;

    public RespProtocolloExt() {
    }

    public RespProtocolloExt(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String[] classificazioni,
           java.util.Calendar dataDocumento,
           it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento,
           int docNumber,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo proprietario,
           java.lang.String segnaturaDocumento,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento,
           it.marche.regione.paleo.services.Allegato[] allegati,
           it.marche.regione.paleo.services.File documentoPrincipale,
           java.lang.Boolean annullato,
           java.util.Calendar data,
           java.util.Calendar dataProtocollazione,
           java.lang.String numero,
           java.lang.String registro,
           java.lang.String segnatura) {
        super(
            messaggioRisultato,
            classificazioni,
            dataDocumento,
            datiProcedimento,
            docNumber,
            oggetto,
            proprietario,
            segnaturaDocumento,
            tipoDocumento,
            allegati,
            documentoPrincipale);
        this.annullato = annullato;
        this.data = data;
        this.dataProtocollazione = dataProtocollazione;
        this.numero = numero;
        this.registro = registro;
        this.segnatura = segnatura;
    }


    /**
     * Gets the annullato value for this RespProtocolloExt.
     * 
     * @return annullato
     */
    public java.lang.Boolean getAnnullato() {
        return annullato;
    }


    /**
     * Sets the annullato value for this RespProtocolloExt.
     * 
     * @param annullato
     */
    public void setAnnullato(java.lang.Boolean annullato) {
        this.annullato = annullato;
    }


    /**
     * Gets the data value for this RespProtocolloExt.
     * 
     * @return data
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this RespProtocolloExt.
     * 
     * @param data
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the dataProtocollazione value for this RespProtocolloExt.
     * 
     * @return dataProtocollazione
     */
    public java.util.Calendar getDataProtocollazione() {
        return dataProtocollazione;
    }


    /**
     * Sets the dataProtocollazione value for this RespProtocolloExt.
     * 
     * @param dataProtocollazione
     */
    public void setDataProtocollazione(java.util.Calendar dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }


    /**
     * Gets the numero value for this RespProtocolloExt.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this RespProtocolloExt.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }


    /**
     * Gets the registro value for this RespProtocolloExt.
     * 
     * @return registro
     */
    public java.lang.String getRegistro() {
        return registro;
    }


    /**
     * Sets the registro value for this RespProtocolloExt.
     * 
     * @param registro
     */
    public void setRegistro(java.lang.String registro) {
        this.registro = registro;
    }


    /**
     * Gets the segnatura value for this RespProtocolloExt.
     * 
     * @return segnatura
     */
    public java.lang.String getSegnatura() {
        return segnatura;
    }


    /**
     * Sets the segnatura value for this RespProtocolloExt.
     * 
     * @param segnatura
     */
    public void setSegnatura(java.lang.String segnatura) {
        this.segnatura = segnatura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespProtocolloExt)) return false;
        RespProtocolloExt other = (RespProtocolloExt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.annullato==null && other.getAnnullato()==null) || 
             (this.annullato!=null &&
              this.annullato.equals(other.getAnnullato()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.dataProtocollazione==null && other.getDataProtocollazione()==null) || 
             (this.dataProtocollazione!=null &&
              this.dataProtocollazione.equals(other.getDataProtocollazione()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.registro==null && other.getRegistro()==null) || 
             (this.registro!=null &&
              this.registro.equals(other.getRegistro()))) &&
            ((this.segnatura==null && other.getSegnatura()==null) || 
             (this.segnatura!=null &&
              this.segnatura.equals(other.getSegnatura())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getAnnullato() != null) {
            _hashCode += getAnnullato().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getDataProtocollazione() != null) {
            _hashCode += getDataProtocollazione().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getRegistro() != null) {
            _hashCode += getRegistro().hashCode();
        }
        if (getSegnatura() != null) {
            _hashCode += getSegnatura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespProtocolloExt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloExt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annullato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Annullato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtocollazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataProtocollazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Registro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnatura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Segnatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
