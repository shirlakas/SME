
package patientflowmonitoring;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultationCompleted3Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultationCompleted3Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsultationCompleted3Response" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultationCompleted3Response", propOrder = {
    "consultationCompleted3Response"
})
public class ConsultationCompleted3Response {

    @XmlElement(name = "ConsultationCompleted3Response")
    protected String consultationCompleted3Response;

    /**
     * Gets the value of the consultationCompleted3Response property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsultationCompleted3Response() {
        return consultationCompleted3Response;
    }

    /**
     * Sets the value of the consultationCompleted3Response property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsultationCompleted3Response(String value) {
        this.consultationCompleted3Response = value;
    }

}
