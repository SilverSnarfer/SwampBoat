/**
 * 
 */
package pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Aaron.Hayward
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BugInstance")
public class BugInstance {
	
	@XmlAttribute(name="id")
	private String id;
	
	@XmlElement(name="ClassName")
	private String className;
	
	@XmlElement(name="Methods")
	private Methods methods;
	
	@XmlElement(name="BugLocations")
	private BugLocations bugLocations;
	
	@XmlElement(name="BugGroup")
	private String bugGroup;
	
	@XmlElement(name="BugCode")
	private String bugCode;
	
	@XmlElement(name="BugRank")
	private String bugRank;
	
	@XmlElement(name="BugSeverity")
	private String bugSeverity;
	
	@XmlElement(name="BugMessage")
	private String bugMessage;
	
	@XmlElement(name="ResolutionSuggestion")
	private String resolutionSuggestion;
	
	@XmlElement(name="BugTrace")
	private BugTrace bugTrace;

	public String getId() {
		return id;
	}

	public String getClassName() {
		return className;
	}

	public Methods getMethods() {
		return methods;
	}

	public BugLocations getBugLocations() {
		return bugLocations;
	}

	public String getBugGroup() {
		return bugGroup;
	}

	public String getBugCode() {
		return bugCode;
	}

	public String getBugRank() {
		return bugRank;
	}

	public String getBugSeverity() {
		return bugSeverity;
	}

	public String getBugMessage() {
		return bugMessage;
	}
	

	public String getResolutionSuggestion() {
		return resolutionSuggestion;
	}

	public BugTrace getBugTrace() {
		return bugTrace;
	}
	
	
}
