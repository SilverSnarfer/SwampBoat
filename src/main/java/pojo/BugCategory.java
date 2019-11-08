/**
 * 
 */
package pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Aaron.Hayward
 *
 */

@XmlType(name="BugCategory")
public class BugCategory {
	
	@XmlAttribute(name="group")
	private String group;
	@XmlAttribute(name="code")
	private String code;
	@XmlAttribute(name="count")
	private String count;
	@XmlAttribute(name="bytes")
	private String bytes;
	public String getGroup() {
		return group;
	}
	public String getCode() {
		return code;
	}
	public String getCount() {
		return count;
	}
	public String getBytes() {
		return bytes;
	}
	
	
	

}
