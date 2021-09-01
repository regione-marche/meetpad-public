/**
 * DocumentoProtocolloWithoutSream.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class DocumentoProtocolloWithoutSream  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream file;

    private java.lang.Boolean isPrincipale;

    public DocumentoProtocolloWithoutSream() {
    }

    public DocumentoProtocolloWithoutSream(
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream file,
           java.lang.Boolean isPrincipale) {
           this.file = file;
           this.isPrincipale = isPrincipale;
    }


    /**
     * Gets the file value for this DocumentoProtocolloWithoutSream.
     * 
     * @return file
     */
    public org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream getFile() {
        return file;
    }


    /**
     * Sets the file value for this DocumentoProtocolloWithoutSream.
     * 
     * @param file
     */
    public void setFile(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.FileWithoutStream file) {
        this.file = file;
    }


    /**
     * Gets the isPrincipale value for this DocumentoProtocolloWithoutSream.
     * 
     * @return isPrincipale
     */
    public java.lang.Boolean getIsPrincipale() {
        return isPrincipale;
    }


    /**
     * Sets the isPrincipale value for this DocumentoProtocolloWithoutSream.
     * 
     * @param isPrincipale
     */
    public void setIsPrincipale(java.lang.Boolean isPrincipale) {
        this.isPrincipale = isPrincipale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentoProtocolloWithoutSream)) return false;
        DocumentoProtocolloWithoutSream other = (DocumentoProtocolloWithoutSream) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.file==null && other.getFile()==null) || 
             (this.file!=null &&
              this.file.equals(other.getFile()))) &&
            ((this.isPrincipale==null && other.getIsPrincipale()==null) || 
             (this.isPrincipale!=null &&
              this.isPrincipale.equals(other.getIsPrincipale())));
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
        if (getFile() != null) {
            _hashCode += getFile().hashCode();
        }
        if (getIsPrincipale() != null) {
            _hashCode += getIsPrincipale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentoProtocolloWithoutSream.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocumentoProtocolloWithoutSream"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("file");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "FileWithoutStream"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IsPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
