/**
 * DomusSismaMISServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class DomusSismaMISServiceLocator extends org.apache.axis.client.Service implements org.tempuri.DomusSismaMISService {

    public DomusSismaMISServiceLocator() {
    }


    public DomusSismaMISServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DomusSismaMISServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IDomusSismaMISService
    private java.lang.String BasicHttpBinding_IDomusSismaMISService_address = "http://arturo3.regionemarche.intra/DomusSismaServices/DomusSismaMISService.svc";

    public java.lang.String getBasicHttpBinding_IDomusSismaMISServiceAddress() {
        return BasicHttpBinding_IDomusSismaMISService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IDomusSismaMISServiceWSDDServiceName = "BasicHttpBinding_IDomusSismaMISService";

    public java.lang.String getBasicHttpBinding_IDomusSismaMISServiceWSDDServiceName() {
        return BasicHttpBinding_IDomusSismaMISServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IDomusSismaMISServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IDomusSismaMISServiceWSDDServiceName = name;
    }

    public it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService getBasicHttpBinding_IDomusSismaMISService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IDomusSismaMISService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IDomusSismaMISService(endpoint);
    }

    public it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService getBasicHttpBinding_IDomusSismaMISService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IDomusSismaMISServiceStub _stub = new org.tempuri.BasicHttpBinding_IDomusSismaMISServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IDomusSismaMISServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IDomusSismaMISServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IDomusSismaMISService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.marche.regione.DomusSismaServices.DomusSismaMIS.IDomusSismaMISService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IDomusSismaMISServiceStub _stub = new org.tempuri.BasicHttpBinding_IDomusSismaMISServiceStub(new java.net.URL(BasicHttpBinding_IDomusSismaMISService_address), this);
                _stub.setPortName(getBasicHttpBinding_IDomusSismaMISServiceWSDDServiceName());
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
        if ("BasicHttpBinding_IDomusSismaMISService".equals(inputPortName)) {
            return getBasicHttpBinding_IDomusSismaMISService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DomusSismaMISService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IDomusSismaMISService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IDomusSismaMISService".equals(portName)) {
            setBasicHttpBinding_IDomusSismaMISServiceEndpointAddress(address);
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
