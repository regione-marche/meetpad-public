/**
 * Corrispondente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class Corrispondente  implements java.io.Serializable {
    private java.lang.String codiceRubrica;

    private paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente corrispondenteOccasionale;

    public Corrispondente() {
    }

    public Corrispondente(
           java.lang.String codiceRubrica,
           paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente corrispondenteOccasionale) {
           this.codiceRubrica = codiceRubrica;
           this.corrispondenteOccasionale = corrispondenteOccasionale;
    }


    /**
     * Gets the codiceRubrica value for this Corrispondente.
     * 
     * @return codiceRubrica
     */
    public java.lang.String getCodiceRubrica() {
        return codiceRubrica;
    }


    /**
     * Sets the codiceRubrica value for this Corrispondente.
     * 
     * @param codiceRubrica
     */
    public void setCodiceRubrica(java.lang.String codiceRubrica) {
        this.codiceRubrica = codiceRubrica;
    }


    /**
     * Gets the corrispondenteOccasionale value for this Corrispondente.
     * 
     * @return corrispondenteOccasionale
     */
    public paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente getCorrispondenteOccasionale() {
        return corrispondenteOccasionale;
    }


    /**
     * Sets the corrispondenteOccasionale value for this Corrispondente.
     * 
     * @param corrispondenteOccasionale
     */
    public void setCorrispondenteOccasionale(paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente corrispondenteOccasionale) {
        this.corrispondenteOccasionale = corrispondenteOccasionale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Corrispondente)) return false;
        Corrispondente other = (Corrispondente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceRubrica==null && other.getCodiceRubrica()==null) || 
             (this.codiceRubrica!=null &&
              this.codiceRubrica.equals(other.getCodiceRubrica()))) &&
            ((this.corrispondenteOccasionale==null && other.getCorrispondenteOccasionale()==null) || 
             (this.corrispondenteOccasionale!=null &&
              this.corrispondenteOccasionale.equals(other.getCorrispondenteOccasionale())));
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
        if (getCodiceRubrica() != null) {
            _hashCode += getCodiceRubrica().hashCode();
        }
        if (getCorrispondenteOccasionale() != null) {
            _hashCode += getCorrispondenteOccasionale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Corrispondente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRubrica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRubrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrispondenteOccasionale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CorrispondenteOccasionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiCorrispondente"));
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
