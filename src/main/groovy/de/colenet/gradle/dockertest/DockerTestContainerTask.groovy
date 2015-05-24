package de.colenet.gradle.dockertest

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DockerTestContainerTask extends DefaultTask {

	DockerTestRunner testRunner

	Iterable<File> specFiles

	String containerId

	TestScriptDelegateFactory testScriptDelegateFactory

	DockerTestReporterFactory reporterFactory

	String reportDirPath

	@TaskAction
	void runTests() {
		def reporter = reporterFactory.create(project.logger,reportDirPath)
		def testScriptDelegate = testScriptDelegateFactory.create(containerId)
		testScriptDelegate.onTestSuccess { description -> reporter.logSuccess(description) }
		specFiles.each {
			reporter.logTestStart(it.absolutePath)
			testRunner.runSpecFile(testScriptDelegate,it)
			reporter.logSummary()
		}
	}
}
