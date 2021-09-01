/**
 * BEBaseOfUpdateRespX2ZTrCF1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.CommonLibrary;

public class BEBaseOfUpdateRespX2ZTrCF1  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateResp result;

    private org.datacontract.schemas._2004._07.CommonLibrary.ResultInfo resultInfo;

    public BEBaseOfUpdateRespX2ZTrCF1() {
    }

    public BEBaseOfUpdateRespX2ZTrCF1(
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateResp result,
           org.datacontract.schemas._2004._07.CommonLibrary.ResultInfo resultInfo) {
           this.result = result;
           this.resultInfo = resultInfo;
    }


    /**
     * Gets the result value for this BEBaseOfUpdateRespX2ZTrCF1.
     * 
     * @return result
     */
    public org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateResp getResult() {
        return result;
    }


    /**
     * Sets the result value for this BEBaseOfUpdateRespX2ZTrCF1.
     * 
     * @param result
     */
    public void setResult(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.UpdateResp result) {
        this.result = result;
    }


    /**
     * Gets the resultInfo value for this BEBaseOfUpdateRespX2ZTrCF1.
     * 
     * @return resultInfo
     */
    public org.datacontract.schemas._2004._07.CommonLibrary.ResultInfo getResultInfo() {
        return resultInfo;
    }


    /**
     * Sets the resultInfo value for this BEBaseOfUpdateRespX2ZTrCF1.
     * 
     * @param resultInfo
     */
    public void setResultInfo(org.datacontract.schemas._2004._07.CommonLibrary.ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BEBaseOfUpdateRespX2ZTrCF1)) return false;
        BEBaseOfUpdateRespX2ZTrCF1 other = (BEBaseOfUpdateRespX2ZTrCF1) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.resultInfo==null && other.getResultInfo()==null) || 
             (this.resultInfo!=null &&
              this.resultInfo.equals(other.getResultInfo())));
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
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getResultInfo() != null) {
            _hashCode += getResultInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BEBaseOfUpdateRespX2ZTrCF1.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "BEBaseOfUpdateRespX2zTrCF1"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "UpdateResp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "ResultInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/CommonLibrary", "ResultInfo"));
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
