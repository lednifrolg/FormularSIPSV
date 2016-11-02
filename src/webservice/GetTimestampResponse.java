package webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetTimestampResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getTimestampResult"
})
@XmlRootElement(name = "GetTimestampResponse")
public class GetTimestampResponse {

    @XmlElement(name = "GetTimestampResult")
    protected String getTimestampResult;

    /**
     * Gets the value of the getTimestampResult property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGetTimestampResult() {
        return getTimestampResult;
    }

    /**
     * Sets the value of the getTimestampResult property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGetTimestampResult(String value) {
        this.getTimestampResult = value;
    }

}
