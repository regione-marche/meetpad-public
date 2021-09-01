/**
 * ReqDocProtocolliInFascicolo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class ReqDocProtocolliInFascicolo  implements java.io.Serializable {
    private java.lang.String codiceFascicolo;

    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    private java.lang.Boolean soloPubblici;

    private java.lang.Boolean soloVerificaFascicolo;

    public ReqDocProtocolliInFascicolo() {
    }

    public ReqDocProtocolliInFascicolo(
           java.lang.String codiceFascicolo,
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           java.lang.Boolean soloPubblici,
           java.lang.Boolean soloVerificaFascicolo) {
           this.codiceFascicolo = codiceFascicolo;
           this.operatore = operatore;
           this.soloPubblici = soloPubblici;
           this.soloVerificaFascicolo = soloVerificaFascicolo;
    }


    /**
     * Gets the codiceFascicolo value for this ReqDocProtocolliInFascicolo.
     * 
     * @return codiceFascicolo
     */
    public java.lang.String getCodiceFascicolo() {
        return codiceFascicolo;
    }


    /**
     * Sets the codiceFascicolo value for this ReqDocProtocolliInFascicolo.
     * 
     * @param codiceFascicolo
     */
    public void setCodiceFascicolo(java.lang.String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }


    /**
     * Gets the operatore value for this ReqDocProtocolliInFascicolo.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqDocProtocolliInFascicolo.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }


    /**
     * Gets the soloPubblici value for this ReqDocProtocolliInFascicolo.
     * 
     * @return soloPubblici
     */
    public java.lang.Boolean getSoloPubblici() {
        return soloPubblici;
    }


    /**
     * Sets the soloPubblici value for this ReqDocProtocolliInFascicolo.
     * 
     * @param soloPubblici
     */
    public void setSoloPubblici(java.lang.Boolean soloPubblici) {
        this.soloPubblici = soloPubblici;
    }


    /**
     * Gets the soloVerificaFascicolo value for this ReqDocProtocolliInFascicolo.
     * 
     * @return soloVerificaFascicolo
     */
    public java.lang.Boolean getSoloVerificaFascicolo() {
        return soloVerificaFascicolo;
    }


    /**
     * Sets the soloVerificaFascicolo value for this ReqDocProtocolliInFascicolo.
     * 
     * @param soloVerificaFascicolo
     */
    public void setSoloVerificaFascicolo(java.lang.Boolean soloVerificaFascicolo) {
        this.soloVerificaFascicolo = soloVerificaFascicolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqDocProtocolliInFascicolo)) return false;
        ReqDocProtocolliInFascicolo other = (ReqDocProtocolliInFascicolo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFascicolo==null && other.getCodiceFascicolo()==null) || 
             (this.codiceFascicolo!=null &&
              this.codiceFascicolo.equals(other.getCodiceFascicolo()))) &&
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore()))) &&
            ((this.soloPubblici==null && other.getSoloPubblici()==null) || 
             (this.soloPubblici!=null &&
              this.soloPubblici.equals(other.getSoloPubblici()))) &&
            ((this.soloVerificaFascicolo==null && other.getSoloVerificaFascicolo()==null) || 
             (this.soloVerificaFascicolo!=null &&
              this.soloVerificaFascicolo.equals(other.getSoloVerificaFascicolo())));
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
        if (getCodiceFascicolo() != null) {
            _hashCode += getCodiceFascicolo().hashCode();
        }
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        if (getSoloPubblici() != null) {
            _hashCode += getSoloPubblici().hashCode();
        }
        if (getSoloVerificaFascicolo() != null) {
            _hashCode += getSoloVerificaFascicolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqDocProtocolliInFascicolo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqDocProtocolliInFascicolo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "CodiceFascicolo"));
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
        elemField.setFieldName("soloPubblici");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SoloPubblici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soloVerificaFascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SoloVerificaFascicolo"));
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
