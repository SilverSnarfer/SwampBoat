/**
 * 
 */
package TestingTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aaron.Hayward
 *
 */
public class PropertiesTool {
	private static final String useDefaultCsvDestinationProp = "useDefaultCsvDestination";
	private static final String defaultCsvDestinationProp = "defaultCsvDestination";
	private static final String defaultReportFilenameProp = "defaultCsvBugReportFilename";
	private static final String defaultSummaryFilenameProp = "defaultCsvBugSummaryFilename";
	
	private static final String customReportFilenameProp = "customCsvBugReportFilename";
	private static final String customSummaryFilenameProp = "customCsvBugSummaryFilename";
	private static final String customReportDestProp = "customCsvBugReportDestination";
	private static final String customSummaryDestProp = "customCsvBugSummaryDestination";
	
	public static Map<String, String> location_defaultAndCustom() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "true");
		props.put(defaultCsvDestinationProp, "wio");
		props.put(customReportDestProp, "jfdks");
		
		return props;
	}
	
	public static Map<String, String> location_niether() {
		Map<String, String> props = new HashMap<>();
		return props;
	}
	
	
	public static String nullSafe(String str) {
		return str == null ? "": str;
	}

}
