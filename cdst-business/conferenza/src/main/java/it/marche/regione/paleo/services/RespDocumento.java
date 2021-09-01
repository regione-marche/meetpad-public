/**
 * RespDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespDocumento  extends it.marche.regione.paleo.services.BEBase  implements java.io.Serializable {
    private java.lang.String[] classificazioni;

    private java.util.Calendar dataDocumento;

    private it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento;

    private int docNumber;

    private java.lang.String oggetto;

    private it.marche.regione.paleo.services.OperatorePaleo proprietario;

    private java.lang.String segnaturaDocumento;

    private org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento;

    public RespDocumento() {
    }

    public RespDocumento(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String[] classificazioni,
           java.util.Calendar dataDocumento,
           it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento,
           int docNumber,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo proprietario,
           java.lang.String segnaturaDocumento,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento) {
        super(
            messaggioRisultato);
        this.classificazioni = classificazioni;
        this.dataDocumento = dataDocumento;
        this.datiProcedimento = datiProcedimento;
        this.docNumber = docNumber;
        this.oggetto = oggetto;
        this.proprietario = proprietario;
        this.segnaturaDocumento = segnaturaDocumento;
        this.tipoDocumento = tipoDocumento;
    }


    /**
     * Gets the classificazioni value for this RespDocumento.
     * 
     * @return classificazioni
     */
    public java.lang.String[] getClassificazioni() {
        return classificazioni;
    }


    /**
     * Sets the classificazioni value for this RespDocumento.
     * 
     * @param classificazioni
     */
    public void setClassificazioni(java.lang.String[] classificazioni) {
        this.classificazioni = classificazioni;
    }


    /**
     * Gets the dataDocumento value for this RespDocumento.
     * 
     * @return dataDocumento
     */
    public java.util.Calendar getDataDocumento() {
        return dataDocumento;
    }


    /**
     * Sets the dataDocumento value for this RespDocumento.
     * 
     * @param dataDocumento
     */
    public void setDataDocumento(java.util.Calendar dataDocumento) {
        this.dataDocumento = dataDocumento;
    }


    /**
     * Gets the datiProcedimento value for this RespDocumento.
     * 
     * @return datiProcedimento
     */
    public it.marche.regione.paleo.services.ProcedimentoInfo getDatiProcedimento() {
        return datiProcedimento;
    }


    /**
     * Sets the datiProcedimento value for this RespDocumento.
     * 
     * @param datiProcedimento
     */
    public void setDatiProcedimento(it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento) {
        this.datiProcedimento = datiProcedimento;
    }


    /**
     * Gets the docNumber value for this RespDocumento.
     * 
     * @return docNumber
     */
    public int getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this RespDocumento.
     * 
     * @param docNumber
     */
    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the oggetto value for this RespDocumento.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this RespDocumento.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the proprietario value for this RespDocumento.
     * 
     * @return proprietario
     */
    public it.marche.regione.paleo.services.OperatorePaleo getProprietario() {
        return proprietario;
    }


    /**
     * Sets the proprietario value for this RespDocumento.
     * 
     * @param proprietario
     */
    public void setProprietario(it.marche.regione.paleo.services.OperatorePaleo proprietario) {
        this.proprietario = proprietario;
    }


    /**
     * Gets the segnaturaDocumento value for this RespDocumento.
     * 
     * @return segnaturaDocumento
     */
    public java.lang.String getSegnaturaDocumento() {
        return segnaturaDocumento;
    }


    /**
     * Sets the segnaturaDocumento value for this RespDocumento.
     * 
     * @param segnaturaDocumento
     */
    public void setSegnaturaDocumento(java.lang.String segnaturaDocumento) {
        this.segnaturaDocumento = segnaturaDocumento;
    }


    /**
     * Gets the tipoDocumento value for this RespDocumento.
     * 
     * @return tipoDocumento
     */
    public org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this RespDocumento.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespDocumento)) return false;
        RespDocumento other = (RespDocumento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.classificazioni==null && other.getClassificazioni()==null) || 
             (this.classificazioni!=null &&
              java.util.Arrays.equals(this.classificazioni, other.getClassificazioni()))) &&
            ((this.dataDocumento==null && other.getDataDocumento()==null) || 
             (this.dataDocumento!=null &&
              this.dataDocumento.equals(other.getDataDocumento()))) &&
            ((this.datiProcedimento==null && other.getDatiProcedimento()==null) || 
             (this.datiProcedimento!=null &&
              this.datiProcedimento.equals(other.getDatiProcedimento()))) &&
            this.docNumber == other.getDocNumber() &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.proprietario==null && other.getProprietario()==null) || 
             (this.proprietario!=null &&
              this.proprietario.equals(other.getProprietario()))) &&
            ((this.segnaturaDocumento==null && other.getSegnaturaDocumento()==null) || 
             (this.segnaturaDocumento!=null &&
              this.segnaturaDocumento.equals(other.getSegnaturaDocumento()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento())));
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
        if (getDataDocumento() != null) {
            _hashCode += getDataDocumento().hashCode();
        }
        if (getDatiProcedimento() != null) {
            _hashCode += getDatiProcedimento().hashCode();
        }
        _hashCode += getDocNumber();
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getProprietario() != null) {
            _hashCode += getProprietario().hashCode();
        }
        if (getSegnaturaDocumento() != null) {
            _hashCode += getSegnaturaDocumento().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classificazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Classificazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DatiProcedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProcedimentoInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proprietario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Proprietario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnaturaDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "SegnaturaDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "TipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/RegioneMarche.Protocollo.Common.Tipi", "TipoDocumento"));
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
