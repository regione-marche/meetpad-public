/**
 * RespProtocolloArrivoExt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class RespProtocolloArrivoExt  extends paleoGiunta.it.marche.regione.paleo.services.RespProtocolloExt  implements java.io.Serializable {
    private java.util.Calendar dataArrivo;

    private java.lang.String mittente;

    private java.lang.String protocolloMittente;

    public RespProtocolloArrivoExt() {
    }

    public RespProtocolloArrivoExt(
           paleoGiunta.it.marche.regione.paleo.services.MessaggioRisultato messaggioRisultato,
           java.lang.String[] classificazioni,
           java.util.Calendar dataDocumento,
           paleoGiunta.it.marche.regione.paleo.services.ProcedimentoInfo datiProcedimento,
           int docNumber,
           java.lang.String oggetto,
           paleoGiunta.it.marche.regione.paleo.services.OperatorePaleo proprietario,
           java.lang.String segnaturaDocumento,
           paleoGiunta.org.datacontract.schemas._2004._07.RegioneMarche_Protocollo_Common_Tipi.TipoDocumento tipoDocumento,
           paleoGiunta.it.marche.regione.paleo.services.Allegato[] allegati,
           paleoGiunta.it.marche.regione.paleo.services.File documentoPrincipale,
           java.lang.Boolean annullato,
           java.util.Calendar data,
           java.util.Calendar dataProtocollazione,
           java.lang.String numero,
           java.lang.String registro,
           java.lang.String segnatura,
           java.util.Calendar dataArrivo,
           java.lang.String mittente,
           java.lang.String protocolloMittente) {
        super(
            messaggioRisultato,
            classificazioni,
            dataDocumento,
            datiProcedimento,
            docNumber,
            oggetto,
            proprietario,
            segnaturaDocumento,
            tipoDocumento,
            allegati,
            documentoPrincipale,
            annullato,
            data,
            dataProtocollazione,
            numero,
            registro,
            segnatura);
        this.dataArrivo = dataArrivo;
        this.mittente = mittente;
        this.protocolloMittente = protocolloMittente;
    }


    /**
     * Gets the dataArrivo value for this RespProtocolloArrivoExt.
     * 
     * @return dataArrivo
     */
    public java.util.Calendar getDataArrivo() {
        return dataArrivo;
    }


    /**
     * Sets the dataArrivo value for this RespProtocolloArrivoExt.
     * 
     * @param dataArrivo
     */
    public void setDataArrivo(java.util.Calendar dataArrivo) {
        this.dataArrivo = dataArrivo;
    }


    /**
     * Gets the mittente value for this RespProtocolloArrivoExt.
     * 
     * @return mittente
     */
    public java.lang.String getMittente() {
        return mittente;
    }


    /**
     * Sets the mittente value for this RespProtocolloArrivoExt.
     * 
     * @param mittente
     */
    public void setMittente(java.lang.String mittente) {
        this.mittente = mittente;
    }


    /**
     * Gets the protocolloMittente value for this RespProtocolloArrivoExt.
     * 
     * @return protocolloMittente
     */
    public java.lang.String getProtocolloMittente() {
        return protocolloMittente;
    }


    /**
     * Sets the protocolloMittente value for this RespProtocolloArrivoExt.
     * 
     * @param protocolloMittente
     */
    public void setProtocolloMittente(java.lang.String protocolloMittente) {
        this.protocolloMittente = protocolloMittente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RespProtocolloArrivoExt)) return false;
        RespProtocolloArrivoExt other = (RespProtocolloArrivoExt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataArrivo==null && other.getDataArrivo()==null) || 
             (this.dataArrivo!=null &&
              this.dataArrivo.equals(other.getDataArrivo()))) &&
            ((this.mittente==null && other.getMittente()==null) || 
             (this.mittente!=null &&
              this.mittente.equals(other.getMittente()))) &&
            ((this.protocolloMittente==null && other.getProtocolloMittente()==null) || 
             (this.protocolloMittente!=null &&
              this.protocolloMittente.equals(other.getProtocolloMittente())));
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
        if (getDataArrivo() != null) {
            _hashCode += getDataArrivo().hashCode();
        }
        if (getMittente() != null) {
            _hashCode += getMittente().hashCode();
        }
        if (getProtocolloMittente() != null) {
            _hashCode += getProtocolloMittente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RespProtocolloArrivoExt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "respProtocolloArrivoExt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataArrivo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DataArrivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mittente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Mittente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolloMittente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "ProtocolloMittente"));
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
