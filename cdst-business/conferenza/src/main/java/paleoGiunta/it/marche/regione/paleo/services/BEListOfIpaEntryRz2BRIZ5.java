/**
 * BEListOfIpaEntryRz2BRIZ5.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class BEListOfIpaEntryRz2BRIZ5  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry[] lista;

    private paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato;

    public BEListOfIpaEntryRz2BRIZ5() {
    }

    public BEListOfIpaEntryRz2BRIZ5(
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry[] lista,
           paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato) {
           this.lista = lista;
           this.messaggioRisultato = messaggioRisultato;
    }


    /**
     * Gets the lista value for this BEListOfIpaEntryRz2BRIZ5.
     * 
     * @return lista
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry[] getLista() {
        return lista;
    }


    /**
     * Sets the lista value for this BEListOfIpaEntryRz2BRIZ5.
     * 
     * @param lista
     */
    public void setLista(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.IpaEntry[] lista) {
        this.lista = lista;
    }


    /**
     * Gets the messaggioRisultato value for this BEListOfIpaEntryRz2BRIZ5.
     * 
     * @return messaggioRisultato
     */
    public paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato getMessaggioRisultato() {
        return messaggioRisultato;
    }


    /**
     * Sets the messaggioRisultato value for this BEListOfIpaEntryRz2BRIZ5.
     * 
     * @param messaggioRisultato
     */
    public void setMessaggioRisultato(paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato) {
        this.messaggioRisultato = messaggioRisultato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BEListOfIpaEntryRz2BRIZ5)) return false;
        BEListOfIpaEntryRz2BRIZ5 other = (BEListOfIpaEntryRz2BRIZ5) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lista==null && other.getLista()==null) || 
             (this.lista!=null &&
              java.util.Arrays.equals(this.lista, other.getLista()))) &&
            ((this.messaggioRisultato==null && other.getMessaggioRisultato()==null) || 
             (this.messaggioRisultato!=null &&
              this.messaggioRisultato.equals(other.getMessaggioRisultato())));
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
        if (getLista() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLista());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLista(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessaggioRisultato() != null) {
            _hashCode += getMessaggioRisultato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BEListOfIpaEntryRz2BRIZ5.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BEListOfIpaEntryRz2BRIZ5"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lista");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Lista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "IpaEntry"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "IpaEntry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggioRisultato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioRisultato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioRisultato"));
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
