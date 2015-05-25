package de.colenet.gradle.dockertest

import org.gradle.api.GradleException


class TestExecutor {

	private DockerTestRunner testRunner

	private DockerTestReporter reporter

	TestExecutor(DockerTestRunner testRunner, DockerTestReporter reporter) {
		this.testRunner = testRunner
		this.reporter = reporter
	}

	void execute(File specFile) {
		TestResult result = testRunner.runSpecFile(specFile)
		reporter.log(result)
		if (result.hasFailures()) {
			throw new GradleException("Test failures in ${specFile.absolutePath}")
		}
	}
}
