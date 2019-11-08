/**
 * 
 */
package writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import pojo.BugInstance;
import services.WriterDispatcherService;

/**
 * @author Aaron.Hayward
 * Responsible for creating the owasp csv report and a sub-directory of message files that contain detailed information relative to it's bug.
 */
public class OWASPWriter implements Writeable{
	
	private final File bugReportFile;
	private final File owaspDir;
	public OWASPWriter(File bugReportFile) throws IOException{
		this.bugReportFile = bugReportFile;
		this.owaspDir = initOwaspDir();
	}	
	
	/**
	 * writes csv file and creates several message files in a sub-directory (if applicable)
	 */
	public void write(List<BugInstance> bugInstances, WriterDispatcherService dispatcherState) throws IOException{
		if(bugInstances == null || bugInstances.isEmpty()) {
			return;
		}
		
		FileWriter fileWriter = new FileWriter(new File(bugReportFile.getAbsolutePath()));
		
		fileWriter.append(dispatcherState.getCsvReportHeader() + WriterDispatcherService.NEW_LINE);
		for (BugInstance bugInstance : bugInstances) {
			
			fileWriter.append(csvSafe(bugInstance.getId()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugLocations().getLocations().get(0).getSourceFile()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugGroup()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugCode()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(bugInstance.getBugSeverity()) + WriterDispatcherService.DELIMITER);
			fileWriter.append(csvSafe(createMessageFile(bugInstance, owaspDir)) + WriterDispatcherService.NEW_LINE);

		}
		
		fileWriter.flush();
		fileWriter.close();
		
	}
	
	/**
	 * creates a message file (text) and returns the path to the file.
	 * @param bugInstance
	 * @param directory
	 * @return File Path String
	 * @throws IOException
	 */
	public String createMessageFile(BugInstance bugInstance, File directory) throws IOException{
		File messageFile = new File(directory.getAbsolutePath()+ "\\" +bugInstance.getId()+ "_OWASP_bug.txt");
		
		FileWriter fw = new FileWriter(messageFile);		
		fw.append("ID:\t\t" + bugInstance.getId() + WriterDispatcherService.NEW_LINE);
		fw.append("Bug Code:\t" + bugInstance.getBugCode() + WriterDispatcherService.NEW_LINE);
		fw.append("Bug Group:\t" + bugInstance.getBugGroup() + WriterDispatcherService.NEW_LINE);
		fw.append("Severity:\t" + bugInstance.getBugSeverity() + WriterDispatcherService.NEW_LINE);
		fw.append("Message:" + WriterDispatcherService.NEW_LINE + "\t" + formatBugMessage(bugInstance.getBugMessage()));


		fw.flush();
		fw.close();
		
		return messageFile.getAbsolutePath();
	}
	
	/**
	 * Splits up the bug message and re-writes so that line breaks can be recognized in the text file.
	 * @param message
	 * @return
	 */
	private String formatBugMessage(String message) {
		
		String messageSection = message.split("Vulnerable Versions:\\n")[0];
		String versionSection = message.split("Vulnerable Versions:\\n")[1].split("Identifiers:\\n")[0];
		String identifiersSection = message.split("Identifiers:\\n")[1];
		
		StringBuilder formattedMessage = new StringBuilder(messageSection.split("\\s{3,}- ").length + versionSection.split("\\s{3,}- ").length + identifiersSection.split("\\s{3,}- ").length);
		int position = 0;
		for(String point: messageSection.split("\\s{3,}- ")) {
			if(position == 0) {
				formattedMessage.append(point + WriterDispatcherService.NEW_LINE);

			} else {
				formattedMessage.append("\t- " + point + WriterDispatcherService.NEW_LINE);
			}
			position++;
		}
		
		formattedMessage.append(WriterDispatcherService.NEW_LINE + "Vulnerable Versions:" + WriterDispatcherService.NEW_LINE);
		for(String point: versionSection.split("\\s{3,}- ")) {
			if(point.matches("")) {
				continue;
			}
			formattedMessage.append("\t- " + point + WriterDispatcherService.NEW_LINE);
		}
		
		formattedMessage.append(WriterDispatcherService.NEW_LINE + "Identifiers:" + WriterDispatcherService.NEW_LINE);
		for(String point: identifiersSection.split("\\s{3,}- ")) {
			if(point.matches("")) {
				continue;
			}
			formattedMessage.append("\t- " + point + WriterDispatcherService.NEW_LINE);	
		}

		return formattedMessage.toString();
	}

	
	/**
	 * creates the sub-directory that will house the owasp message files.
	 * @return
	 */
	private File initOwaspDir() {
		
		File owaspFolder = new File(bugReportFile.getParent() + "\\owasp_messages\\");		
		
		if(!owaspFolder.exists()) {
			owaspFolder.mkdir();
		} else {
			owaspFolder.delete();
			owaspFolder = new File(bugReportFile.getParent() + "\\owasp_messages\\");
			owaspFolder.mkdir();
		}
		
		return owaspFolder;
		
	}
	
	private String csvSafe(String str) {
		
		if(str == null) {
			return "";
		}
		
		return "\"" +str.trim() + "\"";
	}
}
