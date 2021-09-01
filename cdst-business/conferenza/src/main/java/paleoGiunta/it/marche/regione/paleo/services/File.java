/**
 * File.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class File  implements java.io.Serializable {
    private java.lang.String estensione;

    private java.lang.Integer idFile;

    private java.lang.String impronta;

    private java.lang.String mimeType;

    private java.lang.String nome;

    private byte[] stream;

    public File() {
    }

    public File(
           java.lang.String estensione,
           java.lang.Integer idFile,
           java.lang.String impronta,
           java.lang.String mimeType,
           java.lang.String nome,
           byte[] stream) {
           this.estensione = estensione;
           this.idFile = idFile;
           this.impronta = impronta;
           this.mimeType = mimeType;
           this.nome = nome;
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
     * Gets the idFile value for this File.
     * 
     * @return idFile
     */
    public java.lang.Integer getIdFile() {
        return idFile;
    }


    /**
     * Sets the idFile value for this File.
     * 
     * @param idFile
     */
    public void setIdFile(java.lang.Integer idFile) {
        this.idFile = idFile;
    }


    /**
     * Gets the impronta value for this File.
     * 
     * @return impronta
     */
    public java.lang.String getImpronta() {
        return impronta;
    }


    /**
     * Sets the impronta value for this File.
     * 
     * @param impronta
     */
    public void setImpronta(java.lang.String impronta) {
        this.impronta = impronta;
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
            ((this.idFile==null && other.getIdFile()==null) || 
             (this.idFile!=null &&
              this.idFile.equals(other.getIdFile()))) &&
            ((this.impronta==null && other.getImpronta()==null) || 
             (this.impronta!=null &&
              this.impronta.equals(other.getImpronta()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
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
        if (getIdFile() != null) {
            _hashCode += getIdFile().hashCode();
        }
        if (getImpronta() != null) {
            _hashCode += getImpronta().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
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
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "File"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estensione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Estensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impronta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Impronta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mimeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MimeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stream");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Stream"));
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
