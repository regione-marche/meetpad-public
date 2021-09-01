/**
 * TrasmissioneRuolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TrasmissioneRuolo  implements java.io.Serializable {
    private java.lang.String codiceUODestinataria;

    private java.util.Calendar dataScadenza;

    private java.lang.String note;

    private java.lang.String ragione;

    private java.lang.String ruoloDestinatario;

    public TrasmissioneRuolo() {
    }

    public TrasmissioneRuolo(
           java.lang.String codiceUODestinataria,
           java.util.Calendar dataScadenza,
           java.lang.String note,
           java.lang.String ragione,
           java.lang.String ruoloDestinatario) {
           this.codiceUODestinataria = codiceUODestinataria;
           this.dataScadenza = dataScadenza;
           this.note = note;
           this.ragione = ragione;
           this.ruoloDestinatario = ruoloDestinatario;
    }


    /**
     * Gets the codiceUODestinataria value for this TrasmissioneRuolo.
     * 
     * @return codiceUODestinataria
     */
    public java.lang.String getCodiceUODestinataria() {
        return codiceUODestinataria;
    }


    /**
     * Sets the codiceUODestinataria value for this TrasmissioneRuolo.
     * 
     * @param codiceUODestinataria
     */
    public void setCodiceUODestinataria(java.lang.String codiceUODestinataria) {
        this.codiceUODestinataria = codiceUODestinataria;
    }


    /**
     * Gets the dataScadenza value for this TrasmissioneRuolo.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this TrasmissioneRuolo.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the note value for this TrasmissioneRuolo.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this TrasmissioneRuolo.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the ragione value for this TrasmissioneRuolo.
     * 
     * @return ragione
     */
    public java.lang.String getRagione() {
        return ragione;
    }


    /**
     * Sets the ragione value for this TrasmissioneRuolo.
     * 
     * @param ragione
     */
    public void setRagione(java.lang.String ragione) {
        this.ragione = ragione;
    }


    /**
     * Gets the ruoloDestinatario value for this TrasmissioneRuolo.
     * 
     * @return ruoloDestinatario
     */
    public java.lang.String getRuoloDestinatario() {
        return ruoloDestinatario;
    }


    /**
     * Sets the ruoloDestinatario value for this TrasmissioneRuolo.
     * 
     * @param ruoloDestinatario
     */
    public void setRuoloDestinatario(java.lang.String ruoloDestinatario) {
        this.ruoloDestinatario = ruoloDestinatario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrasmissioneRuolo)) return false;
        TrasmissioneRuolo other = (TrasmissioneRuolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceUODestinataria==null && other.getCodiceUODestinataria()==null) || 
             (this.codiceUODestinataria!=null &&
              this.codiceUODestinataria.equals(other.getCodiceUODestinataria()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.ragione==null && other.getRagione()==null) || 
             (this.ragione!=null &&
              this.ragione.equals(other.getRagione()))) &&
            ((this.ruoloDestinatario==null && other.getRuoloDestinatario()==null) || 
             (this.ruoloDestinatario!=null &&
              this.ruoloDestinatario.equals(other.getRuoloDestinatario())));
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
        if (getCodiceUODestinataria() != null) {
            _hashCode += getCodiceUODestinataria().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getRagione() != null) {
            _hashCode += getRagione().hashCode();
        }
        if (getRuoloDestinatario() != null) {
            _hashCode += getRuoloDestinatario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrasmissioneRuolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceUODestinataria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceUODestinataria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("ragione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Ragione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruoloDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RuoloDestinatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
