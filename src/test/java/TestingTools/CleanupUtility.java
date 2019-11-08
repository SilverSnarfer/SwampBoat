/**
 * 
 */
package TestingTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import services.TestWriterDispatcherService;

/**
 * @author Aaron.Hayward
 *
 */
public class CleanupUtility {
	private static final Logger logger = LogManager.getLogger(CleanupUtility.class);
	public static final File testOutputs = new File(System.getProperty("user.home") + "\\swampTest\\output\\swampDefault");

	
	public static void cleanup() throws IOException{
		for(File file: testOutputs.listFiles()) {
			recursiveMove(file);
			if(file.isDirectory()) {
				move(file);
			}
		}		
	}
	
	private static void recursiveMove(File root) {
		if(!root.isDirectory()) {
			move(root);
			return;
		}
		File[] children = root.listFiles();
		if(children.length < 1) {
			move(root);
			return;
		}
		for(File child: children) {
			if(child.isDirectory()) {
				recursiveMove(child);
				if(child.listFiles().length < 1) {
					move(child);
				}
			} else {
				move(child);
			}
		}	
	}
	
	private static void move(File file) {
		try {
			Path source = Paths.get(file.getAbsolutePath());
			Path target = Paths.get(System.getProperty("user.home") + "\\swampTrash\\");
			
			Files.move(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException i) {
			logger.error(("CleanupUtility: Unable to move file '" + file.getAbsolutePath() + "'"));
		}
	}

}
