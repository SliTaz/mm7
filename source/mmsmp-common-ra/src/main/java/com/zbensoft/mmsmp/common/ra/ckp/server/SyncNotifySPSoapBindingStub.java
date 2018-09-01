package com.zbensoft.mmsmp.common.ra.ckp.server;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.xml.namespace.QName;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

public class SyncNotifySPSoapBindingStub extends Stub implements SyncNotifySPService {
	private Vector cachedSerClasses = new Vector();
	private Vector cachedSerQNames = new Vector();
	private Vector cachedSerFactories = new Vector();
	private Vector cachedDeserFactories = new Vector();

	static OperationDesc[] _operations = new OperationDesc[1];

	static {
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		OperationDesc oper = new OperationDesc();
		oper.setName("orderRelationUpdateNotify");
		ParameterDesc param = new ParameterDesc(new QName("", "orderRelationUpdateNotifyRequest"), (byte) 1, new QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest"),
				OrderRelationUpdateNotifyRequest.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse"));
		oper.setReturnClass(OrderRelationUpdateNotifyResponse.class);
		oper.setReturnQName(new QName("", "orderRelationUpdateNotifyReturn"));
		oper.setStyle(Style.RPC);
		oper.setUse(Use.ENCODED);
		_operations[0] = oper;
	}

	public SyncNotifySPSoapBindingStub() throws AxisFault {
		this(null);
	}

	public SyncNotifySPSoapBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
		this(service);
		this.cachedEndpoint = endpointURL;
	}

	public SyncNotifySPSoapBindingStub(javax.xml.rpc.Service service) throws AxisFault {
		if (service == null)
			this.service = new org.apache.axis.client.Service();
		else {
			this.service = service;
		}
		((org.apache.axis.client.Service) this.service).setTypeMappingVersion("1.2");

		Class beansf = BeanSerializerFactory.class;
		Class beandf = BeanDeserializerFactory.class;
		Class enumsf = EnumSerializerFactory.class;
		Class enumdf = EnumDeserializerFactory.class;
		Class arraysf = ArraySerializerFactory.class;
		Class arraydf = ArrayDeserializerFactory.class;
		Class simplesf = SimpleSerializerFactory.class;
		Class simpledf = SimpleDeserializerFactory.class;
		Class simplelistsf = SimpleListSerializerFactory.class;
		Class simplelistdf = SimpleListDeserializerFactory.class;
		QName qName = new QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest");
		this.cachedSerQNames.add(qName);
		Class cls = OrderRelationUpdateNotifyRequest.class;
		this.cachedSerClasses.add(cls);
		this.cachedSerFactories.add(beansf);
		this.cachedDeserFactories.add(beandf);

		qName = new QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse");
		this.cachedSerQNames.add(qName);
		cls = OrderRelationUpdateNotifyResponse.class;
		this.cachedSerClasses.add(cls);
		this.cachedSerFactories.add(beansf);
		this.cachedDeserFactories.add(beandf);
	}

	protected Call createCall() throws RemoteException {
		try {
			Call _call = super._createCall();
			if (this.maintainSessionSet) {
				_call.setMaintainSession(this.maintainSession);
			}
			if (this.cachedUsername != null) {
				_call.setUsername(this.cachedUsername);
			}
			if (this.cachedPassword != null) {
				_call.setPassword(this.cachedPassword);
			}
			if (this.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(this.cachedEndpoint);
			}
			if (this.cachedTimeout != null) {
				_call.setTimeout(this.cachedTimeout);
			}
			if (this.cachedPortName != null) {
				_call.setPortName(this.cachedPortName);
			}
			Enumeration keys = this.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				_call.setProperty(key, this.cachedProperties.get(key));
			}

			synchronized (this) {
				if (firstCall()) {
					_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
					_call.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
					for (int i = 0; i < this.cachedSerFactories.size(); i++) {
						Class cls = (Class) this.cachedSerClasses.get(i);
						QName qName = (QName) this.cachedSerQNames.get(i);
						Object x = this.cachedSerFactories.get(i);
						if ((x instanceof Class)) {
							Class sf = (Class) this.cachedSerFactories.get(i);
							Class df = (Class) this.cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) this.cachedSerFactories.get(i);
							DeserializerFactory df = (DeserializerFactory) this.cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (Throwable _t) {
			throw new AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws RemoteException {
		if (this.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName("http://soap.bossagent.vac.unicom.com", "orderRelationUpdateNotify"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			Object _resp = _call.invoke(new Object[] { orderRelationUpdateNotifyRequest });

			if ((_resp instanceof RemoteException)) {
				throw ((RemoteException) _resp);
			}

			extractAttachments(_call);
			try {
				return (OrderRelationUpdateNotifyResponse) _resp;
			} catch (Exception _exception) {
				return (OrderRelationUpdateNotifyResponse) JavaUtils.convert(_resp, OrderRelationUpdateNotifyResponse.class);
			}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}
}
