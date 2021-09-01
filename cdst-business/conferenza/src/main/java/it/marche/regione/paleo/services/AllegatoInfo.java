/**
 * AllegatoInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class AllegatoInfo  implements java.io.Serializable {
	
    private java.lang.String idFile;
    
    private java.lang.String impronta;
	
	private java.lang.String descrizione;

    private java.lang.String nomeFile;

    public AllegatoInfo() {
    }

    public AllegatoInfo(
            java.lang.String idFile,
            java.lang.String impronta,
            java.lang.String descrizione,
           java.lang.String nomeFile) {
		this.idFile = idFile;
		this.impronta = impronta;
		this.descrizione = descrizione;
		this.nomeFile = nomeFile;
    }

    public java.lang.String getImpronta() {
		return impronta;
	}

	public void setImpronta(java.lang.String impronta) {
		this.impronta = impronta;
	}

    public java.lang.String getIdFile() {
        return idFile;
    }


    public void setIdFile(java.lang.String IdFile) {
        this.idFile = IdFile;
    }
    
    /**
     * Gets the descrizione value for this AllegatoInfo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AllegatoInfo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the nomeFile value for this AllegatoInfo.
     * 
     * @return nomeFile
     */
    public java.lang.String getNomeFile() {
        return nomeFile;
    }


    /**
     * Sets the nomeFile value for this AllegatoInfo.
     * 
     * @param nomeFile
     */
    public void setNomeFile(java.lang.String nomeFile) {
        this.nomeFile = nomeFile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AllegatoInfo)) return false;
        AllegatoInfo other = (AllegatoInfo) obj;
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
            ((this.nomeFile==null && other.getNomeFile()==null) || 
             (this.nomeFile!=null &&
              this.nomeFile.equals(other.getNomeFile())));
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
        if (getNomeFile() != null) {
            _hashCode += getNomeFile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AllegatoInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AllegatoInfo"));
        org.apache.axis.description.ElementDesc elemField;
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impronta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Impronta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impronta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Impronta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NomeFile"));
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
