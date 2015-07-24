package de.colenet.gradle.dockertest

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DockerTestContainerTask extends DefaultTask {

	def specFile

	@TaskAction
	void runTests() {
		def fileName = specFile ?: project.projectDir.absolutePath + '/test/docker/dockertest.spec'
		project.dockertest.testrunner.runSpecFile(project.file(fileName))
	}
}
