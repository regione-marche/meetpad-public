/**
 * ReqProtocollo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqProtocollo  extends it.marche.regione.paleo.services.ReqDocumento  implements java.io.Serializable {
    private java.lang.String codiceRegistro;

    private it.marche.regione.paleo.services.DatiEmergenza emergenza;

    private java.lang.String segnaturaProtocolloPrecedente;

    public ReqProtocollo() {
    }

    public ReqProtocollo(
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
           java.lang.String segnaturaProtocolloPrecedente) {
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
            trasmissione);
        this.codiceRegistro = codiceRegistro;
        this.emergenza = emergenza;
        this.segnaturaProtocolloPrecedente = segnaturaProtocolloPrecedente;
    }


    /**
     * Gets the codiceRegistro value for this ReqProtocollo.
     * 
     * @return codiceRegistro
     */
    public java.lang.String getCodiceRegistro() {
        return codiceRegistro;
    }


    /**
     * Sets the codiceRegistro value for this ReqProtocollo.
     * 
     * @param codiceRegistro
     */
    public void setCodiceRegistro(java.lang.String codiceRegistro) {
        this.codiceRegistro = codiceRegistro;
    }


    /**
     * Gets the emergenza value for this ReqProtocollo.
     * 
     * @return emergenza
     */
    public it.marche.regione.paleo.services.DatiEmergenza getEmergenza() {
        return emergenza;
    }


    /**
     * Sets the emergenza value for this ReqProtocollo.
     * 
     * @param emergenza
     */
    public void setEmergenza(it.marche.regione.paleo.services.DatiEmergenza emergenza) {
        this.emergenza = emergenza;
    }


    /**
     * Gets the segnaturaProtocolloPrecedente value for this ReqProtocollo.
     * 
     * @return segnaturaProtocolloPrecedente
     */
    public java.lang.String getSegnaturaProtocolloPrecedente() {
        return segnaturaProtocolloPrecedente;
    }


    /**
     * Sets the segnaturaProtocolloPrecedente value for this ReqProtocollo.
     * 
     * @param segnaturaProtocolloPrecedente
     */
    public void setSegnaturaProtocolloPrecedente(java.lang.String segnaturaProtocolloPrecedente) {
        this.segnaturaProtocolloPrecedente = segnaturaProtocolloPrecedente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqProtocollo)) return false;
        ReqProtocollo other = (ReqProtocollo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceRegistro==null && other.getCodiceRegistro()==null) || 
             (this.codiceRegistro!=null &&
              this.codiceRegistro.equals(other.getCodiceRegistro()))) &&
            ((this.emergenza==null && other.getEmergenza()==null) || 
             (this.emergenza!=null &&
              this.emergenza.equals(other.getEmergenza()))) &&
            ((this.segnaturaProtocolloPrecedente==null && other.getSegnaturaProtocolloPrecedente()==null) || 
             (this.segnaturaProtocolloPrecedente!=null &&
              this.segnaturaProtocolloPrecedente.equals(other.getSegnaturaProtocolloPrecedente())));
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
        if (getCodiceRegistro() != null) {
            _hashCode += getCodiceRegistro().hashCode();
        }
        if (getEmergenza() != null) {
            _hashCode += getEmergenza().hashCode();
        }
        if (getSegnaturaProtocolloPrecedente() != null) {
            _hashCode += getSegnaturaProtocolloPrecedente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqProtocollo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqProtocollo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emergenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Emergenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiEmergenza"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnaturaProtocolloPrecedente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SegnaturaProtocolloPrecedente"));
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
