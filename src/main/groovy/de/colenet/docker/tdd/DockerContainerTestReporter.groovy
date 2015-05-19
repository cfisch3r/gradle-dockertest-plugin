package de.colenet.docker.tdd

import org.gradle.api.logging.Logger;

/**
 * Reporter for Container Test results.
 *
 */
class DockerContainerTestReporter {
	
	private class Report {
		
		Integer testCount = 0
		
		Integer errorCount = 0
		
		List<String> messages = []
		
		final String name
		
		Report(String name) {
			this.name = name
		} 
		
	} 

	private Integer totalErrorCount = 0
	
	private Report report
	

	private Logger logger

	private String reportDirPath

	/**
	 * Constructor.
	 * 
	 * @param logger Gradle Logger
	 * @param reportDirPath absolute path where report files should be saved
	 */
	DockerContainerTestReporter(Logger logger, String reportDirPath) {
		this.logger = logger
		this.reportDirPath = reportDirPath
		new File(reportDirPath).mkdirs()
	}

	/**
	 * logs test start.
	 * 
	 * @param specFile name of test specification file
	 */
	void logTestStart(String specFile) {
		updateTotalErrorCount()
		report  = new Report(createReportFileName(specFile));
		logger.lifecycle("Running specification $specFile")
	}
	
	private String createReportFileName(String specFile) {
		 return specFile.find(/([^\/\.]+)(\.\w+)?$/,{it[1]}) + '.txt' 
	}

	private updateTotalErrorCount() {
		if (report != null) {
			totalErrorCount += report.errorCount
		}
	}

	/**
	 * logs successfull test.
	 * 
	 * @param description test description
	 */
	void logSuccess(String description) {
		report.testCount++
		report.messages.add("Specification: $description passed.")
	}

	/**
	 * logs failed test.
	 * 
	 * @param description test description
	 * @param e assertion exception
	 */
	void logFailure(String description, AssertionError e) {
		report.errorCount++
		report.testCount++
		report.messages.add("Specification: $description failed.${e.message}")
	}

	/**
	 * logs Test results.
	 */
	void logSummary() {
		writeMessageToReportFile(report.name)
		logger.lifecycle("Tests run: ${report.testCount}, Failures: ${report.errorCount}")
	}
	
	/**
	 * checks total failure count.
	 * 
	 * @return true if failure was detected
	 */
	boolean getFailuresExist() {
		return totalErrorCount > 0
	}

	private writeMessageToReportFile(String reportFileName) {
		File reportFile = new File(reportDirPath, reportFileName)
		reportFile.withWriter { writer ->
			report.messages.each { writer.writeLine it }
		}
	}
}
