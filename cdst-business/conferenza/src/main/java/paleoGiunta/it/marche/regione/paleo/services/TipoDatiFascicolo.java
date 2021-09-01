/**
 * TipoDatiFascicolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TipoDatiFascicolo  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTipoDatiFascicolo;

    public TipoDatiFascicolo() {
    }

    public TipoDatiFascicolo(
           java.lang.String descrizione,
           java.lang.Integer idTipoDatiFascicolo) {
           this.descrizione = descrizione;
           this.idTipoDatiFascicolo = idTipoDatiFascicolo;
    }


    /**
     * Gets the descrizione value for this TipoDatiFascicolo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this TipoDatiFascicolo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTipoDatiFascicolo value for this TipoDatiFascicolo.
     * 
     * @return idTipoDatiFascicolo
     */
    public java.lang.Integer getIdTipoDatiFascicolo() {
        return idTipoDatiFascicolo;
    }


    /**
     * Sets the idTipoDatiFascicolo value for this TipoDatiFascicolo.
     * 
     * @param idTipoDatiFascicolo
     */
    public void setIdTipoDatiFascicolo(java.lang.Integer idTipoDatiFascicolo) {
        this.idTipoDatiFascicolo = idTipoDatiFascicolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoDatiFascicolo)) return false;
        TipoDatiFascicolo other = (TipoDatiFascicolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idTipoDatiFascicolo==null && other.getIdTipoDatiFascicolo()==null) || 
             (this.idTipoDatiFascicolo!=null &&
              this.idTipoDatiFascicolo.equals(other.getIdTipoDatiFascicolo())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdTipoDatiFascicolo() != null) {
            _hashCode += getIdTipoDatiFascicolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoDatiFascicolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDatiFascicolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoDatiFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdTipoDatiFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
