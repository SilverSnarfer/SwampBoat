/**
 * 
 */
package TestingTools;

/**
 * @author Aaron.Hayward
 *
 */
public class TestFilenames {
	private static final String testSettings = "src/main/resources/testData/testSettings/";
	private static final String testData = "src/main/resources/testData/";

	//Config files
	public static final String goodConfig = testSettings + "config.properties";
	
	
	//Config filter files
	public static final String goodFilter = testSettings + "filter.properties";
	public static final String goodFilter_whitelist = testSettings + "Filter_Whitelist.properties";
	public static final String goodFilter_Blacklist = testSettings + "Filter_Blacklist.properties";
	public static final String badFilter = testSettings + "Filter_Bad.properties";

	
	
	//Data Files
	public static final String goodOWASP = testData + "OWASP.xml";
	public static final String goodCheckstyle = testData + "checkstyle.xml";
	public static final String goodPMD = testData + "PMD.xml";
	public static final String goodSpotBugs = testData + "SpotBugs.xml";

}
