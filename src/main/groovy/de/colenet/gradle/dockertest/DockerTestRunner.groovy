package de.colenet.gradle.dockertest

class DockerTestRunner {

	TestScriptDelegate delegate

	DockerTestRunner(TestScriptDelegate delegate) {
		this.delegate = delegate
	}

	TestResult runSpecFile(File specFile) {
	}
}
