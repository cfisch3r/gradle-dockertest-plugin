package de.colenet.gradle.dockertest

import de.colenet.dockertest.DockerClient


class DockerTestSpecification {

	private DockerTestReporter reporter

	private DockerClient client

	private String title;

	DockerTestSpecification(String title, DockerClient client, DockerTestReporter reporter) {
		this.title = title;
		this.client = client
		this.reporter = reporter
	}

	void requires(Closure assertion) {
		assertion.setDelegate(client)
		assertion.setResolveStrategy(Closure.DELEGATE_FIRST)
		try {
			assertion.call()
			reporter.logSuccess(title)
		} catch (Throwable e) {
			reporter.logFailure(title,e)
		}
	}
}
