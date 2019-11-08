/**
 * 
 */
package tools;

import java.io.File;
import java.util.List;

import pojo.AnalyzerReport.ToolName;
import pojo.Location;
import services.WriterDispatcherService;

/**
 * @author Aaron.Hayward
 *
 */
public class FileWriterTool {
	public static final String winHome = "user.home";
	public static final String swampDefaultFoldername = "swampDefault";
	
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
	
	public static File initWorkspace(String path, String fileName, WriterDispatcherService dispatcherState) {
		File file = null;
		boolean saveRuns = dispatcherState.isSaveRuns();
		String timeStamp = !saveRuns ? "" : dispatcherState.getTimeStamp();
		
		File workingDir = new File(path+ "/" + dispatcherState.getToolName().toString() + timeStamp);
		if(!workingDir.exists()) {
			workingDir.mkdir();
		}
		
		file = new File(workingDir.getAbsolutePath() + "/" + fileName);
			
		return file;
	}
}
