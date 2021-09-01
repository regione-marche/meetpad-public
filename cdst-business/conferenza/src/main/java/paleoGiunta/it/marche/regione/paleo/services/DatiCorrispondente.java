/**
 * DatiCorrispondente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class DatiCorrispondente  extends paleoGiunta.it.marche.regione.paleo.services.BEBase  implements java.io.Serializable {
    private java.lang.String cognome;

    private java.lang.String email;

    private java.lang.String idFiscale;

    private java.lang.String istatComune;

    private java.lang.String nome;

    private paleoGiunta.it.marche.regione.paleo.services.TipoVoceRubrica tipo;

    public DatiCorrispondente() {
    }

    public DatiCorrispondente(
           paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String cognome,
           java.lang.String email,
           java.lang.String idFiscale,
           java.lang.String istatComune,
           java.lang.String nome,
           paleoGiunta.it.marche.regione.paleo.services.TipoVoceRubrica tipo) {
        super(
            messaggioRisultato);
        this.cognome = cognome;
        this.email = email;
        this.idFiscale = idFiscale;
        this.istatComune = istatComune;
        this.nome = nome;
        this.tipo = tipo;
    }


    /**
     * Gets the cognome value for this DatiCorrispondente.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this DatiCorrispondente.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the email value for this DatiCorrispondente.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this DatiCorrispondente.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the idFiscale value for this DatiCorrispondente.
     * 
     * @return idFiscale
     */
    public java.lang.String getIdFiscale() {
        return idFiscale;
    }


    /**
     * Sets the idFiscale value for this DatiCorrispondente.
     * 
     * @param idFiscale
     */
    public void setIdFiscale(java.lang.String idFiscale) {
        this.idFiscale = idFiscale;
    }


    /**
     * Gets the istatComune value for this DatiCorrispondente.
     * 
     * @return istatComune
     */
    public java.lang.String getIstatComune() {
        return istatComune;
    }


    /**
     * Sets the istatComune value for this DatiCorrispondente.
     * 
     * @param istatComune
     */
    public void setIstatComune(java.lang.String istatComune) {
        this.istatComune = istatComune;
    }


    /**
     * Gets the nome value for this DatiCorrispondente.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this DatiCorrispondente.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the tipo value for this DatiCorrispondente.
     * 
     * @return tipo
     */
    public paleoGiunta.it.marche.regione.paleo.services.TipoVoceRubrica getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this DatiCorrispondente.
     * 
     * @param tipo
     */
    public void setTipo(paleoGiunta.it.marche.regione.paleo.services.TipoVoceRubrica tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiCorrispondente)) return false;
        DatiCorrispondente other = (DatiCorrispondente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.idFiscale==null && other.getIdFiscale()==null) || 
             (this.idFiscale!=null &&
              this.idFiscale.equals(other.getIdFiscale()))) &&
            ((this.istatComune==null && other.getIstatComune()==null) || 
             (this.istatComune!=null &&
              this.istatComune.equals(other.getIstatComune()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
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
        int _hashCode = super.hashCode();
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getIdFiscale() != null) {
            _hashCode += getIdFiscale().hashCode();
        }
        if (getIstatComune() != null) {
            _hashCode += getIstatComune().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiCorrispondente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiCorrispondente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Email"));
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
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Nome"));
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
