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
	
	public static Map<String, String> location_incompleteCustom() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "false");
		props.put(customReportDestProp, "fjdsio");
		return props;

	}
	
	public static Map<String, String> filenames_empty() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "true");
		props.put(defaultCsvDestinationProp, "kopewk");
		return props;
	}
	
	
	public static Map<String, String> filenames_mixed() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "true");
		props.put(defaultCsvDestinationProp, "kopewk");
		props.put(customReportFilenameProp, "jdfsoi");
		props.put(defaultSummaryFilenameProp, "fjdklsjk");
		return props;
	}
	
	public static Map<String, String> filenames_incompleteDefault() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "true");
		props.put(defaultCsvDestinationProp, "kopewk");
		props.put(defaultSummaryFilenameProp, "fjdklsjk");
		return props;
	}
	
	public static Map<String, String> filenames_incompleteCustom() {
		Map<String, String> props = new HashMap<>();
		props.put(useDefaultCsvDestinationProp, "true");
		props.put(defaultCsvDestinationProp, "kopewk");
		props.put(customSummaryFilenameProp, "fjdklsjk");
		return props;
	}
	
	public static String nullSafe(String str) {
		return str == null ? "": str;
	}

}
