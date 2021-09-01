/**
 * Trasmissione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class Trasmissione  implements java.io.Serializable {
    private java.lang.Boolean invioOriginaleCartaceo;

    private java.lang.String noteGenerali;

    private boolean segueCartaceo;

    private paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] trasmissioniRuolo;

    private paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] trasmissioniUtente;

    public Trasmissione() {
    }

    public Trasmissione(
           java.lang.Boolean invioOriginaleCartaceo,
           java.lang.String noteGenerali,
           boolean segueCartaceo,
           paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] trasmissioniRuolo,
           paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] trasmissioniUtente) {
           this.invioOriginaleCartaceo = invioOriginaleCartaceo;
           this.noteGenerali = noteGenerali;
           this.segueCartaceo = segueCartaceo;
           this.trasmissioniRuolo = trasmissioniRuolo;
           this.trasmissioniUtente = trasmissioniUtente;
    }


    /**
     * Gets the invioOriginaleCartaceo value for this Trasmissione.
     * 
     * @return invioOriginaleCartaceo
     */
    public java.lang.Boolean getInvioOriginaleCartaceo() {
        return invioOriginaleCartaceo;
    }


    /**
     * Sets the invioOriginaleCartaceo value for this Trasmissione.
     * 
     * @param invioOriginaleCartaceo
     */
    public void setInvioOriginaleCartaceo(java.lang.Boolean invioOriginaleCartaceo) {
        this.invioOriginaleCartaceo = invioOriginaleCartaceo;
    }


    /**
     * Gets the noteGenerali value for this Trasmissione.
     * 
     * @return noteGenerali
     */
    public java.lang.String getNoteGenerali() {
        return noteGenerali;
    }


    /**
     * Sets the noteGenerali value for this Trasmissione.
     * 
     * @param noteGenerali
     */
    public void setNoteGenerali(java.lang.String noteGenerali) {
        this.noteGenerali = noteGenerali;
    }


    /**
     * Gets the segueCartaceo value for this Trasmissione.
     * 
     * @return segueCartaceo
     */
    public boolean isSegueCartaceo() {
        return segueCartaceo;
    }


    /**
     * Sets the segueCartaceo value for this Trasmissione.
     * 
     * @param segueCartaceo
     */
    public void setSegueCartaceo(boolean segueCartaceo) {
        this.segueCartaceo = segueCartaceo;
    }


    /**
     * Gets the trasmissioniRuolo value for this Trasmissione.
     * 
     * @return trasmissioniRuolo
     */
    public paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] getTrasmissioniRuolo() {
        return trasmissioniRuolo;
    }


    /**
     * Sets the trasmissioniRuolo value for this Trasmissione.
     * 
     * @param trasmissioniRuolo
     */
    public void setTrasmissioniRuolo(paleoGiunta.it.marche.regione.paleo.services.TrasmissioneRuolo[] trasmissioniRuolo) {
        this.trasmissioniRuolo = trasmissioniRuolo;
    }


    /**
     * Gets the trasmissioniUtente value for this Trasmissione.
     * 
     * @return trasmissioniUtente
     */
    public paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] getTrasmissioniUtente() {
        return trasmissioniUtente;
    }


    /**
     * Sets the trasmissioniUtente value for this Trasmissione.
     * 
     * @param trasmissioniUtente
     */
    public void setTrasmissioniUtente(paleoGiunta.it.marche.regione.paleo.services.TrasmissioneUtente[] trasmissioniUtente) {
        this.trasmissioniUtente = trasmissioniUtente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Trasmissione)) return false;
        Trasmissione other = (Trasmissione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.invioOriginaleCartaceo==null && other.getInvioOriginaleCartaceo()==null) || 
             (this.invioOriginaleCartaceo!=null &&
              this.invioOriginaleCartaceo.equals(other.getInvioOriginaleCartaceo()))) &&
            ((this.noteGenerali==null && other.getNoteGenerali()==null) || 
             (this.noteGenerali!=null &&
              this.noteGenerali.equals(other.getNoteGenerali()))) &&
            this.segueCartaceo == other.isSegueCartaceo() &&
            ((this.trasmissioniRuolo==null && other.getTrasmissioniRuolo()==null) || 
             (this.trasmissioniRuolo!=null &&
              java.util.Arrays.equals(this.trasmissioniRuolo, other.getTrasmissioniRuolo()))) &&
            ((this.trasmissioniUtente==null && other.getTrasmissioniUtente()==null) || 
             (this.trasmissioniUtente!=null &&
              java.util.Arrays.equals(this.trasmissioniUtente, other.getTrasmissioniUtente())));
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
        if (getInvioOriginaleCartaceo() != null) {
            _hashCode += getInvioOriginaleCartaceo().hashCode();
        }
        if (getNoteGenerali() != null) {
            _hashCode += getNoteGenerali().hashCode();
        }
        _hashCode += (isSegueCartaceo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getTrasmissioniRuolo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrasmissioniRuolo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrasmissioniRuolo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTrasmissioniUtente() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrasmissioniUtente());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrasmissioniUtente(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Trasmissione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Trasmissione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invioOriginaleCartaceo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "InvioOriginaleCartaceo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteGenerali");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NoteGenerali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segueCartaceo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SegueCartaceo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trasmissioniRuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioniRuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneRuolo"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trasmissioniUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioniUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TrasmissioneUtente"));
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
