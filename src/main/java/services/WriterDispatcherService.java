/**
 * 
 */
package services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import exceptions.BugReportException;
import exceptions.BugSummaryException;
import exceptions.CSVHeaderException;
import exceptions.ConfigFileException;
import exceptions.NullReportException;
import pojo.AnalyzerReport.ToolName;
import pojo.BugInstance;
import pojo.BugSummary;
import tools.FileWriterTool;
import writers.CheckStyleWriter;
import writers.OWASPWriter;
import writers.PMDWriter;
import writers.SpotBugsWriter;
import writers.SummaryWriter;
import writers.Writeable;

/**
 * @author Aaron.Hayward
 * @apiNote Initializes the filesystem with files/directories with regard to what type of report(SwampBoat) is being generated then writes the report. Also stores the state of which writer is being used and the type of report(xml) that is being processed, along with CSV headers and special characters (Delimiters, line breaks). {@code Writeable} objects will regularly refer back to this class for stateful information.
 */
public class WriterDispatcherService {
	private final String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '.');
	
	public static final String DELIMITER = ",";
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final String SUMMARY_HEADER = "Group,Code,Count,Bytes";
	private static final String PMD_HEADER = "Id,Class Name,Location,Bug Group,Bug Code,Severity,Message";
	private static final String SPOT_BUGS_HEADER = "Id,Class Name,Location,Group,Code,Rank,Severity,Message,Resolution Suggestion";
	private static final String CHECK_STYLE_HEADER = "Id,Location,Bug Group,Bug Code,Severity,Message";
	private static final String OWASP_HEADER = "Id,Source File,Bug Group,Bug Code,Severity,Message Location";
	
	private final Properties config;
	private final ToolName toolName;
	private final boolean saveRuns;
	
	private File bugSummaryFile;
	private File bugReportFile;
	private String csvReportHeader;
	private Writeable reportWriter;
	private SummaryWriter summaryWriter;
	
	public ToolName getToolName() {return this.toolName;}
	public Properties getConfig() {return this.config;}
	public boolean isSaveRuns() {return this.saveRuns;}
	public String getTimeStamp() {return this.timeStamp;}
	public String getNewLine() {return NEW_LINE;}
	public String getCsvReportHeader() {return csvReportHeader;}
	public File getBugReportFile() {return bugReportFile;}
	public File getBugSummaryFile() {return bugSummaryFile;}


	public WriterDispatcherService(Properties config, ToolName toolName) throws ConfigFileException, CSVHeaderException, IOException{
		this.config = config;
		this.toolName = toolName;
		
		
		//Relative to the config property 'saveRuns'. if true, a timestamp will be added to the foldername(for persistence) of the current run
		this.saveRuns = config.getProperty("saveRuns").trim().equalsIgnoreCase("true") ? true : false;
		
		initializeFilesAndFolders();
		
		this.csvReportHeader = determineHeader();

		this.summaryWriter = new SummaryWriter();
		
		//sets writer object based on the xml report type
		if(toolName.equals(ToolName.SPOT_BUGS)) {
			reportWriter = new SpotBugsWriter(bugReportFile);
		} else if(toolName.equals(ToolName.PMD)) {
			reportWriter = new PMDWriter();
		} else if(toolName.equals(ToolName.OWASP)) {
			reportWriter = new OWASPWriter(bugReportFile);
		} else if(toolName.equals(ToolName.CHECK_STYLE)) {
			reportWriter = new CheckStyleWriter();
		}
	}

	/**
	 * Kicks off the writing process for both Summary and Full report.
	 * @param bugInstances
	 * @param bugSummary
	 * @throws IOException
	 * @throws NullReportException
	 */
	public void writeBugInstancesAndSummary(List<BugInstance> bugInstances, BugSummary bugSummary) throws IOException, NullReportException, BugReportException, BugSummaryException{
		if(bugInstances == null || bugInstances.isEmpty()) {
			throw new BugReportException("No bug instances found.");
		} else if(bugSummary == null) {
			throw new BugSummaryException("No bug summary found.");
		}

		reportWriter.write(bugInstances, this);
		summaryWriter.writeBugSummary(bugSummary, this, SUMMARY_HEADER);
	}
	
	private String determineHeader() throws CSVHeaderException{
		if(toolName == ToolName.CHECK_STYLE) {
			return CHECK_STYLE_HEADER;
		} else if(toolName == ToolName.PMD) {
			return PMD_HEADER;
		} else if(toolName == ToolName.SPOT_BUGS) {
			return SPOT_BUGS_HEADER;
		} else if(toolName == ToolName.OWASP) {
			return OWASP_HEADER;
		} else {
			throw new CSVHeaderException("The header has not been set, something is wrong");
		}
		
	}
	
	
	/**
	 * depending on the settings in the configuration file, this creates the proper filename and designates the proper parent directory.
	 * @throws ConfigFileException
	 */
	private void initializeFilesAndFolders() throws ConfigFileException{		
		String spaceRegex = "^\\s*$";

		String useDefaultCsvDestinationProp = "useDefaultCsvDestination";
		String defaultCsvDestinationProp = "defaultCsvDestination";
		String defaultReportFilenameProp = "defaultCsvBugReportFilename";
		String defaultSummaryFilenameProp = "defaultCsvBugSummaryFilename";
		
		String customReportFilenameProp = "customCsvBugReportFilename";
		String customSummaryFilenameProp = "customCsvBugSummaryFilename";
		String customReportDestProp = "customCsvBugReportDestination";
		String customSummaryDestProp = "customCsvBugSummaryDestination";

		String reportFileLocation = null;
		String reportFilename = null;
		String summaryFileLocation = null;
		String summaryFilename = null;
		
		boolean usingDefaultLocation = false;
		boolean usingDefaultFilenames = false;

		//Determines file locations
		if(propertyNullSafe(useDefaultCsvDestinationProp).equalsIgnoreCase("true")) {
			reportFileLocation = config.getProperty(defaultCsvDestinationProp) + "/" + FileWriterTool.swampDefaultFoldername;
			summaryFileLocation = config.getProperty(defaultCsvDestinationProp) + "/" + FileWriterTool.swampDefaultFoldername;
			usingDefaultLocation = true;
		}
		if(!usingDefaultLocation && !propertyNullSafe(customReportDestProp).matches(spaceRegex) && !propertyNullSafe(customSummaryDestProp).matches(spaceRegex)) {
			reportFileLocation = config.getProperty(customReportDestProp);
			summaryFileLocation = config.getProperty(customSummaryDestProp);
		} else if(usingDefaultLocation && (!propertyNullSafe(customReportDestProp).matches(spaceRegex) || !propertyNullSafe(customSummaryDestProp).matches(spaceRegex))){
			throw new ConfigFileException("problem with your csv file settings (config.properties). Make sure either 'customCsvBug(Report/Summary)Destination' OR 'defaultCsvDestination'(not both) is set in the config file.");
		}
		
		//Determines filenames
		if(!propertyNullSafe(defaultReportFilenameProp).matches(spaceRegex) && !propertyNullSafe(defaultSummaryFilenameProp).matches(spaceRegex)) {
			reportFilename = config.getProperty(defaultReportFilenameProp);
			summaryFilename = config.getProperty(defaultSummaryFilenameProp);
			usingDefaultFilenames = true;
		} else if(!propertyNullSafe(defaultReportFilenameProp).matches(spaceRegex) || !propertyNullSafe(defaultSummaryFilenameProp).matches(spaceRegex)){
			throw new ConfigFileException("problem with your csv file settings (config.properties). Make sure either 'customCsvBug(Report/Summary)Filename' OR 'default(Report/Summary)FilenameProp'(not both) is set in the config file.");
		}
		if((!usingDefaultFilenames && (propertyNullSafe(customReportFilenameProp).matches(spaceRegex) || propertyNullSafe(customSummaryFilenameProp).matches(spaceRegex))) ||
		   (usingDefaultFilenames && (!propertyNullSafe(customReportFilenameProp).matches(spaceRegex) || !propertyNullSafe(customSummaryFilenameProp).matches(spaceRegex)))	) {
			throw new ConfigFileException("problem with your csv file settings (config.properties). Make sure either 'customCsvBug(Report/Summary)Filename' OR 'default(Report/Summary)FilenameProp'(not both) is set in the config file.");
		} else if(!usingDefaultFilenames) {
			reportFilename = config.getProperty(customReportFilenameProp);
			summaryFilename = config.getProperty(customSummaryFilenameProp);
		}
		this.bugSummaryFile = FileWriterTool.initWorkspace(summaryFileLocation, summaryFilename, this);
		this.bugReportFile = FileWriterTool.initWorkspace(reportFileLocation, reportFilename, this);
	}
	
	
	private String propertyNullSafe(String property) {
		if(config.getProperty(property) != null) {
			return config.getProperty(property).trim();
		}
		return "";
	}
	
}
