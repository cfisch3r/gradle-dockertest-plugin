package de.colenet.gradle.dockertest

class TestResult {
	class Failure {
		String specification
		String errorMessage
	}
	List<Failure> failures = []

	List<String> successes = []
}
