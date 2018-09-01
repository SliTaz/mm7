package com.zbensoft.mmsmp.common.ra.ckp.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class SyncNotifySPServiceServiceLocator extends Service implements SyncNotifySPServiceService {
	private String SyncNotifySP_address = "http://localhost:8080/ucmms_vas_agent/services/SyncNotifySP";

	private String SyncNotifySPWSDDServiceName = "SyncNotifySP";

	private HashSet ports = null;

	public SyncNotifySPServiceServiceLocator() {
	}

	public SyncNotifySPServiceServiceLocator(EngineConfiguration config) {
		super(config);
	}

	public SyncNotifySPServiceServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	public String getSyncNotifySPAddress() {
		return this.SyncNotifySP_address;
	}

	public String getSyncNotifySPWSDDServiceName() {
		return this.SyncNotifySPWSDDServiceName;
	}

	public void setSyncNotifySPWSDDServiceName(String name) {
		this.SyncNotifySPWSDDServiceName = name;
	}

	public SyncNotifySPService getSyncNotifySP() throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(this.SyncNotifySP_address);
		} catch (MalformedURLException e) {
//			URL endpoint;
			throw new ServiceException(e);
		}
		
		return getSyncNotifySP(endpoint);
	}

	public SyncNotifySPService getSyncNotifySP(URL portAddress) throws ServiceException {
		try {
			SyncNotifySPSoapBindingStub _stub = new SyncNotifySPSoapBindingStub(portAddress, this);
			_stub.setPortName(getSyncNotifySPWSDDServiceName());
			return _stub;
		} catch (AxisFault e) {
		}
		return null;
	}

	public SyncNotifySPService getSyncNotifySP(String url) throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(url);
		} catch (MalformedURLException e) {
//			URL endpoint;
			throw new ServiceException(e);
		}
		
		return getSyncNotifySP(endpoint);
	}

	public void setSyncNotifySPEndpointAddress(String address) {
		this.SyncNotifySP_address = address;
	}

	public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
		try {
			if (SyncNotifySPService.class.isAssignableFrom(serviceEndpointInterface)) {
				SyncNotifySPSoapBindingStub _stub = new SyncNotifySPSoapBindingStub(new URL(this.SyncNotifySP_address), this);
				_stub.setPortName(getSyncNotifySPWSDDServiceName());
				return _stub;
			}
		} catch (Throwable t) {
			throw new ServiceException(t);
		}
		throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("SyncNotifySP".equals(inputPortName)) {
			return getSyncNotifySP();
		}

		Remote _stub = getPort(serviceEndpointInterface);
		((Stub) _stub).setPortName(portName);
		return _stub;
	}

	public QName getServiceName() {
		return new QName("http://soap.bossagent.vac.unicom.com", "SyncNotifySPServiceService");
	}

	public Iterator getPorts() {
		if (this.ports == null) {
			this.ports = new HashSet();
			this.ports.add(new QName("http://soap.bossagent.vac.unicom.com", "SyncNotifySP"));
		}
		return this.ports.iterator();
	}

	public void setEndpointAddress(String portName, String address) throws ServiceException {
		if ("SyncNotifySP".equals(portName)) {
			setSyncNotifySPEndpointAddress(address);
		} else {
			throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	public void setEndpointAddress(QName portName, String address) throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}
}
