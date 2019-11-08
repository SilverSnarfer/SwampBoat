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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}
	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	/**
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	/**
	 * @return the startLine
	 */
	public String getStartLine() {
		return startLine;
	}
	/**
	 * @param startLine the startLine to set
	 */
	public void setStartLine(String startLine) {
		this.startLine = startLine;
	}
	/**
	 * @return the endLine
	 */
	public String getEndLine() {
		return endLine;
	}
	/**
	 * @param endLine the endLine to set
	 */
	public void setEndLine(String endLine) {
		this.endLine = endLine;
	}
	/**
	 * @return the startColumn
	 */
	public String getStartColumn() {
		return startColumn;
	}
	/**
	 * @param startColumn the startColumn to set
	 */
	public void setStartColumn(String startColumn) {
		this.startColumn = startColumn;
	}
	/**
	 * @return the endColumn
	 */
	public String getEndColumn() {
		return endColumn;
	}
	/**
	 * @param endColumn the endColumn to set
	 */
	public void setEndColumn(String endColumn) {
		this.endColumn = endColumn;
	}
	
	
	
	
}
