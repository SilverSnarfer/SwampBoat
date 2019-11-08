/**
 * 
 */
package services;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import pojo.AnalyzerReport;

/**
 * @author Aaron.Hayward
 *
 */
public class JaxbService {

	
	public AnalyzerReport unMarshall(File file) throws JAXBException{
		AnalyzerReport report = null;
		
		JAXBContext jaxbContext = JAXBContext.newInstance(AnalyzerReport.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		report = (AnalyzerReport) jaxbUnmarshaller.unmarshal(file);
	
		
		return report;
	}
}
