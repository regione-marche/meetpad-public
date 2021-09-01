/**
 * ReqProtocolloArrivo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqProtocolloArrivo  extends it.marche.regione.paleo.services.ReqProtocollo  implements java.io.Serializable {
    private java.util.Calendar dataArrivo;

    private java.util.Calendar dataProtocolloMittente;

    private it.marche.regione.paleo.services.Corrispondente mittente;

    private java.lang.String protocolloMittente;

    public ReqProtocolloArrivo() {
    }

    public ReqProtocolloArrivo(
           it.marche.regione.paleo.services.Classificazione[] classificazioni,
           it.marche.regione.paleo.services.Allegato[] documentiAllegati,
           it.marche.regione.paleo.services.File documentoPrincipale,
           boolean documentoPrincipaleAcquisitoIntegralmente,
           it.marche.regione.paleo.services.TipoOriginale documentoPrincipaleOriginale,
           java.lang.String note,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           boolean privato,
           it.marche.regione.paleo.services.DatiProcedimento procedimento,
           java.lang.String tipoDocumento,
           it.marche.regione.paleo.services.Trasmissione trasmissione,
           java.lang.String codiceRegistro,
           it.marche.regione.paleo.services.DatiEmergenza emergenza,
           java.lang.String segnaturaProtocolloPrecedente,
           java.util.Calendar dataArrivo,
           java.util.Calendar dataProtocolloMittente,
           it.marche.regione.paleo.services.Corrispondente mittente,
           java.lang.String protocolloMittente) {
        super(
            classificazioni,
            documentiAllegati,
            documentoPrincipale,
            documentoPrincipaleAcquisitoIntegralmente,
            documentoPrincipaleOriginale,
            note,
            oggetto,
            operatore,
            privato,
            procedimento,
            tipoDocumento,
            trasmissione,
            codiceRegistro,
            emergenza,
            segnaturaProtocolloPrecedente);
        this.dataArrivo = dataArrivo;
        this.dataProtocolloMittente = dataProtocolloMittente;
        this.mittente = mittente;
        this.protocolloMittente = protocolloMittente;
    }


    /**
     * Gets the dataArrivo value for this ReqProtocolloArrivo.
     * 
     * @return dataArrivo
     */
    public java.util.Calendar getDataArrivo() {
        return dataArrivo;
    }


    /**
     * Sets the dataArrivo value for this ReqProtocolloArrivo.
     * 
     * @param dataArrivo
     */
    public void setDataArrivo(java.util.Calendar dataArrivo) {
        this.dataArrivo = dataArrivo;
    }


    /**
     * Gets the dataProtocolloMittente value for this ReqProtocolloArrivo.
     * 
     * @return dataProtocolloMittente
     */
    public java.util.Calendar getDataProtocolloMittente() {
        return dataProtocolloMittente;
    }


    /**
     * Sets the dataProtocolloMittente value for this ReqProtocolloArrivo.
     * 
     * @param dataProtocolloMittente
     */
    public void setDataProtocolloMittente(java.util.Calendar dataProtocolloMittente) {
        this.dataProtocolloMittente = dataProtocolloMittente;
    }


    /**
     * Gets the mittente value for this ReqProtocolloArrivo.
     * 
     * @return mittente
     */
    public it.marche.regione.paleo.services.Corrispondente getMittente() {
        return mittente;
    }


    /**
     * Sets the mittente value for this ReqProtocolloArrivo.
     * 
     * @param mittente
     */
    public void setMittente(it.marche.regione.paleo.services.Corrispondente mittente) {
        this.mittente = mittente;
    }


    /**
     * Gets the protocolloMittente value for this ReqProtocolloArrivo.
     * 
     * @return protocolloMittente
     */
    public java.lang.String getProtocolloMittente() {
        return protocolloMittente;
    }


    /**
     * Sets the protocolloMittente value for this ReqProtocolloArrivo.
     * 
     * @param protocolloMittente
     */
    public void setProtocolloMittente(java.lang.String protocolloMittente) {
        this.protocolloMittente = protocolloMittente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqProtocolloArrivo)) return false;
        ReqProtocolloArrivo other = (ReqProtocolloArrivo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataArrivo==null && other.getDataArrivo()==null) || 
             (this.dataArrivo!=null &&
              this.dataArrivo.equals(other.getDataArrivo()))) &&
            ((this.dataProtocolloMittente==null && other.getDataProtocolloMittente()==null) || 
             (this.dataProtocolloMittente!=null &&
              this.dataProtocolloMittente.equals(other.getDataProtocolloMittente()))) &&
            ((this.mittente==null && other.getMittente()==null) || 
             (this.mittente!=null &&
              this.mittente.equals(other.getMittente()))) &&
            ((this.protocolloMittente==null && other.getProtocolloMittente()==null) || 
             (this.protocolloMittente!=null &&
              this.protocolloMittente.equals(other.getProtocolloMittente())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDataArrivo() != null) {
            _hashCode += getDataArrivo().hashCode();
        }
        if (getDataProtocolloMittente() != null) {
            _hashCode += getDataProtocolloMittente().hashCode();
        }
        if (getMittente() != null) {
            _hashCode += getMittente().hashCode();
        }
        if (getProtocolloMittente() != null) {
            _hashCode += getProtocolloMittente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqProtocolloArrivo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocolloArrivo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataArrivo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataArrivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtocolloMittente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataProtocolloMittente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mittente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Mittente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Corrispondente"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolloMittente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocolloMittente"));
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
