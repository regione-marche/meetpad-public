/**
 * RespProtocolloInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespProtocolloInfo  implements java.io.Serializable {
    private java.lang.String codiceRegistro;

    private java.lang.String codiceUO;

    private java.util.Calendar dataProtocollo;

    private java.lang.Integer docNumber;

    private java.lang.Integer numeroProtocollo;

    private java.lang.String oggetto;

    private java.lang.String segnaturaProtocollo;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo;

    private java.lang.String tipoDocumento;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo;

    public RespProtocolloInfo() {
    }

    public RespProtocolloInfo(
           java.lang.String codiceRegistro,
           java.lang.String codiceUO,
           java.util.Calendar dataProtocollo,
           java.lang.Integer docNumber,
           java.lang.Integer numeroProtocollo,
           java.lang.String oggetto,
           java.lang.String segnaturaProtocollo,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo,
           java.lang.String tipoDocumento,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo) {
           this.codiceRegistro = codiceRegistro;
           this.codiceUO = codiceUO;
           this.dataProtocollo = dataProtocollo;
           this.docNumber = docNumber;
           this.numeroProtocollo = numeroProtocollo;
           this.oggetto = oggetto;
           this.segnaturaProtocollo = segnaturaProtocollo;
           this.statoProtocollo = statoProtocollo;
           this.tipoDocumento = tipoDocumento;
           this.tipoProtocollo = tipoProtocollo;
    }


    /**
     * Gets the codiceRegistro value for this RespProtocolloInfo.
     * 
     * @return codiceRegistro
     */
    public java.lang.String getCodiceRegistro() {
        return codiceRegistro;
    }


    /**
     * Sets the codiceRegistro value for this RespProtocolloInfo.
     * 
     * @param codiceRegistro
     */
    public void setCodiceRegistro(java.lang.String codiceRegistro) {
        this.codiceRegistro = codiceRegistro;
    }


    /**
     * Gets the codiceUO value for this RespProtocolloInfo.
     * 
     * @return codiceUO
     */
    public java.lang.String getCodiceUO() {
        return codiceUO;
    }


    /**
     * Sets the codiceUO value for this RespProtocolloInfo.
     * 
     * @param codiceUO
     */
    public void setCodiceUO(java.lang.String codiceUO) {
        this.codiceUO = codiceUO;
    }


    /**
     * Gets the dataProtocollo value for this RespProtocolloInfo.
     * 
     * @return dataProtocollo
     */
    public java.util.Calendar getDataProtocollo() {
        return dataProtocollo;
    }


    /**
     * Sets the dataProtocollo value for this RespProtocolloInfo.
     * 
     * @param dataProtocollo
     */
    public void setDataProtocollo(java.util.Calendar dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }


    /**
     * Gets the docNumber value for this RespProtocolloInfo.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this RespProtocolloInfo.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the numeroProtocollo value for this RespProtocolloInfo.
     * 
     * @return numeroProtocollo
     */
    public java.lang.Integer getNumeroProtocollo() {
        return numeroProtocollo;
    }


    /**
     * Sets the numeroProtocollo value for this RespProtocolloInfo.
     * 
     * @param numeroProtocollo
     */
    public void setNumeroProtocollo(java.lang.Integer numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }


    /**
     * Gets the oggetto value for this RespProtocolloInfo.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this RespProtocolloInfo.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the segnaturaProtocollo value for this RespProtocolloInfo.
     * 
     * @return segnaturaProtocollo
     */
    public java.lang.String getSegnaturaProtocollo() {
        return segnaturaProtocollo;
    }


    /**
     * Sets the segnaturaProtocollo value for this RespProtocolloInfo.
     * 
     * @param segnaturaProtocollo
     */
    public void setSegnaturaProtocollo(java.lang.String segnaturaProtocollo) {
        this.segnaturaProtocollo = segnaturaProtocollo;
    }


    /**
     * Gets the statoProtocollo value for this RespProtocolloInfo.
     * 
     * @return statoProtocollo
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo getStatoProtocollo() {
        return statoProtocollo;
    }


    /**
     * Sets the statoProtocollo value for this RespProtocolloInfo.
     * 
     * @param statoProtocollo
     */
    public void setStatoProtocollo(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo) {
        this.statoProtocollo = statoProtocollo;
    }


    /**
     * Gets the tipoDocumento value for this RespProtocolloInfo.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this RespProtocolloInfo.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipoProtocollo value for this RespProtocolloInfo.
     * 
     * @return tipoProtocollo
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo getTipoProtocollo() {
        return tipoProtocollo;
    }


    /**
     * Sets the tipoProtocollo value for this RespProtocolloInfo.
     * 
     * @param tipoProtocollo
     */
    public void setTipoProtocollo(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo) {
        this.tipoProtocollo = tipoProtocollo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespProtocolloInfo)) return false;
        RespProtocolloInfo other = (RespProtocolloInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceRegistro==null && other.getCodiceRegistro()==null) || 
             (this.codiceRegistro!=null &&
              this.codiceRegistro.equals(other.getCodiceRegistro()))) &&
            ((this.codiceUO==null && other.getCodiceUO()==null) || 
             (this.codiceUO!=null &&
              this.codiceUO.equals(other.getCodiceUO()))) &&
            ((this.dataProtocollo==null && other.getDataProtocollo()==null) || 
             (this.dataProtocollo!=null &&
              this.dataProtocollo.equals(other.getDataProtocollo()))) &&
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.numeroProtocollo==null && other.getNumeroProtocollo()==null) || 
             (this.numeroProtocollo!=null &&
              this.numeroProtocollo.equals(other.getNumeroProtocollo()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.segnaturaProtocollo==null && other.getSegnaturaProtocollo()==null) || 
             (this.segnaturaProtocollo!=null &&
              this.segnaturaProtocollo.equals(other.getSegnaturaProtocollo()))) &&
            ((this.statoProtocollo==null && other.getStatoProtocollo()==null) || 
             (this.statoProtocollo!=null &&
              this.statoProtocollo.equals(other.getStatoProtocollo()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipoProtocollo==null && other.getTipoProtocollo()==null) || 
             (this.tipoProtocollo!=null &&
              this.tipoProtocollo.equals(other.getTipoProtocollo())));
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
        if (getCodiceRegistro() != null) {
            _hashCode += getCodiceRegistro().hashCode();
        }
        if (getCodiceUO() != null) {
            _hashCode += getCodiceUO().hashCode();
        }
        if (getDataProtocollo() != null) {
            _hashCode += getDataProtocollo().hashCode();
        }
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getNumeroProtocollo() != null) {
            _hashCode += getNumeroProtocollo().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getSegnaturaProtocollo() != null) {
            _hashCode += getSegnaturaProtocollo().hashCode();
        }
        if (getStatoProtocollo() != null) {
            _hashCode += getStatoProtocollo().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipoProtocollo() != null) {
            _hashCode += getTipoProtocollo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespProtocolloInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceUO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceUO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NumeroProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnaturaProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SegnaturaProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "StatoProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoStatoProtocollo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoProtocollo"));
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
