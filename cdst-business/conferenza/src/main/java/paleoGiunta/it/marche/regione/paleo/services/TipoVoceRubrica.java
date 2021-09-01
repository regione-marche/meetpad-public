/**
 * TipoVoceRubrica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TipoVoceRubrica implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TipoVoceRubrica(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Indefinito = "Indefinito";
    public static final java.lang.String _Amministrazione = "Amministrazione";
    public static final java.lang.String _AOO = "AOO";
    public static final java.lang.String _UO = "UO";
    public static final java.lang.String _Persona = "Persona";
    public static final java.lang.String _Altro = "Altro";
    public static final java.lang.String _Impresa = "Impresa";
    public static final java.lang.String _Gruppo = "Gruppo";
    public static final TipoVoceRubrica Indefinito = new TipoVoceRubrica(_Indefinito);
    public static final TipoVoceRubrica Amministrazione = new TipoVoceRubrica(_Amministrazione);
    public static final TipoVoceRubrica AOO = new TipoVoceRubrica(_AOO);
    public static final TipoVoceRubrica UO = new TipoVoceRubrica(_UO);
    public static final TipoVoceRubrica Persona = new TipoVoceRubrica(_Persona);
    public static final TipoVoceRubrica Altro = new TipoVoceRubrica(_Altro);
    public static final TipoVoceRubrica Impresa = new TipoVoceRubrica(_Impresa);
    public static final TipoVoceRubrica Gruppo = new TipoVoceRubrica(_Gruppo);
    public java.lang.String getValue() { return _value_;}
    public static TipoVoceRubrica fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TipoVoceRubrica enumeration = (TipoVoceRubrica)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TipoVoceRubrica fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TipoVoceRubrica.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoVoceRubrica"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
