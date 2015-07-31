package de.colenet.gradle.dockertest

import de.colenet.dockertest.DockerClient


class DockerTestSpecification {

	private DockerTestReporter reporter

	private DockerClient client

	DockerTestSpecification(DockerClient client, DockerTestReporter reporter) {
		this.client = client
		this.reporter = reporter
	}

	void requires(Closure assertion) {
		client.execute("a","b")
		assertion.setDelegate(client)
		assertion.setResolveStrategy(Closure.DELEGATE_FIRST)
		assertion.call()
		reporter.log(new TestResult())
	}

	void execute(String a, String b) {
	}
}
