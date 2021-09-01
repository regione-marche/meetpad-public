/**
 * ProtocolloPratica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class ProtocolloPratica  implements java.io.Serializable {
    private java.lang.String docNumber;

    private java.lang.String genereValidita;

    private java.lang.String segnatura;

    private java.lang.String tipoDocumento;

    private java.lang.String tipologiaIstanza;

    public ProtocolloPratica() {
    }

    public ProtocolloPratica(
           java.lang.String docNumber,
           java.lang.String genereValidita,
           java.lang.String segnatura,
           java.lang.String tipoDocumento,
           java.lang.String tipologiaIstanza) {
           this.docNumber = docNumber;
           this.genereValidita = genereValidita;
           this.segnatura = segnatura;
           this.tipoDocumento = tipoDocumento;
           this.tipologiaIstanza = tipologiaIstanza;
    }


    /**
     * Gets the docNumber value for this ProtocolloPratica.
     * 
     * @return docNumber
     */
    public java.lang.String getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this ProtocolloPratica.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.String docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the genereValidita value for this ProtocolloPratica.
     * 
     * @return genereValidita
     */
    public java.lang.String getGenereValidita() {
        return genereValidita;
    }


    /**
     * Sets the genereValidita value for this ProtocolloPratica.
     * 
     * @param genereValidita
     */
    public void setGenereValidita(java.lang.String genereValidita) {
        this.genereValidita = genereValidita;
    }


    /**
     * Gets the segnatura value for this ProtocolloPratica.
     * 
     * @return segnatura
     */
    public java.lang.String getSegnatura() {
        return segnatura;
    }


    /**
     * Sets the segnatura value for this ProtocolloPratica.
     * 
     * @param segnatura
     */
    public void setSegnatura(java.lang.String segnatura) {
        this.segnatura = segnatura;
    }


    /**
     * Gets the tipoDocumento value for this ProtocolloPratica.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this ProtocolloPratica.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipologiaIstanza value for this ProtocolloPratica.
     * 
     * @return tipologiaIstanza
     */
    public java.lang.String getTipologiaIstanza() {
        return tipologiaIstanza;
    }


    /**
     * Sets the tipologiaIstanza value for this ProtocolloPratica.
     * 
     * @param tipologiaIstanza
     */
    public void setTipologiaIstanza(java.lang.String tipologiaIstanza) {
        this.tipologiaIstanza = tipologiaIstanza;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProtocolloPratica)) return false;
        ProtocolloPratica other = (ProtocolloPratica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.genereValidita==null && other.getGenereValidita()==null) || 
             (this.genereValidita!=null &&
              this.genereValidita.equals(other.getGenereValidita()))) &&
            ((this.segnatura==null && other.getSegnatura()==null) || 
             (this.segnatura!=null &&
              this.segnatura.equals(other.getSegnatura()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipologiaIstanza==null && other.getTipologiaIstanza()==null) || 
             (this.tipologiaIstanza!=null &&
              this.tipologiaIstanza.equals(other.getTipologiaIstanza())));
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
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getGenereValidita() != null) {
            _hashCode += getGenereValidita().hashCode();
        }
        if (getSegnatura() != null) {
            _hashCode += getSegnatura().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipologiaIstanza() != null) {
            _hashCode += getTipologiaIstanza().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProtocolloPratica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("genereValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GenereValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnatura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Segnatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaIstanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "TipologiaIstanza"));
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
