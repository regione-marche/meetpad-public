/**
 * SignServicesPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.marche.regione.calamaio.services;

public interface SignServicesPortType extends java.rmi.Remote {
    public it.marche.regione.calamaio.services.MultiSignResult hsmMultiSignature(java.lang.String configurationId, java.lang.String user, java.lang.String password, java.lang.String otp, java.lang.String domain, byte[][] documents) throws java.rmi.RemoteException;
    public it.marche.regione.calamaio.services.SignResult hsmSignature(java.lang.String configurationId, java.lang.String user, java.lang.String password, java.lang.String otp, java.lang.String domain, byte[] document) throws java.rmi.RemoteException;
}
