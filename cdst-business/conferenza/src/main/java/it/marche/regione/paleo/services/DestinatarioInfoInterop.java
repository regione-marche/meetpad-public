/**
 * DestinatarioInfoInterop.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class DestinatarioInfoInterop  extends it.marche.regione.paleo.services.DestinatarioInfo  implements java.io.Serializable {
    private it.marche.regione.paleo.services.MessaggioInteropInfo2[] messaggiInterop;

    public DestinatarioInfoInterop() {
    }

    public DestinatarioInfoInterop(
           java.lang.String descrizione,
           java.lang.String email,
           java.lang.String idFiscale,
           it.marche.regione.paleo.services.MessaggioInteropInfo2[] messaggiInterop) {
        super(
            descrizione,
            email,
            idFiscale);
        this.messaggiInterop = messaggiInterop;
    }


    /**
     * Gets the messaggiInterop value for this DestinatarioInfoInterop.
     * 
     * @return messaggiInterop
     */
    public it.marche.regione.paleo.services.MessaggioInteropInfo2[] getMessaggiInterop() {
        return messaggiInterop;
    }


    /**
     * Sets the messaggiInterop value for this DestinatarioInfoInterop.
     * 
     * @param messaggiInterop
     */
    public void setMessaggiInterop(it.marche.regione.paleo.services.MessaggioInteropInfo2[] messaggiInterop) {
        this.messaggiInterop = messaggiInterop;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DestinatarioInfoInterop)) return false;
        DestinatarioInfoInterop other = (DestinatarioInfoInterop) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.messaggiInterop==null && other.getMessaggiInterop()==null) || 
             (this.messaggiInterop!=null &&
              java.util.Arrays.equals(this.messaggiInterop, other.getMessaggiInterop())));
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
        if (getMessaggiInterop() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessaggiInterop());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessaggiInterop(), i);
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
        new org.apache.axis.description.TypeDesc(DestinatarioInfoInterop.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DestinatarioInfoInterop"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggiInterop");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggiInterop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2"));
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
