package pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Aaron.Hayward
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanceLocation")
public class InstanceLocation {

	@XmlElement(name="Xpath")
	private String xPath;

	/**
	 * @return the xPath
	 */
	public String getxPath() {
		return xPath;
	}

	/**
	 * @param xPath the xPath to set
	 */
	public void setxPath(String xPath) {
		this.xPath = xPath;
	}
	
	
}
