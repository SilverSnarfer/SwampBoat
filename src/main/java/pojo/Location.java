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
@XmlType(name = "Location")
public class Location {
	
	@XmlAttribute(name="id")
	private String id;
	@XmlAttribute(name="primary")
	private String primary;
	@XmlElement(name="SourceFile")
	private String sourceFile;
	@XmlElement(name="StartLine")
	private String startLine;
	@XmlElement(name="EndLine")
	private String endLine;
	@XmlElement(name="StartColumn")
	private String startColumn;
	@XmlElement(name="EndColumn")
	private String endColumn;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}
	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	/**
	 * @return the startLine
	 */
	public String getStartLine() {
		return startLine;
	}
	/**
	 * @return the endLine
	 */
	public String getEndLine() {
		return endLine;
	}
	/**
	 * @return the startColumn
	 */
	public String getStartColumn() {
		return startColumn;
	}
	/**
	 * @return the endColumn
	 */
	public String getEndColumn() {
		return endColumn;
	}
	

	
	
	
}
