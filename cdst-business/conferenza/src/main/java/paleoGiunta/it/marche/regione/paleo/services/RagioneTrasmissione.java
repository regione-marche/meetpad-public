/**
 * RagioneTrasmissione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class RagioneTrasmissione  extends paleoGiunta.it.marche.regione.paleo.services.Messaggio  implements java.io.Serializable {
    private java.lang.Boolean canWrite;

    private java.lang.String codice;

    private java.lang.String descrizione;

    private java.lang.String descrizioneEstesa;

    private java.lang.Boolean estendeVisibilita;

    private java.lang.Integer giorniScadenza;

    private java.lang.Boolean previstaApprovazione;

    private java.lang.Boolean previstaPresaInCarico;

    private java.lang.Boolean previstaRisposta;

    private java.lang.Boolean previstaVisioneMassiva;

    private paleoGiunta.it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario;

    private java.lang.Boolean visibile;

    public RagioneTrasmissione() {
    }

    public RagioneTrasmissione(
           paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.MessaggioClass messaggioRisultato,
           java.lang.Boolean canWrite,
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.String descrizioneEstesa,
           java.lang.Boolean estendeVisibilita,
           java.lang.Integer giorniScadenza,
           java.lang.Boolean previstaApprovazione,
           java.lang.Boolean previstaPresaInCarico,
           java.lang.Boolean previstaRisposta,
           java.lang.Boolean previstaVisioneMassiva,
           paleoGiunta.it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario,
           java.lang.Boolean visibile) {
        super(
            messaggioRisultato);
        this.canWrite = canWrite;
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizioneEstesa = descrizioneEstesa;
        this.estendeVisibilita = estendeVisibilita;
        this.giorniScadenza = giorniScadenza;
        this.previstaApprovazione = previstaApprovazione;
        this.previstaPresaInCarico = previstaPresaInCarico;
        this.previstaRisposta = previstaRisposta;
        this.previstaVisioneMassiva = previstaVisioneMassiva;
        this.tipoDestinatario = tipoDestinatario;
        this.visibile = visibile;
    }


    /**
     * Gets the canWrite value for this RagioneTrasmissione.
     * 
     * @return canWrite
     */
    public java.lang.Boolean getCanWrite() {
        return canWrite;
    }


    /**
     * Sets the canWrite value for this RagioneTrasmissione.
     * 
     * @param canWrite
     */
    public void setCanWrite(java.lang.Boolean canWrite) {
        this.canWrite = canWrite;
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
     * Gets the descrizioneEstesa value for this RagioneTrasmissione.
     * 
     * @return descrizioneEstesa
     */
    public java.lang.String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }


    /**
     * Sets the descrizioneEstesa value for this RagioneTrasmissione.
     * 
     * @param descrizioneEstesa
     */
    public void setDescrizioneEstesa(java.lang.String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }


    /**
     * Gets the estendeVisibilita value for this RagioneTrasmissione.
     * 
     * @return estendeVisibilita
     */
    public java.lang.Boolean getEstendeVisibilita() {
        return estendeVisibilita;
    }


    /**
     * Sets the estendeVisibilita value for this RagioneTrasmissione.
     * 
     * @param estendeVisibilita
     */
    public void setEstendeVisibilita(java.lang.Boolean estendeVisibilita) {
        this.estendeVisibilita = estendeVisibilita;
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
     * Gets the previstaApprovazione value for this RagioneTrasmissione.
     * 
     * @return previstaApprovazione
     */
    public java.lang.Boolean getPrevistaApprovazione() {
        return previstaApprovazione;
    }


    /**
     * Sets the previstaApprovazione value for this RagioneTrasmissione.
     * 
     * @param previstaApprovazione
     */
    public void setPrevistaApprovazione(java.lang.Boolean previstaApprovazione) {
        this.previstaApprovazione = previstaApprovazione;
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
     * Gets the previstaVisioneMassiva value for this RagioneTrasmissione.
     * 
     * @return previstaVisioneMassiva
     */
    public java.lang.Boolean getPrevistaVisioneMassiva() {
        return previstaVisioneMassiva;
    }


    /**
     * Sets the previstaVisioneMassiva value for this RagioneTrasmissione.
     * 
     * @param previstaVisioneMassiva
     */
    public void setPrevistaVisioneMassiva(java.lang.Boolean previstaVisioneMassiva) {
        this.previstaVisioneMassiva = previstaVisioneMassiva;
    }


    /**
     * Gets the tipoDestinatario value for this RagioneTrasmissione.
     * 
     * @return tipoDestinatario
     */
    public paleoGiunta.it.marche.regione.paleo.services.TipoDestinatarioTrasmissione getTipoDestinatario() {
        return tipoDestinatario;
    }


    /**
     * Sets the tipoDestinatario value for this RagioneTrasmissione.
     * 
     * @param tipoDestinatario
     */
    public void setTipoDestinatario(paleoGiunta.it.marche.regione.paleo.services.TipoDestinatarioTrasmissione tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }


    /**
     * Gets the visibile value for this RagioneTrasmissione.
     * 
     * @return visibile
     */
    public java.lang.Boolean getVisibile() {
        return visibile;
    }


    /**
     * Sets the visibile value for this RagioneTrasmissione.
     * 
     * @param visibile
     */
    public void setVisibile(java.lang.Boolean visibile) {
        this.visibile = visibile;
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
        _equals = super.equals(obj) && 
            ((this.canWrite==null && other.getCanWrite()==null) || 
             (this.canWrite!=null &&
              this.canWrite.equals(other.getCanWrite()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.descrizioneEstesa==null && other.getDescrizioneEstesa()==null) || 
             (this.descrizioneEstesa!=null &&
              this.descrizioneEstesa.equals(other.getDescrizioneEstesa()))) &&
            ((this.estendeVisibilita==null && other.getEstendeVisibilita()==null) || 
             (this.estendeVisibilita!=null &&
              this.estendeVisibilita.equals(other.getEstendeVisibilita()))) &&
            ((this.giorniScadenza==null && other.getGiorniScadenza()==null) || 
             (this.giorniScadenza!=null &&
              this.giorniScadenza.equals(other.getGiorniScadenza()))) &&
            ((this.previstaApprovazione==null && other.getPrevistaApprovazione()==null) || 
             (this.previstaApprovazione!=null &&
              this.previstaApprovazione.equals(other.getPrevistaApprovazione()))) &&
            ((this.previstaPresaInCarico==null && other.getPrevistaPresaInCarico()==null) || 
             (this.previstaPresaInCarico!=null &&
              this.previstaPresaInCarico.equals(other.getPrevistaPresaInCarico()))) &&
            ((this.previstaRisposta==null && other.getPrevistaRisposta()==null) || 
             (this.previstaRisposta!=null &&
              this.previstaRisposta.equals(other.getPrevistaRisposta()))) &&
            ((this.previstaVisioneMassiva==null && other.getPrevistaVisioneMassiva()==null) || 
             (this.previstaVisioneMassiva!=null &&
              this.previstaVisioneMassiva.equals(other.getPrevistaVisioneMassiva()))) &&
            ((this.tipoDestinatario==null && other.getTipoDestinatario()==null) || 
             (this.tipoDestinatario!=null &&
              this.tipoDestinatario.equals(other.getTipoDestinatario()))) &&
            ((this.visibile==null && other.getVisibile()==null) || 
             (this.visibile!=null &&
              this.visibile.equals(other.getVisibile())));
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
        if (getCanWrite() != null) {
            _hashCode += getCanWrite().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDescrizioneEstesa() != null) {
            _hashCode += getDescrizioneEstesa().hashCode();
        }
        if (getEstendeVisibilita() != null) {
            _hashCode += getEstendeVisibilita().hashCode();
        }
        if (getGiorniScadenza() != null) {
            _hashCode += getGiorniScadenza().hashCode();
        }
        if (getPrevistaApprovazione() != null) {
            _hashCode += getPrevistaApprovazione().hashCode();
        }
        if (getPrevistaPresaInCarico() != null) {
            _hashCode += getPrevistaPresaInCarico().hashCode();
        }
        if (getPrevistaRisposta() != null) {
            _hashCode += getPrevistaRisposta().hashCode();
        }
        if (getPrevistaVisioneMassiva() != null) {
            _hashCode += getPrevistaVisioneMassiva().hashCode();
        }
        if (getTipoDestinatario() != null) {
            _hashCode += getTipoDestinatario().hashCode();
        }
        if (getVisibile() != null) {
            _hashCode += getVisibile().hashCode();
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
        elemField.setFieldName("canWrite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CanWrite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("descrizioneEstesa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DescrizioneEstesa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estendeVisibilita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "EstendeVisibilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorniScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "GiorniScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("previstaApprovazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "PrevistaApprovazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setFieldName("previstaVisioneMassiva");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "PrevistaVisioneMassiva"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Visibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
