/**
 * GetFileReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class GetFileReq  extends org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.BaseReq  implements java.io.Serializable {
    private java.lang.Integer idFile;

    private java.lang.Boolean decriptaP7M;

    public GetFileReq() {
    }

    public GetFileReq(
           java.lang.String password,
           java.lang.String userID,
           java.lang.Integer idFile,
           java.lang.Boolean decriptaP7M) {
        super(
            password,
            userID);
        this.idFile = idFile;
        this.decriptaP7M = decriptaP7M;
    }


    /**
     * Gets the idFile value for this GetFileReq.
     * 
     * @return idFile
     */
    public java.lang.Integer getIdFile() {
        return idFile;
    }


    /**
     * Sets the idFile value for this GetFileReq.
     * 
     * @param idFile
     */
    public void setIdFile(java.lang.Integer idFile) {
        this.idFile = idFile;
    }


    /**
     * Gets the decriptaP7M value for this GetFileReq.
     * 
     * @return decriptaP7M
     */
    public java.lang.Boolean getDecriptaP7M() {
        return decriptaP7M;
    }


    /**
     * Sets the decriptaP7M value for this GetFileReq.
     * 
     * @param decriptaP7M
     */
    public void setDecriptaP7M(java.lang.Boolean decriptaP7M) {
        this.decriptaP7M = decriptaP7M;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetFileReq)) return false;
        GetFileReq other = (GetFileReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idFile==null && other.getIdFile()==null) || 
             (this.idFile!=null &&
              this.idFile.equals(other.getIdFile()))) &&
            ((this.decriptaP7M==null && other.getDecriptaP7M()==null) || 
             (this.decriptaP7M!=null &&
              this.decriptaP7M.equals(other.getDecriptaP7M())));
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
        if (getIdFile() != null) {
            _hashCode += getIdFile().hashCode();
        }
        if (getDecriptaP7M() != null) {
            _hashCode += getDecriptaP7M().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetFileReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "GetFileReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("decriptaP7M");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "decriptaP7M"));
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
