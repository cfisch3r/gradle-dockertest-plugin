package de.colenet.gradle.dockertest

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DockerTestContainerTask extends DefaultTask {

	DockerTestRunner testRunner

	Iterable<File> specFiles

	String containerId

	TestScriptDelegateFactory testScriptDelegateFactory

	@TaskAction
	void runTests() {
		specFiles.each {  testRunner.runSpecFile(testScriptDelegateFactory.create(containerId),it)  }
	}
}
