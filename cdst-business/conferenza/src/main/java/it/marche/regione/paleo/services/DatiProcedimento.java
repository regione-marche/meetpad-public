/**
 * DatiProcedimento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class DatiProcedimento  implements java.io.Serializable {
    private java.lang.String codiceTipoProcedimento;

    private java.lang.Integer numeroProcedimento;

    private java.lang.String statoProcedimento;

    public DatiProcedimento() {
    }

    public DatiProcedimento(
           java.lang.String codiceTipoProcedimento,
           java.lang.Integer numeroProcedimento,
           java.lang.String statoProcedimento) {
           this.codiceTipoProcedimento = codiceTipoProcedimento;
           this.numeroProcedimento = numeroProcedimento;
           this.statoProcedimento = statoProcedimento;
    }


    /**
     * Gets the codiceTipoProcedimento value for this DatiProcedimento.
     * 
     * @return codiceTipoProcedimento
     */
    public java.lang.String getCodiceTipoProcedimento() {
        return codiceTipoProcedimento;
    }


    /**
     * Sets the codiceTipoProcedimento value for this DatiProcedimento.
     * 
     * @param codiceTipoProcedimento
     */
    public void setCodiceTipoProcedimento(java.lang.String codiceTipoProcedimento) {
        this.codiceTipoProcedimento = codiceTipoProcedimento;
    }


    /**
     * Gets the numeroProcedimento value for this DatiProcedimento.
     * 
     * @return numeroProcedimento
     */
    public java.lang.Integer getNumeroProcedimento() {
        return numeroProcedimento;
    }


    /**
     * Sets the numeroProcedimento value for this DatiProcedimento.
     * 
     * @param numeroProcedimento
     */
    public void setNumeroProcedimento(java.lang.Integer numeroProcedimento) {
        this.numeroProcedimento = numeroProcedimento;
    }


    /**
     * Gets the statoProcedimento value for this DatiProcedimento.
     * 
     * @return statoProcedimento
     */
    public java.lang.String getStatoProcedimento() {
        return statoProcedimento;
    }


    /**
     * Sets the statoProcedimento value for this DatiProcedimento.
     * 
     * @param statoProcedimento
     */
    public void setStatoProcedimento(java.lang.String statoProcedimento) {
        this.statoProcedimento = statoProcedimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiProcedimento)) return false;
        DatiProcedimento other = (DatiProcedimento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceTipoProcedimento==null && other.getCodiceTipoProcedimento()==null) || 
             (this.codiceTipoProcedimento!=null &&
              this.codiceTipoProcedimento.equals(other.getCodiceTipoProcedimento()))) &&
            ((this.numeroProcedimento==null && other.getNumeroProcedimento()==null) || 
             (this.numeroProcedimento!=null &&
              this.numeroProcedimento.equals(other.getNumeroProcedimento()))) &&
            ((this.statoProcedimento==null && other.getStatoProcedimento()==null) || 
             (this.statoProcedimento!=null &&
              this.statoProcedimento.equals(other.getStatoProcedimento())));
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
        if (getCodiceTipoProcedimento() != null) {
            _hashCode += getCodiceTipoProcedimento().hashCode();
        }
        if (getNumeroProcedimento() != null) {
            _hashCode += getNumeroProcedimento().hashCode();
        }
        if (getStatoProcedimento() != null) {
            _hashCode += getStatoProcedimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiProcedimento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiProcedimento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTipoProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceTipoProcedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NumeroProcedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "StatoProcedimento"));
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
