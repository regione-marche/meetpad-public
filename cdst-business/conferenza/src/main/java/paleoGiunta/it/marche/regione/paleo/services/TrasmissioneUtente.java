/**
 * TrasmissioneUtente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class TrasmissioneUtente  implements java.io.Serializable {
    private java.util.Calendar dataScadenza;

    private java.lang.String note;

    private paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatoreDestinatario;

    private java.lang.String ragione;

    public TrasmissioneUtente() {
    }

    public TrasmissioneUtente(
           java.util.Calendar dataScadenza,
           java.lang.String note,
           paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatoreDestinatario,
           java.lang.String ragione) {
           this.dataScadenza = dataScadenza;
           this.note = note;
           this.operatoreDestinatario = operatoreDestinatario;
           this.ragione = ragione;
    }


    /**
     * Gets the dataScadenza value for this TrasmissioneUtente.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this TrasmissioneUtente.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the note value for this TrasmissioneUtente.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this TrasmissioneUtente.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the operatoreDestinatario value for this TrasmissioneUtente.
     * 
     * @return operatoreDestinatario
     */
    public paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo getOperatoreDestinatario() {
        return operatoreDestinatario;
    }


    /**
     * Sets the operatoreDestinatario value for this TrasmissioneUtente.
     * 
     * @param operatoreDestinatario
     */
    public void setOperatoreDestinatario(paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo operatoreDestinatario) {
        this.operatoreDestinatario = operatoreDestinatario;
    }


    /**
     * Gets the ragione value for this TrasmissioneUtente.
     * 
     * @return ragione
     */
    public java.lang.String getRagione() {
        return ragione;
    }


    /**
     * Sets the ragione value for this TrasmissioneUtente.
     * 
     * @param ragione
     */
    public void setRagione(java.lang.String ragione) {
        this.ragione = ragione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrasmissioneUtente)) return false;
        TrasmissioneUtente other = (TrasmissioneUtente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.operatoreDestinatario==null && other.getOperatoreDestinatario()==null) || 
             (this.operatoreDestinatario!=null &&
              this.operatoreDestinatario.equals(other.getOperatoreDestinatario()))) &&
            ((this.ragione==null && other.getRagione()==null) || 
             (this.ragione!=null &&
              this.ragione.equals(other.getRagione())));
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
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getOperatoreDestinatario() != null) {
            _hashCode += getOperatoreDestinatario().hashCode();
        }
        if (getRagione() != null) {
            _hashCode += getRagione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrasmissioneUtente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("operatoreDestinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatoreDestinatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Ragione"));
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
