/**
 * ReqAddVersione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class ReqAddVersione  implements java.io.Serializable {
    private java.lang.Integer docNumber;

    private it.marche.regione.paleo.services.File documentoPrincipale;

    private java.lang.String noteVersione;

    private it.marche.regione.paleo.services.OperatorePaleo operatore;

    public ReqAddVersione() {
    }

    public ReqAddVersione(
           java.lang.Integer docNumber,
           it.marche.regione.paleo.services.File documentoPrincipale,
           java.lang.String noteVersione,
           it.marche.regione.paleo.services.OperatorePaleo operatore) {
           this.docNumber = docNumber;
           this.documentoPrincipale = documentoPrincipale;
           this.noteVersione = noteVersione;
           this.operatore = operatore;
    }


    /**
     * Gets the docNumber value for this ReqAddVersione.
     * 
     * @return docNumber
     */
    public java.lang.Integer getDocNumber() {
        return docNumber;
    }


    /**
     * Sets the docNumber value for this ReqAddVersione.
     * 
     * @param docNumber
     */
    public void setDocNumber(java.lang.Integer docNumber) {
        this.docNumber = docNumber;
    }


    /**
     * Gets the documentoPrincipale value for this ReqAddVersione.
     * 
     * @return documentoPrincipale
     */
    public it.marche.regione.paleo.services.File getDocumentoPrincipale() {
        return documentoPrincipale;
    }


    /**
     * Sets the documentoPrincipale value for this ReqAddVersione.
     * 
     * @param documentoPrincipale
     */
    public void setDocumentoPrincipale(it.marche.regione.paleo.services.File documentoPrincipale) {
        this.documentoPrincipale = documentoPrincipale;
    }


    /**
     * Gets the noteVersione value for this ReqAddVersione.
     * 
     * @return noteVersione
     */
    public java.lang.String getNoteVersione() {
        return noteVersione;
    }


    /**
     * Sets the noteVersione value for this ReqAddVersione.
     * 
     * @param noteVersione
     */
    public void setNoteVersione(java.lang.String noteVersione) {
        this.noteVersione = noteVersione;
    }


    /**
     * Gets the operatore value for this ReqAddVersione.
     * 
     * @return operatore
     */
    public it.marche.regione.paleo.services.OperatorePaleo getOperatore() {
        return operatore;
    }


    /**
     * Sets the operatore value for this ReqAddVersione.
     * 
     * @param operatore
     */
    public void setOperatore(it.marche.regione.paleo.services.OperatorePaleo operatore) {
        this.operatore = operatore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReqAddVersione)) return false;
        ReqAddVersione other = (ReqAddVersione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docNumber==null && other.getDocNumber()==null) || 
             (this.docNumber!=null &&
              this.docNumber.equals(other.getDocNumber()))) &&
            ((this.documentoPrincipale==null && other.getDocumentoPrincipale()==null) || 
             (this.documentoPrincipale!=null &&
              this.documentoPrincipale.equals(other.getDocumentoPrincipale()))) &&
            ((this.noteVersione==null && other.getNoteVersione()==null) || 
             (this.noteVersione!=null &&
              this.noteVersione.equals(other.getNoteVersione()))) &&
            ((this.operatore==null && other.getOperatore()==null) || 
             (this.operatore!=null &&
              this.operatore.equals(other.getOperatore())));
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
        if (getDocNumber() != null) {
            _hashCode += getDocNumber().hashCode();
        }
        if (getDocumentoPrincipale() != null) {
            _hashCode += getDocumentoPrincipale().hashCode();
        }
        if (getNoteVersione() != null) {
            _hashCode += getNoteVersione().hashCode();
        }
        if (getOperatore() != null) {
            _hashCode += getOperatore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReqAddVersione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "reqAddVersione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentoPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "DocumentoPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "File"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteVersione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "NoteVersione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "Operatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "OperatorePaleo"));
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
