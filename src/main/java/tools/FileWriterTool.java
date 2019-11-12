/**
 * 
 */
package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public static void cleanup(File... rootDir) throws IOException{
		for(File dir: rootDir) {
			for(File file: dir.listFiles()) {				
				if(file.isDirectory()) {
					recursiveMove(file);
					move(file);
				} else {
					move(file);
				}
			}
		}
	}
	
	private static void recursiveMove(File root) {
		if(!root.isDirectory() ||(root.isDirectory() && root.listFiles().length == 0)) {
			move(root);
			return;
		} 
		
		for(File child: root.listFiles()) {
			if(child.isDirectory()) {
				recursiveMove(child);
				move(child);
			} else {
				recursiveMove(child);
			}
		}	
	}
	
	private static void move(File file) {
		try {
			Path source = Paths.get(file.getAbsolutePath());
			Path target = Paths.get(System.getProperty("user.home") + "\\swampTrash\\");
			
			Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException i) {
			logger.error(("FileWriterTool: Unable to move file '" + file.getAbsolutePath() + "'"));
		}
	}
}
