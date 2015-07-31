package de.colenet.gradle.dockertest

class DockerTestDSL {

	private DockerTestReporter reporter

	DockerTestDSL(DockerTestReporter reporter) {
		this.reporter = reporter
	}

	DockerTestSpecification specification(String description) {
		return new DockerTestSpecification(reporter);
	}
}
