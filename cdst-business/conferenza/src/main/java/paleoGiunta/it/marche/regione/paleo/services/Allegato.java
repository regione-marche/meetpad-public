/**
 * Allegato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class Allegato  implements java.io.Serializable {
    private java.lang.String descrizione;

    private paleoGiunta.it.marche.regione.paleo.services.File documento;

    private java.lang.Integer numeroPagine;

    private paleoGiunta.it.marche.regione.paleo.services.TipoOriginale originale;

    public Allegato() {
    }

    public Allegato(
           java.lang.String descrizione,
           paleoGiunta.it.marche.regione.paleo.services.File documento,
           java.lang.Integer numeroPagine,
           paleoGiunta.it.marche.regione.paleo.services.TipoOriginale originale) {
           this.descrizione = descrizione;
           this.documento = documento;
           this.numeroPagine = numeroPagine;
           this.originale = originale;
    }


    /**
     * Gets the descrizione value for this Allegato.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this Allegato.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the documento value for this Allegato.
     * 
     * @return documento
     */
    public paleoGiunta.it.marche.regione.paleo.services.File getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this Allegato.
     * 
     * @param documento
     */
    public void setDocumento(paleoGiunta.it.marche.regione.paleo.services.File documento) {
        this.documento = documento;
    }


    /**
     * Gets the numeroPagine value for this Allegato.
     * 
     * @return numeroPagine
     */
    public java.lang.Integer getNumeroPagine() {
        return numeroPagine;
    }


    /**
     * Sets the numeroPagine value for this Allegato.
     * 
     * @param numeroPagine
     */
    public void setNumeroPagine(java.lang.Integer numeroPagine) {
        this.numeroPagine = numeroPagine;
    }


    /**
     * Gets the originale value for this Allegato.
     * 
     * @return originale
     */
    public paleoGiunta.it.marche.regione.paleo.services.TipoOriginale getOriginale() {
        return originale;
    }


    /**
     * Sets the originale value for this Allegato.
     * 
     * @param originale
     */
    public void setOriginale(paleoGiunta.it.marche.regione.paleo.services.TipoOriginale originale) {
        this.originale = originale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Allegato)) return false;
        Allegato other = (Allegato) obj;
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
            ((this.documento==null && other.getDocumento()==null) || 
             (this.documento!=null &&
              this.documento.equals(other.getDocumento()))) &&
            ((this.numeroPagine==null && other.getNumeroPagine()==null) || 
             (this.numeroPagine!=null &&
              this.numeroPagine.equals(other.getNumeroPagine()))) &&
            ((this.originale==null && other.getOriginale()==null) || 
             (this.originale!=null &&
              this.originale.equals(other.getOriginale())));
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
        if (getDocumento() != null) {
            _hashCode += getDocumento().hashCode();
        }
        if (getNumeroPagine() != null) {
            _hashCode += getNumeroPagine().hashCode();
        }
        if (getOriginale() != null) {
            _hashCode += getOriginale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Allegato.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Documento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "File"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPagine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NumeroPagine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Originale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoOriginale"));
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
