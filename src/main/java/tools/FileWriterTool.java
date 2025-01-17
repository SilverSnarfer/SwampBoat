/**
 * 
 */
package tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.ConfigFileException;
import exceptions.SwampException.ExceptionCode;
import pojo.AnalyzerReport.ToolName;
import pojo.Location;
import services.WriterDispatcherService;

/**
 * @author Aaron.Hayward
 *
 */
public class FileWriterTool {
	private static final Logger logger = LogManager.getLogger(FileWriterTool.class);

	public static final String winHome = "user.home";	
	/**
	 * converts the data from a list of Location objects to a single string
	 * @param bugLocations
	 * @param dispatcherState
	 * @return
	 */
	 public static String buildLocationString(List<Location> bugLocations, WriterDispatcherService dispatcherState) {
		if(bugLocations == null || bugLocations.size() < 1) {
			return "n/a";
		}
		
		StringBuilder locationString = new StringBuilder(70);
		for (Location location : bugLocations) {
			locationString.append("\"Start line: " + location.getStartLine() + dispatcherState.getNewLine());
			locationString.append("End line: " + location.getEndLine() + dispatcherState.getNewLine());
			if (dispatcherState.getToolName().equals(ToolName.CHECK_STYLE)) {
				locationString.append("Source File: " + location.getSourceFile() + dispatcherState.getNewLine());
			} 
			if(dispatcherState.getToolName().equals(ToolName.PMD)) {
				locationString.append("Start Col: " + location.getStartColumn() + dispatcherState.getNewLine());
				locationString.append("End Col: " + location.getEndColumn());
			}
			
			locationString.append("\"");
		}		
		
		return locationString.toString();

	}
	
	private static File initWorkspace(String path, String fileName, ToolName toolName, String timeStamp, boolean isSaveRuns) {
		File workingDir = new File(path+ "/" + toolName.toString() + (!isSaveRuns ? "" : timeStamp));
		if(!workingDir.exists()) {
			workingDir.mkdir();
		}			
		return new File(workingDir.getAbsolutePath() + "/" + fileName);
	}
	
	public static void cleanup(File... rootDir) throws IOException{		
		for(File dir: rootDir) {
			for(File child: dir.listFiles()) {
				move(child);
			}	
		}
	}
	
	private static void move(File file) {
		try {
			FileUtils.moveDirectoryToDirectory(file, new File(System.getProperty("user.home") + "\\swampTrash\\"), true);
		} catch(FileExistsException f) {
			try {
				FileUtils.deleteDirectory(new File(System.getProperty("user.home") + "\\swampTrash\\" + file.getName()));
				FileUtils.moveDirectoryToDirectory(file, new File(System.getProperty("user.home") + "\\swampTrash\\"), true);
			} catch (IOException e) {
				logger.error(("FileWriterTool: Unable to delete '" + System.getProperty("user.home") + "\\swampTrash\\" + file.getName() + "' before moving '" + file.getAbsolutePath() + "'"));
			}
		} catch(IOException i) {
			logger.error(("FileWriterTool: Unable to move '" + file.getAbsolutePath() + "'"));
		}
	}

	/**
	 * depending on the settings in the configuration file, this creates the proper filename and designates the proper parent directory.
	 * @throws ConfigFileException
	 */
	public static Map<Enum<FileType>, File> initializeFilesAndFolders(Properties config, ToolName toolName, String timeStamp, boolean isSaveRuns) throws ConfigFileException{		
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
		if(propertyNullSafe(useDefaultCsvDestinationProp, config).equalsIgnoreCase("true")) {
			usingDefaultLocation = true;
		}

		if(propertyNullSafe(defaultCsvDestinationProp ,config).matches(spaceRegex) && propertyNullSafe(customReportDestProp ,config).matches(spaceRegex) && propertyNullSafe(customSummaryDestProp ,config).matches(spaceRegex)) {
			throw new ConfigFileException("problem with your csv file settings (config.properties). No locations found", ExceptionCode.ConfigFile_EmptyLocations);
		} else if(usingDefaultLocation && (!propertyNullSafe(customReportDestProp ,config).matches(spaceRegex) || !propertyNullSafe(customSummaryDestProp ,config).matches(spaceRegex))){ //Mixing locations
			throw new ConfigFileException("problem with your csv file settings (config.properties). Both Default and custom locations are present.", ExceptionCode.ConfigFile_MixedLocations);
		} else if(!usingDefaultLocation && (propertyNullSafe(customReportDestProp ,config).matches(spaceRegex) || propertyNullSafe(customSummaryDestProp ,config).matches(spaceRegex))) {//incomplete custom location 
			throw new ConfigFileException("problem with your csv file settings (config.properties) Incomplete custom locations.", ExceptionCode.ConfigFile_IncompleteCustomLocations);

		} else if(usingDefaultLocation){
			reportFileLocation = config.getProperty(defaultCsvDestinationProp);
			summaryFileLocation = config.getProperty(defaultCsvDestinationProp);
		} else {
			reportFileLocation = config.getProperty(customReportDestProp);
			summaryFileLocation = config.getProperty(customSummaryDestProp);
		}
		
		//Determines filenames
		if(propertyNullSafe(defaultReportFilenameProp ,config).matches(spaceRegex) && propertyNullSafe(defaultSummaryFilenameProp ,config).matches(spaceRegex) &&
				  propertyNullSafe(customReportFilenameProp ,config).matches(spaceRegex) && propertyNullSafe(customSummaryFilenameProp ,config).matches(spaceRegex)) { // empty filnames
			throw new ConfigFileException("problem with your csv file settings (config.properties). No File names found.", ExceptionCode.ConfigFile_EmptyFilenames);
		} else if((!propertyNullSafe(defaultReportFilenameProp ,config).matches(spaceRegex) || !propertyNullSafe(defaultSummaryFilenameProp ,config).matches(spaceRegex)) &&
				  (!propertyNullSafe(customReportFilenameProp ,config).matches(spaceRegex) || !propertyNullSafe(customSummaryFilenameProp ,config).matches(spaceRegex))) {// mixing filenames
			throw new ConfigFileException("problem with your csv file settings (config.properties). Both default and custom names found.", ExceptionCode.ConfigFile_MixedFilenames);
		} else if(propertyNullSafe(customReportFilenameProp ,config).matches(spaceRegex) && propertyNullSafe(customSummaryFilenameProp ,config).matches(spaceRegex)){ //incomplete default filenames
			usingDefaultFilenames = true;
			if(propertyNullSafe(defaultReportFilenameProp ,config).matches(spaceRegex) || propertyNullSafe(defaultSummaryFilenameProp ,config).matches(spaceRegex)) {
				throw new ConfigFileException("problem with your csv file settings (config.properties). Incomplete Default file names.", ExceptionCode.ConfigFile_IncompleteDefaultFilenames);
			}
		} else if (propertyNullSafe(defaultReportFilenameProp ,config).matches(spaceRegex) && propertyNullSafe(defaultSummaryFilenameProp ,config).matches(spaceRegex)) { //incomplete custom filenames
			if(propertyNullSafe(customReportFilenameProp ,config).matches(spaceRegex) || propertyNullSafe(customSummaryFilenameProp ,config).matches(spaceRegex)) {
				throw new ConfigFileException("problem with your csv file settings (config.properties). Incomplete Custom file names.", ExceptionCode.ConfigFile_IncompleteCustomFilenames);
			}
		} 
		
		if(usingDefaultFilenames) {
			reportFilename = config.getProperty(defaultReportFilenameProp);
			summaryFilename = config.getProperty(defaultSummaryFilenameProp);
		} else {
			reportFilename = config.getProperty(customReportFilenameProp);
			summaryFilename = config.getProperty(customSummaryFilenameProp);
		}
		Map<Enum<FileType>, File> files = new HashMap<>();
		
		
		files.put(FileType.BUG_SUMMARY, initWorkspace(summaryFileLocation, summaryFilename, toolName, timeStamp, isSaveRuns));
		files.put(FileType.BUG_REPORT, initWorkspace(reportFileLocation, reportFilename, toolName, timeStamp, isSaveRuns));
		
		return files;
	}
	
	private static String propertyNullSafe(String property, Properties config) {
		if(config.getProperty(property) != null) {
			return config.getProperty(property).trim();
		}
		return "";
	}
	
	public static enum FileType {
		BUG_SUMMARY,
		BUG_REPORT
	}
}
