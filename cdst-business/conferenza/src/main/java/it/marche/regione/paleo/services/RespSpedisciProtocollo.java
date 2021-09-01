/**
 * RespSpedisciProtocollo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespSpedisciProtocollo  extends it.marche.regione.paleo.services.BEBase  implements java.io.Serializable {
    private it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatari;

    private it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatariCC;

    public RespSpedisciProtocollo() {
    }

    public RespSpedisciProtocollo(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatari,
           it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatariCC) {
        super(
            messaggioRisultato);
        this.destinatari = destinatari;
        this.destinatariCC = destinatariCC;
    }


    /**
     * Gets the destinatari value for this RespSpedisciProtocollo.
     * 
     * @return destinatari
     */
    public it.marche.regione.paleo.services.DestinatarioInfoInterop[] getDestinatari() {
        return destinatari;
    }


    /**
     * Sets the destinatari value for this RespSpedisciProtocollo.
     * 
     * @param destinatari
     */
    public void setDestinatari(it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatari) {
        this.destinatari = destinatari;
    }


    /**
     * Gets the destinatariCC value for this RespSpedisciProtocollo.
     * 
     * @return destinatariCC
     */
    public it.marche.regione.paleo.services.DestinatarioInfoInterop[] getDestinatariCC() {
        return destinatariCC;
    }


    /**
     * Sets the destinatariCC value for this RespSpedisciProtocollo.
     * 
     * @param destinatariCC
     */
    public void setDestinatariCC(it.marche.regione.paleo.services.DestinatarioInfoInterop[] destinatariCC) {
        this.destinatariCC = destinatariCC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespSpedisciProtocollo)) return false;
        RespSpedisciProtocollo other = (RespSpedisciProtocollo) obj;
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
              java.util.Arrays.equals(this.destinatariCC, other.getDestinatariCC())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespSpedisciProtocollo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respSpedisciProtocollo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatari");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Destinatari"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatariCC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatariCC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop"));
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
