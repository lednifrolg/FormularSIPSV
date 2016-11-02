package webservice;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebServiceClient(name = "TS", targetNamespace = "http://www.ditec.sk/", wsdlLocation = "http://test.ditec.sk/timestampws/TS.asmx?WSDL")
public class TS
        extends Service {

    private final static URL TS_WSDL_LOCATION;
    private final static WebServiceException TS_EXCEPTION;
    private final static QName TS_QNAME = new QName("http://www.ditec.sk/", "TS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://test.ditec.sk/timestampws/TS.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TS_WSDL_LOCATION = url;
        TS_EXCEPTION = e;
    }

    public TS() {
        super(__getWsdlLocation(), TS_QNAME);
    }

    public TS(WebServiceFeature... features) {
        super(__getWsdlLocation(), TS_QNAME, features);
    }

    public TS(URL wsdlLocation) {
        super(wsdlLocation, TS_QNAME);
    }

    public TS(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TS_QNAME, features);
    }

    public TS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TS(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    private static URL __getWsdlLocation() {
        if (TS_EXCEPTION != null) {
            throw TS_EXCEPTION;
        }
        return TS_WSDL_LOCATION;
    }

    /**
     * @return returns TSSoap
     */
    @WebEndpoint(name = "TSSoap")
    public TSSoap getTSSoap() {
        return super.getPort(new QName("http://www.ditec.sk/", "TSSoap"), TSSoap.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns TSSoap
     */
    @WebEndpoint(name = "TSSoap")
    public TSSoap getTSSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.ditec.sk/", "TSSoap"), TSSoap.class, features);
    }

}
