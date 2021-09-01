/**
 * ReqDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class ReqDocumento  implements java.io.Serializable {
    private it.marche.regione.paleo.services.Classificazione[] classificazioni;

    private it.marche.regione.paleo.services.Allegato[] documentiAllegati;

    private it.marche.regione.paleo.services.File documentoPrincipale;

    private boolean documentoPrincipaleAcquisitoIntegralmente;

    private it.marche.regione.paleo.services.TipoOriginale documentoPrincipaleOriginale;

    private java.lang.String note;

    private java.lang.String oggetto;

    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    private boolean privato;

    private it.marche.regione.paleo.services.DatiProcedimento procedimento;

    private java.lang.String tipoDocumento;

    private it.marche.regione.paleo.services.Trasmissione trasmissione;

    public ReqDocumento() {
    }

    public ReqDocumento(
           it.marche.regione.paleo.services.Classificazione[] classificazioni,
           it.marche.regione.paleo.services.Allegato[] documentiAllegati,
           it.marche.regione.paleo.services.File documentoPrincipale,
           boolean documentoPrincipaleAcquisitoIntegralmente,
           it.marche.regione.paleo.services.TipoOriginale documentoPrincipaleOriginale,
           java.lang.String note,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           boolean privato,
           it.marche.regione.paleo.services.DatiProcedimento procedimento,
           java.lang.String tipoDocumento,
           it.marche.regione.paleo.services.Trasmissione trasmissione) {
           this.classificazioni = classificazioni;
           this.documentiAllegati = documentiAllegati;
           this.documentoPrincipale = documentoPrincipale;
           this.documentoPrincipaleAcquisitoIntegralmente = documentoPrincipaleAcquisitoIntegralmente;
           this.documentoPrincipaleOriginale = documentoPrincipaleOriginale;
           this.note = note;
           this.oggetto = oggetto;
           this.operatore = operatore;
           this.privato = privato;
           this.procedimento = procedimento;
           this.tipoDocumento = tipoDocumento;
           this.trasmissione = trasmissione;
    }


    /**
     * Gets the classificazioni value for this ReqDocumento.
     * 
     * @return classificazioni
     */
    public it.marche.regione.paleo.services.Classificazione[] getClassificazioni() {
        return classificazioni;
    }


    /**
     * Sets the classificazioni value for this ReqDocumento.
     * 
     * @param classificazioni
     */
    public void setClassificazioni(it.marche.regione.paleo.services.Classificazione[] classificazioni) {
        this.classificazioni = classificazioni;
    }


    /**
     * Gets the documentiAllegati value for this ReqDocumento.
     * 
     * @return documentiAllegati
     */
    public it.marche.regione.paleo.services.Allegato[] getDocumentiAllegati() {
        return documentiAllegati;
    }


    /**
     * Sets the documentiAllegati value for this ReqDocumento.
     * 
     * @param documentiAllegati
     */
    public void setDocumentiAllegati(it.marche.regione.paleo.services.Allegato[] documentiAllegati) {
        this.documentiAllegati = documentiAllegati;
    }


    /**
     * Gets the documentoPrincipale value for this ReqDocumento.
     * 
     * @return documentoPrincipale
     */
    public it.marche.regione.paleo.services.File getDocumentoPrincipale() {
        return documentoPrincipale;
    }


    /**
     * Sets the documentoPrincipale value for this ReqDocumento.
     * 
     * @param documentoPrincipale
     */
    public void setDocumentoPrincipale(it.marche.regione.paleo.services.File documentoPrincipale) {
        this.documentoPrincipale = documentoPrincipale;
    }


    /**
     * Gets the documentoPrincipaleAcquisitoIntegralmente value for this ReqDocumento.
     * 
     * @return documentoPrincipaleAcquisitoIntegralmente
     */
    public boolean isDocumentoPrincipaleAcquisitoIntegralmente() {
        return documentoPrincipaleAcquisitoIntegralmente;
    }


    /**
     * Sets the documentoPrincipaleAcquisitoIntegralmente value for this ReqDocumento.
     * 
     * @param documentoPrincipaleAcquisitoIntegralmente
     */
    public void setDocumentoPrincipaleAcquisitoIntegralmente(boolean documentoPrincipaleAcquisitoIntegralmente) {
        this.documentoPrincipaleAcquisitoIntegralmente = documentoPrincipaleAcquisitoIntegralmente;
    }


    /**
     * Gets the documentoPrincipaleOriginale value for this ReqDocumento.
     * 
     * @return documentoPrincipaleOriginale
     */
    public it.marche.regione.paleo.services.TipoOriginale getDocumentoPrincipaleOriginale() {
        return documentoPrincipaleOriginale;
    }


    /**
     * Sets the documentoPrincipaleOriginale value for this ReqDocumento.
     * 
     * @param documentoPrincipaleOriginale
     */
    public void setDocumentoPrincipaleOriginale(it.marche.regione.paleo.services.TipoOriginale documentoPrincipaleOriginale) {
        this.documentoPrincipaleOriginale = documentoPrincipaleOriginale;
    }


    /**
     * Gets the note value for this ReqDocumento.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this ReqDocumento.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the oggetto value for this ReqDocumento.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this ReqDocumento.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the operatore value for this ReqDocumento.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqDocumento.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }


    /**
     * Gets the privato value for this ReqDocumento.
     * 
     * @return privato
     */
    public boolean isPrivato() {
        return privato;
    }


    /**
     * Sets the privato value for this ReqDocumento.
     * 
     * @param privato
     */
    public void setPrivato(boolean privato) {
        this.privato = privato;
    }


    /**
     * Gets the procedimento value for this ReqDocumento.
     * 
     * @return procedimento
     */
    public it.marche.regione.paleo.services.DatiProcedimento getProcedimento() {
        return procedimento;
    }


    /**
     * Sets the procedimento value for this ReqDocumento.
     * 
     * @param procedimento
     */
    public void setProcedimento(it.marche.regione.paleo.services.DatiProcedimento procedimento) {
        this.procedimento = procedimento;
    }


    /**
     * Gets the tipoDocumento value for this ReqDocumento.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this ReqDocumento.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the trasmissione value for this ReqDocumento.
     * 
     * @return trasmissione
     */
    public it.marche.regione.paleo.services.Trasmissione getTrasmissione() {
        return trasmissione;
    }


    /**
     * Sets the trasmissione value for this ReqDocumento.
     * 
     * @param trasmissione
     */
    public void setTrasmissione(it.marche.regione.paleo.services.Trasmissione trasmissione) {
        this.trasmissione = trasmissione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqDocumento)) return false;
        ReqDocumento other = (ReqDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.classificazioni==null && other.getClassificazioni()==null) || 
             (this.classificazioni!=null &&
              java.util.Arrays.equals(this.classificazioni, other.getClassificazioni()))) &&
            ((this.documentiAllegati==null && other.getDocumentiAllegati()==null) || 
             (this.documentiAllegati!=null &&
              java.util.Arrays.equals(this.documentiAllegati, other.getDocumentiAllegati()))) &&
            ((this.documentoPrincipale==null && other.getDocumentoPrincipale()==null) || 
             (this.documentoPrincipale!=null &&
              this.documentoPrincipale.equals(other.getDocumentoPrincipale()))) &&
            this.documentoPrincipaleAcquisitoIntegralmente == other.isDocumentoPrincipaleAcquisitoIntegralmente() &&
            ((this.documentoPrincipaleOriginale==null && other.getDocumentoPrincipaleOriginale()==null) || 
             (this.documentoPrincipaleOriginale!=null &&
              this.documentoPrincipaleOriginale.equals(other.getDocumentoPrincipaleOriginale()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore()))) &&
            this.privato == other.isPrivato() &&
            ((this.procedimento==null && other.getProcedimento()==null) || 
             (this.procedimento!=null &&
              this.procedimento.equals(other.getProcedimento()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.trasmissione==null && other.getTrasmissione()==null) || 
             (this.trasmissione!=null &&
              this.trasmissione.equals(other.getTrasmissione())));
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
        if (getClassificazioni() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClassificazioni());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClassificazioni(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentiAllegati() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentiAllegati());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentiAllegati(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentoPrincipale() != null) {
            _hashCode += getDocumentoPrincipale().hashCode();
        }
        _hashCode += (isDocumentoPrincipaleAcquisitoIntegralmente() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDocumentoPrincipaleOriginale() != null) {
            _hashCode += getDocumentoPrincipaleOriginale().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        _hashCode += (isPrivato() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getProcedimento() != null) {
            _hashCode += getProcedimento().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTrasmissione() != null) {
            _hashCode += getTrasmissione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classificazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazione"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentiAllegati");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocumentiAllegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegato"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentoPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocumentoPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "File"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentoPrincipaleAcquisitoIntegralmente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocumentoPrincipaleAcquisitoIntegralmente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentoPrincipaleOriginale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocumentoPrincipaleOriginale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoOriginale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Operatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Privato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Procedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiProcedimento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trasmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Trasmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Trasmissione"));
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
