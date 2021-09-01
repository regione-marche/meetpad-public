/**
 * RespDocumentoExt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.paleo.services;

public class RespDocumentoExt  extends it.marche.regione.paleo.services.RespDocumento  implements java.io.Serializable {
    private it.marche.regione.paleo.services.Allegato[] allegati;

    private it.marche.regione.paleo.services.File documentoPrincipale;

    public RespDocumentoExt() {
    }

    public RespDocumentoExt(
           it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String[] classificazioni,
           java.util.Calendar dataDocumento,
           it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento,
           int docNumber,
           java.lang.String oggetto,
           it.marche.regione.paleo.services.OperatorePaleo proprietario,
           java.lang.String segnaturaDocumento,
           org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento,
           it.marche.regione.paleo.services.Allegato[] allegati,
           it.marche.regione.paleo.services.File documentoPrincipale) {
        super(
            messaggioRisultato,
            classificazioni,
            dataDocumento,
            datiProcedimento,
            docNumber,
            oggetto,
            proprietario,
            segnaturaDocumento,
            tipoDocumento);
        this.allegati = allegati;
        this.documentoPrincipale = documentoPrincipale;
    }


    /**
     * Gets the allegati value for this RespDocumentoExt.
     * 
     * @return allegati
     */
    public it.marche.regione.paleo.services.Allegato[] getAllegati() {
        return allegati;
    }


    /**
     * Sets the allegati value for this RespDocumentoExt.
     * 
     * @param allegati
     */
    public void setAllegati(it.marche.regione.paleo.services.Allegato[] allegati) {
        this.allegati = allegati;
    }


    /**
     * Gets the documentoPrincipale value for this RespDocumentoExt.
     * 
     * @return documentoPrincipale
     */
    public it.marche.regione.paleo.services.File getDocumentoPrincipale() {
        return documentoPrincipale;
    }


    /**
     * Sets the documentoPrincipale value for this RespDocumentoExt.
     * 
     * @param documentoPrincipale
     */
    public void setDocumentoPrincipale(it.marche.regione.paleo.services.File documentoPrincipale) {
        this.documentoPrincipale = documentoPrincipale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespDocumentoExt)) return false;
        RespDocumentoExt other = (RespDocumentoExt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegati==null && other.getAllegati()==null) || 
             (this.allegati!=null &&
              java.util.Arrays.equals(this.allegati, other.getAllegati()))) &&
            ((this.documentoPrincipale==null && other.getDocumentoPrincipale()==null) || 
             (this.documentoPrincipale!=null &&
              this.documentoPrincipale.equals(other.getDocumentoPrincipale())));
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
        if (getAllegati() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegati());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegati(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentoPrincipale() != null) {
            _hashCode += getDocumentoPrincipale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespDocumentoExt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "RespDocumentoExt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegati");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Allegati"));
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
