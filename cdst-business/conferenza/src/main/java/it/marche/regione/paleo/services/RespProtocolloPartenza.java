/**
 * RespProtocolloPartenza.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespProtocolloPartenza  extends it.marche.regione.paleo.services.RespProtocollo  implements java.io.Serializable {
    private java.lang.String[] destinatari;

    private java.lang.String[] destinatariCC;

    private it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfo;

    private it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfoCC;

    public RespProtocolloPartenza() {
    }

    public RespProtocolloPartenza(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String[] classificazioni,
           java.util.Calendar dataDocumento,
           it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento,
           int docNumber,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo proprietario,
           java.lang.String segnaturaDocumento,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento,
           java.lang.Boolean annullato,
           java.util.Calendar data,
           java.util.Calendar dataProtocollazione,
           java.lang.String numero,
           java.lang.String registro,
           java.lang.String segnatura,
           java.lang.String[] destinatari,
           java.lang.String[] destinatariCC,
           it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfo,
           it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfoCC) {
        super(
            messaggioRisultato,
            classificazioni,
            dataDocumento,
            datiProcedimento,
            docNumber,
            oggetto,
            proprietario,
            segnaturaDocumento,
            tipoDocumento,
            annullato,
            data,
            dataProtocollazione,
            numero,
            registro,
            segnatura);
        this.destinatari = destinatari;
        this.destinatariCC = destinatariCC;
        this.destinatariInfo = destinatariInfo;
        this.destinatariInfoCC = destinatariInfoCC;
    }


    /**
     * Gets the destinatari value for this RespProtocolloPartenza.
     * 
     * @return destinatari
     */
    public java.lang.String[] getDestinatari() {
        return destinatari;
    }


    /**
     * Sets the destinatari value for this RespProtocolloPartenza.
     * 
     * @param destinatari
     */
    public void setDestinatari(java.lang.String[] destinatari) {
        this.destinatari = destinatari;
    }


    /**
     * Gets the destinatariCC value for this RespProtocolloPartenza.
     * 
     * @return destinatariCC
     */
    public java.lang.String[] getDestinatariCC() {
        return destinatariCC;
    }


    /**
     * Sets the destinatariCC value for this RespProtocolloPartenza.
     * 
     * @param destinatariCC
     */
    public void setDestinatariCC(java.lang.String[] destinatariCC) {
        this.destinatariCC = destinatariCC;
    }


    /**
     * Gets the destinatariInfo value for this RespProtocolloPartenza.
     * 
     * @return destinatariInfo
     */
    public it.marche.regione.paleo.services.DestinatarioInfo[] getDestinatariInfo() {
        return destinatariInfo;
    }


    /**
     * Sets the destinatariInfo value for this RespProtocolloPartenza.
     * 
     * @param destinatariInfo
     */
    public void setDestinatariInfo(it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfo) {
        this.destinatariInfo = destinatariInfo;
    }


    /**
     * Gets the destinatariInfoCC value for this RespProtocolloPartenza.
     * 
     * @return destinatariInfoCC
     */
    public it.marche.regione.paleo.services.DestinatarioInfo[] getDestinatariInfoCC() {
        return destinatariInfoCC;
    }


    /**
     * Sets the destinatariInfoCC value for this RespProtocolloPartenza.
     * 
     * @param destinatariInfoCC
     */
    public void setDestinatariInfoCC(it.marche.regione.paleo.services.DestinatarioInfo[] destinatariInfoCC) {
        this.destinatariInfoCC = destinatariInfoCC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespProtocolloPartenza)) return false;
        RespProtocolloPartenza other = (RespProtocolloPartenza) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.destinatari==null && other.getDestinatari()==null) || 
             (this.destinatari!=null &&
              java.util.Arrays.equals(this.destinatari, other.getDestinatari()))) &&
            ((this.destinatariCC==null && other.getDestinatariCC()==null) || 
             (this.destinatariCC!=null &&
              java.util.Arrays.equals(this.destinatariCC, other.getDestinatariCC()))) &&
            ((this.destinatariInfo==null && other.getDestinatariInfo()==null) || 
             (this.destinatariInfo!=null &&
              java.util.Arrays.equals(this.destinatariInfo, other.getDestinatariInfo()))) &&
            ((this.destinatariInfoCC==null && other.getDestinatariInfoCC()==null) || 
             (this.destinatariInfoCC!=null &&
              java.util.Arrays.equals(this.destinatariInfoCC, other.getDestinatariInfoCC())));
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
        if (getDestinatari() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDestinatari());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDestinatari(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinatariCC() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDestinatariCC());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDestinatariCC(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinatariInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDestinatariInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDestinatariInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinatariInfoCC() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDestinatariInfoCC());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDestinatariInfoCC(), i);
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
        new org.apache.axis.description.TypeDesc(RespProtocolloPartenza.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloPartenza"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatari");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Destinatari"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatariCC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatariCC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatariInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatariInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatariInfoCC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatariInfoCC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfo"));
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
