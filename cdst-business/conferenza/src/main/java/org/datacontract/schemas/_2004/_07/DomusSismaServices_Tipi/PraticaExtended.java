/**
 * PraticaExtended.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.DomusSismaServices_Tipi;

public class PraticaExtended  extends org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica  implements java.io.Serializable {
    private java.lang.String codiceProvincia;

    private java.lang.String comuneIntervento;

    private java.lang.String indirizzoIntervento;

    private java.lang.String intestatario;

    private java.lang.String istatComune;

    private java.lang.String ordinanza;

    private java.lang.String richiedente;
    private java.lang.String codiceFiscaleRichiedente;
    private java.lang.String pecRichiedente;

    public PraticaExtended() {
    }

    public PraticaExtended(
           java.lang.String codiceFascicolo,
           java.lang.String destinazioneUso,
           java.lang.Integer idRichiesta,
           org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.ProtocolloPratica[] protocolli,
           java.lang.String stato,
           java.lang.String codiceProvincia,
           java.lang.String comuneIntervento,
           java.lang.String indirizzoIntervento,
           java.lang.String intestatario,
           java.lang.String istatComune,
           java.lang.String ordinanza,
           java.lang.String richiedente,
           java.lang.String codiceFiscaleRichiedente,
           java.lang.String pecRichiedente) {
        super(
            codiceFascicolo,
            destinazioneUso,
            idRichiesta,
            protocolli,
            stato);
        this.codiceProvincia = codiceProvincia;
        this.comuneIntervento = comuneIntervento;
        this.indirizzoIntervento = indirizzoIntervento;
        this.intestatario = intestatario;
        this.istatComune = istatComune;
        this.ordinanza = ordinanza;
        this.richiedente = richiedente;
        this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
        this.pecRichiedente = pecRichiedente;
    }

    
    

    public java.lang.String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(java.lang.String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public java.lang.String getPecRichiedente() {
		return pecRichiedente;
	}

	public void setPecRichiedente(java.lang.String pecRichiedente) {
		this.pecRichiedente = pecRichiedente;
	}

	/**
     * Gets the codiceProvincia value for this PraticaExtended.
     * 
     * @return codiceProvincia
     */
    public java.lang.String getCodiceProvincia() {
        return codiceProvincia;
    }


    /**
     * Sets the codiceProvincia value for this PraticaExtended.
     * 
     * @param codiceProvincia
     */
    public void setCodiceProvincia(java.lang.String codiceProvincia) {
        this.codiceProvincia = codiceProvincia;
    }


    /**
     * Gets the comuneIntervento value for this PraticaExtended.
     * 
     * @return comuneIntervento
     */
    public java.lang.String getComuneIntervento() {
        return comuneIntervento;
    }


    /**
     * Sets the comuneIntervento value for this PraticaExtended.
     * 
     * @param comuneIntervento
     */
    public void setComuneIntervento(java.lang.String comuneIntervento) {
        this.comuneIntervento = comuneIntervento;
    }


    /**
     * Gets the indirizzoIntervento value for this PraticaExtended.
     * 
     * @return indirizzoIntervento
     */
    public java.lang.String getIndirizzoIntervento() {
        return indirizzoIntervento;
    }


    /**
     * Sets the indirizzoIntervento value for this PraticaExtended.
     * 
     * @param indirizzoIntervento
     */
    public void setIndirizzoIntervento(java.lang.String indirizzoIntervento) {
        this.indirizzoIntervento = indirizzoIntervento;
    }


    /**
     * Gets the intestatario value for this PraticaExtended.
     * 
     * @return intestatario
     */
    public java.lang.String getIntestatario() {
        return intestatario;
    }


    /**
     * Sets the intestatario value for this PraticaExtended.
     * 
     * @param intestatario
     */
    public void setIntestatario(java.lang.String intestatario) {
        this.intestatario = intestatario;
    }


    /**
     * Gets the istatComune value for this PraticaExtended.
     * 
     * @return istatComune
     */
    public java.lang.String getIstatComune() {
        return istatComune;
    }


    /**
     * Sets the istatComune value for this PraticaExtended.
     * 
     * @param istatComune
     */
    public void setIstatComune(java.lang.String istatComune) {
        this.istatComune = istatComune;
    }


    /**
     * Gets the ordinanza value for this PraticaExtended.
     * 
     * @return ordinanza
     */
    public java.lang.String getOrdinanza() {
        return ordinanza;
    }


    /**
     * Sets the ordinanza value for this PraticaExtended.
     * 
     * @param ordinanza
     */
    public void setOrdinanza(java.lang.String ordinanza) {
        this.ordinanza = ordinanza;
    }


    /**
     * Gets the richiedente value for this PraticaExtended.
     * 
     * @return richiedente
     */
    public java.lang.String getRichiedente() {
        return richiedente;
    }


    /**
     * Sets the richiedente value for this PraticaExtended.
     * 
     * @param richiedente
     */
    public void setRichiedente(java.lang.String richiedente) {
        this.richiedente = richiedente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PraticaExtended)) return false;
        PraticaExtended other = (PraticaExtended) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceProvincia==null && other.getCodiceProvincia()==null) || 
             (this.codiceProvincia!=null &&
              this.codiceProvincia.equals(other.getCodiceProvincia()))) &&
            ((this.comuneIntervento==null && other.getComuneIntervento()==null) || 
             (this.comuneIntervento!=null &&
              this.comuneIntervento.equals(other.getComuneIntervento()))) &&
            ((this.indirizzoIntervento==null && other.getIndirizzoIntervento()==null) || 
             (this.indirizzoIntervento!=null &&
              this.indirizzoIntervento.equals(other.getIndirizzoIntervento()))) &&
            ((this.intestatario==null && other.getIntestatario()==null) || 
             (this.intestatario!=null &&
              this.intestatario.equals(other.getIntestatario()))) &&
            ((this.istatComune==null && other.getIstatComune()==null) || 
             (this.istatComune!=null &&
              this.istatComune.equals(other.getIstatComune()))) &&
            ((this.ordinanza==null && other.getOrdinanza()==null) || 
             (this.ordinanza!=null &&
              this.ordinanza.equals(other.getOrdinanza()))) &&
            ((this.richiedente==null && other.getRichiedente()==null) || 
             (this.richiedente!=null &&
              this.richiedente.equals(other.getRichiedente()))) &&
            ((this.codiceFiscaleRichiedente==null && other.getCodiceFiscaleRichiedente()==null) || 
                    (this.codiceFiscaleRichiedente!=null &&
                     this.codiceFiscaleRichiedente.equals(other.getCodiceFiscaleRichiedente()))) &&
            ((this.pecRichiedente==null && other.getPecRichiedente()==null) || 
                    (this.pecRichiedente!=null &&
                     this.pecRichiedente.equals(other.getPecRichiedente())));
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
        if (getCodiceProvincia() != null) {
            _hashCode += getCodiceProvincia().hashCode();
        }
        if (getComuneIntervento() != null) {
            _hashCode += getComuneIntervento().hashCode();
        }
        if (getIndirizzoIntervento() != null) {
            _hashCode += getIndirizzoIntervento().hashCode();
        }
        if (getIntestatario() != null) {
            _hashCode += getIntestatario().hashCode();
        }
        if (getIstatComune() != null) {
            _hashCode += getIstatComune().hashCode();
        }
        if (getOrdinanza() != null) {
            _hashCode += getOrdinanza().hashCode();
        }
        if (getRichiedente() != null) {
            _hashCode += getRichiedente().hashCode();
        }
        if (getCodiceFiscaleRichiedente() != null) {
            _hashCode += getCodiceFiscaleRichiedente().hashCode();
        }
        if (getPecRichiedente() != null) {
            _hashCode += getPecRichiedente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PraticaExtended.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PraticaExtended"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "CodiceProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuneIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "ComuneIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IndirizzoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intestatario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Intestatario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istatComune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "IstatComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordinanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Ordinanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("richiedente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "Richiedente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscaleRichiedente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "CodiceFiscaleRichiedente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pecRichiedente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/DomusSismaServices.Tipi", "PecRichiedente"));
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
