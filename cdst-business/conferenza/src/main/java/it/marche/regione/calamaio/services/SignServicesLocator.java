/**
 * SignServicesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.calamaio.services;

public class SignServicesLocator extends org.apache.axis.client.Service implements it.marche.regione.calamaio.services.SignServices {

    public SignServicesLocator() {
    }


    public SignServicesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SignServicesLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SignServicesPort
    private java.lang.String SignServicesPort_address = "http://calamaio.regionemarche.intra:8080/EjbCalamaio/SignServices/SignServices";

    public java.lang.String getSignServicesPortAddress() {
        return SignServicesPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SignServicesPortWSDDServiceName = "SignServicesPort";

    public java.lang.String getSignServicesPortWSDDServiceName() {
        return SignServicesPortWSDDServiceName;
    }

    public void setSignServicesPortWSDDServiceName(java.lang.String name) {
        SignServicesPortWSDDServiceName = name;
    }

    public it.marche.regione.calamaio.services.SignServicesPortType getSignServicesPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SignServicesPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSignServicesPort(endpoint);
    }

    public it.marche.regione.calamaio.services.SignServicesPortType getSignServicesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.marche.regione.calamaio.services.SignServicesSoapBindingStub _stub = new it.marche.regione.calamaio.services.SignServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getSignServicesPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSignServicesPortEndpointAddress(java.lang.String address) {
        SignServicesPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.marche.regione.calamaio.services.SignServicesPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.marche.regione.calamaio.services.SignServicesSoapBindingStub _stub = new it.marche.regione.calamaio.services.SignServicesSoapBindingStub(new java.net.URL(SignServicesPort_address), this);
                _stub.setPortName(getSignServicesPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SignServicesPort".equals(inputPortName)) {
            return getSignServicesPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://calamaio.gpi.it/services", "SignServices");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://calamaio.gpi.it/services", "SignServicesPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SignServicesPort".equals(portName)) {
            setSignServicesPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
