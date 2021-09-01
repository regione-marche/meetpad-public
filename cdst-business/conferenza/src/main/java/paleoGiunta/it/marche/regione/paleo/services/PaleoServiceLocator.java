/**
 * PaleoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package paleoGiunta.it.marche.regione.paleo.services;

public class PaleoServiceLocator extends org.apache.axis.client.Service implements paleoGiunta.it.marche.regione.paleo.services.PaleoService {

    public PaleoServiceLocator() {
    }


    public PaleoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PaleoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IPaleoService
    private java.lang.String BasicHttpBinding_IPaleoService_address = "https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc";

    public java.lang.String getBasicHttpBinding_IPaleoServiceAddress() {
        return BasicHttpBinding_IPaleoService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IPaleoServiceWSDDServiceName = "BasicHttpBinding_IPaleoService";

    public java.lang.String getBasicHttpBinding_IPaleoServiceWSDDServiceName() {
        return BasicHttpBinding_IPaleoServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IPaleoServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IPaleoServiceWSDDServiceName = name;
    }

    public paleoGiunta.it.marche.regione.paleo.services.IPaleoService getBasicHttpBinding_IPaleoService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IPaleoService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IPaleoService(endpoint);
    }

    public paleoGiunta.it.marche.regione.paleo.services.IPaleoService getBasicHttpBinding_IPaleoService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            paleoGiunta.it.marche.regione.paleo.services.BasicHttpBinding_IPaleoServiceStub _stub = new paleoGiunta.it.marche.regione.paleo.services.BasicHttpBinding_IPaleoServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IPaleoServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IPaleoServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IPaleoService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (paleoGiunta.it.marche.regione.paleo.services.IPaleoService.class.isAssignableFrom(serviceEndpointInterface)) {
                paleoGiunta.it.marche.regione.paleo.services.BasicHttpBinding_IPaleoServiceStub _stub = new paleoGiunta.it.marche.regione.paleo.services.BasicHttpBinding_IPaleoServiceStub(new java.net.URL(BasicHttpBinding_IPaleoService_address), this);
                _stub.setPortName(getBasicHttpBinding_IPaleoServiceWSDDServiceName());
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
        if ("BasicHttpBinding_IPaleoService".equals(inputPortName)) {
            return getBasicHttpBinding_IPaleoService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "PaleoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://paleo.regione.marche.it/services/", "BasicHttpBinding_IPaleoService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IPaleoService".equals(portName)) {
            setBasicHttpBinding_IPaleoServiceEndpointAddress(address);
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
