/**
 * ReqSpedisciProtocollo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqSpedisciProtocollo  implements java.io.Serializable {
    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    private java.lang.String segnatura;

    public ReqSpedisciProtocollo() {
    }

    public ReqSpedisciProtocollo(
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           java.lang.String segnatura) {
           this.operatore = operatore;
           this.segnatura = segnatura;
    }


    /**
     * Gets the operatore value for this ReqSpedisciProtocollo.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqSpedisciProtocollo.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }


    /**
     * Gets the segnatura value for this ReqSpedisciProtocollo.
     * 
     * @return segnatura
     */
    public java.lang.String getSegnatura() {
        return segnatura;
    }


    /**
     * Sets the segnatura value for this ReqSpedisciProtocollo.
     * 
     * @param segnatura
     */
    public void setSegnatura(java.lang.String segnatura) {
        this.segnatura = segnatura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqSpedisciProtocollo)) return false;
        ReqSpedisciProtocollo other = (ReqSpedisciProtocollo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore()))) &&
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
        int _hashCode = 1;
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        if (getSegnatura() != null) {
            _hashCode += getSegnatura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqSpedisciProtocollo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqSpedisciProtocollo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
