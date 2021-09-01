/**
 * GetInfoProtocolloReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class GetInfoProtocolloReq  extends org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.BaseReq  implements java.io.Serializable {
    private java.lang.String codiceFascicolo;

    private java.lang.Integer docNumber;

    private java.lang.Boolean isDocInterno;

    private java.lang.String numeroIntervento;

    private java.lang.String tipoDocumentoInvio;

    public GetInfoProtocolloReq() {
    }

    public GetInfoProtocolloReq(
           java.lang.String password,
           java.lang.String userID,
           java.lang.String codiceFascicolo,
           java.lang.Integer docNumber,
           java.lang.Boolean isDocInterno,
           java.lang.String numeroIntervento,
           java.lang.String tipoDocumentoInvio) {
        super(
            password,
            userID);
        this.codiceFascicolo = codiceFascicolo;
        this.docNumber = docNumber;
        this.isDocInterno = isDocInterno;
        this.numeroIntervento = numeroIntervento;
        this.tipoDocumentoInvio = tipoDocumentoInvio;
    }


    /**
     * Gets the codiceFascicolo value for this GetInfoProtocolloReq.
     * 
     * @return codiceFascicolo
     */
    public java.lang.String getCodiceFascicolo() {
        return codiceFascicolo;
    }


    /**
     * Sets the codiceFascicolo value for this GetInfoProtocolloReq.
     * 
     * @param codiceFascicolo
     */
    public void setCodiceFascicolo(java.lang.String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }


    /**
     * Gets the docNumber value for this GetInfoProtocolloReq.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this GetInfoProtocolloReq.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the isDocInterno value for this GetInfoProtocolloReq.
     * 
     * @return isDocInterno
     */
    public java.lang.Boolean getIsDocInterno() {
        return isDocInterno;
    }


    /**
     * Sets the isDocInterno value for this GetInfoProtocolloReq.
     * 
     * @param isDocInterno
     */
    public void setIsDocInterno(java.lang.Boolean isDocInterno) {
        this.isDocInterno = isDocInterno;
    }


    /**
     * Gets the numeroIntervento value for this GetInfoProtocolloReq.
     * 
     * @return numeroIntervento
     */
    public java.lang.String getNumeroIntervento() {
        return numeroIntervento;
    }


    /**
     * Sets the numeroIntervento value for this GetInfoProtocolloReq.
     * 
     * @param numeroIntervento
     */
    public void setNumeroIntervento(java.lang.String numeroIntervento) {
        this.numeroIntervento = numeroIntervento;
    }


    /**
     * Gets the tipoDocumentoInvio value for this GetInfoProtocolloReq.
     * 
     * @return tipoDocumentoInvio
     */
    public java.lang.String getTipoDocumentoInvio() {
        return tipoDocumentoInvio;
    }


    /**
     * Sets the tipoDocumentoInvio value for this GetInfoProtocolloReq.
     * 
     * @param tipoDocumentoInvio
     */
    public void setTipoDocumentoInvio(java.lang.String tipoDocumentoInvio) {
        this.tipoDocumentoInvio = tipoDocumentoInvio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetInfoProtocolloReq)) return false;
        GetInfoProtocolloReq other = (GetInfoProtocolloReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceFascicolo==null && other.getCodiceFascicolo()==null) || 
             (this.codiceFascicolo!=null &&
              this.codiceFascicolo.equals(other.getCodiceFascicolo()))) &&
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.isDocInterno==null && other.getIsDocInterno()==null) || 
             (this.isDocInterno!=null &&
              this.isDocInterno.equals(other.getIsDocInterno()))) &&
            ((this.numeroIntervento==null && other.getNumeroIntervento()==null) || 
             (this.numeroIntervento!=null &&
              this.numeroIntervento.equals(other.getNumeroIntervento()))) &&
            ((this.tipoDocumentoInvio==null && other.getTipoDocumentoInvio()==null) || 
             (this.tipoDocumentoInvio!=null &&
              this.tipoDocumentoInvio.equals(other.getTipoDocumentoInvio())));
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
        if (getCodiceFascicolo() != null) {
            _hashCode += getCodiceFascicolo().hashCode();
        }
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getIsDocInterno() != null) {
            _hashCode += getIsDocInterno().hashCode();
        }
        if (getNumeroIntervento() != null) {
            _hashCode += getNumeroIntervento().hashCode();
        }
        if (getTipoDocumentoInvio() != null) {
            _hashCode += getTipoDocumentoInvio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetInfoProtocolloReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetInfoProtocolloReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "CodiceFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDocInterno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IsDocInterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "NumeroIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumentoInvio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "TipoDocumentoInvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
