/**
 * TitolarioInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TitolarioInfo  implements java.io.Serializable {
    private java.lang.String codiceClassifica;

    private java.lang.String codiceFascicolo;

    private paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo custode;

    private java.lang.String descrizioneClassifica;

    private java.lang.String descrizioneFascicolo;

    public TitolarioInfo() {
    }

    public TitolarioInfo(
           java.lang.String codiceClassifica,
           java.lang.String codiceFascicolo,
           paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo custode,
           java.lang.String descrizioneClassifica,
           java.lang.String descrizioneFascicolo) {
           this.codiceClassifica = codiceClassifica;
           this.codiceFascicolo = codiceFascicolo;
           this.custode = custode;
           this.descrizioneClassifica = descrizioneClassifica;
           this.descrizioneFascicolo = descrizioneFascicolo;
    }


    /**
     * Gets the codiceClassifica value for this TitolarioInfo.
     * 
     * @return codiceClassifica
     */
    public java.lang.String getCodiceClassifica() {
        return codiceClassifica;
    }


    /**
     * Sets the codiceClassifica value for this TitolarioInfo.
     * 
     * @param codiceClassifica
     */
    public void setCodiceClassifica(java.lang.String codiceClassifica) {
        this.codiceClassifica = codiceClassifica;
    }


    /**
     * Gets the codiceFascicolo value for this TitolarioInfo.
     * 
     * @return codiceFascicolo
     */
    public java.lang.String getCodiceFascicolo() {
        return codiceFascicolo;
    }


    /**
     * Sets the codiceFascicolo value for this TitolarioInfo.
     * 
     * @param codiceFascicolo
     */
    public void setCodiceFascicolo(java.lang.String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }


    /**
     * Gets the custode value for this TitolarioInfo.
     * 
     * @return custode
     */
    public paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo getCustode() {
        return custode;
    }


    /**
     * Sets the custode value for this TitolarioInfo.
     * 
     * @param custode
     */
    public void setCustode(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo custode) {
        this.custode = custode;
    }


    /**
     * Gets the descrizioneClassifica value for this TitolarioInfo.
     * 
     * @return descrizioneClassifica
     */
    public java.lang.String getDescrizioneClassifica() {
        return descrizioneClassifica;
    }


    /**
     * Sets the descrizioneClassifica value for this TitolarioInfo.
     * 
     * @param descrizioneClassifica
     */
    public void setDescrizioneClassifica(java.lang.String descrizioneClassifica) {
        this.descrizioneClassifica = descrizioneClassifica;
    }


    /**
     * Gets the descrizioneFascicolo value for this TitolarioInfo.
     * 
     * @return descrizioneFascicolo
     */
    public java.lang.String getDescrizioneFascicolo() {
        return descrizioneFascicolo;
    }


    /**
     * Sets the descrizioneFascicolo value for this TitolarioInfo.
     * 
     * @param descrizioneFascicolo
     */
    public void setDescrizioneFascicolo(java.lang.String descrizioneFascicolo) {
        this.descrizioneFascicolo = descrizioneFascicolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TitolarioInfo)) return false;
        TitolarioInfo other = (TitolarioInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceClassifica==null && other.getCodiceClassifica()==null) || 
             (this.codiceClassifica!=null &&
              this.codiceClassifica.equals(other.getCodiceClassifica()))) &&
            ((this.codiceFascicolo==null && other.getCodiceFascicolo()==null) || 
             (this.codiceFascicolo!=null &&
              this.codiceFascicolo.equals(other.getCodiceFascicolo()))) &&
            ((this.custode==null && other.getCustode()==null) || 
             (this.custode!=null &&
              this.custode.equals(other.getCustode()))) &&
            ((this.descrizioneClassifica==null && other.getDescrizioneClassifica()==null) || 
             (this.descrizioneClassifica!=null &&
              this.descrizioneClassifica.equals(other.getDescrizioneClassifica()))) &&
            ((this.descrizioneFascicolo==null && other.getDescrizioneFascicolo()==null) || 
             (this.descrizioneFascicolo!=null &&
              this.descrizioneFascicolo.equals(other.getDescrizioneFascicolo())));
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
        if (getCodiceClassifica() != null) {
            _hashCode += getCodiceClassifica().hashCode();
        }
        if (getCodiceFascicolo() != null) {
            _hashCode += getCodiceFascicolo().hashCode();
        }
        if (getCustode() != null) {
            _hashCode += getCustode().hashCode();
        }
        if (getDescrizioneClassifica() != null) {
            _hashCode += getDescrizioneClassifica().hashCode();
        }
        if (getDescrizioneFascicolo() != null) {
            _hashCode += getDescrizioneFascicolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TitolarioInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TitolarioInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceClassifica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceClassifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Custode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneClassifica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DescrizioneClassifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DescrizioneFascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
