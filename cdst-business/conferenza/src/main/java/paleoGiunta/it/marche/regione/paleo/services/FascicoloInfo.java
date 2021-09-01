/**
 * FascicoloInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class FascicoloInfo  implements java.io.Serializable {
    private java.lang.String codice;

    private it.marche.regione.paleo.services.OperatorePaleo custode;

    private java.util.Calendar dataApertura;

    private java.util.Calendar dataChiusura;

    private java.util.Calendar dataCreazione;

    private java.lang.String descrizione;

    public FascicoloInfo() {
    }

    public FascicoloInfo(
           java.lang.String codice,
           it.marche.regione.paleo.services.OperatorePaleo custode,
           java.util.Calendar dataApertura,
           java.util.Calendar dataChiusura,
           java.util.Calendar dataCreazione,
           java.lang.String descrizione) {
           this.codice = codice;
           this.custode = custode;
           this.dataApertura = dataApertura;
           this.dataChiusura = dataChiusura;
           this.dataCreazione = dataCreazione;
           this.descrizione = descrizione;
    }


    /**
     * Gets the codice value for this FascicoloInfo.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this FascicoloInfo.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the custode value for this FascicoloInfo.
     * 
     * @return custode
     */
    public it.marche.regione.paleo.services.OperatorePaleo getCustode() {
        return custode;
    }


    /**
     * Sets the custode value for this FascicoloInfo.
     * 
     * @param custode
     */
    public void setCustode(it.marche.regione.paleo.services.OperatorePaleo custode) {
        this.custode = custode;
    }


    /**
     * Gets the dataApertura value for this FascicoloInfo.
     * 
     * @return dataApertura
     */
    public java.util.Calendar getDataApertura() {
        return dataApertura;
    }


    /**
     * Sets the dataApertura value for this FascicoloInfo.
     * 
     * @param dataApertura
     */
    public void setDataApertura(java.util.Calendar dataApertura) {
        this.dataApertura = dataApertura;
    }


    /**
     * Gets the dataChiusura value for this FascicoloInfo.
     * 
     * @return dataChiusura
     */
    public java.util.Calendar getDataChiusura() {
        return dataChiusura;
    }


    /**
     * Sets the dataChiusura value for this FascicoloInfo.
     * 
     * @param dataChiusura
     */
    public void setDataChiusura(java.util.Calendar dataChiusura) {
        this.dataChiusura = dataChiusura;
    }


    /**
     * Gets the dataCreazione value for this FascicoloInfo.
     * 
     * @return dataCreazione
     */
    public java.util.Calendar getDataCreazione() {
        return dataCreazione;
    }


    /**
     * Sets the dataCreazione value for this FascicoloInfo.
     * 
     * @param dataCreazione
     */
    public void setDataCreazione(java.util.Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }


    /**
     * Gets the descrizione value for this FascicoloInfo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this FascicoloInfo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FascicoloInfo)) return false;
        FascicoloInfo other = (FascicoloInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.custode==null && other.getCustode()==null) || 
             (this.custode!=null &&
              this.custode.equals(other.getCustode()))) &&
            ((this.dataApertura==null && other.getDataApertura()==null) || 
             (this.dataApertura!=null &&
              this.dataApertura.equals(other.getDataApertura()))) &&
            ((this.dataChiusura==null && other.getDataChiusura()==null) || 
             (this.dataChiusura!=null &&
              this.dataChiusura.equals(other.getDataChiusura()))) &&
            ((this.dataCreazione==null && other.getDataCreazione()==null) || 
             (this.dataCreazione!=null &&
              this.dataCreazione.equals(other.getDataCreazione()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getCustode() != null) {
            _hashCode += getCustode().hashCode();
        }
        if (getDataApertura() != null) {
            _hashCode += getDataApertura().hashCode();
        }
        if (getDataChiusura() != null) {
            _hashCode += getDataChiusura().hashCode();
        }
        if (getDataCreazione() != null) {
            _hashCode += getDataCreazione().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FascicoloInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "FascicoloInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Custode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataApertura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataApertura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
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
