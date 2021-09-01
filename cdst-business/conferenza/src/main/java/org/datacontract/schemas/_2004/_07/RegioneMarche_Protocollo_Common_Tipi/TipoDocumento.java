/**
 * TipoDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi;

public class TipoDocumento  implements java.io.Serializable {
    private java.lang.String _Descrizione;

    private int _IdTipoDocumento;

    private java.lang.String _x003C_Codice_x003E_k__BackingField;

    public TipoDocumento() {
    }

    public TipoDocumento(
           java.lang.String _Descrizione,
           int _IdTipoDocumento,
           java.lang.String _x003C_Codice_x003E_k__BackingField) {
           this._Descrizione = _Descrizione;
           this._IdTipoDocumento = _IdTipoDocumento;
           this._x003C_Codice_x003E_k__BackingField = _x003C_Codice_x003E_k__BackingField;
    }


    /**
     * Gets the _Descrizione value for this TipoDocumento.
     * 
     * @return _Descrizione
     */
    public java.lang.String get_Descrizione() {
        return _Descrizione;
    }


    /**
     * Sets the _Descrizione value for this TipoDocumento.
     * 
     * @param _Descrizione
     */
    public void set_Descrizione(java.lang.String _Descrizione) {
        this._Descrizione = _Descrizione;
    }


    /**
     * Gets the _IdTipoDocumento value for this TipoDocumento.
     * 
     * @return _IdTipoDocumento
     */
    public int get_IdTipoDocumento() {
        return _IdTipoDocumento;
    }


    /**
     * Sets the _IdTipoDocumento value for this TipoDocumento.
     * 
     * @param _IdTipoDocumento
     */
    public void set_IdTipoDocumento(int _IdTipoDocumento) {
        this._IdTipoDocumento = _IdTipoDocumento;
    }


    /**
     * Gets the _x003C_Codice_x003E_k__BackingField value for this TipoDocumento.
     * 
     * @return _x003C_Codice_x003E_k__BackingField
     */
    public java.lang.String get_x003C_Codice_x003E_k__BackingField() {
        return _x003C_Codice_x003E_k__BackingField;
    }


    /**
     * Sets the _x003C_Codice_x003E_k__BackingField value for this TipoDocumento.
     * 
     * @param _x003C_Codice_x003E_k__BackingField
     */
    public void set_x003C_Codice_x003E_k__BackingField(java.lang.String _x003C_Codice_x003E_k__BackingField) {
        this._x003C_Codice_x003E_k__BackingField = _x003C_Codice_x003E_k__BackingField;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoDocumento)) return false;
        TipoDocumento other = (TipoDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._Descrizione==null && other.get_Descrizione()==null) || 
             (this._Descrizione!=null &&
              this._Descrizione.equals(other.get_Descrizione()))) &&
            this._IdTipoDocumento == other.get_IdTipoDocumento() &&
            ((this._x003C_Codice_x003E_k__BackingField==null && other.get_x003C_Codice_x003E_k__BackingField()==null) || 
             (this._x003C_Codice_x003E_k__BackingField!=null &&
              this._x003C_Codice_x003E_k__BackingField.equals(other.get_x003C_Codice_x003E_k__BackingField())));
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
        if (get_Descrizione() != null) {
            _hashCode += get_Descrizione().hashCode();
        }
        _hashCode += get_IdTipoDocumento();
        if (get_x003C_Codice_x003E_k__BackingField() != null) {
            _hashCode += get_x003C_Codice_x003E_k__BackingField().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "_Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IdTipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "_IdTipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_x003C_Codice_x003E_k__BackingField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "_x003C_Codice_x003E_k__BackingField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
