package webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebService(name = "TSSoap", targetNamespace = "http://www.ditec.sk/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface TSSoap {


    /**
     * @param dataB64
     * @return returns java.lang.String
     */
    @WebMethod(operationName = "GetTimestamp", action = "http://www.ditec.sk/GetTimestamp")
    @WebResult(name = "GetTimestampResult", targetNamespace = "http://www.ditec.sk/")
    @RequestWrapper(localName = "GetTimestamp", targetNamespace = "http://www.ditec.sk/", className = "sk.ditec.GetTimestamp")
    @ResponseWrapper(localName = "GetTimestampResponse", targetNamespace = "http://www.ditec.sk/", className = "sk.ditec.GetTimestampResponse")
    public String getTimestamp(
            @WebParam(name = "dataB64", targetNamespace = "http://www.ditec.sk/")
                    String dataB64);

}
