/**
 * OperatorePaleo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class OperatorePaleo  implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String codiceRuolo;

    private java.lang.String codiceUO;

    private java.lang.String codiceUOSicurezza;

    private java.lang.String cognome;

    private java.lang.String nome;

    private java.lang.String ruolo;

    private java.lang.String UO;

    public OperatorePaleo() {
    }

    public OperatorePaleo(
           java.lang.String codiceFiscale,
           java.lang.String codiceRuolo,
           java.lang.String codiceUO,
           java.lang.String codiceUOSicurezza,
           java.lang.String cognome,
           java.lang.String nome,
           java.lang.String ruolo,
           java.lang.String UO) {
           this.codiceFiscale = codiceFiscale;
           this.codiceRuolo = codiceRuolo;
           this.codiceUO = codiceUO;
           this.codiceUOSicurezza = codiceUOSicurezza;
           this.cognome = cognome;
           this.nome = nome;
           this.ruolo = ruolo;
           this.UO = UO;
    }


    /**
     * Gets the codiceFiscale value for this OperatorePaleo.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this OperatorePaleo.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the codiceRuolo value for this OperatorePaleo.
     * 
     * @return codiceRuolo
     */
    public java.lang.String getCodiceRuolo() {
        return codiceRuolo;
    }


    /**
     * Sets the codiceRuolo value for this OperatorePaleo.
     * 
     * @param codiceRuolo
     */
    public void setCodiceRuolo(java.lang.String codiceRuolo) {
        this.codiceRuolo = codiceRuolo;
    }


    /**
     * Gets the codiceUO value for this OperatorePaleo.
     * 
     * @return codiceUO
     */
    public java.lang.String getCodiceUO() {
        return codiceUO;
    }


    /**
     * Sets the codiceUO value for this OperatorePaleo.
     * 
     * @param codiceUO
     */
    public void setCodiceUO(java.lang.String codiceUO) {
        this.codiceUO = codiceUO;
    }


    /**
     * Gets the codiceUOSicurezza value for this OperatorePaleo.
     * 
     * @return codiceUOSicurezza
     */
    public java.lang.String getCodiceUOSicurezza() {
        return codiceUOSicurezza;
    }


    /**
     * Sets the codiceUOSicurezza value for this OperatorePaleo.
     * 
     * @param codiceUOSicurezza
     */
    public void setCodiceUOSicurezza(java.lang.String codiceUOSicurezza) {
        this.codiceUOSicurezza = codiceUOSicurezza;
    }


    /**
     * Gets the cognome value for this OperatorePaleo.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this OperatorePaleo.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the nome value for this OperatorePaleo.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this OperatorePaleo.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the ruolo value for this OperatorePaleo.
     * 
     * @return ruolo
     */
    public java.lang.String getRuolo() {
        return ruolo;
    }


    /**
     * Sets the ruolo value for this OperatorePaleo.
     * 
     * @param ruolo
     */
    public void setRuolo(java.lang.String ruolo) {
        this.ruolo = ruolo;
    }


    /**
     * Gets the UO value for this OperatorePaleo.
     * 
     * @return UO
     */
    public java.lang.String getUO() {
        return UO;
    }


    /**
     * Sets the UO value for this OperatorePaleo.
     * 
     * @param UO
     */
    public void setUO(java.lang.String UO) {
        this.UO = UO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OperatorePaleo)) return false;
        OperatorePaleo other = (OperatorePaleo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.codiceRuolo==null && other.getCodiceRuolo()==null) || 
             (this.codiceRuolo!=null &&
              this.codiceRuolo.equals(other.getCodiceRuolo()))) &&
            ((this.codiceUO==null && other.getCodiceUO()==null) || 
             (this.codiceUO!=null &&
              this.codiceUO.equals(other.getCodiceUO()))) &&
            ((this.codiceUOSicurezza==null && other.getCodiceUOSicurezza()==null) || 
             (this.codiceUOSicurezza!=null &&
              this.codiceUOSicurezza.equals(other.getCodiceUOSicurezza()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.ruolo==null && other.getRuolo()==null) || 
             (this.ruolo!=null &&
              this.ruolo.equals(other.getRuolo()))) &&
            ((this.UO==null && other.getUO()==null) || 
             (this.UO!=null &&
              this.UO.equals(other.getUO())));
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
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCodiceRuolo() != null) {
            _hashCode += getCodiceRuolo().hashCode();
        }
        if (getCodiceUO() != null) {
            _hashCode += getCodiceUO().hashCode();
        }
        if (getCodiceUOSicurezza() != null) {
            _hashCode += getCodiceUOSicurezza().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getRuolo() != null) {
            _hashCode += getRuolo().hashCode();
        }
        if (getUO() != null) {
            _hashCode += getUO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OperatorePaleo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceRuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceUO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceUO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceUOSicurezza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceUOSicurezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Ruolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "UO"));
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
