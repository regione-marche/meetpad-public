/**
 * ReqCreaFascicolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class ReqCreaFascicolo  extends it.marche.regione.paleo.services.DatiNuovoFascicolo  implements java.io.Serializable {
    private java.lang.Integer numero;

    public ReqCreaFascicolo() {
    }

    public ReqCreaFascicolo(
           java.lang.Integer anniConservazione,
           java.lang.String codiceClassifica,
           java.lang.String codiceFaldone,
           it.marche.regione.paleo.services.OperatorePaleo custode,
           java.lang.String descrizione,
           java.lang.Integer idSerieArchivistica,
           java.lang.Integer idTipoDati,
           java.lang.String note,
           java.lang.Integer numero) {
        super(
            anniConservazione,
            codiceClassifica,
            codiceFaldone,
            custode,
            descrizione,
            idSerieArchivistica,
            idTipoDati,
            note);
        this.numero = numero;
    }


    /**
     * Gets the numero value for this ReqCreaFascicolo.
     * 
     * @return numero
     */
    public java.lang.Integer getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this ReqCreaFascicolo.
     * 
     * @param numero
     */
    public void setNumero(java.lang.Integer numero) {
        this.numero = numero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqCreaFascicolo)) return false;
        ReqCreaFascicolo other = (ReqCreaFascicolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero())));
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
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqCreaFascicolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCreaFascicolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Numero"));
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
