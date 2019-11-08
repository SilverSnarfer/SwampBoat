/**
 * 
 */
package pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Aaron.Hayward
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name ="BugLocations")
public class BugLocations {
	@XmlElement(name="Location")
	private List<Location> locations;

	/**
	 * @return the location
	 */
	public List<Location> getLocations() {
		return locations;
	}	
	
}
