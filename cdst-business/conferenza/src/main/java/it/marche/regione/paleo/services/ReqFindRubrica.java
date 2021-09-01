/**
 * ReqFindRubrica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqFindRubrica  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.String descrizione;

    private java.lang.String idFiscale;

    private java.lang.String istatComune;

    private it.marche.regione.paleo.services.TipoVoceRubrica tipo;

    public ReqFindRubrica() {
    }

    public ReqFindRubrica(
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.String idFiscale,
           java.lang.String istatComune,
           it.marche.regione.paleo.services.TipoVoceRubrica tipo) {
           this.codice = codice;
           this.descrizione = descrizione;
           this.idFiscale = idFiscale;
           this.istatComune = istatComune;
           this.tipo = tipo;
    }


    /**
     * Gets the codice value for this ReqFindRubrica.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this ReqFindRubrica.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this ReqFindRubrica.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ReqFindRubrica.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idFiscale value for this ReqFindRubrica.
     * 
     * @return idFiscale
     */
    public java.lang.String getIdFiscale() {
        return idFiscale;
    }


    /**
     * Sets the idFiscale value for this ReqFindRubrica.
     * 
     * @param idFiscale
     */
    public void setIdFiscale(java.lang.String idFiscale) {
        this.idFiscale = idFiscale;
    }


    /**
     * Gets the istatComune value for this ReqFindRubrica.
     * 
     * @return istatComune
     */
    public java.lang.String getIstatComune() {
        return istatComune;
    }


    /**
     * Sets the istatComune value for this ReqFindRubrica.
     * 
     * @param istatComune
     */
    public void setIstatComune(java.lang.String istatComune) {
        this.istatComune = istatComune;
    }


    /**
     * Gets the tipo value for this ReqFindRubrica.
     * 
     * @return tipo
     */
    public it.marche.regione.paleo.services.TipoVoceRubrica getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this ReqFindRubrica.
     * 
     * @param tipo
     */
    public void setTipo(it.marche.regione.paleo.services.TipoVoceRubrica tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqFindRubrica)) return false;
        ReqFindRubrica other = (ReqFindRubrica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idFiscale==null && other.getIdFiscale()==null) || 
             (this.idFiscale!=null &&
              this.idFiscale.equals(other.getIdFiscale()))) &&
            ((this.istatComune==null && other.getIstatComune()==null) || 
             (this.istatComune!=null &&
              this.istatComune.equals(other.getIstatComune()))) &&
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdFiscale() != null) {
            _hashCode += getIdFiscale().hashCode();
        }
        if (getIstatComune() != null) {
            _hashCode += getIstatComune().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqFindRubrica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqFindRubrica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoVoceRubrica"));
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
