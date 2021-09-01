/**
 * UpdateStatoPraticaMISReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class UpdateStatoPraticaMISReq  extends org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.BaseReq  implements java.io.Serializable {
    private java.util.Calendar dataConsegnaMIS;

    private java.util.Calendar dataPresaInCaricoMIS;

    private java.lang.String idMIS;

    private java.lang.Integer idRichiesta;

    public UpdateStatoPraticaMISReq() {
    }

    public UpdateStatoPraticaMISReq(
           java.lang.String password,
           java.lang.String userID,
           java.util.Calendar dataConsegnaMIS,
           java.util.Calendar dataPresaInCaricoMIS,
           java.lang.String idMIS,
           java.lang.Integer idRichiesta) {
        super(
            password,
            userID);
        this.dataConsegnaMIS = dataConsegnaMIS;
        this.dataPresaInCaricoMIS = dataPresaInCaricoMIS;
        this.idMIS = idMIS;
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the dataConsegnaMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @return dataConsegnaMIS
     */
    public java.util.Calendar getDataConsegnaMIS() {
        return dataConsegnaMIS;
    }


    /**
     * Sets the dataConsegnaMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @param dataConsegnaMIS
     */
    public void setDataConsegnaMIS(java.util.Calendar dataConsegnaMIS) {
        this.dataConsegnaMIS = dataConsegnaMIS;
    }


    /**
     * Gets the dataPresaInCaricoMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @return dataPresaInCaricoMIS
     */
    public java.util.Calendar getDataPresaInCaricoMIS() {
        return dataPresaInCaricoMIS;
    }


    /**
     * Sets the dataPresaInCaricoMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @param dataPresaInCaricoMIS
     */
    public void setDataPresaInCaricoMIS(java.util.Calendar dataPresaInCaricoMIS) {
        this.dataPresaInCaricoMIS = dataPresaInCaricoMIS;
    }


    /**
     * Gets the idMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @return idMIS
     */
    public java.lang.String getIdMIS() {
        return idMIS;
    }


    /**
     * Sets the idMIS value for this UpdateStatoPraticaMISReq.
     * 
     * @param idMIS
     */
    public void setIdMIS(java.lang.String idMIS) {
        this.idMIS = idMIS;
    }


    /**
     * Gets the idRichiesta value for this UpdateStatoPraticaMISReq.
     * 
     * @return idRichiesta
     */
    public java.lang.Integer getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this UpdateStatoPraticaMISReq.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(java.lang.Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateStatoPraticaMISReq)) return false;
        UpdateStatoPraticaMISReq other = (UpdateStatoPraticaMISReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataConsegnaMIS==null && other.getDataConsegnaMIS()==null) || 
             (this.dataConsegnaMIS!=null &&
              this.dataConsegnaMIS.equals(other.getDataConsegnaMIS()))) &&
            ((this.dataPresaInCaricoMIS==null && other.getDataPresaInCaricoMIS()==null) || 
             (this.dataPresaInCaricoMIS!=null &&
              this.dataPresaInCaricoMIS.equals(other.getDataPresaInCaricoMIS()))) &&
            ((this.idMIS==null && other.getIdMIS()==null) || 
             (this.idMIS!=null &&
              this.idMIS.equals(other.getIdMIS()))) &&
            ((this.idRichiesta==null && other.getIdRichiesta()==null) || 
             (this.idRichiesta!=null &&
              this.idRichiesta.equals(other.getIdRichiesta())));
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
        if (getDataConsegnaMIS() != null) {
            _hashCode += getDataConsegnaMIS().hashCode();
        }
        if (getDataPresaInCaricoMIS() != null) {
            _hashCode += getDataPresaInCaricoMIS().hashCode();
        }
        if (getIdMIS() != null) {
            _hashCode += getIdMIS().hashCode();
        }
        if (getIdRichiesta() != null) {
            _hashCode += getIdRichiesta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateStatoPraticaMISReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UpdateStatoPraticaMISReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataConsegnaMIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DataConsegnaMIS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataPresaInCaricoMIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DataPresaInCaricoMIS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdMIS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
