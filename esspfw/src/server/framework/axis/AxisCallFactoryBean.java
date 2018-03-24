package server.framework.axis;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AxisCallFactoryBean implements FactoryBean, InitializingBean{
	protected final Log logger = LogFactory.getLog(getClass());
	private String wsdlDocumentUrl;
	private String namespaceUri;
	private String operationName;
	private String registerTypeClass;
	private QName qname;
	private Call call ;

	public QName getQname() {
		return qname;
	}

	public void setQname(QName qname) {
		this.qname = qname;
	}

	public String getRegisterTypeClass() {
		return registerTypeClass;
	}

	public void setRegisterTypeClass(String registerTypeClass) {
		this.registerTypeClass = registerTypeClass;
	}

	public Object getObject() throws Exception {
		return call;
	}

	public Class getObjectType() {
		return Call.class;
	}

	public boolean isSingleton() {
		return true;
	}

	/**
	 * Set the URL of the WSDL document that describes the service.
	 */
	public void setWsdlDocumentUrl(String wsdlDocumentUrl) {
		this.wsdlDocumentUrl = wsdlDocumentUrl;
	}

	/**
	 * Return the URL of the WSDL document that describes the service.
	 */
	public String getWsdlDocumentUrl() {
		return wsdlDocumentUrl;
	}

	/**
	 * Set the namespace URI of the service.
	 * Corresponds to the WSDL "targetNamespace".
	 */
	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	/**
	 * Return the namespace URI of the service.
	 */
	public String getNamespaceUri() {
		return namespaceUri;
	}
	/**
	 * Return a QName for the given name, relative to the namespace URI
	 * of this factory, if given.
	 * @see #setNamespaceUri
	 */
	public QName getQName(String name) {
		return (this.namespaceUri != null) ? new QName(this.namespaceUri, name) : new QName(name);
	}
	public void afterPropertiesSet() throws Exception {
		createCall();
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	private void createCall() throws ServiceException, MalformedURLException{
		Service service = new Service();
		call = (Call) service.createCall();

		Class registryType = null;
		try {
			registryType = Class.forName(registerTypeClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("can not find register type:"+registerTypeClass);
		}

		call.registerTypeMapping(registryType, qname,
		        new BeanSerializerFactory(registryType, qname),
		        new BeanDeserializerFactory(registryType, qname));

		call.setOperationName(new QName(namespaceUri, operationName));
		call.setTargetEndpointAddress(new java.net.URL(wsdlDocumentUrl));
	}
}
