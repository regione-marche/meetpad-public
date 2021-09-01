/**
 * ReqCercaProtocolliCorrispondente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class ReqCercaProtocolliCorrispondente  implements java.io.Serializable {
    private java.lang.String idFiscaleCorrispondente;

    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    private java.lang.String tipoDocumento;

    private java.lang.String tipoProcedimento;

    public ReqCercaProtocolliCorrispondente() {
    }

    public ReqCercaProtocolliCorrispondente(
           java.lang.String idFiscaleCorrispondente,
           it.marche.regione.paleo.services.OperatorePaleo operatore,
           java.lang.String tipoDocumento,
           java.lang.String tipoProcedimento) {
           this.idFiscaleCorrispondente = idFiscaleCorrispondente;
           this.operatore = operatore;
           this.tipoDocumento = tipoDocumento;
           this.tipoProcedimento = tipoProcedimento;
    }


    /**
     * Gets the idFiscaleCorrispondente value for this ReqCercaProtocolliCorrispondente.
     * 
     * @return idFiscaleCorrispondente
     */
    public java.lang.String getIdFiscaleCorrispondente() {
        return idFiscaleCorrispondente;
    }


    /**
     * Sets the idFiscaleCorrispondente value for this ReqCercaProtocolliCorrispondente.
     * 
     * @param idFiscaleCorrispondente
     */
    public void setIdFiscaleCorrispondente(java.lang.String idFiscaleCorrispondente) {
        this.idFiscaleCorrispondente = idFiscaleCorrispondente;
    }


    /**
     * Gets the operatore value for this ReqCercaProtocolliCorrispondente.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqCercaProtocolliCorrispondente.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }


    /**
     * Gets the tipoDocumento value for this ReqCercaProtocolliCorrispondente.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this ReqCercaProtocolliCorrispondente.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the tipoProcedimento value for this ReqCercaProtocolliCorrispondente.
     * 
     * @return tipoProcedimento
     */
    public java.lang.String getTipoProcedimento() {
        return tipoProcedimento;
    }


    /**
     * Sets the tipoProcedimento value for this ReqCercaProtocolliCorrispondente.
     * 
     * @param tipoProcedimento
     */
    public void setTipoProcedimento(java.lang.String tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqCercaProtocolliCorrispondente)) return false;
        ReqCercaProtocolliCorrispondente other = (ReqCercaProtocolliCorrispondente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idFiscaleCorrispondente==null && other.getIdFiscaleCorrispondente()==null) || 
             (this.idFiscaleCorrispondente!=null &&
              this.idFiscaleCorrispondente.equals(other.getIdFiscaleCorrispondente()))) &&
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.tipoProcedimento==null && other.getTipoProcedimento()==null) || 
             (this.tipoProcedimento!=null &&
              this.tipoProcedimento.equals(other.getTipoProcedimento())));
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
        if (getIdFiscaleCorrispondente() != null) {
            _hashCode += getIdFiscaleCorrispondente().hashCode();
        }
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getTipoProcedimento() != null) {
            _hashCode += getTipoProcedimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqCercaProtocolliCorrispondente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqCercaProtocolliCorrispondente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFiscaleCorrispondente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "IdFiscaleCorrispondente"));
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
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoProcedimento"));
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
