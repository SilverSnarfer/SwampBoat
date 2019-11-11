/**
 * 
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import TestingTools.TestFilenames;
import exceptions.BugFilterException;
import pojo.AnalyzerReport;
import pojo.BugInstance;
import services.BugFilterService.ListType;
import tools.SwampProperties;

/**
 * @author Aaron.Hayward
 *
 */
public class TestBugFilterService {
	private static final Logger logger = LogManager.getLogger(TestBugFilterService.class);
	private final JaxbService jaxbService = new JaxbService();
	
	@Test
	public void testInstantiationWhitelist() {
		boolean noError = true;
		BugFilterService bf = null;
		try {
			SwampProperties sp = new SwampProperties(new FileInputStream(TestFilenames.goodFilter_whitelist));
			bf = new BugFilterService(sp);
		} catch (Exception e) {
			logger.error("TestBugFilterService.testInstantiation", e);
			noError = false;
		}
		Assert.assertTrue(noError && bf.getListType().equals(ListType.WHITELIST));
	}
	
	
	
	@Test
	public void testInstantiationBlacklist() {
		boolean noError = true;
		BugFilterService bf = null;
		try {
			Properties sp = new SwampProperties(new FileInputStream(TestFilenames.goodFilter_Blacklist));
			bf = new BugFilterService(sp);
		} catch (Exception e) {
			logger.error("TestBugFilterService.testInstantiation", e);
			noError = false;
		}
		Assert.assertTrue(noError && bf.getListType().equals(ListType.BLACKLIST));
	}
	
	@Test
	public void testBadInstantiation() {
		boolean noError = true;
		BugFilterService bf = null;
		try {
			SwampProperties sp = new SwampProperties(new FileInputStream(TestFilenames.badFilter));
			bf = new BugFilterService(sp);
		}catch (BugFilterException b) {
			noError = false;
		} catch (Exception e) {
			logger.error("Error during 'TestBugFilterService.testInstantiation'", e);
		}
		Assert.assertTrue(!noError);
	}
	
	@Test
	public void testFilter() {
		boolean noError = true;
		BugFilterService bf = null;
		AnalyzerReport analyzerReport = null;
		List<BugInstance> bugInstances = null;
		
		try {
			Properties sp = new SwampProperties(new FileInputStream(TestFilenames.goodFilter));
			analyzerReport = jaxbService.unMarshall(new File(TestFilenames.goodPMD));
			bf = new BugFilterService(sp);
			bugInstances = bf.filter(analyzerReport.getBugInstances());
		} catch (Exception e) {
			logger.error("Error during 'TestBugFilterService.testFilter'", e);
			noError = false;
		}
		
		Assert.assertTrue(noError && bugInstances.size() < analyzerReport.getBugInstances().size());
	}
	

}
