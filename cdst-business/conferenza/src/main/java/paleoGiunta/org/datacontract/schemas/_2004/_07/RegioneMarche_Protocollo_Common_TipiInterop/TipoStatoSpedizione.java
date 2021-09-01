/**
 * TipoStatoSpedizione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop;

public class TipoStatoSpedizione implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipoStatoSpedizione(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NonSpedito = "NonSpedito";
    public static final java.lang.String _Spedito = "Spedito";
    public static final java.lang.String _Accettato = "Accettato";
    public static final java.lang.String _NonAccettato = "NonAccettato";
    public static final java.lang.String _PresoInCarico = "PresoInCarico";
    public static final java.lang.String _Consegnato = "Consegnato";
    public static final java.lang.String _NonConsegnato = "NonConsegnato";
    public static final TipoStatoSpedizione NonSpedito = new TipoStatoSpedizione(_NonSpedito);
    public static final TipoStatoSpedizione Spedito = new TipoStatoSpedizione(_Spedito);
    public static final TipoStatoSpedizione Accettato = new TipoStatoSpedizione(_Accettato);
    public static final TipoStatoSpedizione NonAccettato = new TipoStatoSpedizione(_NonAccettato);
    public static final TipoStatoSpedizione PresoInCarico = new TipoStatoSpedizione(_PresoInCarico);
    public static final TipoStatoSpedizione Consegnato = new TipoStatoSpedizione(_Consegnato);
    public static final TipoStatoSpedizione NonConsegnato = new TipoStatoSpedizione(_NonConsegnato);
    public java.lang.String getValue() { return _value_;}
    public static TipoStatoSpedizione fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipoStatoSpedizione enumeration = (TipoStatoSpedizione)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipoStatoSpedizione fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoStatoSpedizione.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoStatoSpedizione"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
