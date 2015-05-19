package de.colenet.docker.tdd

import org.codehaus.groovy.control.CompilerConfiguration

//import com.bmuschko.gradle.docker.tasks.container.DockerExistingContainer

import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection


class DockerTestContainer {

	String reportDirPath = "${project.buildDir}/containertest-reports"

	FileCollection specFiles = project.fileTree(dir: 'spec')

	void runRemoteCommand(dockerClient) {
		def dsl = new DockerSpecificationDSL(getContainerId(),dockerClient)
		def reporter = createReporter(dsl)
		def testRunner = new ContainerTestRunner(dsl)

		specFiles.each {
			reporter.logTestStart(it.absolutePath)
			testRunner.runSpecFile(it)
			reporter.logSummary()
		}

		if (reporter.failuresExist) {
			throw new GradleException('Failures in Docker Container Tests.')
		}
	}
	
	private DockerContainerTestReporter createReporter(DockerSpecificationDSL dslDelegate) {
		def reporter = new DockerContainerTestReporter(logger,reportDirPath)
		dslDelegate.onTestSuccess += {description -> reporter.logSuccess(description)}
		dslDelegate.onTestFailure += {description, e -> reporter.logFailure(description, e)}
		return reporter
	}

}

