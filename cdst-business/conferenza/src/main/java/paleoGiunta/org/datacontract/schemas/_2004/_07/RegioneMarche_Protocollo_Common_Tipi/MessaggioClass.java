/**
 * MessaggioClass.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi;

public class MessaggioClass  implements java.io.Serializable {
    private int _NumeroRecordRestituiti;

    private java.lang.String descrizione;

    private int nrec;

    private paleoGiunta.it.marche.regione.paleo.services.TipoRisultato tipoRisultato;

    public MessaggioClass() {
    }

    public MessaggioClass(
           int _NumeroRecordRestituiti,
           java.lang.String descrizione,
           int nrec,
           paleoGiunta.it.marche.regione.paleo.services.TipoRisultato tipoRisultato) {
           this._NumeroRecordRestituiti = _NumeroRecordRestituiti;
           this.descrizione = descrizione;
           this.nrec = nrec;
           this.tipoRisultato = tipoRisultato;
    }


    /**
     * Gets the _NumeroRecordRestituiti value for this MessaggioClass.
     * 
     * @return _NumeroRecordRestituiti
     */
    public int get_NumeroRecordRestituiti() {
        return _NumeroRecordRestituiti;
    }


    /**
     * Sets the _NumeroRecordRestituiti value for this MessaggioClass.
     * 
     * @param _NumeroRecordRestituiti
     */
    public void set_NumeroRecordRestituiti(int _NumeroRecordRestituiti) {
        this._NumeroRecordRestituiti = _NumeroRecordRestituiti;
    }


    /**
     * Gets the descrizione value for this MessaggioClass.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this MessaggioClass.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the nrec value for this MessaggioClass.
     * 
     * @return nrec
     */
    public int getNrec() {
        return nrec;
    }


    /**
     * Sets the nrec value for this MessaggioClass.
     * 
     * @param nrec
     */
    public void setNrec(int nrec) {
        this.nrec = nrec;
    }


    /**
     * Gets the tipoRisultato value for this MessaggioClass.
     * 
     * @return tipoRisultato
     */
    public paleoGiunta.it.marche.regione.paleo.services.TipoRisultato getTipoRisultato() {
        return tipoRisultato;
    }


    /**
     * Sets the tipoRisultato value for this MessaggioClass.
     * 
     * @param tipoRisultato
     */
    public void setTipoRisultato(paleoGiunta.it.marche.regione.paleo.services.TipoRisultato tipoRisultato) {
        this.tipoRisultato = tipoRisultato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessaggioClass)) return false;
        MessaggioClass other = (MessaggioClass) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this._NumeroRecordRestituiti == other.get_NumeroRecordRestituiti() &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            this.nrec == other.getNrec() &&
            ((this.tipoRisultato==null && other.getTipoRisultato()==null) || 
             (this.tipoRisultato!=null &&
              this.tipoRisultato.equals(other.getTipoRisultato())));
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
        _hashCode += get_NumeroRecordRestituiti();
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        _hashCode += getNrec();
        if (getTipoRisultato() != null) {
            _hashCode += getTipoRisultato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessaggioClass.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "MessaggioClass"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NumeroRecordRestituiti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "_NumeroRecordRestituiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "nrec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRisultato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "tipoRisultato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoRisultato"));
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
