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
		specFiles.each {
			println "sdfsd"
			println it.absolutePath
			reporter.logTestStart(it.absolutePath)
			testRunner.runSpecFile(testScriptDelegateFactory.create(containerId),it)
			reporter.logSummary()
		}
	}
}
