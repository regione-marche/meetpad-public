/**
 * SendDocumentiMISReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class SendDocumentiMISReq  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File[] file;

    private java.lang.String idMIS;

    private java.lang.Integer idRichiesta;

    private java.lang.String password;

    private java.lang.String tipoDocumentoInvio;

    private java.lang.String userID;

    public SendDocumentiMISReq() {
    }

    public SendDocumentiMISReq(
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File[] file,
           java.lang.String idMIS,
           java.lang.Integer idRichiesta,
           java.lang.String password,
           java.lang.String tipoDocumentoInvio,
           java.lang.String userID) {
           this.file = file;
           this.idMIS = idMIS;
           this.idRichiesta = idRichiesta;
           this.password = password;
           this.tipoDocumentoInvio = tipoDocumentoInvio;
           this.userID = userID;
    }


    /**
     * Gets the file value for this SendDocumentiMISReq.
     * 
     * @return file
     */
    public org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File[] getFile() {
        return file;
    }


    /**
     * Sets the file value for this SendDocumentiMISReq.
     * 
     * @param file
     */
    public void setFile(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.File[] file) {
        this.file = file;
    }


    /**
     * Gets the idMIS value for this SendDocumentiMISReq.
     * 
     * @return idMIS
     */
    public java.lang.String getIdMIS() {
        return idMIS;
    }


    /**
     * Sets the idMIS value for this SendDocumentiMISReq.
     * 
     * @param idMIS
     */
    public void setIdMIS(java.lang.String idMIS) {
        this.idMIS = idMIS;
    }


    /**
     * Gets the idRichiesta value for this SendDocumentiMISReq.
     * 
     * @return idRichiesta
     */
    public java.lang.Integer getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this SendDocumentiMISReq.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(java.lang.Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the password value for this SendDocumentiMISReq.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this SendDocumentiMISReq.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the tipoDocumentoInvio value for this SendDocumentiMISReq.
     * 
     * @return tipoDocumentoInvio
     */
    public java.lang.String getTipoDocumentoInvio() {
        return tipoDocumentoInvio;
    }


    /**
     * Sets the tipoDocumentoInvio value for this SendDocumentiMISReq.
     * 
     * @param tipoDocumentoInvio
     */
    public void setTipoDocumentoInvio(java.lang.String tipoDocumentoInvio) {
        this.tipoDocumentoInvio = tipoDocumentoInvio;
    }


    /**
     * Gets the userID value for this SendDocumentiMISReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this SendDocumentiMISReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendDocumentiMISReq)) return false;
        SendDocumentiMISReq other = (SendDocumentiMISReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.file==null && other.getFile()==null) || 
             (this.file!=null &&
              java.util.Arrays.equals(this.file, other.getFile()))) &&
            ((this.idMIS==null && other.getIdMIS()==null) || 
             (this.idMIS!=null &&
              this.idMIS.equals(other.getIdMIS()))) &&
            ((this.idRichiesta==null && other.getIdRichiesta()==null) || 
             (this.idRichiesta!=null &&
              this.idRichiesta.equals(other.getIdRichiesta()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.tipoDocumentoInvio==null && other.getTipoDocumentoInvio()==null) || 
             (this.tipoDocumentoInvio!=null &&
              this.tipoDocumentoInvio.equals(other.getTipoDocumentoInvio()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID())));
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
        if (getFile() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFile());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFile(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdMIS() != null) {
            _hashCode += getIdMIS().hashCode();
        }
        if (getIdRichiesta() != null) {
            _hashCode += getIdRichiesta().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getTipoDocumentoInvio() != null) {
            _hashCode += getTipoDocumentoInvio().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SendDocumentiMISReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "SendDocumentiMISReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("file");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "File"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMIS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdMIS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumentoInvio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "TipoDocumentoInvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UserID"));
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
