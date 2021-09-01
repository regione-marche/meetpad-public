/**
 * TipoStatoProtocollo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi;

public class TipoStatoProtocollo implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipoStatoProtocollo(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Indefinito = "Indefinito";
    public static final java.lang.String _Predisposto = "Predisposto";
    public static final java.lang.String _PredispostoInterop = "PredispostoInterop";
    public static final java.lang.String _Protocollato = "Protocollato";
    public static final java.lang.String _Annullato = "Annullato";
    public static final java.lang.String _PredispostoAnnullato = "PredispostoAnnullato";
    public static final TipoStatoProtocollo Indefinito = new TipoStatoProtocollo(_Indefinito);
    public static final TipoStatoProtocollo Predisposto = new TipoStatoProtocollo(_Predisposto);
    public static final TipoStatoProtocollo PredispostoInterop = new TipoStatoProtocollo(_PredispostoInterop);
    public static final TipoStatoProtocollo Protocollato = new TipoStatoProtocollo(_Protocollato);
    public static final TipoStatoProtocollo Annullato = new TipoStatoProtocollo(_Annullato);
    public static final TipoStatoProtocollo PredispostoAnnullato = new TipoStatoProtocollo(_PredispostoAnnullato);
    public java.lang.String getValue() { return _value_;}
    public static TipoStatoProtocollo fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipoStatoProtocollo enumeration = (TipoStatoProtocollo)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipoStatoProtocollo fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TipoStatoProtocollo.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoStatoProtocollo"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
