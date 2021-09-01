/**
 * BEBase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class BEBase  implements java.io.Serializable {
    private it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato;

    public BEBase() {
    }

    public BEBase(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato) {
           this.messaggioRisultato = messaggioRisultato;
    }


    /**
     * Gets the messaggioRisultato value for this BEBase.
     * 
     * @return messaggioRisultato
     */
    public it.marche.regione.paleo.services.MessaggioRisultato getMessaggioRisultato() {
        return messaggioRisultato;
    }


    /**
     * Sets the messaggioRisultato value for this BEBase.
     * 
     * @param messaggioRisultato
     */
    public void setMessaggioRisultato(it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato) {
        this.messaggioRisultato = messaggioRisultato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BEBase)) return false;
        BEBase other = (BEBase) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.messaggioRisultato==null && other.getMessaggioRisultato()==null) || 
             (this.messaggioRisultato!=null &&
              this.messaggioRisultato.equals(other.getMessaggioRisultato())));
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
        if (getMessaggioRisultato() != null) {
            _hashCode += getMessaggioRisultato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BEBase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEBase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggioRisultato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioRisultato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioRisultato"));
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
