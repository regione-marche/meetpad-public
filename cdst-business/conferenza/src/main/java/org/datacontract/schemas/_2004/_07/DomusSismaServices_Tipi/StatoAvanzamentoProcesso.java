/**
 * StatoAvanzamentoProcesso.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class StatoAvanzamentoProcesso  implements java.io.Serializable {
    private java.lang.Integer idRichiesta;

    private org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso[] listaProssimiTask;

    public StatoAvanzamentoProcesso() {
    }

    public StatoAvanzamentoProcesso(
           java.lang.Integer idRichiesta,
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso[] listaProssimiTask) {
           this.idRichiesta = idRichiesta;
           this.listaProssimiTask = listaProssimiTask;
    }


    /**
     * Gets the idRichiesta value for this StatoAvanzamentoProcesso.
     * 
     * @return idRichiesta
     */
    public java.lang.Integer getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this StatoAvanzamentoProcesso.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(java.lang.Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the listaProssimiTask value for this StatoAvanzamentoProcesso.
     * 
     * @return listaProssimiTask
     */
    public org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso[] getListaProssimiTask() {
        return listaProssimiTask;
    }


    /**
     * Sets the listaProssimiTask value for this StatoAvanzamentoProcesso.
     * 
     * @param listaProssimiTask
     */
    public void setListaProssimiTask(org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.StatoProcesso[] listaProssimiTask) {
        this.listaProssimiTask = listaProssimiTask;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatoAvanzamentoProcesso)) return false;
        StatoAvanzamentoProcesso other = (StatoAvanzamentoProcesso) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idRichiesta==null && other.getIdRichiesta()==null) || 
             (this.idRichiesta!=null &&
              this.idRichiesta.equals(other.getIdRichiesta()))) &&
            ((this.listaProssimiTask==null && other.getListaProssimiTask()==null) || 
             (this.listaProssimiTask!=null &&
              java.util.Arrays.equals(this.listaProssimiTask, other.getListaProssimiTask())));
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
        if (getIdRichiesta() != null) {
            _hashCode += getIdRichiesta().hashCode();
        }
        if (getListaProssimiTask() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaProssimiTask());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaProssimiTask(), i);
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
        new org.apache.axis.description.TypeDesc(StatoAvanzamentoProcesso.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoAvanzamentoProcesso"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IdRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaProssimiTask");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ListaProssimiTask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoProcesso"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "StatoProcesso"));
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
