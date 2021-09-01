/**
 * MessaggioInteropInfo2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class MessaggioInteropInfo2  implements java.io.Serializable {
    private java.util.Calendar dataOraInvioRicezione;

    private java.lang.String direzione;

    private it.marche.regione.paleo.services.MessaggioPostaInfo2[] messaggiPosta;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoStatoSpedizione statoSpedizione;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoMessaggioInterop tipo;

    public MessaggioInteropInfo2() {
    }

    public MessaggioInteropInfo2(
           java.util.Calendar dataOraInvioRicezione,
           java.lang.String direzione,
           it.marche.regione.paleo.services.MessaggioPostaInfo2[] messaggiPosta,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoStatoSpedizione statoSpedizione,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoMessaggioInterop tipo) {
           this.dataOraInvioRicezione = dataOraInvioRicezione;
           this.direzione = direzione;
           this.messaggiPosta = messaggiPosta;
           this.statoSpedizione = statoSpedizione;
           this.tipo = tipo;
    }


    /**
     * Gets the dataOraInvioRicezione value for this MessaggioInteropInfo2.
     * 
     * @return dataOraInvioRicezione
     */
    public java.util.Calendar getDataOraInvioRicezione() {
        return dataOraInvioRicezione;
    }


    /**
     * Sets the dataOraInvioRicezione value for this MessaggioInteropInfo2.
     * 
     * @param dataOraInvioRicezione
     */
    public void setDataOraInvioRicezione(java.util.Calendar dataOraInvioRicezione) {
        this.dataOraInvioRicezione = dataOraInvioRicezione;
    }


    /**
     * Gets the direzione value for this MessaggioInteropInfo2.
     * 
     * @return direzione
     */
    public java.lang.String getDirezione() {
        return direzione;
    }


    /**
     * Sets the direzione value for this MessaggioInteropInfo2.
     * 
     * @param direzione
     */
    public void setDirezione(java.lang.String direzione) {
        this.direzione = direzione;
    }


    /**
     * Gets the messaggiPosta value for this MessaggioInteropInfo2.
     * 
     * @return messaggiPosta
     */
    public it.marche.regione.paleo.services.MessaggioPostaInfo2[] getMessaggiPosta() {
        return messaggiPosta;
    }


    /**
     * Sets the messaggiPosta value for this MessaggioInteropInfo2.
     * 
     * @param messaggiPosta
     */
    public void setMessaggiPosta(it.marche.regione.paleo.services.MessaggioPostaInfo2[] messaggiPosta) {
        this.messaggiPosta = messaggiPosta;
    }


    /**
     * Gets the statoSpedizione value for this MessaggioInteropInfo2.
     * 
     * @return statoSpedizione
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoStatoSpedizione getStatoSpedizione() {
        return statoSpedizione;
    }


    /**
     * Sets the statoSpedizione value for this MessaggioInteropInfo2.
     * 
     * @param statoSpedizione
     */
    public void setStatoSpedizione(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoStatoSpedizione statoSpedizione) {
        this.statoSpedizione = statoSpedizione;
    }


    /**
     * Gets the tipo value for this MessaggioInteropInfo2.
     * 
     * @return tipo
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoMessaggioInterop getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this MessaggioInteropInfo2.
     * 
     * @param tipo
     */
    public void setTipo(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop.TipoMessaggioInterop tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessaggioInteropInfo2)) return false;
        MessaggioInteropInfo2 other = (MessaggioInteropInfo2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataOraInvioRicezione==null && other.getDataOraInvioRicezione()==null) || 
             (this.dataOraInvioRicezione!=null &&
              this.dataOraInvioRicezione.equals(other.getDataOraInvioRicezione()))) &&
            ((this.direzione==null && other.getDirezione()==null) || 
             (this.direzione!=null &&
              this.direzione.equals(other.getDirezione()))) &&
            ((this.messaggiPosta==null && other.getMessaggiPosta()==null) || 
             (this.messaggiPosta!=null &&
              java.util.Arrays.equals(this.messaggiPosta, other.getMessaggiPosta()))) &&
            ((this.statoSpedizione==null && other.getStatoSpedizione()==null) || 
             (this.statoSpedizione!=null &&
              this.statoSpedizione.equals(other.getStatoSpedizione()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getDataOraInvioRicezione() != null) {
            _hashCode += getDataOraInvioRicezione().hashCode();
        }
        if (getDirezione() != null) {
            _hashCode += getDirezione().hashCode();
        }
        if (getMessaggiPosta() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessaggiPosta());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessaggiPosta(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoSpedizione() != null) {
            _hashCode += getStatoSpedizione().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessaggioInteropInfo2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioInteropInfo2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataOraInvioRicezione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataOraInvioRicezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direzione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Direzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggiPosta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggiPosta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "MessaggioPostaInfo2"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoSpedizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "StatoSpedizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoStatoSpedizione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoMessaggioInterop"));
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
