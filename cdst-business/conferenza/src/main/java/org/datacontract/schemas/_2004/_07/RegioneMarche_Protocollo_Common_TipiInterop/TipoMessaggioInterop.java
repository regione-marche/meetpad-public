/**
 * TipoMessaggioInterop.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_TipiInterop;

public class TipoMessaggioInterop implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipoMessaggioInterop(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Indefinito = "Indefinito";
    public static final java.lang.String _Segnatura = "Segnatura";
    public static final java.lang.String _ConfermaRicezione = "ConfermaRicezione";
    public static final java.lang.String _NotificaEccezione = "NotificaEccezione";
    public static final java.lang.String _Aggiornamento = "Aggiornamento";
    public static final java.lang.String _Annullamento = "Annullamento";
    public static final java.lang.String _NoInterop = "NoInterop";
    public static final TipoMessaggioInterop Indefinito = new TipoMessaggioInterop(_Indefinito);
    public static final TipoMessaggioInterop Segnatura = new TipoMessaggioInterop(_Segnatura);
    public static final TipoMessaggioInterop ConfermaRicezione = new TipoMessaggioInterop(_ConfermaRicezione);
    public static final TipoMessaggioInterop NotificaEccezione = new TipoMessaggioInterop(_NotificaEccezione);
    public static final TipoMessaggioInterop Aggiornamento = new TipoMessaggioInterop(_Aggiornamento);
    public static final TipoMessaggioInterop Annullamento = new TipoMessaggioInterop(_Annullamento);
    public static final TipoMessaggioInterop NoInterop = new TipoMessaggioInterop(_NoInterop);
    public java.lang.String getValue() { return _value_;}
    public static TipoMessaggioInterop fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipoMessaggioInterop enumeration = (TipoMessaggioInterop)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipoMessaggioInterop fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TipoMessaggioInterop.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.TipiInterop", "TipoMessaggioInterop"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
