/**
 * 
 */
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
@XmlType(name = "BugTrace")
public class BugTrace {
	@XmlElement(name="BuildId")
	private String buildId;
	@XmlElement(name="AssessmentReportFile")
	private String assessmentReportFile;
	@XmlElement(name="InstanceLocation")
	private InstanceLocation instanceLocation;
	
	
	/**
	 * @return the buildId
	 */
	public String getBuildId() {
		return buildId;
	}

	/**
	 * @return the assessmentReportFile
	 */
	public String getAssessmentReportFile() {
		return assessmentReportFile;
	}
	/**
	 * @return the instanceLocation
	 */
	public InstanceLocation getInstanceLocation() {
		return instanceLocation;
	}

	
	
	
	
	
	
	
	
}
