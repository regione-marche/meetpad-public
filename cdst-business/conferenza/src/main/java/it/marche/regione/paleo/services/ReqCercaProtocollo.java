/**
 * ReqCercaProtocollo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqCercaProtocollo  implements java.io.Serializable {
    private java.lang.String docNumber;

    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    private java.lang.String segnatura;

    private java.lang.Boolean withouthStream;

    public ReqCercaProtocollo() {
    }

    public ReqCercaProtocollo(
           java.lang.String docNumber,
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           java.lang.String segnatura,
           java.lang.Boolean withouthStream) {
           this.docNumber = docNumber;
           this.operatore = operatore;
           this.segnatura = segnatura;
           this.withouthStream = withouthStream;
    }


    /**
     * Gets the docNumber value for this ReqCercaProtocollo.
     * 
     * @return docNumber
     */
    public java.lang.String getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this ReqCercaProtocollo.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.String docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the operatore value for this ReqCercaProtocollo.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqCercaProtocollo.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }


    /**
     * Gets the segnatura value for this ReqCercaProtocollo.
     * 
     * @return segnatura
     */
    public java.lang.String getSegnatura() {
        return segnatura;
    }


    /**
     * Sets the segnatura value for this ReqCercaProtocollo.
     * 
     * @param segnatura
     */
    public void setSegnatura(java.lang.String segnatura) {
        this.segnatura = segnatura;
    }


    /**
     * Gets the withouthStream value for this ReqCercaProtocollo.
     * 
     * @return withouthStream
     */
    public java.lang.Boolean getWithouthStream() {
        return withouthStream;
    }


    /**
     * Sets the withouthStream value for this ReqCercaProtocollo.
     * 
     * @param withouthStream
     */
    public void setWithouthStream(java.lang.Boolean withouthStream) {
        this.withouthStream = withouthStream;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqCercaProtocollo)) return false;
        ReqCercaProtocollo other = (ReqCercaProtocollo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore()))) &&
            ((this.segnatura==null && other.getSegnatura()==null) || 
             (this.segnatura!=null &&
              this.segnatura.equals(other.getSegnatura()))) &&
            ((this.withouthStream==null && other.getWithouthStream()==null) || 
             (this.withouthStream!=null &&
              this.withouthStream.equals(other.getWithouthStream())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        if (getSegnatura() != null) {
            _hashCode += getSegnatura().hashCode();
        }
        if (getWithouthStream() != null) {
            _hashCode += getWithouthStream().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqCercaProtocollo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocollo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Operatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnatura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Segnatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withouthStream");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "WithouthStream"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
