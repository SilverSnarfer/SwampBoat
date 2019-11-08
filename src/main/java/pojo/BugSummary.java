/**
 * 
 */
package pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Aaron.Hayward
 *
 */
@XmlType(name="BugSummary")
public class BugSummary {
	
	@XmlElement(name="BugCategory")
	private List<BugCategory> bugCategory;

	public List<BugCategory> getBugCategory() {
		return bugCategory;
	}
	
	

}
