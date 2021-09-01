/**
 * TrasmissioneDoc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TrasmissioneDoc  extends paleoGiunta.it.marche.regione.paleo.services.Trasmissione  implements java.io.Serializable {
    private java.lang.Integer docNumber;

    public TrasmissioneDoc() {
    }

    public TrasmissioneDoc(
           java.lang.Boolean invioOriginaleCartaceo,
           java.lang.String noteGenerali,
           boolean segueCartaceo,
           paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] trasmissioniRuolo,
           paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] trasmissioniUtente,
           java.lang.Integer docNumber) {
        super(
            invioOriginaleCartaceo,
            noteGenerali,
            segueCartaceo,
            trasmissioniRuolo,
            trasmissioniUtente);
        this.docNumber = docNumber;
    }


    /**
     * Gets the docNumber value for this TrasmissioneDoc.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this TrasmissioneDoc.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrasmissioneDoc)) return false;
        TrasmissioneDoc other = (TrasmissioneDoc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber())));
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
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrasmissioneDoc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneDoc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
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
