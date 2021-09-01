/**
 * PraticaOOPP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class PraticaOOPP  implements java.io.Serializable {
    private java.lang.String codiceFascicolo;

    private java.lang.Integer idRichiesta;

    private java.lang.String numeroIntervento;

    private org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[] protocolli;

    public PraticaOOPP() {
    }

    public PraticaOOPP(
           java.lang.String codiceFascicolo,
           java.lang.Integer idRichiesta,
           java.lang.String numeroIntervento,
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[] protocolli) {
           this.codiceFascicolo = codiceFascicolo;
           this.idRichiesta = idRichiesta;
           this.numeroIntervento = numeroIntervento;
           this.protocolli = protocolli;
    }


    /**
     * Gets the codiceFascicolo value for this PraticaOOPP.
     * 
     * @return codiceFascicolo
     */
    public java.lang.String getCodiceFascicolo() {
        return codiceFascicolo;
    }


    /**
     * Sets the codiceFascicolo value for this PraticaOOPP.
     * 
     * @param codiceFascicolo
     */
    public void setCodiceFascicolo(java.lang.String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }


    /**
     * Gets the idRichiesta value for this PraticaOOPP.
     * 
     * @return idRichiesta
     */
    public java.lang.Integer getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this PraticaOOPP.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(java.lang.Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the numeroIntervento value for this PraticaOOPP.
     * 
     * @return numeroIntervento
     */
    public java.lang.String getNumeroIntervento() {
        return numeroIntervento;
    }


    /**
     * Sets the numeroIntervento value for this PraticaOOPP.
     * 
     * @param numeroIntervento
     */
    public void setNumeroIntervento(java.lang.String numeroIntervento) {
        this.numeroIntervento = numeroIntervento;
    }


    /**
     * Gets the protocolli value for this PraticaOOPP.
     * 
     * @return protocolli
     */
    public org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[] getProtocolli() {
        return protocolli;
    }


    /**
     * Sets the protocolli value for this PraticaOOPP.
     * 
     * @param protocolli
     */
    public void setProtocolli(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[] protocolli) {
        this.protocolli = protocolli;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PraticaOOPP)) return false;
        PraticaOOPP other = (PraticaOOPP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFascicolo==null && other.getCodiceFascicolo()==null) || 
             (this.codiceFascicolo!=null &&
              this.codiceFascicolo.equals(other.getCodiceFascicolo()))) &&
            ((this.idRichiesta==null && other.getIdRichiesta()==null) || 
             (this.idRichiesta!=null &&
              this.idRichiesta.equals(other.getIdRichiesta()))) &&
            ((this.numeroIntervento==null && other.getNumeroIntervento()==null) || 
             (this.numeroIntervento!=null &&
              this.numeroIntervento.equals(other.getNumeroIntervento()))) &&
            ((this.protocolli==null && other.getProtocolli()==null) || 
             (this.protocolli!=null &&
              java.util.Arrays.equals(this.protocolli, other.getProtocolli())));
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
        if (getCodiceFascicolo() != null) {
            _hashCode += getCodiceFascicolo().hashCode();
        }
        if (getIdRichiesta() != null) {
            _hashCode += getIdRichiesta().hashCode();
        }
        if (getNumeroIntervento() != null) {
            _hashCode += getNumeroIntervento().hashCode();
        }
        if (getProtocolli() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProtocolli());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProtocolli(), i);
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
        new org.apache.axis.description.TypeDesc(PraticaOOPP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaOOPP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "CodiceFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "NumeroIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolli");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Protocolli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ProtocolloPratica"));
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
