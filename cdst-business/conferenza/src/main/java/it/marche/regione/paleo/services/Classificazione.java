/**
 * Classificazione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class Classificazione  implements java.io.Serializable {
    private java.lang.String codiceFascicolo;

    private it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo;

    public Classificazione() {
    }

    public Classificazione(
           java.lang.String codiceFascicolo,
           it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo) {
           this.codiceFascicolo = codiceFascicolo;
           this.nuovoFascicolo = nuovoFascicolo;
    }


    /**
     * Gets the codiceFascicolo value for this Classificazione.
     * 
     * @return codiceFascicolo
     */
    public java.lang.String getCodiceFascicolo() {
        return codiceFascicolo;
    }


    /**
     * Sets the codiceFascicolo value for this Classificazione.
     * 
     * @param codiceFascicolo
     */
    public void setCodiceFascicolo(java.lang.String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }


    /**
     * Gets the nuovoFascicolo value for this Classificazione.
     * 
     * @return nuovoFascicolo
     */
    public it.marche.regione.paleo.services.DatiNuovoFascicolo getNuovoFascicolo() {
        return nuovoFascicolo;
    }


    /**
     * Sets the nuovoFascicolo value for this Classificazione.
     * 
     * @param nuovoFascicolo
     */
    public void setNuovoFascicolo(it.marche.regione.paleo.services.DatiNuovoFascicolo nuovoFascicolo) {
        this.nuovoFascicolo = nuovoFascicolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Classificazione)) return false;
        Classificazione other = (Classificazione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFascicolo==null && other.getCodiceFascicolo()==null) || 
             (this.codiceFascicolo!=null &&
              this.codiceFascicolo.equals(other.getCodiceFascicolo()))) &&
            ((this.nuovoFascicolo==null && other.getNuovoFascicolo()==null) || 
             (this.nuovoFascicolo!=null &&
              this.nuovoFascicolo.equals(other.getNuovoFascicolo())));
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
        if (getCodiceFascicolo() != null) {
            _hashCode += getCodiceFascicolo().hashCode();
        }
        if (getNuovoFascicolo() != null) {
            _hashCode += getNuovoFascicolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Classificazione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nuovoFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NuovoFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiNuovoFascicolo"));
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
