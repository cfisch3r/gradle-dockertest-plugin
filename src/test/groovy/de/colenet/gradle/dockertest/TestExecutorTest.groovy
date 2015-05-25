package de.colenet.gradle.dockertest

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener.*
import org.gradle.api.GradleException

import spock.lang.Specification

class TestExecutorTest extends Specification {

	TestExecutor sut;

	def specFile = Mock(File)

	def testRunner = Mock(DockerTestRunner)

	def reporter = Mock(DockerTestReporter)

	def result = Mock(TestResult)

	def setup() {
		testRunner.runSpecFile(specFile) >> result
		sut = new TestExecutor(testRunner, reporter)
	}

	def "runSpecFile delegates execution to test runner"() {
		when:
		sut.execute(specFile)
		then:
		1 * testRunner.runSpecFile(specFile)  >> result
	}

	def "runSpecFile logs result with reporter"() {
		when:
		sut.execute(specFile)
		then:
		1 * reporter.log(result)
	}

	def "runSpecFile throws Exception when there are any failures in test result"() {
		setup:
		result.hasFailures() >> Boolean.TRUE
		when:
		sut.execute(specFile)
		then:
		thrown(GradleException)
	}
}
