/**
 * File.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class File  implements java.io.Serializable {
    private java.lang.String estensione;

    private java.lang.String mimeType;

    private java.lang.String nome;

    private java.lang.Boolean principale;

    private byte[] stream;

    public File() {
    }

    public File(
           java.lang.String estensione,
           java.lang.String mimeType,
           java.lang.String nome,
           java.lang.Boolean principale,
           byte[] stream) {
           this.estensione = estensione;
           this.mimeType = mimeType;
           this.nome = nome;
           this.principale = principale;
           this.stream = stream;
    }


    /**
     * Gets the estensione value for this File.
     * 
     * @return estensione
     */
    public java.lang.String getEstensione() {
        return estensione;
    }


    /**
     * Sets the estensione value for this File.
     * 
     * @param estensione
     */
    public void setEstensione(java.lang.String estensione) {
        this.estensione = estensione;
    }


    /**
     * Gets the mimeType value for this File.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this File.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * Gets the nome value for this File.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this File.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the principale value for this File.
     * 
     * @return principale
     */
    public java.lang.Boolean getPrincipale() {
        return principale;
    }


    /**
     * Sets the principale value for this File.
     * 
     * @param principale
     */
    public void setPrincipale(java.lang.Boolean principale) {
        this.principale = principale;
    }


    /**
     * Gets the stream value for this File.
     * 
     * @return stream
     */
    public byte[] getStream() {
        return stream;
    }


    /**
     * Sets the stream value for this File.
     * 
     * @param stream
     */
    public void setStream(byte[] stream) {
        this.stream = stream;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof File)) return false;
        File other = (File) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.estensione==null && other.getEstensione()==null) || 
             (this.estensione!=null &&
              this.estensione.equals(other.getEstensione()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.principale==null && other.getPrincipale()==null) || 
             (this.principale!=null &&
              this.principale.equals(other.getPrincipale()))) &&
            ((this.stream==null && other.getStream()==null) || 
             (this.stream!=null &&
              java.util.Arrays.equals(this.stream, other.getStream())));
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
        if (getEstensione() != null) {
            _hashCode += getEstensione().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getPrincipale() != null) {
            _hashCode += getPrincipale().hashCode();
        }
        if (getStream() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStream());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStream(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(File.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estensione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Estensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mimeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "MimeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("principale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Principale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stream");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Stream"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
