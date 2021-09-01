/**
 * DatiNuovoFascicolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class DatiNuovoFascicolo  implements java.io.Serializable {
    private java.lang.Integer anniConservazione;

    private java.lang.String codiceClassifica;

    private java.lang.String codiceFaldone;

    private it.marche.regione.paleo.services.OperatorePaleo custode;

    private java.lang.String descrizione;

    private java.lang.Integer idSerieArchivistica;

    private java.lang.Integer idTipoDati;

    private java.lang.String note;

    public DatiNuovoFascicolo() {
    }

    public DatiNuovoFascicolo(
           java.lang.Integer anniConservazione,
           java.lang.String codiceClassifica,
           java.lang.String codiceFaldone,
           it.marche.regione.paleo.services.OperatorePaleo custode,
           java.lang.String descrizione,
           java.lang.Integer idSerieArchivistica,
           java.lang.Integer idTipoDati,
           java.lang.String note) {
           this.anniConservazione = anniConservazione;
           this.codiceClassifica = codiceClassifica;
           this.codiceFaldone = codiceFaldone;
           this.custode = custode;
           this.descrizione = descrizione;
           this.idSerieArchivistica = idSerieArchivistica;
           this.idTipoDati = idTipoDati;
           this.note = note;
    }


    /**
     * Gets the anniConservazione value for this DatiNuovoFascicolo.
     * 
     * @return anniConservazione
     */
    public java.lang.Integer getAnniConservazione() {
        return anniConservazione;
    }


    /**
     * Sets the anniConservazione value for this DatiNuovoFascicolo.
     * 
     * @param anniConservazione
     */
    public void setAnniConservazione(java.lang.Integer anniConservazione) {
        this.anniConservazione = anniConservazione;
    }


    /**
     * Gets the codiceClassifica value for this DatiNuovoFascicolo.
     * 
     * @return codiceClassifica
     */
    public java.lang.String getCodiceClassifica() {
        return codiceClassifica;
    }


    /**
     * Sets the codiceClassifica value for this DatiNuovoFascicolo.
     * 
     * @param codiceClassifica
     */
    public void setCodiceClassifica(java.lang.String codiceClassifica) {
        this.codiceClassifica = codiceClassifica;
    }


    /**
     * Gets the codiceFaldone value for this DatiNuovoFascicolo.
     * 
     * @return codiceFaldone
     */
    public java.lang.String getCodiceFaldone() {
        return codiceFaldone;
    }


    /**
     * Sets the codiceFaldone value for this DatiNuovoFascicolo.
     * 
     * @param codiceFaldone
     */
    public void setCodiceFaldone(java.lang.String codiceFaldone) {
        this.codiceFaldone = codiceFaldone;
    }


    /**
     * Gets the custode value for this DatiNuovoFascicolo.
     * 
     * @return custode
     */
    public it.marche.regione.paleo.services.OperatorePaleo getCustode() {
        return custode;
    }


    /**
     * Sets the custode value for this DatiNuovoFascicolo.
     * 
     * @param custode
     */
    public void setCustode(it.marche.regione.paleo.services.OperatorePaleo custode) {
        this.custode = custode;
    }


    /**
     * Gets the descrizione value for this DatiNuovoFascicolo.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this DatiNuovoFascicolo.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idSerieArchivistica value for this DatiNuovoFascicolo.
     * 
     * @return idSerieArchivistica
     */
    public java.lang.Integer getIdSerieArchivistica() {
        return idSerieArchivistica;
    }


    /**
     * Sets the idSerieArchivistica value for this DatiNuovoFascicolo.
     * 
     * @param idSerieArchivistica
     */
    public void setIdSerieArchivistica(java.lang.Integer idSerieArchivistica) {
        this.idSerieArchivistica = idSerieArchivistica;
    }


    /**
     * Gets the idTipoDati value for this DatiNuovoFascicolo.
     * 
     * @return idTipoDati
     */
    public java.lang.Integer getIdTipoDati() {
        return idTipoDati;
    }


    /**
     * Sets the idTipoDati value for this DatiNuovoFascicolo.
     * 
     * @param idTipoDati
     */
    public void setIdTipoDati(java.lang.Integer idTipoDati) {
        this.idTipoDati = idTipoDati;
    }


    /**
     * Gets the note value for this DatiNuovoFascicolo.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this DatiNuovoFascicolo.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiNuovoFascicolo)) return false;
        DatiNuovoFascicolo other = (DatiNuovoFascicolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.anniConservazione==null && other.getAnniConservazione()==null) || 
             (this.anniConservazione!=null &&
              this.anniConservazione.equals(other.getAnniConservazione()))) &&
            ((this.codiceClassifica==null && other.getCodiceClassifica()==null) || 
             (this.codiceClassifica!=null &&
              this.codiceClassifica.equals(other.getCodiceClassifica()))) &&
            ((this.codiceFaldone==null && other.getCodiceFaldone()==null) || 
             (this.codiceFaldone!=null &&
              this.codiceFaldone.equals(other.getCodiceFaldone()))) &&
            ((this.custode==null && other.getCustode()==null) || 
             (this.custode!=null &&
              this.custode.equals(other.getCustode()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idSerieArchivistica==null && other.getIdSerieArchivistica()==null) || 
             (this.idSerieArchivistica!=null &&
              this.idSerieArchivistica.equals(other.getIdSerieArchivistica()))) &&
            ((this.idTipoDati==null && other.getIdTipoDati()==null) || 
             (this.idTipoDati!=null &&
              this.idTipoDati.equals(other.getIdTipoDati()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote())));
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
        if (getAnniConservazione() != null) {
            _hashCode += getAnniConservazione().hashCode();
        }
        if (getCodiceClassifica() != null) {
            _hashCode += getCodiceClassifica().hashCode();
        }
        if (getCodiceFaldone() != null) {
            _hashCode += getCodiceFaldone().hashCode();
        }
        if (getCustode() != null) {
            _hashCode += getCustode().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdSerieArchivistica() != null) {
            _hashCode += getIdSerieArchivistica().hashCode();
        }
        if (getIdTipoDati() != null) {
            _hashCode += getIdTipoDati().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiNuovoFascicolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiNuovoFascicolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anniConservazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "AnniConservazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceClassifica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceClassifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFaldone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceFaldone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Custode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSerieArchivistica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdSerieArchivistica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoDati");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdTipoDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Note"));
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
