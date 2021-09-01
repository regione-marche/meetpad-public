/**
 * MessaggioPostaInfo2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class MessaggioPostaInfo2  implements java.io.Serializable {
    private java.util.Calendar dataOra;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoDirezioneMessaggio direzione;

    private java.lang.Integer idCasellaPosta;

    private java.lang.String indirizzoCasella;

    private java.lang.String messageId;

    private java.lang.String subject;

    public MessaggioPostaInfo2() {
    }

    public MessaggioPostaInfo2(
           java.util.Calendar dataOra,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoDirezioneMessaggio direzione,
           java.lang.Integer idCasellaPosta,
           java.lang.String indirizzoCasella,
           java.lang.String messageId,
           java.lang.String subject) {
           this.dataOra = dataOra;
           this.direzione = direzione;
           this.idCasellaPosta = idCasellaPosta;
           this.indirizzoCasella = indirizzoCasella;
           this.messageId = messageId;
           this.subject = subject;
    }


    /**
     * Gets the dataOra value for this MessaggioPostaInfo2.
     * 
     * @return dataOra
     */
    public java.util.Calendar getDataOra() {
        return dataOra;
    }


    /**
     * Sets the dataOra value for this MessaggioPostaInfo2.
     * 
     * @param dataOra
     */
    public void setDataOra(java.util.Calendar dataOra) {
        this.dataOra = dataOra;
    }


    /**
     * Gets the direzione value for this MessaggioPostaInfo2.
     * 
     * @return direzione
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoDirezioneMessaggio getDirezione() {
        return direzione;
    }


    /**
     * Sets the direzione value for this MessaggioPostaInfo2.
     * 
     * @param direzione
     */
    public void setDirezione(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoDirezioneMessaggio direzione) {
        this.direzione = direzione;
    }


    /**
     * Gets the idCasellaPosta value for this MessaggioPostaInfo2.
     * 
     * @return idCasellaPosta
     */
    public java.lang.Integer getIdCasellaPosta() {
        return idCasellaPosta;
    }


    /**
     * Sets the idCasellaPosta value for this MessaggioPostaInfo2.
     * 
     * @param idCasellaPosta
     */
    public void setIdCasellaPosta(java.lang.Integer idCasellaPosta) {
        this.idCasellaPosta = idCasellaPosta;
    }


    /**
     * Gets the indirizzoCasella value for this MessaggioPostaInfo2.
     * 
     * @return indirizzoCasella
     */
    public java.lang.String getIndirizzoCasella() {
        return indirizzoCasella;
    }


    /**
     * Sets the indirizzoCasella value for this MessaggioPostaInfo2.
     * 
     * @param indirizzoCasella
     */
    public void setIndirizzoCasella(java.lang.String indirizzoCasella) {
        this.indirizzoCasella = indirizzoCasella;
    }


    /**
     * Gets the messageId value for this MessaggioPostaInfo2.
     * 
     * @return messageId
     */
    public java.lang.String getMessageId() {
        return messageId;
    }


    /**
     * Sets the messageId value for this MessaggioPostaInfo2.
     * 
     * @param messageId
     */
    public void setMessageId(java.lang.String messageId) {
        this.messageId = messageId;
    }


    /**
     * Gets the subject value for this MessaggioPostaInfo2.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this MessaggioPostaInfo2.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessaggioPostaInfo2)) return false;
        MessaggioPostaInfo2 other = (MessaggioPostaInfo2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataOra==null && other.getDataOra()==null) || 
             (this.dataOra!=null &&
              this.dataOra.equals(other.getDataOra()))) &&
            ((this.direzione==null && other.getDirezione()==null) || 
             (this.direzione!=null &&
              this.direzione.equals(other.getDirezione()))) &&
            ((this.idCasellaPosta==null && other.getIdCasellaPosta()==null) || 
             (this.idCasellaPosta!=null &&
              this.idCasellaPosta.equals(other.getIdCasellaPosta()))) &&
            ((this.indirizzoCasella==null && other.getIndirizzoCasella()==null) || 
             (this.indirizzoCasella!=null &&
              this.indirizzoCasella.equals(other.getIndirizzoCasella()))) &&
            ((this.messageId==null && other.getMessageId()==null) || 
             (this.messageId!=null &&
              this.messageId.equals(other.getMessageId()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject())));
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
        if (getDataOra() != null) {
            _hashCode += getDataOra().hashCode();
        }
        if (getDirezione() != null) {
            _hashCode += getDirezione().hashCode();
        }
        if (getIdCasellaPosta() != null) {
            _hashCode += getIdCasellaPosta().hashCode();
        }
        if (getIndirizzoCasella() != null) {
            _hashCode += getIndirizzoCasella().hashCode();
        }
        if (getMessageId() != null) {
            _hashCode += getMessageId().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessaggioPostaInfo2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataOra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataOra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direzione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Direzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoDirezioneMessaggio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCasellaPosta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdCasellaPosta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzoCasella");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IndirizzoCasella"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Subject"));
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
