/**
 * RagioneTrasmissione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RagioneTrasmissione  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.String descrizione;

    private java.lang.Integer giorniScadenza;

    private java.lang.String nome;

    private java.lang.Boolean previstaPresaInCarico;

    private java.lang.Boolean previstaRisposta;

    private it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario;

    public RagioneTrasmissione() {
    }

    public RagioneTrasmissione(
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.Integer giorniScadenza,
           java.lang.String nome,
           java.lang.Boolean previstaPresaInCarico,
           java.lang.Boolean previstaRisposta,
           it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario) {
           this.codice = codice;
           this.descrizione = descrizione;
           this.giorniScadenza = giorniScadenza;
           this.nome = nome;
           this.previstaPresaInCarico = previstaPresaInCarico;
           this.previstaRisposta = previstaRisposta;
           this.tipoDestinatario = tipoDestinatario;
    }


    /**
     * Gets the codice value for this RagioneTrasmissione.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this RagioneTrasmissione.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this RagioneTrasmissione.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this RagioneTrasmissione.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the giorniScadenza value for this RagioneTrasmissione.
     * 
     * @return giorniScadenza
     */
    public java.lang.Integer getGiorniScadenza() {
        return giorniScadenza;
    }


    /**
     * Sets the giorniScadenza value for this RagioneTrasmissione.
     * 
     * @param giorniScadenza
     */
    public void setGiorniScadenza(java.lang.Integer giorniScadenza) {
        this.giorniScadenza = giorniScadenza;
    }


    /**
     * Gets the nome value for this RagioneTrasmissione.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this RagioneTrasmissione.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the previstaPresaInCarico value for this RagioneTrasmissione.
     * 
     * @return previstaPresaInCarico
     */
    public java.lang.Boolean getPrevistaPresaInCarico() {
        return previstaPresaInCarico;
    }


    /**
     * Sets the previstaPresaInCarico value for this RagioneTrasmissione.
     * 
     * @param previstaPresaInCarico
     */
    public void setPrevistaPresaInCarico(java.lang.Boolean previstaPresaInCarico) {
        this.previstaPresaInCarico = previstaPresaInCarico;
    }


    /**
     * Gets the previstaRisposta value for this RagioneTrasmissione.
     * 
     * @return previstaRisposta
     */
    public java.lang.Boolean getPrevistaRisposta() {
        return previstaRisposta;
    }


    /**
     * Sets the previstaRisposta value for this RagioneTrasmissione.
     * 
     * @param previstaRisposta
     */
    public void setPrevistaRisposta(java.lang.Boolean previstaRisposta) {
        this.previstaRisposta = previstaRisposta;
    }


    /**
     * Gets the tipoDestinatario value for this RagioneTrasmissione.
     * 
     * @return tipoDestinatario
     */
    public it.marche.regione.paleo.services.TipoDestinatarioTrasmissione getTipoDestinatario() {
        return tipoDestinatario;
    }


    /**
     * Sets the tipoDestinatario value for this RagioneTrasmissione.
     * 
     * @param tipoDestinatario
     */
    public void setTipoDestinatario(it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RagioneTrasmissione)) return false;
        RagioneTrasmissione other = (RagioneTrasmissione) obj;
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
            ((this.giorniScadenza==null && other.getGiorniScadenza()==null) || 
             (this.giorniScadenza!=null &&
              this.giorniScadenza.equals(other.getGiorniScadenza()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.previstaPresaInCarico==null && other.getPrevistaPresaInCarico()==null) || 
             (this.previstaPresaInCarico!=null &&
              this.previstaPresaInCarico.equals(other.getPrevistaPresaInCarico()))) &&
            ((this.previstaRisposta==null && other.getPrevistaRisposta()==null) || 
             (this.previstaRisposta!=null &&
              this.previstaRisposta.equals(other.getPrevistaRisposta()))) &&
            ((this.tipoDestinatario==null && other.getTipoDestinatario()==null) || 
             (this.tipoDestinatario!=null &&
              this.tipoDestinatario.equals(other.getTipoDestinatario())));
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
        if (getGiorniScadenza() != null) {
            _hashCode += getGiorniScadenza().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getPrevistaPresaInCarico() != null) {
            _hashCode += getPrevistaPresaInCarico().hashCode();
        }
        if (getPrevistaRisposta() != null) {
            _hashCode += getPrevistaRisposta().hashCode();
        }
        if (getTipoDestinatario() != null) {
            _hashCode += getTipoDestinatario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RagioneTrasmissione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RagioneTrasmissione"));
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
        elemField.setFieldName("giorniScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GiorniScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("previstaPresaInCarico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "PrevistaPresaInCarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("previstaRisposta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "PrevistaRisposta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDestinatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDestinatarioTrasmissione"));
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
