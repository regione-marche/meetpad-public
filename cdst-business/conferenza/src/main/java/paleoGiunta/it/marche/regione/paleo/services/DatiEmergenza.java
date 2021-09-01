/**
 * DatiEmergenza.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class DatiEmergenza  implements java.io.Serializable {
    private java.util.Calendar data;

    private java.util.Calendar dataCreazioneEmergenza;

    private int idProfiloProtocollo;

    private int idProtocolloEmergenza;

    private int numero;

    private java.lang.String segnatura;

    public DatiEmergenza() {
    }

    public DatiEmergenza(
           java.util.Calendar data,
           java.util.Calendar dataCreazioneEmergenza,
           int idProfiloProtocollo,
           int idProtocolloEmergenza,
           int numero,
           java.lang.String segnatura) {
           this.data = data;
           this.dataCreazioneEmergenza = dataCreazioneEmergenza;
           this.idProfiloProtocollo = idProfiloProtocollo;
           this.idProtocolloEmergenza = idProtocolloEmergenza;
           this.numero = numero;
           this.segnatura = segnatura;
    }


    /**
     * Gets the data value for this DatiEmergenza.
     * 
     * @return data
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this DatiEmergenza.
     * 
     * @param data
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the dataCreazioneEmergenza value for this DatiEmergenza.
     * 
     * @return dataCreazioneEmergenza
     */
    public java.util.Calendar getDataCreazioneEmergenza() {
        return dataCreazioneEmergenza;
    }


    /**
     * Sets the dataCreazioneEmergenza value for this DatiEmergenza.
     * 
     * @param dataCreazioneEmergenza
     */
    public void setDataCreazioneEmergenza(java.util.Calendar dataCreazioneEmergenza) {
        this.dataCreazioneEmergenza = dataCreazioneEmergenza;
    }


    /**
     * Gets the idProfiloProtocollo value for this DatiEmergenza.
     * 
     * @return idProfiloProtocollo
     */
    public int getIdProfiloProtocollo() {
        return idProfiloProtocollo;
    }


    /**
     * Sets the idProfiloProtocollo value for this DatiEmergenza.
     * 
     * @param idProfiloProtocollo
     */
    public void setIdProfiloProtocollo(int idProfiloProtocollo) {
        this.idProfiloProtocollo = idProfiloProtocollo;
    }


    /**
     * Gets the idProtocolloEmergenza value for this DatiEmergenza.
     * 
     * @return idProtocolloEmergenza
     */
    public int getIdProtocolloEmergenza() {
        return idProtocolloEmergenza;
    }


    /**
     * Sets the idProtocolloEmergenza value for this DatiEmergenza.
     * 
     * @param idProtocolloEmergenza
     */
    public void setIdProtocolloEmergenza(int idProtocolloEmergenza) {
        this.idProtocolloEmergenza = idProtocolloEmergenza;
    }


    /**
     * Gets the numero value for this DatiEmergenza.
     * 
     * @return numero
     */
    public int getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this DatiEmergenza.
     * 
     * @param numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }


    /**
     * Gets the segnatura value for this DatiEmergenza.
     * 
     * @return segnatura
     */
    public java.lang.String getSegnatura() {
        return segnatura;
    }


    /**
     * Sets the segnatura value for this DatiEmergenza.
     * 
     * @param segnatura
     */
    public void setSegnatura(java.lang.String segnatura) {
        this.segnatura = segnatura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiEmergenza)) return false;
        DatiEmergenza other = (DatiEmergenza) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.dataCreazioneEmergenza==null && other.getDataCreazioneEmergenza()==null) || 
             (this.dataCreazioneEmergenza!=null &&
              this.dataCreazioneEmergenza.equals(other.getDataCreazioneEmergenza()))) &&
            this.idProfiloProtocollo == other.getIdProfiloProtocollo() &&
            this.idProtocolloEmergenza == other.getIdProtocolloEmergenza() &&
            this.numero == other.getNumero() &&
            ((this.segnatura==null && other.getSegnatura()==null) || 
             (this.segnatura!=null &&
              this.segnatura.equals(other.getSegnatura())));
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
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getDataCreazioneEmergenza() != null) {
            _hashCode += getDataCreazioneEmergenza().hashCode();
        }
        _hashCode += getIdProfiloProtocollo();
        _hashCode += getIdProtocolloEmergenza();
        _hashCode += getNumero();
        if (getSegnatura() != null) {
            _hashCode += getSegnatura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiEmergenza.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiEmergenza"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCreazioneEmergenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataCreazioneEmergenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProfiloProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdProfiloProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProtocolloEmergenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdProtocolloEmergenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnatura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Segnatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
