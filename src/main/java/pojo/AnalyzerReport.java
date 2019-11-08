/**
 * 
 */
package pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import exceptions.XMLSourceException;

/**
 * @author Aaron.Hayward
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AnalyzerReport")
public class AnalyzerReport {
	
	/**
	 * Categories of report type by tool name found in the xml report.
	 * @author Aaron.Hayward
	 *
	 */
	public enum ToolName {
		PMD,
		SPOT_BUGS,
		CHECK_STYLE,
		OWASP
	}
	private ToolName toolName;
	
	@XmlAttribute(name="assess_fw_version")
	private String assess_fw_version;
	
	@XmlAttribute(name="assess_fw")
	private String assess_fw;
	
	@XmlAttribute(name="assessment_start_ts")
	private String assessment_start_ts;
	
	@XmlAttribute(name="uuid")
	private String uuid;
	
	@XmlAttribute(name="parser_fw_version")
	private String parser_fw_version;
	
	@XmlAttribute(name="build_root_dir")
	private String build_root_dir;

	@XmlAttribute(name="package_version")
	private String package_version;

	@XmlAttribute(name="platform_name")
	private String platform_name;

	@XmlAttribute(name="tool_version")
	private String tool_version;

	@XmlAttribute(name="package_root_dir")
	private String package_root_dir;

	@XmlAttribute(name="package_name")
	private String package_name;

	@XmlAttribute(name="parser_fw")
	private String parser_fw;

	@XmlAttribute(name="tool_name")
	private String tool_name;
	
	
	@XmlElement(name="BugInstance")
	private List<BugInstance> bugInstances;
	
	@XmlElement(name="BugSummary")
	private BugSummary bugSummary;

	public ToolName getToolName() throws XMLSourceException{
		if(toolName == null) {
			setToolName();
		}
		return toolName;
	}

	private void setToolName() throws XMLSourceException{
		
		if(this.tool_name == null) {
			throw new XMLSourceException("'tool_name' not found in the xml");
		}
		String tn = tool_name.toLowerCase(java.util.Locale.ENGLISH);
		if(tn.equals("checkstyle")) {
			toolName = ToolName.CHECK_STYLE;
		} else if (tn.equals("dependency-check")) {
			toolName = ToolName.OWASP;
		}else if (tn.equals("spotbugs")) {
			toolName = ToolName.SPOT_BUGS;
		}else if (tn.equals("pmd")) {
			toolName = ToolName.PMD;
		}
	}

	public String getAssessFwVersion() {
		return assess_fw_version;
	}

	public String getAssessFw() {
		return assess_fw;
	}

	public String getAssessmentStartTs() {
		return assessment_start_ts;
	}

	public String getUuid() {
		return uuid;
	}

	public String getParserFwVersion() {
		return parser_fw_version;
	}

	public String getBuildRootDir() {
		return build_root_dir;
	}

	public String getPackageVersion() {
		return package_version;
	}

	public String getPlatformName() {
		return platform_name;
	}

	public String getToolVersion() {
		return tool_version;
	}

	public String getPackageRootDir() {
		return package_root_dir;
	}

	public String getPackageName() {
		return package_name;
	}

	public String getParserFw() {
		return parser_fw;
	}

	public String getXmlToolName() {
		return tool_name;
	}

	public List<BugInstance> getBugInstances() {
		return bugInstances;
	}

	public BugSummary getBugSummary() {
		return bugSummary;
	}
	
	
}
