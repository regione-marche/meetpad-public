/**
 * RespDocProtocolliInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class RespDocProtocolliInfo  implements java.io.Serializable {
    private java.lang.String codiceRegistro;

    private java.lang.String corrispondenti;

    private java.util.Calendar dataCreazione;

    private java.util.Calendar dataProtocollo;

    private java.lang.Integer docNumber;

    private java.lang.Integer numeroProtocollo;

    private java.lang.String oggetto;

    private java.lang.Boolean privato;

    private java.lang.Boolean pubblico;

    private java.lang.String segnaturaProtocollo;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo;

    public RespDocProtocolliInfo() {
    }

    public RespDocProtocolliInfo(
           java.lang.String codiceRegistro,
           java.lang.String corrispondenti,
           java.util.Calendar dataCreazione,
           java.util.Calendar dataProtocollo,
           java.lang.Integer docNumber,
           java.lang.Integer numeroProtocollo,
           java.lang.String oggetto,
           java.lang.Boolean privato,
           java.lang.Boolean pubblico,
           java.lang.String segnaturaProtocollo,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo) {
           this.codiceRegistro = codiceRegistro;
           this.corrispondenti = corrispondenti;
           this.dataCreazione = dataCreazione;
           this.dataProtocollo = dataProtocollo;
           this.docNumber = docNumber;
           this.numeroProtocollo = numeroProtocollo;
           this.oggetto = oggetto;
           this.privato = privato;
           this.pubblico = pubblico;
           this.segnaturaProtocollo = segnaturaProtocollo;
           this.statoProtocollo = statoProtocollo;
           this.tipoProtocollo = tipoProtocollo;
    }


    /**
     * Gets the codiceRegistro value for this RespDocProtocolliInfo.
     * 
     * @return codiceRegistro
     */
    public java.lang.String getCodiceRegistro() {
        return codiceRegistro;
    }


    /**
     * Sets the codiceRegistro value for this RespDocProtocolliInfo.
     * 
     * @param codiceRegistro
     */
    public void setCodiceRegistro(java.lang.String codiceRegistro) {
        this.codiceRegistro = codiceRegistro;
    }


    /**
     * Gets the corrispondenti value for this RespDocProtocolliInfo.
     * 
     * @return corrispondenti
     */
    public java.lang.String getCorrispondenti() {
        return corrispondenti;
    }


    /**
     * Sets the corrispondenti value for this RespDocProtocolliInfo.
     * 
     * @param corrispondenti
     */
    public void setCorrispondenti(java.lang.String corrispondenti) {
        this.corrispondenti = corrispondenti;
    }


    /**
     * Gets the dataCreazione value for this RespDocProtocolliInfo.
     * 
     * @return dataCreazione
     */
    public java.util.Calendar getDataCreazione() {
        return dataCreazione;
    }


    /**
     * Sets the dataCreazione value for this RespDocProtocolliInfo.
     * 
     * @param dataCreazione
     */
    public void setDataCreazione(java.util.Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }


    /**
     * Gets the dataProtocollo value for this RespDocProtocolliInfo.
     * 
     * @return dataProtocollo
     */
    public java.util.Calendar getDataProtocollo() {
        return dataProtocollo;
    }


    /**
     * Sets the dataProtocollo value for this RespDocProtocolliInfo.
     * 
     * @param dataProtocollo
     */
    public void setDataProtocollo(java.util.Calendar dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }


    /**
     * Gets the docNumber value for this RespDocProtocolliInfo.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this RespDocProtocolliInfo.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the numeroProtocollo value for this RespDocProtocolliInfo.
     * 
     * @return numeroProtocollo
     */
    public java.lang.Integer getNumeroProtocollo() {
        return numeroProtocollo;
    }


    /**
     * Sets the numeroProtocollo value for this RespDocProtocolliInfo.
     * 
     * @param numeroProtocollo
     */
    public void setNumeroProtocollo(java.lang.Integer numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }


    /**
     * Gets the oggetto value for this RespDocProtocolliInfo.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this RespDocProtocolliInfo.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the privato value for this RespDocProtocolliInfo.
     * 
     * @return privato
     */
    public java.lang.Boolean getPrivato() {
        return privato;
    }


    /**
     * Sets the privato value for this RespDocProtocolliInfo.
     * 
     * @param privato
     */
    public void setPrivato(java.lang.Boolean privato) {
        this.privato = privato;
    }


    /**
     * Gets the pubblico value for this RespDocProtocolliInfo.
     * 
     * @return pubblico
     */
    public java.lang.Boolean getPubblico() {
        return pubblico;
    }


    /**
     * Sets the pubblico value for this RespDocProtocolliInfo.
     * 
     * @param pubblico
     */
    public void setPubblico(java.lang.Boolean pubblico) {
        this.pubblico = pubblico;
    }


    /**
     * Gets the segnaturaProtocollo value for this RespDocProtocolliInfo.
     * 
     * @return segnaturaProtocollo
     */
    public java.lang.String getSegnaturaProtocollo() {
        return segnaturaProtocollo;
    }


    /**
     * Sets the segnaturaProtocollo value for this RespDocProtocolliInfo.
     * 
     * @param segnaturaProtocollo
     */
    public void setSegnaturaProtocollo(java.lang.String segnaturaProtocollo) {
        this.segnaturaProtocollo = segnaturaProtocollo;
    }


    /**
     * Gets the statoProtocollo value for this RespDocProtocolliInfo.
     * 
     * @return statoProtocollo
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo getStatoProtocollo() {
        return statoProtocollo;
    }


    /**
     * Sets the statoProtocollo value for this RespDocProtocolliInfo.
     * 
     * @param statoProtocollo
     */
    public void setStatoProtocollo(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoStatoProtocollo statoProtocollo) {
        this.statoProtocollo = statoProtocollo;
    }


    /**
     * Gets the tipoProtocollo value for this RespDocProtocolliInfo.
     * 
     * @return tipoProtocollo
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo getTipoProtocollo() {
        return tipoProtocollo;
    }


    /**
     * Sets the tipoProtocollo value for this RespDocProtocolliInfo.
     * 
     * @param tipoProtocollo
     */
    public void setTipoProtocollo(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoProtocollo tipoProtocollo) {
        this.tipoProtocollo = tipoProtocollo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespDocProtocolliInfo)) return false;
        RespDocProtocolliInfo other = (RespDocProtocolliInfo) obj;
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
            ((this.corrispondenti==null && other.getCorrispondenti()==null) || 
             (this.corrispondenti!=null &&
              this.corrispondenti.equals(other.getCorrispondenti()))) &&
            ((this.dataCreazione==null && other.getDataCreazione()==null) || 
             (this.dataCreazione!=null &&
              this.dataCreazione.equals(other.getDataCreazione()))) &&
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
            ((this.privato==null && other.getPrivato()==null) || 
             (this.privato!=null &&
              this.privato.equals(other.getPrivato()))) &&
            ((this.pubblico==null && other.getPubblico()==null) || 
             (this.pubblico!=null &&
              this.pubblico.equals(other.getPubblico()))) &&
            ((this.segnaturaProtocollo==null && other.getSegnaturaProtocollo()==null) || 
             (this.segnaturaProtocollo!=null &&
              this.segnaturaProtocollo.equals(other.getSegnaturaProtocollo()))) &&
            ((this.statoProtocollo==null && other.getStatoProtocollo()==null) || 
             (this.statoProtocollo!=null &&
              this.statoProtocollo.equals(other.getStatoProtocollo()))) &&
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
        if (getCorrispondenti() != null) {
            _hashCode += getCorrispondenti().hashCode();
        }
        if (getDataCreazione() != null) {
            _hashCode += getDataCreazione().hashCode();
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
        if (getPrivato() != null) {
            _hashCode += getPrivato().hashCode();
        }
        if (getPubblico() != null) {
            _hashCode += getPubblico().hashCode();
        }
        if (getSegnaturaProtocollo() != null) {
            _hashCode += getSegnaturaProtocollo().hashCode();
        }
        if (getStatoProtocollo() != null) {
            _hashCode += getStatoProtocollo().hashCode();
        }
        if (getTipoProtocollo() != null) {
            _hashCode += getTipoProtocollo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespDocProtocolliInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocProtocolliInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corrispondenti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCreazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataCreazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Privato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pubblico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Pubblico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "StatoProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoStatoProtocollo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoProtocollo"));
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
