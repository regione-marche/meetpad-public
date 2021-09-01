/**
 * RespAddAllegati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespAddAllegati  extends it.marche.regione.paleo.services.BEBase  implements java.io.Serializable {
    private it.marche.regione.paleo.services.AllegatoInfo[] allegati;

    private java.lang.Integer docNumber;

    private java.lang.String segnaturaProtocollo;

    public RespAddAllegati() {
    }

    public RespAddAllegati(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           it.marche.regione.paleo.services.AllegatoInfo[] allegati,
           java.lang.Integer docNumber,
           java.lang.String segnaturaProtocollo) {
        super(
            messaggioRisultato);
        this.allegati = allegati;
        this.docNumber = docNumber;
        this.segnaturaProtocollo = segnaturaProtocollo;
    }


    /**
     * Gets the allegati value for this RespAddAllegati.
     * 
     * @return allegati
     */
    public it.marche.regione.paleo.services.AllegatoInfo[] getAllegati() {
        return allegati;
    }


    /**
     * Sets the allegati value for this RespAddAllegati.
     * 
     * @param allegati
     */
    public void setAllegati(it.marche.regione.paleo.services.AllegatoInfo[] allegati) {
        this.allegati = allegati;
    }


    /**
     * Gets the docNumber value for this RespAddAllegati.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this RespAddAllegati.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the segnaturaProtocollo value for this RespAddAllegati.
     * 
     * @return segnaturaProtocollo
     */
    public java.lang.String getSegnaturaProtocollo() {
        return segnaturaProtocollo;
    }


    /**
     * Sets the segnaturaProtocollo value for this RespAddAllegati.
     * 
     * @param segnaturaProtocollo
     */
    public void setSegnaturaProtocollo(java.lang.String segnaturaProtocollo) {
        this.segnaturaProtocollo = segnaturaProtocollo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespAddAllegati)) return false;
        RespAddAllegati other = (RespAddAllegati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegati==null && other.getAllegati()==null) || 
             (this.allegati!=null &&
              java.util.Arrays.equals(this.allegati, other.getAllegati()))) &&
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.segnaturaProtocollo==null && other.getSegnaturaProtocollo()==null) || 
             (this.segnaturaProtocollo!=null &&
              this.segnaturaProtocollo.equals(other.getSegnaturaProtocollo())));
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
        if (getAllegati() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegati());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegati(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getSegnaturaProtocollo() != null) {
            _hashCode += getSegnaturaProtocollo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespAddAllegati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respAddAllegati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegati");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnaturaProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SegnaturaProtocollo"));
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
